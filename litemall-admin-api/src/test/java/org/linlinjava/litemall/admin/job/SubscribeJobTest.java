package org.linlinjava.litemall.admin.job;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * @author ：stephen
 * @date ：Created in 2020/8/12 13:54
 * @description：TODO
 */
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class SubscribeJobTest {
    @Autowired
    private SubscribeJob subscribeJob;

    @Test
    public void test(){
        subscribeJob.subscribeOrder();
    }
}
