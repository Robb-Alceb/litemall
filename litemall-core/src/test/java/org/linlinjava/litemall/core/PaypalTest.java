package org.linlinjava.litemall.core;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.linlinjava.litemall.core.payment.paypal.service.PaypalService;
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
    private PaypalService paypalService;

    @Test
    public void testPayment(){
        String successUrl = "http://192.168.0.101:8080/paypal/success";
        String cancelUrl = "http://192.168.0.101:8080/paypal/cancelUrl";
        paypalService.getPayment(1,1, successUrl, cancelUrl);
    }
}
