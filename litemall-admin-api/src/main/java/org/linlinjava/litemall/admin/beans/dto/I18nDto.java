package org.linlinjava.litemall.admin.beans.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author ：stephen
 * @date ：Created in 2020/5/20 18:34
 * @description：TODO
 */
@Data
public class I18nDto {
    private Integer id;
    private String key;
    @NotNull
    private String value;
    private String type;
}
