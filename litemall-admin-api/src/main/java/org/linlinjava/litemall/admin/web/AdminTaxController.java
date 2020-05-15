package org.linlinjava.litemall.admin.web;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.linlinjava.litemall.admin.beans.annotation.LogAnno;
import org.linlinjava.litemall.admin.beans.annotation.RequiresPermissionsDesc;
import org.linlinjava.litemall.admin.service.AdminTaxService;
import org.linlinjava.litemall.core.validator.Order;
import org.linlinjava.litemall.core.validator.Sort;
import org.linlinjava.litemall.db.domain.LitemallTax;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;


/**
 * @author ：stephen
 * @date ：Created in 2020/5/13 17:22
 * @description：TODO
 */
@RestController
@RequestMapping("/admin/tax")
public class AdminTaxController {
    @Autowired
    private AdminTaxService adminTaxService;

    @RequiresPermissions("admin:tax:create")
    @RequiresPermissionsDesc(menu = {"税费管理", "税费管理"}, button = "添加")
    @PostMapping("/create")
    @LogAnno
    public Object create(@RequestBody LitemallTax tax){
        return adminTaxService.create(tax);
    }

    @RequiresPermissions("admin:tax:delete")
    @RequiresPermissionsDesc(menu = {"税费管理", "税费管理"}, button = "删除")
    @DeleteMapping("/delete")
    @LogAnno
    public Object delete(@NotNull Integer id){
        return adminTaxService.delete(id);
    }

    @RequiresPermissions("admin:tax:update")
    @RequiresPermissionsDesc(menu = {"税费管理", "税费管理"}, button = "修改")
    @PutMapping("/update")
    @LogAnno
    public Object update(@RequestBody LitemallTax tax){
        return adminTaxService.update(tax);
    }

    @RequiresPermissions("admin:tax:list")
    @RequiresPermissionsDesc(menu = {"税费管理", "税费管理"}, button = "列表")
    @GetMapping("/list")
    @LogAnno
    public Object querySelective(Integer regionId,
                                 String code,
                                 @RequestParam(defaultValue = "1") Integer page,
                                 @RequestParam(defaultValue = "10") Integer limit,
                                 @Sort @RequestParam(defaultValue = "add_time") String sort,
                                 @Order @RequestParam(defaultValue = "desc") String order){
        return adminTaxService.querySelective(regionId, code, page, limit, sort, order);
    }

    @RequiresPermissions("admin:tax:enable")
    @RequiresPermissionsDesc(menu = {"税费管理", "税费管理"}, button = "启用")
    @PutMapping("/enable")
    @LogAnno
    public Object enable(@RequestBody LitemallTax tax){
        return adminTaxService.enable(tax);
    }
}
