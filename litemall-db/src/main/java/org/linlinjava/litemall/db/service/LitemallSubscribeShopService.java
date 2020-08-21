package org.linlinjava.litemall.db.service;

import org.linlinjava.litemall.db.dao.LitemallSubscribeShopMapper;
import org.linlinjava.litemall.db.domain.LitemallSubscribeShop;
import org.linlinjava.litemall.db.domain.LitemallSubscribeShopExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class LitemallSubscribeShopService {
    @Resource
    private LitemallSubscribeShopMapper subscribeShopMapper;

    public void deleteById(Integer id) {
        subscribeShopMapper.logicalDeleteByPrimaryKey(id);
    }

    public int add(LitemallSubscribeShop subscribeGoods) {
        subscribeGoods.setAddTime(LocalDateTime.now());
        return subscribeShopMapper.insertSelective(subscribeGoods);
    }

    public List<LitemallSubscribeShop> queryBySubId(Integer subId) {
        LitemallSubscribeShopExample example = new LitemallSubscribeShopExample();
        example.or().andDeletedEqualTo(false).andSubscribeIdEqualTo(subId);
        return subscribeShopMapper.selectByExample(example);
    }

    public int deleteBySubId(Integer subId) {
        LitemallSubscribeShopExample example = new LitemallSubscribeShopExample();
        example.or().andDeletedEqualTo(false).andSubscribeIdEqualTo(subId);
        return subscribeShopMapper.deleteByExample(example);
    }

    public int updateById(LitemallSubscribeShop subscribeGoods) {
        return subscribeShopMapper.updateByPrimaryKeySelective(subscribeGoods);
    }

    public LitemallSubscribeShop findById(Integer id) {
        return subscribeShopMapper.selectByPrimaryKey(id);
    }

    public List<LitemallSubscribeShop> queryByShopId(Integer shopId) {
        LitemallSubscribeShopExample example = new LitemallSubscribeShopExample();
        if(shopId != null){
            example.or().andDeletedEqualTo(false).andShopIdEqualTo(shopId);
        }else{
            example.or().andDeletedEqualTo(false);
        }
        return subscribeShopMapper.selectByExample(example);
    }
}
