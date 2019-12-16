package org.linlinjava.litemall.admin.beans.vo;

import lombok.Data;
import org.linlinjava.litemall.db.domain.LitemallGoodsSpecification;

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
    private List<LitemallGoodsSpecification> specifications;
}
