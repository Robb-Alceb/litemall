package org.linlinjava.litemall.db.service;

import org.linlinjava.litemall.db.dao.LitemallOrderGoodsMapper;
import org.linlinjava.litemall.db.domain.LitemallOrderGoods;
import org.linlinjava.litemall.db.domain.LitemallOrderGoodsExample;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class LitemallOrderGoodsService {
    @Resource
    private LitemallOrderGoodsMapper orderGoodsMapper;

    public int add(LitemallOrderGoods orderGoods) {
        orderGoods.setAddTime(LocalDateTime.now());
        orderGoods.setUpdateTime(LocalDateTime.now());
        return orderGoodsMapper.insertSelective(orderGoods);
    }

    public List<LitemallOrderGoods> queryByGidAndSid(Integer goodsId, Integer shopId) {
        LitemallOrderGoodsExample example = new LitemallOrderGoodsExample();
        if(shopId != null){
            example.or().andGoodsIdEqualTo(goodsId).andShopIdEqualTo(shopId).andDeletedEqualTo(false);
        }else{
            example.or().andGoodsIdEqualTo(goodsId).andDeletedEqualTo(false);
        }
        return orderGoodsMapper.selectByExample(example);
    }

    public List<LitemallOrderGoods> queryByGid(Integer goodsId) {
        return queryByGidAndSid(goodsId, null);
    }

    public List<LitemallOrderGoods> queryByOid(Integer orderId) {
        LitemallOrderGoodsExample example = new LitemallOrderGoodsExample();
        example.or().andOrderIdEqualTo(orderId).andDeletedEqualTo(false);
        return orderGoodsMapper.selectByExample(example);
    }

    public List<LitemallOrderGoods> findByOidAndGid(Integer orderId, Integer goodsId) {
        LitemallOrderGoodsExample example = new LitemallOrderGoodsExample();
        example.or().andOrderIdEqualTo(orderId).andGoodsIdEqualTo(goodsId).andDeletedEqualTo(false);
        return orderGoodsMapper.selectByExample(example);
    }

    public List<LitemallOrderGoods> findByShopIdAndGoodsid(Integer shopId, Integer goodsId) {
        LitemallOrderGoodsExample example = new LitemallOrderGoodsExample();
        example.or().andShopIdEqualTo(shopId).andGoodsIdEqualTo(goodsId).andDeletedEqualTo(false);
        return orderGoodsMapper.selectByExample(example);
    }

    public LitemallOrderGoods findById(Integer id) {
        return orderGoodsMapper.selectByPrimaryKey(id);
    }

    public void updateById(LitemallOrderGoods orderGoods) {
        orderGoods.setUpdateTime(LocalDateTime.now());
        orderGoodsMapper.updateByPrimaryKeySelective(orderGoods);
    }

    public Short getComments(Integer orderId) {
        LitemallOrderGoodsExample example = new LitemallOrderGoodsExample();
        example.or().andOrderIdEqualTo(orderId).andDeletedEqualTo(false);
        long count = orderGoodsMapper.countByExample(example);
        return (short) count;
    }

    public boolean checkExist(Integer goodsId) {
        LitemallOrderGoodsExample example = new LitemallOrderGoodsExample();
        example.or().andGoodsIdEqualTo(goodsId).andDeletedEqualTo(false);
        return orderGoodsMapper.countByExample(example) != 0;
    }

    public List<LitemallOrderGoods> queryGoodsStatistics(LocalDateTime startTime, LocalDateTime endTime, Integer shopId, Integer goodsId) {
        LitemallOrderGoodsExample example = new LitemallOrderGoodsExample();
        LitemallOrderGoodsExample.Criteria criteria = example.or();
        if(!ObjectUtils.isEmpty(shopId)){
            criteria.andShopIdEqualTo(shopId);
        }
        if(!ObjectUtils.isEmpty(goodsId)){
            criteria.andGoodsIdEqualTo(goodsId);
        }
        if (!ObjectUtils.isEmpty(startTime) && !ObjectUtils.isEmpty(endTime)) {
            criteria.andAddTimeBetween(startTime, endTime);
        }
        criteria.andDeletedEqualTo(false);
        return orderGoodsMapper.selectByExample(example);
    }

    public List<LitemallOrderGoods> queryGoodsSalesStatistics(Integer goodsId, Integer categoryId, LocalDateTime startTime,  LocalDateTime endTime, Integer page,
                                                         Integer limit, String sort, String order) {
        LitemallOrderGoodsExample example = new LitemallOrderGoodsExample();
        LitemallOrderGoodsExample.Criteria criteria = example.or();
        if(!ObjectUtils.isEmpty(goodsId)){
            criteria.andGoodsIdEqualTo(goodsId);
        }
        if(!ObjectUtils.isEmpty(categoryId)){
            criteria.andCategoryIdEqualTo(categoryId);
        }
        if (!ObjectUtils.isEmpty(startTime) && !ObjectUtils.isEmpty(endTime)) {
            criteria.andAddTimeBetween(startTime, endTime);
        }
        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }
//        PageHelper.startPage(page, limit);
        criteria.andDeletedEqualTo(false);
        return orderGoodsMapper.selectByExample(example);
    }
}
