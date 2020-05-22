package org.linlinjava.litemall.core.i18n.Interceptor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.linlinjava.litemall.core.i18n.config.I18nProperties;
import org.linlinjava.litemall.db.domain.LitemallI18n;
import org.linlinjava.litemall.db.service.LitemallI18nService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author ：stephen
 * @date ：Created in 2020/3/27 16:04
 * @description：TODO
 */


@Component
@Intercepts({
        @Signature(
                type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class
        })
})
public class I18nInterceptor implements Interceptor {

    private static final Log logger = LogFactory.getLog(I18nInterceptor.class);

    @Autowired
    private I18nProperties i18nProperties;
    @Autowired
    @Lazy
    private LitemallI18nService litemallI18nService;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        // 方法一
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        MetaObject metaObject = MetaObject.forObject(statementHandler, SystemMetaObject.DEFAULT_OBJECT_FACTORY, SystemMetaObject.DEFAULT_OBJECT_WRAPPER_FACTORY, new DefaultReflectorFactory());
        //先拦截到RoutingStatementHandler，里面有个StatementHandler类型的delegate变量，其实现类是BaseStatementHandler，然后就到BaseStatementHandler的成员变量mappedStatement
        MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
        //sql语句类型 select、delete、insert、update
        String sqlCommandType = mappedStatement.getSqlCommandType().toString();

        if("UPDATE".equals(sqlCommandType) || "INSERT".equals(sqlCommandType)){
            Object parameterObject = statementHandler.getParameterHandler().getParameterObject();
            List<String> classNames = i18nProperties.getClasses().get(statementHandler.getParameterHandler().getParameterObject().getClass().getName());
            if(classNames != null && classNames.size() > 0){
                for(String className : classNames){
                    String i18nKey = getFieldValueByName(className, parameterObject);
                    addI18n(i18nKey);
                }
            }
        }


        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }

    /**
     * 获取属性值
     * @param fieldName
     * @param o
     * @return
     */
    private static String getFieldValueByName(String fieldName, Object o) {
        try {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + fieldName.substring(1);
            Method method = o.getClass().getMethod(getter, new Class[] {});
            Object value = method.invoke(o, new Object[] {});
            return String.valueOf(value);
        } catch (Exception e) {
            return null;
        }
    }
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void addI18n(String key){
        try{
            if(!StringUtils.isEmpty(key)){
                if(litemallI18nService.countByKey(key) == 0){
                    LitemallI18n record = new LitemallI18n();
                    record.setKey(key.trim());
                    record.setValue(key.trim());
                    litemallI18nService.add(record);
                };
            }
        }catch (Exception e){
            logger.info("insert i18n error, key is : "+ key);
        }

    }

}
