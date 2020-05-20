package org.linlinjava.litemall.db.service;

import org.linlinjava.litemall.db.dao.LitemallShopRegionMapper;
import org.linlinjava.litemall.db.domain.LitemallShopRegion;
import org.linlinjava.litemall.db.domain.LitemallShopRegionExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author ：stephen
 * @date ：Created in 2020/2/28 14:28
 * @description：TODO
 */
@Service
public class LitemallShopRegionService {
    @Resource
    private LitemallShopRegionMapper shopRegionMapper;

    public void add(LitemallShopRegion shopRegion) {
        shopRegion.setAddTime(LocalDateTime.now());
        shopRegionMapper.insertSelective(shopRegion);
    }
    public LitemallShopRegion queryById(Integer id){
        return shopRegionMapper.selectByPrimaryKey(id);
    }

    public List<LitemallShopRegion> queryByShopId(Integer shopId){
        LitemallShopRegionExample example = new LitemallShopRegionExample();
        example.or().andShopIdEqualTo(shopId).andDeletedEqualTo(false);
        return shopRegionMapper.selectByExample(example);
    }
    public int deletedByShopId(Integer shopId){
        LitemallShopRegionExample example = new LitemallShopRegionExample();
        example.or().andShopIdEqualTo(shopId);
        return shopRegionMapper.logicalDeleteByExample(example);
    }

    public List<LitemallShopRegion> queryByRegionId(Integer regionId){
        LitemallShopRegionExample example = new LitemallShopRegionExample();
        example.or().andRegionIdEqualTo(regionId);
        return shopRegionMapper.selectByExample(example);
    }

    public List<LitemallShopRegion> queryByRegionIds(List<Integer> regionIds) {
        LitemallShopRegionExample example = new LitemallShopRegionExample();
        example.or().andRegionIdIn(regionIds);
        return shopRegionMapper.selectByExample(example);
    }
}
