package org.linlinjava.litemall.admin.beans.enumerate;

public enum AdminPayStatusEnum {

    P_1(1, "待支付"),
    P_2(2, "已支付")
    ;

    private Integer code;
    private String desc;

    AdminPayStatusEnum(Integer code, String desc){
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
