package org.linlinjava.litemall.db.service;

import org.linlinjava.litemall.db.domain.LitemallCart;
import org.linlinjava.litemall.db.domain.LitemallCoupon;
import org.linlinjava.litemall.db.domain.LitemallCouponUser;
import org.linlinjava.litemall.db.domain.LitemallGoods;
import org.linlinjava.litemall.db.util.CouponConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 优惠券满额计算使用商品总价，不计税费、运费、其他优惠
 */
@Service
public class CouponVerifyService {

    @Autowired
    private LitemallCouponUserService couponUserService;
    @Autowired
    private LitemallCouponService couponService;
    @Autowired
    private LitemallCartService litemallCartService;
    @Autowired
    private LitemallGoodsService litemallGoodsService;

    /**
     * 检测优惠券是否适合
     *
     * @param userId
     * @param couponId
     * @param checkedGoodsPrice
     * @return
     */
    public LitemallCoupon checkCoupon(Integer userId, Integer couponId, BigDecimal checkedGoodsPrice) {
        LitemallCoupon coupon = couponService.findById(couponId);
        LitemallCouponUser couponUser = couponUserService.queryOne(userId, couponId);
        if (coupon == null || couponUser == null) {
            return null;
        }

        // 检查是否超期
        Short timeType = coupon.getTimeType();
        Short days = coupon.getDays();
        LocalDateTime now = LocalDateTime.now();
        if (timeType.equals(CouponConstant.TIME_TYPE_TIME)) {
            if (now.isBefore(coupon.getStartTime()) || now.isAfter(coupon.getEndTime())) {
                return null;
            }
        }
        else if(timeType.equals(CouponConstant.TIME_TYPE_DAYS)) {
            LocalDateTime expired = couponUser.getAddTime().plusDays(days);
            if (now.isAfter(expired)) {
                return null;
            }
        }
        else {
            return null;
        }

        // 检测商品是否符合
        // TODO 目前仅支持全平台商品，所以不需要检测
        Short goodType = coupon.getGoodsType();
        if (!goodType.equals(CouponConstant.GOODS_TYPE_ALL)) {
            return null;
        }

        // 检测订单状态
        Short status = coupon.getStatus();
        if (!status.equals(CouponConstant.STATUS_NORMAL)) {
            return null;
        }
        // 检测是否满足最低消费
        if (checkedGoodsPrice.compareTo(coupon.getMin()) == -1) {
            return null;
        }

        return coupon;
    }

    public LitemallCoupon checkCoupon(Integer userId, Integer couponId, BigDecimal checkedGoodsPrice, Integer goodsId) {
        return checkCoupon(userId, couponId, checkedGoodsPrice, null, goodsId);
    }

    public LitemallCoupon checkCoupon(Integer userId, Integer couponId, BigDecimal checkedGoodsPrice, Integer categoryId, Integer goodsId) {
        LitemallCoupon coupon = couponService.findById(couponId);
        LitemallCouponUser couponUser = couponUserService.queryOne(userId, couponId);
        if (coupon == null || couponUser == null) {
            return null;
        }

        // 检查是否超期
        Short timeType = coupon.getTimeType();
        Short days = coupon.getDays();
        LocalDateTime now = LocalDateTime.now();
        if (timeType.equals(CouponConstant.TIME_TYPE_TIME)) {
            if (now.isBefore(coupon.getStartTime()) || now.isAfter(coupon.getEndTime())) {
                return null;
            }
        }
        else if(timeType.equals(CouponConstant.TIME_TYPE_DAYS)) {
            LocalDateTime expired = couponUser.getAddTime().plusDays(days);
            if (now.isAfter(expired)) {
                return null;
            }
        }
        else {
            return null;
        }

        // 检测商品是否符合
        Short goodType = coupon.getGoodsType();
        if (goodType.equals(CouponConstant.GOODS_TYPE_ARRAY)) {
            if(coupon.getGoodsValue() != null && Arrays.asList(coupon.getGoodsValue()).contains(goodsId)){
                if(checkedGoodsPrice.compareTo(coupon.getMin()) == -1){
                    return coupon;
                }
            }
        }

        // 检测订单状态
        Short status = coupon.getStatus();
        if (!status.equals(CouponConstant.STATUS_NORMAL)) {
            return null;
        }
        // 检测是否满足最低消费
        if (checkedGoodsPrice.compareTo(coupon.getMin()) == -1) {
            return null;
        }
        if (goodType.equals(CouponConstant.GOODS_TYPE_CATEGORY)){
            if(categoryId == null){
                return null;
            }
            if(coupon.getGoodsValue() != null && Arrays.asList(coupon.getGoodsValue()).contains(categoryId)){
                return coupon;
            }
        }

        return coupon;
    }

