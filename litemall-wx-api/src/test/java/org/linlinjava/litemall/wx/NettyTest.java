package org.linlinjava.litemall.wx;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.linlinjava.litemall.core.notify.netty.PushService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author ：stephen
 * @date ：Created in 2020/4/8 14:01
 * @description：TODO
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class NettyTest {
    @Autowired
    private PushService pushService;

    @Test
    public void testPushOne(){
        String userId = "123456";
        String msg = "测试netty推送单个用户消息";
        pushService.pushMsgToOne(userId, msg);
//        new PushServiceImpl().pushMsgToOne(userId, msg);
    }

    @Test
    public void testPushAll(){
        String msg = "测试netty推送所有用户消息";
        pushService.pushMsgToAll(msg);
    }
}
