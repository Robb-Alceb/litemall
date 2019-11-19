package org.linlinjava.litemall.db.service;

import com.github.pagehelper.PageHelper;
import org.linlinjava.litemall.db.dao.LitemallShopGoodsMapper;
import org.linlinjava.litemall.db.domain.LitemallShopGoods;
import org.linlinjava.litemall.db.domain.LitemallShopGoodsExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LitemallShopGoodsService {
    @Resource
    private LitemallShopGoodsMapper litemallShopGoodsMapper;

    public List<Map<String, Object>> querySelective(String goodsSn, String name, Integer shopId, Integer page,
                                                    Integer limit, String sort, String order) {
        Map<String, Object> map = new HashMap<>();
        map.put("shopId", shopId);
        map.put("name", name);
        map.put("goodsSn", goodsSn);
        PageHelper.startPage(page, limit);
        return litemallShopGoodsMapper.selectShopGoodsList(map);
    }

    /**
     * 查询门店内商品详情
     * @return
     */
    public LitemallShopGoods queryByShopIdAndGoodsid(Integer shopId, Integer goodsId) {
        LitemallShopGoodsExample example = new LitemallShopGoodsExample();
        example.or().andShopIdEqualTo(shopId).andGoodsIdEqualTo(goodsId).andDeletedEqualTo(false);
        return litemallShopGoodsMapper.selectOneByExampleSelective(example);
    }

    public int updateById(LitemallShopGoods shop) {
        shop.setUpdateTime(LocalDateTime.now());
        return litemallShopGoodsMapper.updateByPrimaryKeySelective(shop);
    }

    public void deleteById(Integer id) {
        litemallShopGoodsMapper.logicalDeleteByPrimaryKey(id);
    }

    public void add(LitemallShopGoods shop) {
        shop.setAddTime(LocalDateTime.now());
        shop.setUpdateTime(LocalDateTime.now());
        litemallShopGoodsMapper.insertSelective(shop);
    }

    public LitemallShopGoods findById(Integer id) {
        return litemallShopGoodsMapper.selectByPrimaryKeySelective(id);
    }

}
