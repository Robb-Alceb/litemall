package org.linlinjava.litemall.admin.beans.dto;

import lombok.Data;
import org.linlinjava.litemall.db.domain.LitemallShop;

@Data
public class Shop {
    private LitemallShop litemallShop;
    private Integer shopManagerId;
    private Integer shopkeeperId;

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
