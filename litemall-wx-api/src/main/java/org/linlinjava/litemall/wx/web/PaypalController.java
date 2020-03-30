package org.linlinjava.litemall.wx.web;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.wx.annotation.LogAnno;
import org.linlinjava.litemall.wx.annotation.LoginUser;
import org.linlinjava.litemall.core.payment.paypal.service.PaypalService;
import org.linlinjava.litemall.wx.util.URLUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author ：stephen
 * @date ：Created in 11/19/2019 2:14 PM
 * @description：TODO
 */

@Controller
public class PaypalController {
    public static final String PAYPAL_SUCCESS_URL = "wx/paypal/success";
    public static final String PAYPAL_CANCEL_URL = "wx/paypal/cancel";

    public static final String PAYPAL_RECHARGE_SUCCESS_URL = "wx/paypal/recharge/success";
    public static final String PAYPAL_RECHARGE_CANCEL_URL = "wx/paypal/recharge/cancel";

    private Log log = LogFactory.getLog(PaypalController.class);

    @Autowired
    private PaypalService paypalService;


    @GetMapping("/wx/paypal/pay")
    @LogAnno
    public Object pay(HttpServletRequest request,@LoginUser Integer userId,@NotNull Integer orderId){
        String cancelUrl = URLUtils.getBaseURl(request) + "/" + PAYPAL_CANCEL_URL;
        String successUrl = URLUtils.getBaseURl(request) + "/" + PAYPAL_SUCCESS_URL;
//        successUrl = "http://192.168.0.101:8080/"+PAYPAL_CANCEL_URL;
        Object obj =  paypalService.getPayment(userId, orderId, successUrl, cancelUrl);
        if(!(obj instanceof Payment)){
            return obj;
        }

        Payment payment = (Payment)obj;
        for(Links links : payment.getLinks()){
            if(links.getRel().equals("approval_url")){
//                return String.format("<script type=\"text/javascript\">location.href=\"%s\"</script>",links.getHref() );
                return "redirect:" + links.getHref();
            }
        }

        return "<script type=\"text/javascript\">location.href=\"/\"</script>" ;
    }

    @GetMapping("/wx/paypal/cancel")
    @LogAnno
    public Object cancelPay(){
        return ResponseUtil.ok("cancel") ;
    }

    @GetMapping("/wx/paypal/success")
    @LogAnno
    public Object successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId){
        log.debug("paypal success");
        System.out.println("paypal success");
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

    /**
     * 用户充值
     * @param userId
     * @param amount
     * @return
     */
    @GetMapping("/wx/paypal/recharge")
    @LogAnno
    public Object recharge(HttpServletRequest request, @LoginUser Integer userId, BigDecimal amount){
        String cancelUrl = URLUtils.getBaseURl(request) + "/" + PAYPAL_RECHARGE_CANCEL_URL;
        String successUrl = URLUtils.getBaseURl(request) + "/" + PAYPAL_RECHARGE_SUCCESS_URL;
        Object obj =  new Object();
        if(!(obj instanceof Payment)){
            return obj;
        }

        Payment payment = (Payment)obj;
        for(Links links : payment.getLinks()){
            if(links.getRel().equals("approval_url")){
                return "redirect:" + links.getHref();
            }
        }

        return "<script type=\"text/javascript\">location.href=\"/\"</script>" ;
    }
}
