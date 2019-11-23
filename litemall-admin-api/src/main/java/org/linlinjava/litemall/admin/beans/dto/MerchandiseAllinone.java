package org.linlinjava.litemall.admin.beans.dto;

import org.linlinjava.litemall.db.domain.LitemallMerchandise;
import org.linlinjava.litemall.db.domain.LitemallShopMerchandise;

public class MerchandiseAllinone {
    LitemallMerchandise litemallMerchandise;
    LitemallShopMerchandise litemallShopMerchandise;

    public LitemallMerchandise getLitemallMerchandise() {
        return litemallMerchandise;
    }

    public void setLitemallMerchandise(LitemallMerchandise litemallMerchandise) {
        this.litemallMerchandise = litemallMerchandise;
    }

    public LitemallShopMerchandise getLitemallShopMerchandise() {
        return litemallShopMerchandise;
    }

    public void setLitemallShopMerchandise(LitemallShopMerchandise litemallShopMerchandise) {
        this.litemallShopMerchandise = litemallShopMerchandise;
    }
}
