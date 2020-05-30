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
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;

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

    @PostMapping("/report/{registrationId}")
    public Object report(@LoginUser Integer userId,@PathVariable String registrationId){
        if(userId == null){
            return ResponseUtil.unlogin();
        }
        //清空之前绑定的sessionKey
        List<LitemallUser> litemallUsers = litemallUserService.queryBySessionKey(registrationId);
        for(LitemallUser user : litemallUsers){
            if(user.getId() != userId){
                LitemallUser update = new LitemallUser();
                update.setId(userId);
                update.setSessionKey("");
                litemallUserService.updateById(update);
                jpushConfig.getUserChannelMap().remove(String.valueOf(user.getId()));
            }
        }
        //设置sessionKey
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
