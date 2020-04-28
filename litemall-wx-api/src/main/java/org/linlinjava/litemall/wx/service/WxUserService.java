package org.linlinjava.litemall.wx.service;

import org.linlinjava.litemall.core.payment.PaymentResponseCode;
import org.linlinjava.litemall.core.payment.paypal.service.impl.UserPaypalServiceImpl;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.beans.Constants;
import org.linlinjava.litemall.db.domain.LitemallRechargeConsumption;
import org.linlinjava.litemall.db.domain.LitemallUser;
import org.linlinjava.litemall.db.domain.LitemallUserRechargeOrder;
import org.linlinjava.litemall.db.service.LitemallRechargeConsumptionService;
import org.linlinjava.litemall.db.service.LitemallUserRechargeOrderService;
import org.linlinjava.litemall.db.service.LitemallUserService;
import org.linlinjava.litemall.wx.dto.UserRechargeDto;
import org.linlinjava.litemall.wx.util.WxResponseEnum;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author ：stephen
 * @date ：Created in 2020/4/9 17:15
 * @description：TODO
 */
@Service
public class WxUserService {

    @Autowired
    private LitemallUserRechargeOrderService litemallUserRechargeOrderService;
    @Autowired
    private UserPaypalServiceImpl userPaypalService;
    @Autowired
    private LitemallUserService litemallUserService;
    @Autowired
    private LitemallRechargeConsumptionService litemallRechargeConsumptionService;

    /**
     * 用户充值
     * @param userId
     * @param dto
     * @return
     */
    public Object recharge(Integer userId, UserRechargeDto dto) {

        LitemallUserRechargeOrder order = new LitemallUserRechargeOrder();
        order.setAmount(dto.getAmount());
        order.setUserId(userId);
        litemallUserRechargeOrderService.add(order);
        return order;
    }

    /**
     * 充值支付
     * @param id
     * @param userId
     * @param successUrl
     * @param cancelUrl
     * @return
     */
    public Object getPayment(Integer id, Integer userId, String successUrl, String cancelUrl) {
        return userPaypalService.getPayment(userId, id, successUrl, cancelUrl);
    }

    /**
     * 支付成功
     * @param paymentId
     * @param payerId
     * @return
     */
    @Transactional
    public Object executePayment(String paymentId, String payerId) {
        try {
            Object payment = userPaypalService.executePayment(paymentId, payerId);
            createOrUpdateAmount(paymentId);
            return payment;
        }catch (Exception e){
            return ResponseUtil.fail(PaymentResponseCode.PAYMENT_FAIL, "支付失败");
        }
    }

    /**
     * 更新余额
     * @param paymentId
     */
    @Transactional
    public Object createOrUpdateAmount(String paymentId) {

        LitemallUserRechargeOrder order = litemallUserRechargeOrderService.findByOutTradeNo(paymentId);
        if(order.getPayStatus() != Constants.PAY_STATUS_DONE){
            return ResponseUtil.fail(WxResponseEnum.NOT_FIND_ORDER);
        }
        LitemallUser user = litemallUserService.findById(order.getUserId());
        if(user == null){
            return ResponseUtil.fail(WxResponseEnum.NOT_FIND_USER);
        }
        //记录用户充值日志
        addRechargeConsumption(user, Constants.USER_SAVING, order.getAmount(),order.getId());
        LitemallUser update = new LitemallUser();
        update.setId(order.getUserId());
        update.setAvailableAmount(user.getAvailableAmount().add(order.getAmount()));
        //更新用户余额
        if(litemallUserService.updateWithOptimisticLocker(update, user.getUpdateTime()) == 0){
            //更新失败，重新更新
            createOrUpdateAmount(paymentId);
        }
        return null;
    }

    public Object bill(Integer userId, Integer page, Integer limit, String sort, String order) {
        List<LitemallRechargeConsumption> litemallRechargeConsumptions = litemallRechargeConsumptionService.querySelectiveList(userId, null, null, page, limit, sort, order);
        return ResponseUtil.okList(litemallRechargeConsumptions);
    }

    public void addRechargeConsumption(LitemallUser user, Byte type, BigDecimal amount){
        addRechargeConsumption(user,type,amount,null);

    }

    public void addRechargeConsumption(LitemallUser user, Byte type, BigDecimal amount, Integer orderId){
        LitemallRechargeConsumption record = new LitemallRechargeConsumption();
        BeanUtils.copyProperties(user, record);
        record.setUserId(user.getId());
        record.setUsername(user.getUsername());
        record.setAddUserId(user.getId());
        record.setType(type);
        record.setAmount(amount);
        record.setOrderId(orderId);
        if(record.getAvailableAmount() != null && Constants.USER_SAVING == type){
            record.setAvailableAmount(record.getAvailableAmount().add(amount));
        }else if(record.getAvailableAmount() != null && Constants.USER_CONSUME == type){
            record.setAvailableAmount(record.getAvailableAmount().subtract(amount));
        }
        litemallRechargeConsumptionService.add(record);

    }
}
