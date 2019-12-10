package org.linlinjava.litemall.admin.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.admin.beans.Constants;
import org.linlinjava.litemall.admin.beans.dto.GoodsAllinone;
import org.linlinjava.litemall.admin.beans.dto.GoodsReviewDto;
import org.linlinjava.litemall.admin.beans.dto.GoodsStatusDto;
import org.linlinjava.litemall.admin.beans.vo.CatVo;
import org.linlinjava.litemall.admin.beans.vo.GoodsVo;
import org.linlinjava.litemall.core.qcode.QCodeService;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.domain.*;
import org.linlinjava.litemall.db.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.linlinjava.litemall.admin.util.AdminResponseCode.GOODS_NAME_EXIST;

@Service
public class AdminGoodsService {
    private final Log logger = LogFactory.getLog(AdminGoodsService.class);

    @Autowired
    private LitemallGoodsService goodsService;
    @Autowired
    private LitemallGoodsSpecificationService specificationService;
    @Autowired
    private LitemallGoodsAttributeService attributeService;
    @Autowired
    private LitemallGoodsProductService productService;
    @Autowired
    private LitemallCategoryService categoryService;
    @Autowired
    private LitemallBrandService brandService;
    @Autowired
    private LitemallOrderGoodsService orderGoodsService;
    @Autowired
    private QCodeService qCodeService;
    @Autowired
    private LitemallGoodsLogService goodsLogService;
    @Autowired
    private GoodsReviewService goodsReviewService;
    @Autowired
    private LitemallVipGoodsService vipGoodsService;
    @Autowired
    private LitemallGoodsLadderPriceService goodsLadderPriceService;
    @Autowired
    private LitemallGoodsMaxMinusPriceService goodsMaxMinusPriceService;

    /**
     * 商品列表
     * @return
     */
    public Object list(String goodsSn, String name,Integer shopId,
                       Integer page, Integer limit, String sort, String order) {
        /*if(shopId!=null){
            //查询门店商品
            List<Map<String, Object>> shops = shopGoodsService.querySelective(goodsSn, name, shopId,
                    page, limit, sort, order);
            List<GoodsVo> goodsVos =  getShopGoodsVos(shops);
            return ResponseUtil.okList(goodsVos, shops);
        }else{*/
        List<LitemallGoods> goodsList = goodsService.querySelective(goodsSn, name, shopId, page, limit, sort, order);
        List<GoodsVo> goodsVos = new ArrayList<>();
        getGoodsVos(goodsList, goodsVos);
        return ResponseUtil.okList(goodsVos, goodsList);
    }

    private Object validate(GoodsAllinone goodsAllinone) {
        LitemallGoods goods = goodsAllinone.getGoods();
        String name = goods.getName();
        if (StringUtils.isEmpty(name)) {
            return ResponseUtil.badArgument();
        }
        String goodsSn = goods.getGoodsSn();
        if (StringUtils.isEmpty(goodsSn)) {
            return ResponseUtil.badArgument();
        }
        // 品牌商可以不设置，如果设置则需要验证品牌商存在
        Integer brandId = goods.getBrandId();
        if (brandId != null && brandId != 0) {
            if (brandService.findById(brandId) == null) {
                return ResponseUtil.badArgumentValue();
            }
        }
        // 分类可以不设置，如果设置则需要验证分类存在
        Integer categoryId = goods.getCategoryId();
        if (categoryId != null && categoryId != 0) {
            if (categoryService.findById(categoryId) == null) {
                return ResponseUtil.badArgumentValue();
            }
        }

        LitemallGoodsAttribute[] attributes = goodsAllinone.getAttributes();
        for (LitemallGoodsAttribute attribute : attributes) {
            String attr = attribute.getAttribute();
            if (StringUtils.isEmpty(attr)) {
                return ResponseUtil.badArgument();
            }
            String value = attribute.getValue();
            if (StringUtils.isEmpty(value)) {
                return ResponseUtil.badArgument();
            }
        }

        LitemallGoodsSpecification[] specifications = goodsAllinone.getSpecifications();
        for (LitemallGoodsSpecification specification : specifications) {
            String spec = specification.getSpecification();
            if (StringUtils.isEmpty(spec)) {
                return ResponseUtil.badArgument();
            }
            String value = specification.getValue();
            if (StringUtils.isEmpty(value)) {
                return ResponseUtil.badArgument();
            }
        }

        LitemallGoodsProduct[] products = goodsAllinone.getProducts();
        for (LitemallGoodsProduct product : products) {
            Integer number = product.getNumber();
            if (number == null || number < 0) {
                return ResponseUtil.badArgument();
            }

            BigDecimal price = product.getSellPrice();
            if (price == null) {
                return ResponseUtil.badArgument();
            }

/*            String[] productSpecifications = product.getSpecifications();
            if (productSpecifications.length == 0) {
                return ResponseUtil.badArgument();
            }*/
        }


        return null;
    }

