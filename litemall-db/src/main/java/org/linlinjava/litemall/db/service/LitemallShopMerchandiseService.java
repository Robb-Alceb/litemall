package org.linlinjava.litemall.db.service;

import com.github.pagehelper.PageHelper;
import org.linlinjava.litemall.db.dao.LitemallShopMerchandiseMapper;
import org.linlinjava.litemall.db.domain.LitemallShopMerchandise;
import org.linlinjava.litemall.db.domain.LitemallShopMerchandiseExample;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class LitemallShopMerchandiseService {
    @Resource
    private LitemallShopMerchandiseMapper litemallShopMerchandiseMapper;

    public List<LitemallShopMerchandise> querySelective(String merchandiseName, String merchandiseSn, Integer shopId,
                                                        Integer page, Integer limit, String sort, String order) {
        LitemallShopMerchandiseExample example = new LitemallShopMerchandiseExample();
        LitemallShopMerchandiseExample.Criteria criteria = example.createCriteria();
        if(!ObjectUtils.isEmpty(shopId)){
            criteria.andShopIdEqualTo(shopId);
        }
        if (!StringUtils.isEmpty(merchandiseSn)) {
            criteria.andMerchandiseSnLike("%" + merchandiseSn + "%");
        }
        if (!StringUtils.isEmpty(merchandiseName)) {
            criteria.andMerchandiseNameLike("%" + merchandiseName + "%");
        }
        criteria.andDeletedEqualTo(false);
        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }
        PageHelper.startPage(page, limit);
        return litemallShopMerchandiseMapper.selectByExampleSelective(example);
    }

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
        litemallShopMerchandiseMapper.updateByExample(shopMerchandise, merchandise);
    }
}
