package org.linlinjava.litemall.web.web;

import com.alibaba.fastjson.JSON;
import org.linlinjava.litemall.core.notify.NoticeHelper;
import org.linlinjava.litemall.db.domain.LitemallOrder;
import org.linlinjava.litemall.db.domain.LitemallOrderCash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：stephen
 * @date ：Created in 2020/6/3 9:43
 * @description：TODO
 */
@RestController
@RequestMapping("web/socket")
public class WebWebSocketController {
    @Autowired
    private NoticeHelper noticeHelper;

    @GetMapping("push")
    public void push(Integer shopId){
        LitemallOrder order = new LitemallOrder();
        order.setId(1);
        order.setOrderStatus((short)201);
        noticeHelper.noticeShop((byte)1, JSON.toJSONString(order), shopId);
    }
}
