package org.linlinjava.litemall.admin.erp;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.linlinjava.litemall.admin.erp.client.DepotClient;
import org.linlinjava.litemall.admin.erp.rq.PurchaseRQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author ：stephen
 * @date ：Created in 2020/7/1 16:36
 * @description：TODO
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class DepotTest {
    @Autowired
    private DepotClient depotClient;

    @Test
    public void testAdd(){
        String info = "{\"Type\":\"其它\",\"SubType\":\"销售订单\",\"DefaultNumber\":\"XSDD00000000493\",\"Number\":\"XSDD00000000460\",\"LinkNumber\":\"\",\"OperTime\":\"2020-07-01 15:37:02\",\"OrganId\":\"59\",\"HandsPersonId\":\"\",\"Salesman\":\"\",\"AccountId\":\"\",\"ChangeAmount\":0,\"TotalPrice\":\"1.00\",\"PayType\":\"现付\",\"Remark\":\"\",\"AccountMoneyList\":\"\",\"Discount\":\"\",\"DiscountMoney\":\"\",\"DiscountLastMoney\":\"\",\"OtherMoney\":\"\"}";
        String inserted = "[{\"MaterialName\":\"1010_ 咖啡(个)\",\"MType\":\"\",\"MaterialExtendId\":\"10\",\"Stock\":\"118.00\",\"AnotherDepotId\":\"\",\"Unit\":\"个\",\"OperNumber\":\"3\",\"UnitPrice\":\"1\",\"TaxUnitPrice\":\"1.00\",\"AllPrice\":\"1\",\"TaxRate\":\"0\",\"TaxMoney\":\"0.00\",\"TaxLastMoney\":\"1.00\",\"Remark\":\"\"}]";
        String url = "http://localhost:8083/api/depothead/addDepotHeadAndDetail";
        PurchaseRQ rq = new PurchaseRQ();
        rq.setInfo(info);
        rq.setInserted(inserted);
        Object o = depotClient.addDepotHeadAndDetail(rq, url);
        System.out.println(o.toString());
    }

    @Test
    public void testGetBuildNumber(){
        String url = "http://localhost:8083/api/depothead/buildNumber";
        Object o = depotClient.getBuildNumber(url);
        System.out.println(o.toString());
    }

}
