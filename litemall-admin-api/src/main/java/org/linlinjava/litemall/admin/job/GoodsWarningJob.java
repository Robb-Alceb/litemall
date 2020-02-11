package org.linlinjava.litemall.admin.job;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.admin.beans.Constants;
import org.linlinjava.litemall.core.notify.NotifyService;
import org.linlinjava.litemall.db.domain.LitemallAdmin;
import org.linlinjava.litemall.db.domain.LitemallGoods;
import org.linlinjava.litemall.db.domain.LitemallGoodsProduct;
import org.linlinjava.litemall.db.service.LitemallAdminService;
import org.linlinjava.litemall.db.service.LitemallGoodsProductService;
import org.linlinjava.litemall.db.service.LitemallGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * @Author: stephen
 * @Date: 2020/2/11 9:51
 * @Version: 1.0
 * @Description: 检测商品数量预警
 *
 */

@Component
public class GoodsWarningJob {
    private final Log logger = LogFactory.getLog(GoodsWarningJob.class);

    @Autowired
    private LitemallGoodsService litemallGoodsService;
    @Autowired
    private LitemallAdminService litemallAdminService;
    @Autowired
    private LitemallGoodsProductService litemallGoodsProductService;
    @Autowired
    private NotifyService notifyService;

    @Scheduled(fixedDelay = 60 * 60 * 1000)
    public void checkCouponExpired() {
        logger.info("系统开启任务检查商品数量预警");

        List<Integer> goodsIds = litemallGoodsProductService.queryWarning();
        List<LitemallGoods> goodsList = litemallGoodsService.findByIds(goodsIds);
        goodsList.forEach(goods -> {
            List<LitemallAdmin> admins = litemallAdminService.findByShopId(goods.getShopId());
            for (LitemallAdmin admin : admins) {
                if(Arrays.asList(admin.getRoleIds()).indexOf(Constants.SHOPKEEPER_ROLE_ID) >= 0){
                    logger.info("商品数量预警发送邮件:"+goods.getName());
                    String content = goods.getName() + "数量即将不足";
                    notifyService.notifyMail("商品数量预警",content, admin.getEmail());
                }
            }
        });

    }

}
