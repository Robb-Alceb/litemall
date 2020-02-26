package org.linlinjava.litemall.admin.web;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.linlinjava.litemall.admin.beans.annotation.LogAnno;
import org.linlinjava.litemall.admin.beans.annotation.RequiresPermissionsDesc;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.core.validator.Order;
import org.linlinjava.litemall.core.validator.Sort;
import org.linlinjava.litemall.db.domain.LitemallGiftCardUser;
import org.linlinjava.litemall.db.service.LitemallGiftCardUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author ：stephen
 * @date ：Created in 2020/2/24 16:42
 * @description：礼物卡用户购买情况
 */

@RestController
@RequestMapping("admin/giftcarduser")
public class AdminGiftCardUserController {
    @Autowired
    private LitemallGiftCardUserService litemallGiftCardUserService;

    @RequiresPermissions("admin:giftcarduser:list")
    @RequiresPermissionsDesc(menu = {"活动管理", "购买礼物卡用户"}, button = "查询")
    @GetMapping("/list")
    @LogAnno
    public Object list(Integer giftCardId, String userName,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @Sort @RequestParam(defaultValue = "add_time") String sort,
                       @Order @RequestParam(defaultValue = "desc") String order){
        List<LitemallGiftCardUser> litemallGiftCardUsers = litemallGiftCardUserService.querySelective(giftCardId, userName, page, limit, sort,
                order);
        return ResponseUtil.okList(litemallGiftCardUsers);
    }
}
