package org.linlinjava.litemall.wx.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.domain.LitemallCategory;
import org.linlinjava.litemall.db.service.LitemallCategoryService;
import org.linlinjava.litemall.wx.annotation.LogAnno;
import org.linlinjava.litemall.wx.service.HomeCacheManager;
import org.linlinjava.litemall.wx.vo.CategoryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类目服务
 */
@RestController
@RequestMapping("/wx/catalog")
@Validated
public class WxCatalogController {
    private final Log logger = LogFactory.getLog(WxCatalogController.class);

    @Autowired
    private LitemallCategoryService categoryService;

    /**
     * 分类详情
     *
     * @param id   分类类目ID。
     *             如果分类类目ID是空，则选择第一个分类类目。
     *             需要注意，这里分类类目是一级类目
     * @return 分类详情
     */
    @GetMapping("index")
    public Object index(Integer id) {

        // 所有一级分类目录
        List<LitemallCategory> l1CatList = categoryService.queryL1();

        // 当前一级分类目录
        LitemallCategory currentCategory = null;
        if (id != null) {
            currentCategory = categoryService.findById(id);
        } else {
            currentCategory = l1CatList.get(0);
        }

        // 当前一级分类目录对应的二级分类目录
        List<LitemallCategory> currentSubCategory = null;
        if (null != currentCategory) {
            currentSubCategory = categoryService.queryByPid(currentCategory.getId());
        }

        Map<String, Object> data = new HashMap<String, Object>();
        data.put("categoryList", l1CatList);
        data.put("currentCategory", currentCategory);
        data.put("currentSubCategory", currentSubCategory);
        return ResponseUtil.ok(data);
    }

    /**
     * 所有分类数据
     *
     * @return 所有分类数据
     */
    @GetMapping("all")
    public Object queryAll() {
        //优先从缓存中读取
        if (HomeCacheManager.hasData(HomeCacheManager.CATALOG)) {
            return ResponseUtil.ok(HomeCacheManager.getCacheData(HomeCacheManager.CATALOG));
        }


        // 所有一级分类目录
        List<LitemallCategory> l1CatList = categoryService.queryL1();

        //所有子分类列表
        Map<Integer, List<LitemallCategory>> allList = new HashMap<>();
        List<LitemallCategory> sub;
        for (LitemallCategory category : l1CatList) {
            sub = categoryService.queryByPid(category.getId());
            allList.put(category.getId(), sub);
        }

        // 当前一级分类目录
        LitemallCategory currentCategory = l1CatList.get(0);

        // 当前一级分类目录对应的二级分类目录
        List<LitemallCategory> currentSubCategory = null;
        if (null != currentCategory) {
            currentSubCategory = categoryService.queryByPid(currentCategory.getId());
        }

        Map<String, Object> data = new HashMap<String, Object>();
        data.put("categoryList", l1CatList);
        data.put("allList", allList);
        data.put("currentCategory", currentCategory);
        data.put("currentSubCategory", currentSubCategory);

        //缓存数据
        HomeCacheManager.loadData(HomeCacheManager.CATALOG, data);
        return ResponseUtil.ok(data);
    }

    /**
     * 所有分类数据
     * @return
     */
    @GetMapping("list")
    @LogAnno
    public Object list() {
        List<CategoryVo> categoryVoList = new ArrayList<>();

        List<LitemallCategory> categoryList = categoryService.queryByPid(0);
        for (LitemallCategory category : categoryList) {
            CategoryVo categoryVO = getCategoryVo(category);

            List<CategoryVo> children = new ArrayList<>();
            List<LitemallCategory> subCategoryList = categoryService.queryByPid(category.getId());
            for (LitemallCategory subCategory : subCategoryList) {
                CategoryVo subCategoryVo = getCategoryVo(subCategory);

                List<CategoryVo> childrenLThree = new ArrayList<>();
                List<LitemallCategory> litemallCategories = categoryService.queryByPid(subCategory.getId());
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

    /**
     * 所有分类数据
     * @return
     */
    @GetMapping("list/{level}")
    @LogAnno
    public Object getListByLevel(@PathVariable String level) {
        List<CategoryVo> categoryVoList = new ArrayList<>();
        if(StringUtils.isEmpty(level)){
            return ResponseUtil.okList(categoryVoList);
        }
        List<LitemallCategory> categoryList = categoryService.queryByLevel(level.toLowerCase());
        for (LitemallCategory category : categoryList) {
            CategoryVo categoryVO = getCategoryVo(category);
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

    /**
     * 当前分类栏目
     *
     * @param id 分类类目ID
     * @return 当前分类栏目
     */
    @GetMapping("current")
    @LogAnno
    public Object current(@NotNull Integer id) {
        // 当前分类
        LitemallCategory currentCategory = categoryService.findById(id);
        if(currentCategory == null){
            return ResponseUtil.badArgumentValue();
        }
        List<LitemallCategory> currentSubCategory = categoryService.queryByPid(currentCategory.getId());

        Map<String, Object> data = new HashMap<String, Object>();
        data.put("currentCategory", currentCategory);
        data.put("currentSubCategory", currentSubCategory);
        return ResponseUtil.ok(data);
    }
}