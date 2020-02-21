package org.linlinjava.litemall.web.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author ：stephen
 * @date ：Created in 2020/2/20 16:19
 * @description：结算金额Vo
 */
@Data
public class CalculationOrderVo {
    private BigDecimal amount;
    private BigDecimal number;
}
