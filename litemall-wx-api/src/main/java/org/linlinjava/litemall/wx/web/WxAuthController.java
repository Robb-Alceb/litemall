package org.linlinjava.litemall.wx.web;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.core.notify.NoticeHelper;
import org.linlinjava.litemall.core.notify.NotifyService;
import org.linlinjava.litemall.core.notify.NotifyType;
import org.linlinjava.litemall.core.util.CharUtil;
import org.linlinjava.litemall.core.util.JacksonUtil;
import org.linlinjava.litemall.core.util.RegexUtil;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.core.util.bcrypt.BCryptPasswordEncoder;
import org.linlinjava.litemall.db.domain.LitemallUser;
import org.linlinjava.litemall.db.service.CouponAssignService;
import org.linlinjava.litemall.db.service.LitemallMessageService;
import org.linlinjava.litemall.db.service.LitemallNoticeService;
import org.linlinjava.litemall.db.service.LitemallUserService;
import org.linlinjava.litemall.wx.annotation.LogAnno;
import org.linlinjava.litemall.wx.annotation.LoginUser;
import org.linlinjava.litemall.wx.dto.UserInfo;
import org.linlinjava.litemall.wx.dto.WxLoginInfo;
import org.linlinjava.litemall.wx.service.CaptchaCodeManager;
import org.linlinjava.litemall.wx.service.UserTokenManager;
import org.linlinjava.litemall.core.util.IpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.linlinjava.litemall.wx.util.WxResponseCode.*;

/**
 * 鉴权服务
 */
@RestController
@RequestMapping("/wx/auth")
@Validated
public class WxAuthController {
    private final Log logger = LogFactory.getLog(WxAuthController.class);

    @Autowired
    private LitemallUserService userService;

    @Autowired
    private WxMaService wxService;

    @Autowired
    private NotifyService notifyService;

    @Autowired
    private CouponAssignService couponAssignService;

    @Autowired
    private LitemallNoticeService litemallMsgService;

    @Autowired
    private LitemallMessageService litemallMessageService;

    @Autowired
    private NoticeHelper noticeHelper;

