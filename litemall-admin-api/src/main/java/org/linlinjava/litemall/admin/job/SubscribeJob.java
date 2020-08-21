package org.linlinjava.litemall.admin.job;

import com.alibaba.fastjson.JSON;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.admin.beans.Constants;
import org.linlinjava.litemall.admin.beans.bo.AccessoryBo;
import org.linlinjava.litemall.admin.beans.bo.GoodsItemBo;
import org.linlinjava.litemall.admin.beans.bo.SpecificationBo;
import org.linlinjava.litemall.core.notify.NoticeHelper;
import org.linlinjava.litemall.core.notify.NotifyType;
import org.linlinjava.litemall.core.payment.DefaultCurType;
import org.linlinjava.litemall.db.domain.*;
import org.linlinjava.litemall.db.service.*;
import org.linlinjava.litemall.db.util.OrderHandleOption;
import org.linlinjava.litemall.db.util.OrderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ：stephen
 * @date ：Created in 2020/8/11 16:11
 * @description：TODO
 */
@Component
public class SubscribeJob {
    private final Log logger = LogFactory.getLog(SubscribeJob.class);

    @Autowired
    private LitemallOrderService orderService;
    @Autowired
    private LitemallGoodsProductService litemallGoodsProductService;
    @Autowired
    private LitemallUserService userService;
    @Autowired
    private LitemallSubscribeUserService litemallSubscribeUserService;
    @Autowired
    private LitemallGoodsService litemallGoodsService;
    @Autowired
    private LitemallAddressService litemallAddressService;
    @Autowired
    private LitemallOrderGoodsService litemallOrderGoodsService;
    @Autowired
    private LitemallOrderGoodsAccessoryService litemallOrderGoodsAccessoryService;
    @Autowired
    private LitemallShopRegionService litemallShopRegionService;
    @Autowired
    private LitemallTaxService litemallTaxService;
    @Autowired
    private LitemallSubscribeGoodsService litemallSubscribeGoodsService;
    @Autowired
    private LitemallSubscribeGoodsPriceService litemallSubscribeGoodsPriceService;
    @Autowired
    private LitemallOrderTaxService litemallOrderTaxService;
    @Autowired
    private LitemallGoodsAccessoryService litemallGoodsAccessoryService;
    @Autowired
    private LitemallShopMerchandiseService litemallShopMerchandiseService;
    @Autowired
    private NoticeHelper noticeHelper;
    @Autowired
    private LitemallRechargeConsumptionService litemallRechargeConsumptionService;


    /**
     * 每天凌晨三点点执行一次，在自动续订之后执行
     */
    @Scheduled(cron = "0 0 3 * * ?")
//    @Scheduled(cron = "0 0/1 * * * ?")
    public void subscribeOrder(){
        List<LitemallSubscribeUser> subscribeUsers = litemallSubscribeUserService.all();
        for(LitemallSubscribeUser item : subscribeUsers){
            if(checkSubscribOrder(item)){
                logger.info("auto handle subscribe start, id is :" + item.getId());
                LitemallGoods goods = litemallGoodsService.findById(item.getGoodsId());
                LitemallGoodsProduct goodsProduct = litemallGoodsProductService.findById(item.getGoodsProductId());
                String goodsItem = item.getGoodsItem();
                GoodsItemBo goodsItemBo = JSON.parseObject(goodsItem, GoodsItemBo.class);
                Integer orderId = autoSubmitOrder(item, goods, goodsProduct, goodsItemBo);
                try {
                    autoPayment(orderId, item);
                } catch (Exception e) {
                    e.printStackTrace();
                    autoPayFail(orderId, e.getMessage(), goods, item);
                }
            }
        }
    }

