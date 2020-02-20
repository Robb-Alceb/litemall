package org.linlinjava.litemall.web.service;

import org.linlinjava.litemall.web.util.JwtHelper;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 维护用户token
 */
public class UserTokenManager {
	public static String generateToken(Integer id) {
        return generateToken(id, "");
    }
    public static String generateToken(Integer id, String username) {
        JwtHelper jwtHelper = new JwtHelper();
        return jwtHelper.createToken(id, username);
    }
    public static String generateToken(Integer id, Integer shopId) {
        JwtHelper jwtHelper = new JwtHelper();
        Map<String, Integer> claimMap = new HashMap<>();
        claimMap.put("userId",id);
        claimMap.put("shopId", shopId);
        return jwtHelper.createToken(claimMap);
    }
    public static Integer getUserId(String token) {
    	JwtHelper jwtHelper = new JwtHelper();
    	Integer userId = jwtHelper.verifyTokenAndGetUserId(token);
    	if(userId == null || userId == 0){
    		return null;
    	}
        return userId;
    }
    public static Integer getShopId(String token) {
        JwtHelper jwtHelper = new JwtHelper();
        Integer shopId = jwtHelper.verifyTokenAndGetShopId(token);
        if(shopId == null || shopId == 0){
            return null;
        }
        return shopId;
    }
    public static String getUserName(String token) {
        JwtHelper jwtHelper = new JwtHelper();
        String userName = jwtHelper.verifyTokenAndGetUserName(token);
        if(userName == null || StringUtils.isEmpty(userName)){
            return null;
        }
        return userName;
    }
}
