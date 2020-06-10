package org.linlinjava.litemall.db.service;

import org.linlinjava.litemall.db.dao.LitemallCollectAccessoryMapper;
import org.linlinjava.litemall.db.domain.LitemallCollectAccessory;
import org.linlinjava.litemall.db.domain.LitemallCollectAccessoryExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class LitemallCollectAccessoryService {
    @Resource
    private LitemallCollectAccessoryMapper litemallCollectAccessoryMapper;


    public int updateById(LitemallCollectAccessory collect){
        return litemallCollectAccessoryMapper.updateByPrimaryKeySelective(collect);
    }

    public List<LitemallCollectAccessory> queryByCollectId(Integer collectId) {
        LitemallCollectAccessoryExample example = new LitemallCollectAccessoryExample();
        example.or().andCollectIdEqualTo(collectId).andDeletedEqualTo(false);
        return litemallCollectAccessoryMapper.selectByExample(example);
    }


    public int deleteByCollectId(Integer collectId) {
        LitemallCollectAccessoryExample example = new LitemallCollectAccessoryExample();
        example.or().andCollectIdEqualTo(collectId);
        return litemallCollectAccessoryMapper.logicalDeleteByExample(example);
    }

    public void deleteById(Integer id) {
        litemallCollectAccessoryMapper.logicalDeleteByPrimaryKey(id);
    }

    public int add(LitemallCollectAccessory collect) {
        return litemallCollectAccessoryMapper.insertSelective(collect);
    }
}
