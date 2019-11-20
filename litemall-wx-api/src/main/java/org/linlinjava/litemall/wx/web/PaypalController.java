package org.linlinjava.litemall.wx.web;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import org.linlinjava.litemall.wx.config.paypal.PaypalPaymentIntent;
import org.linlinjava.litemall.wx.config.paypal.PaypalPaymentMethod;
import org.linlinjava.litemall.wx.service.PaypalService;
import org.linlinjava.litemall.wx.util.URLUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * @author ：stephen
 * @date ：Created in 11/19/2019 2:14 PM
 * @description：TODO
 */

@RestController
@RequestMapping("/paypal")
public class PaypalController {
    public static final String PAYPAL_SUCCESS_URL = "paypal/success";
    public static final String PAYPAL_CANCEL_URL = "paypal/cancel";

    private Log log = LogFactory.getLog(PaypalController.class);

    @Autowired
    private PaypalService paypalService;


    @PostMapping("/pay")
    public String pay(HttpServletRequest request){
        String cancelUrl = URLUtils.getBaseURl(request) + "/" + PAYPAL_CANCEL_URL;
        String successUrl = URLUtils.getBaseURl(request) + "/" + PAYPAL_SUCCESS_URL;
        try {
            Payment payment = paypalService.createPayment(
                    1.00,
                    "USD",
                    PaypalPaymentMethod.paypal,
                    PaypalPaymentIntent.sale,
                    "payment description",
                    cancelUrl,
                    successUrl);
            for(Links links : payment.getLinks()){
                if(links.getRel().equals("approval_url")){
                    return "redirect:" + links.getHref();
                }
            }
        } catch (PayPalRESTException e) {
            log.error(e.getMessage());
        }
        return "redirect:/";
    }

    @GetMapping(PAYPAL_CANCEL_URL)
    public String cancelPay(){
        return "cancel";
    }

    @GetMapping(PAYPAL_SUCCESS_URL)
    public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId){
        try {
            Payment payment = paypalService.executePayment(paymentId, payerId);
            if(payment.getState().equals("approved")){
                return "success";
            }
        } catch (PayPalRESTException e) {
            log.error(e.getMessage());
        }
        return "redirect:/";
    }
}
