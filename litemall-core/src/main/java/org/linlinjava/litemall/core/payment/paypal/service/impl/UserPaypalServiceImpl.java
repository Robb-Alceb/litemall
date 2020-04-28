package org.linlinjava.litemall.core.payment.paypal.service.impl;

import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.core.payment.DefaultCurType;
import org.linlinjava.litemall.core.payment.PaymentResponseCode;
import org.linlinjava.litemall.core.payment.paypal.config.PaypalPaymentIntent;
import org.linlinjava.litemall.core.payment.paypal.config.PaypalPaymentMethod;
import org.linlinjava.litemall.core.payment.paypal.config.PaypalPaymentState;
import org.linlinjava.litemall.core.payment.paypal.service.PaypalService;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.beans.Constants;
import org.linlinjava.litemall.db.domain.LitemallUserRechargeOrder;
import org.linlinjava.litemall.db.service.LitemallUserRechargeOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ：stephen
 * @date ：Created in 2020/4/9 17:43
 * @description：TODO
 */
@Service
public class UserPaypalServiceImpl implements PaypalService {
    private final Log logger = LogFactory.getLog(UserPaypalServiceImpl.class);

    @Autowired
    private APIContext apiContext;
    @Autowired
    private LitemallUserRechargeOrderService litemallUserRechargeOrderService;


    @Override
    public Object getPayment(Integer userId, Integer orderId, String successUrl, String cancelUrl) {
        LitemallUserRechargeOrder order = litemallUserRechargeOrderService.findById(orderId);
        if(order == null){
            return ResponseUtil.fail(PaymentResponseCode.ORDER_UNKNOWN, "订单不存在");
        }
        if(order.getUserId() != userId){
            return ResponseUtil.fail(PaymentResponseCode.ORDER_INVALID_OPERATION, "订单不能支付");
        }
        if(order.getPayStatus() != null){
            return ResponseUtil.fail(PaymentResponseCode.ORDER_INVALID_OPERATION, "订单不能支付");
        }
        BigDecimal total = order.getAmount();
        Amount amount = new Amount();
        amount.setCurrency(DefaultCurType.USD.getType());
        amount.setTotal(String.format("%.2f", total));

        Transaction transaction = new Transaction();
        transaction.setDescription("lumiere gift card pay");
        transaction.setAmount(amount);


        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        Payer payer = new Payer();
        payer.setPaymentMethod(PaypalPaymentMethod.paypal.toString());

        Payment payment = new Payment();
        payment.setIntent(PaypalPaymentIntent.sale.toString());
        payment.setPayer(payer);
        payment.setTransactions(transactions);
        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(cancelUrl);
        redirectUrls.setReturnUrl(successUrl);
        payment.setRedirectUrls(redirectUrls);


        try {
            logger.debug("success url is " + redirectUrls.getReturnUrl());
            logger.debug("cancel url is " + redirectUrls.getCancelUrl());
            Payment rtn =  payment.create(apiContext);
            if(null == rtn.getId() || !PaypalPaymentState.created.toString().equals(rtn.getState())){
                return ResponseUtil.fail(PaymentResponseCode.ORDER_INVALID_OPERATION, "订单不能支付");
            }
            String paymentId = rtn.getId();
            LitemallUserRechargeOrder update = new LitemallUserRechargeOrder();
            //保存paymentId作为PayPal的商户订单号
            update.setOutTradeNo(paymentId);
            //状态为进行中
            update.setPayStatus(Constants.PAY_STATUS_DOING);
            if (litemallUserRechargeOrderService.paddingPay(update, order) == 0) {
                return ResponseUtil.updatedDateExpired();
            }
            return rtn;
        } catch (PayPalRESTException e) {
            e.printStackTrace();
            return ResponseUtil.fail(PaymentResponseCode.ORDER_INVALID_OPERATION, "订单不能支付");
        }
    }

    @Override
    public Object executePayment(String paymentId, String payerId) throws PayPalRESTException {
        Payment payment = new Payment();
        payment.setId(paymentId);
        PaymentExecution paymentExecute = new PaymentExecution();
        paymentExecute.setPayerId(payerId);
        Payment rtn = payment.execute(apiContext, paymentExecute);

        // 交易号
        String transationId = rtn.getTransactions().get(0).getRelatedResources().get(0).getSale().getId();
        if(rtn.getState().equals("approved")){
            LitemallUserRechargeOrder order = litemallUserRechargeOrderService.findByOutTradeNo(paymentId);
            List<Transaction> transactions = rtn.getTransactions();
            BigDecimal totalFee = new BigDecimal(0.00);
            if(transactions != null){
                for(Transaction transaction: transactions){
                    String total = transaction.getAmount().getTotal();
                    BigDecimal b = new BigDecimal(total);
                    b.setScale(2, BigDecimal.ROUND_DOWN);
                    totalFee = totalFee.add(b);
                }
            }
            if (order == null) {
                return ResponseUtil.fail(PaymentResponseCode.ORDER_UNKNOWN, "订单不存在");
            }

            // 检查这个订单是否已经处理过
            if (order.getPayStatus() == Constants.PAY_STATUS_DONE || order.getPayId() != null) {
                return ResponseUtil.fail(PaymentResponseCode.PAYMENT_FAIL, "订单已经处理成功!");
            }

            // 检查支付订单金额
            if (!totalFee.toString().equals(order.getAmount().toString())) {
                return ResponseUtil.fail(PaymentResponseCode.PAYMENT_FAIL, " : 支付金额不符合 totalFee=" + totalFee);
            }

            LitemallUserRechargeOrder update = new LitemallUserRechargeOrder();
            update.setPayType(Constants.PAY_TYPE_PAYPAL);
            update.setCurrency(DefaultCurType.USD.getType());
            update.setTransationId(transationId);        //交易号，退款时需要用到
            update.setPayId(payment.getId());            //付款编号，获取详情是需要用到？
            update.setPayerId(payerId);
            update.setPayTime(LocalDateTime.now());
            update.setPayStatus(Constants.PAY_STATUS_DONE);

            if (litemallUserRechargeOrderService.payDone(order, update) == 0) {
                return ResponseUtil.updatedDateExpired();
            }

            return rtn;
        }else{
            return ResponseUtil.fail(PaymentResponseCode.PAYMENT_FAIL, "支付失败");
        }
    }

    @Override
    public boolean refund(Integer orderId) {
        LitemallUserRechargeOrder order = litemallUserRechargeOrderService.findById(orderId);
        // ###Sale

        // ###Refund
        RefundRequest refund = new RefundRequest();
        // ###Amount
        Amount amount = new Amount();
        amount.setCurrency(order.getCurrency());
        amount.setTotal(String.format("%.2f", order.getAmount()));
        refund.setAmount(amount);

        try {
            if(StringUtils.isEmpty(order.getTransationId())) {
                //注意这段代码，获取saleId
                Payment payment = Payment.get(apiContext, order.getPayId());
                Transaction transaction = payment.getTransactions().get(0);
                RelatedResources resources = transaction.getRelatedResources().get(0);
                String id = resources.getSale().getId();
                order.setTransationId(id);
            }

            Sale sale = new Sale();
            sale.setId(order.getTransationId());
            DetailedRefund res = sale.refund(apiContext, refund);
            if(res.getState().equals(PaypalPaymentState.completed.toString())){
                return true;
            }
            return false;
        } catch (PayPalRESTException e) {
            e.printStackTrace();
            return false;
        }
    }
}