    /**
     * 编辑商品
     * <p>
     * TODO
     * 目前商品修改的逻辑是
     * 1. 更新litemall_goods、litemall_goods_product、
     * 2. 逻辑删除litemall_goods_specification、litemall_goods_attribute、litemall_goods_ladder_price、litemall_goods_max_minus_price、litemall_vip_goods_price表
     * 3. 添加litemall_goods_specification、litemall_goods_attribute、litemall_goods_product、litemall_goods_ladder_price、litemall_goods_max_minus_price、litemall_vip_goods_price表
     * <p>
     * 这里商品5个表的数据采用删除再添加的策略是因为
     * 商品编辑页面，支持管理员添加删除商品规格、添加删除商品属性，因此这里仅仅更新是不可能的，
     * 只能删除5个表旧的数据，然后添加新的数据。
     */
    @Transactional
    public Object update(GoodsAllinone goodsAllinone, Integer shopId) {
        //更新门店内商品
//        if(!ObjectUtils.isEmpty(shopId)){
//            LitemallShopGoods litemallShopGoods = goodsAllinone.getShopGoods();
//            shopGoodsService.updateById(litemallShopGoods);
//            return ResponseUtil.ok();
//        }
        Object error = validate(goodsAllinone);
        if (error != null) {
            return error;
        }


        LitemallGoods goods = goodsAllinone.getGoods();
        LitemallGoodsAttribute[] attributes = goodsAllinone.getAttributes();
        LitemallGoodsSpecification[] specifications = goodsAllinone.getSpecifications();
        LitemallGoodsProduct[] products = goodsAllinone.getProducts();
        LitemallVipGoodsPrice vipGoodsPrice = goodsAllinone.getVipPrice();
        LitemallGoodsLadderPrice[] ladderPrices = goodsAllinone.getLadderPrices();
        LitemallGoodsMaxMinusPrice[] maxMinusPrices = goodsAllinone.getMaxMinusPrices();

        Integer id = goods.getId();
        LitemallGoods litemallGoods = goodsService.findById(id);
        if(litemallGoods.getIsOnSale()){
            return ResponseUtil.fail(1,"商品已经上架不能修改");
        }

        //将生成的分享图片地址写入数据库
        String url = qCodeService.createGoodShareImage(goods.getId().toString(), goods.getPicUrl(), goods.getName());
        goods.setShareUrl(url);

        // 商品基本信息表litemall_goods
        if (goodsService.updateById(goods) == 0) {
            throw new RuntimeException("更新数据失败");
        }
        saveGoodsLog(goods, Constants.UPDATE_GOODS);
        Integer gid = goods.getId();
        specificationService.deleteByGid(gid);
        attributeService.deleteByGid(gid);
//        productService.deleteByGid(gid);
        goodsLadderPriceService.deleteByGoodsId(gid);
        goodsMaxMinusPriceService.deleteByGoodsId(gid);

        // 商品规格表litemall_goods_specification
        for (LitemallGoodsSpecification specification : specifications) {
            specification.setGoodsId(goods.getId());
            specificationService.add(specification);
        }

        // 商品参数表litemall_goods_attribute
        for (LitemallGoodsAttribute attribute : attributes) {
            attribute.setGoodsId(goods.getId());
            attributeService.add(attribute);
        }

        // 商品货品表litemall_product
        for (LitemallGoodsProduct product : products) {
            product.setGoodsId(goods.getId());
            productService.updateByGoodsId(product);
        }

        if(null != vipGoodsPrice){
            if(vipGoodsPrice.getDiamondVipPrice() != null && vipGoodsPrice.getGoldVipPrice() != null || vipGoodsPrice.getPlatinumVipPrice() != null || vipGoodsPrice.getSilverVipPrice() != null){
                vipGoodsPrice.setGoodsId(goods.getId());
                vipGoodsPrice.setGoodsName(goods.getName());
                vipGoodsService.add(vipGoodsPrice);
            }
        }


        if(null != ladderPrices){
            for(LitemallGoodsLadderPrice ladderPrice : ladderPrices){
                if(null != ladderPrice.getNumber() && null != ladderPrice.getPrice()){
                    ladderPrice.setGoodsId(goods.getId());
                    ladderPrice.setGoodsName(goods.getName());
                    goodsLadderPriceService.add(ladderPrice);
                }
            }
        }

        if(null != maxMinusPrices) {
            for (LitemallGoodsMaxMinusPrice maxMinusPrice : maxMinusPrices) {
                if (null != maxMinusPrice.getMaxPrice() && null != maxMinusPrice.getMinusPrice()) {
                    maxMinusPrice.setGoodsId(goods.getId());
                    maxMinusPrice.setGoodsName(goods.getName());
                    goodsMaxMinusPriceService.add(maxMinusPrice);
                }
            }
        }

        return ResponseUtil.ok();
    }

