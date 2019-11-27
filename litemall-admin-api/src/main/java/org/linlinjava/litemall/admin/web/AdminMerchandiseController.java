package org.linlinjava.litemall.admin.web;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.linlinjava.litemall.admin.beans.annotation.LoginAdminShopId;
import org.linlinjava.litemall.admin.beans.annotation.RequiresPermissionsDesc;
import org.linlinjava.litemall.admin.beans.dto.MerchandiseAllinone;
import org.linlinjava.litemall.admin.service.AdminMerchandiseService;
import org.linlinjava.litemall.core.validator.Order;
import org.linlinjava.litemall.core.validator.Sort;
import org.linlinjava.litemall.db.domain.LitemallMerchandise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 货品库存
 */
@RestController
@RequestMapping("/admin/merchandise")
@Validated
public class AdminMerchandiseController {

    @Autowired
    private AdminMerchandiseService adminMerchandiseService;

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
    @RequiresPermissions("admin:merchandise:list")
    @RequiresPermissionsDesc(menu = {"库存管理", "库存管理"}, button = "查询")
    @GetMapping("/list")
    public Object list(String name, String merchandiseSn, @LoginAdminShopId Integer shopId,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @Sort @RequestParam(defaultValue = "add_time") String sort,
                       @Order @RequestParam(defaultValue = "desc") String order) {
        return adminMerchandiseService.list(name, merchandiseSn, shopId, page, limit, sort, order);
    }

    /**
     * 添加货品
     * @param litemallMerchandise
     * @return
     */
    @RequiresPermissions("admin:merchandise:update")
    @RequiresPermissionsDesc(menu = {"库存管理", "库存管理"}, button = "添加")
    @PostMapping("/create")
    public Object create(@RequestBody LitemallMerchandise litemallMerchandise) {
        return adminMerchandiseService.create(litemallMerchandise);
    }

    /**
     * 修改货品信息
     * @param merchandiseAllinone
     * @return
     */
    @RequiresPermissions("admin:merchandise:update")
    @RequiresPermissionsDesc(menu = {"库存管理", "库存管理"}, button = "修改")
    @PutMapping("/update")
    public Object update(@RequestBody MerchandiseAllinone merchandiseAllinone, @LoginAdminShopId Integer shopId) {
        return adminMerchandiseService.update(merchandiseAllinone, shopId);
    }


    /**
     * 读取货品信息
     * @param id
     * @return
     */
    @RequiresPermissions("admin:merchandise:read")
    @RequiresPermissionsDesc(menu = {"库存管理", "库存管理"}, button = "详情")
    @GetMapping("/read")
    public Object read(Integer id, @LoginAdminShopId Integer shopId) {
        return adminMerchandiseService.read(id, shopId);
    }

    /**
     * 出库入库列表
     * @param merchandiseId
     * @param merchandiseName
     * @param orderSn
     * @param shopId
     * @param page
     * @param limit
     * @param sort
     * @param order
     * @return
     */
    @RequiresPermissions("admin:merchandiseRecord:list")
    @RequiresPermissionsDesc(menu = {"库存管理", "库存管理"}, button = "出库入库查询")
    @GetMapping("/merchandiseRecordList")
    public Object merchandiseRecordList(Integer merchandiseId, String merchandiseName, String orderSn, @LoginAdminShopId Integer shopId,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @Sort @RequestParam(defaultValue = "add_time") String sort,
                       @Order @RequestParam(defaultValue = "desc") String order) {
        return adminMerchandiseService.goodsProductRecordList(merchandiseId, merchandiseName, orderSn, shopId, page, limit, sort, order);
    }
}