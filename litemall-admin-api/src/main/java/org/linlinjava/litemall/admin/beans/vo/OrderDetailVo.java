package org.linlinjava.litemall.admin.beans.vo;

import lombok.Data;
import org.linlinjava.litemall.db.domain.LitemallOrder;
import org.linlinjava.litemall.db.domain.LitemallOrderGoods;
import org.linlinjava.litemall.db.domain.LitemallUser;
import org.linlinjava.litemall.db.domain.UserVo;

import java.util.List;

/**
 * @author ：stephen
 * @date ：Created in 2019/12/10 11:10
 * @description：TODO
 */

@Data
public class OrderDetailVo {
    private LitemallOrder order;
    private UserVo user;
    private List<LitemallOrderGoods> orderGoods;
}