    @Transactional
    public Object delete(LitemallGoods goods, Integer shopId) {
        //门店删除商品
//        if(!ObjectUtils.isEmpty(shopId)){
//            shopGoodsService.deleteById(goods.getId());
//            return ResponseUtil.ok();
//        }
        Integer id = goods.getId();
        if (id == null) {
            return ResponseUtil.badArgument();
        }

        Integer gid = goods.getId();
        saveGoodsLog(goods, Constants.DELETE_GOODS);
        goodsService.deleteById(gid);
        specificationService.deleteByGid(gid);
        attributeService.deleteByGid(gid);
        productService.deleteByGid(gid);
        goodsLadderPriceService.deleteByGoodsId(gid);
        goodsMaxMinusPriceService.deleteByGoodsId(gid);
        vipGoodsService.deleteByGoodsId(gid);
        return ResponseUtil.ok();
    }

    @Transactional
    public Object create(GoodsAllinone goodsAllinone) {
        Object error = validate(goodsAllinone);
        if (error != null) {
            return error;
        }

        LitemallGoods goods = goodsAllinone.getGoods();
        LitemallGoodsAttribute[] attributes = goodsAllinone.getAttributes();
        LitemallGoodsSpecification[] specifications = goodsAllinone.getSpecifications();
        LitemallGoodsProduct[] products = goodsAllinone.getProducts();
        LitemallVipGoodsPrice vipGoodsPrice = goodsAllinone.getVipPrice();
        LitemallGoodsLadderPrice[] ladderPrices = goodsAllinone.getLadderPrices();
        LitemallGoodsMaxMinusPrice[] maxMinusPrices = goodsAllinone.getMaxMinusPrices();

        String name = goods.getName();
        Integer shopId = goods.getShopId();
        if (goodsService.checkExistByName(shopId, name)) {
            return ResponseUtil.fail(GOODS_NAME_EXIST, "此门店商品名已经存在");
        }

        // 商品基本信息表litemall_goods
        goodsService.add(goods);
        saveGoodsLog(goods, Constants.CREATE_GOODS);
        //将生成的分享图片地址写入数据库
        String url = qCodeService.createGoodShareImage(goods.getId().toString(), goods.getPicUrl(), goods.getName());
        if (!StringUtils.isEmpty(url)) {
            goods.setShareUrl(url);
            if (goodsService.updateById(goods) == 0) {
                throw new RuntimeException("更新数据失败");
            }
        }

        // 商品规格表litemall_goods_specification
        for (LitemallGoodsSpecification specification : specifications) {
            specification.setGoodsId(goods.getId());
            specificationService.add(specification);
        }

        // 商品参数表litemall_goods_attribute
        for (LitemallGoodsAttribute attribute : attributes) {
            attribute.setGoodsId(goods.getId());
            attributeService.add(attribute);
        }

        // 商品货品表litemall_product
        for (LitemallGoodsProduct product : products) {
            product.setGoodsId(goods.getId());
            productService.add(product);
        }

        // vip价格
        if(null != vipGoodsPrice){
            vipGoodsPrice.setGoodsId(goods.getId());
            vipGoodsPrice.setGoodsName(goods.getName());
            vipGoodsService.updateByGoodsId(vipGoodsPrice);
        }

        //阶梯价格
        for(LitemallGoodsLadderPrice ladderPrice : ladderPrices){
            ladderPrice.setGoodsId(goods.getId());
            ladderPrice.setGoodsName(goods.getName());
            goodsLadderPriceService.add(ladderPrice);
        }

        //满减价格
        for(LitemallGoodsMaxMinusPrice maxMinusPrice : maxMinusPrices){
            maxMinusPrice.setGoodsId(goods.getId());
            maxMinusPrice.setGoodsName(goods.getName());
            goodsMaxMinusPriceService.add(maxMinusPrice);
        }
        return ResponseUtil.ok();
    }

