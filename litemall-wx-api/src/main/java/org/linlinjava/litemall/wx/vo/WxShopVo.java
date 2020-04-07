package org.linlinjava.litemall.wx.vo;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

@Data
public class WxShopVo {
    private Integer id;
    private String name;
    private String streetAddress;
    private String mobile;
    private Double longitude;
    private Double latitude;
    private Short status;
    private Integer range;
    private String openTime;
    private String closeTime;
    private Integer[] types;
    private Integer[] weeks;
    private Integer createUserId;
    private LocalDateTime addTime;
    private LocalDateTime updateTime;
    private String aptUnit;
    private String postalCode;
    private String description;
    /**
     *  和当前位置距离（m）
     */
    private Double distance;
    /**
     *  营业中
     */
    private Boolean open;
}