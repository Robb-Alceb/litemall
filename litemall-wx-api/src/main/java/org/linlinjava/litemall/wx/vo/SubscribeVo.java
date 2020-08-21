package org.linlinjava.litemall.wx.vo;

import lombok.Data;
import org.linlinjava.litemall.db.domain.LitemallTax;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author ：stephen
 * @date ：Created in 2020/8/8 15:21
 * @description：TODO
 */
@Data
public class SubscribeVo {
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
    private List<LitemallTax> taxes;
    private Integer subscribeId;
    private Integer subscribeGoodsId;
}
