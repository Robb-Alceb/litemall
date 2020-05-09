package org.linlinjava.litemall.admin.web;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.linlinjava.litemall.admin.beans.annotation.LogAnno;
import org.linlinjava.litemall.admin.beans.annotation.RequiresPermissionsDesc;
import org.linlinjava.litemall.admin.beans.dto.CardDto;
import org.linlinjava.litemall.admin.util.AdminResponseEnum;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.core.validator.Order;
import org.linlinjava.litemall.core.validator.Sort;
import org.linlinjava.litemall.db.domain.LitemallGiftCardUser;
import org.linlinjava.litemall.db.service.LitemallGiftCardUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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

    @RequiresPermissions("admin:giftcarduser:bind")
    @RequiresPermissionsDesc(menu = {"活动管理", "修改礼物卡"}, button = "绑定实物卡")
    @PostMapping("/bind")
    @LogAnno
    public Object bind(@Valid @RequestBody CardDto dto){
        LitemallGiftCardUser giftCardUser = new LitemallGiftCardUser();
        giftCardUser.setId(dto.getId());
        giftCardUser.setEntityCardCode(dto.getEntityCardCode());
        litemallGiftCardUserService.updateById(giftCardUser);
        return ResponseUtil.ok();
    }

    @RequiresPermissions("admin:giftcarduser:unbind")
    @RequiresPermissionsDesc(menu = {"活动管理", "修改礼物卡"}, button = "解绑实物卡")
    @PutMapping("/unbind")
    @LogAnno
    public Object unbind(@NotNull Integer id){
        LitemallGiftCardUser giftCardUser = litemallGiftCardUserService.findById(id);
        if(giftCardUser == null){
            return ResponseUtil.fail(AdminResponseEnum.GIFTCARD_NOT_EXIST);
        }
        giftCardUser.setEntityCardCode(null);
        litemallGiftCardUserService.unbind(giftCardUser);
        return ResponseUtil.ok();
    }
}
