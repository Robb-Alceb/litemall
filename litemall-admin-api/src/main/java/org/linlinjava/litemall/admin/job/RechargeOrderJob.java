package org.linlinjava.litemall.admin.job;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.core.system.SystemConfig;
import org.linlinjava.litemall.db.beans.Constants;
import org.linlinjava.litemall.db.domain.LitemallUserRechargeOrder;
import org.linlinjava.litemall.db.service.LitemallUserRechargeOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

/**
 * @author ：stephen
 * @date ：Created in 2020/4/16 17:45
 * @description：TODO
 */
public class RechargeOrderJob {
    private final Log logger = LogFactory.getLog(CardOrderJob.class);

    @Autowired
    private LitemallUserRechargeOrderService litemallUserRechargeOrderService;

    @Scheduled(fixedDelay = 40 * 60 * 1000)
    public void checkOrderUnpaid(){
        logger.info("系统开启任务检查用户充值订单是否已经超期自动取消订单");
        List<LitemallUserRechargeOrder> orders = litemallUserRechargeOrderService.getUnpaidOrder(SystemConfig.getOrderUnpaid());
        for(LitemallUserRechargeOrder order : orders){
            LitemallUserRechargeOrder update = new LitemallUserRechargeOrder();
            update.setId(order.getId());
            update.setPayStatus(Constants.PAY_STATUS_AUTO_CANCEL);
            update.setDeleted(true);
            litemallUserRechargeOrderService.updateById(update);
            logger.info("用户充值订单 ID " + order.getId() + " 已经超期自动取消订单");
        }

    }
}
