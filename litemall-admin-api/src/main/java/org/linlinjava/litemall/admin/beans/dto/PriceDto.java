package org.linlinjava.litemall.admin.beans.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author ：stephen
 * @date ：Created in 2019/12/12 14:55
 * @description：TODO
 */
@Data
public class PriceDto {
    @NotNull
    private Integer id;
    @NotNull
    private BigDecimal price;
}
