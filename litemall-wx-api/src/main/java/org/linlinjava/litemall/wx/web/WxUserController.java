package org.linlinjava.litemall.wx.web;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.core.validator.Order;
import org.linlinjava.litemall.core.validator.Sort;
import org.linlinjava.litemall.db.service.LitemallOrderService;
import org.linlinjava.litemall.wx.annotation.LogAnno;
import org.linlinjava.litemall.wx.annotation.LoginUser;
import org.linlinjava.litemall.wx.dto.UserRechargeDto;
import org.linlinjava.litemall.wx.service.WxUserService;
import org.linlinjava.litemall.wx.util.URLUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户服务
 */
@RestController
@RequestMapping("/wx/user")
@Validated
public class WxUserController {
    private final Log logger = LogFactory.getLog(WxUserController.class);

    public static final String PAYPAL_SUCCESS_URL = "wx/user/recharge/success";
    public static final String PAYPAL_CANCEL_URL = "wx/user/recharge/cancel";

    @Autowired
    private LitemallOrderService orderService;

    @Autowired
    private WxUserService wxUserService;

    /**
     * 用户个人页面数据
     * <p>
     * 目前是用户订单统计信息
     *
     * @param userId 用户ID
     * @return 用户个人页面数据
     */
    @GetMapping("index")
    @LogAnno
    public Object list(@LoginUser Integer userId) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }

        Map<Object, Object> data = new HashMap<Object, Object>();
        data.put("order", orderService.orderInfo(userId));
        return ResponseUtil.ok(data);
    }

    @PostMapping("recharge")
    @LogAnno
    public Object recharge(@LoginUser Integer userId, @RequestBody @Valid UserRechargeDto dto) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }

        return ResponseUtil.ok(wxUserService.recharge(userId, dto));
    }

    @GetMapping("recharge")
    @LogAnno
    public Object pay(@LoginUser Integer userId, @NotNull @RequestParam("id") Integer id, HttpServletResponse res,  HttpServletRequest request) throws Exception {
        if(userId == null){
            return ResponseUtil.unlogin();
        }

        String cancelUrl = URLUtils.getBaseURl(request) + "/" + PAYPAL_CANCEL_URL;
        String successUrl = URLUtils.getBaseURl(request) + "/" + PAYPAL_SUCCESS_URL;

        Object obj =  wxUserService.getPayment(id, userId, successUrl, cancelUrl);
        if(!(obj instanceof Payment)){
            return obj;
        }
        Payment payment = (Payment)obj;
        for(Links links : payment.getLinks()){
            if(links.getRel().equals("approval_url")){
                res.sendRedirect(links.getHref().toString());
                return "redirect:" + links.getHref();
            }
        }
        return "<script type=\"text/javascript\">location.href=\"/\"</script>" ;
    }

    @GetMapping("recharge/success")
    @LogAnno
    public Object success(@LoginUser Integer userId, @NotNull @RequestParam("paymentId") String paymentId, @NotNull @RequestParam("PayerID") String payerId, HttpServletResponse resp, HttpServletRequest request) {
        logger.debug("executePayment");
        Object obj =  wxUserService.executePayment(paymentId, payerId);
        if(!(obj instanceof Payment)){
            return obj;
        }
        Payment payment = (Payment)obj;
        if(payment.getState().equals("approved")){
            //付款完成后生成礼物卡或者更新余额
//            wxUserService.createOrUpdateAmount(userId,paymentId);
//            logger.info(URLUtils.getBaseURl(request));
//            resp.sendRedirect(URLUtils.getBaseURl(request) + "/wx/pay/success");
//            return null;
            return ResponseUtil.ok("success");
        }
        return "<script type=\"text/javascript\">location.href=\"/\"</script>" ;
    }

    @GetMapping("recharge/cancel")
    @LogAnno
    public Object cancel() {

        return ResponseUtil.ok();
    }

    @GetMapping("bill/list")
    @LogAnno
    public Object history(@LoginUser Integer userId,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer limit,
            @Sort @RequestParam(defaultValue = "add_time") String sort,
            @Order @RequestParam(defaultValue = "desc") String order) {
        if(userId == null){
            return ResponseUtil.unlogin();
        }
        return wxUserService.bill(userId, page, limit, sort, order);
    }
}