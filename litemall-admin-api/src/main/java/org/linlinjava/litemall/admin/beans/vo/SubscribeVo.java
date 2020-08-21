package org.linlinjava.litemall.admin.beans.vo;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author ：stephen
 * @date ：Created in 2020/8/7 16:59
 * @description：TODO
 */
@Data
public class SubscribeVo {
    private Integer id;
    private String name;
    private Integer[] methods;
    private String[] timeRanges;
    private Integer[] weeks;
    private Boolean autoSub;
    private LocalDateTime addTime;
    private List<SubscribeGoodsVo> subscribeGoodsDtos;
    private List<SubscribeShopVo> subscribeShopDtos;
}
