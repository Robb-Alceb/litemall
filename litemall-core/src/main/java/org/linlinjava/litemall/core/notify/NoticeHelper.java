package org.linlinjava.litemall.core.notify;

import com.alibaba.fastjson.JSON;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.core.notify.jpush.JpushConfig;
import org.linlinjava.litemall.core.notify.netty.PushService;
import org.linlinjava.litemall.db.domain.LitemallNotice;
import org.linlinjava.litemall.db.domain.LitemallNoticeWithBLOBs;
import org.linlinjava.litemall.db.service.LitemallNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ：stephen
 * @date ：Created in 2020/4/9 14:21
 * @description：TODO
 */
@Component
public class NoticeHelper {
    private Log log = LogFactory.getLog(NoticeHelper.class);
    @Autowired
    private LitemallNoticeService litemallMsgService;
    @Autowired
    private PushService pushService;
    @Autowired
    private NotifyService notifyService;
    @Autowired
    private JpushConfig jpushConfig;


    @Async
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void noticeShop(Byte type, String content, Integer shopId) {
        Map<String, Object> map = new HashMap<>();
        map.put("type", type);
        map.put("content", content);
        pushService.pushMsgToShop(map, shopId);
    }

    @Async
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void noticeUser(Byte type, String content, Integer userId, Object extend) {
        noticeUser(type, "", content, null, userId, true, extend);
    }

    @Async
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void noticeUser(Byte type, String title, String content, Integer userId, Object extend) {
        noticeUser(type, title, content, null, userId, true, extend);
    }

    /**
     *  推送消息并保存到数据库，指定用户
     * @param type
     * @param title
     * @param content
     * @param link
     * @param userId
     */
    @Async
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void noticeUser(Byte type, String title, String content, String link, Integer userId, boolean isSave, Object extend) {


        //保存消息
        LitemallNoticeWithBLOBs msg = new LitemallNoticeWithBLOBs();
        msg.setContent(content);
        msg.setUserId(userId);
        msg.setType(type);
        msg.setLink(link);
        msg.setTitle(title);
        if(extend != null){
            msg.setExtend(JSON.toJSONString(extend));
        }

        log.info("noticeUser param is :" + msg.toString());
        //推送消息
        pushService.pushMsgToOne(String.valueOf(userId), msg);
        //极光推送
        notifyService.sendToRegistrationId(jpushConfig.getUserChannelMap().get(String.valueOf(userId)), title, title, content, JSON.toJSONString(msg));

        if(isSave){
            litemallMsgService.create(msg);
        }
    }

    @Async
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void noticeAll(Byte type, String content, String title, Object extend) {
        noticeAll(type, content, title, null, extend);
    }

    /**
     * 推送到所有用户
     * @param type
     * @param content
     * @param title
     * @param link
     */
    @Async
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void noticeAll(Byte type, String content, String title, String link, Object extend) {
        //保存消息
        LitemallNoticeWithBLOBs msg = new LitemallNoticeWithBLOBs();
        msg.setContent(content);
        msg.setType(type);
        msg.setLink(link);
        msg.setTitle(title);
        if(extend != null){
            msg.setExtend(JSON.toJSONString(extend));
        }

        log.info("noticeUser param is :" + msg.toString());
        //推送消息
        pushService.pushMsgToAll( msg);
        //极光推送
        notifyService.sendToAll(title, title, content, JSON.toJSONString(msg));
    }

    /**
     *  推送到所有ios用户
     * @param type
     * @param content
     * @param title
     * @param link
     */
    public void noticeAllIos(Byte type, String content, String title, String link, Object extend) {
        //保存消息
        LitemallNoticeWithBLOBs msg = new LitemallNoticeWithBLOBs();
        msg.setContent(content);
        msg.setType(type);
        msg.setLink(link);
        msg.setTitle(title);
        if(extend != null){
            msg.setExtend(JSON.toJSONString(extend));
        }

        log.info("noticeUser param is :" + msg.toString());
        //推送消息
        pushService.pushMsgToAll( msg);
        //极光推送
        notifyService.sendToAllIos(title, title, content, JSON.toJSONString(msg));
    }

    /**
     *  推送到所有android用户
     * @param type
     * @param content
     * @param title
     * @param link
     */
    public void noticeAllAndroid(Byte type, String content, String title, String link, Object extend) {
        //保存消息
        LitemallNoticeWithBLOBs msg = new LitemallNoticeWithBLOBs();
        msg.setContent(content);
        msg.setType(type);
        msg.setLink(link);
        msg.setTitle(title);
        if(extend != null){
            msg.setExtend(JSON.toJSONString(extend));
        }

        log.info("noticeUser param is :" + msg.toString());
        //推送消息
        pushService.pushMsgToAll( msg);
        //极光推送
        notifyService.sendToAllAndroid(title, title, content, JSON.toJSONString(msg));
    }
}
