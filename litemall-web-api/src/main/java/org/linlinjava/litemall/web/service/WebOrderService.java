package org.linlinjava.litemall.web.service;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.core.express.ExpressService;
import org.linlinjava.litemall.core.express.dao.ExpressInfo;
import org.linlinjava.litemall.core.util.JacksonUtil;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.beans.Constants;
import org.linlinjava.litemall.db.domain.*;
import org.linlinjava.litemall.db.service.*;
import org.linlinjava.litemall.db.util.CouponUserConstant;
import org.linlinjava.litemall.db.util.OrderHandleOption;
import org.linlinjava.litemall.db.util.OrderUtil;
import org.linlinjava.litemall.web.annotation.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static org.linlinjava.litemall.db.beans.Constants.ORDER_AET;
import static org.linlinjava.litemall.web.util.WebResponseCode.*;

/**
 * 订单服务
 *
 * <p>
 * 订单状态：
 * 101 订单生成，未支付；102，下单后未支付用户取消；103，下单后未支付超时系统自动取消
 * 201 支付完成，商家未发货；202，订单生产，已付款未发货，但是退款取消；
 * 301 商家发货，用户未确认；
 * 401 用户确认收货； 402 用户没有确认收货超过一定时间，系统自动确认收货；
 *
 * <p>
 * 用户操作：
 * 当101用户未付款时，此时用户可以进行的操作是取消订单，或者付款操作
 * 当201支付完成而商家未发货时，此时用户可以取消订单并申请退款
 * 当301商家已发货时，此时用户可以有确认收货的操作
 * 当401用户确认收货以后，此时用户可以进行的操作是删除订单，评价商品，或者再次购买
 * 当402系统自动确认收货以后，此时用户可以删除订单，评价商品，或者再次购买
 *
 * <p>
 * 注意：目前不支持订单退货和售后服务
 */
@Service
public class WebOrderService {
    private final Log logger = LogFactory.getLog(WebOrderService.class);

    @Autowired
    private LitemallOrderService orderService;
    @Autowired
    private LitemallOrderGoodsService orderGoodsService;
    @Autowired
    private LitemallGoodsProductService productService;
    @Autowired
    private LitemallGrouponService grouponService;
    @Autowired
    private ExpressService expressService;
    @Autowired
    private LitemallGoodsService goodsService;
    @Autowired
    private LitemallShopService shopService;
    @Autowired
    private LitemallCartService cartService;
    @Autowired
    private LitemallGoodsSpecificationService goodsSpecificationService;

