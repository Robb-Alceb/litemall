package org.linlinjava.litemall.wx.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.core.util.JacksonUtil;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.core.validator.Order;
import org.linlinjava.litemall.core.validator.Sort;
import org.linlinjava.litemall.db.beans.Constants;
import org.linlinjava.litemall.db.domain.LitemallCoupon;
import org.linlinjava.litemall.db.domain.LitemallCouponUser;
import org.linlinjava.litemall.db.service.*;
import org.linlinjava.litemall.wx.annotation.LogAnno;
import org.linlinjava.litemall.wx.annotation.LoginUser;
import org.linlinjava.litemall.wx.vo.CouponVo;
import org.linlinjava.litemall.wx.util.WxResponseCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.*;

/**
 * 优惠券服务
 */
@RestController
@RequestMapping("/wx/coupon")
@Validated
public class WxCouponController {
    private final Log logger = LogFactory.getLog(WxCouponController.class);

    @Autowired
    private LitemallCouponService couponService;
    @Autowired
    private LitemallCouponUserService couponUserService;
    @Autowired
    private LitemallCartService cartService;
    @Autowired
    private CouponVerifyService couponVerifyService;

    /**
     * 优惠券列表
     *
     * @param page
     * @param limit
     * @param sort
     * @param order
     * @return
     */
    @GetMapping("list")
    @LogAnno
    public Object list(@RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @Sort @RequestParam(defaultValue = "add_time") String sort,
                       @Order @RequestParam(defaultValue = "desc") String order) {

        List<LitemallCoupon> couponList = couponService.queryList(page, limit, sort, order);
        return ResponseUtil.okList(couponList);
    }

