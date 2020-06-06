package org.linlinjava.litemall.wx.service;

import com.alibaba.fastjson.JSON;
import org.linlinjava.litemall.core.notify.NoticeHelper;
import org.linlinjava.litemall.core.payment.DefaultCurType;
import org.linlinjava.litemall.core.payment.PaymentResponseCode;
import org.linlinjava.litemall.core.util.JacksonUtil;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.beans.Constants;
import org.linlinjava.litemall.db.domain.LitemallGiftCardUser;
import org.linlinjava.litemall.db.domain.LitemallGiftCardUserLog;
import org.linlinjava.litemall.db.domain.LitemallOrder;
import org.linlinjava.litemall.db.service.LitemallGiftCardUserLogService;
import org.linlinjava.litemall.db.service.LitemallGiftCardUserService;
import org.linlinjava.litemall.db.service.LitemallOrderService;
import org.linlinjava.litemall.db.util.OrderHandleOption;
import org.linlinjava.litemall.db.util.OrderUtil;
import org.linlinjava.litemall.wx.util.WxResponseEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * @author ：stephen
 * @date ：Created in 2020/4/9 10:41
 * @description：TODO
 */
@Service
public class WxCardPayService {
    @Autowired
    private LitemallOrderService litemallOrderService;
    @Autowired
    private LitemallGiftCardUserService litemallGiftCardUserService;
    @Autowired
    private LitemallGiftCardUserLogService litemallGiftCardUserLogService;
    @Autowired
    private NoticeHelper noticeHelper;


    /**
     * 礼物卡支付订单
     * @param userId
     * @param body
     * @return
     */
    @Transactional
    public Object pay(Integer userId, String body){
        Integer orderId = JacksonUtil.parseInteger(body, "orderId");
        Integer cardId = JacksonUtil.parseInteger(body, "cardId");
        if(orderId == null){
            return ResponseUtil.badArgument(WxResponseEnum.BAD_ARGUMENT_ORDERID);
        }
        if(cardId == null){
            return ResponseUtil.badArgument(WxResponseEnum.BAD_ARGUMENT_CARDID);
        }

        LitemallOrder order = litemallOrderService.findById(orderId);
        // 检测订单是否能够支付
        OrderHandleOption handleOption = OrderUtil.build(order);
        if (!handleOption.isPay()) {
            return ResponseUtil.fail(PaymentResponseCode.ORDER_INVALID_OPERATION, "订单不能支付");
        }

        LitemallGiftCardUser card = litemallGiftCardUserService.findById(cardId);
        if(card == null || card.getUserId() != userId){
            return ResponseUtil.badArgument(WxResponseEnum.IS_NULL_CARD);
        }
        if(card.getAmount().compareTo(order.getActualPrice()) < 1){
            return ResponseUtil.badArgument(WxResponseEnum.NOT_ENOUGH_AMOUNT);
        }

        // 检查这个订单是否已经处理过
        if (OrderUtil.isPayStatus(order) || order.getPayId() != null) {
            return ResponseUtil.fail(PaymentResponseCode.PAYMENT_FAIL, order.getOrderSn() + "订单已经处理成功!");
        }

        //记录消费情况
        LitemallGiftCardUserLog log = saveLog(order, card, userId, "消费");


        order.setPayType(Constants.PAY_TYPE_GIFT_CARD);
        order.setCurrency(DefaultCurType.USD.getType());
        order.setPayTime(LocalDateTime.now());
        order.setOrderStatus(OrderUtil.STATUS_PAY);
        order.setPayId(String.valueOf(log.getId()));        //将消费日志id作为支付id
        order.setTransationId(String.valueOf(log.getId())); //将消费日志id作为transation id

        //更新订单
        if (litemallOrderService.updateWithOptimisticLocker(order) == 0) {
            // 这里可能存在这样一个问题，用户支付和系统自动取消订单发生在同时
            // 如果数据库首先因为系统自动取消订单而更新了订单状态；
            // 此时用户支付完成回调这里也要更新数据库，而由于乐观锁机制这里的更新会失败
            // 因此，这里会重新读取数据库检查状态是否是订单自动取消，如果是则更新成支付状态。
            order = litemallOrderService.findBySn(order.getOrderSn());
            int updated = 0;
            if (OrderUtil.isAutoCancelStatus(order)) {
                order.setPayTime(LocalDateTime.now());
                order.setOrderStatus(OrderUtil.STATUS_PAY);
                updated = litemallOrderService.updateWithOptimisticLocker(order);
            }

            // 如果updated是0，那么数据库更新失败
            if (updated == 0) {
                return ResponseUtil.updatedDateExpired();
            }
        }


        //更新卡余额
        LitemallGiftCardUser giftCard = new LitemallGiftCardUser();
        giftCard.setAmount(card.getAmount().subtract(order.getActualPrice()));
        giftCard.setId(card.getId());
        if(litemallGiftCardUserService.updateWithOptimisticLocker(giftCard, card.getUpdateTime()) == 0){
            return ResponseUtil.updatedDateExpired();
        }

//        notifyService.notifyMail("新订单", order.toString());
        //发送订单支付成功通知
        noticeHelper.noticeUser(Constants.MSG_TYPE_ORDER, order.getOrderSn()+"订单支付成功", userId);
        //发送已支付订单到pos系统，门店开始制作商品
        noticeHelper.noticeShop(Constants.MSG_TYPE_ORDER, JSON.toJSONString(order), order.getShopId());
        //发送礼物卡消费通知
        noticeHelper.noticeUser( Constants.MSG_TYPE_OTHER, "您的卡"+card.getCardNumber()+"消费：$"+order.getActualPrice(), userId);

        return ResponseUtil.ok();
    }

    /**
     * 记录日志并返回
     * @param order
     * @param card
     * @param userId
     * @return
     */
    @Transactional
    public LitemallGiftCardUserLog saveLog( LitemallOrder order, LitemallGiftCardUser card, Integer userId, String content){
        LitemallGiftCardUserLog log = new LitemallGiftCardUserLog();
        log.setOrderId(order.getId());
        log.setAmount(order.getActualPrice());
        log.setAddUserId(userId);
        log.setType(Constants.LOG_GIFTCARD_CONSUME);
        log.setCardNumber(card.getCardNumber());
        log.setContent(content);
        litemallGiftCardUserLogService.add(log);
        return log;
    }

}
