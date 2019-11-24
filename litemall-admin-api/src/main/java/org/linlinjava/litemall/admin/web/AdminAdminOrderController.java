package org.linlinjava.litemall.admin.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.linlinjava.litemall.admin.beans.annotation.LoginAdminShopId;
import org.linlinjava.litemall.admin.beans.annotation.RequiresPermissionsDesc;
import org.linlinjava.litemall.admin.beans.vo.AdminOrderVo;
import org.linlinjava.litemall.admin.service.AdminAdminOrderService;
import org.linlinjava.litemall.core.validator.Order;
import org.linlinjava.litemall.core.validator.Sort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/order")
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
    @RequiresPermissions("admin:order:list")
    @RequiresPermissionsDesc(menu = {"商场管理", "订单管理"}, button = "查询")
    @GetMapping("/list")
    public Object list(String orderSn, String userName, String address,
                       @LoginAdminShopId Integer shopId,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @Sort @RequestParam(defaultValue = "add_time") String sort,
                       @Order @RequestParam(defaultValue = "desc") String order) {
        return adminAdminOrderService.list(orderSn, userName, address, shopId, page, limit, sort, order);
    }

    /**
     * 订单申请
     * @param adminOrderVo
     * @return
     */
    @RequiresPermissions("admin:order:orderApplying")
    @RequiresPermissionsDesc(menu = {"商场管理", "订单管理"}, button = "订单申请")
    @PostMapping("/orderApplying")
    public Object orderApplying(AdminOrderVo adminOrderVo){
        return adminAdminOrderService.orderApplying(adminOrderVo);
    }

    /**
     * 同意调货
     * @param adminOrderVo
     * @return
     */
    @RequiresPermissions("admin:order:orderPass")
    @RequiresPermissionsDesc(menu = {"商场管理", "订单管理"}, button = "同意调货")
    @PostMapping("/orderPass")
    public Object orderPass(AdminOrderVo adminOrderVo){
        return adminAdminOrderService.orderPass(adminOrderVo);
    }

    /**
     * 不同意调货
     * @param adminOrderVo
     * @return
     */
    @RequiresPermissions("admin:order:orderNoPass")
    @RequiresPermissionsDesc(menu = {"商场管理", "订单管理"}, button = "拒绝调货")
    @PostMapping("/orderNoPass")
    public Object orderNoPass (AdminOrderVo adminOrderVo){
        return adminAdminOrderService.orderNoPass(adminOrderVo);
    }

    /**
     * 支付货款
     * @param adminOrderVo
     * @return
     */
    @RequiresPermissions("admin:order:orderPay")
    @RequiresPermissionsDesc(menu = {"商场管理", "订单管理"}, button = "支付货款")
    @PostMapping("/orderPay")
    public Object orderPay(AdminOrderVo adminOrderVo){
        return adminAdminOrderService.orderPass(adminOrderVo);
    }

    /**
     * 同意发货
     * @param adminOrderVo
     * @return
     */
    @RequiresPermissions("admin:order:deliverGoods")
    @RequiresPermissionsDesc(menu = {"商场管理", "订单管理"}, button = "同意发货")
    @PostMapping("/deliverGoods")
    public Object deliverGoods(AdminOrderVo adminOrderVo){
        return adminAdminOrderService.deliverGoods(adminOrderVo);
    }

    /**
     * 拒绝发货
     * @param adminOrderVo
     * @return
     */
    @RequiresPermissions("admin:order:cancelDeliverGoods")
    @RequiresPermissionsDesc(menu = {"商场管理", "订单管理"}, button = "拒绝发货")
    @PostMapping("/cancelDeliverGoods")
    public Object cancelDeliverGoods(AdminOrderVo adminOrderVo){
        return adminAdminOrderService.cancelDeliverGoods(adminOrderVo);
    }


    /**
     * 确认收货
     * @param adminOrderVo
     * @return
     */
    @RequiresPermissions("admin:order:takeDelivery")
    @RequiresPermissionsDesc(menu = {"商场管理", "订单管理"}, button = "确认收货")
    @PostMapping("/takeDelivery")
    public Object takeDelivery (AdminOrderVo adminOrderVo){
        return adminAdminOrderService.takeDelivery(adminOrderVo);
    }


}
