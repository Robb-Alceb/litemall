package org.linlinjava.litemall.core.notify;

import cn.jpush.api.JPushClient;

/**
 * @author ：stephen
 * @date ：Created in 2020/5/6 14:57
 * @description：TODO
 */
public class JpushSender {

    private JPushClient jPushClient;
    private Boolean apnsProduction;

    public JPushClient getjPushClient() {
        return jPushClient;
    }

    public void setjPushClient(JPushClient jPushClient) {
        this.jPushClient = jPushClient;
    }

    public Boolean getApnsProduction() {
        return apnsProduction;
    }

    public void setApnsProduction(Boolean apnsProduction) {
        this.apnsProduction = apnsProduction;
    }
}
