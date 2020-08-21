package org.linlinjava.litemall.admin.beans.bo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author ：stephen
 * @date ：Created in 2020/8/10 11:17
 * @description：TODO
 */
@Data
public class SpecificationBo {
    @NotNull
    private Integer id;
    private String name;
    private BigDecimal price;
}
