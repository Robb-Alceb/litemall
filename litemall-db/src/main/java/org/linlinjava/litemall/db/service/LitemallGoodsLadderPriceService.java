package org.linlinjava.litemall.db.service;

import org.linlinjava.litemall.db.dao.LitemallGoodsLadderPriceMapper;
import org.linlinjava.litemall.db.domain.LitemallGoodsLadderPrice;
import org.linlinjava.litemall.db.domain.LitemallGoodsLadderPriceExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class LitemallGoodsLadderPriceService {
    @Resource
    private LitemallGoodsLadderPriceMapper goodsLadderPriceMapper;

    public LitemallGoodsLadderPrice queryById(Integer id){
        return goodsLadderPriceMapper.selectByPrimaryKeyWithLogicalDelete(id, false);
    }
    public List<LitemallGoodsLadderPrice> queryByGoodsId(Integer goodsId){
        LitemallGoodsLadderPriceExample example = new LitemallGoodsLadderPriceExample();
        example.or().andLogicalDeleted(false).andGoodsIdEqualTo(goodsId);
        return goodsLadderPriceMapper.selectByExample(example);
    }
    public void deleteByGoodsId(Integer goodsId){
        LitemallGoodsLadderPriceExample example = new LitemallGoodsLadderPriceExample();
        example.or().andLogicalDeleted(false).andGoodsIdEqualTo(goodsId);
        goodsLadderPriceMapper.logicalDeleteByExample(example);

    }
    public void add(LitemallGoodsLadderPrice vipGoodsPrice){
        vipGoodsPrice.setUpdateTime(LocalDateTime.now());
        vipGoodsPrice.setAddTime(LocalDateTime.now());
        goodsLadderPriceMapper.insertSelective(vipGoodsPrice);
    }
}
