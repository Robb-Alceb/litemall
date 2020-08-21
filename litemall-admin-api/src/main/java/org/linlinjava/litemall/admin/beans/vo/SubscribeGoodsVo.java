package org.linlinjava.litemall.admin.beans.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author ：stephen
 * @date ：Created in 2020/8/8 10:38
 * @description：TODO
 */
@Data
public class SubscribeGoodsVo {
    private Integer id;
    private Integer subscribeId;
    @NotNull
    private Integer goodsId;
    private BigDecimal basePrice;
    private List<SubscribeGoodsPriceVo> subscribeGoodsPriceDtos;

}