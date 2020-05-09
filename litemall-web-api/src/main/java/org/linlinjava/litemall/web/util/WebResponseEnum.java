package org.linlinjava.litemall.web.util;

public enum WebResponseEnum {



    BARCODE_EXPIRE(700001,"二维码已失效"),
    BARCODE_ERROR(700002,"错误的二维码"),
    BAD_ARGUMENT_ORDERID(700003,"订单id为空"),
    BAD_ARGUMENT_CARDID(700005,"礼物卡id为空"),
    IS_NULL_CARD(700006,"礼物卡不存在"),
    NOT_ENOUGH_AMOUNT(700007,"余额不足"),
    SCAN_PAY_ERROR(700008,"扫码支付失败"),

    GIFT_CARD_SHARE_NOT_AUTH(764,"");

    private Integer code;
    private String msg;
    WebResponseEnum(Integer code , String msg){
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

