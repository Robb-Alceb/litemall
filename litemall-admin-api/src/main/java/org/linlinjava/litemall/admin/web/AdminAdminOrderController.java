package org.linlinjava.litemall.admin.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.linlinjava.litemall.admin.beans.annotation.LogAnno;
import org.linlinjava.litemall.admin.beans.annotation.LoginAdminShopId;
import org.linlinjava.litemall.admin.beans.annotation.RequiresPermissionsDesc;
import org.linlinjava.litemall.admin.beans.vo.AdminOrderVo;
import org.linlinjava.litemall.admin.service.AdminAdminOrderService;
import org.linlinjava.litemall.core.validator.Order;
import org.linlinjava.litemall.core.validator.Sort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/admin/shopOrder")
@Validated
public class AdminAdminOrderController {
    private final Log logger = LogFactory.getLog(AdminAdminOrderController.class);

    @Autowired
    private AdminAdminOrderService adminAdminOrderService;

    /**
     * 调货申请列表
     *
     * @param orderSn
     * @param userName
     * @param address
     * @param shopId
     * @param page
     * @param limit
     * @param sort
     * @param order
     * @return
     */
    @RequiresPermissions("admin:order:shopList")
    @RequiresPermissionsDesc(menu = {"门店管理", "门店订单"}, button = "查询")
    @GetMapping("/list")
    @LogAnno
    public Object list(String orderSn, String userName, String address,
                       Integer orderStatus,
                       @LoginAdminShopId Integer shopId,
                       String startDate,
                       String endDate,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @Sort @RequestParam(defaultValue = "add_time") String sort,
                       @Order @RequestParam(defaultValue = "desc") String order) {
        return adminAdminOrderService.list(orderStatus, startDate, endDate, orderSn, userName, address, shopId, page, limit, sort, order);
    }

    /**
     * 订单申请
     * @param adminOrderVo
     * @return
     */
    @RequiresPermissions("admin:order:orderApplying")
    @RequiresPermissionsDesc(menu = {"门店管理", "门店订单"}, button = "订单申请")
    @PostMapping("/orderApplying")
    @LogAnno
    public Object orderApplying(@RequestBody AdminOrderVo adminOrderVo, @LoginAdminShopId Integer shopId){
        adminOrderVo.setShopId(shopId);
        return adminAdminOrderService.orderApplying(adminOrderVo);
    }

    /**
     * 同意调货
     * @param adminOrderVo
     * @return
     */
    @RequiresPermissions("admin:order:orderPass")
    @RequiresPermissionsDesc(menu = {"门店管理", "门店订单"}, button = "同意调货")
    @PostMapping("/orderPass")
    @LogAnno
    public Object orderPass(@RequestBody AdminOrderVo adminOrderVo){
        return adminAdminOrderService.orderPass(adminOrderVo);
    }

    /**
     * 不同意调货
     * @param adminOrderVo
     * @return
     */
    @RequiresPermissions("admin:order:orderNoPass")
    @RequiresPermissionsDesc(menu = {"门店管理", "门店订单"}, button = "拒绝调货")
    @PostMapping("/orderNoPass")
    public Object orderNoPass (@RequestBody AdminOrderVo adminOrderVo){
        return adminAdminOrderService.orderNoPass(adminOrderVo);
    }

    /**
     * 支付货款
     * @param adminOrderVo
     * @return
     */
    @RequiresPermissions("admin:order:orderPay")
    @RequiresPermissionsDesc(menu = {"门店管理", "门店订单"}, button = "支付货款")
    @PostMapping("/orderPay")
    @LogAnno
    public Object orderPay(@RequestBody AdminOrderVo adminOrderVo, @LoginAdminShopId Integer shopId){
        adminOrderVo.setShopId(shopId);
        return adminAdminOrderService.orderPay(adminOrderVo);
    }

    /**
     * 同意发货
     * @param adminOrderVo
     * @return
     */
    @RequiresPermissions("admin:order:deliverGoods")
    @RequiresPermissionsDesc(menu = {"门店管理", "门店订单"}, button = "同意发货")
    @PostMapping("/deliverGoods")
    @LogAnno
    public Object deliverGoods(@RequestBody AdminOrderVo adminOrderVo){
        return adminAdminOrderService.deliverGoods(adminOrderVo);
    }

    /**
     * 拒绝发货
     * @param adminOrderVo
     * @return
     */
    @RequiresPermissions("admin:order:cancelDeliverGoods")
    @RequiresPermissionsDesc(menu = {"门店管理", "门店订单"}, button = "拒绝发货")
    @PostMapping("/cancelDeliverGoods")
    @LogAnno
    public Object cancelDeliverGoods(@RequestBody AdminOrderVo adminOrderVo){
        return adminAdminOrderService.cancelDeliverGoods(adminOrderVo);
    }


    /**
     * 确认收货
     * @param adminOrderVo
     * @return
     */
    @RequiresPermissions("admin:order:takeDelivery")
    @RequiresPermissionsDesc(menu = {"门店管理", "门店订单"}, button = "确认收货")
    @PostMapping("/takeDelivery")
    @LogAnno
    public Object takeDelivery(@RequestBody AdminOrderVo adminOrderVo, @LoginAdminShopId Integer shopId){
        adminOrderVo.setShopId(shopId);
        return adminAdminOrderService.takeDelivery(adminOrderVo);
    }

    /**
     * 门店订单详情
     * @param id
     * @return
     */
    @RequiresPermissions("admin:order:takeDelivery")
    @RequiresPermissionsDesc(menu = {"门店管理", "门店订单"}, button = "详情")
    @GetMapping("/read")
    @LogAnno
    public Object read(@NotNull  @RequestParam(value = "id") Integer id, @LoginAdminShopId Integer shopId){
        return adminAdminOrderService.read(id, shopId);
    }

}
