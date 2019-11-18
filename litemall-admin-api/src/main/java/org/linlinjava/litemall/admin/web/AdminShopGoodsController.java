package org.linlinjava.litemall.admin.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.linlinjava.litemall.admin.beans.annotation.LoginAdminShopId;
import org.linlinjava.litemall.admin.beans.annotation.RequiresPermissionsDesc;
import org.linlinjava.litemall.admin.service.ShopGoodsService;
import org.linlinjava.litemall.core.validator.Order;
import org.linlinjava.litemall.core.validator.Sort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: robb
 * @Date: 2019/11/18 11:25
 * @Version: 1.0
 * @Description: 门店商品controller
 */

@RestController
@RequestMapping("/admin/shopGoods")
@Validated
public class AdminShopGoodsController {
    private final Log logger = LogFactory.getLog(AdminShopGoodsController.class);

    @Autowired
    private ShopGoodsService shopGoodsService;

    /**
     * 查询门店商品
     *
     * @param goodsSn
     * @param name
     * @param page
     * @param limit
     * @param sort
     * @param order
     * @return
     */
    @RequiresPermissions("admin:shopGoods:list")
    @RequiresPermissionsDesc(menu = {"门店商品管理", "门店商品管理"}, button = "查询")
    @GetMapping("/list")
    public Object list(String goodsSn, String name, @LoginAdminShopId Integer shopId,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @Sort @RequestParam(defaultValue = "add_time") String sort,
                       @Order @RequestParam(defaultValue = "desc") String order) {
        return shopGoodsService.list(goodsSn, name, shopId, page, limit, sort, order);
    }


}
