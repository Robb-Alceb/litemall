package org.linlinjava.litemall.admin.beans.enums;

public enum PromptEnum {

    P_101(101, "参数为空"),
    P_102(102, "没有订单");

    private Integer code;
    private String desc;

    PromptEnum(Integer code, String desc){
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
