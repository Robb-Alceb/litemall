package org.linlinjava.litemall.core;

import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.base.rest.APIContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.linlinjava.litemall.core.payment.paypal.service.impl.GoodsPaypalServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * @author ：stephen
 * @date ：Created in 11/25/2019 4:47 PM
 * @description：TODO
 */

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class PaypalTest {
    @Autowired
    private GoodsPaypalServiceImpl paypalService;
    @Autowired
    private APIContext apiContext;

    @Test
    public void testPayment(){
        String successUrl = "http://192.168.0.101:8080/paypal/success";
        String cancelUrl = "http://192.168.0.101:8080/paypal/cancelUrl";
        paypalService.getPayment(1,1, successUrl, cancelUrl);
    }
    @Test
    public void executePayment() throws Exception{
        String paymentId = "PAYID-L2DOUBQ7U667059LY8667941";
        String payerId = "SGG2NS9TFFCKL";
        Payment payment = new Payment();
        payment.setId(paymentId);
        PaymentExecution paymentExecute = new PaymentExecution();
        paymentExecute.setPayerId(payerId);
        Payment rtn = payment.execute(apiContext, paymentExecute);
        System.out.println(rtn.toJSON().toString());
    }



}
