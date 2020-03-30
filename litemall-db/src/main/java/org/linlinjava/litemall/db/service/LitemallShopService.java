package org.linlinjava.litemall.db.service;

import com.github.pagehelper.PageHelper;
import org.linlinjava.litemall.db.dao.LitemallShopMapper;
import org.linlinjava.litemall.db.domain.LitemallShop;
import org.linlinjava.litemall.db.domain.LitemallShop.Column;
import org.linlinjava.litemall.db.domain.LitemallShopExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class LitemallShopService {
    private final Column[] idAndName = new Column[]{Column.id, Column.name, Column.types};
    @Resource
    private LitemallShopMapper litemallShopMapper;

    public List<LitemallShop> querySelective(Integer shopId, String name, String address, Short status,
                                             LocalDateTime addTimeFrom, LocalDateTime addTimeTo, Integer page,
                                             Integer limit, String sort, String order) {
        LitemallShopExample example = new LitemallShopExample();
        LitemallShopExample.Criteria criteria = example.createCriteria();
        if(null != shopId){
            criteria.andIdEqualTo(shopId);
        }
        if (!StringUtils.isEmpty(name)) {
            criteria.andNameLike("%" + name + "%");
        }
        if (!StringUtils.isEmpty(status)) {
            criteria.andStatusEqualTo(status);
        }
        if (null != addTimeFrom && null != addTimeTo) {
            criteria.andAddTimeBetween(addTimeFrom,addTimeTo);
        }
        criteria.andDeletedEqualTo(false);
        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }
        PageHelper.startPage(page, limit);
        return litemallShopMapper.selectByExampleSelective(example);
    }

    public int updateById(LitemallShop shop) {
        shop.setUpdateTime(LocalDateTime.now());
        return litemallShopMapper.updateByPrimaryKeySelective(shop);
    }

    public void deleteById(Integer id) {
        litemallShopMapper.logicalDeleteByPrimaryKey(id);
    }

    public void add(LitemallShop shop) {
        shop.setAddTime(LocalDateTime.now());
        shop.setUpdateTime(LocalDateTime.now());
        litemallShopMapper.insertSelective(shop);
    }

    public LitemallShop findById(Integer id) {
        return litemallShopMapper.selectByPrimaryKeySelective(id);
    }

    public List<LitemallShop> all() {
        LitemallShopExample example = new LitemallShopExample();
        example.or().andDeletedEqualTo(false);
        return litemallShopMapper.selectByExample(example);
    }

    public List<LitemallShop> getByIds(List<Integer> ids) {
        LitemallShopExample example = new LitemallShopExample();
        example.or().andIdIn(ids).andDeletedEqualTo(false);
        return litemallShopMapper.selectByExampleSelective(example,idAndName);
    }

    public LitemallShop getInfoById(Integer id) {
        LitemallShopExample example = new LitemallShopExample();
        example.or().andIdEqualTo(id).andDeletedEqualTo(false);
        return litemallShopMapper.selectOneByExampleSelective(example,idAndName);
    }
}
