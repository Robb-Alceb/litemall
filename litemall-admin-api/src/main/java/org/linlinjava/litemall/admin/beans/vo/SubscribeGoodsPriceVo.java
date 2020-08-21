package org.linlinjava.litemall.admin.beans.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author ：stephen
 * @date ：Created in 2020/8/7 17:00
 * @description：TODO
 */
@Data
public class SubscribeGoodsPriceVo {
    private Integer id;
    private Integer subscribeGoodsId;
    private Byte userLevel;
    private BigDecimal price;
    private Integer number;
}
