package org.linlinjava.litemall.admin.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.linlinjava.litemall.admin.beans.annotation.RequiresPermissionsDesc;
import org.linlinjava.litemall.admin.beans.vo.CategoryVo;
import org.linlinjava.litemall.admin.service.CategoryService;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.domain.LitemallCategory;
import org.linlinjava.litemall.db.service.LitemallCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.*;

@RestController
@RequestMapping("/admin/category")
@Validated
public class AdminCategoryController {
    private final Log logger = LogFactory.getLog(AdminCategoryController.class);

    @Autowired
    private LitemallCategoryService litemallCategoryService;
    @Autowired
    private CategoryService categoryService;

    @RequiresPermissions("admin:category:list")
    @RequiresPermissionsDesc(menu = {"商品管理", "类目管理"}, button = "查询")
    @GetMapping("/list")
    public Object list() {
        List<CategoryVo> categoryVoList = new ArrayList<>();

        List<LitemallCategory> categoryList = litemallCategoryService.queryByPid(0);
        for (LitemallCategory category : categoryList) {
            CategoryVo categoryVO = getCategoryVo(category);

            List<CategoryVo> children = new ArrayList<>();
            List<LitemallCategory> subCategoryList = litemallCategoryService.queryByPid(category.getId());
            for (LitemallCategory subCategory : subCategoryList) {
                CategoryVo subCategoryVo = getCategoryVo(subCategory);

                List<CategoryVo> childrenLThree = new ArrayList<>();
                List<LitemallCategory> litemallCategories = litemallCategoryService.queryByPid(subCategory.getId());
                if(!CollectionUtils.isEmpty(litemallCategories)){
                    for(LitemallCategory litemallCategory : litemallCategories){
                        CategoryVo categoryVoThree = getCategoryVo(litemallCategory);
                        childrenLThree.add(categoryVoThree);
                    }
                }

                subCategoryVo.setChildren(childrenLThree);
                children.add(subCategoryVo);
            }

            categoryVO.setChildren(children);
            categoryVoList.add(categoryVO);
        }

        return ResponseUtil.okList(categoryVoList);
    }

    private CategoryVo getCategoryVo(LitemallCategory litemallCategory) {
        CategoryVo categoryVoThree = new CategoryVo();
        categoryVoThree.setId(litemallCategory.getId());
        categoryVoThree.setDesc(litemallCategory.getDesc());
        categoryVoThree.setIconUrl(litemallCategory.getIconUrl());
        categoryVoThree.setPicUrl(litemallCategory.getPicUrl());
        categoryVoThree.setKeywords(litemallCategory.getKeywords());
        categoryVoThree.setName(litemallCategory.getName());
        categoryVoThree.setLevel(litemallCategory.getLevel());
        return categoryVoThree;
    }

    private Object validate(LitemallCategory category) {
        String name = category.getName();
        if (StringUtils.isEmpty(name)) {
            return ResponseUtil.badArgument();
        }

        String level = category.getLevel();
        if (StringUtils.isEmpty(level)) {
            return ResponseUtil.badArgument();
        }
        if (!level.equals("L1") && !level.equals("L2") && !level.equals("L3")) {
            return ResponseUtil.badArgumentValue();
        }

        Integer pid = category.getPid();
        if ((level.equals("L2")||level.equals("L3")) && (pid == null)) {
            return ResponseUtil.badArgument();
        }

        return null;
    }

    @RequiresPermissions("admin:category:create")
    @RequiresPermissionsDesc(menu = {"商品管理", "类目管理"}, button = "添加")
    @PostMapping("/create")
    public Object create(@RequestBody LitemallCategory category) {
        Object error = validate(category);
        if (error != null) {
            return error;
        }
        litemallCategoryService.add(category);
        return ResponseUtil.ok(category);
    }

    @RequiresPermissions("admin:category:read")
    @RequiresPermissionsDesc(menu = {"商品管理", "类目管理"}, button = "详情")
    @GetMapping("/read")
    public Object read(@NotNull Integer id) {
        LitemallCategory category = litemallCategoryService.findById(id);
        return ResponseUtil.ok(category);
    }

    @RequiresPermissions("admin:category:update")
    @RequiresPermissionsDesc(menu = {"商场管理", "类目管理"}, button = "编辑")
    @PostMapping("/update")
    public Object update(@RequestBody LitemallCategory category) {
        Object error = validate(category);
        if (error != null) {
            return error;
        }

        if (litemallCategoryService.updateById(category) == 0) {
            return ResponseUtil.updatedDataFailed();
        }
        return ResponseUtil.ok();
    }

    @RequiresPermissions("admin:category:delete")
    @RequiresPermissionsDesc(menu = {"商品管理", "类目管理"}, button = "删除")
    @PostMapping("/delete")
    public Object delete(@RequestBody LitemallCategory category) {
        return categoryService.delete(category);
    }

    @RequiresPermissions("admin:category:list")
    @GetMapping("/l1")
    public Object catL1() {
        // 所有一级分类目录
        List<LitemallCategory> l1CatList = litemallCategoryService.queryL1();
        List<Map<String, Object>> data = new ArrayList<>(l1CatList.size());
        for (LitemallCategory category : l1CatList) {
            Map<String, Object> d = new HashMap<>(2);
            d.put("value", category.getId());
            d.put("label", category.getName());
            data.add(d);
        }
        return ResponseUtil.okList(data);
    }
    @RequiresPermissions("admin:category:list")
    @GetMapping("/l2")
    public Object catL2() {
        // 所有一、二级分类目录
        return categoryService.queryL2();
    }
}
