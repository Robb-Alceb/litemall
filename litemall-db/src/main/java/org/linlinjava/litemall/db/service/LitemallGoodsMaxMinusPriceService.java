package org.linlinjava.litemall.db.service;

import org.linlinjava.litemall.db.dao.LitemallGoodsMaxMinusPriceMapper;
import org.linlinjava.litemall.db.dao.LitemallVipGoodsPriceMapper;
import org.linlinjava.litemall.db.domain.LitemallGoodsMaxMinusPrice;
import org.linlinjava.litemall.db.domain.LitemallGoodsMaxMinusPriceExample;
import org.linlinjava.litemall.db.domain.LitemallVipGoodsPrice;
import org.linlinjava.litemall.db.domain.LitemallVipGoodsPriceExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class LitemallGoodsMaxMinusPriceService {
    @Resource
    private LitemallGoodsMaxMinusPriceMapper goodsMaxMinusPriceMapper;

    public LitemallGoodsMaxMinusPrice queryById(Integer id){
        return goodsMaxMinusPriceMapper.selectByPrimaryKeyWithLogicalDelete(id, false);
    }
    public List<LitemallGoodsMaxMinusPrice> queryByGoodsId(Integer goodsId){
        LitemallGoodsMaxMinusPriceExample example = new LitemallGoodsMaxMinusPriceExample();
        example.or().andLogicalDeleted(true).andGoodsIdEqualTo(goodsId);
        return goodsMaxMinusPriceMapper.selectByExample(example);
    }
    public void deleteByGoodsId(Integer goodsId){
        LitemallGoodsMaxMinusPriceExample example = new LitemallGoodsMaxMinusPriceExample();
        example.or().andGoodsIdEqualTo(goodsId);
        goodsMaxMinusPriceMapper.logicalDeleteByExample(example);

    }
    public void add(LitemallGoodsMaxMinusPrice vipGoodsPrice){
        vipGoodsPrice.setUpdateTime(LocalDateTime.now());
        vipGoodsPrice.setAddTime(LocalDateTime.now());
        goodsMaxMinusPriceMapper.insertSelective(vipGoodsPrice);
    }
}
