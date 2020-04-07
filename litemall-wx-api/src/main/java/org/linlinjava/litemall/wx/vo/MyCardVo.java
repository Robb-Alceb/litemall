package org.linlinjava.litemall.wx.vo;

import lombok.Data;
import org.linlinjava.litemall.db.domain.LitemallGiftCardShare;

import java.math.BigDecimal;

/**
 * @author ：stephen
 * @date ：Created in 2020/4/2 18:34
 * @description：TODO
 */
@Data
public class MyCardVo {
    private Integer id;
    private BigDecimal amount;
    private String cardNumber;
    private String name;
    private String picUrl;
    private LitemallGiftCardShare shareDetail;
}
