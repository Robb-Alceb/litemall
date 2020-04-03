package org.linlinjava.litemall.core.payment.paypal.service;

import com.paypal.base.rest.PayPalRESTException;
import org.linlinjava.litemall.db.domain.LitemallOrder;

/**
 * @author ：stephen
 * @date ：Created in 2020/4/2 16:20
 * @description：TODO
 */
public interface PaypalService {
    Object getPayment(Integer userId, Integer orderId, String successUrl, String cancelUrl);

    Object executePayment(String paymentId, String payerId) throws PayPalRESTException;

    boolean refund(Integer orderId);
}