    /**
     * 账号登录
     *
     * @param body    请求内容，{ username: xxx, password: xxx }
     * @param request 请求对象
     * @return 登录结果
     */
    @PostMapping("login")
    @LogAnno
    public Object login(@RequestBody String body, HttpServletRequest request) {
        String username = JacksonUtil.parseString(body, "username");
        String password = JacksonUtil.parseString(body, "password");
        if (username == null || password == null) {
            return ResponseUtil.badArgument();
        }

        List<LitemallUser> userList = userService.queryByUsername(username);
        LitemallUser user = null;
        if (userList.size() > 1) {
            return ResponseUtil.serious();
        } else if (userList.size() == 0) {
            return ResponseUtil.fail(AUTH_INVALID_ACCOUNT, "账号不存在");
        } else {
            user = userList.get(0);
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (!encoder.matches(password, user.getPassword())) {
            return ResponseUtil.fail(AUTH_INVALID_ACCOUNT, "账号密码不对");
        }

        // 更新登录情况
        user.setLastLoginTime(LocalDateTime.now());
        user.setLastLoginIp(IpUtil.getIpAddr(request));
        if (userService.updateById(user) == 0) {
            return ResponseUtil.updatedDataFailed();
        }

        /**
         * 这里获取未读的系统消息或站内消息，
         * 设计思想从一开始生成消息改变为用户登录时判断，避免发布消息是系统资源消耗过高
         */
/*        List<LitemallMessage> messages = litemallMessageService.queryAll();
        for(LitemallMessage message : messages){
            long i = litemallMsgService.countByMessageId(message.getId(), user.getId());
            if(i == 0 ){
                if(message.getType() == Constants.MESSAGE_TYPE_SYSTEM || (message.getReceiverLevels() != null && Arrays.asList(message.getReceiverLevels()).contains(user.getUserLevel()))){
                    noticeHelper.noticeUser(Constants.MESSAGE_TYPE_SYSTEM, message.getTitle(), message.getContent(), user.getId());
*//*                    LitemallNotice msg = new LitemallNotice();
                    msg.setContent(message.getContent());
                    msg.setMessageId(message.getId());
                    msg.setUserId(user.getId());
                    msg.setType(Constants.MSG_TYPE_SYSTEM);
                    litemallMsgService.create(msg);*//*
                }
            }
        }*/



        // userInfo
        UserInfo userInfo = new UserInfo();
        userInfo.setNickName(username);
        userInfo.setAvatarUrl(user.getAvatar());

        // token
        String token = UserTokenManager.generateToken(user.getId());

        Map<Object, Object> result = new HashMap<Object, Object>();
        result.put("token", token);
        result.put("userInfo", userInfo);
        return ResponseUtil.ok(result);
    }

    /**
     * 微信登录
     *
     * @param wxLoginInfo 请求内容，{ code: xxx, userInfo: xxx }
     * @param request     请求对象
     * @return 登录结果
     */
    @PostMapping("login_by_weixin")
    @LogAnno
    public Object loginByWeixin(@RequestBody WxLoginInfo wxLoginInfo, HttpServletRequest request) {
        String code = wxLoginInfo.getCode();
        UserInfo userInfo = wxLoginInfo.getUserInfo();
        if (code == null || userInfo == null) {
            return ResponseUtil.badArgument();
        }

        String sessionKey = null;
        String openId = null;
        try {
            WxMaJscode2SessionResult result = this.wxService.getUserService().getSessionInfo(code);
            sessionKey = result.getSessionKey();
            openId = result.getOpenid();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (sessionKey == null || openId == null) {
            return ResponseUtil.fail();
        }

        LitemallUser user = userService.queryByOid(openId);
        if (user == null) {
            user = new LitemallUser();
            user.setUsername(openId);
            user.setPassword(openId);
            user.setWeixinOpenid(openId);
            user.setAvatar(userInfo.getAvatarUrl());
            user.setNickname(userInfo.getNickName());
            user.setGender(userInfo.getGender());
            user.setUserLevel((byte) 0);
            user.setStatus((byte) 0);
            user.setLastLoginTime(LocalDateTime.now());
            user.setLastLoginIp(IpUtil.getIpAddr(request));
            user.setSessionKey(sessionKey);

            userService.add(user);

            // 新用户发送注册优惠券
            couponAssignService.assignForRegister(user.getId());
        } else {
            user.setLastLoginTime(LocalDateTime.now());
            user.setLastLoginIp(IpUtil.getIpAddr(request));
            user.setSessionKey(sessionKey);
            if (userService.updateById(user) == 0) {
                return ResponseUtil.updatedDataFailed();
            }
        }

        // token
        String token = UserTokenManager.generateToken(user.getId());

        Map<Object, Object> result = new HashMap<Object, Object>();
        result.put("token", token);
        result.put("userInfo", userInfo);
        return ResponseUtil.ok(result);
    }


    /**
     * 请求注册验证码
     *
     * TODO
     * 这里需要一定机制防止短信验证码被滥用
     *
     * @param body 手机号码 { mobile }
     * @param body 邮件地址 { email }
     * @return
     */
//    @PostMapping("regCaptcha")
//    @LogAnno
   /*public Object registerCaptcha(@RequestBody String body) {
        String username = JacksonUtil.parseString(body, "username");
        String type = JacksonUtil.parseString(body, "type");
        String email = JacksonUtil.parseString(body, "email");
        String phoneNumber = JacksonUtil.parseString(body, "mobile");
        if(!StringUtils.isEmpty(username)){
            return ResponseUtil.badArgument();
        }
        if("email".equals(type)){
            if (StringUtils.isEmpty(email)) {
                return ResponseUtil.badArgument();
            }
            if (!RegexUtil.isEmailExact(email)) {
                return ResponseUtil.badArgumentValue();
            }
            if (!notifyService.isMailEnable()) {
                return ResponseUtil.fail(AUTH_CAPTCHA_UNSUPPORT, "邮件发送后台验证码服务不支持");
            }
            String code = CharUtil.getRandomNum(6);
            notifyService.notifySmsTemplate(email, NotifyType.CAPTCHA, new String[]{code});

            boolean successful = CaptchaCodeManager.addToCache(email, code);
            if (!successful) {
                return ResponseUtil.fail(AUTH_CAPTCHA_FREQUENCY, "验证码未超时1分钟，不能发送");
            }
            notifyService.notifyMail("注册码通知", code, email);
        }else if("mobile".equals(type)){
            if (StringUtils.isEmpty(phoneNumber)) {
                return ResponseUtil.badArgument();
            }
            if (!RegexUtil.isMobileExact(phoneNumber)) {
                return ResponseUtil.badArgumentValue();
            }

            if (!notifyService.isSmsEnable()) {
                return ResponseUtil.fail(AUTH_CAPTCHA_UNSUPPORT, "小程序后台验证码服务不支持");
            }
            String code = CharUtil.getRandomNum(6);
            notifyService.notifySmsTemplate(phoneNumber, NotifyType.CAPTCHA, new String[]{code});

            boolean successful = CaptchaCodeManager.addToCache(phoneNumber, code);
            if (!successful) {
                return ResponseUtil.fail(AUTH_CAPTCHA_FREQUENCY, "验证码未超时1分钟，不能发送");
            }
        }else{
            return ResponseUtil.badArgumentValue();
        }


        return ResponseUtil.ok();
    }*/
    /**
     * 请求注册验证码
     *
     * TODO
     * 这里需要一定机制防止短信验证码被滥用
     *
     * @param body 手机号码 { mobile }
     * @param body 邮件地址 { email }
     * @return
     */
    @PostMapping("regCaptcha")
    @LogAnno
    public Object registerCaptcha(@RequestBody String body) {
        String username = JacksonUtil.parseString(body, "username");
        if(StringUtils.isEmpty(username)){
            return ResponseUtil.badArgument();
        }
        if(RegexUtil.isMobileExact(username)){

            if (!RegexUtil.isMobileExact(username)) {
                return ResponseUtil.badArgumentValue();
            }

            if (!notifyService.isSmsEnable()) {
                return ResponseUtil.fail(AUTH_CAPTCHA_UNSUPPORT, "后台验证码服务不支持");
            }
            String code = CharUtil.getRandomNum(6);
            notifyService.notifySmsTemplate(username, NotifyType.CAPTCHA, new String[]{code});

            boolean successful = CaptchaCodeManager.addToCache(username, code);
            if (!successful) {
                return ResponseUtil.fail(AUTH_CAPTCHA_FREQUENCY, "验证码未超时1分钟，不能发送");
            }
        }else if(RegexUtil.isEmailExact(username)){
            if (!notifyService.isMailEnable()) {
                return ResponseUtil.fail(AUTH_CAPTCHA_UNSUPPORT, "邮件发送后台验证码服务不支持");
            }
            String code = CharUtil.getRandomNum(6);
//            String tmplate = "欢迎注册Lumiere。你的注册码为%s,有效时间一分钟";
            notifyService.notifyMailTemplate("注册码通知", NotifyType.CAPTCHA, username, new String[]{code});

            boolean successful = CaptchaCodeManager.addToCache(username, code);
            if (!successful) {
                return ResponseUtil.fail(AUTH_CAPTCHA_FREQUENCY, "验证码未超时1分钟，不能发送");
            }
        }

        return ResponseUtil.ok();
    }
    /**
     * 账号注册
     *
     * @param body    请求内容
     *                {
     *                username: xxx,
     *                password: xxx,
     *                mobile: xxx
     *                code: xxx
     *                }
     *                其中code是手机验证码，目前还不支持手机短信验证码
     * @param request 请求对象
     * @return 登录结果
     * 成功则
     * {
     * errno: 0,
     * errmsg: '成功',
     * data:
     * {
     * token: xxx,
     * tokenExpire: xxx,
     * userInfo: xxx
     * }
     * }
     * 失败则 { errno: XXX, errmsg: XXX }
     */
    @PostMapping("register")
    @LogAnno
    public Object register(@RequestBody String body, HttpServletRequest request) {
        String username = JacksonUtil.parseString(body, "username");
        String password = JacksonUtil.parseString(body, "password");
        String mobile = JacksonUtil.parseString(body, "mobile");
        String code = JacksonUtil.parseString(body, "code");
        String wxCode = JacksonUtil.parseString(body, "wxCode");
        String email = JacksonUtil.parseString(body, "email");

        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)
                || (StringUtils.isEmpty(wxCode) && StringUtils.isEmpty(code))) {
            return ResponseUtil.badArgument();
        }

        List<LitemallUser> userList = userService.queryByUsername(username);
        if (userList.size() > 0) {
            return ResponseUtil.fail(AUTH_NAME_REGISTERED, "用户名已注册");
        }

        if (!RegexUtil.isMobileExact(username) && !RegexUtil.isEmailExact(username)) {
            return ResponseUtil.fail(AUTH_INVALID_MOBILE, "手机号或邮箱格式不正确");
        }

        if(RegexUtil.isMobileExact(username)){
            userList = userService.queryByMobile(username);
            if (userList.size() > 0) {
                return ResponseUtil.fail(AUTH_MOBILE_REGISTERED, "手机号已注册");
            }
            mobile = username;
        }

        if(RegexUtil.isEmailExact(username)) {
            userList = userService.queryByEmail(username);
            if (userList.size() > 0) {
                return ResponseUtil.fail(AUTH_EMAIL_REGISTERED, "邮箱已注册");
            }
            email = username;
        }

        //判断验证码是否正确
//        String cacheCode = CaptchaCodeManager.getCachedCaptcha(mobile);
//        if (cacheCode == null || cacheCode.isEmpty() || !cacheCode.equals(code)) {
//            return ResponseUtil.fail(AUTH_CAPTCHA_UNMATCH, "验证码错误");
//        }


        LitemallUser user = null;
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(password);
        user = new LitemallUser();
        user.setUsername(username);
        user.setPassword(encodedPassword);
        user.setMobile(mobile);
        user.setEmail(email);
        user.setAvatar("https://yanxuan.nosdn.127.net/80841d741d7fa3073e0ae27bf487339f.jpg?imageView&quality=90&thumbnail=64x64");
        user.setNickname(username);
        user.setGender((byte) 0);
        user.setUserLevel((byte) 0);
        user.setStatus((byte) 0);
        user.setLastLoginTime(LocalDateTime.now());
        user.setLastLoginIp(IpUtil.getIpAddr(request));
        userService.add(user);

        // 给新用户发送注册优惠券
        couponAssignService.assignForRegister(user.getId());

        // userInfo
        UserInfo userInfo = new UserInfo();
        userInfo.setNickName(username);
        userInfo.setAvatarUrl(user.getAvatar());

        // token
        String token = UserTokenManager.generateToken(user.getId());
        
        Map<Object, Object> result = new HashMap<Object, Object>();
        result.put("token", token);
        result.put("userInfo", userInfo);
        return ResponseUtil.ok(result);
    }


