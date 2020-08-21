package org.linlinjava.litemall.admin.beans.vo;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author ：stephen
 * @date ：Created in 2020/8/7 16:59
 * @description：TODO
 */
@Data
public class SubscribeListVo {
    private Integer id;
    private String name;
    private Integer[] methods;
    private String[] timeRanges;
    private Integer[] weeks;
    private Boolean autoSub;
    private List<Map<String, Object>> subscribeGoods;
    private List<Map<String, Object>> subscribeShops;
}
