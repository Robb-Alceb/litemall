package org.linlinjava.litemall.wx.dto;

import lombok.Data;
import org.linlinjava.litemall.db.domain.LitemallCartGoodsAccessory;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author ：stephen
 * @date ：Created in 2020/6/5 18:34
 * @description：TODO
 */
@Data
public class CartDto {
    private Integer id;
    private Integer userId;
    private Integer shopId;
    private String shopName;
    @NotNull
    private Integer goodsId;
    private String goodsSn;
    private String goodsName;
    @NotNull
    private Integer productId;
    private BigDecimal price;
    private BigDecimal taxPrice;
    private Short number;
    @NotNull
    private Integer[] specificationIds;
    private String[] specifications;
    private Boolean checked;
    private String picUrl;
    private List<LitemallCartGoodsAccessory> cartGoodsAccessoryList;
}
