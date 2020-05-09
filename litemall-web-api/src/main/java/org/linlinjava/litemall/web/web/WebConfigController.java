package org.linlinjava.litemall.web.web;

import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.service.LitemallSystemConfigService;
import org.linlinjava.litemall.web.annotation.LogAnno;
import org.linlinjava.litemall.web.annotation.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：stephen
 * @date ：Created in 2020/2/24 10:52
 * @description：系统配置读取
 */
@RestController
@RequestMapping("/web/config")
public class WebConfigController {
    @Autowired
    private LitemallSystemConfigService systemConfigService;

    @GetMapping("settlement")
    @LogAnno
    public Object listSettlement(@LoginUser Integer userId){
        if(userId == null){
            return ResponseUtil.unlogin();
        }
        return ResponseUtil.ok(systemConfigService.listSettlement());
    }
}
