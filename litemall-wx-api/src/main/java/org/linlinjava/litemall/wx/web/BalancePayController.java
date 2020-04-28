package org.linlinjava.litemall.wx.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.wx.annotation.LogAnno;
import org.linlinjava.litemall.wx.annotation.LoginUser;
import org.linlinjava.litemall.wx.service.WxBalancePayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：stephen
 * @date ：Created in 2020/4/10 13:52
 * @description：余额支付
 */

@RestController
@RequestMapping("/wx/balance")
public class BalancePayController {

    private static final Log log = LogFactory.getLog(CardPayController.class);

    @Autowired
    private WxBalancePayService balancePayService;


    @PostMapping("pay")
    @LogAnno
    public Object pay(@LoginUser Integer userId, @RequestBody String body){
        if(userId == null){
            return ResponseUtil.unlogin();
        }

        return balancePayService.pay(userId, body);
    }
}
