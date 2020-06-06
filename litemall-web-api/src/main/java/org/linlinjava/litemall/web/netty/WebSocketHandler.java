package org.linlinjava.litemall.web.netty;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.AttributeKey;
import org.linlinjava.litemall.core.notify.netty.NettyConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author ：stephen
 * @date ：Created in 2020/4/8 13:52
 * @description：TODO
 */
@Component
@ChannelHandler.Sharable
public class WebSocketHandler  extends SimpleChannelInboundHandler<TextWebSocketFrame>{
    private static final Logger log = LoggerFactory.getLogger(WebNettyServer.class);

    /**
     * 一旦连接，第一个被执行
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        log.info("handlerAdded 被调用"+ctx.channel().id().asLongText());
        // 添加到channelGroup 通道组
        NettyConfig.getChannelGroup().add(ctx.channel());
        // 回复消息
        ctx.channel().writeAndFlush(new TextWebSocketFrame("OK！"));
    }

    /**
     * 读取数据
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        log.info("服务器收到消息：{}",msg.text());

        // 获取门店ID,关联channel
        JSONObject jsonObject = JSON.parseObject(msg.text());
        String sid = jsonObject.getString("sid");
        if(NettyConfig.getShopChannelMap().get(sid) == null){
            List<Channel> list = new LinkedList<>();
            list.add(ctx.channel());
            NettyConfig.getShopChannelMap().put(sid, list);
        }else{
            NettyConfig.getShopChannelMap().get(sid).add(ctx.channel());
        }

        // 将用户ID作为自定义属性加入到channel中，方便随时channel中获取用户ID
        AttributeKey<String> key = AttributeKey.valueOf("shopId");
        ctx.channel().attr(key).setIfAbsent(sid);

        // 回复消息
        ctx.channel().writeAndFlush(new TextWebSocketFrame("服务器连接成功！"));
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        log.info("handlerRemoved 被调用"+ctx.channel().id().asLongText());
        // 删除通道
        NettyConfig.getChannelGroup().remove(ctx.channel());
        removeUserId(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.info("异常：{}",cause.getMessage());
        // 删除通道
        NettyConfig.getChannelGroup().remove(ctx.channel());
        removeUserId(ctx);
        ctx.close();
    }

    /**
     * 删除用户与channel的对应关系
     * @param ctx
     */
    private void removeUserId(ChannelHandlerContext ctx){
        AttributeKey<String> key = AttributeKey.valueOf("shopId");
        String shopId = ctx.channel().attr(key).get();
        List<Channel> channels = NettyConfig.getShopChannelMap().get(shopId);
        if(channels != null){
            channels.remove(ctx);
        }
    }
}
