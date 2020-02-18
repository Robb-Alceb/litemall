package org.linlinjava.litemall.web.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.beans.Constants;
import org.linlinjava.litemall.db.domain.*;
import org.linlinjava.litemall.db.service.*;
import org.linlinjava.litemall.db.util.OrderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.linlinjava.litemall.web.util.WebResponseCode.*;

/**
 * @author ：stephen
 * @date ：Created in 2020/2/17 15:24
 * @description：购物车service
 */
@Service
public class WebCartService {
    private final Log logger = LogFactory.getLog(WebCartService.class);

    @Autowired
    private LitemallCartService cartService;
    @Autowired
    private LitemallGoodsService goodsService;
    @Autowired
    private LitemallGoodsProductService productService;
    @Autowired
    private LitemallGoodsSpecificationService specificationService;
    @Autowired
    private LitemallGoodsSpecificationService goodsSpecificationService;
    @Autowired
    private LitemallOrderService orderService;
    @Autowired
    private LitemallOrderGoodsService orderGoodsService;

    public Object checkout(Integer userId, Integer cartId){

        // 商品价格
        List<LitemallCart> checkedGoodsList = null;
        if (cartId == null || cartId.equals(0)) {
            checkedGoodsList = cartService.queryByUidAndChecked(userId);
        } else {
            LitemallCart cart = cartService.findById(cartId);
            if (cart == null) {
                return ResponseUtil.badArgumentValue();
            }
            checkedGoodsList = new ArrayList<>(1);
            checkedGoodsList.add(cart);
        }
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


        // 获取优惠券减免金额，优惠券可用数量
        BigDecimal couponPrice = new BigDecimal(0);

        BigDecimal freightPrice = new BigDecimal(0.00);

        // 可以使用的其他钱，例如用户积分
        BigDecimal integralPrice = new BigDecimal(0.00);

        // 订单费用
        BigDecimal orderTotalPrice = checkedGoodsPrice.add(freightPrice).subtract(couponPrice).max(new BigDecimal(0.00));

        BigDecimal actualPrice = orderTotalPrice.subtract(integralPrice);

        Integer orderId = null;
        LitemallOrder order = null;
        // 订单
        order = new LitemallOrder();
        order.setUserId(userId);
        order.setOrderSn(orderService.generateOrderSn(userId));
        order.setOrderStatus(OrderUtil.STATUS_CREATE);
        order.setGoodsPrice(checkedGoodsPrice);
        order.setFreightPrice(new BigDecimal(0.00));
        order.setCouponPrice(couponPrice);
        order.setIntegralPrice(integralPrice);
        order.setOrderPrice(orderTotalPrice);
        order.setActualPrice(actualPrice);
        order.setTaxPrice(taxGoodsPrice);
        if(null != checkedGoodsList && checkedGoodsList.size() > 0){
            order.setShopId(checkedGoodsList.get(0).getShopId());
        }
        order.setOrderType(Constants.ORDER_AET);
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

        return ResponseUtil.ok(data);
    }
}
