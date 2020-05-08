package org.linlinjava.litemall.core.notify.netty;

import com.alibaba.fastjson.JSON;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.core.notify.NotifyService;
import org.linlinjava.litemall.core.util.JacksonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ：stephen
 * @date ：Created in 2020/4/8 13:54
 * @description：TODO
 */
@Service
public class PushServiceImpl implements PushService{
    private static final Log log = LogFactory.getLog(PushServiceImpl.class);
    @Autowired
    private NotifyService notifyService;
    /**
     * TODO 返回值为true表明不能保证发送成功。其实是有问题的，后续改造
     * @param userId
     * @param msg
     * @return
     */
    @Override
    public boolean pushMsgToOne(String userId, Object msg){
        ConcurrentHashMap<String, Channel> userChannelMap = NettyConfig.getUserChannelMap();
        Channel channel = userChannelMap.get(userId);
        if(channel != null){
            String msgStr = JSON.toJSONString(msg);
            log.info("pushMsgToOne msg :" + msgStr);
            channel.writeAndFlush(new TextWebSocketFrame(msgStr));
            notifyService.sendToRegistrationId(userId, "", "", msgStr, "");
            return true;
        }
        return false;
    }
    @Override
    public boolean pushMsgToAll(Object msg){
        String msgStr = JSON.toJSONString(msg);
        log.info("pushMsgToAll msg :" + msgStr);
        NettyConfig.getChannelGroup().writeAndFlush(new TextWebSocketFrame(msgStr));
        notifyService.sendToAll("", "", msgStr, "");
        return true;
    }

    @Override
    public boolean pushMsgToOne(String userId, String msg){
        log.info("pushMsgToOne msg :" + msg);
        ConcurrentHashMap<String, Channel> userChannelMap = NettyConfig.getUserChannelMap();
        Channel channel = userChannelMap.get(userId);
        if(channel != null){
            channel.writeAndFlush(new TextWebSocketFrame(msg));
            notifyService.sendToRegistrationId(userId, "", "", msg, "");
            return true;
        }
        return false;
    }
    @Override
    public boolean pushMsgToAll(String msg){
        log.info("pushMsgToAll msg :" + msg);
        NettyConfig.getChannelGroup().writeAndFlush(new TextWebSocketFrame(msg));
        notifyService.sendToAll("", "", msg, "");
        return true;
    }
}
