package org.linlinjava.litemall.web.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author ：stephen
 * @date ：Created in 2020/5/12 13:52
 * @description：TODO
 */
@Data
public class CouponDto {
    @NotNull
    private String barcode;
    @NotNull
    private Integer orderId;
}
