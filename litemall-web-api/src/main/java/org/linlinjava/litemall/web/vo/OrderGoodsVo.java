package org.linlinjava.litemall.web.vo;

import lombok.Data;
import org.linlinjava.litemall.db.domain.LitemallGoodsSpecification;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author ：stephen
 * @date ：Created in 2020/5/13 15:42
 * @description：TODO
 */
@Data
public class OrderGoodsVo {
    private Integer id;
    private Integer orderId;
    private Integer shopId;
    private Integer goodsId;
    private String goodsName;
    private String goodsSn;
    private Integer categoryId;
    private String categoryName;
    private Integer productId;
    private Short number;
    private BigDecimal taxPrice;
    private BigDecimal price;
    private String[] specifications;
    private String picUrl;
    private Integer comment;
    private LocalDateTime addTime;
    private LocalDateTime updateTime;
    private Boolean deleted;
    private Integer[] specificationIds;
    private List<LitemallGoodsSpecification> specificationList;
}
