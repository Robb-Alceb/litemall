package org.linlinjava.litemall.admin.erp;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.linlinjava.litemall.admin.erp.client.MaterialClient;
import org.linlinjava.litemall.admin.erp.rq.MaterialRQ;
import org.linlinjava.litemall.admin.erp.rs.MaterialRS;
import org.linlinjava.litemall.admin.erp.rs.PageModelRS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author ：stephen
 * @date ：Created in 2020/7/1 14:20
 * @description：TODO
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class MaterialTest {

    @Autowired
    private MaterialClient materialClient;

    @Test
    public void listTest(){
        String url = "http://localhost:8083/api/material/list";
        MaterialRQ rq = new MaterialRQ();
        rq.setApiName("material");

        PageModelRS<MaterialRS> materialList = materialClient.getMaterialList(rq, url);
        System.out.println(materialList.toString());
    }
}
