package org.linlinjava.litemall.admin.web;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.linlinjava.litemall.admin.beans.annotation.RequiresPermissionsDesc;
import org.linlinjava.litemall.admin.beans.dto.GoodsReviewDto;
import org.linlinjava.litemall.admin.service.GoodsReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author ：stephen
 * @date ：Created in 11/15/2019 3:50 PM
 * @description：商品审批controller
 */
@RestController
@RequestMapping("/admin/goods/")
public class AdminGoodsReviewController {

    @Autowired
    private GoodsReviewService goodsReviewService;

    /**
     * 审核商品通过
     *
     * @param goodsReviewDto
     * @return
     */
    @RequiresPermissions("admin:goods:approve")
    @RequiresPermissionsDesc(menu = {"商品管理", "商品审核"}, button = "通过")
    @PostMapping("/approve")
    public Object create(@RequestBody GoodsReviewDto goodsReviewDto) {
        return goodsReviewService.approve(goodsReviewDto);
    }

    /**
     * 审核商品不通过
     *
     * @param goodsReviewDto
     * @return
     */
    @RequiresPermissions("admin:goods:reject")
    @RequiresPermissionsDesc(menu = {"商品管理", "商品审核"}, button = "不通过")
    @PostMapping("/reject")
    public Object detail(@RequestBody GoodsReviewDto goodsReviewDto) {
        return goodsReviewService.reject(goodsReviewDto);
    }
}
