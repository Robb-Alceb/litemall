package org.linlinjava.litemall.admin.beans.pojo.convert;

import org.linlinjava.litemall.admin.beans.Constants;
import org.linlinjava.litemall.admin.beans.dto.ShopDto;
import org.linlinjava.litemall.admin.beans.vo.*;
import org.linlinjava.litemall.admin.util.DateUtil;
import org.linlinjava.litemall.db.domain.*;
import org.springframework.beans.BeanUtils;
import sun.security.provider.Sun;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BeanConvert {
    public static ShopVo toShopVo(LitemallShop litemallShop, List<LitemallAdmin> admins, List<LitemallRegion> regions){
        ShopVo vo = new ShopVo();
        vo.setId(litemallShop.getId());
        vo.setShopId(litemallShop.getId());
        vo.setStreetAddress(litemallShop.getStreetAddress());
        vo.setAptUnit(litemallShop.getAptUnit());
        vo.setPostalCode(litemallShop.getPostalCode());
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
        vo.setWeeks(litemallShop.getWeeks());
        vo.setRegions(regions);
        vo.setLatitude(litemallShop.getLatitude());
        vo.setLongitude(litemallShop.getLongitude());
        admins.forEach(admin -> {
            if(Arrays.asList(admin.getRoleIds()).contains(Constants.SHOPKEEPER_ROLE_ID)){
                vo.setShopkeeper(admin.getUsername());
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
        vo.setUserName(user.getUsername());
        return vo;
    }
    public static CollectVo toCollectVo(LitemallCollect litemallCollect, String goodsName, String userName){
        CollectVo vo = new CollectVo();
        vo.setId(litemallCollect.getId());
        vo.setUserId(litemallCollect.getUserId());
        vo.setUserName(userName);
        vo.setValueId(litemallCollect.getGoodsId());
        vo.setValueName(goodsName);
        return vo;
    }

    public static LitemallUser toUser(LitemallAdmin admin){
        LitemallUser user = new LitemallUser();
        user.setEmail(admin.getEmail());
        user.setAvatar(admin.getAvatar());
        user.setMobile(admin.getMobile());
        user.setNickname(admin.getNickName());
        user.setPassword(admin.getPassword());
        user.setUsername(admin.getUsername());
        return user;
    }


    public static SubscribeVo toSubVo(LitemallSubscribe subscribe, List<LitemallSubscribeShop> subscribeShops, List<LitemallSubscribeGoods> subscribeGoodss, List<LitemallSubscribeGoodsPrice> subscribeGoodsPrices){
        SubscribeVo vo = new SubscribeVo();
        BeanUtils.copyProperties(subscribe, vo);
        vo.setSubscribeGoodsDtos(new ArrayList<>());
        vo.setSubscribeShopDtos(new ArrayList<>());
        if(subscribeShops != null) {
            for (LitemallSubscribeShop subscribeShop : subscribeShops) {
                SubscribeShopVo shopVo = new SubscribeShopVo();
                BeanUtils.copyProperties(subscribeShop, shopVo);
                vo.getSubscribeShopDtos().add(shopVo);
            }
        }
        if(subscribeGoodss != null) {
            for (LitemallSubscribeGoods subscribeGoods : subscribeGoodss) {
                SubscribeGoodsVo goodsVo = new SubscribeGoodsVo();
                BeanUtils.copyProperties(subscribeGoods, goodsVo);
                vo.getSubscribeGoodsDtos().add(goodsVo);
                goodsVo.setSubscribeGoodsPriceDtos(new ArrayList<>());
                if (subscribeGoodsPrices != null) {
                    for (LitemallSubscribeGoodsPrice subscribeGoodsPrice : subscribeGoodsPrices) {
                        SubscribeGoodsPriceVo goodsPriceVo = new SubscribeGoodsPriceVo();
                        BeanUtils.copyProperties(subscribeGoodsPrice, goodsPriceVo);
                        goodsVo.getSubscribeGoodsPriceDtos().add(goodsPriceVo);
                    }
                }
            }
        }
        return vo;
    }
}
