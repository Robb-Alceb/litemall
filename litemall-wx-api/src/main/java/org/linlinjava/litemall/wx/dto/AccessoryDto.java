package org.linlinjava.litemall.wx.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author ：stephen
 * @date ：Created in 2020/8/10 11:18
 * @description：TODO
 */
@Data
public class AccessoryDto {
    private Integer id;
    private String name;
    private Integer number;
    private BigDecimal price;
}
