package org.linlinjava.litemall.wx.web;

import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.service.LitemallShopService;
import org.linlinjava.litemall.wx.annotation.LogAnno;
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
     *
     * @param longitude 经度
     * @param latitude  纬度
     * @param type      类型（1：自取；2：配送）
     * @return
     */
    @GetMapping("all")
    @LogAnno
    public Object all(BigDecimal longitude, BigDecimal latitude, Integer type){
        return shopService.all(longitude, latitude, type);
    }


    @GetMapping("detail")
    @LogAnno
    public Object detail(@NotNull Integer id){
        return ResponseUtil.ok(litemallShopService.findById(id));
    }
}
