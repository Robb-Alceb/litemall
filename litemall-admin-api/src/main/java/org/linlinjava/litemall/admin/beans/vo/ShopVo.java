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
    private Integer[] types;
    private Integer[] weeks;
    private String openTime;
    private String closeTime;
    private String description;
    private Integer range;
    private String mobile;
    private Integer shopkeeperId;
    private Integer shopManagerId;
    private Integer id;

}
