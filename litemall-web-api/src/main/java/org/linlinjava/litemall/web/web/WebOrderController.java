package org.linlinjava.litemall.web.web;

import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.core.validator.Order;
import org.linlinjava.litemall.core.validator.Sort;
import org.linlinjava.litemall.db.domain.LitemallCart;
import org.linlinjava.litemall.web.annotation.LogAnno;
import org.linlinjava.litemall.web.annotation.LoginShop;
import org.linlinjava.litemall.web.annotation.LoginUser;
import org.linlinjava.litemall.web.dto.CartDto;
import org.linlinjava.litemall.web.service.WebOrderService;
import org.linlinjava.litemall.web.vo.CalculationOrderVo;
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
    @LogAnno
    public Object list(@LoginUser Integer userId,
                       @RequestParam(name = "isAll", defaultValue = "0")Boolean isAll,
                       @RequestParam(defaultValue = "1") Boolean today,
                       @RequestParam(defaultValue = "0") Integer showType,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @Sort @RequestParam(defaultValue = "add_time") String sort,
                       @Order @RequestParam(defaultValue = "desc") String order) {
        return orderService.list(isAll, userId, today, showType, page, limit, sort, order);
    }

    /**
     * 订单详情
     *
     * @param userId  用户ID
     * @param orderId 订单ID
     * @return 订单详情
     */
    @GetMapping("detail")
    @LogAnno
    public Object detail(@LoginUser Integer userId, @NotNull Integer orderId) {
        return orderService.detail(userId, orderId);
    }

    /**
     * 提交订单
     *
     * @param userId 用户id
     * @param body   订单数据
     * @return 提交订单
     */
    @PostMapping("submit")
    @LogAnno
    public Object submit(@LoginShop Integer shopId, @LoginUser Integer userId, @RequestBody String body) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }
        return orderService.submit(shopId, userId, body);
    }

    /**
     * 支付订单（ipad订单，现金支付或者pos支付），打印订单信息
     * @param userId
     * @param body
     * @return
     */
    @PostMapping("pay")
    @LogAnno
    public Object pay(@LoginUser Integer userId, @RequestBody String body) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }
        return orderService.pay(userId, body);
    }

    @PostMapping("complete")
    @LogAnno
    public Object complete(@LoginUser Integer userId, @RequestBody String body) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }
        return orderService.complete(userId, body);
    }

    /**
     * 订单总数
     * @param userId
     * @return
     */
    @GetMapping("countorder")
    @LogAnno
    public Object countorder(@LoginUser Integer userId){
        if (userId == null) {
            return ResponseUtil.unlogin();
        }
        return orderService.countorder(null);
    }

    /**
     * 订单总数
     * @param userId
     * @return
     */
    @GetMapping("countbystatus")
    @LogAnno
    public Object countByStatus(@LoginUser Integer userId){
        if (userId == null) {
            return ResponseUtil.unlogin();
        }
        return orderService.countByStatus(userId);
    }

    /**
     * 跳过添加购物车、直接下单
     *
     * @param userId 用户id
     * @param cartDto   订单数据
     * @return
     */
    @PostMapping("directly")
    @LogAnno
    public Object orderDirectly(@LoginShop Integer shopId, @LoginUser Integer userId, @RequestBody CartDto cartDto) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }
        return orderService.orderDirectly(shopId, userId, cartDto);
    }

    /**
     *  ipad用户下班结算金额
     *
     * @param userId 用户id
     * @param calculationOrderVos   金额数据
     *
     */
    @PostMapping("calculation")
    @LogAnno
    public Object calculationOrder(@LoginUser Integer userId, @RequestBody List<CalculationOrderVo> calculationOrderVos) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }
        return orderService.calculationOrder(userId, calculationOrderVos);
    }
}