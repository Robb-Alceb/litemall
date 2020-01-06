package org.linlinjava.litemall.admin.beans.annotation.support;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.SecurityUtils;
import org.linlinjava.litemall.admin.beans.annotation.LoginAdminShopId;
import org.linlinjava.litemall.db.domain.LitemallAdmin;
import org.springframework.core.MethodParameter;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;


public class LoginAdminHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
    private final Log logger = LogFactory.getLog(LoginAdminHandlerMethodArgumentResolver.class);

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        logger.debug("LoginAdminHandlerMethodArgumentResolver clazz is: " + parameter.getContainingClass().getName());
        logger.debug("LoginAdminHandlerMethodArgumentResolver method is: " + parameter.getMethod().getName());
        logger.debug("LoginAdminHandlerMethodArgumentResolver supportsParameter parameter is: " + parameter.getParameterName());
        return parameter.getParameterType().isAssignableFrom(Integer.class) && parameter.hasParameterAnnotation(LoginAdminShopId.class);
    }

    /**
     * 获取登录用户的门店id
     * 如果是门店用户，则返回自己所在门店的shopId
     * 如果不是门店用户，则用前端传过来的值
     */
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer container,
                                  NativeWebRequest request, WebDataBinderFactory factory) throws Exception {
        LitemallAdmin admin = (LitemallAdmin) SecurityUtils.getSubject().getPrincipal();

        if(null != admin.getShopId()){
            logger.debug("LoginAdminHandlerMethodArgumentResolver get admin is "+ admin.toString());
            return admin.getShopId();
        }
        String value  = request.getParameter(parameter.getParameterName());
        logger.debug("LoginAdminHandlerMethodArgumentResolver get parameter name is "+ parameter.getParameterName());
        logger.debug("LoginAdminHandlerMethodArgumentResolver get parameter value is "+ value);
        if(!StringUtils.isEmpty(value)){
            return Integer.parseInt(value);
        }
        return null;
    }
}
