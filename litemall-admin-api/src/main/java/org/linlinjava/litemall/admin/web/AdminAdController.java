package org.linlinjava.litemall.admin.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.linlinjava.litemall.admin.beans.Constants;
import org.linlinjava.litemall.admin.beans.annotation.LogAnno;
import org.linlinjava.litemall.admin.beans.annotation.LoginAdminShopId;
import org.linlinjava.litemall.admin.beans.annotation.RequiresPermissionsDesc;
import org.linlinjava.litemall.admin.service.AdService;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.core.validator.Order;
import org.linlinjava.litemall.core.validator.Sort;
import org.linlinjava.litemall.db.domain.LitemallAd;
import org.linlinjava.litemall.db.service.LitemallAdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/admin/ad")
@Validated
public class AdminAdController {
    private final Log logger = LogFactory.getLog(AdminAdController.class);

    @Autowired
    private LitemallAdService litemallAdService;
    @Autowired
    private AdService adService;

    @RequiresPermissions("admin:ad:list")
    @RequiresPermissionsDesc(menu = {"推广管理", "广告管理"}, button = "查询")
    @GetMapping("/list")
    @LogAnno
    public Object list(String name, String content,
                       @LoginAdminShopId Integer shopId,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @Sort @RequestParam(defaultValue = "add_time") String sort,
                       @Order @RequestParam(defaultValue = "desc") String order) {
        List<LitemallAd> adList = litemallAdService.querySelective(shopId, name, content, page, limit, sort, order);
        return ResponseUtil.okList(adList);
    }

    private Object validate(LitemallAd ad) {
        String name = ad.getName();
        if (StringUtils.isEmpty(name)) {
            return ResponseUtil.badArgument();
        }
        String content = ad.getContent();
        if (StringUtils.isEmpty(content)) {
            return ResponseUtil.badArgument();
        }
        return null;
    }

    @RequiresPermissions("admin:ad:create")
    @RequiresPermissionsDesc(menu = {"推广管理", "广告管理"}, button = "添加")
    @PostMapping("/create")
    @LogAnno
    public Object create(@RequestBody LitemallAd ad, @LoginAdminShopId Integer shopId) {
        Object error = validate(ad);
        if (error != null) {
            return error;
        }
        if(null != shopId){
            ad.setType(Constants.AD_TYPE_SHOP);
        }
        litemallAdService.add(ad);
        return ResponseUtil.ok(ad);
    }

    @RequiresPermissions("admin:ad:read")
    @RequiresPermissionsDesc(menu = {"推广管理", "广告管理"}, button = "详情")
    @GetMapping("/read")
    @LogAnno
    public Object read(@NotNull Integer id) {
        LitemallAd ad = litemallAdService.findById(id);
        return ResponseUtil.ok(ad);
    }

    @RequiresPermissions("admin:ad:update")
    @RequiresPermissionsDesc(menu = {"推广管理", "广告管理"}, button = "编辑")
    @PostMapping("/update")
    @LogAnno
    public Object update(@RequestBody LitemallAd ad, @LoginAdminShopId Integer shopId) {
        Object error = validate(ad);
        if (error != null) {
            return error;
        }
        if(null != shopId){
            ad.setType(Constants.AD_TYPE_SHOP);
        }
        return adService.update(ad, shopId);
    }

    @RequiresPermissions("admin:ad:delete")
    @RequiresPermissionsDesc(menu = {"推广管理", "广告管理"}, button = "删除")
    @PostMapping("/delete")
    @LogAnno
    public Object delete(@RequestBody LitemallAd ad) {
        Integer id = ad.getId();
        if (id == null) {
            return ResponseUtil.badArgument();
        }
        litemallAdService.deleteById(id);
        return ResponseUtil.ok();
    }

}
