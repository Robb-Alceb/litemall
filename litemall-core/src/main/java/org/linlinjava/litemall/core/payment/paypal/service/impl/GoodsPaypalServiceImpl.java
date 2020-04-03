package org.linlinjava.litemall.core.payment.paypal.service.impl;

import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.core.notify.NotifyService;
import org.linlinjava.litemall.core.payment.DefaultCurType;
import org.linlinjava.litemall.core.payment.PaymentResponseCode;
import org.linlinjava.litemall.core.payment.paypal.service.PaypalService;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.beans.Constants;
import org.linlinjava.litemall.db.domain.LitemallOrder;
import org.linlinjava.litemall.db.domain.LitemallUserFormid;
import org.linlinjava.litemall.db.service.LitemallOrderService;
import org.linlinjava.litemall.db.service.LitemallUserFormIdService;
import org.linlinjava.litemall.db.util.OrderHandleOption;
import org.linlinjava.litemall.db.util.OrderUtil;
import org.linlinjava.litemall.core.payment.paypal.config.PaypalPaymentIntent;
import org.linlinjava.litemall.core.payment.paypal.config.PaypalPaymentMethod;
import org.linlinjava.litemall.core.payment.paypal.config.PaypalPaymentState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


/**
 * @author ：stephen
 * @date ：Created in 11/19/2019 2:02 PM
 * @description：paypeal服务
 */
@Service
public class GoodsPaypalServiceImpl implements PaypalService {
    private final Log logger = LogFactory.getLog(GoodsPaypalServiceImpl.class);

    @Autowired
    private APIContext apiContext;
    @Autowired
    private LitemallOrderService orderService;
    @Autowired
    private LitemallUserFormIdService formIdService;
    @Autowired
    private NotifyService notifyService;


    public Object getPayment(Integer userId, Integer orderId, String successUrl, String cancelUrl){
        LitemallOrder litemallOrder = orderService.findByUserAndId(userId, orderId);

        // 检测订单是否能够支付
        OrderHandleOption handleOption = OrderUtil.build(litemallOrder);
        if (!handleOption.isPay()) {
            return ResponseUtil.fail(PaymentResponseCode.ORDER_INVALID_OPERATION, "订单不能支付");
        }
        BigDecimal total = litemallOrder.getActualPrice();
        BigDecimal tax = litemallOrder.getTaxPrice();
        BigDecimal shipping = litemallOrder.getFreightPrice();
        Amount amount = new Amount();
        amount.setCurrency(DefaultCurType.USD.getType());
        amount.setTotal(String.format("%.2f", total.add(tax).add(shipping)));
        Details details = new Details();
        /**
         * 退款金额
         */
        details.setFee(String.format("%.2f", total));
        /**
         * 税费
         */

        details.setTax(String.format("%.2f", tax));
        /**
         * 运费
         */
        details.setShipping(String.format("%.2f", shipping));

//        amount.setDetails(details);
        Transaction transaction = new Transaction();
        transaction.setDescription(litemallOrder.getDescription());
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
            //保存paymentId作为PayPal的商户订单号
            litemallOrder.setOutTradeNo(paymentId);
            if (orderService.updateWithOptimisticLocker(litemallOrder) == 0) {
                return ResponseUtil.updatedDateExpired();
            }
            //缓存paymentId用于后续模版通知

            LitemallUserFormid userFormid = new LitemallUserFormid();
            userFormid.setFormid(paymentId);
            userFormid.setIsprepay(true);
            userFormid.setUseamount(3);
            userFormid.setOpenid("");
            userFormid.setExpireTime(LocalDateTime.now().plusDays(7));
            formIdService.addUserFormid(userFormid);

            return rtn;
        } catch (PayPalRESTException e) {
            e.printStackTrace();
            return ResponseUtil.fail(PaymentResponseCode.ORDER_INVALID_OPERATION, "订单不能支付");
        }
    }

    public Object executePayment(String paymentId, String payerId) throws PayPalRESTException{
        Payment payment = new Payment();
        payment.setId(paymentId);
        PaymentExecution paymentExecute = new PaymentExecution();
        paymentExecute.setPayerId(payerId);
         Payment rtn = payment.execute(apiContext, paymentExecute);

        // 交易号
        String transationId = rtn.getTransactions().get(0).getRelatedResources().get(0).getSale().getId();
        if(rtn.getState().equals("approved")){
            LitemallOrder order = orderService.findByOutTradeNo(paymentId);
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
            if (OrderUtil.isPayStatus(order) || order.getPayId() != null) {
                return ResponseUtil.fail(PaymentResponseCode.PAYMENT_FAIL, order.getOrderSn() + "订单已经处理成功!");
            }

            // 检查支付订单金额
            if (!totalFee.toString().equals(order.getActualPrice().toString())) {
                return ResponseUtil.fail(PaymentResponseCode.PAYMENT_FAIL, order.getOrderSn() + " : 支付金额不符合 totalFee=" + totalFee);
            }

            order.setPayType(Constants.PAY_TYPE_PAYPAL);
            order.setCurrency(DefaultCurType.USD.getType());
            order.setTransationId(transationId);        //交易号，退款时需要用到
            order.setPayId(payment.getId());            //付款编号，获取详情是需要用到？
            order.setPayTime(LocalDateTime.now());
            order.setOrderStatus(OrderUtil.STATUS_PAY);

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
                    return ResponseUtil.updatedDateExpired();
                }
            }

            //TODO 发送邮件和短信通知，这里采用异步发送
            // 订单支付成功以后，会发送短信给用户，以及发送邮件给管理员
            notifyService.notifyMail("新订单通知", order.toString());
            // 这里微信的短信平台对参数长度有限制，所以将订单号只截取后6位
//            notifyService.notifySmsTemplateSync(order.getMobile(), NotifyType.PAY_SUCCEED, new String[]{order.getOrderSn().substring(8, 14)});

            return rtn;
        }else{
            return ResponseUtil.fail(PaymentResponseCode.PAYMENT_FAIL, "支付失败");
        }
    }

    /**
     *
     * @param orderId
     * @return  false:退款失败； true:退款成功
     */
    public boolean refund(Integer orderId) {
        LitemallOrder order = orderService.findById(orderId);
        // ###Sale
        // A sale transaction.
        // Create a Sale object with the
        // given sale transaction id.

        // ###Refund
        // A refund transaction.
        // Use the amount to create
        // a refund object
        RefundRequest refund = new RefundRequest();
        // ###Amount
        // Create an Amount object to
        // represent the amount to be
        // refunded. Create the refund object, if the refund is partial
        Amount amount = new Amount();
        amount.setCurrency(order.getCurrency());
        amount.setTotal(String.format("%.2f", order.getActualPrice()));
        refund.setAmount(amount);
        refund.setReason(order.getDescription());


        try {
            if(StringUtils.isEmpty(order.getTransationId())) {
                //注意这段代码，获取saleId
                Payment payment = Payment.get(apiContext, order.getPayId());
                Transaction transaction = payment.getTransactions().get(0);
                RelatedResources resources = transaction.getRelatedResources().get(0);
                String id = resources.getSale().getId();
                order.setTransationId(id);
            }

            // ### Api Context
            // Pass in a `ApiContext` object to authenticate
            // the call and to send a unique request id
            // (that ensures idempotency). The SDK generates
            // a request id if you do not pass one explicitly.

            // Refund by posting to the APIService
            // using a valid AccessToken
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
