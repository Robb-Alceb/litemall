package org.linlinjava.litemall.db.service;

import com.github.pagehelper.PageHelper;
import org.linlinjava.litemall.db.dao.LitemallRegionMapper;
import org.linlinjava.litemall.db.domain.LitemallRegion;
import org.linlinjava.litemall.db.domain.LitemallRegionExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class LitemallRegionService {

    @Resource
    private LitemallRegionMapper regionMapper;

    public List<LitemallRegion> getAll(){
        LitemallRegionExample example = new LitemallRegionExample();
        byte b = 4;
        example.or().andTypeNotEqualTo(b);
        return regionMapper.selectByExample(example);
    }


    public List<LitemallRegion> queryByType(Byte type) {
        LitemallRegionExample example = new LitemallRegionExample();
        example.or().andTypeEqualTo(type);
        return regionMapper.selectByExample(example);
    }

    public List<LitemallRegion> queryByPid(Integer parentId) {
        LitemallRegionExample example = new LitemallRegionExample();
        example.or().andPidEqualTo(parentId);
        return regionMapper.selectByExample(example);
    }

    public LitemallRegion findById(Integer id) {
        return regionMapper.selectByPrimaryKey(id);
    }

    public List<LitemallRegion> querySelective(String name, String code, Integer page, Integer size, String sort, String order) {
        LitemallRegionExample example = new LitemallRegionExample();
        LitemallRegionExample.Criteria criteria = example.createCriteria();
        LitemallRegionExample.Criteria criteriaEn = example.createCriteria();

        if (!StringUtils.isEmpty(name)) {
            example.or(criteria);
            example.or(criteriaEn);
            criteria.andNameCnLike("%" + name + "%");
            criteriaEn.andNameEnLike("%" + name + "%");
        }
        if (!StringUtils.isEmpty(code)) {
            criteria.andCodeEqualTo(code);
            criteriaEn.andCodeEqualTo(code);
        }

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, size);
        return regionMapper.selectByExample(example);
    }

    public List<LitemallRegion> findByIds(List<Integer> collect) {
        if(collect == null || collect.size() == 0){
            return null;
        }
        LitemallRegionExample example = new LitemallRegionExample();
        example.or().andIdIn(collect);
        return regionMapper.selectByExample(example);
    }
}
