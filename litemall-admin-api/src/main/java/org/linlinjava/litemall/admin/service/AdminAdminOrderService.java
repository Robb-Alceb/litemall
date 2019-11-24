package org.linlinjava.litemall.admin.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.SecurityUtils;
import org.linlinjava.litemall.admin.beans.enumerate.AdminOrderStatusEnum;
import org.linlinjava.litemall.admin.beans.enumerate.AdminPayStatusEnum;
import org.linlinjava.litemall.admin.beans.enumerate.PromptEnum;
import org.linlinjava.litemall.admin.beans.vo.AdminOrderVo;
import org.linlinjava.litemall.admin.util.RandomUtils;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.domain.*;
import org.linlinjava.litemall.db.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;

@Service

public class AdminAdminOrderService {
    private final Log logger = LogFactory.getLog(AdminAdminOrderService.class);

    @Autowired
    private LitemallAdminOrderService adminOrderService;
    @Autowired
    private LitemallAdminOrderMerchandiseService adminOrderMerchandiseService;
    @Autowired
    private LitemallMerchandiseService merchandiseService;
    @Autowired
    private LitemallShopService shopService;


    /**
     * 调货申请列表
     * @return
     */
    public Object list(String orderSn, String userName, String address, Integer shopId,
                       Integer page, Integer limit, String sort, String order) {
        return ResponseUtil.okList(adminOrderService.querySelective(orderSn, userName, address, shopId, page, limit, sort, order));
    }

    /**
     * 订单申请
     * @return
     */
    public Object orderApplying(AdminOrderVo adminOrderVo) {

        if(ObjectUtils.isEmpty(adminOrderVo.getMerchandiseId()) || ObjectUtils.isEmpty(adminOrderVo.getShopId())
                || ObjectUtils.isEmpty(adminOrderVo.getUserId())){
            return ResponseUtil.fail(PromptEnum.P_101.getCode(), PromptEnum.P_101.getDesc());
        }
        //查询货品信息
        LitemallMerchandise merchandise = merchandiseService.queryById(adminOrderVo.getMerchandiseId());
        //新增订单
        LitemallAdminOrder adminOrder = saveAdminOrder(adminOrderVo, merchandise);
        //新增订单货品
        saveAdminOrderMerchandise(adminOrderVo, merchandise, adminOrder);
        //扣除货品剩余数量
        LitemallMerchandise m = new LitemallMerchandise();
        m.setId(adminOrderVo.getMerchandiseId());
        m.setNumber(m.getNumber()-adminOrderVo.getNumber());
        merchandiseService.updateById(m);

        return ResponseUtil.ok();
    }

    /**
     * 同意调货
     * @return
     */
    public Object orderPass(AdminOrderVo adminOrderVo) {

        if(ObjectUtils.isEmpty(adminOrderVo.getAdminOrderId())){
            return ResponseUtil.fail(PromptEnum.P_101.getCode(), PromptEnum.P_101.getDesc());
        }

        updateOrderStatus(adminOrderVo, AdminOrderStatusEnum.P_2.getCode().toString());
        return ResponseUtil.ok();
    }

    /**
     * 不同意调货
     * @return
     */
    public Object orderNoPass(AdminOrderVo adminOrderVo) {

        if(ObjectUtils.isEmpty(adminOrderVo.getAdminOrderId())){
            return ResponseUtil.fail(PromptEnum.P_101.getCode(), PromptEnum.P_101.getDesc());
        }

        updateOrderStatus(adminOrderVo, AdminOrderStatusEnum.P_6.getCode().toString());
        //货品数量返回
        backMerchandiseNum(adminOrderVo);

        return ResponseUtil.ok();
    }

    /**
     * 支付货款
     * @return
     */
    public Object orderPay(AdminOrderVo adminOrderVo) {

        if(ObjectUtils.isEmpty(adminOrderVo.getAdminOrderId())){
            return ResponseUtil.fail(PromptEnum.P_101.getCode(), PromptEnum.P_101.getDesc());
        }

        updateOrderStatus(adminOrderVo, AdminOrderStatusEnum.P_3.getCode().toString());
        return ResponseUtil.ok();
    }

    /**
     * 同意发货
     * @return
     */
    public Object deliverGoods(AdminOrderVo adminOrderVo) {

        if(ObjectUtils.isEmpty(adminOrderVo.getAdminOrderId())){
            return ResponseUtil.fail(PromptEnum.P_101.getCode(), PromptEnum.P_101.getDesc());
        }

        updateOrderStatus(adminOrderVo, AdminOrderStatusEnum.P_4.getCode().toString());
        return ResponseUtil.ok();
    }

