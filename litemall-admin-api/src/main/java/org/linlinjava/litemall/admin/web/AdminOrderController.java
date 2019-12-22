package org.linlinjava.litemall.admin.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.linlinjava.litemall.admin.beans.annotation.LogAnno;
import org.linlinjava.litemall.admin.beans.annotation.LoginAdminShopId;
import org.linlinjava.litemall.admin.beans.annotation.RequiresPermissionsDesc;
import org.linlinjava.litemall.admin.service.AdminOrderService;
import org.linlinjava.litemall.core.validator.Order;
import org.linlinjava.litemall.core.validator.Sort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
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
    @LogAnno
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
    @LogAnno
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
    @LogAnno
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
    @LogAnno
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
    @LogAnno
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
    @LogAnno
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
    @LogAnno
    public Object goodsStatistics(@LoginAdminShopId Integer shopId,@NotNull String startTime,@NotNull  String endTime){
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
    @RequiresPermissions("admin:order:goodsSalesStatistics")
    @RequiresPermissionsDesc(menu = {"商场管理", "订单管理"}, button = "商品销售统计")
    @GetMapping("/goodsSalesStatistics")
    @LogAnno
    public Object goodsSalesStatistics(@LoginAdminShopId Integer shopId, @NotNull String type,@NotNull String startTime,@NotNull  String endTime,
                                       @RequestParam(defaultValue = "1") Integer page,
                                       @RequestParam(defaultValue = "10") Integer limit,
                                       @Sort @RequestParam(defaultValue = "add_time") String sort,
                                       @Order @RequestParam(defaultValue = "desc") String order){
        return adminOrderService.goodsSalesStatistics(shopId, type, startTime, endTime, page, limit, sort, order);
    }

    /**
     * 销售统计
     * @param startTime
     * @param endTime
     * @return
     */
    @RequiresPermissions("admin:order:salesStatistics")
    @RequiresPermissionsDesc(menu = {"商场管理", "订单管理"}, button = "销售统计")
    @GetMapping("/salesStatistics")
    @LogAnno
    public Object salesStatistics(@NotNull String startTime,@NotNull String endTime){
        return adminOrderService.salesStatistics(startTime, endTime);
    }

    /**
     * 交易数据
     * @param startTime
     * @param endTime
     * @return
     */
    @RequiresPermissions("admin:order:transactionData")
    @RequiresPermissionsDesc(menu = {"商场管理", "订单管理"}, button = "交易数据")
    @GetMapping("/transactionData")
    @LogAnno
    public Object transactionData(@NotNull String startTime,@NotNull  String endTime){
        return adminOrderService.transactionData(startTime, endTime);
    }



    /**
     * 根据订单ID 查询订单是否完成 或者已评价 1：已完成 2：已评价 3：未完成
     * @param orderId
     * @return
     */
    @RequiresPermissions("admin:order:queryOrderIsCompletionById")
    @RequiresPermissionsDesc(menu = {"商场管理", "订单管理"}, button = "查询订单是否完成")
    @GetMapping("/queryOrderIsCompletionById")
    @LogAnno
    public Object queryOrderIsCompletionById(@NotNull Integer orderId){
        return adminOrderService.queryOrderIsCompletionById(orderId);
    }
}
