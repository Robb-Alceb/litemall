package org.linlinjava.litemall.admin.job;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.db.domain.LitemallSubscribeUser;
import org.linlinjava.litemall.db.service.LitemallSubscribeUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * @author ：stephen
 * @date ：Created in 2020/8/19 15:59
 * @description：TODO
 */
@Component
public class AutoSubscribeJob {
    private final Log logger = LogFactory.getLog(SubscribeJob.class);

    @Autowired
    private LitemallSubscribeUserService litemallSubscribeUserService;


    /**
     * 每天凌晨一点执行一次，在自动下单之前执行
     */
    @Scheduled(cron = "0 0 1 * * ?")
//    @Scheduled(cron = "0 0/1 * * * ?")
    public void autoSubscribe(){
        List<LitemallSubscribeUser> subscribeUsers = litemallSubscribeUserService.autoSubs();
        for(LitemallSubscribeUser subscribeUser : subscribeUsers){
            if(subscribeUser.getEndTime().compareTo(LocalDateTime.now()) < 0){
                int count = litemallSubscribeUserService.countByLinkId(subscribeUser.getId());
                if(count == 0 ){
                    LitemallSubscribeUser autoSub = new LitemallSubscribeUser();
                    BeanUtils.copyProperties(subscribeUser, autoSub);
                    autoSub.setUpdateTime(null);
                    autoSub.setId(null);
                    autoSub.setLinkId(subscribeUser.getId());
                    /**
                     * 重新设置有效时间
                     */
                    long between = ChronoUnit.DAYS.between(subscribeUser.getStartTime(), subscribeUser.getEndTime());
                    autoSub.setStartTime(LocalDateTime.of(LocalDate.now(), LocalTime.MIN));
                    autoSub.setEndTime(LocalDateTime.of(LocalDate.now(), LocalTime.MAX).plusDays(between));
                    litemallSubscribeUserService.add(autoSub);
                }
            }
        }
    }


}
