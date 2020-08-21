package org.linlinjava.litemall.wx.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.beans.Constants;
import org.linlinjava.litemall.db.domain.*;
import org.linlinjava.litemall.db.service.*;
import org.linlinjava.litemall.wx.annotation.LoginUser;
import org.linlinjava.litemall.wx.dto.AccessoryDto;
import org.linlinjava.litemall.wx.dto.SpecificationDto;
import org.linlinjava.litemall.wx.dto.SubscribeUserDto;
import org.linlinjava.litemall.wx.util.WxResponseEnum;
import org.linlinjava.litemall.wx.vo.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.security.provider.certpath.OCSPResponse;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * @author ：stephen
 * @date ：Created in 2020/8/8 14:23
 * @description：订阅相关服务
 */
@Service
public class WxSubscribeService {
    private static final Log logger = LogFactory.getLog(WxSubscribeService.class);

    @Autowired
    private LitemallSubscribeService litemallSubscribeService;
    @Autowired
    private LitemallSubscribeShopService litemallSubscribeShopService;
    @Autowired
    private LitemallSubscribeGoodsService litemallSubscribeGoodsService;
    @Autowired
    private LitemallSubscribeGoodsPriceService litemallSubscribeGoodsPriceService;
    @Autowired
    private LitemallShopService litemallShopService;
    @Autowired
    private LitemallGoodsService litemallGoodsService;
    @Autowired
    private WxGoodsSpecificationService wxGoodsSpecificationService;
    @Autowired
    private LitemallTaxService litemallTaxService;
    @Autowired
    private LitemallShopRegionService litemallShopRegionService;
    @Autowired
    private LitemallGoodsAccessoryService litemallGoodsAccessoryService;
    @Autowired
    private LitemallGoodsAttributeService goodsAttributeService;
    @Autowired
    private LitemallGoodsProductService litemallGoodsProductService;
    @Autowired
    private LitemallSubscribeUserService litemallSubscribeUserService;
    @Autowired
    private LitemallUserService litemallUserService;
    @Autowired
    private LitemallAddressService litemallAddressService;
    @Autowired
    private LitemallGoodsSpecificationService litemallGoodsSpecificationService;
    @Autowired
    private LitemallMerchandiseService litemallMerchandiseService;
    @Autowired
    private LitemallShopMerchandiseService litemallShopMerchandiseService;

    private final static ArrayBlockingQueue<Runnable> WORK_QUEUE = new ArrayBlockingQueue<>(9);
    private final static RejectedExecutionHandler HANDLER = new ThreadPoolExecutor.CallerRunsPolicy();
    private static ThreadPoolExecutor executorService = new ThreadPoolExecutor(16, 16, 1000, TimeUnit.MILLISECONDS, WORK_QUEUE, HANDLER);

    /**
     * 查询可订阅商品列表
     * @param shopId
     * @return
     */
    public Object list(Integer shopId){
        List<LitemallSubscribeShop> litemallSubscribeShops = litemallSubscribeShopService.queryByShopId(shopId);
        if(litemallSubscribeShops != null && litemallSubscribeShops.size() > 0){
            List<Integer> subIds = litemallSubscribeShops.stream().map(LitemallSubscribeShop::getSubscribeId).collect(Collectors.toList());
            if(subIds != null && subIds.size() > 0){
                List<LitemallSubscribeGoods> litemallSubscribeGoods = litemallSubscribeGoodsService.queryBySubIds(subIds);
                List<SubscribeVo> collect = litemallSubscribeGoods.stream().map(subGoods -> {
                    LitemallGoods goods = litemallGoodsService.findById(subGoods.getGoodsId());
                    SubscribeVo subscribeVo = new SubscribeVo();
                    if(goods == null){
                        return null;
                    }
                    subscribeVo.setBrief(goods.getBrief());
                    subscribeVo.setIsHot(goods.getIsHot());
                    subscribeVo.setId(goods.getId());
                    subscribeVo.setName(goods.getName());
                    subscribeVo.setSubscribeId(subGoods.getSubscribeId());
                    subscribeVo.setSubscribeGoodsId(subGoods.getId());
                    subscribeVo.setIsNew(goods.getIsNew());
                    subscribeVo.setPicUri(goods.getPicUrl());
                    subscribeVo.setCategoryId(goods.getCategoryId());
                    subscribeVo.setRetailPrice(subGoods.getBasePrice());
                    return subscribeVo;
                }).filter(item->{
                    return item != null;
                }).collect(Collectors.toList());
                return ResponseUtil.okList(collect);
            }
        }
        return ResponseUtil.okList(new ArrayList());
    }

