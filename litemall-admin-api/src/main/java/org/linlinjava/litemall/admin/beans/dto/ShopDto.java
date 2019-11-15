package org.linlinjava.litemall.admin.beans.dto;

import lombok.Data;
import org.linlinjava.litemall.db.domain.LitemallShop;

@Data
public class ShopDto {
    private LitemallShop litemallShop;
    private Integer shopManagerId;
    private Integer shopkeeperId;
    private Integer shopId;
    private String name;
    private String address;
    private String shopkeeper;
    private int  members;
    private Integer status;
    private String manager;
    private String mobile;
    private String openTime;
    private String closeTime;
    private Integer id;
    //门店介绍
    private String description;
    //服务范围（0为不限制，单位为km）
    private Integer range;
    //订单类型（0：自取1；：配送）
    private Integer[] types;

}
