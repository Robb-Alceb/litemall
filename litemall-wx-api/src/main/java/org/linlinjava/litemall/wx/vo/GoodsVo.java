package org.linlinjava.litemall.wx.vo;


import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author: stephen
 * @Date: 2019/12/21 21:57
 * @Version: 1.0
 * @Description: TODO
 */

@Data
public class GoodsVo {
    private Integer id;
    private String name;
    private String brief;
    private Boolean isNew;
    private Boolean isHot;
    private BigDecimal retailPrice;
    private BigDecimal tax;
    private String picUri;
    private Integer categoryId;
    private String categoryName;
    private Boolean userHasCollect;
}
