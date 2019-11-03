package org.linlinjava.litemall.admin.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.annotations.Delete;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.linlinjava.litemall.admin.beans.annotation.RequiresPermissionsDesc;
import org.linlinjava.litemall.admin.beans.dto.Shop;
import org.linlinjava.litemall.admin.service.ShopService;
import org.linlinjava.litemall.core.validator.Order;
import org.linlinjava.litemall.core.validator.Sort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

/**
 * @Author: robb
 * @Date: 2019/11/2 11:25
 * @Version: 1.0
 * @Description: 门店管理controller
 */

@RestController
@RequestMapping("/admin/shop")
@Validated
public class AdminShopController {
    private final Log logger = LogFactory.getLog(AdminGoodsController.class);

    @Autowired
    private ShopService shopService;

    /**
     * 查询门店
     *
     * @param address
     * @param name
     * @param page
     * @param limit
     * @param sort
     * @param order
     * @return
     */
    @RequiresPermissions("admin:shop:list")
    @RequiresPermissionsDesc(menu = {"门店管理", "门店管理"}, button = "查询")
    @GetMapping("/list")
    public Object list(String name, String address, Integer status, String addTimeFrom, String addTimeTo,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @Sort @RequestParam(defaultValue = "add_time") String sort,
                       @Order @RequestParam(defaultValue = "desc") String order) {
        return shopService.list(name, address, status, addTimeFrom, addTimeTo, page, limit, sort, order);
    }


    /**
     * 编辑门店
     *
     * @param shop
     * @return
     */
    @RequiresPermissions("admin:shop:update")
    @RequiresPermissionsDesc(menu = {"门店管理", "门店管理"}, button = "编辑")
    @PutMapping("/update")
    public Object update(@RequestBody Shop shop) {
        return shopService.update(shop);
    }

    /**
     * 删除门店
     *
     * @param id
     * @return
     */
    @RequiresPermissions("admin:shop:delete")
    @RequiresPermissionsDesc(menu = {"门店管理", "门店管理"}, button = "删除")
    @DeleteMapping("/delete")
    public Object delete(@NotNull Integer id) {
        return shopService.delete(id);
    }

    /**
     * 添加门店
     *
     * @param shop
     * @return
     */
    @RequiresPermissions("admin:goods:create")
    @RequiresPermissionsDesc(menu = {"门店管理", "门店管理"}, button = "新增")
    @PostMapping("/create")
    public Object create(@RequestBody Shop shop) {
        return shopService.create(shop);
    }

    /**
     * 门店详情
     *
     * @param id
     * @return
     */
    @RequiresPermissions("admin:shop:read")
    @RequiresPermissionsDesc(menu = {"门店管理", "门店管理"}, button = "详情")
    @GetMapping("/detail")
    public Object detail(@NotNull Integer id) {
        return shopService.detail(id);

    }
}
