package org.linlinjava.litemall.admin.util;

import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    public static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    /**
     * 英文
     */
    public static final String DD_MMM_YYYY = "dd MMM yyyy";


    /**
     *  日期转字符串
     * @param dateTime
     * @return
     */
    public static String dateToString(LocalDateTime dateTime){
        return dateToString(dateTime, YYYY_MM_DD_HH_MM);
    }
    /**
     *  日期转字符串
     * @param dateTime
     * @param pattern
     * @return
     */
    public static String dateToString(LocalDateTime dateTime, String pattern){
        if(null == dateTime){
            return null;
        }
        if(StringUtils.isEmpty(pattern)){
            pattern = YYYY_MM_DD_HH_MM;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return dateTime.format(formatter);
    }

    /**
     *
     * 字符串转日期
     * @param dateTimeStr
     * @return
     */
    public static LocalDateTime stringToDate(String dateTimeStr){
        return stringToDate(dateTimeStr, YYYY_MM_DD_HH_MM);
    }
    /**
     *
     * 字符串转日期
     * @param dateTimeStr
     * @param pattern
     * @return
     */
    public static LocalDateTime stringToDate(String dateTimeStr, String pattern){
        if(StringUtils.isEmpty(dateTimeStr)){
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDateTime.parse(dateTimeStr, formatter);
    }
}
