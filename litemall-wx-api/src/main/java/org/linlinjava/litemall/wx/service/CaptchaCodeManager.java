package org.linlinjava.litemall.wx.service;

import org.linlinjava.litemall.wx.dto.CaptchaItem;

import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 缓存系统中的验证码
 */
public class CaptchaCodeManager {
    private static ConcurrentHashMap<String, CaptchaItem> captchaCodeCache = new ConcurrentHashMap<>();

    /**
     * 添加到缓存
     *
     * @param cacheKey 电话号码或者邮箱地址
     * @param code        验证码
     */
    public static boolean addToCache(String cacheKey, String code) {


        //已经发过验证码且验证码还未过期
        if (captchaCodeCache.get(cacheKey) != null) {
            if (captchaCodeCache.get(cacheKey).getExpireTime().isAfter(LocalDateTime.now())) {
                return false;
            } else {
                //存在但是已过期，删掉
                captchaCodeCache.remove(cacheKey);
            }
        }

        CaptchaItem captchaItem = new CaptchaItem();
        captchaItem.setPhoneNumber(cacheKey);
        captchaItem.setCode(code);
        // 有效期为1分钟
        captchaItem.setExpireTime(LocalDateTime.now().plusMinutes(1));

        captchaCodeCache.put(cacheKey, captchaItem);

        return true;
    }

    /**
     * 获取缓存的验证码
     *
     * @param cachekey 关联的电话号码或邮箱地址
     * @return 验证码
     */
    public static String getCachedCaptcha(String cachekey) {
        //没有这个电话记录
        if (captchaCodeCache.get(cachekey) == null)
            return null;

        //有电话记录但是已经过期
        if (captchaCodeCache.get(cachekey).getExpireTime().isBefore(LocalDateTime.now())) {
            return null;
        }

        return captchaCodeCache.get(cachekey).getCode();
    }
}
