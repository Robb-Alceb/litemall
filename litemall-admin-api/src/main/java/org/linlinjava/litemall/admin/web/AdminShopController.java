package org.linlinjava.litemall.admin.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.linlinjava.litemall.admin.beans.annotation.LogAnno;
import org.linlinjava.litemall.admin.beans.annotation.LoginAdminShopId;
import org.linlinjava.litemall.admin.beans.annotation.RequiresPermissionsDesc;
import org.linlinjava.litemall.admin.beans.dto.ShopDto;
import org.linlinjava.litemall.admin.service.ShopService;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.core.validator.Order;
import org.linlinjava.litemall.core.validator.Sort;
import org.linlinjava.litemall.db.domain.LitemallShop;
import org.linlinjava.litemall.db.service.LitemallShopMerchandiseService;
import org.linlinjava.litemall.db.service.LitemallShopRegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    @Autowired
    private LitemallShopMerchandiseService shopMerchandiseService;
    @Autowired
    private LitemallShopRegionService shopRegionService;

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
    @LogAnno
    public Object list(@LoginAdminShopId Integer shopId, String name, String address, Integer regionId, Integer status, String addTimeFrom, String addTimeTo,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @Sort @RequestParam(defaultValue = "add_time") String sort,
                       @Order @RequestParam(defaultValue = "desc") String order) {
        return shopService.list(shopId, name, address, regionId, status, addTimeFrom, addTimeTo, page, limit, sort, order);
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
    @LogAnno
    public Object update(@RequestBody ShopDto shop, @LoginAdminShopId Integer shopId) {
        if(null != shopId){
            shop.getLitemallShop().setId(shopId);
        }
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
    @LogAnno
    public Object delete(@NotNull @LoginAdminShopId Integer id) {
        return shopService.delete(id);
    }

    /**
     * 添加门店
     *
     * @param shop
     * @return
     */
    @RequiresPermissions("admin:shop:create")
    @RequiresPermissionsDesc(menu = {"门店管理", "门店管理"}, button = "新增")
    @PostMapping("/create")
    @LogAnno
    public Object create(@RequestBody ShopDto shop) {
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
    @LogAnno
    public Object detail(@NotNull @LoginAdminShopId Integer id) {
        return shopService.detail(id);

    }

    /**
     * 所有门店
     *
     * @param id
     * @return
     */
    @RequiresPermissions("admin:shop:allForPerm")
    @RequiresPermissionsDesc(menu = {"门店管理", "门店管理"}, button = "所有有权限门店")
    @GetMapping("/allForPerm")
    @LogAnno
    public Object all(@LoginAdminShopId Integer id) {
        List<LitemallShop> shops = shopService.all(id);
        return ResponseUtil.okList(shops);
    }

    /**
     * 门店货品库存
     *
     * @param id
     * @return
     */
//    @RequiresPermissions("admin:shop:merchandiseNumber")
//    @RequiresPermissionsDesc(menu = {"门店管理", "门店管理"}, button = "门店库存数量")
/*    @GetMapping("/merchandiseNumber")
    @LogAnno
    public Object all(@LoginAdminShopId Integer id, String merchandiseSn) {
        return ResponseUtil.ok(shopMerchandiseService.queryBySn(merchandiseSn, id));
    }*/


    /**
     * 查询门店商品信息 销售情况 订单情况  商品情况
     * @param id
     * @return
     */
    @RequiresPermissions("admin:shop:shopOverview")
    @RequiresPermissionsDesc(menu = {"门店管理", "门店管理"}, button = "门店统计信息")
    @GetMapping("/shopOverview")
    @LogAnno
    public Object queryShopGoodsInfo(@LoginAdminShopId @NotNull Integer id){
        return ResponseUtil.ok(shopService.querShopGoodsInfo(id));
    }

    /**
     * 查询门店位置信息
     * @param id
     * @return
     */
    @GetMapping("/region")
    @LogAnno
    public Object queryShopRegions(@LoginAdminShopId @NotNull Integer id){
        return ResponseUtil.okList(shopRegionService.queryByShopId(id));
    }

    @GetMapping("/ops")
    @LogAnno
    public Object queryShopOps(@LoginAdminShopId Integer id){
        return shopService.queryShopOps(id);
    }
}
