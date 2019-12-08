package org.linlinjava.litemall.db.service;

import org.linlinjava.litemall.db.dao.LitemallVipGoodsPriceMapper;
import org.linlinjava.litemall.db.domain.LitemallVipGoodsPrice;
import org.linlinjava.litemall.db.domain.LitemallVipGoodsPriceExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@Service
public class LitemallVipGoodsService {
    @Resource
    private LitemallVipGoodsPriceMapper vipGoodsPriceMapper;

    public LitemallVipGoodsPrice queryById(Integer id){
        return vipGoodsPriceMapper.selectByPrimaryKeyWithLogicalDelete(id, false);
    }
    public LitemallVipGoodsPrice queryByGoodsId(Integer goodsId){
        LitemallVipGoodsPriceExample example = new LitemallVipGoodsPriceExample();
        example.or().andLogicalDeleted(true).andGoodsIdEqualTo(goodsId);
        return vipGoodsPriceMapper.selectOneByExample(example);
    }
    public void deleteByGoodsId(Integer goodsId){
        LitemallVipGoodsPriceExample example = new LitemallVipGoodsPriceExample();
        example.or().andGoodsIdEqualTo(goodsId);
        vipGoodsPriceMapper.logicalDeleteByExample(example);

    }
    public int updateByGoodsId(LitemallVipGoodsPrice vipGoodsPrice){
        vipGoodsPrice.setUpdateTime(LocalDateTime.now());
        LitemallVipGoodsPriceExample example = new LitemallVipGoodsPriceExample();
        example.or().andGoodsIdEqualTo(vipGoodsPrice.getGoodsId());
        return vipGoodsPriceMapper.updateByExampleSelective(vipGoodsPrice, example);
    }
    public void add(LitemallVipGoodsPrice vipGoodsPrice){
        vipGoodsPrice.setUpdateTime(LocalDateTime.now());
        vipGoodsPrice.setAddTime(LocalDateTime.now());
        vipGoodsPriceMapper.insertSelective(vipGoodsPrice);
    }
}
