package org.linlinjava.litemall.admin.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.linlinjava.litemall.admin.beans.annotation.LogAnno;
import org.linlinjava.litemall.admin.beans.annotation.LoginAdminShopId;
import org.linlinjava.litemall.admin.beans.annotation.RequiresPermissionsDesc;
import org.linlinjava.litemall.admin.service.ShopLogService;
import org.linlinjava.litemall.core.validator.Order;
import org.linlinjava.litemall.core.validator.Sort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: robb
 * @Date: 2019/11/15 11:25
 * @Version: 1.0
 * @Description: 门店操作日志controller
 */

@RestController
@RequestMapping("/admin/shopLog")
@Validated
public class AdminShopLogController {
    private final Log logger = LogFactory.getLog(AdminShopLogController.class);

    @Autowired
    private ShopLogService shopLogService;

    /**
     * 查询门店操作日志
     *
     * @param content
     * @param page
     * @param limit
     * @param sort
     * @param order
     * @return
     */
    @RequiresPermissions("admin:shopLog:list")
    @RequiresPermissionsDesc(menu = {"门店管理", "操作日志"}, button = "查询")
    @GetMapping("/list")
    @LogAnno
    public Object list(String content,
                       @LoginAdminShopId @RequestParam(value = "shopId") Integer shopId,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @Sort @RequestParam(defaultValue = "add_time") String sort,
                       @Order @RequestParam(defaultValue = "desc") String order) {
        return shopLogService.list(shopId, content, page, limit, sort, order);
    }

}