    /**
     * 请求验证码
     *
     * TODO
     * 这里需要一定机制防止短信验证码被滥用
     *
     * @param body 手机号码 { mobile: xxx, type: xxx }
     * @return
     */
    @PostMapping("captcha")
    @LogAnno
    public Object captcha(@LoginUser Integer userId, @RequestBody String body) {
        if(userId == null){
            return ResponseUtil.unlogin();
        }
        String phoneNumber = JacksonUtil.parseString(body, "mobile");
        String type = JacksonUtil.parseString(body, "type");
        String email = JacksonUtil.parseString(body, "email");
        if(StringUtils.isEmpty(type)){
            return ResponseUtil.badArgument();
        }

        if("email".equals(type)){
            LitemallUser user = userService.findById(userId);
            if(StringUtils.isEmpty(user.getEmail())){
                return ResponseUtil.fail(AUTH_EMAIL_NOT_EXIST, "该用户未绑定邮箱");
            }
            if (!notifyService.isMailEnable()) {
                return ResponseUtil.fail(AUTH_CAPTCHA_UNSUPPORT, "邮件发送后台验证码服务不支持");
            }
            String code = CharUtil.getRandomNum(6);
            notifyService.notifySmsTemplate(user.getEmail(), NotifyType.CAPTCHA, new String[]{code});

            boolean successful = CaptchaCodeManager.addToCache(user.getEmail(), code);
            if (!successful) {
                return ResponseUtil.fail(AUTH_CAPTCHA_FREQUENCY, "验证码未超时1分钟，不能发送");
            }
            notifyService.notifyMail("验证码通知", code, user.getEmail());
        }else{
            LitemallUser user = userService.findById(userId);
            if(StringUtils.isEmpty(user.getMobile())){
                return ResponseUtil.fail(AUTH_MOBILE_NOT_EXIST, "该用户未绑定手机号");
            }
            if (!notifyService.isSmsEnable()) {
                return ResponseUtil.fail(AUTH_CAPTCHA_UNSUPPORT, "小程序后台验证码服务不支持");
            }
            String code = CharUtil.getRandomNum(6);
            // TODO
            // 根据type发送不同的验证码
            notifyService.notifySmsTemplate(user.getMobile(), NotifyType.CAPTCHA, new String[]{code});

            boolean successful = CaptchaCodeManager.addToCache(user.getMobile(), code);
            if (!successful) {
                return ResponseUtil.fail(AUTH_CAPTCHA_FREQUENCY, "验证码未超时1分钟，不能发送");
            }
        }
        return ResponseUtil.ok();
    }

