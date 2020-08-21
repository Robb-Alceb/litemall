package org.linlinjava.litemall.admin.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.linlinjava.litemall.admin.beans.annotation.LogAnno;
import org.linlinjava.litemall.admin.beans.annotation.LoginAdminShopId;
import org.linlinjava.litemall.admin.beans.annotation.RequiresPermissionsDesc;
import org.linlinjava.litemall.admin.beans.dto.SubscribeDto;
import org.linlinjava.litemall.admin.beans.vo.SubscribeVo;
import org.linlinjava.litemall.admin.service.SubscribeService;
import org.linlinjava.litemall.core.storage.StorageService;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.core.validator.Order;
import org.linlinjava.litemall.core.validator.Sort;
import org.linlinjava.litemall.db.domain.LitemallStorage;
import org.linlinjava.litemall.db.service.LitemallStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author ：stephen
 * @date ：Created in 2020/8/8 13:51
 * @description：TODO
 */

@RestController
@RequestMapping("/admin/subscribe")
public class AdminSubscribeController {
    private final Log logger = LogFactory.getLog(AdminSubscribeController.class);

    @Autowired
    private SubscribeService subscribeService;

    @RequiresPermissions("admin:subscribe:list")
    @RequiresPermissionsDesc(menu = {"订阅管理", "订阅管理"}, button = "查询")
    @GetMapping("/list")
    @LogAnno
    public Object list(String name,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @Sort @RequestParam(defaultValue = "add_time") String sort,
                       @Order @RequestParam(defaultValue = "desc") String order) {
        return subscribeService.querySelective(name, page, limit, sort, order);
    }

    @RequiresPermissions("admin:subscribe:create")
    @RequiresPermissionsDesc(menu = {"订阅管理", "订阅管理"}, button = "添加")
    @PostMapping("/create")
    @LogAnno
    public Object create(@RequestBody @Validated SubscribeDto dto) {
        return subscribeService.add(dto);
    }

    @RequiresPermissions("admin:subscribe:delete")
    @RequiresPermissionsDesc(menu = {"订阅管理", "订阅管理"}, button = "删除")
    @DeleteMapping("/delete")
    @LogAnno
    public Object delete(@NotNull Integer id) {
        return subscribeService.delete(id);
    }

    @RequiresPermissions("admin:subscribe:update")
    @RequiresPermissionsDesc(menu = {"订阅管理", "订阅管理"}, button = "修改")
    @PutMapping("/update")
    @LogAnno
    public Object update(@RequestBody @Validated SubscribeDto dto) {
        return subscribeService.update(dto);
    }

    @RequiresPermissions("admin:subscribe:read")
    @RequiresPermissionsDesc(menu = {"订阅管理", "订阅管理"}, button = "详情")
    @GetMapping("/read")
    @LogAnno
    public Object read(@NotNull Integer id) {
        return subscribeService.read(id);
    }

    @RequiresPermissions("admin:subscribe:users")
    @RequiresPermissionsDesc(menu = {"订阅管理", "订阅用户"}, button = "列表查询")
    @GetMapping("/users")
    @LogAnno
    public Object users(
            String name,
            Integer goodsId,
            @LoginAdminShopId Integer shopId,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer limit,
            @Sort @RequestParam(defaultValue = "add_time") String sort,
            @Order @RequestParam(defaultValue = "desc") String order) {
        return subscribeService.subscribeUserList(name, goodsId, shopId, page, limit, sort, order);
    }

    @RequiresPermissions("admin:subscribe:userDetail")
    @RequiresPermissionsDesc(menu = {"订阅管理", "订阅用户"}, button = "详情")
    @GetMapping("/user/detail")
    @LogAnno
    public Object userDetail(@NotNull Integer id) {
        return subscribeService.subscribeUserDetail(id);
    }
}

