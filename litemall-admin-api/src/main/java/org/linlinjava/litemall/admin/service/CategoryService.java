package org.linlinjava.litemall.admin.service;

import org.linlinjava.litemall.admin.beans.Constants;
import org.linlinjava.litemall.admin.beans.vo.CatVo;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.domain.LitemallCategory;
import org.linlinjava.litemall.db.domain.LitemallGoods;
import org.linlinjava.litemall.db.service.LitemallCategoryService;
import org.linlinjava.litemall.db.service.LitemallGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.linlinjava.litemall.admin.util.AdminResponseCode.GOODS_CATEGORY_HAS_CHILDREN;
import static org.linlinjava.litemall.admin.util.AdminResponseCode.GOODS_CATEGORY_HAS_GOODS;

/**
 * @author ：stephen
 * @date ：Created in 2019/12/17 14:23
 * @description：TODO
 */

@Service
public class CategoryService {

    @Autowired
    private LitemallCategoryService litemallCategoryService;
    @Autowired
    private LitemallGoodsService goodsService;

    public Object queryL2() {
        Map<String,List> data = new HashMap<>();
        // 管理员设置“所属分类”
        List<LitemallCategory> l1CatList = litemallCategoryService.queryL1();
        List<CatVo> categoryList = new ArrayList<>(l1CatList.size());

        for (LitemallCategory l1 : l1CatList) {
            CatVo l1CatVo = new CatVo();
            l1CatVo.setValue(l1.getId());
            l1CatVo.setLabel(l1.getName());
            List<LitemallCategory> l2CatList = litemallCategoryService.queryByPid(l1.getId());
            List<CatVo> children = new ArrayList<>(l2CatList.size());
            for (LitemallCategory l2 : l2CatList) {
                CatVo l2CatVo = new CatVo();
                l2CatVo.setValue(l2.getId());
                l2CatVo.setLabel(l2.getName());
                children.add(l2CatVo);
            }
            l1CatVo.setChildren(children);

            categoryList.add(l1CatVo);
        }

        data.put("categoryList", categoryList);
        return ResponseUtil.ok(data);
    }

    public Object delete(LitemallCategory category){
        List<Integer> categoryIds = new ArrayList<>();
        Integer id = category.getId();
        if (id == null) {
            return ResponseUtil.badArgument();
        }
        LitemallCategory litemallCategory = litemallCategoryService.findById(id);
        if(Constants.CATEGORY_LEVEL_L3.equals(litemallCategory.getLevel())){
            List<LitemallGoods> byCategoryId = goodsService.findByCategoryId(id);
            if(null != byCategoryId && byCategoryId.size() > 0){
                return ResponseUtil.fail(GOODS_CATEGORY_HAS_GOODS, "分类下面有商品不能删除");
            }
        }else{
            List<LitemallCategory> subList = litemallCategoryService.queryByPid(litemallCategory.getId());
            if(null != subList && subList.size() > 0){
                return ResponseUtil.fail(GOODS_CATEGORY_HAS_CHILDREN, "有子分类不能删除");
            }
        }
        litemallCategoryService.deleteById(id);
        return ResponseUtil.ok();
    }
}
