package org.linlinjava.litemall.web.web;

import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.service.LitemallShopService;
import org.linlinjava.litemall.web.annotation.LogAnno;
import org.linlinjava.litemall.web.annotation.LoginShop;
import org.linlinjava.litemall.web.service.WebShopService;
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
@RequestMapping("/web/shop")
@Validated
public class WebShopController {
    @Autowired
    private LitemallShopService litemallShopService;
    @Autowired
    private WebShopService shopService;

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
    public Object detail(@LoginShop Integer shopId){
        return ResponseUtil.ok(litemallShopService.findById(shopId));
    }
}
