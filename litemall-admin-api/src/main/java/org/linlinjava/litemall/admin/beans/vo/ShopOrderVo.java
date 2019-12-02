package org.linlinjava.litemall.admin.beans.vo;

import lombok.Data;
import org.linlinjava.litemall.db.domain.LitemallAdminOrder;
import org.linlinjava.litemall.db.domain.LitemallAdminOrderMerchandise;

import java.util.List;

/**
 * @author ：stephen
 * @date ：Created in 2019/12/2 10:54
 * @description：商品订单详情vo
 */
@Data
public class ShopOrderVo {
    private LitemallAdminOrder order;
    private List<LitemallAdminOrderMerchandise> merchandises;
}
