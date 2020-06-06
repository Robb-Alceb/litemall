package org.linlinjava.litemall.core.notify.netty;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ：stephen
 * @date ：Created in 2020/4/8 13:50
 * @description：TODO
 */
public class NettyConfig {
    /**
     * 定义一个channel组，管理所有的channel
     * GlobalEventExecutor.INSTANCE 是全局的事件执行器，是一个单例
     */
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    /**
     * 存放用户与Chanel的对应信息，用于给指定用户发送消息
     */
    private static ConcurrentHashMap<String,Channel> userChannelMap = new ConcurrentHashMap<>();

    /**
     * 存放门店与Chanel的对应信息，用于给指定门店发送消息
     */
    private static ConcurrentHashMap<String, List<Channel>> shopChannelMap = new ConcurrentHashMap<>();

    private NettyConfig() {}

    /**
     * 获取channel组
     * @return
     */
    public static ChannelGroup getChannelGroup() {
        return channelGroup;
    }

    /**
     * 获取用户channel map
     * @return
     */
    public static ConcurrentHashMap<String,Channel> getUserChannelMap(){
        return userChannelMap;
    }

    /**
     * 获取门店channel map
     * @return
     */
    public static ConcurrentHashMap<String, List<Channel>> getShopChannelMap() {
        return shopChannelMap;
    }

}
