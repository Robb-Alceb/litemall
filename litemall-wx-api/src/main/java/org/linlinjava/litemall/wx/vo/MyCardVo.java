package org.linlinjava.litemall.wx.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author ：stephen
 * @date ：Created in 2020/4/2 18:34
 * @description：TODO
 */
@Data
public class MyCardVo {
    private BigDecimal amount;
    private String cardNumber;
    private String cardName;
    private String picUrl;
}
