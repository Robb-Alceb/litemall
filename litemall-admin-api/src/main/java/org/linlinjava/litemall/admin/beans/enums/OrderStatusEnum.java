package org.linlinjava.litemall.admin.beans.enums;

public enum OrderStatusEnum {

    P_101(101, "参数为空"),
    P_102(102, "没有订单"),
    P_103(103, "没有订单"),
    P_201(201, "没有订单"),
    P_202(202, "没有订单"),
    P_203(203, "没有订单"),
    P_301(301, "没有订单"),
    P_401(401, "没有订单"),
    P_402(402, "没有订单");

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
