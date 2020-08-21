package org.linlinjava.litemall.admin.beans.bo;

import lombok.Data;

import java.util.List;

/**
 * @author ：stephen
 * @date ：Created in 2020/8/10 11:16
 * @description：TODO
 */
@Data
public class GoodsItemBo {
    private List<SpecificationBo> specificationDtos;
    private List<AccessoryBo> accessoryDtos;
}
