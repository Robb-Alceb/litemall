package org.linlinjava.litemall.wx.dto;

import lombok.Data;
import org.linlinjava.litemall.db.domain.LitemallCollectAccessory;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author ：stephen
 * @date ：Created in 2020/6/9 17:14
 * @description：收藏Dto
 */
@Data
public class WxCollectDto {
    @NotNull
    private Integer goodsId;
    @NotNull
    private Integer[] specificationIds;
    private List<LitemallCollectAccessory> accessories;
}
