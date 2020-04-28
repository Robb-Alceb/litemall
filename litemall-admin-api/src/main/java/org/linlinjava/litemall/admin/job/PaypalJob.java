package org.linlinjava.litemall.admin.job;

import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.core.notify.NotifyService;
import org.linlinjava.litemall.core.payment.paypal.service.impl.CardPaypalServiceImpl;
import org.linlinjava.litemall.core.system.SystemConfig;
import org.linlinjava.litemall.core.util.GeneratorUtil;
import org.linlinjava.litemall.db.beans.Constants;
import org.linlinjava.litemall.db.domain.*;
import org.linlinjava.litemall.db.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.*;
import java.util.List;

/**
 * @author ：stephen
 * @date ：Created in 2020/4/7 9:49
 * @description：检测订单paypal付款状态
 */
//@Component
public class PaypalJob {
    private static final Log log = LogFactory.getLog(PaypalJob.class);
    @Autowired
    private APIContext apiContext;
    @Autowired
    private LitemallOrderService litemallOrderService;
    @Autowired
    private LitemallGiftCardOrderService litemallGiftCardOrderService;
    @Resource
    private LitemallGiftCardShareService litemallGiftCardShareService;
    @Autowired
    private CardPaypalServiceImpl cardPaypalService;
    @Autowired
    private NotifyService notifyService;
    @Autowired
    private LitemallUserService litemallUserService;
    @Autowired
    private LitemallGiftCardUserService litemallGiftCardUserService;
    @Autowired
    private LitemallGiftCardUserLogService litemallGiftCardUserLogService;

//    @Scheduled(fixedDelay = 24 * 60 * 60 * 1000)
    @Transactional(rollbackFor = Exception.class)
    public void goodsOrderCheck() throws Exception{
        log.info("start goodsOrderCheck");
        LocalDateTime start = LocalDateTime.of(LocalDate.now().plusDays(-30), LocalTime.MIN);
        LocalDateTime end = LocalDateTime.of(LocalDate.now().plusDays(-1), LocalTime.MAX);
        List<LitemallOrder> litemallOrders = litemallOrderService.queryTime(start, end);
        for(LitemallOrder order : litemallOrders){

        }
    }

//    @Scheduled(fixedDelay = 30 * 60 * 1000)
    @Transactional(rollbackFor = Exception.class)
    public void cardOrderCheck(){
        log.info("start goodsOrderCheck");
        /**
         * 获取可能支付的订单
         */
        LocalDateTime end = LocalDateTime.now().minusMinutes(SystemConfig.getOrderUnpaid());
        List<LitemallGiftCardOrder> orders = litemallGiftCardOrderService.getDoingOrder(end);
        for(LitemallGiftCardOrder order : orders){
            /**
             * 从paypal获取订单详情
             */
            Payment payment = getPayment(order.getOutTradeNo());
            if(payment != null){
                /**
                 * 更新订单状态
                 */
                LitemallGiftCardOrder update = new LitemallGiftCardOrder();
                update.setPayStatus(Constants.PAY_STATUS_DONE);
                update.setPayId(payment.getId());
                litemallGiftCardOrderService.payDone(order, update);
                /**
                 * 更新礼物卡余额或者创建新的礼物卡
                 */
                createOrUpdateCard(order);
            }
            log.info(payment.toJSON());
        }

    }


    private Payment getPayment(String paymentId){
        //注意这段代码，获取saleId
        try {
            return Payment.get(apiContext, paymentId);
        } catch (PayPalRESTException e) {
            e.printStackTrace();
            return null;
        }
    }
    public void createOrUpdateCard(LitemallGiftCardOrder order){
        if(StringUtils.isEmpty(order.getCardNumber())){
            LitemallGiftCardUser card = new LitemallGiftCardUser();
            card.setUserId(order.getUserId());
            card.setAmount(order.getAmount());
            card.setGiftCardId(order.getGiftCardId());
            LitemallUser user = litemallUserService.findById(order.getUserId());
            if(user != null){
                card.setUserName(user.getUsername());
            }
            card.setCardNumber(GeneratorUtil.cardNumberGenerator("CG",order.getUserId()));
            try{
                //插入用户礼物卡
                litemallGiftCardUserService.add(card);
                //修改订单的cardNumber
                LitemallGiftCardOrder update = new LitemallGiftCardOrder();
                update.setId(order.getId());
                update.setCardNumber(card.getCardNumber());
                litemallGiftCardOrderService.update(update);
                log(order.getCardNumber(), Constants.LOG_GIFTCARD_PICK, "领取礼物卡",order.getUserId());
                log(order.getCardNumber(), Constants.LOG_GIFTCARD_RECHARGE, "充值礼物卡",order.getUserId(),order.getAmount(),order.getId());
            }catch (Exception e){
                log.info("Exception in createCard:"+ e.getMessage());
                //当cardNumber冲突时重新插入
                createOrUpdateCard(order);
            }
            //修改卡余额
        }else{
            LitemallGiftCardUser card = litemallGiftCardUserService.findByNumber(order.getCardNumber());
            card.setAmount(card.getAmount().add(order.getAmount()));
            litemallGiftCardUserService.updateWithOptimisticLocker(card, card.getUpdateTime());
            log(order.getCardNumber(), Constants.LOG_GIFTCARD_PICK, "领取礼物卡",order.getUserId());
        }
    }
    /**
     * 记录礼物卡日志(充值、消费、赠送、领取、销毁)
     * @param cardNumber
     * @param type
     * @param content
     * @param userId
     */
    public void log(String cardNumber, Byte type, String content, Integer userId){
        log(cardNumber,type,content,userId,null);
    }
    public void log(String cardNumber, Byte type, String content, Integer userId, BigDecimal amount){
        log(cardNumber,type,content,userId,amount, null);
    }

    public void log(String cardNumber, Byte type, String content, Integer userId, BigDecimal amount, Integer orderId){
        LitemallUser user = litemallUserService.findById(userId);
        LitemallGiftCardUserLog log = new LitemallGiftCardUserLog();
        log.setCardNumber(cardNumber);
        log.setType(type);
        log.setAddUserId(userId);
        log.setContent(content);
        log.setAmount(amount);
        log.setOrderId(orderId);
        if(user != null){
            log.setContent(user.getUsername() + ":" + content);
            log.setAddUserName(user.getUsername());
        }
        litemallGiftCardUserLogService.add(log);
    }

}
