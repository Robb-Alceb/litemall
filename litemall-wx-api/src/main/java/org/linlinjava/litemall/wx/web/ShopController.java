package org.linlinjava.litemall.wx.web;

import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.service.LitemallShopService;
import org.linlinjava.litemall.wx.service.WxShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author ：stephen
 * @date ：Created in 2019/12/17 18:31
 * @description：门店
 */
@RestController
@RequestMapping("/wx/shop")
@Validated
public class ShopController {
    @Autowired
    private LitemallShopService litemallShopService;
    @Autowired
    private WxShopService shopService;

    /**
     * 所有门店
     * @return
     */
    @GetMapping("all")
    public Object all(BigDecimal longitude, BigDecimal latitude, Integer type){
        return shopService.all(longitude, latitude, type);
    }

    /**
     * 根据经纬度获取最近的门店
     * @param longitude
     * @param latitude
     * @return
     */
    @GetMapping("nearby")
    public Object nearby(@NotNull BigDecimal longitude, @NotNull BigDecimal latitude){
        return ResponseUtil.ok(litemallShopService.all());
    }
}