    /**
     * 判断优惠券是否能使用
     * @param userId
     * @param couponId
     * @param cartIds
     * @return
     */
    public LitemallCoupon checkCoupon(Integer userId, Integer couponId, List<Integer> cartIds){
        LitemallCoupon coupon = couponService.findById(couponId);
        LitemallCouponUser couponUser = couponUserService.queryOne(userId, couponId);
        if (coupon == null || couponUser == null) {
            return null;
        }
        // 检查是否超期
        Short timeType = coupon.getTimeType();
        Short days = coupon.getDays();
        LocalDateTime now = LocalDateTime.now();
        if (timeType.equals(CouponConstant.TIME_TYPE_TIME)) {
            if (now.isBefore(coupon.getStartTime()) || now.isAfter(coupon.getEndTime())) {
                return null;
            }
        }
        else if(timeType.equals(CouponConstant.TIME_TYPE_DAYS)) {
            LocalDateTime expired = couponUser.getAddTime().plusDays(days);
            if (now.isAfter(expired)) {
                return null;
            }
        }
        else {
            return null;
        }

        // 检测订单状态
        Short status = coupon.getStatus();
        if (!status.equals(CouponConstant.STATUS_NORMAL)) {
            return null;
        }

        // 检测是否满足最低消费
        Short goodType = coupon.getGoodsType();
        List<LitemallCart> carts = litemallCartService.queryByIds(cartIds);
        //全场通用，比较总价
        if(goodType.equals(CouponConstant.GOODS_TYPE_ALL)){
            Double totalPrice = carts.stream().mapToDouble(item -> {
                return item.getPrice().doubleValue();
            }).sum();
            if (totalPrice.compareTo(coupon.getMin().doubleValue()) == -1) {
                return null;
            }
            return coupon;
        //指定类目，比较符合类目的商品总价
        }else if(goodType.equals(CouponConstant.GOODS_TYPE_CATEGORY)){
            List<Integer> goodsIds = carts.stream().map(LitemallCart::getGoodsId).collect(Collectors.toList());
            List<LitemallGoods> goodsList = litemallGoodsService.findByIds(goodsIds);
            List<Integer> filterGoodsIds = goodsList.stream().filter(goods -> {
                return Arrays.asList(coupon.getGoodsValue()).contains(goods.getCategoryId());
            }).map(LitemallGoods::getId).collect(Collectors.toList());

            Double totalPrice = carts.stream().filter(cart -> {
                return filterGoodsIds.contains(cart.getGoodsId());
            }).mapToDouble(item -> {
                return item.getPrice().doubleValue();
            }).sum();
            if (totalPrice.compareTo(coupon.getMin().doubleValue()) == -1) {
                return null;
            }
            return coupon;
            //指定商品，单个商品比较
        }else if(goodType.equals(CouponConstant.GOODS_TYPE_ARRAY)){
            boolean match = carts.stream().anyMatch(cart -> {
                return cart.getPrice().compareTo(coupon.getMin()) > -1;
            });
            if(match){
                return coupon;
            }
        }
        return null;
    }
}