    /**
     * 自动下订单
     * @param subscribeUser
     * @param goods
     * @param goodsProduct
     * @param goodsItemBo
     */
    @Transactional
    public Integer autoSubmitOrder(LitemallSubscribeUser subscribeUser, LitemallGoods goods, LitemallGoodsProduct goodsProduct, GoodsItemBo goodsItemBo){
        LitemallOrder order = new LitemallOrder();
        LitemallOrderGoods orderGoods = new LitemallOrderGoods();

        /**
         * 设置订单项
         */
        order.setShopId(subscribeUser.getShopId());
        Integer addressId = subscribeUser.getAddressId();
        LitemallUser user = userService.findById(subscribeUser.getUserId());
        if(addressId != null){
            LitemallAddress address = litemallAddressService.findById(addressId);
            if(address != null){
                String addressStr = address.getCountry() + address.getProvince() + address.getCity() + address.getCounty() + address.getAddressDetail();
                order.setAddress(addressStr);
                order.setMobile(address.getTel());
                order.setConsignee(user.getNickname());
            }
        }else{
            order.setConsignee("");
            order.setMobile("");
            order.setAddress("");
        }
        order.setPayType(subscribeUser.getAutoPayType());
        order.setOrderSource(Constants.ORDER_SOURCE_SUBSCRIBE);
        order.setActualPrice(subscribeUser.getPrice());
        order.setOrderPrice(subscribeUser.getPrice());
        order.setOrderType(subscribeUser.getDeliveryMethod());
        order.setTaxPrice(subscribeUser.getTaxPrice());
        order.setOrderSn(orderService.generateOrderSn(user.getId()));
        order.setReceiptStartTime(LocalDateTime.of(LocalDate.now(), subscribeUser.getDeliveryStartTime()));
        order.setReceiptEndTime(LocalDateTime.of(LocalDate.now(), subscribeUser.getDeliveryEndTime()));
        order.setUserId(user.getId());
        order.setMessage("");
        order.setFreightPrice(new BigDecimal(0.00));
        order.setCouponPrice(new BigDecimal(0.00));
        order.setIntegralPrice(new BigDecimal(0.00));
        order.setOrderStatus(OrderUtil.STATUS_CREATE);
        order.setExternalId(subscribeUser.getId());
        orderService.add(order);
        Integer orderId = order.getId();
        /**
         * 设置订单商品项
         */
        orderGoods.setShopId(subscribeUser.getShopId());
        if(goodsItemBo.getSpecificationDtos() != null){
            orderGoods.setSpecificationIds(goodsItemBo.getSpecificationDtos().stream().map(SpecificationBo::getId).collect(Collectors.toList()).toArray(new Integer[]{}));
            orderGoods.setSpecifications(goodsItemBo.getSpecificationDtos().stream().map(SpecificationBo::getName).collect(Collectors.toList()).toArray(new String[]{}));
        }
        orderGoods.setGoodsId(goods.getId());
        orderGoods.setCategoryId(goods.getCategoryId());
        orderGoods.setGoodsName(goods.getName());
        orderGoods.setGoodsSn(goods.getGoodsSn());
        orderGoods.setOrderId(orderId);
        orderGoods.setProductId(subscribeUser.getGoodsProductId());
        orderGoods.setNumber(subscribeUser.getNumber().shortValue());
        orderGoods.setPicUrl(orderGoods.getPicUrl());
        orderGoods.setPrice(subscribeUser.getPrice());
        orderGoods.setTaxPrice(subscribeUser.getTaxPrice());
        int orderGoodsId = litemallOrderGoodsService.add(orderGoods);
        /**
         * 设置订单商品辅料项
         */
        if(goodsItemBo.getAccessoryDtos() != null){
            for(AccessoryBo accessoryBo : goodsItemBo.getAccessoryDtos()){
                LitemallOrderGoodsAccessory orderGoodsAccessory = new LitemallOrderGoodsAccessory();
                orderGoodsAccessory.setOrderGoodsId(orderGoodsId);
                orderGoodsAccessory.setOrderId(orderId);
                orderGoodsAccessory.setPrice(accessoryBo.getPrice());
                orderGoodsAccessory.setNumber(accessoryBo.getNumber());
                orderGoodsAccessory.setAccessoryId(accessoryBo.getId());
                orderGoodsAccessory.setGoodsId(subscribeUser.getGoodsId());
                litemallOrderGoodsAccessoryService.add(orderGoodsAccessory);
            }
        }
        /**
         * 设置订单税费项
         */
        /**
         * 获取门店对应的税费率
         */
        List<LitemallShopRegion> shopRegions = litemallShopRegionService.queryByShopId(subscribeUser.getShopId());
        List<LitemallTax> litemallTaxes = litemallTaxService.queryByRegionIds(shopRegions.stream().map(LitemallShopRegion::getRegionId).collect(Collectors.toList()));
        BigDecimal tax = new BigDecimal(0.00);
        List<Integer> taxTypes = new ArrayList<>(Arrays.asList(goodsProduct.getTaxTypes()));

        LitemallSubscribeGoods subscribeGoods = litemallSubscribeGoodsService.findByGoodsIdAndSubId(subscribeUser.getGoodsId(), subscribeUser.getSubscribeId());
        BigDecimal subGoodsPrice = getSubGoodsPrice(subscribeGoods.getId(), subscribeUser.getUserId(), subscribeUser.getNumber());
        for(LitemallTax item : litemallTaxes){
            boolean anyMatch = taxTypes.stream().anyMatch(type -> {
                return type == item.getType().intValue();
            });
            if(anyMatch){
                //计算税价
                tax = tax.add(item.getValue().divide(new BigDecimal(100.00)));
                /**
                 * 记录订单税费项
                 */
                LitemallOrderTax orderTax = new LitemallOrderTax();
                orderTax.setCode(item.getCode());
                orderTax.setName(item.getName());
                orderTax.setType(item.getType());
                orderTax.setValue(item.getValue());
                orderTax.setGoodsId(orderGoodsId);
                orderTax.setPrice(tax.multiply(subGoodsPrice));
                orderTax.setOrderGoodsId(orderGoodsId);
                litemallOrderTaxService.add(orderTax);
            }
        }

        // 商品货品数量减少
        LitemallGoodsProduct product = litemallGoodsProductService.findById(subscribeUser.getGoodsProductId());

        Integer remainNumber = product.getNumber() - subscribeUser.getNumber();
        if (remainNumber < 0) {
            throw new RuntimeException("下单的商品货品数量大于库存量");
        }
        if (litemallGoodsProductService.reduceStock(subscribeUser.getGoodsProductId(), subscribeUser.getNumber().shortValue()) == 0) {
            throw new RuntimeException("商品货品库存减少失败");
        }
        // 门店辅料货品数量减少
        if(goodsItemBo.getAccessoryDtos() != null){

            for (AccessoryBo item : goodsItemBo.getAccessoryDtos()) {
                LitemallGoodsAccessory accessory = litemallGoodsAccessoryService.findById(item.getId());
                LitemallShopMerchandise shopMerchandise = litemallShopMerchandiseService.queryByMerId(accessory.getMerchandiseId(), subscribeUser.getShopId());
                LitemallShopMerchandise update = new LitemallShopMerchandise();
                update.setId(shopMerchandise.getId());
                /**
                 * 实际减少的数量 = 订单商品数 * 每个商品选择的辅料数量
                 */
                Integer remainNumber1 = shopMerchandise.getNumber() - item.getNumber() * subscribeUser.getNumber();
                if (remainNumber1 < 0) {
                    throw new RuntimeException("下单的辅料数量大于库存量");
                }
                update.setNumber(shopMerchandise.getNumber() - item.getNumber() * subscribeUser.getNumber());
                litemallShopMerchandiseService.updateById(update);
            }
        }
        return orderId;
    }

