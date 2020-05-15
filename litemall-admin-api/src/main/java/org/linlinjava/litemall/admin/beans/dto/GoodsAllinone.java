package org.linlinjava.litemall.admin.beans.dto;

import lombok.Data;
import org.linlinjava.litemall.db.domain.*;

import java.util.List;

@Data
public class GoodsAllinone {
    private List<Integer> shopIds;
    private LitemallGoods goods;
    private LitemallGoodsSpecification[] specifications;
    private LitemallGoodsAttribute[] attributes;
    private LitemallGoodsProduct[] products;
    private LitemallShopGoods shopGoods;
    private LitemallVipGoodsPrice vipPrice;
    private LitemallGoodsLadderPrice[] ladderPrices;
    private LitemallGoodsMaxMinusPrice[] maxMinusPrices;


}
