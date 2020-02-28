package org.linlinjava.litemall.admin.beans.vo;

import lombok.Data;

import java.util.List;

@Data
public class RegionVo {
    private Integer id;
    private String nameCn;
    private String nameEn;
    private Byte type;
    private Integer code;

    private List<RegionVo> children;


}
