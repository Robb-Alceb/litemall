package org.linlinjava.litemall.db.service;

import com.github.pagehelper.PageHelper;
import org.linlinjava.litemall.db.dao.LitemallOrderGoodsAccessoryMapper;
import org.linlinjava.litemall.db.domain.LitemallOrderGoodsAccessory;
import org.linlinjava.litemall.db.domain.LitemallOrderGoodsAccessoryExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class LitemallOrderGoodsAccessoryService {

    @Resource
    private LitemallOrderGoodsAccessoryMapper litemallOrderGoodsAccessoryMapper;

    public int add(LitemallOrderGoodsAccessory litemallGoodsAccessory) {
        litemallGoodsAccessory.setAddTime(LocalDateTime.now());
        return litemallOrderGoodsAccessoryMapper.insertSelective(litemallGoodsAccessory);
    }

    public int deleteById(Integer id) {
        return litemallOrderGoodsAccessoryMapper.logicalDeleteByPrimaryKey(id);
    }

    public int deleteByGoodsId(Integer goodsId) {
        LitemallOrderGoodsAccessoryExample example = new LitemallOrderGoodsAccessoryExample();
        example.or().andGoodsIdEqualTo(goodsId).andDeletedEqualTo(false);
        return litemallOrderGoodsAccessoryMapper.logicalDeleteByExample(example);
    }

    public int update(LitemallOrderGoodsAccessory litemallGoodsAccessory) {
        return litemallOrderGoodsAccessoryMapper.updateByPrimaryKeySelective(litemallGoodsAccessory);
    }


    public LitemallOrderGoodsAccessory findById(Integer id) {
        return litemallOrderGoodsAccessoryMapper.selectByPrimaryKey(id);
    }
    
    public List<LitemallOrderGoodsAccessory> findByGoodsId(Integer goodsId) {
        LitemallOrderGoodsAccessoryExample example = new LitemallOrderGoodsAccessoryExample();
        example.or().andGoodsIdEqualTo(goodsId).andDeletedEqualTo(false);
        return litemallOrderGoodsAccessoryMapper.selectByExample(example);
    }

    public List<LitemallOrderGoodsAccessory> findByOrderId(Integer orderId) {
        LitemallOrderGoodsAccessoryExample example = new LitemallOrderGoodsAccessoryExample();
        example.or().andOrderIdEqualTo(orderId);
        return litemallOrderGoodsAccessoryMapper.selectByExample(example);
    }

    public List<LitemallOrderGoodsAccessory> querySelective(Integer page, Integer size, String sort, String order) {
        LitemallOrderGoodsAccessoryExample example = new LitemallOrderGoodsAccessoryExample();
        LitemallOrderGoodsAccessoryExample.Criteria criteria = example.createCriteria();


        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, size);
        return litemallOrderGoodsAccessoryMapper.selectByExample(example);
    }



}
