package org.linlinjava.litemall.admin.beans.vo;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author ：stephen
 * @date ：Created in 2019/12/3 10:31
 * @description：TODO
 */

@Data
public class MerchandiseVo {
    @NotEmpty
    private Integer id;
    private Integer number;
}
