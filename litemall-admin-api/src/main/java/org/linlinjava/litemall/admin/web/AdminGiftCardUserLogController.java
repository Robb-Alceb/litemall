package org.linlinjava.litemall.admin.web;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.linlinjava.litemall.admin.beans.annotation.LogAnno;
import org.linlinjava.litemall.admin.beans.annotation.RequiresPermissionsDesc;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.core.validator.Order;
import org.linlinjava.litemall.core.validator.Sort;
import org.linlinjava.litemall.db.service.LitemallGiftCardUserLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：stephen
 * @date ：Created in 2020/2/24 16:37
 * @description：用户充值消费日志
 */
@RestController
@RequestMapping("admin/giftcardlog")
public class AdminGiftCardUserLogController {
    @Autowired
    private LitemallGiftCardUserLogService litemallGiftCardUserLogService;

    @RequiresPermissions("admin:giftcardlog:list")
    @RequiresPermissionsDesc(menu = {"用户管理", "礼物卡日志"}, button = "查询")
    @GetMapping("/list")
    @LogAnno
    public Object list(String userName, Byte type,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @Sort @RequestParam(defaultValue = "add_time") String sort,
                       @Order @RequestParam(defaultValue = "desc") String order){
        return ResponseUtil.okList(litemallGiftCardUserLogService.querySelective(userName, type, page, limit, sort, order));
    }
}