    /**
     * 查询可订阅商品详情
     * @param id
     * @param userId
     * @param shopId
     * @param subscribeId
     * @param subscribeGoodsId
     * @return
     */
    public Object detail(Integer id, Integer userId, Integer shopId, Integer subscribeId, Integer subscribeGoodsId){
        // 商品信息
        LitemallGoods info = litemallGoodsService.findById(id);

        // 商品属性
        Callable<List> goodsAttributeListCallable = () -> goodsAttributeService.queryByGid(id);
        // 商品规格 返回的是定制的GoodsSpecificationVo
        Callable<List<GoodsGroupSpecificationVo>> objectCallable = () -> wxGoodsSpecificationService.getSpecificationVoList(id);

        // 商品规格对应的数量和价格
        Callable<List<LitemallGoodsProduct>> productListCallable = () -> litemallGoodsProductService.queryByGid(id);


        // 订阅规则
        Callable<LitemallSubscribe> subscribeCallable = () -> litemallSubscribeService.findById(subscribeId);


        // 订阅商品价格
        Callable<LitemallSubscribeGoods> subscribeGoodsCallable = () -> litemallSubscribeGoodsService.findById(subscribeGoodsId);

//        LitemallShop shop = litemallShopService.findById(shopId);
//        List<LitemallShopRegion> shopRegions = litemallShopRegionService.queryByShopId(shop.getId());

        // 商品税费
//        Callable<List> taxCallable = () -> litemallTaxService.queryByRegionIds(shopRegions.stream().map(LitemallShopRegion::getRegionId).collect(Collectors.toList()));

        // 商品辅料
        boolean hasAccessory = litemallGoodsAccessoryService.countByGoodsId(id);

        FutureTask<List> goodsAttributeListTask = new FutureTask<>(goodsAttributeListCallable);
        FutureTask<List<GoodsGroupSpecificationVo>> objectCallableTask = new FutureTask<>(objectCallable);
        FutureTask<List<LitemallGoodsProduct>> productListCallableTask = new FutureTask<>(productListCallable);
//        FutureTask<List> taxCallableTask = new FutureTask<>(taxCallable);
        FutureTask<LitemallSubscribe> subScribeCallableTask = new FutureTask<>(subscribeCallable);
        FutureTask<LitemallSubscribeGoods> subScribeGoodsCallableTask = new FutureTask<>(subscribeGoodsCallable);

        executorService.submit(goodsAttributeListTask);
        executorService.submit(objectCallableTask);
        executorService.submit(productListCallableTask);
//        executorService.submit(taxCallableTask);
        executorService.submit(subScribeCallableTask);
        executorService.submit(subScribeGoodsCallableTask);

        Map<String, Object> data = new HashMap<>();

        try {
            List<GoodsGroupSpecificationVo> specificationList = objectCallableTask.get();
            for(GoodsGroupSpecificationVo goodsGroupSpecificationVo : specificationList){
                if(goodsGroupSpecificationVo.getValueList() != null && goodsGroupSpecificationVo.getValueList().size() > 0){
                    goodsGroupSpecificationVo.getValueList().get(0).setSelected(true);
                }
            }

            /**
             * 这里按照规则取订阅价格
             */
            BigDecimal subGoodsPrice = getSubGoodsPrice(subscribeGoodsId, userId, null);
            LitemallSubscribeGoods litemallSubscribeGoods = subScribeGoodsCallableTask.get();
            LitemallSubscribe subscribe = subScribeCallableTask.get();
            List<LitemallGoodsProduct> products = productListCallableTask.get();
            List<Map> shopAndTaxs = new ArrayList<>();
            List<LitemallSubscribeShop> litemallSubscribeShops = litemallSubscribeShopService.queryBySubId(subscribe.getId());
            for(LitemallSubscribeShop litemallSubscribeShop : litemallSubscribeShops){
                if(products.size() > 0){
                    Map<String, Object> shopAndTax = new HashMap<>();
                    BigDecimal taxPrice = getTax(litemallSubscribeShop.getShopId(), products.get(0).getId(), subGoodsPrice);
                    shopAndTax.put("shop", litemallShopService.findById(litemallSubscribeShop.getShopId()));
                    shopAndTax.put("taxPrice", taxPrice);
                    shopAndTaxs.add(shopAndTax);
                }
            }
            litemallSubscribeGoods.setBasePrice(subGoodsPrice);
            data.put("info", info);
            data.put("hasAccessory", hasAccessory);
            data.put("specificationList", specificationList);
            data.put("productList", products);
            data.put("attribute", goodsAttributeListTask.get());
            data.put("shopAndTaxs", shopAndTaxs);
//            data.put("taxes", taxCallableTask.get());
            data.put("subscribe", subScribeCallableTask.get());
            data.put("subscribeGoods", litemallSubscribeGoods);

        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseUtil.fail();
        }
        return ResponseUtil.ok(data);
    }

