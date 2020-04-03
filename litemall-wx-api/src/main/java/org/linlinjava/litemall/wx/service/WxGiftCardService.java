package org.linlinjava.litemall.wx.service;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.core.notify.NotifyService;
import org.linlinjava.litemall.core.payment.DefaultCurType;
import org.linlinjava.litemall.core.payment.PaymentResponseCode;
import org.linlinjava.litemall.core.payment.paypal.service.impl.CardPaypalServiceImpl;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.beans.Constants;
import org.linlinjava.litemall.db.dao.*;
import org.linlinjava.litemall.db.domain.*;
import org.linlinjava.litemall.db.service.*;
import org.linlinjava.litemall.wx.dto.CardShareDto;
import org.linlinjava.litemall.wx.util.GeneratorUtil;
import org.linlinjava.litemall.wx.util.WxResponseCode;
import org.linlinjava.litemall.wx.vo.GiftCardVo;
import org.linlinjava.litemall.wx.vo.MyCardVo;
import org.linlinjava.litemall.wx.vo.TypeAndName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static org.linlinjava.litemall.wx.util.WxResponseCode.CARD_HAS;
import static org.linlinjava.litemall.wx.util.WxResponseCode.CARD_INVALID;

/**
 * @author ：stephen
 * @date ：Created in 2020/4/2 13:48
 * @description：礼物卡服务
 * TODO 第三方付款成功、本地失败处理
 *  暂时的思路是起一个定时任务通过转账id从第三方检查付款状态是否本地余额是否改变成功
 *  目前使用更新时间来作为是否成功修改余额判断
 */
@Service
public class WxGiftCardService {
    private final Log logger = LogFactory.getLog(WxGiftCardService.class);

    @Autowired
    private LitemallGiftCardService litemallGiftCardService;
    @Autowired
    private LitemallGiftCardUserService litemallGiftCardUserService;
    @Autowired
    private LitemallGiftCardUserLogService litemallGiftCardUserLogService;
    @Autowired
    private LitemallCategoryService categoryService;
    @Resource
    private LitemallGiftCardOrderService litemallGiftCardOrderService;
    @Resource
    private LitemallGiftCardShareService litemallGiftCardShareService;
    @Autowired
    private CardPaypalServiceImpl cardPaypalService;
    @Autowired
    private NotifyService notifyService;
    @Autowired
    private LitemallUserService litemallUserService;

    @Value("${card.paypal.success}")
    private String successUrl;
    @Value("${card.paypal.success}")
    private String cancelUrl;

    /**
     * 查询所有可购买的礼物卡，
     * 根据category_ids 分类
     * 没有的则认为是通用的(返回值type=0)
     * 其他的则会按照商品分类去查询type和name，理论上添加礼物卡时只能指定一个父级类目下的子类，这里不做判断，仅查询第一个category_id的父级作为他的分组依据
     * @return
     */
    public Object all() {
        List<GiftCardVo> vos = new ArrayList<>();
        List<LitemallGiftCard> cards = litemallGiftCardService.all();
        Map<Integer[], List<LitemallGiftCard>> map = cards.stream().collect(Collectors.groupingBy(LitemallGiftCard::getCategoryIds));
        Set<Map.Entry<Integer[], List<LitemallGiftCard>>> entries = map.entrySet();
        Iterator<Map.Entry<Integer[], List<LitemallGiftCard>>> iterator = entries.iterator();
        while(iterator.hasNext()){
            Map.Entry<Integer[], List<LitemallGiftCard>> next = iterator.next();
            Integer[] ids = next.getKey();
            //通用
            GiftCardVo vo = new GiftCardVo();
            if(ids == null || ids.length == 0){
                TypeAndName item = new TypeAndName(0,"");
                vo.setTypeAndName(item);
            //指定类目
            }else{
                Integer id = ids[0];
                LitemallCategory category = categoryService.findById(id);
                LitemallCategory father = categoryService.findById(category.getPid());
                if(father != null){
                    TypeAndName item = new TypeAndName(father.getId(),father.getName());
                    vo.setTypeAndName(item);
                }
            }
            vo.setNumber(next.getValue()==null?0:next.getValue().size());
            vo.setCards(next.getValue());

            boolean anyMath = false;
            for(GiftCardVo item: vos){
                if(item.getTypeAndName().getType() == vo.getTypeAndName().getType()){
                    item.setNumber(vo.getNumber());
                    item.getCards().addAll(vo.getCards());
                    anyMath = true;
                }
            }
            if(!anyMath){
                vos.add(vo);
            }
        }
        return ResponseUtil.ok(vos);
    }


