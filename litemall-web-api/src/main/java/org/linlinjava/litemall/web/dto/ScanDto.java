package org.linlinjava.litemall.web.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author ：stephen
 * @date ：Created in 2020/5/9 15:39
 * @description：扫码支付dto
 */
@Data
public class ScanDto {
    @NotNull
    private Integer orderId;
    @NotNull
    private String barcode;
}
