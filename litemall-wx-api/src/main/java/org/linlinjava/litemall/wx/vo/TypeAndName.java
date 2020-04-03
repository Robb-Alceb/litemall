package org.linlinjava.litemall.wx.vo;

import lombok.Data;

/**
 * @author ：stephen
 * @date ：Created in 2020/4/2 14:06
 * @description：TODO
 */
@Data
public class TypeAndName {
    private Integer type;
    private String name;

    public TypeAndName(Integer type, String name) {
        this.type = type;
        this.name = name;
    }
}
