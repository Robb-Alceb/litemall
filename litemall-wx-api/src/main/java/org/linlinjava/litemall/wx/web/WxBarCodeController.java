package org.linlinjava.litemall.wx.web;

import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.wx.annotation.LoginUser;
import org.linlinjava.litemall.wx.service.WxBarcodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：stephen
 * @date ：Created in 2020/5/9 16:16
 * @description：二维码生成器controller
 */

@RestController
@RequestMapping("/wx/barcode")
public class WxBarCodeController {
    @Autowired
    private WxBarcodeService wxBarcodeService;

    @GetMapping("/generate")
    public Object getBarcode(@LoginUser Integer userId, Integer cardId){
        if(userId == null){
            return ResponseUtil.unlogin();
        }

        if(cardId == null){
            return wxBarcodeService.userBarcodeGenerate(userId);
        }else{
            return wxBarcodeService.cardBarcodeGenerate(userId, cardId);
        }
    }
}
