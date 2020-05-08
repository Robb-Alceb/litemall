package org.linlinjava.litemall.db.service;

import org.linlinjava.litemall.db.dao.LitemallGoodsTaxMapper;
import org.linlinjava.litemall.db.domain.LitemallGoodsTax;
import org.linlinjava.litemall.db.domain.LitemallGoodsTaxExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class LitemallGoodsTaxService {
    @Resource
    private LitemallGoodsTaxMapper litemallGoodsTaxMapper;


    public void add(LitemallGoodsTax goodsTax) {
        goodsTax.setAddTime(LocalDateTime.now());
        litemallGoodsTaxMapper.insertSelective(goodsTax);
    }

    public int delete(int goodsId) {
        LitemallGoodsTaxExample example = new LitemallGoodsTaxExample();
        example.or().andGoodsIdEqualTo(goodsId);
        return litemallGoodsTaxMapper.logicalDeleteByExample(example);
    }

    public LitemallGoodsTax findById(Integer id) {
        return litemallGoodsTaxMapper.selectByPrimaryKey(id);
    }


    public List<LitemallGoodsTax> findByGoodsId(Integer goodsId) {
        LitemallGoodsTaxExample example = new LitemallGoodsTaxExample();
        example.or().andGoodsIdEqualTo(goodsId);
        return litemallGoodsTaxMapper.selectByExample(example);
    }

    public void deleteById(Integer id) {
        litemallGoodsTaxMapper.logicalDeleteByPrimaryKey(id);
    }

}
