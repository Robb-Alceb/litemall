package org.linlinjava.litemall.admin.service;

import org.apache.shiro.crypto.hash.Hash;
import org.linlinjava.litemall.admin.beans.dto.SubscribeDto;
import org.linlinjava.litemall.admin.beans.dto.SubscribeGoodsDto;
import org.linlinjava.litemall.admin.beans.dto.SubscribeGoodsPriceDto;
import org.linlinjava.litemall.admin.beans.dto.SubscribeShopDto;
import org.linlinjava.litemall.admin.beans.pojo.convert.BeanConvert;
import org.linlinjava.litemall.admin.beans.vo.SubscribeListVo;
import org.linlinjava.litemall.admin.beans.vo.SubscribeUserListVo;
import org.linlinjava.litemall.admin.beans.vo.SubscribeVo;
import org.linlinjava.litemall.admin.util.AdminResponseEnum;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.domain.*;
import org.linlinjava.litemall.db.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * @author ：stephen
 * @date ：Created in 2020/8/7 16:55
 * @description：商品订阅相关服务
 */
@Service
public class SubscribeService {
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
    private LitemallSubscribeUserService litemallSubscribeUserService;
    @Autowired
    private LitemallUserService litemallUserService;
    @Autowired
    private SubscribeUserService subscribeUserService;
    @Autowired
    private LitemallAddressService litemallAddressService;

    @Transactional
    public Object update(SubscribeDto dto){
        if(litemallSubscribeUserService.countBySubId(dto.getId()) > 0){
            return ResponseUtil.fail(AdminResponseEnum.DELETE_SUBSCRIBE_ERROR);
        };
        LitemallSubscribe subscribe = new LitemallSubscribe();
        BeanUtils.copyProperties(dto, subscribe);

        litemallSubscribeService.updateById(subscribe);
        if(dto.getSubscribeShopDtos() != null){
            litemallSubscribeShopService.deleteBySubId(subscribe.getId());
            for(SubscribeShopDto shopDto: dto.getSubscribeShopDtos()){
                updateOrAddSubShop(shopDto, subscribe.getId());
            }
        }

        if(dto.getSubscribeGoodsDtos() != null){
            for(SubscribeGoodsDto goodsDto: dto.getSubscribeGoodsDtos()){
                int subGoodsId = updateOrAddSubGoods(goodsDto, subscribe.getId());
                if(goodsDto.getSubscribeGoodsPriceDtos() != null) {
                    for (SubscribeGoodsPriceDto subscribeGoodsPriceDto : goodsDto.getSubscribeGoodsPriceDtos()) {
                        updateOrAddSubGoodsPrice(subscribeGoodsPriceDto, subscribe.getId(), subGoodsId);
                    }
                }
            }
        }
        return ResponseUtil.ok();
    }

    @Transactional
    public Object add(SubscribeDto dto){
        LitemallSubscribe subscribe = new LitemallSubscribe();
        BeanUtils.copyProperties(dto, subscribe);

        litemallSubscribeService.add(subscribe);
        if(dto.getSubscribeShopDtos() != null){
            litemallSubscribeShopService.deleteBySubId(subscribe.getId());
            for(SubscribeShopDto shopDto: dto.getSubscribeShopDtos()){
                updateOrAddSubShop(shopDto, subscribe.getId());
            }
        }
        if(dto.getSubscribeGoodsDtos() != null){
            for(SubscribeGoodsDto goodsDto: dto.getSubscribeGoodsDtos()){
                int subscribeGoodsId = updateOrAddSubGoods(goodsDto, subscribe.getId());
                if(goodsDto.getSubscribeGoodsPriceDtos() != null) {
                    for (SubscribeGoodsPriceDto subscribeGoodsPriceDto : goodsDto.getSubscribeGoodsPriceDtos()) {
                        updateOrAddSubGoodsPrice(subscribeGoodsPriceDto, subscribe.getId(), subscribeGoodsId);
                    }
                }
            }
        }
        return ResponseUtil.ok();
    }

    @Transactional
    public Object delete(Integer id){
        if(litemallSubscribeUserService.countBySubId(id) > 0){
            return ResponseUtil.fail(AdminResponseEnum.DELETE_SUBSCRIBE_ERROR);
        };
        litemallSubscribeService.deleteById(id);
        litemallSubscribeShopService.deleteBySubId(id);
        litemallSubscribeGoodsService.deleteBySubId(id);
        litemallSubscribeGoodsPriceService.deleteBySubId(id);
        return ResponseUtil.ok();
    }