    /**
     * 账号密码重置
     *
     * @param body    请求内容
     *                {
     *                password: xxx,
     *                mobile: xxx
     *                code: xxx
     *                }
     *                其中code是手机验证码，目前还不支持手机短信验证码
     * @param request 请求对象
     * @return 登录结果
     * 成功则 { errno: 0, errmsg: '成功' }
     * 失败则 { errno: XXX, errmsg: XXX }
     */
    @PostMapping("reset")
    @LogAnno
    public Object reset(@RequestBody String body, HttpServletRequest request, @LoginUser Integer userId) {
        if(userId == null){
            return ResponseUtil.unlogin();
        }
        LitemallUser user = userService.findById(userId);
        String password = JacksonUtil.parseString(body, "password");
        String email = user.getEmail();
        String mobile = user.getMobile();
        String code = JacksonUtil.parseString(body, "code");
        String type = JacksonUtil.parseString(body, "type");

        if (StringUtils.isEmpty(type) || (StringUtils.isEmpty(email) &&  StringUtils.isEmpty(mobile)) || StringUtils.isEmpty(code) || StringUtils.isEmpty(password)) {
            return ResponseUtil.badArgument();
        }
        if("email".equals(type)) {
            //判断验证码是否正确
            String cacheCode = CaptchaCodeManager.getCachedCaptcha(email);
            if (cacheCode == null || cacheCode.isEmpty() || !cacheCode.equals(code))
                return ResponseUtil.fail(AUTH_CAPTCHA_UNMATCH, "验证码错误");
            if (StringUtils.isEmpty(email))
                return ResponseUtil.fail(AUTH_EMAIL_NOT_EXIST, "邮箱未绑定");

            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String encodedPassword = encoder.encode(password);
            user.setPassword(encodedPassword);

            if (userService.updateById(user) == 0) {
                return ResponseUtil.updatedDataFailed();
            }

        }else if("mobile".equals(type)){
            //判断验证码是否正确
            String cacheCode = CaptchaCodeManager.getCachedCaptcha(mobile);
            if (cacheCode == null || cacheCode.isEmpty() || !cacheCode.equals(code))
                return ResponseUtil.fail(AUTH_CAPTCHA_UNMATCH, "验证码错误");
            if (StringUtils.isEmpty(mobile))
                return ResponseUtil.fail(AUTH_MOBILE_NOT_EXIST, "手机号未绑定");


            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String encodedPassword = encoder.encode(password);
            user.setPassword(encodedPassword);

            if (userService.updateById(user) == 0) {
                return ResponseUtil.updatedDataFailed();
            }
        }



        return ResponseUtil.ok();
    }

