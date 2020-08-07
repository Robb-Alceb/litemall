package org.linlinjava.litemall.db.service;

import com.github.pagehelper.PageHelper;
import org.linlinjava.litemall.db.dao.LitemallSubscribeGoodsMapper;
import org.linlinjava.litemall.db.domain.LitemallSubscribeGoods;
import org.linlinjava.litemall.db.domain.LitemallSubscribeGoodsExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class LitemallSubscribeGoodsService {
    @Resource
    private LitemallSubscribeGoodsMapper subscribeGoodsMapper;

    public void deleteById(Integer id) {
        subscribeGoodsMapper.logicalDeleteByPrimaryKey(id);
    }

    public void add(LitemallSubscribeGoods subscribeGoods) {
        subscribeGoods.setAddTime(LocalDateTime.now());
        subscribeGoodsMapper.insertSelective(subscribeGoods);
    }

    public List<LitemallSubscribeGoods> queryBySubId(Integer subId) {
        LitemallSubscribeGoodsExample example = new LitemallSubscribeGoodsExample();
        example.or().andDeletedEqualTo(false).andSubscribeIdEqualTo(subId);
        return subscribeGoodsMapper.selectByExample(example);
    }

    public int deleteBySubId(Integer subId) {
        LitemallSubscribeGoodsExample example = new LitemallSubscribeGoodsExample();
        example.or().andDeletedEqualTo(false).andSubscribeIdEqualTo(subId);
        return subscribeGoodsMapper.deleteByExample(example);
    }

    public int updateById(LitemallSubscribeGoods subscribeGoods) {
        return subscribeGoodsMapper.updateByPrimaryKeySelective(subscribeGoods);
    }

    public LitemallSubscribeGoods findById(Integer id) {
        return subscribeGoodsMapper.selectByPrimaryKey(id);
    }
}
