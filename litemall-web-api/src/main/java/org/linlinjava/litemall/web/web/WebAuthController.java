package org.linlinjava.litemall.web.web;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.core.notify.NotifyService;
import org.linlinjava.litemall.core.notify.NotifyType;
import org.linlinjava.litemall.core.util.*;
import org.linlinjava.litemall.core.util.bcrypt.BCryptPasswordEncoder;
import org.linlinjava.litemall.db.domain.LitemallAdmin;
import org.linlinjava.litemall.db.domain.LitemallUser;
import org.linlinjava.litemall.db.service.CouponAssignService;
import org.linlinjava.litemall.db.service.LitemallAdminService;
import org.linlinjava.litemall.db.service.LitemallUserService;
import org.linlinjava.litemall.web.annotation.LogAnno;
import org.linlinjava.litemall.web.annotation.LoginUser;
import org.linlinjava.litemall.web.dto.UserInfo;
import org.linlinjava.litemall.web.service.UserTokenManager;
import org.linlinjava.litemall.web.service.WebSettlementLogService;
import org.linlinjava.litemall.web.vo.CalculationOrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.linlinjava.litemall.web.util.WebResponseCode.*;

/**
 * 鉴权服务
 */
@RestController
@RequestMapping("/web/auth")
@Validated
public class WebAuthController {
    private final Log logger = LogFactory.getLog(WebAuthController.class);

    @Autowired
    private LitemallUserService userService;
    @Autowired
    private LitemallAdminService adminService;
    @Autowired
    private WebSettlementLogService settlementLogService;


    /**
     * 账号登录，此账号为普通用户账号和系统用户账号
     * 普通用户账号为系统生成、账号密码应和系统用户一致
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

        //验证普通用户
        List<LitemallUser> userList = userService.queryByUsername(username);
        LitemallUser user = null;
        if (userList.size() > 1) {
            return ResponseUtil.serious();
        } else if (userList.size() == 0) {
            return ResponseUtil.fail(AUTH_INVALID_ACCOUNT, "账号不存在");
        } else {
            user = userList.get(0);
            if(!user.getInnerAccount()){
                return ResponseUtil.fail(AUTH_INVALID_ACCOUNT, "账号不存在");
            }
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (!encoder.matches(password, user.getPassword())) {
            return ResponseUtil.fail(AUTH_INVALID_ACCOUNT, "账号密码不对");
        }

        // 验证是否为门店系统用户
        List<LitemallAdmin> admins = adminService.findAdmin(username);
        LitemallAdmin admin = null;
        if (admins.size() > 1) {
            return ResponseUtil.serious();
        } else if (admins.size() == 0) {
            return ResponseUtil.fail(AUTH_NOT_PERMISSION, "无权限的账号");
        } else {
            admin = admins.get(0);
        }
        BCryptPasswordEncoder encoderAdmin = new BCryptPasswordEncoder();
        if (!encoderAdmin.matches(password, admin.getPassword())) {
            return ResponseUtil.fail(AUTH_INVALID_ACCOUNT, "账号密码不对");
        }

        // 更新登录情况
        user.setLastLoginTime(LocalDateTime.now());
        user.setLastLoginIp(IpUtil.getIpAddr(request));
        if (userService.updateById(user) == 0) {
            return ResponseUtil.updatedDataFailed();
        }

        // userInfo
        UserInfo userInfo = new UserInfo();
        userInfo.setNickName(username);
        userInfo.setAvatarUrl(user.getAvatar());

        // token
        String token = UserTokenManager.generateToken(user.getId(), admin.getShopId());

        Map<Object, Object> result = new HashMap<Object, Object>();
        result.put("token", token);
        result.put("userInfo", userInfo);
        return ResponseUtil.ok(result);
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
        data.put("name", user.getUsername());
        data.put("avatar", user.getAvatar());
        data.put("gender", user.getGender());
        data.put("mobile", user.getMobile());
        data.put("email", user.getEmail());

        return ResponseUtil.ok(data);
    }

    /**
     * ipad用户下班
     * @param userId
     * @return
     */
    @PostMapping("knockoff")
    @LogAnno
    public Object knockOff(@LoginUser Integer userId, @RequestBody List<CalculationOrderVo> calculationOrderVos) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }
        settlementLogService.add(userId, calculationOrderVos);

        return ResponseUtil.ok();
    }
}