    /**
     * 订单列表
     *
     * @param userId   用户ID
     * @param showType 订单信息：
     *                 0，全部订单；
     *                 1，待付款；
     *                 2，待发货；
     *                 3，待收货；
     *                 4，待评价。
     * @param page     分页页数
     * @param limit     分页大小
     * @return 订单列表
     */
    public Object list(Integer userId,Boolean today, Integer showType, Integer page, Integer limit, String sort, String order) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }

        List<Short> orderStatus = OrderUtil.orderStatus(showType);
        List<LitemallOrder> orderList = orderService.queryTodayByOrderStatus(userId, today, orderStatus, page, limit, sort, order);

        List<Map<String, Object>> orderVoList = new ArrayList<>(orderList.size());
        for (LitemallOrder o : orderList) {
            Map<String, Object> orderVo = new HashMap<>();
            orderVo.put("id", o.getId());
            orderVo.put("orderSn", o.getOrderSn());
            orderVo.put("actualPrice", o.getActualPrice());
            orderVo.put("orderStatusText", OrderUtil.orderStatusText(o));
            orderVo.put("taxPrice", o.getTaxPrice());
            orderVo.put("orderStatus", o.getOrderStatus());
            orderVo.put("handleOption", OrderUtil.build(o));

            LitemallGroupon groupon = grouponService.queryByOrderId(o.getId());
            if (groupon != null) {
                orderVo.put("isGroupin", true);
            } else {
                orderVo.put("isGroupin", false);
            }

            List<LitemallOrderGoods> orderGoodsList = orderGoodsService.queryByOid(o.getId());
            List<Map<String, Object>> orderGoodsVoList = new ArrayList<>(orderGoodsList.size());
            for (LitemallOrderGoods orderGoods : orderGoodsList) {
                Map<String, Object> orderGoodsVo = new HashMap<>();
                orderGoodsVo.put("id", orderGoods.getId());
                orderGoodsVo.put("goodsName", orderGoods.getGoodsName());
                orderGoodsVo.put("number", orderGoods.getNumber());
                orderGoodsVo.put("picUrl", orderGoods.getPicUrl());
                orderGoodsVo.put("specifications", orderGoods.getSpecifications());
                orderGoodsVoList.add(orderGoodsVo);
            }
            orderVo.put("goodsList", orderGoodsVoList);

            orderVoList.add(orderVo);
        }

        return ResponseUtil.okList(orderVoList, orderList);
    }

    /**
     * 订单详情
     *
     * @param userId  用户ID
     * @param orderId 订单ID
     * @return 订单详情
     */
    public Object detail(Integer userId, Integer orderId) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }

        // 订单信息
        LitemallOrder order = orderService.findById(orderId);
        if (null == order) {
            return ResponseUtil.fail(ORDER_UNKNOWN, "订单不存在");
        }
        if (!order.getUserId().equals(userId)) {
            return ResponseUtil.fail(ORDER_INVALID, "不是当前用户的订单");
        }
        Map<String, Object> orderVo = new HashMap<String, Object>();
        orderVo.put("id", order.getId());
        orderVo.put("orderSn", order.getOrderSn());
        orderVo.put("addTime", order.getAddTime());
        orderVo.put("consignee", order.getConsignee());
        orderVo.put("mobile", order.getMobile());
        orderVo.put("address", order.getAddress());
        orderVo.put("goodsPrice", order.getGoodsPrice());
        orderVo.put("couponPrice", order.getCouponPrice());
        orderVo.put("freightPrice", order.getFreightPrice());
        orderVo.put("actualPrice", order.getActualPrice());
        orderVo.put("taxPrice", order.getTaxPrice());
        orderVo.put("orderStatusText", OrderUtil.orderStatusText(order));
        orderVo.put("orderStatus", order.getOrderStatus());
        orderVo.put("handleOption", OrderUtil.build(order));
        orderVo.put("expCode", order.getShipChannel());
        orderVo.put("expNo", order.getShipSn());

        List<LitemallOrderGoods> orderGoodsList = orderGoodsService.queryByOid(order.getId());

        Map<String, Object> result = new HashMap<>();
        result.put("orderInfo", orderVo);
        result.put("orderGoods", orderGoodsList);

        // 订单状态为已发货且物流信息不为空
        //"YTO", "800669400640887922"
        if (order.getOrderStatus().equals(OrderUtil.STATUS_SHIP)) {
            ExpressInfo ei = expressService.getExpressInfo(order.getShipChannel(), order.getShipSn());
            result.put("expressInfo", ei);
        }

        return ResponseUtil.ok(result);

    }

    /**
     * 提交订单
     * <p>
     * 1. 创建订单表项和订单商品表项;
     *  1-1.判断商品是否上架
     * 2. 商品货品库存减少;
     *
     * @return 提交订单操作结果
     */
    @Transactional
    public Object submit(Integer shopId, Integer userId, String body) {

//        Integer shopId = JacksonUtil.parseInteger(body, "shopId");
        Integer cartId = JacksonUtil.parseInteger(body, "cartId");
        //订单类型（1：自提订单;2:外送订单）
        Integer orderType = JacksonUtil.parseInteger(body, "orderType");


        if ((userId == null && cartId == null) || orderType == null || shopId == null) {
            return ResponseUtil.badArgument();
        }
        Object error = validShop(shopId);
        if( error != null){
            return error;
        }

        // 货品价格
        List<LitemallCart> checkedGoodsList = null;
        if (cartId == null || cartId.equals(0)) {
            checkedGoodsList = cartService.queryByUidAndChecked(userId);
        } else {
            LitemallCart cart = cartService.findById(cartId);
            checkedGoodsList = new ArrayList<>(1);
            checkedGoodsList.add(cart);
        }
        if (checkedGoodsList.size() == 0) {
            return ResponseUtil.badArgumentValue();
        }

        //优惠券抵扣价格
        BigDecimal couponPrice = new BigDecimal(0.00);

        /**
         * 商品价格
         */
        BigDecimal checkedGoodsPrice = new BigDecimal(0.00);
        /**
         * 税费
         */
        BigDecimal taxGoodsPrice = new BigDecimal(0.00);

        // 商品优惠价格，如会员减免、满减、阶梯价等
        BigDecimal discountPrice = new BigDecimal(0.00);
        for (LitemallCart checkGoods : checkedGoodsList) {
            Integer goodsId = checkGoods.getGoodsId();
            LitemallGoods litemallGoods = goodsService.findById(goodsId);
            if(litemallGoods == null){
                return ResponseUtil.fail(GOODS_UNKNOWN,"商品不存在");
            }else if(!litemallGoods.getIsOnSale()){
                return ResponseUtil.fail(GOODS_NOT_SALE,"商品已下架");
            }
            Integer productId = checkGoods.getProductId();
            LitemallGoodsProduct goodsProduct = productService.findById(productId);
            //  规格价格
            BigDecimal specGoodsPrice = new BigDecimal(0.00);
            if(checkGoods.getSpecificationIds() != null){
                for(Integer sid : checkGoods.getSpecificationIds()){
                    LitemallGoodsSpecification specificationServiceById = goodsSpecificationService.findById(sid);
                    if(specificationServiceById != null){
                        specGoodsPrice = specGoodsPrice.add(specificationServiceById.getPrice().multiply(new BigDecimal(checkGoods.getNumber())));
                    }
                }
            }
            logger.debug("WxOrderService [submit] specGoodsPrice is: "+specGoodsPrice.toString());
            logger.debug("WxOrderService [submit] goodsProduct is: "+goodsProduct.getSellPrice().toString());
            if(goodsProduct.getSellPrice().add(specGoodsPrice).compareTo(checkGoods.getPrice()) != 0){
                return ResponseUtil.fail(GOODS_PRICE_CHANGE,"商品价格已更新，请重新添加商品");
            }
            checkedGoodsPrice = checkedGoodsPrice.add(checkGoods.getPrice().multiply(new BigDecimal(checkGoods.getNumber())));


            taxGoodsPrice = taxGoodsPrice.add(checkGoods.getTaxPrice());
        }
        checkedGoodsPrice = checkedGoodsPrice.add(taxGoodsPrice).subtract(discountPrice);


        // 可以使用的其他钱，例如用户积分
        BigDecimal integralPrice = new BigDecimal(0.00);



        // 订单费用
        BigDecimal orderTotalPrice = checkedGoodsPrice.subtract(couponPrice).max(new BigDecimal(0.00));
        // 最终支付费用
        BigDecimal actualPrice = orderTotalPrice.subtract(integralPrice);

        Integer orderId = null;
        LitemallOrder order = null;
        // 订单
        order = new LitemallOrder();
        order.setUserId(userId);
        order.setOrderSn(orderService.generateOrderSn(userId));
        order.setOrderStatus(OrderUtil.STATUS_CREATE);
        order.setConsignee("");
        order.setMobile("");
        order.setMessage("");
        order.setAddress("");
        order.setGoodsPrice(checkedGoodsPrice);
        order.setFreightPrice(new BigDecimal(0.00));
        order.setCouponPrice(couponPrice);
        order.setIntegralPrice(integralPrice);
        order.setOrderPrice(orderTotalPrice);
        order.setActualPrice(actualPrice);
        order.setTaxPrice(taxGoodsPrice);
        order.setShopId(shopId);
        order.setOrderType(orderType.byteValue());
        order.setShopOrder(true);

        // 添加订单表项
        orderService.add(order);
        orderId = order.getId();

        // 添加订单商品表项
        for (LitemallCart cartGoods : checkedGoodsList) {
            // 订单商品
            LitemallOrderGoods orderGoods = new LitemallOrderGoods();
            orderGoods.setOrderId(order.getId());
            orderGoods.setGoodsId(cartGoods.getGoodsId());
            orderGoods.setGoodsSn(cartGoods.getGoodsSn());
            orderGoods.setProductId(cartGoods.getProductId());
            orderGoods.setGoodsName(cartGoods.getGoodsName());
            orderGoods.setPicUrl(cartGoods.getPicUrl());
            orderGoods.setPrice(cartGoods.getPrice());
            orderGoods.setNumber(cartGoods.getNumber());
            orderGoods.setSpecifications(cartGoods.getSpecifications());
            orderGoods.setAddTime(LocalDateTime.now());
            orderGoods.setTaxPrice(cartGoods.getTaxPrice());

            orderGoodsService.add(orderGoods);
        }

        // 删除购物车里面的商品信息
        if(cartId != null){
            cartService.clearGoods(userId, cartId);
        }else{
            cartService.clearGoods(userId);
        }

        // 商品货品数量减少
        for (LitemallCart checkGoods : checkedGoodsList) {
            Integer productId = checkGoods.getProductId();
            LitemallGoodsProduct product = productService.findById(productId);

            Integer remainNumber = product.getNumber() - checkGoods.getNumber();
            if (remainNumber < 0) {
                throw new RuntimeException("下单的商品货品数量大于库存量");
            }
            if (productService.reduceStock(productId, checkGoods.getNumber()) == 0) {
                throw new RuntimeException("商品货品库存减少失败");
            }
        }
        Map<String, Object> data = new HashMap<>();
        data.put("orderId", orderId);
        return ResponseUtil.ok();
    }

    public Object countorder(Integer userId){
        List<Short> status = new ArrayList<>(Arrays.asList(new Short[]{OrderUtil.STATUS_CREATE, OrderUtil.STATUS_PAY, OrderUtil.STATUS_CONFIRM}));
        return ResponseUtil.ok(orderService.count(userId, status, true));
    }

    public Object countByStatus(Integer userId){
        Map<Object, Object> map = new HashMap<>();
        List<Short> createStatus = new ArrayList<>(Arrays.asList(new Short[]{OrderUtil.STATUS_CREATE}));
        List<Short> payStatus = new ArrayList<>(Arrays.asList(new Short[]{OrderUtil.STATUS_PAY}));
        List<Short> confirmStatus = new ArrayList<>(Arrays.asList(new Short[]{OrderUtil.STATUS_CONFIRM}));
        map.put("1", orderService.count(userId, createStatus, true));
        map.put("2", orderService.count(userId, payStatus, true));
        map.put("4", orderService.count(userId, confirmStatus, true));
        return ResponseUtil.ok(map);
    }

    public Object pay(Integer userId, String body) {
        Integer orderId = JacksonUtil.parseInteger(body, "orderId");
        if(orderId == null){
            return ResponseUtil.badArgument();
        }
        LitemallOrder order = orderService.findByUserAndId(userId, orderId);
        if(order == null){
            return ResponseUtil.fail(ORDER_UNKNOWN, "订单不存在");
        }
        //检测订单是否是门店订单
        if(!order.getShopOrder()){
            return ResponseUtil.fail(ORDER_INVALID_OPERATION, "订单不能支付");
        }
        // 检测是否能够支付
        OrderHandleOption handleOption = OrderUtil.build(order);
        if (!handleOption.isPay()) {
            return ResponseUtil.fail(ORDER_INVALID_OPERATION, "订单不能支付");
        }
        LitemallOrder updater = new LitemallOrder();
        updater.setId(order.getId());
        updater.setOrderStatus(OrderUtil.STATUS_PAY);
        updater.setUpdateTime(order.getUpdateTime());
        orderService.updateWithOptimisticLocker(updater);
        return ResponseUtil.ok();
    }

    public Object complete(Integer userId, String body) {
        Integer orderId = JacksonUtil.parseInteger(body, "orderId");
        if(orderId == null){
            return ResponseUtil.badArgument();
        }
        LitemallOrder order = orderService.findByUserAndId(userId, orderId);
        if(order == null){
            return ResponseUtil.fail(ORDER_UNKNOWN, "订单不存在");
        }
        //检测订单是否是门店订单
        if(!order.getShopOrder()){
            return ResponseUtil.fail(ORDER_INVALID_OPERATION, "订单不能完成");
        }
        // 检测是否能够支付
        if (!order.getOrderStatus().equals(OrderUtil.STATUS_PAY)) {
            return ResponseUtil.fail(ORDER_INVALID_OPERATION, "订单不能完成");
        }
        LitemallOrder updater = new LitemallOrder();
        updater.setId(order.getId());
        updater.setOrderStatus(OrderUtil.STATUS_CONFIRM);
        updater.setUpdateTime(order.getUpdateTime());
        orderService.updateWithOptimisticLocker(updater);
        return ResponseUtil.ok();
    }

    /**
     * 跳过添加购物车、直接下单
     * @param userId
     * @param cart
     * @return
     */
    @Transactional
    public Object orderDirectly(Integer shopId, Integer userId, LitemallCart cart) {
//        Integer shopId = cart.getShopId();
        Object error = validShop(shopId);
        if( error != null){
            return error;
        }
        Integer productId = cart.getProductId();
        Integer number = cart.getNumber().intValue();
        Integer goodsId = cart.getGoodsId();
        Integer[] specIds = cart.getSpecificationIds();
        if (!ObjectUtils.allNotNull(productId, number, goodsId, shopId)) {
            return ResponseUtil.badArgument();
        }
        if(number <= 0){
            return ResponseUtil.badArgument();
        }

        //判断商品是否可以购买
        LitemallGoods goods = goodsService.findById(goodsId);
        if (goods == null || !goods.getIsOnSale()) {
            return ResponseUtil.fail(GOODS_UNSHELVE, "商品已下架");
        }
        LitemallGoodsProduct product = productService.findById(productId);
        List<LitemallGoodsSpecification> specifications = goodsSpecificationService.findByIds(specIds);
        /**
         * 商品价格
         */
        BigDecimal checkedGoodsPrice = new BigDecimal(0.00);
        checkedGoodsPrice = checkedGoodsPrice.add(product.getSellPrice());
        for(LitemallGoodsSpecification sp : specifications){
            checkedGoodsPrice = checkedGoodsPrice.add(sp.getPrice());
        }
        /**
         * 税费
         */
        BigDecimal taxGoodsPrice = checkedGoodsPrice.divide(new BigDecimal(100.00)).multiply(new BigDecimal(product.getNumber()));
        BigDecimal actualPrice = checkedGoodsPrice.add(taxGoodsPrice);

        LitemallOrder order = new LitemallOrder();
        order.setUserId(userId);
        order.setOrderSn(orderService.generateOrderSn(userId));
        order.setOrderStatus(OrderUtil.STATUS_CREATE);
        order.setConsignee("");
        order.setMobile("");
        order.setMessage("");
        order.setAddress("");
        order.setGoodsPrice(checkedGoodsPrice);
        order.setFreightPrice(new BigDecimal(0.00));
        order.setCouponPrice(new BigDecimal(0.00));
        order.setIntegralPrice(new BigDecimal(0.00));
        order.setOrderPrice(new BigDecimal(0.00));
        order.setActualPrice(actualPrice);
        order.setTaxPrice(taxGoodsPrice);
        order.setShopId(shopId);
        order.setOrderType(ORDER_AET);
        order.setShopOrder(true);

        Integer orderId = null;
        // 添加订单表项
        orderService.add(order);
        orderId = order.getId();

        // 订单商品
        LitemallOrderGoods orderGoods = new LitemallOrderGoods();
        orderGoods.setOrderId(order.getId());
        orderGoods.setGoodsId(goods.getId());
        orderGoods.setGoodsSn(goods.getGoodsSn());
        orderGoods.setProductId(productId);
        orderGoods.setGoodsName(goods.getName());
        orderGoods.setPicUrl(goods.getPicUrl());
        orderGoods.setPrice(product.getSellPrice());
        orderGoods.setNumber(number.shortValue());
        List<String> specificationStrs = specifications.stream().map(s -> {
            return s.getSpecification();
        }).collect(Collectors.toList());
        orderGoods.setSpecifications(specificationStrs.toArray(new String[]{}));
        orderGoods.setAddTime(LocalDateTime.now());
        orderGoods.setTaxPrice(product.getTax());

        orderGoodsService.add(orderGoods);


        // 商品货品数量减少
        Integer remainNumber = product.getNumber() - number;
        if (remainNumber < 0) {
            throw new RuntimeException("下单的商品货品数量大于库存量");
        }
        if (productService.reduceStock(productId, number.shortValue()) == 0) {
            throw new RuntimeException("商品货品库存减少失败");
        }
        Map<String, Object> data = new HashMap<>();
        data.put("orderId", orderId);

        return ResponseUtil.ok();
    }
    private Object validShop(Integer shopId){
        if(shopId == null){
            return ResponseUtil.fail(SHOP_NOT_EXSIT, "门店不存在");
        }
        LitemallShop litemallShop = shopService.findById(shopId);
        //判断门店状态
        if(litemallShop == null || !litemallShop.getStatus().equals(Constants.SHOP_STATUS_OPEN)){
            return ResponseUtil.fail(SHOP_UNABLE, "门店未开业");
        }else{
            String closeTime = litemallShop.getCloseTime();
            String openTime = litemallShop.getOpenTime();
            DateTimeFormatter timeDtf = DateTimeFormatter.ofPattern("HH:mm");
            LocalTime startTimes = LocalTime.parse(openTime, timeDtf);
            LocalTime endTime = LocalTime.parse(closeTime, timeDtf);
            LocalTime now = LocalTime.now();
            Integer dayOfWeek = LocalDateTime.now().getDayOfWeek().getValue();
            //判断星期
            if(litemallShop.getWeeks() != null && !Arrays.asList(litemallShop.getWeeks()).contains(dayOfWeek)){
                return ResponseUtil.fail(SHOP_CLOSED, "门店已歇业");
            }
            //判断每天开业时间
            if(now.compareTo(startTimes) != 1 && now.compareTo(endTime) != -1){
                return ResponseUtil.fail(SHOP_CLOSED, "门店已歇业");
            }
        }
/*        if(!Arrays.asList(litemallShop.getTypes()).contains(orderType)){
            return ResponseUtil.fail(SHOP_UNSUPPOT, "不支持该服务");
        }*/
        return null;
    }
}