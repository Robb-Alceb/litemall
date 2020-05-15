package org.linlinjava.litemall.wx.web;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.core.util.JacksonUtil;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.core.validator.Order;
import org.linlinjava.litemall.core.validator.Sort;
import org.linlinjava.litemall.wx.annotation.LogAnno;
import org.linlinjava.litemall.wx.annotation.LoginUser;
import org.linlinjava.litemall.wx.dto.CardShareDto;
import org.linlinjava.litemall.wx.service.WxGiftCardService;
import org.linlinjava.litemall.wx.util.URLUtils;
import org.linlinjava.litemall.wx.util.WxResponseCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author ：stephen
 * @date ：Created in 2020/4/2 13:47
 * @description：礼物卡
 */
@RestController
@RequestMapping("/wx/card")
@Validated
public class GiftCardController {

    @Autowired
    private WxGiftCardService giftCardService;
    private static Log log = LogFactory.getLog(GiftCardController.class);

    /**
     * 查询所有可购买的礼物卡，
     * @return
     */
    @GetMapping("all")
    @LogAnno
    public Object all(){
        return giftCardService.all();
    }
    /**
     * 购买礼物卡
     * @param cardId
     * @param amount
     * @param userId
     * @return
     */
    @PostMapping("buy")
    @LogAnno
    public Object buy(@LoginUser Integer userId, @NotNull Integer cardId, @NotNull BigDecimal amount){
        if(userId == null){
            return ResponseUtil.unlogin();
        }
        return giftCardService.buyCard(cardId, amount, userId);
    }

    /**
     * 付款准备
     * @param orderId
     * @param userId
     * @return
     */
    @GetMapping("pay")
    @LogAnno
    public Object pay(@LoginUser Integer userId, @NotNull Integer orderId, HttpServletResponse resp) throws Exception{
        if(userId == null){
            return ResponseUtil.unlogin();
        }

        Object obj =  giftCardService.getPayment(orderId, userId);
        if(!(obj instanceof Payment)){
            return obj;
        }
        Payment payment = (Payment)obj;
        for(Links links : payment.getLinks()){
            if(links.getRel().equals("approval_url")){
                resp.sendRedirect(links.getHref().toString());
                return "redirect:" + links.getHref();
            }
        }
        return "<script type=\"text/javascript\">location.href=\"/\"</script>" ;
    }

    /**
     * 付款完成
     * @param paymentId
     * @param payerId
     * @return
     */
    @GetMapping("pay/success")
    @LogAnno
    public Object success(@LoginUser Integer userId, @NotNull @RequestParam("paymentId") String paymentId, @NotNull @RequestParam("PayerID") String payerId, HttpServletResponse resp, HttpServletRequest request) throws Exception{
        log.debug("paypal success");
        Object obj =  giftCardService.executePayment(paymentId, payerId);
        if(!(obj instanceof Payment)){
            return obj;
        }
        Payment payment = (Payment)obj;
        if(payment.getState().equals("approved")){
            //付款完成后生成礼物卡或者更新余额
            giftCardService.createOrUpdateCard(userId,paymentId);
            log.info(URLUtils.getBaseURl(request));
//            resp.sendRedirect(URLUtils.getBaseURl(request) + "/wx/pay/success");
//            return null;
            return ResponseUtil.ok("success");
        }
        return "<script type=\"text/javascript\">location.href=\"/\"</script>" ;
    }

    @GetMapping("pay/cancel")
    @LogAnno
    public Object cancelPay(){
        return ResponseUtil.ok("cancel") ;
    }

    /**
     * 退款
     * @param orderId
     * @return
     */
    @PutMapping("refuse")
    @LogAnno
    public Object refuse(@LoginUser Integer userId, @NotNull Integer orderId){
        if(userId == null){
            return ResponseUtil.unlogin();
        }
        return giftCardService.refund(orderId,userId);
    }
    /**
     * 分享
     * @param dto
     * @return
     */
    @PostMapping("share")
    @LogAnno
    public Object share(@LoginUser Integer userId, @Valid @RequestBody CardShareDto dto){
        if(userId == null){
            return ResponseUtil.unlogin();
        }
        return giftCardService.share(dto, userId);
    }
    /**
     * 取消分享
     * @param shareCardId
     * @param userId
     * @return
     */
    @PutMapping("share/cancel")
    @LogAnno
    public Object cancel(@LoginUser Integer userId,  @NotNull Integer shareCardId){
        if(userId == null){
            return ResponseUtil.unlogin();
        }
        return giftCardService.shareCancel(shareCardId, userId);
    }
    /**
     * 根据link查找分享的礼物卡
     * @param link
     * @return
     */
    @GetMapping("find")
    @LogAnno
    public Object find(@LoginUser Integer userId, @NotNull String link){
        if(userId == null){
            return ResponseUtil.unlogin();
        }
        return giftCardService.find(link);
    }

    /**
     * 领取礼物卡
     * @param link
     * @param code
     * @return
     */
    @PostMapping("pick")
    @LogAnno
    public Object pick(@LoginUser Integer userId,  @NotNull String link, @NotNull String code){
        if(userId == null){
            return ResponseUtil.unlogin();
        }
        return giftCardService.pick(link, code, userId);
    }

    /**
     * 我的礼物卡列表
     * @param userId
     * @return
     */
    @GetMapping("my")
    @LogAnno
    public Object myCards(@LoginUser Integer userId){
        if(userId == null){
            return ResponseUtil.unlogin();
        }
        return giftCardService.myCards(userId);
    }

    /**
     * 我的分享列表
     * @param userId
     * @return
     */
    @GetMapping("shares")
    @LogAnno
    public Object myShares(@LoginUser Integer userId){
        if(userId == null){
            return ResponseUtil.unlogin();
        }
        return giftCardService.myShares(userId);
    }

    /**
     * 充值
     * @param userId
     * @return
     */
    @PostMapping("recharge")
    @LogAnno
    public Object recharge(@LoginUser Integer userId, @RequestBody String body){
        if(userId == null){
            return ResponseUtil.unlogin();
        }
        String cardNumber = JacksonUtil.parseString(body, "cardNumber");
        BigDecimal amount = JacksonUtil.parseObject(body, "amount", BigDecimal.class);
        if(StringUtils.isEmpty(cardNumber)){
            return ResponseUtil.badArgument();
        }
        if(amount == null){
            return ResponseUtil.badArgument();
        }
        return giftCardService.recharge(userId, cardNumber, amount);
    }

    @GetMapping("bill/list")
    @LogAnno
    public Object history(@LoginUser Integer userId,
                          @NotNull String cardNumber,
                          @RequestParam(defaultValue = "1") Integer page,
                          @RequestParam(defaultValue = "10") Integer limit,
                          @Sort @RequestParam(defaultValue = "add_time") String sort,
                          @Order @RequestParam(defaultValue = "desc") String order) {
        if(userId == null){
            return ResponseUtil.unlogin();
        }
        return giftCardService.bill(userId, cardNumber, page, limit, sort, order);
    }
}