    public List<LitemallGiftCard> getByCategory(Integer[] ids) {
        List<LitemallGiftCard> cards = litemallGiftCardService.getByCategoryIds(ids);
        return cards;
    }

    /**
     * 购买礼物卡
     * @param cardId
     * @param amount
     * @param userId
     * @return
     */
    public Object buyCard(Integer cardId, BigDecimal amount, Integer userId) {
        LitemallGiftCard card = litemallGiftCardService.findById(cardId);
        if(card == null){
            return ResponseUtil.fail(CARD_INVALID, "礼物卡不存在");
        }
        if(litemallGiftCardUserService.countByCardId(cardId, userId) > 0){
            return ResponseUtil.fail(CARD_HAS, "你已有该类型的卡");
        }
        LitemallGiftCardOrder order = new LitemallGiftCardOrder();
        order.setAmount(amount);
        order.setGiftCardId(card.getId());
        order.setUserId(userId);
        order.setPayType(Constants.PAY_TYPE_PAYPAL);
        order.setCurrency(DefaultCurType.USD.getType());
        litemallGiftCardOrderService.add(order);
        return ResponseUtil.ok(order);
    }

    /**
     * 付款准备
     * @param orderId
     * @param userId
     * @return
     */
    public Object getPayment(Integer orderId, Integer userId) {
        return cardPaypalService.getPayment(userId, orderId, successUrl, cancelUrl);
    }

    /**
     * 付款完成
     * @param paymentId
     * @param payerId
     * @return
     */
    public Object executePayment(String paymentId, String payerId) {
        try {
            return cardPaypalService.executePayment(paymentId, payerId);
        }catch (Exception e){
            return ResponseUtil.fail(PaymentResponseCode.PAYMENT_FAIL, "支付失败");
        }
    }

    /**
     * 退款
     * @param orderId
     * @return
     */
    public Object refund(Integer orderId, Integer userId) {
        LitemallGiftCardOrder orderServiceById = litemallGiftCardOrderService.findById(orderId);
        if(orderServiceById == null){
            return ResponseUtil.fail(PaymentResponseCode.ORDER_UNKNOWN, "订单不存在");
        }
        if(orderServiceById.getUserId() != userId){
            return ResponseUtil.fail(PaymentResponseCode.ORDER_UNKNOWN, "订单不存在");
        }
        if(cardPaypalService.refund(orderId)){
            LitemallGiftCardOrder order = new LitemallGiftCardOrder();
            order.setId(orderId);
            order.setPayStatus(Constants.PAY_STATUS_REFUSE);
            litemallGiftCardOrderService.update(order);
            return orderId;
        }
        return ResponseUtil.fail(PaymentResponseCode.PAYMENT_FAIL, "退款失败");
    }


    /**
     * 分享
     * @param dto
     * @return
     */
    public Object share(CardShareDto dto, Integer userId) {
        LitemallGiftCardUser card = litemallGiftCardUserService.findByNumber(dto.getCardNumber());
        if(card == null){
            return ResponseUtil.fail(WxResponseCode.CARD_INVALID, "礼物卡不存在");
        }
        LitemallGiftCardShare share = new LitemallGiftCardShare();
        share.setCardNumber(dto.getCardNumber());
        share.setCode(StringUtils.isNotBlank(dto.getCode())?dto.getCode(): GeneratorUtil.codeGenerator());
        if(dto.getActiveTime() != null){
            DateTimeFormatter timeDtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            share.setActiveTime(LocalDateTime.of(LocalDate.parse(dto.getActiveTime(), timeDtf), LocalTime.MIN));
        }
        share.setLink(GeneratorUtil.linkGenerator());
        share.setUserId(userId);
        litemallGiftCardShareService.add(share);
        log(share.getCardNumber(), Constants.LOG_GIFTCARD_SHARE, "分享礼物卡",userId);
        return ResponseUtil.ok(share);
    }

