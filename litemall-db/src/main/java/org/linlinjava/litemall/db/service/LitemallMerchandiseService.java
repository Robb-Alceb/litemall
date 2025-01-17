package org.linlinjava.litemall.db.service;

import com.github.pagehelper.PageHelper;
import org.linlinjava.litemall.db.dao.LitemallMerchandiseMapper;
import org.linlinjava.litemall.db.domain.LitemallMerchandise;
import org.linlinjava.litemall.db.domain.LitemallMerchandiseExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class LitemallMerchandiseService {
    @Resource
    private LitemallMerchandiseMapper litemallMerchandiseMapper;

    public List<LitemallMerchandise> querySelective(String name, String merchandiseSn,
                                                        Integer page, Integer limit, String sort, String order) {
        LitemallMerchandiseExample example = new LitemallMerchandiseExample();
        LitemallMerchandiseExample.Criteria criteria = example.createCriteria();
        if (!StringUtils.isEmpty(merchandiseSn)) {
            criteria.andMerchandiseSnLike("%" + merchandiseSn + "%");
        }
        if (!StringUtils.isEmpty(name)) {
            criteria.andNameLike("%" + name + "%");
        }
        criteria.andDeletedEqualTo(false);
        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }
        PageHelper.startPage(page, limit);
        return litemallMerchandiseMapper.selectByExampleSelective(example);
    }

    public void updateById(LitemallMerchandise merchandise) {
        merchandise.setUpdateTime(LocalDateTime.now());
        litemallMerchandiseMapper.updateByPrimaryKeySelective(merchandise);
    }

    public void deleteById(LitemallMerchandise merchandise) {
        LitemallMerchandiseExample merchandiseExample = new LitemallMerchandiseExample();
        merchandiseExample.or().andIdEqualTo(merchandise.getId());

        merchandise.setDeleted(true);
        merchandise.setUpdateTime(LocalDateTime.now());
        merchandise.setUpdateTime(LocalDateTime.now());
        litemallMerchandiseMapper.updateByExampleSelective(merchandise, merchandiseExample);
    }

    public void create(LitemallMerchandise litemallMerchandise){
        litemallMerchandise.setAddTime(LocalDateTime.now());
        litemallMerchandise.setUpdateTime(LocalDateTime.now());
        litemallMerchandiseMapper.insertSelective(litemallMerchandise);
    }

    public LitemallMerchandise findById(Integer merchandiseId){
        return litemallMerchandiseMapper.selectByPrimaryKeyWithLogicalDelete(merchandiseId, false);
    }

    public List<LitemallMerchandise> all() {
        LitemallMerchandiseExample merchandiseExample = new LitemallMerchandiseExample();
        merchandiseExample.or().andDeletedEqualTo(false);
        return litemallMerchandiseMapper.selectByExampleSelective(merchandiseExample);
    }

    public Integer count() {
        LitemallMerchandiseExample merchandiseExample = new LitemallMerchandiseExample();
        merchandiseExample.or().andDeletedEqualTo(false);
        return (int)litemallMerchandiseMapper.countByExample(merchandiseExample);
    }

    public LitemallMerchandise queryBySn(String merchandiseSn) {
        LitemallMerchandiseExample merchandiseExample = new LitemallMerchandiseExample();
        merchandiseExample.or().andMerchandiseSnEqualTo(merchandiseSn).andDeletedEqualTo(false);
        return litemallMerchandiseMapper.selectOneByExampleSelective(merchandiseExample);
    }
}
