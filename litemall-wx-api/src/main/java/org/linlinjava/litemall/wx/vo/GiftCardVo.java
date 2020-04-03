package org.linlinjava.litemall.wx.vo;

import lombok.Data;
import org.linlinjava.litemall.db.domain.LitemallGiftCard;

import java.util.List;

/**
 * @author ：stephen
 * @date ：Created in 2020/4/2 14:05
 * @description：TODO
 */
@Data
public class GiftCardVo {
    private TypeAndName typeAndName;
    private Integer number;
    private List<LitemallGiftCard> cards;
}
