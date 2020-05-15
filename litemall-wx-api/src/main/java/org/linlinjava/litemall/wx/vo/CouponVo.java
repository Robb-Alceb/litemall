package org.linlinjava.litemall.wx.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CouponVo {
    private Integer id;
    private String name;
    private String desc;
    private String tag;
    private BigDecimal min;
    private BigDecimal discount;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Byte discountType;
    private Byte discountRate;
    private Short type;
    private Integer[] userLevel;
    private Integer[] goodsValue;
    private Short goodsType;
    private Boolean promotionOnly;


}
