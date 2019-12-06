package org.linlinjava.litemall.admin.beans.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author ：stephen
 * @date ：Created in 2019/12/6 17:17
 * @description：TODO
 */
@Data
public class GoodsStatusDto {

    @NotNull
    private Integer id;
    private Boolean isNew;
    private Boolean isOnSale;
    private Boolean isHot;
}