    /**
     * 账号手机号码重置
     *
     * @param body    请求内容
     *                {
     *                password: xxx,
     *                mobile: xxx
     *                code: xxx
     *                }
     *                其中code是手机验证码，目前还不支持手机短信验证码
     * @param request 请求对象
     * @return 登录结果
     * 成功则 { errno: 0, errmsg: '成功' }
     * 失败则 { errno: XXX, errmsg: XXX }
     */
    @PostMapping("resetPhone")
    @LogAnno
    public Object resetPhone(@LoginUser Integer userId, @RequestBody String body, HttpServletRequest request) {
        if(userId == null){
            return ResponseUtil.unlogin();
        }
        String password = JacksonUtil.parseString(body, "password");
        String mobile = JacksonUtil.parseString(body, "mobile");
        String code = JacksonUtil.parseString(body, "code");

        if (mobile == null || code == null || password == null) {
            return ResponseUtil.badArgument();
        }

        //判断验证码是否正确
        String cacheCode = CaptchaCodeManager.getCachedCaptcha(mobile);
        if (cacheCode == null || cacheCode.isEmpty() || !cacheCode.equals(code))
            return ResponseUtil.fail(AUTH_CAPTCHA_UNMATCH, "验证码错误");

        List<LitemallUser> userList = userService.queryByMobile(mobile);
        LitemallUser user = null;
        if (userList.size() > 1) {
            return ResponseUtil.fail(AUTH_MOBILE_REGISTERED, "手机号已注册");
        }
        user = userService.findById(userId);

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (!encoder.matches(password, user.getPassword())) {
            return ResponseUtil.fail(AUTH_INVALID_ACCOUNT, "账号密码不对");
        }

        user.setMobile(mobile);
        if (userService.updateById(user) == 0) {
            return ResponseUtil.updatedDataFailed();
        }

        return ResponseUtil.ok();
    }