    private void saveGoodsLog(LitemallGoods goods, String content) {
        LitemallGoods litemallGoods = goodsService.findById(goods.getId());
        GoodsReviewDto goodsReviewDto = new GoodsReviewDto();
        goodsReviewDto.setId(goods.getId());
        goodsReviewDto.setGoodsName(litemallGoods.getName());
        goodsReviewDto.setGoodsSn(litemallGoods.getGoodsSn());
        goodsReviewDto.setContent(content+litemallGoods.getName());
        goodsReviewService.saveLog(goodsReviewDto, null);
    }

    public Object list2() {
        // http://element-cn.eleme.io/#/zh-CN/component/cascader
        // 管理员设置“所属分类”
        List<LitemallCategory> l1CatList = categoryService.queryL1();
        List<CatVo> categoryList = new ArrayList<>(l1CatList.size());

        for (LitemallCategory l1 : l1CatList) {
            CatVo l1CatVo = new CatVo();
            l1CatVo.setValue(l1.getId());
            l1CatVo.setLabel(l1.getName());

            List<LitemallCategory> l2CatList = categoryService.queryByPid(l1.getId());
            List<CatVo> children = new ArrayList<>(l2CatList.size());
            for (LitemallCategory l2 : l2CatList) {
                CatVo l2CatVo = new CatVo();
                l2CatVo.setValue(l2.getId());
                l2CatVo.setLabel(l2.getName());
                children.add(l2CatVo);

                List<LitemallCategory> l3CatList = categoryService.queryByPid(l2.getId());
                List<CatVo> c3 = new ArrayList<>(l3CatList.size());
                for(LitemallCategory l3 : l3CatList){
                    CatVo l3CatVo = new CatVo();
                    l3CatVo.setValue(l3.getId());
                    l3CatVo.setLabel(l3.getName());
                    c3.add(l3CatVo);
                }
                l2CatVo.setChildren(c3);
            }
            l1CatVo.setChildren(children);

            categoryList.add(l1CatVo);
        }

        // http://element-cn.eleme.io/#/zh-CN/component/select
        // 管理员设置“所属品牌商”
        List<LitemallBrand> list = brandService.all();
        List<Map<String, Object>> brandList = new ArrayList<>(l1CatList.size());
        for (LitemallBrand brand : list) {
            Map<String, Object> b = new HashMap<>(2);
            b.put("value", brand.getId());
            b.put("label", brand.getName());
            brandList.add(b);
        }

        Map<String, Object> data = new HashMap<>();
        data.put("categoryList", categoryList);
        data.put("brandList", brandList);
        return ResponseUtil.ok(data);
    }

    public Object detail(Integer id, Integer shopId) {
        LitemallGoods goods = goodsService.findById(id);
        //查询门店商品
//        if(!ObjectUtils.isEmpty(shopId)){
//            LitemallShopGoods shopGoods = shopGoodsService.queryByShopIdAndGoodsid(shopId, id);
//            return ResponseUtil.ok(new HashMap<String, Object>(){{put("goods", goods);put("shopGoods", shopGoods);}});
//        }
        List<LitemallGoodsProduct> products = productService.queryByGid(id);
        List<LitemallGoodsSpecification> specifications = specificationService.queryByGid(id);
        List<LitemallGoodsAttribute> attributes = attributeService.queryByGid(id);
        LitemallVipGoodsPrice vipGoodsPrice = vipGoodsService.queryByGoodsId(id);
        List<LitemallGoodsLadderPrice> ladderPrices = goodsLadderPriceService.queryByGoodsId(id);
        List<LitemallGoodsMaxMinusPrice> maxMinusPrices = goodsMaxMinusPriceService.queryByGoodsId(id);

        Integer categoryId = goods.getCategoryId();
        LitemallCategory category = categoryService.findById(categoryId);
        Integer[] categoryIds = new Integer[]{};
        if (category != null) {
            Integer parentCategoryId = category.getPid();
            LitemallCategory c2 = categoryService.findById(parentCategoryId);
            categoryIds = new Integer[]{c2.getPid(), parentCategoryId, categoryId};
        }

        Map<String, Object> data = new HashMap<>();
        data.put("goods", goods);
        data.put("specifications", specifications);
        data.put("products", products);
        data.put("attributes", attributes);
        data.put("categoryIds", categoryIds);
        data.put("vipGoodsPrice", vipGoodsPrice);
        data.put("ladderPrices", ladderPrices);
        data.put("maxMinusPrices", maxMinusPrices);

        return ResponseUtil.ok(data);
    }

