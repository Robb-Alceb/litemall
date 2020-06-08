package org.linlinjava.litemall.web.vo;

import lombok.Data;
import org.linlinjava.litemall.db.domain.LitemallGoodsSpecification;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author ：stephen
 * @date ：Created in 2020/6/6 15:37
 * @description：TODO
 */
@Data
public class CartVo {
    private Integer id;
    private Integer userId;
    private Integer shopId;
    private String shopName;
    private Integer goodsId;
    private String goodsSn;
    private String goodsName;
    private Integer productId;
    private BigDecimal price;
    private BigDecimal goodsPrice;
    private Short number;
    private Integer[] specificationIds;
    private String[] specifications;
    private Boolean checked;
    private String picUrl;
    private LocalDateTime addTime;
    private LocalDateTime updateTime;
    private List<AccessoryVo> accessories;
    private List<LitemallGoodsSpecification> specificationList;
}
