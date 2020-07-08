package org.linlinjava.litemall.admin.erp.api;

import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.SecurityUtils;
import org.linlinjava.litemall.admin.beans.enums.AdminOrderStatusEnum;
import org.linlinjava.litemall.db.domain.LitemallAdmin;
import org.linlinjava.litemall.db.domain.LitemallAdminOrder;
import org.linlinjava.litemall.db.domain.LitemallMerchandise;
import org.linlinjava.litemall.db.domain.LitemallMerchandiseLog;
import org.linlinjava.litemall.db.service.LitemallAdminOrderService;
import org.linlinjava.litemall.db.service.LitemallMerchandiseLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ：stephen
 * @date ：Created in 2020/7/7 16:20
 * @description：TODO
 */

@RestController
@RequestMapping("/api/merchandise")
public class ShopMerchandiseController {
    @Autowired
    private LitemallAdminOrderService litemallAdminOrderService;
    @Autowired
    private LitemallMerchandiseLogService merchandiseLogService;

    @PostMapping("/done/{number}")
    public Object done(@PathVariable String number, ServletResponse response) throws Exception{
        HttpServletResponse servletResponse = (HttpServletResponse) response;
        LitemallAdminOrder adminOrder = litemallAdminOrderService.getByOrderSn(number);

        if(adminOrder != null){
            LitemallAdminOrder update = new LitemallAdminOrder();
            update.setId(adminOrder.getId());
            update.setOrderStatus(AdminOrderStatusEnum.P_4.getCode().byteValue());
            litemallAdminOrderService.update(update);

            //记录日志
            LitemallMerchandiseLog merchandiseLog = new LitemallMerchandiseLog();
            merchandiseLog.setAdminOrderId(adminOrder.getAdminId());
            merchandiseLog.setContent("erp发货成功");
            merchandiseLog.setOrderSn(adminOrder.getOrderSn());
            merchandiseLog.setPayPrice(adminOrder.getOrderPrice());
            merchandiseLog.setShopId(adminOrder.getShopId());
            merchandiseLogService.add(merchandiseLog);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("msg","修改成功");
            return jsonObject;
        }
        servletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("error","修改失败");
        return jsonObject;
    };
}
