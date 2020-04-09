package org.linlinjava.litemall.wx.util;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @author ：stephen
 * @date ：Created in 2020/4/2 17:48
 * @description：TODO
 */
public class GeneratorUtil {
    public static String codeGenerator(){
        return RandomStringUtils.randomAlphabetic(4);
    }

    public static String linkGenerator(){
        return RandomStringUtils.randomAlphabetic(16);
    }

    /**
     * 根据类型和用户id生成 2位Type + 10位数字的卡号
     * @param type (CG:礼物卡)
     * @return
     */
    public static String cardNumberGenerator(String type, Integer userId){
        String user = StringUtils.leftPad(String.valueOf(userId), 6, "0");
        return type + user + RandomStringUtils.randomNumeric(6);
    }

}