    public Object querySelective(String name, Integer page, Integer size, String sort, String order){
        List<LitemallSubscribe> subscribes = litemallSubscribeService.querySelective(name, page, size, sort, order);
        List<SubscribeVo> collect = subscribes.stream().map(item -> {
            SubscribeListVo vo = new SubscribeListVo();
            BeanUtils.copyProperties(item, vo);
            List<LitemallSubscribeShop> subscribeShops = litemallSubscribeShopService.queryBySubId(vo.getId());
            List<LitemallSubscribeGoods> subscribeGoods = litemallSubscribeGoodsService.queryBySubId(vo.getId());
            return BeanConvert.toSubVo(item, subscribeShops, subscribeGoods, null);
        }).collect(Collectors.toList());
        return ResponseUtil.okList(collect);
    }

    public Object read(Integer id){
        LitemallSubscribe subscribe = litemallSubscribeService.findById(id);
        List<LitemallSubscribeShop> subscribeShops = litemallSubscribeShopService.queryBySubId(id);
        List<LitemallSubscribeGoods> subscribeGoods = litemallSubscribeGoodsService.queryBySubId(id);
        List<LitemallSubscribeGoodsPrice> subscribeGoodsPrices = litemallSubscribeGoodsPriceService.queryBySubId(id);
        SubscribeVo subscribeVo = BeanConvert.toSubVo(subscribe, subscribeShops, subscribeGoods, subscribeGoodsPrices);
        return ResponseUtil.ok(subscribeVo);
    }

    public Object subscribeUserList(String name, Integer goodsId, Integer shopId, Integer page, Integer size, String sort, String order){
        List<SubscribeUserVo> subscribeUserVos = subscribeUserService.querySelective(name, goodsId, shopId, page, size, sort, order);
        return ResponseUtil.okList(subscribeUserVos);
    }

    public int updateOrAddSubShop(SubscribeShopDto dto, Integer subId){
        LitemallSubscribeShop subscribeShop = new LitemallSubscribeShop();
        BeanUtils.copyProperties(dto, subscribeShop);
        subscribeShop.setSubscribeId(subId);
        return litemallSubscribeShopService.add(subscribeShop);
    }

    public int updateOrAddSubGoods(SubscribeGoodsDto dto, Integer subId){
        LitemallSubscribeGoods subscribeGoods = new LitemallSubscribeGoods();
        BeanUtils.copyProperties(dto, subscribeGoods);
        if(dto.getId() == null){
            subscribeGoods.setSubscribeId(subId);
            return litemallSubscribeGoodsService.add(subscribeGoods);
        }else{
            litemallSubscribeGoodsService.updateById(subscribeGoods);
            return 0;
        }
    }

    public int updateOrAddSubGoodsPrice(SubscribeGoodsPriceDto dto, Integer subId, Integer subGoodsId){
        LitemallSubscribeGoodsPrice subscribeGoodsPrice = new LitemallSubscribeGoodsPrice();
        BeanUtils.copyProperties(dto, subscribeGoodsPrice);
        if(dto.getId() == null){
            subscribeGoodsPrice.setSubscribeId(subId);
            subscribeGoodsPrice.setSubscribeGoodsId(subGoodsId);
            return litemallSubscribeGoodsPriceService.add(subscribeGoodsPrice);
        }else{
            return litemallSubscribeGoodsPriceService.updateById(subscribeGoodsPrice);
        }
    }

    /**
     * 订阅用户详情
     * @param id
     * @return
     */
    public Object subscribeUserDetail(Integer id) {
        Map<String, Object> rtn = new HashMap<>();
        LitemallSubscribeUser subscribeUser = litemallSubscribeUserService.findById(id);
        LitemallShop shop = litemallShopService.findById(subscribeUser.getShopId());
        LitemallGoods goods = litemallGoodsService.findById(subscribeUser.getGoodsId());
        LitemallUser user = litemallUserService.findById(subscribeUser.getUserId());
        if(subscribeUser.getAddressId() != null){
            LitemallAddress address = litemallAddressService.findById(subscribeUser.getAddressId());
            rtn.put("address", address);
        }
        rtn.put("subscribeUser", subscribeUser);
        rtn.put("shop", shop);
        rtn.put("goods", goods);
        rtn.put("user", user);
        return ResponseUtil.ok(rtn);
    }
}
