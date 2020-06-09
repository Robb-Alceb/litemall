package org.linlinjava.litemall.wx.util;

public enum WxResponseEnum {

    AUTH_INVALID_ACCOUNT(700,""),
    AUTH_CAPTCHA_UNSUPPORT(701,""),
    AUTH_CAPTCHA_FREQUENCY(702,""),
    AUTH_CAPTCHA_UNMATCH(703,""),
    AUTH_NAME_REGISTERED(704,""),
    AUTH_MOBILE_REGISTERED(705,""),
    AUTH_MOBILE_UNREGISTERED(706,""),
    AUTH_INVALID_MOBILE(707,""),
    AUTH_OPENID_UNACCESS(708,""),
    AUTH_OPENID_BINDED(709,""),
    AUTH_EMAIL_REGISTERED(710,""),
    AUTH_EMAIL_UNREGISTERED(711,""),
    AUTH_INVALID_EMAIL(712,""),
    AUTH_EMAIL_NOT_EXIST(713,""),
    AUTH_MOBILE_NOT_EXIST(714,""),

    GOODS_UNSHELVE(710,""),

    GOODS_NO_STOCK(711,""),
    GOODS_UNKNOWN(712,""),
    GOODS_INVALID(713,""),
    GOODS_PRICE_CHANGE(714,""),
    GOODS_TAX_CHANGE(715,""),
    GOODS_NOT_SALE(716,""),

    ORDER_UNKNOWN(720,""),
    ORDER_INVALID(721,""),
    ORDER_CHECKOUT_FAIL(722,""),
    ORDER_CANCEL_FAIL(723,""),
    ORDER_PAY_FAIL(724,""),
    ORDER_INVALID_OPERATION(725,""),
    ORDER_COMMENTED(726,""),
    ORDER_COMMENT_EXPIRED(727,""),

    GROUPON_EXPIRED(730,""),
    COUPON_EXCEED_LIMIT(740,""),
    COUPON_RECEIVE_FAIL(741,""),
    COUPON_CODE_INVALID(742,""),

    SHOP_UNABLE(750,""),
    SHOP_CLOSED(750,""),
    SHOP_UNSUPPOT(751,""),
    CARD_INVALID(760,""),
    CARD_HAS(761,""),
    GIFT_CARD_SHARE_NOT_EXIST(762,""),
    GIFT_CARD_SHARE_PICKED(763,""),



    BAD_ARGUMENT_ORDERID(401001,"orderId为空"),
    BAD_ARGUMENT_CARDID(401002,"cardId为空"),
    IS_NULL_CARD(701001,"card不存在"),
    NOT_ENOUGH_AMOUNT(701001,"余额不足"),
    NOT_FIND_ORDER(701002,"订单不存在"),
    NOT_FIND_USER(701003,"用户不存在"),
    UNKOWN_LOCATION(701004,"未知的位置"),
    IS_NULL_CARD_SHARE(701005,"分享不存在"),
    ACCESSORY_ENOUGH(701006,"辅料不足"),

    GIFT_CARD_SHARE_NOT_AUTH(764,"");

    private Integer code;
    private String msg;
    WxResponseEnum(Integer code ,String msg){
        this.code = code;
        this.msg = msg;
    }

    public java.lang.Integer getCode() {
        return code;
    }

    public void setCode(java.lang.Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

