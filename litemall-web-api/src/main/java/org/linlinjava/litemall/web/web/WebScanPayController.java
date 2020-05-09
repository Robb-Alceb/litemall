package org.linlinjava.litemall.web.web;

import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.web.annotation.LogAnno;
import org.linlinjava.litemall.web.annotation.LoginUser;
import org.linlinjava.litemall.web.dto.ScanDto;
import org.linlinjava.litemall.web.service.WebScanPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


/**
 * @author ：stephen
 * @date ：Created in 2020/5/9 10:49
 * @description：扫码支付controller
 */
@RestController
@RequestMapping("/web/scan")
public class WebScanPayController {
    @Autowired
    private WebScanPayService webCodePayService;

    @PostMapping("/pay")
    @LogAnno
    public Object scanPay(@Valid @RequestBody ScanDto dto, @LoginUser Integer userId){
        if(userId == null){
            return ResponseUtil.unlogin();
        }
        return webCodePayService.pay(dto);
    }
}
