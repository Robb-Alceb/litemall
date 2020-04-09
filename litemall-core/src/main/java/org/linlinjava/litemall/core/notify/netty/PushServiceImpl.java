package org.linlinjava.litemall.core.notify.netty;

import com.alibaba.fastjson.JSON;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.linlinjava.litemall.core.util.JacksonUtil;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ：stephen
 * @date ：Created in 2020/4/8 13:54
 * @description：TODO
 */
@Service
public class PushServiceImpl implements PushService{
    /**
     *
     * @param userId
     * @param msg
     * @return TODO 返回值为true表明不能保证发送成功。其实是有问题的，后续改造
     */
    @Override
    public boolean pushMsgToOne(String userId, Object msg){
        ConcurrentHashMap<String, Channel> userChannelMap = NettyConfig.getUserChannelMap();
        Channel channel = userChannelMap.get(userId);
        if(channel != null){
            String msgStr = JSON.toJSONString(msg);
            channel.writeAndFlush(new TextWebSocketFrame(msgStr));
            return true;
        }
        return false;
    }
    @Override
    public boolean pushMsgToAll(Object msg){
        String msgStr = JSON.toJSONString(msg);
        NettyConfig.getChannelGroup().writeAndFlush(new TextWebSocketFrame(msgStr));
        return true;
    }

    @Override
    public boolean pushMsgToOne(String userId, String msg){
        ConcurrentHashMap<String, Channel> userChannelMap = NettyConfig.getUserChannelMap();
        Channel channel = userChannelMap.get(userId);
        if(channel != null){
            channel.writeAndFlush(new TextWebSocketFrame(msg));
            return true;
        }
        return false;
    }
    @Override
    public boolean pushMsgToAll(String msg){
        NettyConfig.getChannelGroup().writeAndFlush(new TextWebSocketFrame(msg));
        return true;
    }
}
