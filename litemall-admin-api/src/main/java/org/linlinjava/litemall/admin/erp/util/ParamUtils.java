package org.linlinjava.litemall.admin.erp.util;

import org.linlinjava.litemall.admin.erp.rq.BaseRQ;
import org.linlinjava.litemall.admin.erp.rq.PurchaseRQ;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * @author ：stephen
 * @date ：Created in 2020/7/1 14:11
 * @description：erp参数处理工具类
 */

/**
 * 将RQ转换为Map<String, String>类型
 */
public class ParamUtils {

    public static String rqToParam(BaseRQ model, String url) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(url);
        buffer.append("?a=1");
        if(model == null){
            return buffer.toString();
        }
        Field[] field = model.getClass().getDeclaredFields(); // 获取实体类的所有属性，返回Field数组
        try {
            for (int j = 0; j < field.length; j++) { // 遍历所有属性
                String name = field[j].getName(); // 获取属性的名字
                Method m = model.getClass().getMethod("get" + name.substring(0, 1).toUpperCase() + name.substring(1));
                Object value = m.invoke(model); // 调用getter方法获取属性值
                if(value != null){
                    buffer.append("&").append(name).append("=").append(value);
                }
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return buffer.toString();
    }

    public static MultiValueMap<String, String> rqToMultiMap(PurchaseRQ model) {
        MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
        if(model == null){
            return map;
        }
        Field[] field = model.getClass().getDeclaredFields(); // 获取实体类的所有属性，返回Field数组
        try {
            for (int j = 0; j < field.length; j++) { // 遍历所有属性
                String name = field[j].getName(); // 获取属性的名字
                Method m = model.getClass().getMethod("get" + name.substring(0, 1).toUpperCase() + name.substring(1));
                Object value = m.invoke(model); // 调用getter方法获取属性值
                if(value != null){
                    map.add(name, String.valueOf(value));
                }
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return map;
    }
}
