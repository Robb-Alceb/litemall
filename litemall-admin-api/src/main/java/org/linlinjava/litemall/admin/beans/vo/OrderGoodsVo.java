package org.linlinjava.litemall.admin.beans.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderGoodsVo {
    //商品id
    private Integer id;
    //类目ID
    private Integer categoryId;
    //类目名称
    private String categoryName;
    //商品id
    private Integer goodsId;
    //商品名称
    private String goodsName;
    //商品编号
    private String goodsSn;
    //商品数量
    private int number;
    //门店id
    private Integer shopId;
    //订单ID
    private Integer orderId;
    //图片
    private String picUrl;
    //税价
    private BigDecimal taxPrice;
}
