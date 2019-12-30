package org.linlinjava.litemall.admin.beans.vo;

import lombok.Data;
import org.linlinjava.litemall.db.domain.LitemallGoodsLadderPrice;
import org.linlinjava.litemall.db.domain.LitemallGoodsMaxMinusPrice;
import org.linlinjava.litemall.db.domain.LitemallGoodsSpecification;
import org.linlinjava.litemall.db.domain.LitemallVipGoodsPrice;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author ：stephen
 * @date ：Created in 2019/12/16 14:28
 * @description：TODO
 */
@Data
public class GoodsPriceVo {
    private Integer id;
    private String goodsSn;
    private String goodsName;
    private BigDecimal goodsSellPrice;
    private Byte priceType;
    private List<LitemallGoodsSpecification> specifications;
    private LitemallVipGoodsPrice vipGoodsPrice;
    private List<LitemallGoodsMaxMinusPrice> maxMinusPrices;
    private List<LitemallGoodsLadderPrice> ladderPrices;
}
