package org.linlinjava.litemall.web.dto;

import lombok.Data;
import org.linlinjava.litemall.db.domain.LitemallCartGoodsAccessory;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author ：stephen
 * @date ：Created in 2020/5/13 10:11
 * @description：TODO
 */
@Data
public class CartDto {
    private Integer userId;
    private Integer shopId;
    private String shopName;
    private Integer goodsId;
    private String goodsSn;
    private String goodsName;
    private Integer productId;
    private BigDecimal price;
    private BigDecimal taxPrice;
    private Short number;
    private Integer[] specificationIds;
    private String[] specifications;
    private Byte orderType;
    private List<LitemallCartGoodsAccessory> cartGoodsAccessoryList;
}
