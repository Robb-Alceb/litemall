package org.linlinjava.litemall.admin.beans.vo;

import lombok.Data;
import org.linlinjava.litemall.db.domain.LitemallGoods;
import org.linlinjava.litemall.db.domain.LitemallShop;
import org.linlinjava.litemall.db.domain.LitemallSubscribeUser;
import org.linlinjava.litemall.db.domain.LitemallUser;

/**
 * @author ：stephen
 * @date ：Created in 2020/8/12 15:17
 * @description：TODO
 */
@Data
public class SubscribeUserListVo {
    private LitemallSubscribeUser subscribeUser;
    private LitemallUser user;
    private LitemallGoods goods;
    private LitemallShop shop;
}
