package org.linlinjava.litemall.admin.beans.pojo.convert;

import org.linlinjava.litemall.admin.beans.Constants;
import org.linlinjava.litemall.admin.util.DateUtil;
import org.linlinjava.litemall.admin.beans.vo.ShopVo;
import org.linlinjava.litemall.db.domain.LitemallAdmin;
import org.linlinjava.litemall.db.domain.LitemallShop;

import java.util.Arrays;
import java.util.List;

public class BeanConvert {
    public static ShopVo toShopVo(LitemallShop litemallShop, List<LitemallAdmin> admins){
        ShopVo vo = new ShopVo();
        vo.setShopId(litemallShop.getId());
        vo.setAddress(litemallShop.getAddress());
        vo.setAddTime(DateUtil.dateToString(litemallShop.getAddTime()));
        vo.setMembers(admins.size());
        vo.setName(litemallShop.getName());
        vo.setStatus(Integer.valueOf(litemallShop.getStatus()));
        admins.forEach(admin -> {
            if(Arrays.asList(admin.getRoleIds()).contains(Constants.SHOPKEEPER_ROLE_ID)){
                vo.setShopkeeper(admin.getNickName());
            }
        });
        return vo;
    }
}