    /**
     * 自动支付订单，目前仅支持余额支付
     * @param orderId
     * @param subscribeUser
     * @return
     */
    @Transactional
    public void autoPayment(Integer orderId, LitemallSubscribeUser subscribeUser) throws Exception{
        LitemallOrder order = orderService.findById(orderId);
        // 检测订单是否能够支付
        OrderHandleOption handleOption = OrderUtil.build(order);
        if (!handleOption.isPay()) {
            throw new RuntimeException("订单不能支付");
        }

        LitemallUser user = userService.findById(subscribeUser.getUserId());
        if(user == null){
            throw new RuntimeException("订单不能支付");
        }
        if(user.getAvailableAmount().compareTo(order.getActualPrice()) < 1){
            throw new RuntimeException("用户余额不足");
        }

        if(subscribeUser.getAutoPayType() == Constants.PAY_TYPE_BALANCE){
            LitemallRechargeConsumption litemallRechargeConsumption = saveLog(order, user, Constants.LOG_GIFTCARD_CONSUME);

            order.setPayType(Constants.PAY_TYPE_BALANCE);
            order.setCurrency(DefaultCurType.USD.getType());
            order.setPayTime(LocalDateTime.now());
            order.setOrderStatus(OrderUtil.STATUS_PAY);
            order.setPayId(String.valueOf(litemallRechargeConsumption.getId()));        //将消费日志id作为支付id
            order.setTransationId(String.valueOf(litemallRechargeConsumption.getId())); //将消费日志id作为transation id

            //更新订单
            if (orderService.updateWithOptimisticLocker(order) == 0) {
                // 这里可能存在这样一个问题，用户支付和系统自动取消订单发生在同时
                // 如果数据库首先因为系统自动取消订单而更新了订单状态；
                // 此时用户支付完成回调这里也要更新数据库，而由于乐观锁机制这里的更新会失败
                // 因此，这里会重新读取数据库检查状态是否是订单自动取消，如果是则更新成支付状态。
                order = orderService.findBySn(order.getOrderSn());
                int updated = 0;
                if (OrderUtil.isAutoCancelStatus(order)) {
                    order.setPayTime(LocalDateTime.now());
                    order.setOrderStatus(OrderUtil.STATUS_PAY);
                    updated = orderService.updateWithOptimisticLocker(order);
                }

                // 如果updated是0，那么数据库更新失败
                if (updated == 0) {
                    throw new RuntimeException("订单不能支付");
                }
            }

            //更新用户余额
            LitemallUser update = new LitemallUser();
            update.setId(user.getId());
            update.setAvailableAmount(user.getAvailableAmount().subtract(order.getActualPrice()));
            userService.updateWithOptimisticLocker(update, user.getUpdateTime());
        }else{
            /**
             * TODO 其他方式支付
             */
            throw new RuntimeException("暂不支持的支付方式");
        }


        //发送订单支付成功通知
        noticeHelper.noticeUser(org.linlinjava.litemall.db.beans.Constants.MSG_TYPE_ORDER,"订阅订单", order.getOrderSn()+"订单自动支付成功", subscribeUser.getUserId(), order);
        //发送已支付订单到pos系统，门店开始制作商品
        noticeHelper.noticeShop(org.linlinjava.litemall.db.beans.Constants.MSG_TYPE_ORDER, JSON.toJSONString(order), order.getShopId());
        //发送邮件通知
        LitemallGoods goods = litemallGoodsService.findById(subscribeUser.getGoodsId());
        noticeHelper.notifyMailTemplate("订阅订单", NotifyType.SUBSCRIBE_PAY_SUCCEED, user.getEmail(), new String[]{goods.getName()});
    }

