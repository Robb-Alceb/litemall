package org.linlinjava.litemall.wx.service;

import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.beans.Constants;
import org.linlinjava.litemall.db.domain.LitemallShop;
import org.linlinjava.litemall.db.service.LitemallShopService;
import org.linlinjava.litemall.wx.util.LocationUtils;
import org.linlinjava.litemall.wx.vo.WxShopVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.linlinjava.litemall.wx.util.WxResponseCode.SHOP_CLOSED;
import static org.linlinjava.litemall.wx.util.WxResponseCode.SHOP_UNABLE;

/**
 * @author ：stephen
 * @date ：Created in 2019/12/17 18:37
 * @description：TODO
 */

@Service
public class WxShopService {
    @Autowired
    private LitemallShopService litemallShopService;

    public Object all(BigDecimal longitude, BigDecimal latitude, Integer type){
        List<LitemallShop> all = litemallShopService.all();
        if(null != longitude && null != latitude){
            List<WxShopVo> collect = all.stream().map(o->{
                WxShopVo shopVo = new WxShopVo();
                BeanUtils.copyProperties(o, shopVo);
                shopVo.setOpen(getOpenStatus(o));
                shopVo.setDistance(LocationUtils.getDistance(latitude.doubleValue(), longitude.doubleValue(), o.getLatitude().doubleValue(), o.getLongitude().doubleValue()));
                return shopVo;
            }).sorted((o1, o2) -> {
                return o1.getDistance()
                        .compareTo(o2.getDistance());
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
            List<WxShopVo> collect = all.stream().map(o->{
                WxShopVo shopVo = new WxShopVo();
                BeanUtils.copyProperties(o, shopVo);
                shopVo.setOpen(getOpenStatus(o));
                return shopVo;
            }).filter(c -> {
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

    /**
     * 获取营业状态
     * @param litemallShop
     * @return
     */
    private boolean getOpenStatus(LitemallShop litemallShop){
        if(litemallShop == null || !litemallShop.getStatus().equals(Constants.SHOP_STATUS_OPEN)){
            return false;
        }else{
            String closeTime = litemallShop.getCloseTime();
            String openTime = litemallShop.getOpenTime();
            DateTimeFormatter timeDtf = DateTimeFormatter.ofPattern("HH:mm");
            LocalTime startTimes = LocalTime.parse(openTime, timeDtf);
            LocalTime endTime = LocalTime.parse(closeTime, timeDtf);
            LocalTime now = LocalTime.now();
            Integer dayOfWeek = LocalDateTime.now().getDayOfWeek().getValue();
            //判断星期
            if(litemallShop.getWeeks() == null || !Arrays.asList(litemallShop.getWeeks()).contains(dayOfWeek)){
                return false;
            }
            //判断每天开业时间
            if(now.compareTo(startTimes) != 1 && now.compareTo(endTime) != -1){
                return false;
            }
        }
        return true;
    }
}
