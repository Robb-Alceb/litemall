package org.linlinjava.litemall.admin.beans.dto;

import org.linlinjava.litemall.db.domain.*;

public class GoodsAllinone {
    LitemallGoods goods;
    LitemallGoodsSpecification[] specifications;
    LitemallGoodsAttribute[] attributes;
    LitemallGoodsProduct[] products;
    LitemallShopGoods shopGoods;

    public LitemallShopGoods getShopGoods() {
        return shopGoods;
    }

    public void setShopGoods(LitemallShopGoods shopGoods) {
        this.shopGoods = shopGoods;
    }

    public LitemallGoods getGoods() {
        return goods;
    }

    public void setGoods(LitemallGoods goods) {
        this.goods = goods;
    }

    public LitemallGoodsProduct[] getProducts() {
        return products;
    }

    public void setProducts(LitemallGoodsProduct[] products) {
        this.products = products;
    }

    public LitemallGoodsSpecification[] getSpecifications() {
        return specifications;
    }

    public void setSpecifications(LitemallGoodsSpecification[] specifications) {
        this.specifications = specifications;
    }

    public LitemallGoodsAttribute[] getAttributes() {
        return attributes;
    }

    public void setAttributes(LitemallGoodsAttribute[] attributes) {
        this.attributes = attributes;
    }

}
