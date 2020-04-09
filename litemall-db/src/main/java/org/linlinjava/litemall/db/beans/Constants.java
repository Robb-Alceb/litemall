package org.linlinjava.litemall.db.beans;

public class Constants {

    /**
     * 门店店长角色id
     */
    public static final Integer SHOPKEEPER_ROLE_ID = 4;
    /**
     * 门店副店长角色id
     */
    public static final Integer SHOPKEEPER_ASSISTANT_ROLE_ID = 5;

    /**
     * 门店经理角色id
     */
    public static final Integer SHOP_MANAGER_ROLE_ID = 6;

    /**
     * 门店副经理角色id
     */
    public static final Integer SHOP_MANAGER_ASSISTANT_ROLE_ID = 7;

    /**
     * 店员角色id
     */
    public static final Integer SHOP_ASSISTANT_ROLE_ID = 9;

    /**
     * 角色类型=true，角色为门店角色
     */
    public static final Boolean SHOP_ROLE_TYPE = true;

    /**
     * 商品审核状态：（1：待审核；2：已审核；3：未通过）
     */
    public static final Integer GOODS_REVIEW_PENDING = 1;
    public static final Integer GOODS_REVIEW_APPROVE = 2;
    public static final Integer GOODS_REVIEW_REJECT = 3;
    /**
     * 商品日志类型：（1：审核日志；2:普通日志）
     */
    public static final Integer GOODS_LOG_REVIEW = 1;
    public static final Integer GOODS_LOG_COMMON = 2;

    /**
     * 支付方式
     */
    public static final Byte PAY_TYPE_PAYPAL = 2;    //PayPal支付
    public static final Byte PAY_TYPE_GIFT_CARD = 3;    //礼物卡支付


    /**
     * 用户反馈状态
     */
    public static final Integer FEEDBACK_STATUS_REPLY = 1;
    public static final Integer FEEDBACK_STATUS_IGNORE = 2;

    /**
     * 广告类型
     */
    public static final Boolean AD_TYPE_COMMON = false;     //  通用
    public static final Boolean AD_TYPE_SHOP= true;         //  指定门店


    /**
     * 商品分类级别
     */
    public static final String CATEGORY_LEVEL_L1 = "L1";     //  分类级别
    public static final String CATEGORY_LEVEL_L2 = "L2";     //
    public static final String CATEGORY_LEVEL_L3 = "L3";     //

    /**
     * 商品优惠价格类型
     */
    public static final Byte GOODS_PRICE_TYPE_VIP = 1;
    public static final Byte GOODS_PRICE_TYPE_LADDER = 2;
    public static final Byte GOODS_PRICE_TYPE_MAXMINU = 3;

    /**
     * 会员等级（0：默认；1：白银；2：黄金；3：铂金；4：钻石）
     */
    public static final Byte USER_LEVEL_DEFAULT = 0;
    public static final Byte USER_LEVEL_SILVER = 1;
    public static final Byte USER_LEVEL_GOLD = 2;
    public static final Byte USER_LEVEL_PLATINUNM = 3;
    public static final Byte USER_LEVEL_DIAMOND = 4;

    /**
     * 优惠券类型（0：全商品；2：指定商品）
     */
    public static final Short COUPON_TYPE_COMMON = 0;
    public static final Short COUPON_TYPE_GOODS = 2;

    /**
     * 门店状态（1:营业；2：歇业；3：装修）
     */
    public static final Short SHOP_STATUS_OPEN = 1;
    public static final Short SHOP_STATUS_CLOSE = 2;
    public static final Short SHOP_STATUS_UNABLE = 2;

    /**
     * 用户充值/用户消费
     */
    public static final Byte USER_SAVING = 1;
    public static final Byte USER_CONSUME = 2;

    /**
     * 订单类型(1:自提；2：外送)
     */
    public static final Byte ORDER_AET = 1;
    public static final Byte ORDER_SEND = 2;

    /**
     *  订单支付方式
     */
    public static final Byte ORDER_TYPE_CASH = 11;  //现金

    /**
     *  地区类型
     */
    public static final Byte REGION_TYPE_COUNTRY = 1;  //国家
    public static final Byte REGION_TYPE_PROVINCE = 2;  //省份

    /**
     *  礼物卡类型
     */
    public static final Byte GIFTCARD_TYPE_COMMON = 1;  //不限
    public static final Byte GIFTCARD_TYPE_CATEGORY = 2;  //指定类目


    /**
     *  支付状态
     */
    public static final Byte PAY_STATUS_DOING= 1;  //进行中
    public static final Byte PAY_STATUS_DONE = 2;  //已完成
    public static final Byte PAY_STATUS_REFUSE = 3;  //已退款
    public static final Byte PAY_STATUS_AUTO_CANCEL = 4;  //自动取消


    /**
     *  礼物卡日志操作类型
     */
    public static final Byte LOG_GIFTCARD_RECHARGE= 1;  //充值
    public static final Byte LOG_GIFTCARD_CONSUME = 2;  //消费
    public static final Byte LOG_GIFTCARD_SHARE = 3;  //赠送
    public static final Byte LOG_GIFTCARD_PICK = 4;  //领取
    public static final Byte LOG_GIFTCARD_DESTORY = 5;  //销毁


    /**
     * 消息类型
     */
    public static final Byte MESSAGE_TYPE_SYSTEM = 1;  //系统消息
    public static final Byte MESSAGE_TYPE_LEVEL = 2;  //站内消息


    /**
     * 通知消息类型
     */
    public static final Byte MSG_TYPE_SYSTEM = 1;  //系统消息
    public static final Byte MSG_TYPE_ORDER = 2;  //订单消息
    public static final Byte MSG_TYPE_PROMOTION = 3;  //推广消息
    public static final Byte MSG_TYPE_OTHER = 4;  //其他消息
}