    /**
     * 商品日志列表
     * @param userName
     * @param content
     * @param page
     * @param limit
     * @param sort
     * @param order
     * @return
     */
    public Object queryGoodsLogList(Integer goodsId, String userName, String content, Integer page,
                                    Integer limit, String sort, String order){
        return ResponseUtil.okList(goodsLogService.querySelective(goodsId, userName, content, page, limit, sort, order));
    }

    /**
     * 修改商品上架、推荐、新品
     * @param goodsStatusDto
     * @param shopId
     * @return
     */
    public Object updateGoodsStatus( GoodsStatusDto goodsStatusDto, Integer shopId){
        LitemallGoods goods = new LitemallGoods();
        goods.setId(goodsStatusDto.getId());
        if(null != goodsStatusDto.getIsHot()){
            if(goodsStatusDto.getIsHot()){
                saveGoodsLog(goods, Constants.GOODS_COMMENTED);
            }else{
                saveGoodsLog(goods, Constants.GOODS_COMMENTED_NOT);
            }
            goods.setIsHot(goodsStatusDto.getIsHot());
        }
        if(null != goodsStatusDto.getIsNew()){
            if(goodsStatusDto.getIsNew()){
                saveGoodsLog(goods, Constants.GOODS_NEW);
            }else{
                saveGoodsLog(goods, Constants.GOODS_NEW_NOT);
            }
            goods.setIsNew(goodsStatusDto.getIsNew());
        }
        if(null != goodsStatusDto.getIsOnSale()){
            if(goodsStatusDto.getIsOnSale()){
                saveGoodsLog(goods, Constants.GOODS_PUSH);
            }else{
                saveGoodsLog(goods, Constants.GOODS_PUSH_NOT);
            }
            goods.setIsOnSale(goodsStatusDto.getIsOnSale());
        }
        if(null != shopId){
            goods.setShopId(shopId);
            goodsService.updateByIdAndShop(goods);
            return ResponseUtil.ok();
        }else{
            goodsService.updateById(goods);
            return ResponseUtil.ok();
        }
    }

    private void getGoodsVos(List<LitemallGoods> goodsList, List<GoodsVo> goodsVos) {
        if(!CollectionUtils.isEmpty(goodsList)){
            goodsList.stream().forEach(goods->{
                GoodsVo goodsVo = new GoodsVo();
                BeanUtils.copyProperties(goods, goodsVo);
                //库存查询
                goodsVo.setNumber(productService.queryByGid(goodsVo.getId()).get(0).getNumber());
                goodsVo.setRetailPrice(productService.queryByGid(goodsVo.getId()).get(0).getSellPrice());
                //销量查询
                List<LitemallOrderGoods> litemallOrderGoods = orderGoodsService.queryByGid(goodsVo.getId());
                if(!CollectionUtils.isEmpty(litemallOrderGoods)){
                    goodsVo.setSales(litemallOrderGoods.stream().mapToInt(adminOrderGoods-> adminOrderGoods.getNumber()).sum());
                }
                goodsVos.add(goodsVo);
            });
        }
    }

    private List<GoodsVo> getShopGoodsVos(List<Map<String, Object>> shops) {
        List<GoodsVo> goodsVos = new ArrayList<>();
        if(!CollectionUtils.isEmpty(shops)){
            goodsVos = JSONObject.parseArray(JSON.toJSONString(shops), GoodsVo.class);
            goodsVos.stream().forEach(goodsVo->{
                //查询销量中商品销量
                List<LitemallOrderGoods> litemallOrderGoods = orderGoodsService.findByShopIdAndGoodsid(goodsVo.getShopId(), goodsVo.getId());
                if(!CollectionUtils.isEmpty(litemallOrderGoods)){
                    goodsVo.setSales(litemallOrderGoods.stream().mapToInt(adminOrderGoods-> adminOrderGoods.getNumber()).sum());
                }
            });
        }
        return goodsVos;
    }
}
