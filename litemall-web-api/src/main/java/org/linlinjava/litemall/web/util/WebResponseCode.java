package org.linlinjava.litemall.web.util;

public class WebResponseCode {
    public static final Integer AUTH_INVALID_ACCOUNT = 700;
    public static final Integer AUTH_CAPTCHA_UNSUPPORT = 701;
    public static final Integer AUTH_CAPTCHA_FREQUENCY = 702;
    public static final Integer AUTH_CAPTCHA_UNMATCH = 703;
    public static final Integer AUTH_NAME_REGISTERED = 704;
    public static final Integer AUTH_MOBILE_REGISTERED = 705;
    public static final Integer AUTH_MOBILE_UNREGISTERED = 706;
    public static final Integer AUTH_INVALID_MOBILE = 707;
    public static final Integer AUTH_OPENID_UNACCESS = 708;
    public static final Integer AUTH_OPENID_BINDED = 709;
    public static final Integer AUTH_EMAIL_REGISTERED = 710;
    public static final Integer AUTH_EMAIL_UNREGISTERED = 711;
    public static final Integer AUTH_INVALID_EMAIL = 712;
    public static final Integer AUTH_EMAIL_NOT_EXIST = 713;
    public static final Integer AUTH_MOBILE_NOT_EXIST = 714;
    public static final Integer AUTH_NOT_PERMISSION = 715;

    public static final Integer GOODS_UNSHELVE = 710;
    public static final Integer GOODS_NO_STOCK = 711;
    public static final Integer GOODS_UNKNOWN = 712;
    public static final Integer GOODS_INVALID = 713;
    public static final Integer GOODS_PRICE_CHANGE = 714;
    public static final Integer GOODS_TAX_CHANGE = 715;
    //商品已下架
    public static final Integer GOODS_NOT_SALE = 716;

    public static final Integer ORDER_UNKNOWN = 720;
    public static final Integer ORDER_INVALID = 721;
    public static final Integer ORDER_CHECKOUT_FAIL = 722;
    public static final Integer ORDER_CANCEL_FAIL = 723;
    public static final Integer ORDER_PAY_FAIL = 724;
    // 订单当前状态下不支持用户的操作，例如商品未发货状态用户执行确认收货是不可能的。
    public static final Integer ORDER_INVALID_OPERATION = 725;
    public static final Integer ORDER_COMMENTED = 726;
    public static final Integer ORDER_COMMENT_EXPIRED = 727;

    public static final Integer GROUPON_EXPIRED = 730;

    public static final int COUPON_EXCEED_LIMIT = 740;
    public static final int COUPON_RECEIVE_FAIL= 741;
    public static final int COUPON_CODE_INVALID= 742;

    //门店未开业
    public static final int SHOP_UNABLE = 750;
    //门店
    public static final int SHOP_CLOSED = 751;
    //不支持服务（配送，自提）
    public static final int SHOP_UNSUPPOT = 752;
    public static final int SHOP_NOT_EXSIT = 753;

}
