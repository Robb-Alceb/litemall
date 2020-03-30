package org.linlinjava.litemall.wx.aop;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.linlinjava.litemall.wx.annotation.LogAnno;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author ：liushichang
 * @date ：Created in 2019/4/24 17:30
 * @description：日志记录
 */
@Component
@Aspect
public class WxLogAop {

    private static Logger logger = LoggerFactory.getLogger(WxLogAop.class);

    @Around("@annotation(org.linlinjava.litemall.wx.annotation.LogAnno)")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable{
        if(!(pjp.getSignature() instanceof MethodSignature)){
            return pjp.proceed();
        }
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        Method method = AopUtils.getMostSpecificMethod(methodSignature.getMethod(), pjp.getTarget().getClass());
        LogAnno logInfo = method.getAnnotation(LogAnno.class);
        long startTime = System.currentTimeMillis();
        Object result = null;
        try{
            result = pjp.proceed();
        }catch(Throwable e){
            if(logInfo.logError()){
                printLog(method,logInfo,pjp.getArgs(),e,startTime);
            }
            throw e;
        }
        printLog(method,logInfo,pjp.getArgs(),result,startTime);
        return result;
    }

    private void printLog(Method method, LogAnno logInfo, Object[] args, Object result, long startTime) {
        String logBody = builderLog(method,logInfo,result instanceof Throwable);
        List<Object> param = filterReqParam(args);

        if(logInfo.logTime()){
            logger.info(logBody, JSON.toJSONString(param),System.currentTimeMillis()-startTime,result);
        }else{
            logger.info(logBody,JSON.toJSONString(param),result);
        }

    }


    private List<Object> filterReqParam(Object[] args) {
        List<Object> param = Lists.newArrayList();
        for(Object arg : args){
            if(arg instanceof ServletRequest
                    ||arg instanceof ServletResponse
                    ||arg instanceof HttpSession
                    ||arg instanceof ModelMap
                    ||arg instanceof ModelAndView
                    ||arg instanceof MultipartFile
                    ||arg instanceof MultipartFile[]){
                continue;
            }
            param.add(arg);
        }
        return param;
    }

    private String builderLog(Method method, LogAnno logInfo, boolean isException) {
        StringBuilder body = new StringBuilder(builderMethodBody(method,logInfo));
        body.append(" param:{}");
        if(logInfo.logTime()){
            body.append(",time:{}ms");
        }
        if(isException){
            body.append(",exception:");
        }else{
            body.append(",result:{}");
        }
        return body.toString();
    }


    private String builderMethodBody(Method method, LogAnno logInfo) {
        StringBuilder builder = new StringBuilder();
        if (!StringUtils.isEmpty(logInfo.value())) {
            builder.append(logInfo.value());
        }else{
            builder.append(method.getDeclaringClass().getSimpleName()).append(".").append(method.getName()).append("()");
        }
        return builder.toString();
    }


}
