package org.linlinjava.litemall.core.notify.jpush;


import org.linlinjava.litemall.db.domain.LitemallUser;
import org.linlinjava.litemall.db.service.LitemallUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ：stephen
 * @date ：Created in 2020/5/19 15:26
 * @description：TODO
 */

@Component
public class JpushConfig {

    @Autowired
    private LitemallUserService litemallUserService;
    /**
     * 存放用户与jpush registrationId的对应信息，用于给指定用户发送消息
     */
    private ConcurrentHashMap<String, String> userChannelMap = new ConcurrentHashMap<>();

    @Bean
    public JpushConfig getInstance(){
        synchronized (this) {
            List<LitemallUser> litemallUsers = litemallUserService.queryAll();
            for(LitemallUser user : litemallUsers){
                if(!StringUtils.isEmpty(user.getSessionKey())){
                    userChannelMap.put(String.valueOf(user.getId()), user.getSessionKey());
                }
            }
            return this;
        }
    }

    /**
     * 获取用户channel map
     * @return
     */
    public ConcurrentHashMap<String,String> getUserChannelMap(){
        return userChannelMap;
    }
}
