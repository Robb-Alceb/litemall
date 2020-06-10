package org.linlinjava.litemall.wx.vo;

import lombok.Data;

import java.util.List;

/**
 * @author ：stephen
 * @date ：Created in 2020/6/9 19:13
 * @description：TODO
 */
@Data
public class AccessoryGroupVo {
    private String name;
    private List<AccessoryVo> accessoryVos;
}
