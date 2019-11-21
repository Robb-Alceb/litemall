package org.linlinjava.litemall.admin.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.admin.beans.vo.GoodsVo;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.domain.*;
import org.linlinjava.litemall.db.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
public class AdminGoodsProductService {
    private final Log logger = LogFactory.getLog(AdminGoodsProductService.class);

    @Autowired
    private LitemallGoodsService goodsService;
    @Autowired
    private LitemallGoodsProductService productService;
    @Autowired
    private LitemallShopGoodsService shopGoodsService;
    @Autowired
    private LitemallGoodsProductLogService goodsProductLogService;

    /**
     * 库存列表
     * @return
     */
    public Object list(String name,Integer shopId,
                       Integer page, Integer limit, String sort, String order) {
        if(shopId!=null){
            //查询门店库存
            List<Map<String, Object>> shops = shopGoodsService.querySelective(name, shopId,
                    page, limit, sort, order);
            return ResponseUtil.okList(shops);
        }else{
            //查询所有商品
            List<LitemallGoods> goodsList = goodsService.querySelective(null, name, shopId, page, limit, sort, order);
            return ResponseUtil.okList(getGoodsVos(goodsList), goodsList);
        }
    }

    /**
     * 出库入库列表
     * @return
     */
    public Object goodsProductRecordList(String orderSn, String goodsName,Integer shopId,
                       Integer page, Integer limit, String sort, String order) {
        return goodsProductLogService.querySelective(orderSn, goodsName, shopId, page, limit, sort, order);
    }

    private List<GoodsVo> getGoodsVos(List<LitemallGoods> goodsList) {
        List<GoodsVo> goodsVos = new ArrayList<>();
        if(!CollectionUtils.isEmpty(goodsList)){
            goodsList.stream().forEach(goods->{
                GoodsVo goodsVo = new GoodsVo();
                BeanUtils.copyProperties(goods, goodsVo);
                //库存查询
                goodsVo.setNumber(productService.queryByGid(goodsVo.getId()).get(0).getNumber());
                goodsVos.add(goodsVo);
            });
        }
        return goodsVos;
    }

}