    /**
     * 取消分享
     * @param shareCardId
     * @param userId
     * @return
     */
    public Object shareCancel(Integer shareCardId, Integer userId) {
        LitemallGiftCardShare shareCard = litemallGiftCardShareService.findById(shareCardId);
        if(shareCard.getUserId() != userId){
            return ResponseUtil.fail(WxResponseCode.GIFT_CARD_SHARE_NOT_AUTH, "礼物卡不存在");
        }
        shareCard.setDeleted(true);
        shareCard.setActiveTime(LocalDateTime.now());
        litemallGiftCardShareService.update(shareCard);
        log(shareCard.getCardNumber(), Constants.LOG_GIFTCARD_SHARE, "取消分享礼物卡",userId);
        return ResponseUtil.ok(shareCard);
    }

    /**
     * 根据link查找分享的礼物卡
     * @param link
     * @return
     */
    public Object find(String link) {
        LitemallGiftCardShare share = litemallGiftCardShareService.findByLink(link);
        if(share == null){
            return ResponseUtil.fail(WxResponseCode.GIFT_CARD_SHARE_NOT_EXIST, "分享不存在");
        }
        if(share.getActiveTime() != null && share.getActiveTime().compareTo(LocalDateTime.now()) == -1){
            return ResponseUtil.fail(WxResponseCode.GIFT_CARD_SHARE_NOT_EXIST, "分享不存在");
        }
        LitemallGiftCardUser cardUser = litemallGiftCardUserService.findByNumber(share.getCardNumber());
        LitemallGiftCard card = litemallGiftCardService.findById(cardUser.getGiftCardId());
        cardUser.setUserId(null);
        cardUser.setCardNumber(null);
        Map map = new HashMap();
        map.put("user", cardUser);
        map.put("card",card);
        return ResponseUtil.ok(map);
    }

    /**
     * 领取礼物卡
     * @param link
     * @return
     */
    @Transactional
    public Object pick(String link, String code, Integer userId) {
        LitemallGiftCardShare share = litemallGiftCardShareService.findByCodeAndLink(link, code);
        if(share == null){
            return ResponseUtil.fail(WxResponseCode.GIFT_CARD_SHARE_NOT_EXIST, "分享不存在");
        }
        if(share.getActiveTime() != null && share.getActiveTime().compareTo(LocalDateTime.now()) == -1){
            return ResponseUtil.fail(WxResponseCode.GIFT_CARD_SHARE_NOT_EXIST, "分享不存在");
        }
        LitemallGiftCardUser cardUser = litemallGiftCardUserService.findByNumber(share.getCardNumber());

        //修改礼物卡所属
        LitemallGiftCardUser update = new LitemallGiftCardUser();
        update.setId(cardUser.getId());
        update.setUpdateTime(cardUser.getUpdateTime());
        update.setUserId(userId);
        if(litemallGiftCardUserService.updateById(update) == 0){
            return ResponseUtil.fail(WxResponseCode.GIFT_CARD_SHARE_PICKED, "礼物卡已被领取");
        }
        //删除礼物卡分享
        litemallGiftCardShareService.deleteById(share.getId());
        //发送消息给赠送者
//        notifyService.notifySmsTemplateSync(order.getMobile(), NotifyType.PAY_SUCCEED, new String[]{order.getOrderSn().substring(8, 14)});

        log(share.getCardNumber(), Constants.LOG_GIFTCARD_PICK, "领取礼物卡",userId);
        return ResponseUtil.ok(cardUser);
    }


    /**
     * 我的礼物卡列表
     * @param userId
     * @return
     */
    public Object myCards(Integer userId) {
        List<MyCardVo> cards = new ArrayList<>();
        List<LitemallGiftCardUser> litemallGiftCardUsers = litemallGiftCardUserService.queryByUserId(userId);
        for(LitemallGiftCardUser item : litemallGiftCardUsers){
            LitemallGiftCard giftCard = litemallGiftCardService.findById(item.getGiftCardId());
            MyCardVo vo = new MyCardVo();
            vo.setAmount(item.getAmount());
            vo.setCardName(giftCard.getName());
            vo.setCardNumber(item.getCardNumber());
            vo.setPicUrl(giftCard.getPicUrl());
            cards.add(vo);
        }
        return ResponseUtil.ok(cards);
    }

