package org.linlinjava.litemall.wx.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author ：stephen
 * @date ：Created in 2020/4/2 17:43
 * @description：TODO
 */
@Data
public class CardShareDto {
    private Integer id;
    @NotNull
    private String cardNumber;
    private String activeTime;
    private String code;
    private String blessing;
}
