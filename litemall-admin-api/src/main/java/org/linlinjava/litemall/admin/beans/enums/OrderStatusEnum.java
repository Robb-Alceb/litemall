package org.linlinjava.litemall.admin.beans.enums;

public enum OrderStatusEnum {

    P_101(101, "下单"),
    P_102(102, "取消"),
    P_103(103, "自动取消"),
    P_201(201, "待发货"),
    P_202(202, "退款"),
    P_203(203, "退款成功"),
    P_301(301, "待收货"),
    P_401(401, "确认收货"),
    P_402(402, "自动确认收货");

    private Integer code;
    private String desc;

    OrderStatusEnum(Integer code, String desc){
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
