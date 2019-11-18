package org.linlinjava.litemall.admin.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.linlinjava.litemall.admin.beans.vo.ShopGoodsVo;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.service.LitemallOrderGoodsService;
import org.linlinjava.litemall.db.service.LitemallShopGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

/**
 * 门店商品服务
 */
@Service
public class ShopGoodsService {
    @Autowired
    private LitemallShopGoodsService litemallShopGoodsService;
    @Autowired
    private LitemallOrderGoodsService litemallOrderGoodsService;

    public Object list(String goodsSn, String name, Integer shopId, Integer page, Integer limit, String sort, String order){
        List<Map<String, Object>> shops = litemallShopGoodsService.querySelective(goodsSn, name, shopId,
                page, limit, sort, order);
        List<ShopGoodsVo> shopGoodsVos = null;
        if(!CollectionUtils.isEmpty(shops)){
            shopGoodsVos = JSONObject.parseArray(JSON.toJSONString(shops), ShopGoodsVo.class);
            shopGoodsVos.stream().forEach(shopGoodsVo->{
                //查询销量中商品销量
                shopGoodsVo.setSales(litemallOrderGoodsService.findByShopIdAndGoodsid(shopGoodsVo.getShopId(), shopGoodsVo.getGoodsId()).size());
            });
        }
        return ResponseUtil.okList(shopGoodsVos, shops);
    }

}