    public void autoPayFail(Integer orderId, String description, LitemallGoods goods, LitemallSubscribeUser item){
        LitemallOrder order = orderService.findById(orderId);
        LitemallOrder update = new LitemallOrder();
        update.setId(orderId);
        update.setDescription(description);
        orderService.updateById(update);
        noticeHelper.noticeUser(org.linlinjava.litemall.db.beans.Constants.MSG_TYPE_ORDER,"订阅订单", order.getOrderSn()+"订单自动支付失败，请手动支付", order.getUserId(), order);
        //发送邮件通知
        LitemallUser user = userService.findById(item.getUserId());
        noticeHelper.notifyMailTemplate("订阅订单", NotifyType.SUBSCRIBE_PAY_ERROR, user.getEmail(), new String[]{goods.getName()});
    }

    /**
     * 根据用户和数量获取订阅价格
     * @param subscribeGoodsId
     * @param userId
     * @param number
     * @return
     */
    public BigDecimal getSubGoodsPrice(Integer subscribeGoodsId, Integer userId, Integer number){
        LitemallSubscribeGoods subscribeGoods = litemallSubscribeGoodsService.findById(subscribeGoodsId);
        List<LitemallSubscribeGoodsPrice> subscribeGoodsPrices = litemallSubscribeGoodsPriceService.queryBySubGoodsId(subscribeGoodsId);
        if(subscribeGoodsPrices != null && subscribeGoodsPrices.size() > 0){
            LitemallUser user = userService.findById(userId);
            List<LitemallSubscribeGoodsPrice> collect = subscribeGoodsPrices.stream().filter(item -> {
                return item.getUserLevel() == null || item.getUserLevel() == user.getUserLevel();
            }).collect(Collectors.toList());
            if(collect != null && collect.size() > 0){
                /**
                 * 将数据按数量倒叙
                 */
                List<LitemallSubscribeGoodsPrice> sortedSubGoods = collect.stream().sorted(Comparator.comparing(LitemallSubscribeGoodsPrice::getNumber).reversed()).collect(Collectors.toList());
                /**
                 * 取最接近的数量的价格,如果没有则取设置的最少数量对应的价格
                 */
                if(number == null || (sortedSubGoods.get(sortedSubGoods.size() -1).getNumber() != null && sortedSubGoods.get(sortedSubGoods.size() -1).getNumber() < number)){
                    return sortedSubGoods.get(sortedSubGoods.size() -1).getPrice();
                }else{
                    for(LitemallSubscribeGoodsPrice i : sortedSubGoods){
                        if(i.getNumber() != null && number > i.getNumber()){
                            return i.getPrice();
                        }
                    }
                }
            }
        }
        return subscribeGoods.getBasePrice();

    }

