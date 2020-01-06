package org.linlinjava.litemall.web.web;

import org.linlinjava.litemall.core.validator.Order;
import org.linlinjava.litemall.core.validator.Sort;
import org.linlinjava.litemall.db.domain.LitemallOrder;
import org.linlinjava.litemall.db.domain.LitemallOrderGoods;
import org.linlinjava.litemall.web.service.WebOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/web/order")
@Validated
public class WebOrderController {

    @Autowired
    private WebOrderService orderService;

    /**
     * 订单列表
     *
     * @param userId   用户ID
     * @param showType 订单信息
     * @param page     分页页数
     * @param limit     分页大小
     * @return 订单列表
     */
    @GetMapping("list")
    public Object list(Integer userId,
                       @RequestParam(defaultValue = "0") Integer showType,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @Sort @RequestParam(defaultValue = "add_time") String sort,
                       @Order @RequestParam(defaultValue = "desc") String order) {
        return orderService.list(userId, showType, page, limit, sort, order);
    }

    /**
     * 订单详情
     *
     * @param userId  用户ID
     * @param orderId 订单ID
     * @return 订单详情
     */
    @GetMapping("detail")
    public Object detail(Integer userId, @NotNull Integer orderId) {
        return orderService.detail(userId, orderId);
    }

    /**
     * 提交订单
     *
     * @param orderGoodss 订单商品
     * @param order   订单数据
     * @return 提交订单
     */
    @PostMapping("submit")
    public Object submit(@NotNull List<LitemallOrderGoods> orderGoodss, @NotNull LitemallOrder order) {
        return orderService.submit(orderGoodss, order);
    }

}