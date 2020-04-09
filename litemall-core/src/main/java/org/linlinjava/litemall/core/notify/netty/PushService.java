package org.linlinjava.litemall.core.notify.netty;

public interface PushService {
    /**
     * 推送给指定用户
     * @param userId
     * @param msg
     */
    boolean pushMsgToOne(String userId,String msg);

    /**
     * 推送给所有用户
     * @param msg
     */
    boolean pushMsgToAll(String msg);

    /**
     * 推送给指定用户
     * @param userId
     * @param msg
     */
    boolean pushMsgToOne(String userId,Object msg);

    /**
     * 推送给所有用户
     * @param msg
     */
    boolean pushMsgToAll(Object msg);
}
