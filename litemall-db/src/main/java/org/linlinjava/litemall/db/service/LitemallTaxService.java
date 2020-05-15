package org.linlinjava.litemall.db.service;

import com.github.pagehelper.PageHelper;
import org.linlinjava.litemall.db.dao.LitemallTaxMapper;
import org.linlinjava.litemall.db.domain.LitemallTax;
import org.linlinjava.litemall.db.domain.LitemallTaxExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ：stephen
 * @date ：Created in 2020/5/13 16:31
 * @description：TODO
 */
@Service
public class LitemallTaxService {
    @Resource
    private LitemallTaxMapper litemallTaxMapper;

    public List<LitemallTax> getAll(){
        LitemallTaxExample example = new LitemallTaxExample();
        byte b = 4;
        example.or().andTypeNotEqualTo(b);
        return litemallTaxMapper.selectByExample(example);
    }


    public List<LitemallTax> queryByType(Byte type) {
        LitemallTaxExample example = new LitemallTaxExample();
        example.or().andTypeEqualTo(type);
        return litemallTaxMapper.selectByExample(example);
    }


    public LitemallTax findById(Integer id) {
        return litemallTaxMapper.selectByPrimaryKey(id);
    }

    public List<LitemallTax> querySelective(Integer regionId, String code, Integer page, Integer size, String sort, String order) {
        LitemallTaxExample example = new LitemallTaxExample();
        LitemallTaxExample.Criteria criteria = example.createCriteria();

        if (regionId != null) {
            criteria.andRegionIdEqualTo(regionId);
        }

        if(!StringUtils.isEmpty(code)){
            criteria.andCodeEqualTo(code);
        }

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, size);
        return litemallTaxMapper.selectByExample(example);
    }

    public int add(LitemallTax record){
        record.setAddTime(LocalDateTime.now());
        record.setUpdateTime(LocalDateTime.now());
        return litemallTaxMapper.insertSelective(record);
    }

    public int update(LitemallTax record){
        record.setUpdateTime(LocalDateTime.now());
        return litemallTaxMapper.updateByPrimaryKeySelective(record);
    }

    public List<LitemallTax> queryByRegionIds(List<Integer> regionIds) {
        if(regionIds == null || regionIds.size() == 0){
            return new ArrayList<>();
        }
        LitemallTaxExample example = new LitemallTaxExample();
        example.or().andRegionIdIn(regionIds).andDeletedEqualTo(false);
        return litemallTaxMapper.selectByExample(example);
    }

    public boolean exist(LitemallTax tax) {
        LitemallTaxExample example = new LitemallTaxExample();
        example.or().andRegionIdEqualTo(tax.getRegionId()).andCodeEqualTo(tax.getCode()).andDeletedEqualTo(false);
        return litemallTaxMapper.countByExample(example) > 0;
    }

    public int deleteById(Integer id) {
        return litemallTaxMapper.logicalDeleteByPrimaryKey(id);
    }
}
