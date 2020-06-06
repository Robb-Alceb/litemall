package org.linlinjava.litemall.db.service;

import org.linlinjava.litemall.db.dao.LitemallShopMerchandiseMapper;
import org.linlinjava.litemall.db.domain.LitemallShopMerchandise;
import org.linlinjava.litemall.db.domain.LitemallShopMerchandiseExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class LitemallShopMerchandiseService {
    @Resource
    private LitemallShopMerchandiseMapper litemallShopMerchandiseMapper;


    public void updateById(LitemallShopMerchandise merchandise) {
        merchandise.setUpdateTime(LocalDateTime.now());
        litemallShopMerchandiseMapper.updateByPrimaryKeySelective(merchandise);
    }

    public LitemallShopMerchandise queryById(Integer id, Integer shopId) {
        LitemallShopMerchandiseExample merchandise = new LitemallShopMerchandiseExample();
        merchandise.or().andIdEqualTo(id).andLogicalDeleted(false).andShopIdEqualTo(shopId);
        return litemallShopMerchandiseMapper.selectOneByExample(merchandise);
    }

    public void deleteById(Integer id) {
        LitemallShopMerchandiseExample merchandise = new LitemallShopMerchandiseExample();
        merchandise.or().andIdEqualTo(id);
        LitemallShopMerchandise shopMerchandise = new LitemallShopMerchandise();
        shopMerchandise.setDeleted(true);
        shopMerchandise.setUpdateTime(LocalDateTime.now());
        litemallShopMerchandiseMapper.updateByExampleSelective(shopMerchandise, merchandise);
    }

    public int create(LitemallShopMerchandise merchandise) {
        merchandise.setAddTime(LocalDateTime.now());
        merchandise.setUpdateTime(LocalDateTime.now());
        return litemallShopMerchandiseMapper.insertSelective(merchandise);
    }

    public Integer count(Integer shopId) {
        LitemallShopMerchandiseExample merchandise = new LitemallShopMerchandiseExample();
        merchandise.or().andDeletedEqualTo(false).andShopIdEqualTo(shopId);
        return (int)litemallShopMerchandiseMapper.countByExample(merchandise);
    }


    public LitemallShopMerchandise findById(Integer id) {
        return litemallShopMerchandiseMapper.selectByPrimaryKey(id);
    }

    public List<LitemallShopMerchandise> queryByMerIds(Integer shopId, List<Integer> merIds) {
        LitemallShopMerchandiseExample example = new LitemallShopMerchandiseExample();
        example.or().andShopIdEqualTo(shopId).andMerchandiseIdIn(merIds).andDeletedEqualTo(false);
        return litemallShopMerchandiseMapper.selectByExample(example);
    }

    public LitemallShopMerchandise queryByMerId(Integer merId, Integer shopId) {
        LitemallShopMerchandiseExample example = new LitemallShopMerchandiseExample();
        example.or().andShopIdEqualTo(shopId).andMerchandiseIdEqualTo(merId).andDeletedEqualTo(false);
        return litemallShopMerchandiseMapper.selectOneByExample(example);
    }
}
