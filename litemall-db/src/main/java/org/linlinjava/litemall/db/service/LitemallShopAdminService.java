package org.linlinjava.litemall.db.service;

import org.linlinjava.litemall.db.dao.LitemallShopAdminMapper;
import org.linlinjava.litemall.db.domain.LitemallShopAdmin;
import org.linlinjava.litemall.db.domain.LitemallShopAdminExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class LitemallShopAdminService {
    @Resource
    private LitemallShopAdminMapper litemallShopAdminMapper;


    public int updateById(LitemallShopAdmin shop) {
        shop.setUpdateTime(LocalDateTime.now());
        return litemallShopAdminMapper.updateByPrimaryKeySelective(shop);
    }

    public void deleteById(Integer id) {
        litemallShopAdminMapper.logicalDeleteByPrimaryKey(id);
    }

    public void add(LitemallShopAdmin shop) {
        shop.setAddTime(LocalDateTime.now());
        shop.setUpdateTime(LocalDateTime.now());
        litemallShopAdminMapper.insertSelective(shop);
    }

    public List<LitemallShopAdmin> findByIds(List<Integer> ids) {
        LitemallShopAdminExample example = new LitemallShopAdminExample();
        LitemallShopAdminExample.Criteria criteria = LitemallShopAdminExample.newAndCreateCriteria();
        criteria.andIdIn(ids);
        return litemallShopAdminMapper.selectByExample(example);
    }

    public List<LitemallShopAdmin> findByShopId(Integer shopId) {
        LitemallShopAdminExample example = new LitemallShopAdminExample();
        LitemallShopAdminExample.Criteria criteria = LitemallShopAdminExample.newAndCreateCriteria();
        criteria.andShopIdEqualTo(shopId);
        return litemallShopAdminMapper.selectByExample(example);
    }


    public LitemallShopAdmin findById(Integer id) {
        return litemallShopAdminMapper.selectByPrimaryKeySelective(id);
    }

    public List<LitemallShopAdmin> all() {
        LitemallShopAdminExample example = new LitemallShopAdminExample();
        example.or().andDeletedEqualTo(false);
        return litemallShopAdminMapper.selectByExample(example);
    }
}
