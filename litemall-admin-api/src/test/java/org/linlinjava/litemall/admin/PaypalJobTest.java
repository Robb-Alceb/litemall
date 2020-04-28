package org.linlinjava.litemall.admin;

import com.paypal.api.payments.Payment;
import com.paypal.base.rest.APIContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.linlinjava.litemall.admin.job.PaypalJob;
import org.mockito.internal.runners.JUnit45AndHigherRunnerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author ：stephen
 * @date ：Created in 2020/4/16 16:23
 * @description：TODO
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class PaypalJobTest {
    @Autowired
    private APIContext apiContext;

    @Test
    public void getPaymentTest() throws Exception{
        String payId = "PAYID-L2L76KI78J880422F307901D";
        Payment payment = Payment.get(apiContext, payId);
        System.out.println(payment.toJSON());
    }
}
