package org.linlinjava.litemall.admin;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.linlinjava.litemall.admin.service.ErpHttpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author ：stephen
 * @date ：Created in 2020/6/10 14:29
 * @description：TODO
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ErpHttpTest {

    @Autowired
    private ErpHttpService erpHttpService;
    @Test
    public void test(){
        erpHttpService.setErpAdmin(null);
    }
}
