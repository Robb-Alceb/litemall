package org.linlinjava.litemall.web.service;

import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.beans.Constants;
import org.linlinjava.litemall.db.domain.LitemallCoupon;
import org.linlinjava.litemall.db.domain.LitemallOrder;
import org.linlinjava.litemall.db.service.LitemallCouponService;
import org.linlinjava.litemall.db.service.LitemallOrderService;
import org.linlinjava.litemall.db.util.OrderHandleOption;
import org.linlinjava.litemall.db.util.OrderUtil;
import org.linlinjava.litemall.web.dto.CouponDto;
import org.linlinjava.litemall.web.util.WebResponseEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.linlinjava.litemall.db.beans.Constants.ORDER_TYPE_CASH;


/**
 * @author ：stephen
 * @date ：Created in 2020/5/12 13:50
 * @description：TODO
 */
@Service
public class WebCouponService {
    @Autowired
    private LitemallCouponService litemallCouponService;
    @Autowired
    private LitemallOrderService litemallOrderService;


    public Object use(CouponDto dto) {
        LitemallCoupon coupon = litemallCouponService.findByBarCode(dto.getBarcode());
        if (coupon == null) {
            return ResponseUtil.fail(WebResponseEnum.COUPON_BARCODE_ERROR);
        }
        // 检查是否超期
        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(coupon.getStartTime()) || now.isAfter(coupon.getEndTime())) {
            return ResponseUtil.fail(WebResponseEnum.COUPON_BARCODE_EXPIRE);
        }
        if(coupon.getTotal() <= 0){
            return ResponseUtil.fail(WebResponseEnum.COUPON_BARCODE_NOT_ENOUGH);
        }

        LitemallOrder order = litemallOrderService.findById(dto.getOrderId());
        if(order == null){
            return ResponseUtil.fail(WebResponseEnum.ORDER_NOT_EXIST);
        }
        if(order.getActualPrice().compareTo(coupon.getMin()) == -1){
            return ResponseUtil.fail(WebResponseEnum.COUPON_BARCODE_ORDER);
        }
        // 检测订单是否能够使用优惠券
        OrderHandleOption handleOption = OrderUtil.build(order);
        if (!handleOption.isPay()) {
            return ResponseUtil.fail(WebResponseEnum.ORDER_NOT_EXIST);
        }
        BigDecimal couponPrice = new BigDecimal(0.00);
        if(coupon.getDiscountType() == Constants.DISCOUNT_TYPE_REDUCE){
            couponPrice = coupon.getDiscount();
        }else if(coupon.getDiscountType() == Constants.DISCOUNT_TYPE_RATE){
            //优惠价格 = 订单价格 - (订单价格+原先的优惠价格)*优惠比例
            couponPrice = order.getActualPrice().subtract(order.getActualPrice().add(order.getCouponPrice()).multiply(new BigDecimal(coupon.getDiscountRate() / 100)));
        }

        LitemallOrder updater = new LitemallOrder();
        updater.setId(order.getId());
        //订单价格 = 订单价格 + 原先的优惠价格 - 后面的优惠价格
        updater.setOrderPrice(order.getOrderPrice().add(order.getCouponPrice()).subtract(couponPrice));
        updater.setCouponPrice(couponPrice);
        updater.setActualPrice(order.getOrderPrice().subtract(order.getIntegralPrice()));

        if(litemallOrderService.updateWithOptimisticLocker(updater) == 0){
            return ResponseUtil.fail(WebResponseEnum.ORDER_NOT_EXIST);
        };
        return order;
    }


}
