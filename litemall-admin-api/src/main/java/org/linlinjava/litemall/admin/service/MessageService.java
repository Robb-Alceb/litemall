package org.linlinjava.litemall.admin.service;

import io.netty.channel.Channel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.SecurityUtils;
import org.linlinjava.litemall.admin.beans.Constants;
import org.linlinjava.litemall.admin.beans.dto.MessageDto;
import org.linlinjava.litemall.core.notify.netty.NettyConfig;
import org.linlinjava.litemall.core.notify.netty.PushService;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.domain.*;
import org.linlinjava.litemall.db.service.LitemallMessageReceiverService;
import org.linlinjava.litemall.db.service.LitemallMessageService;
import org.linlinjava.litemall.db.service.LitemallMsgService;
import org.linlinjava.litemall.db.service.LitemallUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class MessageService {
    private Log log = LogFactory.getLog(MessageService.class);

    @Autowired
    private LitemallMessageService messageService;
    @Autowired
    private LitemallMessageReceiverService receiverService;
    @Autowired
    private PushService pushService;
    @Autowired
    private LitemallUserService litemallUserService;
    @Autowired
    private LitemallMsgService litemallMsgService;

    public Object queryMessageList(String title, Byte type,
                                   Integer page, Integer limit, String sort, String order) {
        return ResponseUtil.okList(messageService.querySelective(title, type, page, limit, sort, order));
    }

    public Object insert(MessageDto messageDto) {
        if(null == messageDto.getMessage() || StringUtils.isEmpty(messageDto.getMessage().getTitle()) || StringUtils.isEmpty(messageDto.getMessage().getContent())){
            return ResponseUtil.badArgument();
        }
        LitemallMessage message = messageDto.getMessage();
        LitemallAdmin admin = (LitemallAdmin)SecurityUtils.getSubject().getPrincipal();
        message.setAddUserId(admin.getId());
        message.setAddUserName(admin.getUsername());
        messageService.create(message);
        sendMessage(message);
        log.info("**********************");
/*        if(messageDto.getUsers() != null && messageDto.getUsers().size() > 0){
            for (Integer userId:
            messageDto.getUsers()) {
                LitemallMessageReceiver receiver = new LitemallMessageReceiver();
                receiver.setMessageId(messageId);
                receiver.setUserId(userId);
                receiverService.create(receiver);
            }
        }*/
        return ResponseUtil.ok();
    }

    public Object update(LitemallMessage litemallMessage) {
        messageService.updateById(litemallMessage);
        return ResponseUtil.ok();
    }

    public Object delete(Integer messageId) {
        messageService.deleteById(messageId);
        return ResponseUtil.ok();
    }


    @Async
    public void sendMessage(LitemallMessage message){
/*        try{
            Thread.sleep(1000);
        }catch (Exception e){

        }*/
        log.info("sendMessage start param :" + message.toString());
        if(message.getType() == Constants.MESSAGE_TYPE_SYSTEM){
            pushService.pushMsgToAll(message.getContent());
/*          ConcurrentHashMap<String, Channel> channelMap = NettyConfig.getUserChannelMap();
            channelMap.forEach((s, channel) -> {
                LitemallMsg msg = new LitemallMsg();
                msg.setMessageId(message.getId());
                msg.setUserId(Integer.valueOf(s));
                msg.setContent(message.getContent());
                litemallMsgService.create(msg);
            });*/

        }else if(message.getType() == Constants.MESSAGE_TYPE_LEVEL){
            Integer[] receiverLevels = message.getReceiverLevels();
            if(receiverLevels != null){
                List<Byte> levels = new ArrayList<>();
                for(Integer level : receiverLevels){
                    levels.add(level.byteValue());
                }
                List<LitemallUser> users = litemallUserService.queryByUserLevels(levels);
                users.forEach(user -> {
                    pushService.pushMsgToOne(String.valueOf(user.getId()), message.getContent());
                    /*LitemallMsg msg = new LitemallMsg();
                    msg.setMessageId(message.getId());
                    msg.setUserId(Integer.valueOf(user.getId()));
                    msg.setContent(message.getContent());
                    litemallMsgService.create(msg);*/
                });
            }
        }
    }
}
