package org.linlinjava.litemall.admin.web;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.linlinjava.litemall.admin.beans.annotation.LogAnno;
import org.linlinjava.litemall.admin.beans.annotation.RequiresPermissionsDesc;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.core.validator.Order;
import org.linlinjava.litemall.core.validator.Sort;
import org.linlinjava.litemall.db.domain.LitemallGiftCard;
import org.linlinjava.litemall.db.service.LitemallGiftCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author ：stephen
 * @date ：Created in 2020/2/24 16:00
 * @description：TODO
 */
@RestController
@RequestMapping("admin/giftcard")
public class AdminGiftCardController {
    @Autowired
    private LitemallGiftCardService litemallGiftCardService;

    @RequiresPermissions("admin:giftcard:list")
    @RequiresPermissionsDesc(menu = {"活动管理", "礼物卡管理"}, button = "查询")
    @GetMapping("/list")
    @LogAnno
    public Object list(String name, Byte type,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @Sort @RequestParam(defaultValue = "add_time") String sort,
                       @Order @RequestParam(defaultValue = "desc") String order) {
        List<LitemallGiftCard> giftCards = litemallGiftCardService.querySelective(name, type, page, limit, sort,
                order);
        return ResponseUtil.okList(giftCards);
    }

    @RequiresPermissions("admin:giftcard:detail")
    @RequiresPermissionsDesc(menu = {"活动管理", "礼物卡管理"}, button = "详情")
    @GetMapping("/detail")
    @LogAnno
    public Object detail(@NotNull Integer id){
        return ResponseUtil.ok(litemallGiftCardService.findById(id));
    }

    @RequiresPermissions("admin:giftcard:create")
    @RequiresPermissionsDesc(menu = {"活动管理", "礼物卡管理"}, button = "添加")
    @PostMapping("/create")
    @LogAnno
    public Object create(@RequestBody LitemallGiftCard card){
        litemallGiftCardService.add(card);
        return ResponseUtil.ok();
    }

    @RequiresPermissions("admin:giftcard:update")
    @RequiresPermissionsDesc(menu = {"活动管理", "礼物卡管理"}, button = "修改")
    @PutMapping("/update")
    @LogAnno
    public Object update(@RequestBody LitemallGiftCard card){
        litemallGiftCardService.update(card);
        return ResponseUtil.ok();
    }

    @RequiresPermissions("admin:giftcard:delete")
    @RequiresPermissionsDesc(menu = {"活动管理", "礼物卡管理"}, button = "删除")
    @DeleteMapping("/delete")
    @LogAnno
    public Object delete(@NotNull Integer id){
        litemallGiftCardService.deleteById(id);
        return ResponseUtil.ok();
    }
}
