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

    public List<LitemallCartGoodsAccessory> queryByCartId(Integer cartId) {
        LitemallCartGoodsAccessoryExample example = new LitemallCartGoodsAccessoryExample();
        example.or().andCartIdEqualTo(cartId).andDeletedEqualTo(false);
        return litemallCartGoodsAccessoryMapper.selectByExample(example);
    }


    public boolean exist(Integer cartId, Integer accessoryId, Integer number) {
        LitemallCartGoodsAccessoryExample example = new LitemallCartGoodsAccessoryExample();
        example.or().andCartIdEqualTo(cartId).andAccessoryIdEqualTo(accessoryId).andNumberEqualTo(number).andDeletedEqualTo(false);
        return litemallCartGoodsAccessoryMapper.countByExample(example) == 1;
    }

    public void deleteByCartIds(List<Integer> cartIds) {
        LitemallCartGoodsAccessoryExample example = new LitemallCartGoodsAccessoryExample();
        example.or().andCartIdIn(cartIds);
        litemallCartGoodsAccessoryMapper.logicalDeleteByExample(example);
    }

    public void deleteByCartId(Integer id) {
        LitemallCartGoodsAccessoryExample example = new LitemallCartGoodsAccessoryExample();
        example.or().andCartIdEqualTo(id);
        litemallCartGoodsAccessoryMapper.logicalDeleteByExample(example);
    }

    public int countByCartId(Integer cartId) {
        LitemallCartGoodsAccessoryExample example = new LitemallCartGoodsAccessoryExample();
        example.or().andCartIdEqualTo(cartId).andDeletedEqualTo(false);
        return (int)litemallCartGoodsAccessoryMapper.countByExample(example);
    }
}
