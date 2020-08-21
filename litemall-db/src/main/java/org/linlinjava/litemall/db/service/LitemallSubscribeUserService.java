package org.linlinjava.litemall.db.service;

import com.github.pagehelper.PageHelper;
import org.linlinjava.litemall.db.beans.Constants;
import org.linlinjava.litemall.db.dao.LitemallSubscribeUserMapper;
import org.linlinjava.litemall.db.domain.LitemallSubscribeUser;
import org.linlinjava.litemall.db.domain.LitemallSubscribeUserExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
        subscribeGoods.setUpdateTime(LocalDateTime.now());
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

    public List<LitemallSubscribeUser> all() {
        LitemallSubscribeUserExample example = new LitemallSubscribeUserExample();
        example.or().andDeletedEqualTo(false);
        return subscribeGoodsMapper.selectByExample(example);
    }

    public List<LitemallSubscribeUser> querySelective(Integer page, Integer limit, String sort, String order) {
        LitemallSubscribeUserExample example = new LitemallSubscribeUserExample();
        LitemallSubscribeUserExample.Criteria criteria = example.createCriteria();

        criteria.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, limit);
        return subscribeGoodsMapper.selectByExample(example);
    }

    public int countBySubId(Integer subId) {
        LitemallSubscribeUserExample example = new LitemallSubscribeUserExample();
        example.or().andSubscribeIdEqualTo(subId).andDeletedEqualTo(false);
        return (int)subscribeGoodsMapper.countByExample(example);
    }

    public List<LitemallSubscribeUser> autoSubs() {
        LitemallSubscribeUserExample example = new LitemallSubscribeUserExample();
        example.or().andAutoSubEqualTo(true).andDeletedEqualTo(false);
        return subscribeGoodsMapper.selectByExample(example);
    }

    public int countByLinkId(Integer id) {
        LitemallSubscribeUserExample example = new LitemallSubscribeUserExample();
        example.or().andLinkIdEqualTo(id).andDeletedEqualTo(false);
        return (int)subscribeGoodsMapper.countByExample(example);
    }
}