    /**
     * 个人优惠券列表
     *
     * @param userId
     * @param status
     * @param page
     * @param limit
     * @param sort
     * @param order
     * @return
     */
    @GetMapping("mylist")
    @LogAnno
    public Object mylist(@LoginUser Integer userId,
                       @NotNull @RequestParam(value = "status") Short status,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @Sort @RequestParam(defaultValue = "add_time") String sort,
                       @Order @RequestParam(defaultValue = "desc") String order) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }

        List<LitemallCouponUser> couponUserList = couponUserService.queryList(userId, null, status, page, limit, sort, order);
        List<CouponVo> couponVoList = change(couponUserList);
        return ResponseUtil.okList(couponVoList, couponUserList);
    }

    private List<CouponVo> change(List<LitemallCouponUser> couponList) {
        List<CouponVo> couponVoList = new ArrayList<>(couponList.size());
        for(LitemallCouponUser couponUser : couponList){
            Integer couponId = couponUser.getCouponId();
            LitemallCoupon coupon = couponService.findById(couponId);
            CouponVo couponVo = new CouponVo();
            couponVo.setId(coupon.getId());
            couponVo.setName(coupon.getName());
            couponVo.setDesc(coupon.getDesc());
            couponVo.setTag(coupon.getTag());
            couponVo.setMin(coupon.getMin());
            couponVo.setDiscount(coupon.getDiscount());
            couponVo.setStartTime(couponUser.getStartTime());
            couponVo.setEndTime(couponUser.getEndTime());
            couponVo.setPromotionOnly(coupon.getPromotionOnly());
            couponVo.setDiscountRate(coupon.getDiscountRate());
            couponVo.setDiscountType(coupon.getDiscountType());
            couponVo.setGoodsValue(coupon.getGoodsValue());
            couponVo.setGoodsType(coupon.getGoodsType());

            couponVoList.add(couponVo);
        }

        return couponVoList;
    }


    /**
     * 当前购物车下单商品订单可用优惠券
     *
     * @param userId
     * @param cartIds
     * @return
     */
    @GetMapping("selectlist")
    @LogAnno
    public Object selectlist(@LoginUser Integer userId,@RequestParam("cartIds[]") ArrayList<Integer> cartIds) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }

        // 计算优惠券可用情况
        List<LitemallCouponUser> couponUserList = couponUserService.queryAll(userId);
        List<LitemallCouponUser> availableCouponUserList = new ArrayList<>(couponUserList.size());
        for (LitemallCouponUser couponUser : couponUserList) {
            LitemallCoupon coupon = couponVerifyService.checkCoupon(userId, couponUser.getCouponId(), cartIds);
            if (coupon == null) {
                continue;
            }
            availableCouponUserList.add(couponUser);
        }

        List<CouponVo> couponVoList = change(availableCouponUserList);

        return ResponseUtil.okList(couponVoList);
    }

    /**
     * 优惠券领取
     *
     * @param userId 用户ID
     * @param body 请求内容， { couponId: xxx }
     * @return 操作结果
     */
    @PostMapping("receive")
    @LogAnno
    public Object receive(@LoginUser Integer userId, @RequestBody String body) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }

        Integer couponId = JacksonUtil.parseInteger(body, "couponId");
        if(couponId == null){
            return ResponseUtil.badArgument();
        }

        LitemallCoupon coupon = couponService.findById(couponId);
        if(coupon == null){
            return ResponseUtil.badArgumentValue();
        }

        // 当前已领取数量和总数量比较
        Integer total = coupon.getTotal();
        Integer totalCoupons = couponUserService.countCoupon(couponId);
        if((total != 0) && (totalCoupons >= total)){
            return ResponseUtil.fail(WxResponseCode.COUPON_EXCEED_LIMIT, "优惠券已领完");
        }

        // 当前用户已领取数量和用户限领数量比较
        Integer limit = coupon.getLimit().intValue();
        Integer userCounpons = couponUserService.countUserAndCoupon(userId, couponId);
        if((limit != 0) && (userCounpons >= limit)){
            return ResponseUtil.fail(WxResponseCode.COUPON_EXCEED_LIMIT, "优惠券已经领取过");
        }

        // 优惠券分发类型
        // 例如注册赠券类型的优惠券不能领取
        Short type = coupon.getType();
        if(type.equals(Constants.TYPE_REGISTER)){
            return ResponseUtil.fail(WxResponseCode.COUPON_RECEIVE_FAIL, "新用户优惠券自动发送");
        }
        else if(type.equals(Constants.TYPE_CODE)){
            return ResponseUtil.fail(WxResponseCode.COUPON_RECEIVE_FAIL, "优惠券只能兑换");
        }
        else if(!type.equals(Constants.TYPE_COMMON)){
            return ResponseUtil.fail(WxResponseCode.COUPON_RECEIVE_FAIL, "优惠券类型不支持");
        }

        // 优惠券状态，已下架或者过期不能领取
        Short status = coupon.getStatus();
        if(status.equals(Constants.COUPON_STATUS_OUT)){
            return ResponseUtil.fail(WxResponseCode.COUPON_EXCEED_LIMIT, "优惠券已领完");
        }
        else if(status.equals(Constants.COUPON_STATUS_EXPIRED)){
            return ResponseUtil.fail(WxResponseCode.COUPON_RECEIVE_FAIL, "优惠券已经过期");
        }

        // 用户领券记录
        LitemallCouponUser couponUser = new LitemallCouponUser();
        couponUser.setCouponId(couponId);
        couponUser.setUserId(userId);
        Short timeType = coupon.getTimeType();
        if (timeType.equals(Constants.TIME_TYPE_TIME)) {
            couponUser.setStartTime(coupon.getStartTime());
            couponUser.setEndTime(coupon.getEndTime());
        }
        else{
            LocalDateTime now = LocalDateTime.now();
            couponUser.setStartTime(now);
            couponUser.setEndTime(now.plusDays(coupon.getDays()));
        }
        couponUserService.add(couponUser);

        return ResponseUtil.ok();
    }

    /**
     * 优惠券兑换
     *
     * @param userId 用户ID
     * @param body 请求内容， { code: xxx }
     * @return 操作结果
     */
    @PostMapping("exchange")
    @LogAnno
    public Object exchange(@LoginUser Integer userId, @RequestBody String body) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }

        String code = JacksonUtil.parseString(body, "code");
        if(code == null){
            return ResponseUtil.badArgument();
        }

        LitemallCoupon coupon = couponService.findByCode(code);
        if(coupon == null){
            return ResponseUtil.fail(WxResponseCode.COUPON_CODE_INVALID, "优惠券不正确");
        }
        Integer couponId = coupon.getId();

        // 当前已领取数量和总数量比较
        Integer total = coupon.getTotal();
        Integer totalCoupons = couponUserService.countCoupon(couponId);
        if((total != 0) && (totalCoupons >= total)){
            return ResponseUtil.fail(WxResponseCode.COUPON_EXCEED_LIMIT, "优惠券已兑换");
        }

        // 当前用户已领取数量和用户限领数量比较
        Integer limit = coupon.getLimit().intValue();
        Integer userCounpons = couponUserService.countUserAndCoupon(userId, couponId);
        if((limit != 0) && (userCounpons >= limit)){
            return ResponseUtil.fail(WxResponseCode.COUPON_EXCEED_LIMIT, "优惠券已兑换");
        }

        // 优惠券分发类型
        // 例如注册赠券类型的优惠券不能领取
        Short type = coupon.getType();
        if(type.equals(Constants.TYPE_REGISTER)){
            return ResponseUtil.fail(WxResponseCode.COUPON_RECEIVE_FAIL, "新用户优惠券自动发送");
        }
        else if(type.equals(Constants.TYPE_COMMON)){
            return ResponseUtil.fail(WxResponseCode.COUPON_RECEIVE_FAIL, "优惠券只能领取，不能兑换");
        }
        else if(!type.equals(Constants.TYPE_CODE)){
            return ResponseUtil.fail(WxResponseCode.COUPON_RECEIVE_FAIL, "优惠券类型不支持");
        }

        // 优惠券状态，已下架或者过期不能领取
        Short status = coupon.getStatus();
        if(status.equals(Constants.COUPON_STATUS_OUT)){
            return ResponseUtil.fail(WxResponseCode.COUPON_EXCEED_LIMIT, "优惠券已兑换");
        }
        else if(status.equals(Constants.COUPON_STATUS_EXPIRED)){
            return ResponseUtil.fail(WxResponseCode.COUPON_RECEIVE_FAIL, "优惠券已经过期");
        }

        // 用户领券记录
        LitemallCouponUser couponUser = new LitemallCouponUser();
        couponUser.setCouponId(couponId);
        couponUser.setUserId(userId);
        Short timeType = coupon.getTimeType();
        if (timeType.equals(Constants.TIME_TYPE_TIME)) {
            couponUser.setStartTime(coupon.getStartTime());
            couponUser.setEndTime(coupon.getEndTime());
        }
        else{
            LocalDateTime now = LocalDateTime.now();
            couponUser.setStartTime(now);
            couponUser.setEndTime(now.plusDays(coupon.getDays()));
        }
        couponUserService.add(couponUser);

        return ResponseUtil.ok();
    }
}