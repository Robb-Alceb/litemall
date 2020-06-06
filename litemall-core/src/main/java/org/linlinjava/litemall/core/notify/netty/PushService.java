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

    /**
     * 推送给指定门店的所有用户
     * @param msg
     */
    boolean pushMsgToShop(String msg, Integer shopId);

    /**
     * 推送给指定门店的所有用户
     * @param msg
     */
    boolean pushMsgToShop(Object msg, Integer shopId);
}
