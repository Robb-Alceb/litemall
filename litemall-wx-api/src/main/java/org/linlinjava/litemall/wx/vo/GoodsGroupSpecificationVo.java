package org.linlinjava.litemall.wx.vo;

import lombok.Data;

import java.util.List;

/**
 * @author ：stephen
 * @date ：Created in 2020/6/9 17:53
 * @description：TODO
 */
@Data
public class GoodsGroupSpecificationVo {
    private String name;
    private List<GoodsSpecificationVo> valueList;
}
