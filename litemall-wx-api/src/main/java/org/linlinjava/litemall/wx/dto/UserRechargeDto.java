package org.linlinjava.litemall.wx.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author ：stephen
 * @date ：Created in 2020/4/9 17:28
 * @description：TODO
 */
@Data
public class UserRechargeDto {
    @NotNull
    private BigDecimal amount;

}
