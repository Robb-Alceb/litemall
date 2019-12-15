package org.linlinjava.litemall.admin.beans.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author ：stephen
 * @date ：Created in 2019/12/14 11:12
 * @description：TODO
 */
@Data
public class GoodsStoreDto {
    @NotNull
    private Integer id;
    @NotNull
    private Integer number;
}
