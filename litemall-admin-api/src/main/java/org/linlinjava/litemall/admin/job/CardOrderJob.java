package org.linlinjava.litemall.admin.job;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.core.system.SystemConfig;
import org.linlinjava.litemall.db.beans.Constants;
import org.linlinjava.litemall.db.domain.LitemallGiftCardOrder;
import org.linlinjava.litemall.db.service.LitemallGiftCardOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

/**
 * @author ：stephen
 * @date ：Created in 2020/4/7 10:15
 * @description：TODO
 */
public class CardOrderJob {
    private final Log logger = LogFactory.getLog(CardOrderJob.class);

    @Autowired
    private LitemallGiftCardOrderService orderService;

    @Scheduled(fixedDelay = 40 * 60 * 1000)
    public void checkOrderUnpaid(){
        logger.info("系统开启任务检查礼物卡订单是否已经超期自动取消订单");
        List<LitemallGiftCardOrder> orders = orderService.getUnpaidOrder(SystemConfig.getOrderUnpaid());
        for(LitemallGiftCardOrder order : orders){
            LitemallGiftCardOrder update = new LitemallGiftCardOrder();
            update.setId(order.getId());
            update.setPayStatus(Constants.PAY_STATUS_AUTO_CANCEL);
            update.setDeleted(true);
            orderService.update(update);
            logger.info("礼物卡订单 ID " + order.getId() + " 已经超期自动取消订单");
        }

    }
}
