package org.linlinjava.litemall.admin.beans.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author ：stephen
 * @date ：Created in 2020/7/2 16:16
 * @description：TODO
 */
@Data
public class PurchaseItemDto {
    //申请数量
    @NotNull
    private Integer stock;
    //货品id
    @NotNull
    private Integer materialId;
    //货品id
    private String name;
    private String unit;
    private BigDecimal price;


}
