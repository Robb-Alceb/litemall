package org.linlinjava.litemall.admin.beans.dto;

import lombok.Data;

import java.util.List;

/**
 * @author ：stephen
 * @date ：Created in 2020/8/7 16:59
 * @description：TODO
 */
@Data
public class SubscribeDto {
    private Integer id;
    private String name;
    private Integer[] methods;
    private String[] timeRanges;
    private Integer[] weeks;
    private Boolean autoSub;
    private List<SubscribeGoodsDto> subscribeGoodsDtos;
    private List<SubscribeShopDto> subscribeShopDtos;
}
