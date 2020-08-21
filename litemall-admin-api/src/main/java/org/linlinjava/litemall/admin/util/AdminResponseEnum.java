package org.linlinjava.litemall.admin.util;

public enum AdminResponseEnum {



    SHOP_NAME_EXIST(100001,"门店名已存在"),
    SHOP_ADDRESS_EXIST(100001,"门店地址已存在"),
    GIFTCARD_NOT_EXIST(100002,"礼物卡不存在"),
    TAX_EXIST(100003,"税费已存在"),
    TAX_NOT_EXIST(100004,"税费不存在"),
    I18N_EXIST(100005,"该语言已存在"),
    APPLY_PURCHASE_ERROR(100006,"申请采购失败"),
    DELETE_SUBSCRIBE_ERROR(100007,"有用户订阅该配置，不允许删除"),

    GIFT_CARD_SHARE_NOT_AUTH(764,"");

    private Integer code;
    private String msg;
    AdminResponseEnum(Integer code , String msg){
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

