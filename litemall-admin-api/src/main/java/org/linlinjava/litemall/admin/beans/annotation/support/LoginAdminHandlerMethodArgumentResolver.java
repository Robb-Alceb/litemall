package org.linlinjava.litemall.admin.beans.annotation.support;

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

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
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
            return admin.getShopId();
        }
        String value  = request.getParameter(parameter.getParameter().getName());
        if(!StringUtils.isEmpty(value)){
            return Integer.parseInt(value);
        }
        return null;
    }
}
