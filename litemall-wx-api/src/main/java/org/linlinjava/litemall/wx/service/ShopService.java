package org.linlinjava.litemall.wx.service;

import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.domain.LitemallShop;
import org.linlinjava.litemall.db.service.LitemallShopService;
import org.linlinjava.litemall.wx.util.LocationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ：stephen
 * @date ：Created in 2019/12/17 18:37
 * @description：TODO
 */

@Service
public class ShopService {
    @Autowired
    private LitemallShopService litemallShopService;

    public Object all(BigDecimal longitude, BigDecimal latitude) {
        List<LitemallShop> all = litemallShopService.all();
        if(null != longitude && null != latitude){
            List<LitemallShop> collect = all.stream().sorted((o1, o2) -> {
                return LocationUtils.getDistance(latitude.doubleValue(), longitude.doubleValue(), o1.getLatitude().doubleValue(), o1.getLongitude().doubleValue())
                        .compareTo(LocationUtils.getDistance(latitude.doubleValue(), longitude.doubleValue(), o1.getLatitude().doubleValue(), o1.getLongitude().doubleValue()));
            }).collect(Collectors.toList());
            return ResponseUtil.ok(collect);
        }else{
            return ResponseUtil.ok(all);
        }

    }
}
