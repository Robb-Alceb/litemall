package org.linlinjava.litemall.wx.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author ：stephen
 * @date ：Created in 2020/6/9 17:57
 * @description：TODO
 */
@Data
public class GoodsSpecificationVo {
    private Integer id;
    private Integer goodsId;
    private String specification;
    private String value;
    private BigDecimal price;
    private String picUrl;
    private Byte type;
    private LocalDateTime addTime;
    private LocalDateTime updateTime;
    private Boolean selected = false;
}
