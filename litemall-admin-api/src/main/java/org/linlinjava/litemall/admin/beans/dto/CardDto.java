package org.linlinjava.litemall.admin.beans.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;


/**
 * @author ：stephen
 * @date ：Created in 2020/5/9 11:42
 * @description：TODO
 */
@Data
public class CardDto {
    @NotNull
    private Integer id;
    private String cardNumber;
    @NotNull
    private String entityCardCode;
}
