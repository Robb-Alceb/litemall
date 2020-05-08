package org.linlinjava.litemall.admin.util;

public enum AdminResponseEnum {



    SHOP_NAME_EXIST(100001,"门店名已存在"),
    SHOP_ADDRESS_EXIST(100001,"门店地址已存在"),


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

