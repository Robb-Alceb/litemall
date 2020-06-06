package org.linlinjava.litemall.db.service;

import com.github.pagehelper.PageHelper;
import org.linlinjava.litemall.db.dao.LitemallGoodsAccessoryMapper;
import org.linlinjava.litemall.db.domain.LitemallGoodsAccessory;
import org.linlinjava.litemall.db.domain.LitemallGoodsAccessoryExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
public class LitemallGoodsAccessoryService {

    @Resource
    private LitemallGoodsAccessoryMapper litemallGoodsAccessoryMapper;

    public int add(LitemallGoodsAccessory litemallGoodsAccessory) {
        litemallGoodsAccessory.setAddTime(LocalDateTime.now());
        return litemallGoodsAccessoryMapper.insertSelective(litemallGoodsAccessory);
    }

    public int deleteById(Integer id) {
        return litemallGoodsAccessoryMapper.logicalDeleteByPrimaryKey(id);
    }

    public int deleteByGoodsId(Integer goodsId) {
        LitemallGoodsAccessoryExample example = new LitemallGoodsAccessoryExample();
        example.or().andGoodsIdEqualTo(goodsId);
        return litemallGoodsAccessoryMapper.logicalDeleteByExample(example);
    }

    public int update(LitemallGoodsAccessory litemallGoodsAccessory) {
        return litemallGoodsAccessoryMapper.updateByPrimaryKeySelective(litemallGoodsAccessory);
    }


    public LitemallGoodsAccessory findById(Integer id) {
        return litemallGoodsAccessoryMapper.selectByPrimaryKey(id);
    }

    public List<LitemallGoodsAccessory> queryByGoodsId(Integer goodsId) {
        LitemallGoodsAccessoryExample example = new LitemallGoodsAccessoryExample();
        example.or().andGoodsIdEqualTo(goodsId).andDeletedEqualTo(false);
        return litemallGoodsAccessoryMapper.selectByExample(example);
    }

    public List<LitemallGoodsAccessory> querySelective(String name, Integer page, Integer size, String sort, String order) {
        LitemallGoodsAccessoryExample example = new LitemallGoodsAccessoryExample();
        LitemallGoodsAccessoryExample.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(name)) {
            example.or(criteria);
            criteria.andNameLike("%" + name + "%");
        }


        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, size);
        return litemallGoodsAccessoryMapper.selectByExample(example);
    }


    public List<LitemallGoodsAccessory> queryByIds(Integer[] acceIds) {
        LitemallGoodsAccessoryExample example = new LitemallGoodsAccessoryExample();
        example.or().andIdIn(Arrays.asList(acceIds)).andDeletedEqualTo(false);
        return litemallGoodsAccessoryMapper.selectByExample(example);
    }
}
