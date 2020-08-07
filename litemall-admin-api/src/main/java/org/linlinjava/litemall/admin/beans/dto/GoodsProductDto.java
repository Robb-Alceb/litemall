package org.linlinjava.litemall.admin.beans.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author ：stephen
 * @date ：Created in 2020/8/7 10:54
 * @description：TODO
 */
@Data
public class GoodsProductDto {
    private Integer id;
    private Integer goodsId;
    private Integer shopId;
    private BigDecimal sellPrice;
    private BigDecimal costPrice;
    private Integer[] specificationIds;
    private Integer[] taxTypes;
    private Integer number;
    private Integer earlyWarningValue;
    private String url;
    private String unit;
}
