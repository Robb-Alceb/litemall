package org.linlinjava.litemall.admin.beans.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ShopGoodsVo {
    //门店订单id
    private Integer id;
    //门店id
    private Integer shopId;
    //商品id
    private Integer goodsId;
    //商品编号
    private String goodsSn;
    //商品名称
    private String name;
    //图片
    private String picUrl;
    //商品价格
    private BigDecimal retailPrice;
    //货号
    private String goodsNum;
    //是否上架
    private Boolean isOnSale;
    //是否新品
    private Boolean isNew;
    //是否推荐
    private Boolean isHot;
    //上架时间
    private LocalDateTime addTime;
    //库存
    private int number;
    //销量
    private int sales;
    //审核状态
    private Byte reviewType;
}
