package org.linlinjava.litemall.admin.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.linlinjava.litemall.admin.beans.annotation.LoginAdminShopId;
import org.linlinjava.litemall.admin.beans.annotation.RequiresPermissionsDesc;
import org.linlinjava.litemall.admin.service.AdminOrderService;
import org.linlinjava.litemall.core.validator.Order;
import org.linlinjava.litemall.core.validator.Sort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/admin/order")
@Validated
public class AdminOrderController {
    private final Log logger = LogFactory.getLog(AdminOrderController.class);

    @Autowired
    private AdminOrderService adminOrderService;

    /**
     * 订单列表
     *
     * @param orderSn
     * @param page
     * @param limit
     * @param sort
     * @param order
     * @return
     */
    @RequiresPermissions("admin:order:list")
    @RequiresPermissionsDesc(menu = {"商场管理", "订单管理"}, button = "查询")
    @GetMapping("/list")
    public Object list(Integer userId, String orderSn,
                       @LoginAdminShopId Integer shopId,
                       @RequestParam(required = false) List<Short> orderStatusArray,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @Sort @RequestParam(defaultValue = "add_time") String sort,
                       @Order @RequestParam(defaultValue = "desc") String order) {
        return adminOrderService.list(userId, orderSn, orderStatusArray, shopId, page, limit, sort, order);
    }

    /**
     * 详情
     *
     * @param id
     * @return
     */
    @RequiresPermissions("admin:order:read")
    @RequiresPermissionsDesc(menu = {"商场管理", "订单管理"}, button = "详情")
    @GetMapping("/detail")
    public Object detail(@NotNull Integer id) {
        return adminOrderService.detail(id);
    }

    /**
     * 订单退款
     *
     * @param orderId 订单Id，{ orderId：xxx }
     * @return 订单退款操作结果
     */
    @RequiresPermissions("admin:order:refund")
    @RequiresPermissionsDesc(menu = {"商场管理", "订单管理"}, button = "订单退款")
    @PostMapping("/refund")
    public Object refund(@NotNull Integer orderId, @LoginAdminShopId Integer shopId) {
        return adminOrderService.refund(orderId, shopId);
    }

    /**
     * 发货
     *
     * @param body 订单信息，{ orderId：xxx, shipSn: xxx, shipChannel: xxx }
     * @return 订单操作结果
     */
    @RequiresPermissions("admin:order:ship")
    @RequiresPermissionsDesc(menu = {"商场管理", "订单管理"}, button = "订单发货")
    @PostMapping("/ship")
    public Object ship(@RequestBody String body) {
        return adminOrderService.ship(body);
    }


    /**
     * 回复订单商品
     *
     * @param body 订单信息，{ orderId：xxx }
     * @return 订单操作结果
     */
    @RequiresPermissions("admin:order:reply")
    @RequiresPermissionsDesc(menu = {"商场管理", "订单管理"}, button = "订单商品回复")
    @PostMapping("/reply")
    public Object reply(@RequestBody String body) {
        return adminOrderService.reply(body);
    }

    /**
     * 添加订单备注
     *
     * @param body 订单信息，{ orderId：xxx }
     * @return 订单操作结果
     */
    @RequiresPermissions("admin:order:remark")
    @RequiresPermissionsDesc(menu = {"商场管理", "订单管理"}, button = "订单商品备注")
    @PostMapping("/remark")
    public Object mark(@RequestBody String body, @LoginAdminShopId Integer shopId) {
        return adminOrderService.remark(body, shopId);
    }

    /**
     * 商品统计
     *
     * @return
     */
    @RequiresPermissions("admin:order:goodsStatistics")
    @RequiresPermissionsDesc(menu = {"商场管理", "订单管理"}, button = "商品统计")
    @GetMapping("/goodsStatistics")
    public Object goodsStatistics(@LoginAdminShopId Integer shopId, @NotNull LocalDateTime startTime,  @NotNull LocalDateTime endTime){
        return adminOrderService.goodsStatistics(startTime, endTime, shopId);
    }

    /**
     * 商品销售统计
     * @param type 1:商品 其它分类
     * @param startTime
     * @param endTime
     * @param page
     * @param limit
     * @param sort
     * @param order
     * @return
     */
    public Object goodsSalesStatistics( @NotNull String type, @NotNull String startTime,  @NotNull String endTime,
                                       @RequestParam(defaultValue = "1") Integer page,
                                       @RequestParam(defaultValue = "10") Integer limit,
                                       @Sort @RequestParam(defaultValue = "add_time") String sort,
                                       @Order @RequestParam(defaultValue = "desc") String order){
        return adminOrderService.goodsSalesStatistics(type, startTime, endTime, page, limit, sort, order);
    }
}
