package org.linlinjava.litemall.db.service;

import org.linlinjava.litemall.db.dao.LitemallSubscribeGoodsPriceMapper;
import org.linlinjava.litemall.db.domain.LitemallSubscribeGoodsPrice;
import org.linlinjava.litemall.db.domain.LitemallSubscribeGoodsPriceExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class LitemallSubscribeGoodsPriceService {
    @Resource
    private LitemallSubscribeGoodsPriceMapper subscribeShopMapper;

    public void deleteById(Integer id) {
        subscribeShopMapper.logicalDeleteByPrimaryKey(id);
    }

    public int add(LitemallSubscribeGoodsPrice subscribeGoods) {
        subscribeGoods.setAddTime(LocalDateTime.now());
        return subscribeShopMapper.insertSelective(subscribeGoods);
    }

    public List<LitemallSubscribeGoodsPrice> queryBySubGoodsId(Integer subGoodsId) {
        LitemallSubscribeGoodsPriceExample example = new LitemallSubscribeGoodsPriceExample();
        example.or().andDeletedEqualTo(false).andSubscribeGoodsIdEqualTo(subGoodsId);
        return subscribeShopMapper.selectByExample(example);
    }

    public int deleteBySubGoodsId(Integer subGoodsId) {
        LitemallSubscribeGoodsPriceExample example = new LitemallSubscribeGoodsPriceExample();
        example.or().andDeletedEqualTo(false).andSubscribeGoodsIdEqualTo(subGoodsId);
        return subscribeShopMapper.deleteByExample(example);
    }

    public int deleteBySubId(Integer subId) {
        LitemallSubscribeGoodsPriceExample example = new LitemallSubscribeGoodsPriceExample();
        example.or().andDeletedEqualTo(false).andSubscribeIdEqualTo(subId);
        return subscribeShopMapper.deleteByExample(example);
    }

    public int updateById(LitemallSubscribeGoodsPrice subscribeGoods) {
        return subscribeShopMapper.updateByPrimaryKeySelective(subscribeGoods);
    }

    public LitemallSubscribeGoodsPrice findById(Integer id) {
        return subscribeShopMapper.selectByPrimaryKey(id);
    }

    public List<LitemallSubscribeGoodsPrice> queryBySubId(Integer subId) {
        LitemallSubscribeGoodsPriceExample example = new LitemallSubscribeGoodsPriceExample();
        example.or().andDeletedEqualTo(false).andSubscribeIdEqualTo(subId);
        return subscribeShopMapper.selectByExample(example);
    }
}
