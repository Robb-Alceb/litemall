package org.linlinjava.litemall.admin.beans.dto;

import lombok.Data;
import org.linlinjava.litemall.db.domain.*;

@Data
public class GoodsAllinone {
    private LitemallGoods goods;
    private LitemallGoodsSpecification[] specifications;
    private LitemallGoodsAttribute[] attributes;
    private LitemallGoodsProduct[] products;
    private LitemallShopGoods shopGoods;
    private LitemallVipGoodsPrice vipPrice;
    private LitemallGoodsLadderPrice[] ladderPrices;
    private LitemallGoodsMaxMinusPrice[] maxMinusPrices;


}
