package org.linlinjava.litemall.admin.beans.dto;

import lombok.Data;
import org.linlinjava.litemall.db.domain.LitemallShop;

@Data
public class Shop {
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRange() {
        return range;
    }

    public void setRange(Integer range) {
        this.range = range;
    }

    public Integer[] getTypes() {
        return types;
    }

    public void setTypes(Integer[] types) {
        this.types = types;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getShopkeeper() {
        return shopkeeper;
    }

    public void setShopkeeper(String shopkeeper) {
        this.shopkeeper = shopkeeper;
    }

    public int getMembers() {
        return members;
    }

    public void setMembers(int members) {
        this.members = members;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public String getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(String closeTime) {
        this.closeTime = closeTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LitemallShop getLitemallShop() {
        return litemallShop;
    }

    public void setLitemallShop(LitemallShop litemallShop) {
        this.litemallShop = litemallShop;
    }

    public Integer getShopManagerId() {
        return shopManagerId;
    }

    public void setShopManagerId(Integer shopManagerId) {
        this.shopManagerId = shopManagerId;
    }

    public Integer getShopkeeperId() {
        return shopkeeperId;
    }

    public void setShopkeeperId(Integer shopkeeperId) {
        this.shopkeeperId = shopkeeperId;
    }
}