    public Object myList(Integer userId){
        List<LitemallSubscribeUser> litemallSubscribeUsers = litemallSubscribeUserService.queryByUserId(userId);
        List<Map<String, Object>> collect = litemallSubscribeUsers.stream().map(item -> {
            Map<String, Object> map = new HashMap<>();
            LitemallGoods goods = litemallGoodsService.findById(item.getGoodsId());
            LitemallShop shop = litemallShopService.findById(item.getShopId());
            if(item.getAddressId() != null){
                LitemallAddress address = litemallAddressService.findById(item.getAddressId());
                map.put("address", address);
            }
            map.put("goods", goods);
            map.put("subscribe", item);
            map.put("shop", shop);
            return map;
        }).collect(Collectors.toList());
        return ResponseUtil.okList(collect);
    }

    /**
     * 读取用户订阅详情
     * @param id
     * @return
     */
    public Object read(Integer id){
        LitemallSubscribeUser litemallSubscribeUser = litemallSubscribeUserService.findById(id);
        Map<String, Object> map = new HashMap<>();
        LitemallGoods goods = litemallGoodsService.findById(litemallSubscribeUser.getGoodsId());
        LitemallShop shop = litemallShopService.findById(litemallSubscribeUser.getShopId());
        if(litemallSubscribeUser.getAddressId() != null){
            LitemallAddress address = litemallAddressService.findById(litemallSubscribeUser.getAddressId());
            map.put("address", address);
        }
        map.put("goods", goods);
        map.put("subscribe", litemallSubscribeUser);
        map.put("shop", shop);
        return ResponseUtil.ok(map);
    }

