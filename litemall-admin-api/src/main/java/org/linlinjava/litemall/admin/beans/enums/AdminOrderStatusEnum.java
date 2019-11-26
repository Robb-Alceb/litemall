package org.linlinjava.litemall.admin.beans.enums;

public enum AdminOrderStatusEnum {

    P_1(1, "待处理"),
    P_2(2, "待付款"),
    P_3(3, "待发货"),
    P_4(4, "已发货"),
    P_5(5, "确认收货"),
    P_6(6, "已拒绝")
    ;

    private Integer code;
    private String desc;

    AdminOrderStatusEnum(Integer code, String desc){
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
