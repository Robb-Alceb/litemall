package org.linlinjava.litemall.wx.dto;

import lombok.Data;

import java.util.List;

/**
 * @author ：stephen
 * @date ：Created in 2020/8/10 11:16
 * @description：TODO
 */
@Data
public class GoodsItemDto {
    private List<SpecificationDto> specificationDtos;
    private List<AccessoryDto> accessoryDtos;
}