    public Object create(SubscribeUserDto dto, Integer userId){
        /**
         * 判断地址
         */
        if((dto.getDeliveryMethod() == Constants.ORDER_SEND || dto.getDeliveryMethod() == Constants.ORDER_MAIL) && dto.getAddressId() == null){
            return ResponseUtil.fail(WxResponseEnum.ADDRESS_IS_NULL);
        }
        dto.setUserId(userId);
        LitemallSubscribeUser subscribeUser = new LitemallSubscribeUser();
        BeanUtils.copyProperties(dto, subscribeUser);
        /**
         * 没有选择订阅时间范围时。默认根据选择的订阅方式决定，开始时间从订阅的第二天开始
         */
        if(subscribeUser.getStartTime() == null || subscribeUser.getEndTime() == null){
            LocalDateTime tomorrow = LocalDateTime.of(LocalDate.now(), LocalTime.MIN).plusDays(1);
            subscribeUser.setStartTime(tomorrow);
            if(Constants.SUBSCRIBE_METHOD_WEEK == subscribeUser.getMethod()){
                subscribeUser.setEndTime(LocalDateTime.of(LocalDate.now(), LocalTime.MAX).plusDays(7));
            }else if(Constants.SUBSCRIBE_METHOD_MONTH == subscribeUser.getMethod()){
                subscribeUser.setEndTime(LocalDateTime.of(LocalDate.now(), LocalTime.MAX).plusMonths(1));
            }else if(Constants.SUBSCRIBE_METHOD_YEAR == subscribeUser.getMethod()){
                subscribeUser.setEndTime(LocalDateTime.of(LocalDate.now(), LocalTime.MAX).plusYears(1));
            }
        }
        /**
         * 配送日期没有选择时，默认第一天配送
         */
        if(subscribeUser.getDeliveryDays() == null){
            subscribeUser.setDeliveryDays(1);
        }

        /**
         * 计算价格，先计算税费在计算其他
         */
        LitemallSubscribeGoods subscribeGoods = litemallSubscribeGoodsService.findByGoodsIdAndSubId(dto.getGoodsId(), dto.getSubscribeId());
        BigDecimal subGoodsPrice = getSubGoodsPrice(subscribeGoods.getId(), userId, dto.getNumber());

        /**
         * 获取门店对应的税费率
         */
        List<LitemallShopRegion> shopRegions = litemallShopRegionService.queryByShopId(dto.getShopId());
        List<LitemallTax> litemallTaxes = litemallTaxService.queryByRegionIds(shopRegions.stream().map(LitemallShopRegion::getRegionId).collect(Collectors.toList()));

        /**
         * 获取商品选用税费
         */
        BigDecimal taxPrice = new BigDecimal(0.00);
        BigDecimal tax = new BigDecimal(0.00);
        LitemallGoodsProduct products = litemallGoodsProductService.findById(dto.getGoodsProductId());
        if(products != null ){
            List<Integer> taxTypes = new ArrayList<>(Arrays.asList(products.getTaxTypes()));
            for(LitemallTax item : litemallTaxes){
                boolean anyMatch = taxTypes.stream().anyMatch(type -> {
                    return type == item.getType().intValue();
                });
                if(anyMatch){
                    //计算税价
                    tax = tax.add(item.getValue().divide(new BigDecimal(100.00)));
                }
            }
            //税费 = 订阅价格 * 订阅数量 * 税率
            subGoodsPrice = subGoodsPrice.add(tax.multiply(subGoodsPrice));
            taxPrice = tax.multiply(subGoodsPrice);

        }

        if(dto.getGoodsItemDto() != null){
            //规格价格
            if(dto.getGoodsItemDto().getSpecificationDtos() != null){
                for(SpecificationDto item : dto.getGoodsItemDto().getSpecificationDtos()){
                    LitemallGoodsSpecification p = litemallGoodsSpecificationService.findById(item.getId());
                    subGoodsPrice.add(p.getPrice());
                    item.setName(p.getValue());
                    item.setPrice(p.getPrice());
                }
            }

            //辅料价格
            if(dto.getGoodsItemDto().getAccessoryDtos() != null){
                for(AccessoryDto item : dto.getGoodsItemDto().getAccessoryDtos()){
                    LitemallGoodsAccessory ac = litemallGoodsAccessoryService.findById(item.getId());
                    subGoodsPrice.add(ac.getPrice().multiply(new BigDecimal(item.getNumber())));
                    item.setPrice(ac.getPrice());
                    item.setName(ac.getName());
                }
            }
        }

        subscribeUser.setTaxPrice(taxPrice);
        subscribeUser.setPrice(subGoodsPrice);
        /**
         * 将规格、库存等信息转换为json存储
         */
        subscribeUser.setGoodsItem(JSONObject.toJSONString(dto.getGoodsItemDto()));
        litemallSubscribeUserService.add(subscribeUser);
        return ResponseUtil.ok();
    }

