package org.linlinjava.litemall.db.service;

import org.linlinjava.litemall.db.dao.LitemallSubscribeUserMapper;
import org.linlinjava.litemall.db.domain.LitemallSubscribeUser;
import org.linlinjava.litemall.db.domain.LitemallSubscribeUserExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class LitemallSubscribeUserService {
    @Resource
    private LitemallSubscribeUserMapper subscribeGoodsMapper;

    public void deleteById(Integer id) {
        subscribeGoodsMapper.logicalDeleteByPrimaryKey(id);
    }

    public void add(LitemallSubscribeUser subscribeGoods) {
        subscribeGoods.setAddTime(LocalDateTime.now());
        subscribeGoodsMapper.insertSelective(subscribeGoods);
    }

    public List<LitemallSubscribeUser> queryByUserId(Integer userId) {
        LitemallSubscribeUserExample example = new LitemallSubscribeUserExample();
        example.or().andDeletedEqualTo(false).andUserIdEqualTo(userId);
        return subscribeGoodsMapper.selectByExample(example);
    }

    public int deleteByUserId(Integer userId) {
        LitemallSubscribeUserExample example = new LitemallSubscribeUserExample();
        example.or().andDeletedEqualTo(false).andUserIdEqualTo(userId);
        return subscribeGoodsMapper.deleteByExample(example);
    }

    public int updateById(LitemallSubscribeUser subscribeGoods) {
        return subscribeGoodsMapper.updateByPrimaryKeySelective(subscribeGoods);
    }

    public LitemallSubscribeUser findById(Integer id) {
        return subscribeGoodsMapper.selectByPrimaryKey(id);
    }
}