    /**
     * 检查订阅是否可自动下单
     * @return
     */
    public Boolean checkSubscribOrder(LitemallSubscribeUser subscribeUser){
        Boolean rtn = false;
        Byte method = subscribeUser.getMethod();
        Integer deliveryDays = subscribeUser.getDeliveryDays();
        LocalDateTime startTime = subscribeUser.getStartTime();
        LocalDateTime endTime = subscribeUser.getEndTime();
        /**
         * 检查时间
         */
        if(startTime.isBefore(LocalDateTime.now()) && endTime.isAfter(LocalDateTime.now())){
            if(method == Constants.SUBSCRIBE_METHOD_YEAR){
                int dayOfMonth = LocalDate.now().getDayOfMonth();
                if(deliveryDays == dayOfMonth){
                    rtn =  true;
                }
            }else if(method == Constants.SUBSCRIBE_METHOD_MONTH){
                int dayOfWeek = LocalDate.now().getDayOfWeek().getValue();
                if(deliveryDays == dayOfWeek){
                    rtn = true;
                }
            }else if(method == Constants.SUBSCRIBE_METHOD_WEEK){
                rtn = true;
            }
        }
        /**
         * 检查今天是否已经下过单了
         */
        if(orderService.countTodayByExtendId(subscribeUser.getId()) > 0){
            rtn = false;
        }
        return rtn;
    }

    /**
     * 记录日志并返回
     * @param order
     * @return
     */
    @Transactional
    public LitemallRechargeConsumption saveLog(LitemallOrder order, LitemallUser user, Byte type){
        LitemallRechargeConsumption log = new LitemallRechargeConsumption();
        log.setAmount(order.getActualPrice());
        log.setOrderId(order.getId());
        log.setAddUserId(user.getId());
        log.setUserId(user.getId());
        log.setUsername(user.getUsername());
        log.setMobile(user.getMobile());
        log.setPoints(user.getPoints());
        log.setType(type);
        log.setUserLevel(user.getUserLevel());
        log.setAvailableAmount(user.getAvailableAmount().subtract(order.getActualPrice()));
        litemallRechargeConsumptionService.add(log);
        return log;
    }
}
