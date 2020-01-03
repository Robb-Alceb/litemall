package org.linlinjava.litemall.admin.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.admin.beans.annotation.LogAnno;
import org.linlinjava.litemall.admin.beans.annotation.LoginAdminShopId;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/admin/dashboard")
@Validated
public class AdminDashbordController {
    private final Log logger = LogFactory.getLog(AdminDashbordController.class);

    @Autowired
    private LitemallUserService userService;
    @Autowired
    private LitemallGoodsService goodsService;
    @Autowired
    private LitemallGoodsProductService productService;
    @Autowired
    private LitemallOrderService orderService;
    @Autowired
    private LitemallMerchandiseService merchandiseService;
    @Autowired
    private LitemallShopMerchandiseService shopMerchandiseService;


    @GetMapping("")
    @LogAnno
    public Object info(@LoginAdminShopId @RequestParam(value = "shopId") Integer shopId) {
        int userTotal = userService.count();
//        int goodsTotal = goodsService.count();
//        int productTotal = productService.count();
//        int orderTotal = orderService.countShop(shopId);
        Map<String, Integer> data = new HashMap<>();
        data.put("userTotal", userTotal);
        data.put("goodsTotal", shopId == null ? goodsService.count() :goodsService.count(shopId));
        data.put("productTotal", shopId == null ? merchandiseService.count() : shopMerchandiseService.count(shopId));
        data.put("orderTotal", shopId == null ? orderService.count() : orderService.countShop(shopId));

        return ResponseUtil.ok(data);
    }

}
