package org.linlinjava.litemall.web.bo;

import lombok.Data;
import org.linlinjava.litemall.db.domain.LitemallOrderGoods;
import org.linlinjava.litemall.db.domain.LitemallOrderGoodsAccessory;
import org.linlinjava.litemall.db.domain.LitemallOrderTax;

import java.util.List;

/**
 * @author ：stephen
 * @date ：Created in 2020/6/9 14:15
 * @description：TODO
 */
@Data
public class OrderGoodsBo {
    private LitemallOrderGoods litemallOrderGoods;
    private List<LitemallOrderGoodsAccessory> litemallOrderGoodsAccessories;
    private List<LitemallOrderTax> litemallOrderTaxes;
}
