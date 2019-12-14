package org.linlinjava.litemall.admin.beans.pojo.convert;

import org.linlinjava.litemall.admin.beans.Constants;
import org.linlinjava.litemall.admin.beans.dto.ShopDto;
import org.linlinjava.litemall.admin.beans.vo.OrderVo;
import org.linlinjava.litemall.admin.util.DateUtil;
import org.linlinjava.litemall.admin.beans.vo.ShopVo;
import org.linlinjava.litemall.db.domain.LitemallAdmin;
import org.linlinjava.litemall.db.domain.LitemallOrder;
import org.linlinjava.litemall.db.domain.LitemallShop;
import org.linlinjava.litemall.db.domain.LitemallUser;

import java.util.Arrays;
import java.util.List;

public class BeanConvert {
    public static ShopVo toShopVo(LitemallShop litemallShop, List<LitemallAdmin> admins){
        ShopVo vo = new ShopVo();
        vo.setId(litemallShop.getId());
        vo.setShopId(litemallShop.getId());
        vo.setAddress(litemallShop.getAddress());
        vo.setMembers(admins.size());
        vo.setName(litemallShop.getName());
        vo.setStatus(Integer.valueOf(litemallShop.getStatus()));
        vo.setOpenTime(litemallShop.getOpenTime());
        vo.setCloseTime(litemallShop.getCloseTime());
        vo.setDescription(litemallShop.getDescription());
        vo.setRange(litemallShop.getRange());
        vo.setTypes(litemallShop.getTypes());
        vo.setMobile(litemallShop.getMobile());
        vo.setAddTime(DateUtil.dateToString(litemallShop.getAddTime()));
        admins.forEach(admin -> {
            if(Arrays.asList(admin.getRoleIds()).contains(Constants.SHOPKEEPER_ROLE_ID)){
                vo.setShopkeeper(admin.getNickName());
                vo.setShopkeeperId(admin.getId());
            }
            if(Arrays.asList(admin.getRoleIds()).contains(Constants.SHOP_MANAGER_ROLE_ID)){
                vo.setShopManagerId(admin.getId());
            }
        });
        return vo;
    }

    public static OrderVo toOrderVo(LitemallOrder order, LitemallUser user){
        OrderVo vo = new OrderVo();
        vo.setId(order.getId());
        vo.setAddTime(order.getAddTime());
        vo.setOrderPrice(order.getOrderPrice());
        vo.setOrderSn(order.getOrderSn());
        vo.setOrderSource(order.getOrderSource());
        vo.setOrderStatus(order.getOrderStatus());
        vo.setPayType(order.getPayType());
        vo.setShopId(order.getShopId());
        vo.setUserId(order.getUserId());
        vo.setUserName(user.getNickname());
        return vo;
    }
}
