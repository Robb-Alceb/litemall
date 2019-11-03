package org.linlinjava.litemall.admin.beans.vo;

import lombok.Data;

@Data
public class ShopVo {
    private Integer shopId;
    private String name;
    private String address;
    private String shopkeeper;
    private int  members;
    private String addTime;
    private Integer status;
}