    /**
     * 付款成功后创建卡或者更新卡上余额
     * @param userId
     * @param paymentId
     */
    @Transactional
    public void createOrUpdateCard(Integer userId, String paymentId) {
        LitemallGiftCardOrder order = litemallGiftCardOrderService.findByOutTradeNo(paymentId);
        //添加卡
        if(StringUtils.isEmpty(order.getCardNumber())){
            LitemallGiftCardUser card = new LitemallGiftCardUser();
            card.setUserId(order.getUserId());
            card.setAmount(order.getAmount());
            card.setGiftCardId(order.getGiftCardId());
            LitemallUser user = litemallUserService.findById(order.getUserId());
            if(user != null){
                card.setUserName(user.getUsername());
            }
            card.setCardNumber(GeneratorUtil.cardNumberGenerator("CG",order.getUserId()));
            try{
                //插入用户礼物卡
                litemallGiftCardUserService.add(card);
                //修改订单的cardNumber
                LitemallGiftCardOrder update = new LitemallGiftCardOrder();
                update.setId(order.getId());
                update.setCardNumber(card.getCardNumber());
                litemallGiftCardOrderService.update(update);
                log(order.getCardNumber(), Constants.LOG_GIFTCARD_PICK, "领取礼物卡",userId);
                log(order.getCardNumber(), Constants.LOG_GIFTCARD_RECHARGE, "充值礼物卡",userId,order.getAmount());
            }catch (Exception e){
                logger.info("Exception in createCard:"+ e.getMessage());
                //当cardNumber冲突时重新插入
                createOrUpdateCard(userId, paymentId);
            }
            //修改卡余额
        }else{
            LitemallGiftCardUser card = litemallGiftCardUserService.findByNumber(order.getCardNumber());
            card.setAmount(card.getAmount().add(order.getAmount()));
            litemallGiftCardUserService.updateWithOptimisticLocker(card);
            log(order.getCardNumber(), Constants.LOG_GIFTCARD_PICK, "领取礼物卡",userId);
        }

    }

    /**
     * 礼物卡充值
     * @param userId
     * @param cardNumber
     * @param amount
     * @return
     */
    public Object recharge(Integer userId, String cardNumber, BigDecimal amount) {
        LitemallGiftCardUser card = litemallGiftCardUserService.findByNumber(cardNumber);
        if(userId != card.getUserId()){
            return ResponseUtil.fail(WxResponseCode.CARD_INVALID, "礼物卡不存在");
        }
        LitemallGiftCardOrder order = new LitemallGiftCardOrder();
        order.setCurrency(DefaultCurType.USD.getType());
        order.setAmount(amount);
        order.setUserId(userId);
        order.setCardNumber(cardNumber);
        order.setGiftCardId(card.getGiftCardId());
        litemallGiftCardOrderService.add(order);
        return order;
    }

    /**
     * 我的分享
     * @param userId
     * @return
     */
    public Object myShares(Integer userId) {
        List<Map> rtn = new ArrayList<>();
        List<LitemallGiftCardShare> shares = litemallGiftCardShareService.findByUserId(userId);
        for(LitemallGiftCardShare share: shares){
            LitemallGiftCardUser cardUser = litemallGiftCardUserService.findByNumber(share.getCardNumber());
            LitemallGiftCard card = litemallGiftCardService.findById(cardUser.getGiftCardId());
            Map map = new HashMap();
            map.put("share", share);
            map.put("card",card);
            rtn.add(map);
        }
        return ResponseUtil.ok(rtn);
    }

    /**
     * 记录日志(充值、消费、赠送、领取、销毁)
     * @param cardNumber
     * @param type
     * @param content
     * @param userId
     */
    public void log(String cardNumber, Byte type, String content, Integer userId){
        log(cardNumber,type,content,userId,null);
    }
    public void log(String cardNumber, Byte type, String content, Integer userId, BigDecimal amount){
        LitemallUser user = litemallUserService.findById(userId);
        LitemallGiftCardUserLog log = new LitemallGiftCardUserLog();
        log.setCardNumber(cardNumber);
        log.setType(type);
        log.setAddUserId(userId);
        log.setContent(content);
        log.setAmount(amount);
        if(user != null){
            log.setContent(user.getUsername() + ":" + content);
            log.setAddUserName(user.getUsername());
        }
        litemallGiftCardUserLogService.add(log);
    }

}
