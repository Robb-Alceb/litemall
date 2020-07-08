package org.linlinjava.litemall.admin.erp.rs;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author ：stephen
 * @date ：Created in 2020/7/1 11:48
 * @description：TODO
 */
@Data
public class MaterialRS {
    private String unitName;

    private String categoryName;

    private String materialOther;

    private BigDecimal stock;

    private BigDecimal purchaseDecimal;

    private BigDecimal commodityDecimal;

    private BigDecimal wholesaleDecimal;

    private BigDecimal lowDecimal;

    private String mBarCode;

    private String commodityUnit;

    private Long meId;


    private Long id;
    private Long categoryid;
    private String name;
    private String mfrs;
    private BigDecimal packing;
    private BigDecimal safetystock;
    private String model;
    private String standard;
    private String color;
    private String unit;
    private String remark;
    private BigDecimal retailprice;
    private BigDecimal lowprice;
    private BigDecimal presetpriceone;
    private BigDecimal presetpricetwo;
    private Long unitid;
    private String firstoutunit;
    private String firstinunit;
    private String pricestrategy;
    private Boolean enabled;
    private String otherfield1;
    private String otherfield2;
    private String otherfield3;
    private String enableserialnumber;
    private Long tenantId;
    private String deleteFlag;


    /**
     * 门店销售价
     */
    private BigDecimal sellingPrice;
}
