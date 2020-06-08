package org.linlinjava.litemall.web.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author ：stephen
 * @date ：Created in 2020/6/6 10:30
 * @description：TODO
 */
@Data
public class AccessoryVo {
    private Integer id;
    private Integer goodsId;
    private Integer merchandiseId;
    private String groupName;
    private String name;
    private BigDecimal price;
    private String unit;
    private Integer number;
    private Integer selectNum;
}
