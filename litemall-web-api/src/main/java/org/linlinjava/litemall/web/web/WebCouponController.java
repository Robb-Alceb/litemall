package org.linlinjava.litemall.web.web;

import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.web.annotation.LogAnno;
import org.linlinjava.litemall.web.annotation.LoginShop;
import org.linlinjava.litemall.web.dto.CouponDto;
import org.linlinjava.litemall.web.service.WebCouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author ：stephen
 * @date ：Created in 2020/5/12 13:50
 * @description：优惠券
 */
@RestController
@RequestMapping("/web/coupon")
public class WebCouponController {
    @Autowired
    private WebCouponService webCouponService;

    @PostMapping("/use")
    @LogAnno
    public Object use(@RequestBody @Valid CouponDto dto){
        return ResponseUtil.ok(webCouponService.use(dto));
    }
}
