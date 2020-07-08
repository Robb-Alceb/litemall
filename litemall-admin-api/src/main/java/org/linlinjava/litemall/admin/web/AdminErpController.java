package org.linlinjava.litemall.admin.web;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.linlinjava.litemall.admin.beans.Constants;
import org.linlinjava.litemall.admin.beans.annotation.LogAnno;
import org.linlinjava.litemall.admin.beans.annotation.LoginAdminShopId;
import org.linlinjava.litemall.admin.beans.annotation.RequiresPermissionsDesc;
import org.linlinjava.litemall.admin.beans.enums.AdminOrderStatusEnum;
import org.linlinjava.litemall.admin.erp.client.DepotClient;
import org.linlinjava.litemall.admin.erp.client.MaterialClient;
import org.linlinjava.litemall.admin.erp.rq.MaterialRQ;
import org.linlinjava.litemall.admin.erp.rq.PurchaseDepotItem;
import org.linlinjava.litemall.admin.erp.rq.PurchaseInfo;
import org.linlinjava.litemall.admin.erp.rq.PurchaseRQ;
import org.linlinjava.litemall.admin.erp.rs.MaterialRS;
import org.linlinjava.litemall.admin.erp.rs.PageModelRS;
import org.linlinjava.litemall.admin.service.AdminAdminOrderService;
import org.linlinjava.litemall.admin.util.AdminResponseEnum;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.domain.*;
import org.linlinjava.litemall.db.service.LitemallAdminOrderMerchandiseService;
import org.linlinjava.litemall.db.service.LitemallAdminOrderService;
import org.linlinjava.litemall.db.service.LitemallMerchandiseLogService;
import org.linlinjava.litemall.db.service.LitemallShopMerchandiseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ：stephen
 * @date ：Created in 2020/7/2 10:19
 * @description：TODO
 */
@RestController
@RequestMapping("/admin/erp")
public class AdminErpController {
    @Autowired
    private MaterialClient materialClient;
    @Autowired
    private DepotClient depotClient;
    @Autowired
    private LitemallShopMerchandiseService litemallShopMerchandiseService;
    @Autowired
    private LitemallAdminOrderService litemallAdminOrderService;
    @Autowired
    private LitemallAdminOrderMerchandiseService litemallAdminOrderMerchandiseService;
    @Autowired
    private LitemallMerchandiseLogService merchandiseLogService;
    @Autowired
    private AdminAdminOrderService adminAdminOrderService;

    private static final String apiName = "material";

    @Value("${erp.material.list.url}")
    private String materialListUrl;
    @Value("${erp.depot.purchase.url}")
    private String depotPurchaseUrl;
    @Value("${erp.depot.billnumber.url}")
    private String billNumberUrl;

    @RequiresPermissions("admin:erp:material:list")
    @RequiresPermissionsDesc(menu = {"erp管理", "erp配置"}, button = "查询erp商品信息列表")
    @LogAnno
    @GetMapping("/material/list")
    public Object list(String name, @LoginAdminShopId Integer shopId,
                     @RequestParam(defaultValue = "1") Integer page,
                     @RequestParam(defaultValue = "10") Integer limit){
        MaterialRQ rq = new MaterialRQ();
        rq.setName(name);
        rq.setApiName(apiName);
        rq.setCurrentPage(page);
        rq.setPageSize(limit);

        PageModelRS<MaterialRS> materialList = materialClient.getMaterialList(rq, materialListUrl);

        if(materialList.getRows() != null && shopId != null){
            //查询门店库存,货品信息来自erp系统，价格和数量来自自己
            for(MaterialRS merchandise : materialList.getRows()){
                LitemallShopMerchandise shopMerchandise = litemallShopMerchandiseService.queryByMerId(merchandise.getId().intValue(), shopId);
                if (shopMerchandise != null) {
                    merchandise.setStock(new BigDecimal(shopMerchandise.getNumber()));
                    merchandise.setSellingPrice(shopMerchandise.getSellPrice());
                } else {
                    merchandise.setStock(new BigDecimal(0.00));
                    merchandise.setSellingPrice(new BigDecimal(0.00));
                }
            }
        }
        return ResponseUtil.ok(materialList);
    }

    @RequiresPermissions("admin:erp:depot:purchase")
    @RequiresPermissionsDesc(menu = {"erp管理", "erp配置"}, button = "同意申请，向erp发起采购")
    @LogAnno
    @GetMapping("/depot/purchase")
    public Object purchase(@NotNull @RequestParam("adminOrderId") Integer adminOrderId){

        PurchaseRQ rq = new PurchaseRQ();
        LitemallAdminOrder order = litemallAdminOrderService.queryById(adminOrderId);
        //当采购单状态为申请时可以向erp发起采购申请
        if(order.getOrderStatus() != AdminOrderStatusEnum.P_1.getCode().byteValue()){
            return ResponseUtil.fail(AdminResponseEnum.APPLY_PURCHASE_ERROR);
        }
        //设置采购的基本信息
        PurchaseInfo info = new PurchaseInfo();
//        info.setSalesman(order.getConsignee());
//        info.setAccountId(String.valueOf(order.getShopId()));
        info.setOrganId(String.valueOf(order.getShopId()));
        info.setRemark(order.getOrderRemark());
        info.setTotalPrice(order.getActualPrice());
        rq.setInfo(JSONObject.toJSONString(info));

        List<LitemallAdminOrderMerchandise> litemallAdminOrderMerchandises = litemallAdminOrderMerchandiseService.querybyAdminOrderId(adminOrderId);
        if(litemallAdminOrderMerchandises != null){
            //设置需要采购的商品
            List<PurchaseDepotItem> collect = litemallAdminOrderMerchandises.stream().map(item -> {
                PurchaseDepotItem depot = new PurchaseDepotItem();
                depot.setMaterialId(String.valueOf(item.getMerchandiseId()));
                depot.setOperNumber(String.valueOf(item.getNumber()));
                return depot;
            }).collect(Collectors.toList());
            rq.setInserted(JSONArray.toJSONString(collect));
        }
        //得到订单编号保存到数据库
        String number = depotClient.addDepotHeadAndDetail(rq, depotPurchaseUrl);

        //保存单据编号，修改订单状态
        LitemallAdminOrder update = new LitemallAdminOrder();
        update.setId(order.getId());
        update.setOrderSn(number);
        update.setOrderStatus(AdminOrderStatusEnum.P_3.getCode().byteValue());
        litemallAdminOrderService.update(update);

        /**
         * 保存日志
         */
        LitemallAdmin admin = (LitemallAdmin) SecurityUtils.getSubject().getPrincipal();
        LitemallMerchandiseLog merchandiseLog = new LitemallMerchandiseLog();
        merchandiseLog.setAddUserId(admin.getId());
        merchandiseLog.setAdminOrderId(order.getId());
        merchandiseLog.setContent(adminAdminOrderService.generatorContent(Constants.ERP_PURCHASE,order, litemallAdminOrderMerchandises));
        merchandiseLog.setShopId(order.getShopId());
        merchandiseLog.setUserName(admin.getUsername());
        merchandiseLogService.insert(merchandiseLog);
        return ResponseUtil.ok();
    }

    @GetMapping("/depot/billnumber")
    public Object billNumber(@LoginAdminShopId Integer shopId){
        return ResponseUtil.ok(depotClient.getBuildNumber(billNumberUrl));
    }
}