    /**
     * 取消发货
     * @return
     */
    public Object cancelDeliverGoods(AdminOrderVo adminOrderVo) {

        if(ObjectUtils.isEmpty(adminOrderVo.getAdminOrderId())){
            return ResponseUtil.fail(PromptEnum.P_101.getCode(), PromptEnum.P_101.getDesc());
        }
        updateOrderStatus(adminOrderVo, AdminOrderStatusEnum.P_6.getCode().toString());
        //货品数量返回
        backMerchandiseNum(adminOrderVo);
        return ResponseUtil.ok();
    }

    private void backMerchandiseNum(AdminOrderVo adminOrderVo) {
        //货品数量返回
        LitemallMerchandise m = new LitemallMerchandise();
        m.setId(adminOrderVo.getMerchandiseId());
        m.setNumber(m.getNumber() + adminOrderVo.getNumber());
        merchandiseService.updateById(m);
    }

    /**
     * 确认收货
     * @return
     */
    public Object takeDelivery(AdminOrderVo adminOrderVo) {

        if(ObjectUtils.isEmpty(adminOrderVo.getAdminOrderId())){
            return ResponseUtil.fail(PromptEnum.P_101.getCode(), PromptEnum.P_101.getDesc());
        }

        updateOrderStatus(adminOrderVo, AdminOrderStatusEnum.P_5.getCode().toString());
        return ResponseUtil.ok();
    }

    private void updateOrderStatus(AdminOrderVo adminOrderVo, String orderStatus) {
        LitemallAdminOrder adminOrder = new LitemallAdminOrder();
        adminOrder.setId(adminOrderVo.getAdminOrderId());
        if(AdminOrderStatusEnum.P_3.getCode().toString().equals(orderStatus)){
            adminOrder.setPayStatus(Byte.valueOf(AdminPayStatusEnum.P_2.getCode().toString()));
            adminOrder.setPayTime(LocalDateTime.now());
        }
        if(AdminOrderStatusEnum.P_4.getCode().toString().equals(orderStatus)){
            adminOrder.setShipSn(adminOrderVo.getShipSn());
        }
        if(AdminOrderStatusEnum.P_6.getCode().toString().equals(orderStatus)){
            adminOrder.setOrderRemark(adminOrderVo.getRemark());
        }
        adminOrder.setOrderStatus(Byte.valueOf(orderStatus));
        adminOrderService.update(adminOrder);
    }

    private void saveAdminOrderMerchandise(AdminOrderVo adminOrderVo, LitemallMerchandise merchandise, LitemallAdminOrder adminOrder) {
        LitemallAdminOrderMerchandise adminOrderMerchandise = new LitemallAdminOrderMerchandise();
        adminOrderMerchandise.setAdminOrderId(adminOrder.getId());
        adminOrderMerchandise.setMerchandiseId(adminOrderVo.getMerchandiseId());
        adminOrderMerchandise.setMerchandiseName(merchandise.getName());
        adminOrderMerchandise.setMerchandiseSn(merchandise.getMerchandiseSn());
        adminOrderMerchandise.setNumber((short)adminOrderVo.getNumber());
        adminOrderMerchandise.setPrice(adminOrderVo.getOrderPrice());
        adminOrderMerchandise.setPicUrl(merchandise.getPicUrl());
        adminOrderMerchandiseService.insert(adminOrderMerchandise);
    }

    private LitemallAdminOrder saveAdminOrder(AdminOrderVo adminOrderVo, LitemallMerchandise merchandise) {
        //查询用户信息
        LitemallAdmin admin = (LitemallAdmin)SecurityUtils.getSubject().getPrincipal();
        //查询门店信息
        LitemallShop shop = shopService.findById(adminOrderVo.getShopId());
        LitemallAdminOrder adminOrder = new LitemallAdminOrder();
        adminOrder.setAdminId(adminOrderVo.getUserId());
        adminOrder.setUsername(admin.getUsername());
        adminOrder.setShopId(adminOrderVo.getShopId());
        adminOrder.setShopName(shop.getName());
        adminOrder.setOrderRemark(adminOrderVo.getRemark());
        adminOrder.setOrderSn(RandomUtils.getMerchandiseOrderId());
        adminOrder.setConsignee(adminOrderVo.getConsignee());
        adminOrder.setMobile(adminOrderVo.getMobile());
        adminOrder.setAddress(adminOrderVo.getAddress());
        adminOrder.setUnitPrice(merchandise.getPurchasePrice());
        adminOrder.setOrderPrice(adminOrderVo.getOrderPrice());
        adminOrderService.insert(adminOrder);
        return adminOrder;
    }
}
