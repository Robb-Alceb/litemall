package org.linlinjava.litemall.admin.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.linlinjava.litemall.admin.beans.annotation.LogAnno;
import org.linlinjava.litemall.admin.beans.annotation.LoginAdminShopId;
import org.linlinjava.litemall.admin.beans.annotation.RequiresPermissionsDesc;
import org.linlinjava.litemall.admin.beans.dto.GoodsAllinone;
import org.linlinjava.litemall.admin.beans.dto.GoodsStatusDto;
import org.linlinjava.litemall.admin.beans.dto.GoodsStoreDto;
import org.linlinjava.litemall.admin.beans.dto.PriceDto;
import org.linlinjava.litemall.admin.service.AdminGoodsService;
import org.linlinjava.litemall.core.validator.Order;
import org.linlinjava.litemall.core.validator.Sort;
import org.linlinjava.litemall.db.domain.LitemallGoods;
import org.linlinjava.litemall.db.domain.LitemallVipGoodsPrice;
import org.linlinjava.litemall.db.service.LitemallGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/admin/goods")
@Validated
public class AdminGoodsController {
    private final Log logger = LogFactory.getLog(AdminGoodsController.class);

    @Autowired
    private AdminGoodsService adminGoodsService;
    @Autowired
    private LitemallGoodsService litemallGoodsService;

    /**
     * 查询商品
     *
     * @param goodsSn
     * @param name
     * @param page
     * @param limit
     * @param sort
     * @param order
     * @return
     */
    @RequiresPermissions("admin:goods:list")
    @RequiresPermissionsDesc(menu = {"商品管理", "商品管理"}, button = "查询")
    @GetMapping("/list")
    @LogAnno
    public Object list(String goodsSn, String name, @RequestParam(value = "shopId", required = false) @LoginAdminShopId Integer shopId,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @Sort @RequestParam(defaultValue = "add_time") String sort,
                       @Order @RequestParam(defaultValue = "desc") String order) {
        return adminGoodsService.list(goodsSn, name, shopId, page, limit, sort, order);
    }

    @GetMapping("/catAndBrand")
    @LogAnno
    public Object list2() {
        return adminGoodsService.list2();
    }

    /**
     * 编辑商品
     *
     * @param goodsAllinone
     * @return
     */
    @RequiresPermissions("admin:goods:update")
    @RequiresPermissionsDesc(menu = {"商品管理", "商品管理"}, button = "编辑")
    @PostMapping("/update")
    @LogAnno
    public Object update(@RequestBody GoodsAllinone goodsAllinone, @RequestParam(value = "shopId", required = false) @LoginAdminShopId Integer shopId) {
        return adminGoodsService.update(goodsAllinone, shopId);
    }

    /**
     * 删除商品
     *
     * @param goods
     * @return
     */
    @RequiresPermissions("admin:goods:delete")
    @RequiresPermissionsDesc(menu = {"商品管理", "商品管理"}, button = "删除")
    @PostMapping("/delete")
    @LogAnno
    public Object delete(@RequestBody LitemallGoods goods, @RequestParam(value = "shopId", required = false) @LoginAdminShopId Integer shopId) {
        return adminGoodsService.delete(goods, shopId);
    }

    /**
     * 添加商品
     *
     * @param goodsAllinone
     * @return
     */
    @RequiresPermissions("admin:goods:create")
    @RequiresPermissionsDesc(menu = {"商品管理", "商品管理"}, button = "新增")
    @PostMapping("/create")
    @LogAnno
    public Object create(@RequestBody GoodsAllinone goodsAllinone) {
        return adminGoodsService.create(goodsAllinone);
    }

    /**
     * 商品详情
     *
     * @param id
     * @return
     */
    @RequiresPermissions("admin:goods:read")
    @RequiresPermissionsDesc(menu = {"商品管理", "商品管理"}, button = "详情")
    @GetMapping("/detail")
    @LogAnno
    public Object detail(@NotNull @RequestParam(value = "id") Integer id, @RequestParam(value = "shopId", required = false) @LoginAdminShopId Integer shopId) {
        return adminGoodsService.detail(id, shopId);

    }

    /**
     * 商品日志
     *
     * @return
     */
    @RequiresPermissions("admin:goodsLog:read")
    @RequiresPermissionsDesc(menu = {"商品日志", "商品日志"}, button = "详情")
    @GetMapping("/queryGoodsLogList")
    @LogAnno
    public Object queryGoodsLogList(Integer goodsId, String goodsSn, String userName, String content, Integer page,
                                    Integer limit, String sort, String order){
        return adminGoodsService.queryGoodsLogList(goodsId, goodsSn, userName, content, page, limit, sort, order);
    }

