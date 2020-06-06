package org.linlinjava.litemall.db.service;

import com.github.pagehelper.PageHelper;
import org.linlinjava.litemall.db.dao.LitemallCartGoodsAccessoryMapper;
import org.linlinjava.litemall.db.domain.LitemallCartGoodsAccessory;
import org.linlinjava.litemall.db.domain.LitemallCartGoodsAccessoryExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class LitemallCartGoodsAccessoryService {

    @Resource
    private LitemallCartGoodsAccessoryMapper litemallCartGoodsAccessoryMapper;

    public int add(LitemallCartGoodsAccessory litemallGoodsAccessory) {
        litemallGoodsAccessory.setAddTime(LocalDateTime.now());
        return litemallCartGoodsAccessoryMapper.insertSelective(litemallGoodsAccessory);
    }

    public int deleteById(Integer id) {
        return litemallCartGoodsAccessoryMapper.logicalDeleteByPrimaryKey(id);
    }

    public int deleteByGoodsId(Integer goodsId) {
        LitemallCartGoodsAccessoryExample example = new LitemallCartGoodsAccessoryExample();
        example.or().andGoodsIdEqualTo(goodsId);
        return litemallCartGoodsAccessoryMapper.logicalDeleteByExample(example);
    }

    public int update(LitemallCartGoodsAccessory litemallGoodsAccessory) {
        return litemallCartGoodsAccessoryMapper.updateByPrimaryKeySelective(litemallGoodsAccessory);
    }


    public LitemallCartGoodsAccessory findById(Integer id) {
        return litemallCartGoodsAccessoryMapper.selectByPrimaryKey(id);
    }
    
    public List<LitemallCartGoodsAccessory> findByGoodsId(Integer goodsId) {
        LitemallCartGoodsAccessoryExample example = new LitemallCartGoodsAccessoryExample();
        example.or().andGoodsIdEqualTo(goodsId).andDeletedEqualTo(false);
        return litemallCartGoodsAccessoryMapper.selectByExample(example);
    }

    public List<LitemallCartGoodsAccessory> findByCartId(Integer cartId) {
        LitemallCartGoodsAccessoryExample example = new LitemallCartGoodsAccessoryExample();
        example.or().andCartIdEqualTo(cartId).andDeletedEqualTo(false);
        return litemallCartGoodsAccessoryMapper.selectByExample(example);
    }


    public boolean exist(Integer cartId, Integer id, Integer number) {
        LitemallCartGoodsAccessoryExample example = new LitemallCartGoodsAccessoryExample();
        example.or().andCartIdEqualTo(cartId).andIdEqualTo(id).andNumberEqualTo(number).andDeletedEqualTo(false);
        return litemallCartGoodsAccessoryMapper.countByExample(example) == 1;
    }

    public List<LitemallCartGoodsAccessory> querySelective(Integer page, Integer size, String sort, String order) {
        LitemallCartGoodsAccessoryExample example = new LitemallCartGoodsAccessoryExample();
        LitemallCartGoodsAccessoryExample.Criteria criteria = example.createCriteria();


        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, size);
        return litemallCartGoodsAccessoryMapper.selectByExample(example);
    }



}
