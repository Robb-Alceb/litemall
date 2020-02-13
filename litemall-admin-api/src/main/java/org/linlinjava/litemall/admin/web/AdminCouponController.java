package org.linlinjava.litemall.admin.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.linlinjava.litemall.admin.beans.Constants;
import org.linlinjava.litemall.admin.beans.annotation.LogAnno;
import org.linlinjava.litemall.admin.beans.annotation.RequiresPermissionsDesc;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.core.validator.Order;
import org.linlinjava.litemall.core.validator.Sort;
import org.linlinjava.litemall.db.domain.LitemallCoupon;
import org.linlinjava.litemall.db.domain.LitemallCouponUser;
import org.linlinjava.litemall.db.domain.LitemallGoods;
import org.linlinjava.litemall.db.domain.LitemallOrder;
import org.linlinjava.litemall.db.service.LitemallCouponService;
import org.linlinjava.litemall.db.service.LitemallCouponUserService;
import org.linlinjava.litemall.db.service.LitemallGoodsService;
import org.linlinjava.litemall.db.service.LitemallOrderService;
import org.linlinjava.litemall.db.util.CouponConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/coupon")
@Validated
public class AdminCouponController {
    private final Log logger = LogFactory.getLog(AdminCouponController.class);

    @Autowired
    private LitemallCouponService couponService;
    @Autowired
    private LitemallCouponUserService couponUserService;
    @Autowired
    private LitemallGoodsService goodsService;
    @Autowired
    private LitemallOrderService orderService;

    @RequiresPermissions("admin:coupon:list")
    @RequiresPermissionsDesc(menu = {"推广管理", "优惠券管理"}, button = "查询")
    @GetMapping("/list")
    @LogAnno
    public Object list(String name, Short type, Short status,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @Sort @RequestParam(defaultValue = "add_time") String sort,
                       @Order @RequestParam(defaultValue = "desc") String order) {
        List<LitemallCoupon> couponList = couponService.querySelective(name, type, status, page, limit, sort, order);
        return ResponseUtil.okList(couponList);
    }

    @RequiresPermissions("admin:coupon:listuser")
    @RequiresPermissionsDesc(menu = {"推广管理", "优惠券管理"}, button = "查询用户")
    @GetMapping("/listuser")
    @LogAnno
    public Object listuser(Integer userId, Integer couponId, Short status,
                           @RequestParam(defaultValue = "1") Integer page,
                           @RequestParam(defaultValue = "10") Integer limit,
                           @Sort @RequestParam(defaultValue = "add_time") String sort,
                           @Order @RequestParam(defaultValue = "desc") String order) {
        List<LitemallCouponUser> couponList = couponUserService.queryList(userId, couponId, status, page,
                limit, sort, order);
        return ResponseUtil.okList(couponList);
    }

    private Object validate(LitemallCoupon coupon) {
        String name = coupon.getName();
        if (StringUtils.isEmpty(name)) {
            return ResponseUtil.badArgument();
        }
        return null;
    }

    @RequiresPermissions("admin:coupon:create")
    @RequiresPermissionsDesc(menu = {"推广管理", "优惠券管理"}, button = "添加")
    @PostMapping("/create")
    @LogAnno
    public Object create(@RequestBody LitemallCoupon coupon) {
        Object error = validate(coupon);
        if (error != null) {
            return error;
        }

        // 如果是兑换码类型，则这里需要生存一个兑换码
        if (coupon.getType().equals(CouponConstant.TYPE_CODE)) {
            String code = couponService.generateCode();
            coupon.setCode(code);
        }

        couponService.add(coupon);
        return ResponseUtil.ok(coupon);
    }

    @RequiresPermissions("admin:coupon:read")
    @RequiresPermissionsDesc(menu = {"推广管理", "优惠券管理"}, button = "详情")
    @GetMapping("/read")
    @LogAnno
    public Object read(@NotNull @RequestParam(value = "id") Integer id) {
        LitemallCoupon coupon = couponService.findById(id);
        return ResponseUtil.ok(coupon);
    }