    /**
     * 商品上架/下架
     *
     * @param goodsStatusDto
     * @return
     */
    @RequiresPermissions("admin:goods:push")
    @RequiresPermissionsDesc(menu = {"商品管理", "商品管理"}, button = "上架/下架")
    @PutMapping("/push")
    @LogAnno
    public Object push(@RequestBody GoodsStatusDto goodsStatusDto, @RequestParam(value = "shopId", required = false) @LoginAdminShopId Integer shopId) {

        return adminGoodsService.updateGoodsStatus(goodsStatusDto, shopId);

    }

    /**
     * 商品新品/非新品
     *
     * @param goodsStatusDto
     * @return
     */
    @RequiresPermissions("admin:goods:newProduce")
    @RequiresPermissionsDesc(menu = {"商品管理", "商品管理"}, button = "新品/非新品")
    @PutMapping("/newProduce")
    @LogAnno
    public Object newProduce(@RequestBody GoodsStatusDto goodsStatusDto, @RequestParam(value = "shopId", required = false) @LoginAdminShopId Integer shopId) {
        return adminGoodsService.updateGoodsStatus(goodsStatusDto, shopId);

    }

    /**
     * 商品推荐/非推荐
     *
     * @param goodsStatusDto
     * @return
     */
    @RequiresPermissions("admin:goods:recommend")
    @RequiresPermissionsDesc(menu = {"商品管理", "商品管理"}, button = "推荐/非推荐")
    @PutMapping("/recommend")
    @LogAnno
    public Object recommend(@RequestBody GoodsStatusDto goodsStatusDto, @RequestParam(value = "shopId", required = false) @LoginAdminShopId Integer shopId) {
        return adminGoodsService.updateGoodsStatus(goodsStatusDto, shopId);

    }

    /**
     * 修改商品本身价格
     *
     * @param price
     * @return
     */
    @RequiresPermissions("admin:goods:updatePrice")
    @RequiresPermissionsDesc(menu = {"商品管理", "商品管理"}, button = "修改商品价格")
    @PutMapping("/updatePrice")
    @LogAnno
    public Object updatePrice(@RequestBody PriceDto price, @RequestParam(value = "shopId", required = false) @LoginAdminShopId Integer shopId) {
        return adminGoodsService.updateGoodsPrice(price, shopId);

    }

    /**
     * 修改商品规格的价格
     *
     * @param price
     * @return
     */
    @RequiresPermissions("admin:goods:updateSpecPrice")
    @RequiresPermissionsDesc(menu = {"商品管理", "商品管理"}, button = "修改规格价格")
    @PutMapping("/updateSpecPrice")
    @LogAnno
    public Object updateSpecPrice(@RequestBody PriceDto price, @RequestParam(value = "shopId", required = false) @LoginAdminShopId Integer shopId) {
        return adminGoodsService.updateSpecPrice(price, shopId);

    }

    /**
     * 查询商品的价格详情
     *
     * @param goodsId
     * @return
     */
    @RequiresPermissions("admin:goods:allPrice")
    @RequiresPermissionsDesc(menu = {"商品管理", "商品管理"}, button = "查询价格详情")
    @GetMapping("/allPrice")
    @LogAnno
    public Object allPrice(@NotNull @RequestParam(value = "goodsId") Integer goodsId, @RequestParam(value = "shopId", required = false) @LoginAdminShopId Integer shopId) {
        return adminGoodsService.allPrice(goodsId, shopId);

    }
    /**
     * 修改商品库存
     *
     * @param storeDto
     * @return
     */
    @RequiresPermissions("admin:goods:updateStore")
    @RequiresPermissionsDesc(menu = {"商品管理", "商品管理"}, button = "修改商品库存")
    @PutMapping("/updateStore")
    @LogAnno
    public Object updateStore(@RequestBody GoodsStoreDto storeDto, @RequestParam(value = "shopId", required = false) @LoginAdminShopId Integer shopId) {
        return adminGoodsService.updateStore(storeDto, shopId);

    }

    /**
     * 根据商品ID 查询商品会员价格
     *
     * @param goodsId
     * @return
     */
    @RequiresPermissions("admin:goods:queryVipGoodsPrice")
    @RequiresPermissionsDesc(menu = {"商品管理", "商品管理"}, button = "查询商品会员价格")
    @PutMapping("/queryVipGoodsPrice")
    @LogAnno
    public Object queryVipGoodsPrice(@NotNull @RequestParam(value = "goodsId") Integer goodsId){
        return adminGoodsService.queryVipGoodsPrice(goodsId);
    }

    /**
     * 根据商品ID 修改商品优惠价格（包括会员价格、满减价格、阶梯价格）
     *
     * @param goodsAllinone
     * @return
     */
    @RequiresPermissions("admin:goods:updateDiscountPrice")
    @RequiresPermissionsDesc(menu = {"商品管理", "商品管理"}, button = "修改商品优惠价格（包括会员价格、满减价格、阶梯价格）")
    @PutMapping("/updateDiscountPrice")
    @LogAnno
    public Object updateVipGoodsPrice(@RequestBody GoodsAllinone goodsAllinone){
        return adminGoodsService.updateDiscountPrice(goodsAllinone);
    }
}
