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


}