    @RequiresPermissions("admin:coupon:update")
    @RequiresPermissionsDesc(menu = {"推广管理", "优惠券管理"}, button = "编辑")
    @PostMapping("/update")
    @LogAnno
    public Object update(@RequestBody LitemallCoupon coupon) {
        Object error = validate(coupon);
        if (error != null) {
            return error;
        }
        if (couponService.updateById(coupon) == 0) {
            return ResponseUtil.updatedDataFailed();
        }
        return ResponseUtil.ok(coupon);
    }

    @RequiresPermissions("admin:coupon:delete")
    @RequiresPermissionsDesc(menu = {"推广管理", "优惠券管理"}, button = "删除")
    @PostMapping("/delete")
    @LogAnno
    public Object delete(@RequestBody LitemallCoupon coupon) {
        couponService.deleteById(coupon.getId());
        return ResponseUtil.ok();
    }

    /**
     * 根据优惠券ID 查询指定商品列表
     * @param id
     * @return
     */
    @RequiresPermissions("admin:coupon:goodsList")
    @RequiresPermissionsDesc(menu = {"推广管理", "商品列表"}, button = "查询")
    @GetMapping("/goodsList")
    @LogAnno
    public Object goodsList(@NotNull @RequestParam(value = "id") Integer id,
                            @RequestParam(defaultValue = "1") Integer page,
                            @RequestParam(defaultValue = "10") Integer limit,
                            @Sort @RequestParam(defaultValue = "add_time") String sort,
                            @Order @RequestParam(defaultValue = "desc") String order) {
        LitemallCoupon coupon = couponService.findById(id);
        //2:指定商品
        List<LitemallGoods> litemallGoods = new ArrayList<>();
        if(coupon!=null && coupon.getGoodsType()== Constants.GOODS_TYPE_TWO
            &&coupon.getGoodsValue()!=null && coupon.getGoodsValue().length>0){
            litemallGoods = goodsService.querySelective(coupon.getGoodsValue(), page, limit, sort, order);
        }
        return ResponseUtil.okList(litemallGoods);
    }

    @RequiresPermissions("admin:coupon:statistics")
    @RequiresPermissionsDesc(menu = {"推广管理", "优惠券使用统计"}, button = "查询")
    @GetMapping("/statistics")
    @LogAnno
    public Object usedStatistics(@NotNull Integer id){
        Map<String,Object> map = new HashMap<>();

        map.put("pullNumber",couponUserService.countCoupon(id));
        map.put("expiredNumber",couponUserService.countCoupon(id, CouponConstant.STATUS_EXPIRED));
        LitemallCoupon coupon = couponService.findById(id);
        Integer usedNumber = couponUserService.countCoupon(id, CouponConstant.STATUS_USED);
        map.put("usedNumber",usedNumber);
        if(null != coupon){
            map.put("totalDiscount",coupon.getDiscount().multiply(new BigDecimal(usedNumber)));
        }
        List<LitemallOrder> orders = new ArrayList<>();
        List<LitemallCouponUser> litemallCouponUsers = couponUserService.queryByCoupon(id, CouponConstant.STATUS_USED);
        for(LitemallCouponUser item : litemallCouponUsers){
            if(null !=item.getOrderId()){
                orders.add(orderService.findById(item.getOrderId()));
            }
        }
        //订单总价
        BigDecimal orderTotalPrice = new BigDecimal(0.0);
        //订单优惠价
        BigDecimal orderTotalDiscount = new BigDecimal(0.0);
        //订单实际支付价格
        BigDecimal orderTotalActual = new BigDecimal(0.0);
        for(LitemallOrder order : orders){
            orderTotalPrice = orderTotalPrice.add(order.getOrderPrice());
            orderTotalDiscount = orderTotalDiscount.add(order.getCouponPrice());
            orderTotalActual = orderTotalActual.add(order.getActualPrice());
        }
        map.put("orderTotalPrice",orderTotalPrice);
        map.put("orderTotalDiscount",orderTotalDiscount);
        map.put("orderTotalActual",orderTotalActual);
        return ResponseUtil.ok(map);
    }
}
