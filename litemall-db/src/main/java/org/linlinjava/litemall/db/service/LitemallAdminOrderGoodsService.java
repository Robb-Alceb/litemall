package org.linlinjava.litemall.db.service;

import org.linlinjava.litemall.db.dao.LitemallAdminOrderGoodsMapper;
import org.linlinjava.litemall.db.domain.LitemallAdminOrderGoods;
import org.linlinjava.litemall.db.domain.LitemallAdminOrderGoodsExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class LitemallAdminOrderGoodsService {
    @Resource
    private LitemallAdminOrderGoodsMapper adminOrderGoodsMapper;

    public int add(LitemallAdminOrderGoods adminOrderGoods) {
        adminOrderGoods.setAddTime(LocalDateTime.now());
        adminOrderGoods.setUpdateTime(LocalDateTime.now());
        return adminOrderGoodsMapper.insertSelective(adminOrderGoods);
    }

    public List<LitemallAdminOrderGoods> queryByAdminOrderid(Integer adminOrderId) {
        LitemallAdminOrderGoodsExample example = new LitemallAdminOrderGoodsExample();
        example.or().andAdminOrderIdEqualTo(adminOrderId).andDeletedEqualTo(false);
        return adminOrderGoodsMapper.selectByExample(example);
    }

    public List<LitemallAdminOrderGoods> queryByOidAndGid(Integer orderId, Integer goodsId) {
        LitemallAdminOrderGoodsExample example = new LitemallAdminOrderGoodsExample();
        example.or().andAdminOrderIdEqualTo(orderId).andGoodsIdEqualTo(goodsId).andDeletedEqualTo(false);
        return adminOrderGoodsMapper.selectByExample(example);
    }

    public List<LitemallAdminOrderGoods> queryByGoodsId(Integer goodsId) {
        LitemallAdminOrderGoodsExample example = new LitemallAdminOrderGoodsExample();
        example.or().andGoodsIdEqualTo(goodsId).andDeletedEqualTo(false);
        return adminOrderGoodsMapper.selectByExample(example);
    }

    public LitemallAdminOrderGoods queryById(Integer id) {
        return adminOrderGoodsMapper.selectByPrimaryKey(id);
    }

    public void updateById(LitemallAdminOrderGoods orderGoods) {
        orderGoods.setUpdateTime(LocalDateTime.now());
        adminOrderGoodsMapper.updateByPrimaryKeySelective(orderGoods);
    }

}
