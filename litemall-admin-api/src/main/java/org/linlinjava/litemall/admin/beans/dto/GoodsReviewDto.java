package org.linlinjava.litemall.admin.beans.dto;

import lombok.Data;

/**
 * @author ：liushichang
 * @date ：Created in 11/15/2019 10:43 AM
 * @description：商品审核Dto
 */
@Data
public class GoodsReviewDto {

    private Integer id;
    private String goodsSn;
    private String goodsName;
    private String content;

}
