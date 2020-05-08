package org.linlinjava.litemall.admin.beans.vo;

import lombok.Data;
import org.linlinjava.litemall.db.domain.LitemallRegion;
import org.linlinjava.litemall.db.domain.LitemallShopRegion;

import java.util.List;

@Data
public class ShopVo {
    private Integer shopId;
    private String name;
    private String streetAddress;
    private String aptUnit;
    private String postalCode;
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
    private Double longitude;
    private Double latitude;
    private List<LitemallRegion> regions;

}
