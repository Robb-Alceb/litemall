package org.linlinjava.litemall.admin.web;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.linlinjava.litemall.admin.beans.annotation.LogAnno;
import org.linlinjava.litemall.admin.beans.annotation.RequiresPermissionsDesc;
import org.linlinjava.litemall.admin.beans.dto.I18nDto;
import org.linlinjava.litemall.admin.service.AdminI18nService;
import org.linlinjava.litemall.core.validator.Order;
import org.linlinjava.litemall.core.validator.Sort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author ：stephen
 * @date ：Created in 2020/5/20 17:39
 * @description：TODO
 */
@RestController
@RequestMapping("/admin/i18n")
public class AdminI18nController {
    @Autowired
    private AdminI18nService i18nService;

    @RequiresPermissions("admin:i18n:list")
    @RequiresPermissionsDesc(menu = {"配置管理", "国际化"}, button = "查询")
    @LogAnno
    @GetMapping("/list")
    public Object list(String key,
                       String type,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @Sort @RequestParam(defaultValue = "add_time") String sort,
                       @Order @RequestParam(defaultValue = "desc") String order){
        return i18nService.list(key, type, page, limit, sort, order);
    }

    @RequiresPermissions("admin:i18n:update")
    @RequiresPermissionsDesc(menu = {"配置管理", "国际化"}, button = "修改")
    @LogAnno
    @PutMapping("/update")
    public Object update(@Valid @RequestBody I18nDto dto){
        return i18nService.update(dto);
    }

    @RequiresPermissions("admin:i18n:create")
    @RequiresPermissionsDesc(menu = {"配置管理", "国际化"}, button = "添加")
    @LogAnno
    @PostMapping("/create")
    public Object create(@Valid @RequestBody I18nDto dto){
        return i18nService.create(dto);
    }

    @RequiresPermissions("admin:i18n:delete")
    @RequiresPermissionsDesc(menu = {"配置管理", "国际化"}, button = "删除")
    @LogAnno
    @DeleteMapping("/delete")
    public Object create(@RequestParam("key") String key){
        return i18nService.delete(key);
    }


    /**
     * 获取国际化值Map
     * @param key
     * @return
     */
    @GetMapping("/get")
    public Object getByKey(@RequestParam("key") String key){
        return i18nService.get(key);
    }
}
