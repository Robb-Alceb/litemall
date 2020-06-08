package org.linlinjava.litemall.web.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author ：stephen
 * @date ：Created in 2020/6/8 17:12
 * @description：TODO
 */
@Data
public class CartTaxVo {
    private Integer cartId;
    private Integer goodsId;
    private Byte type;
    private String name;
    private String code;
    private BigDecimal value;
    private BigDecimal price;
}
