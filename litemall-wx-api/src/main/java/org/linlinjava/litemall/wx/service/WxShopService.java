package org.linlinjava.litemall.wx.service;

import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.domain.LitemallShop;
import org.linlinjava.litemall.db.service.LitemallShopService;
import org.linlinjava.litemall.wx.util.LocationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ：stephen
 * @date ：Created in 2019/12/17 18:37
 * @description：TODO
 */

@Service
public class WxShopService {
    @Autowired
    private LitemallShopService litemallShopService;

    public Object all(BigDecimal longitude, BigDecimal latitude, Integer type) {
        List<LitemallShop> all = litemallShopService.all();
        if(null != longitude && null != latitude){
            List<LitemallShop> collect = all.stream().sorted((o1, o2) -> {
                return LocationUtils.getDistance(latitude.doubleValue(), longitude.doubleValue(), o1.getLatitude().doubleValue(), o1.getLongitude().doubleValue())
                        .compareTo(LocationUtils.getDistance(latitude.doubleValue(), longitude.doubleValue(), o1.getLatitude().doubleValue(), o1.getLongitude().doubleValue()));
            }).filter(c->{
                if(null == type){
                    return true;
                }
                if(Arrays.asList(c.getTypes()).indexOf(type) >= 0){
                    return true;
                }
                return false;
            }).collect(Collectors.toList());

            return ResponseUtil.ok(collect);
        }else{
            List<LitemallShop> collect = all.stream().filter(c -> {
                if(null == type){
                    return true;
                }
                if (Arrays.asList(c.getTypes()).indexOf(type) >= 0) {
                    return true;
                }
                return false;
            }).collect(Collectors.toList());
            return ResponseUtil.ok(collect);
        }

    }
}
