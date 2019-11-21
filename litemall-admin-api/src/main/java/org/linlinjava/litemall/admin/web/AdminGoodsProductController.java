package org.linlinjava.litemall.admin.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.linlinjava.litemall.admin.beans.annotation.LoginAdminShopId;
import org.linlinjava.litemall.admin.beans.annotation.RequiresPermissionsDesc;
import org.linlinjava.litemall.admin.service.AdminGoodsProductService;
import org.linlinjava.litemall.core.validator.Order;
import org.linlinjava.litemall.core.validator.Sort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 商品库存
 */
@RestController
@RequestMapping("/admin/goodsProduct")
@Validated
public class AdminGoodsProductController {
    private final Log logger = LogFactory.getLog(AdminGoodsProductController.class);

    @Autowired
    private AdminGoodsProductService adminGoodsProductService;

    /**
     * 查询库存列表
     *
     * @param name
     * @param page
     * @param limit
     * @param sort
     * @param order
     * @return
     */
    @RequiresPermissions("admin:goodsProduct:list")
    @RequiresPermissionsDesc(menu = {"库存管理", "库存管理"}, button = "查询")
    @GetMapping("/list")
    public Object list(String name, @LoginAdminShopId Integer shopId,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @Sort @RequestParam(defaultValue = "add_time") String sort,
                       @Order @RequestParam(defaultValue = "desc") String order) {
        return adminGoodsProductService.list(name, shopId, page, limit, sort, order);
    }

    /**
     * 出库入库列表
     * @param orderSn
     * @param goodsName
     * @param shopId
     * @param page
     * @param limit
     * @param sort
     * @param order
     * @return
     */
    @RequiresPermissions("admin:productRecord:list")
    @RequiresPermissionsDesc(menu = {"库存管理", "库存管理"}, button = "出库入库查询")
    @GetMapping("/goodsProductRecordList")
    public Object goodsProductRecordList(String orderSn, String goodsName, @LoginAdminShopId Integer shopId,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @Sort @RequestParam(defaultValue = "add_time") String sort,
                       @Order @RequestParam(defaultValue = "desc") String order) {
        return adminGoodsProductService.goodsProductRecordList(orderSn, goodsName, shopId, page, limit, sort, order);
    }
}