    /**
     * 获取订阅商品辅料
     * @param goodsId
     * @param shopId
     * @return
     */
    public Object accessory(Integer goodsId, Integer shopId){
        List<LitemallGoodsAccessory> accessories = litemallGoodsAccessoryService.queryByGoodsId(goodsId);
        LitemallGoods goods = litemallGoodsService.findById(goodsId);

        List<AccessoryVo> collect = accessories.stream().map(accessory -> {
            AccessoryVo vo = new AccessoryVo();
            BeanUtils.copyProperties(accessory, vo);
            LitemallMerchandise mer = litemallMerchandiseService.findById(accessory.getMerchandiseId());
            if (mer != null) {
                LitemallShopMerchandise shopMerchandise = litemallShopMerchandiseService.queryByMerId(mer.getId(), shopId);
                vo.setUnit(mer.getUnit());
                /**
                 * 设置默认选中数量
                 */
                vo.setSelectNum(0);
                if (shopMerchandise != null) {
                    vo.setNumber(shopMerchandise.getNumber());
                } else {
                    vo.setNumber(0);
                }
            }
            return vo;
        }).collect(Collectors.toList());

        List<AccessoryGroupVo> rtn = new ArrayList<>();
        collect.stream().collect(Collectors.groupingBy(AccessoryVo::getGroupName)).forEach((k,v)->{
            AccessoryGroupVo vo = new AccessoryGroupVo();
            vo.setName(k);
            vo.setAccessoryVos(v);
            rtn.add(vo);
        });
        return ResponseUtil.ok(rtn);
    }

    /**
     * 根据用户和数量获取订阅价格
     * @param subscribeGoodsId
     * @param userId
     * @param number
     * @return
     */
    public BigDecimal getSubGoodsPrice(Integer subscribeGoodsId, Integer userId, Integer number){
        LitemallSubscribeGoods subscribeGoods = litemallSubscribeGoodsService.findById(subscribeGoodsId);
        List<LitemallSubscribeGoodsPrice> subscribeGoodsPrices = litemallSubscribeGoodsPriceService.queryBySubGoodsId(subscribeGoodsId);
        if(subscribeGoodsPrices != null && subscribeGoodsPrices.size() > 0){
            LitemallUser user = litemallUserService.findById(userId);
            List<LitemallSubscribeGoodsPrice> collect = subscribeGoodsPrices.stream().filter(item -> {
                return item.getUserLevel() == null || item.getUserLevel() == user.getUserLevel();
            }).collect(Collectors.toList());
            if(collect != null && collect.size() > 0){
                /**
                 * 将数据按数量倒叙
                 */
                List<LitemallSubscribeGoodsPrice> sortedSubGoods = collect.stream().sorted(Comparator.comparing(LitemallSubscribeGoodsPrice::getNumber).reversed()).collect(Collectors.toList());
                /**
                 * 取最接近的数量的价格,如果没有则取设置的最少数量对应的价格
                 */
                if(number == null || (sortedSubGoods.get(sortedSubGoods.size() -1).getNumber() != null && sortedSubGoods.get(sortedSubGoods.size() -1).getNumber() < number)){
                    return sortedSubGoods.get(sortedSubGoods.size() -1).getPrice();
                }else{
                    for(LitemallSubscribeGoodsPrice i : sortedSubGoods){
                        if(i.getNumber() != null && number > i.getNumber()){
                            return i.getPrice();
                        }
                    }
                }
            }
        }
        return subscribeGoods.getBasePrice();

    }
    public BigDecimal getTax(Integer shopId, Integer goodsProductId, BigDecimal subGoodsPrice){
        LitemallShop shop = litemallShopService.findById(shopId);
        List<LitemallShopRegion> shopRegions = litemallShopRegionService.queryByShopId(shop.getId());

        // 商品税费
        List<LitemallTax> litemallTaxes =  litemallTaxService.queryByRegionIds(shopRegions.stream().map(LitemallShopRegion::getRegionId).collect(Collectors.toList()));

        /**
         * 获取商品选用税费
         */
        BigDecimal taxPrice = new BigDecimal(0.00);
        BigDecimal tax = new BigDecimal(0.00);
        LitemallGoodsProduct products = litemallGoodsProductService.findById(goodsProductId);
        if(products != null ){
            List<Integer> taxTypes = new ArrayList<>(Arrays.asList(products.getTaxTypes()));
            for(LitemallTax item : litemallTaxes){
                boolean anyMatch = taxTypes.stream().anyMatch(type -> {
                    return type == item.getType().intValue();
                });
                if(anyMatch){
                    //计算税价
                    tax = tax.add(item.getValue().divide(new BigDecimal(100.00)));
                }
            }
        }
        taxPrice = tax.multiply(subGoodsPrice);
        return taxPrice;
    }
}
