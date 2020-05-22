package org.linlinjava.litemall.db.service;

import com.github.pagehelper.PageHelper;
import org.linlinjava.litemall.db.dao.I18nMapper;
import org.linlinjava.litemall.db.dao.LitemallI18nMapper;
import org.linlinjava.litemall.db.domain.LitemallI18n;
import org.linlinjava.litemall.db.domain.LitemallI18nExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class LitemallI18nService {

    @Resource
    private LitemallI18nMapper litemallI18nMapper;
    @Resource
    private I18nMapper i18nMapper;


    public LitemallI18n findById(Integer id) {
        return litemallI18nMapper.selectByPrimaryKey(id);
    }

    public List<LitemallI18n> querySelective(String key, String type, Integer page, Integer size, String sort, String order) {
/*        LitemallI18nExample example = new LitemallI18nExample();
        LitemallI18nExample.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(key)) {
            example.or(criteria);
            criteria.andKeyLike("%" + key + "%");
        }
        if (!StringUtils.isEmpty(type)) {
            criteria.andTypeEqualTo(type);
        }

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, size);
        return litemallI18nMapper.selectByExample(example);*/
        PageHelper.startPage(page, size);
        return i18nMapper.list(key, type, page, size, sort, order);
    }

    public int add(LitemallI18n record){
        record.setAddTime(LocalDateTime.now());
        return litemallI18nMapper.insertSelective(record);
    }

    public int update(LitemallI18n record){
        return litemallI18nMapper.updateByPrimaryKeySelective(record);
    }

    public int delete(Integer id){
        return litemallI18nMapper.logicalDeleteByPrimaryKey(id);
    }

    public int deleteByKey(String key){
        LitemallI18nExample example = new LitemallI18nExample();
        example.or().andKeyEqualTo(key);
        return litemallI18nMapper.logicalDeleteByExample(example);
    }

    public int countByKey(String key){
        LitemallI18nExample example = new LitemallI18nExample();
        example.or().andKeyEqualTo(key);
        return (int) litemallI18nMapper.countByExample(example);
    }

    public int countByKeyAndType(String key, String type) {
        LitemallI18nExample example = new LitemallI18nExample();
        example.or().andKeyEqualTo(key).andTypeEqualTo(type).andDeletedEqualTo(false);
        return (int) litemallI18nMapper.countByExample(example);
    }

    public List<LitemallI18n> queryByKey(String key) {
        LitemallI18nExample example = new LitemallI18nExample();
        example.or().andKeyEqualTo(key).andDeletedEqualTo(false);
        return litemallI18nMapper.selectByExample(example);
    }
}