    /**
     * 账号邮箱重置
     *
     * @param body    请求内容
     *                {
     *                password: xxx,
     *                email: xxx
     *                code: xxx
     *                }
     * @param request 请求对象
     * @return 登录结果
     * 成功则 { errno: 0, errmsg: '成功' }
     * 失败则 { errno: XXX, errmsg: XXX }
     */
    @PostMapping("resetEmail")
    @LogAnno
    public Object resetEmail(@LoginUser Integer userId, @RequestBody String body, HttpServletRequest request) {
        if(userId == null){
            return ResponseUtil.unlogin();
        }
        String password = JacksonUtil.parseString(body, "password");
        String email = JacksonUtil.parseString(body, "email");
        String code = JacksonUtil.parseString(body, "code");

        if (email == null || code == null || password == null) {
            return ResponseUtil.badArgument();
        }

        //判断验证码是否正确
        String cacheCode = CaptchaCodeManager.getCachedCaptcha(email);
        if (cacheCode == null || cacheCode.isEmpty() || !cacheCode.equals(code))
            return ResponseUtil.fail(AUTH_CAPTCHA_UNMATCH, "验证码错误");

        List<LitemallUser> userList = userService.queryByEmail(email);
        LitemallUser user = null;
        if (userList.size() > 1) {
            return ResponseUtil.fail(AUTH_EMAIL_REGISTERED, "邮箱已注册");
        }
        user = userService.findById(userId);

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (!encoder.matches(password, user.getPassword())) {
            return ResponseUtil.fail(AUTH_INVALID_ACCOUNT, "账号密码不对");
        }

        user.setEmail(email);
        if (userService.updateById(user) == 0) {
            return ResponseUtil.updatedDataFailed();
        }

        return ResponseUtil.ok();
    }

    /**
     * 账号信息更新
     *
     * @param body    请求内容
     *                {
     *                password: xxx,
     *                mobile: xxx
     *                code: xxx
     *                }
     *                其中code是手机验证码，目前还不支持手机短信验证码
     * @param request 请求对象
     * @return 登录结果
     * 成功则 { errno: 0, errmsg: '成功' }
     * 失败则 { errno: XXX, errmsg: XXX }
     */
    @PostMapping("profile")
    @LogAnno
    public Object profile(@LoginUser Integer userId, @RequestBody String body, HttpServletRequest request) {
        if(userId == null){
            return ResponseUtil.unlogin();
        }
        String avatar = JacksonUtil.parseString(body, "avatar");
        Byte gender = JacksonUtil.parseByte(body, "gender");
        String nickname = JacksonUtil.parseString(body, "nickname");

        LitemallUser user = userService.findById(userId);
        if(!StringUtils.isEmpty(avatar)){
            user.setAvatar(avatar);
        }
        if(gender != null){
            user.setGender(gender);
        }
        if(!StringUtils.isEmpty(nickname)){
            user.setNickname(nickname);
        }

        if (userService.updateById(user) == 0) {
            return ResponseUtil.updatedDataFailed();
        }

        return ResponseUtil.ok();
    }

    /**
     * 微信手机号码绑定
     *
     * @param userId
     * @param body
     * @return
     */
    @PostMapping("bindPhone")
    @LogAnno
    public Object bindPhone(@LoginUser Integer userId, @RequestBody String body) {
    	if (userId == null) {
            return ResponseUtil.unlogin();
        }
    	LitemallUser user = userService.findById(userId);
        String encryptedData = JacksonUtil.parseString(body, "encryptedData");
        String iv = JacksonUtil.parseString(body, "iv");
        WxMaPhoneNumberInfo phoneNumberInfo = this.wxService.getUserService().getPhoneNoInfo(user.getSessionKey(), encryptedData, iv);
        String phone = phoneNumberInfo.getPhoneNumber();
        user.setMobile(phone);
        if (userService.updateById(user) == 0) {
            return ResponseUtil.updatedDataFailed();
        }
        return ResponseUtil.ok();
    }

    @PostMapping("logout")
    @LogAnno
    public Object logout(@LoginUser Integer userId) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }
        return ResponseUtil.ok();
    }

    @GetMapping("info")
    @LogAnno
    public Object info(@LoginUser Integer userId) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }

        LitemallUser user = userService.findById(userId);
        Map<Object, Object> data = new HashMap<Object, Object>();
        data.put("nickName", user.getNickname());
        data.put("avatar", user.getAvatar());
        data.put("gender", user.getGender());
        data.put("mobile", user.getMobile());
        data.put("email", user.getEmail());
        data.put("userLevel", user.getUserLevel());
        data.put("amount", user.getAvailableAmount());
        data.put("points", user.getPoints());

        return ResponseUtil.ok(data);
    }
}
