package org.linlinjava.litemall.admin.beans.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author ：stephen
 * @date ：Created in 2020/8/8 10:38
 * @description：TODO
 */
@Data
public class SubscribeShopDto {
    private Integer id;
    private Integer subscribeId;
    @NotNull
    private Integer shopId;
}
