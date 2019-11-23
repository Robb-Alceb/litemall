package org.linlinjava.litemall.wx.web;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.wx.annotation.LoginUser;
import org.linlinjava.litemall.core.payment.paypal.service.PaypalService;
import org.linlinjava.litemall.wx.util.URLUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * @author ：stephen
 * @date ：Created in 11/19/2019 2:14 PM
 * @description：TODO
 */

@Controller
public class PaypalController {
    public static final String PAYPAL_SUCCESS_URL = "paypal/success";
    public static final String PAYPAL_CANCEL_URL = "paypal/cancel";

    private Log log = LogFactory.getLog(PaypalController.class);

    @Autowired
    private PaypalService paypalService;


    @PostMapping("/paypal/pay")
    public Object pay(HttpServletRequest request, @LoginUser Integer userId, Integer orderId){
        String cancelUrl = URLUtils.getBaseURl(request) + "/" + PAYPAL_CANCEL_URL;
        String successUrl = URLUtils.getBaseURl(request) + "/" + PAYPAL_SUCCESS_URL;
        Object obj =  paypalService.getPayment(userId, orderId, successUrl, cancelUrl);
        if(!(obj instanceof Payment)){
            return obj;
        }
        Payment payment = (Payment)obj;
        for(Links links : payment.getLinks()){
            if(links.getRel().equals("approval_url")){
                return String.format("<script type=\"text/javascript\">location.href=\"%s\"</script>",links.getHref() );
            }
        }

        return "<script type=\"text/javascript\">location.href=\"/\"</script>" ;
    }

    @GetMapping(PAYPAL_CANCEL_URL)
    public Object cancelPay(){
        return ResponseUtil.ok("cancel") ;
    }

    @GetMapping(PAYPAL_SUCCESS_URL)
    public Object successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId){
        try {
            Object obj =  paypalService.executePayment(paymentId, payerId);
            if(!(obj instanceof Payment)){
                return obj;
            }
            Payment payment = (Payment)obj;
            if(payment.getState().equals("approved")){
                return ResponseUtil.ok("success");
            }
        } catch (PayPalRESTException e) {
            log.error(e.getMessage());
        }
        return "<script type=\"text/javascript\">location.href=\"/\"</script>" ;
    }
}
