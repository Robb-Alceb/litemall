package org.linlinjava.litemall.wx;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.wx.util.WxResponseEnum;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author ：stephen
 * @date ：Created in 2020/4/9 11:00
 * @description：TODO
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class ResponseUtilTest {

    @Test
    public void testEnum(){
        Object o = ResponseUtil.badArgument(WxResponseEnum.BAD_ARGUMENT_ORDERID);

        System.out.println(o.toString());
    }
}
