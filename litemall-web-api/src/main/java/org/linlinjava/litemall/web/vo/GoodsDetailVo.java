package org.linlinjava.litemall.web.vo;

import lombok.Data;
import org.linlinjava.litemall.db.domain.LitemallGoodsSpecification;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author ：stephen
 * @date ：Created in 2020/5/14 15:01
 * @description：TODO
 */
@Data
public class GoodsDetailVo {
    private Integer id;
    private String name;
    private Integer number;
    private BigDecimal price;
    private List<LitemallGoodsSpecification> specifications;
}
