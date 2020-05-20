package org.linlinjava.litemall.wx.web;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.notification.IosAlert;
import org.linlinjava.litemall.core.notify.JpushSender;
import org.linlinjava.litemall.core.notify.NotifyService;
import org.linlinjava.litemall.core.notify.jpush.JpushConfig;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.domain.LitemallUser;
import org.linlinjava.litemall.db.service.LitemallUserService;
import org.linlinjava.litemall.wx.annotation.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.HashMap;

/**
 * @author ：stephen
 * @date ：Created in 2020/5/19 15:20
 * @description：TODO
 */
@RestController
@RequestMapping("/wx/jpush")
public class JpushController {

    @Autowired
    private JpushConfig jpushConfig;
    @Autowired
    private LitemallUserService litemallUserService;
    @Autowired
    private NotifyService notifyService;
    @Autowired
    private JpushSender jpushSender;

    @PostMapping("/report")
    public Object report(@LoginUser Integer userId,@NotNull String registrationId){
        if(userId == null){
            return ResponseUtil.unlogin();
        }
        jpushConfig.getUserChannelMap().put(String.valueOf(userId), registrationId);
        LitemallUser user = new LitemallUser();
        user.setId(userId);
        user.setSessionKey(registrationId);
        litemallUserService.updateById(user);
        return ResponseUtil.ok();
    }

    @GetMapping("/push")
    public Object push(String registrationId){

        notifyService.sendToRegistrationId(registrationId, "notification titel", "msg title", "msg content", "hello:world");
        return ResponseUtil.ok();
    }

    @GetMapping("/all")
    public Object all(){
        notifyService.sendToAll("a", "b", "cont", "");
        return ResponseUtil.ok();
    }

    @GetMapping("/ios")
    public Object ios(){
        notifyService.sendToAllIos("a", "b", "cont", "");
        return ResponseUtil.ok();
    }

    @GetMapping("/and")
    public Object and(){
        notifyService.sendToAllAndroid("a", "b", "cont", "");
        return ResponseUtil.ok();
    }
}
