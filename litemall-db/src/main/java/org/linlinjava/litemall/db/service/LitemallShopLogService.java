package org.linlinjava.litemall.db.service;

import com.github.pagehelper.PageHelper;
import org.linlinjava.litemall.db.dao.LitemallShopLogMapper;
import org.linlinjava.litemall.db.domain.LitemallShopLog;
import org.linlinjava.litemall.db.domain.LitemallShopLogExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class LitemallShopLogService {
    @Resource
    private LitemallShopLogMapper litemallShopLogMapper;

    public List<LitemallShopLog> querySelective(String content, Integer page,
                                                Integer limit, String sort, String order) {
        LitemallShopLogExample example = new LitemallShopLogExample();
        LitemallShopLogExample.Criteria criteria = example.createCriteria();
        if (!StringUtils.isEmpty(content)) {
            criteria.andContentLike("%" + content + "%");
        }
        criteria.andDeletedEqualTo(false);
        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }
        PageHelper.startPage(page, limit);
        return litemallShopLogMapper.selectByExampleSelective(example);
    }

    public int updateById(LitemallShopLog shop) {
        return litemallShopLogMapper.updateByPrimaryKeySelective(shop);
    }

    public void deleteById(Integer id) {
        litemallShopLogMapper.logicalDeleteByPrimaryKey(id);
    }

    public void add(LitemallShopLog shop) {
        shop.setAddTime(LocalDateTime.now());
        litemallShopLogMapper.insertSelective(shop);
    }

    public LitemallShopLog findById(Integer id) {
        return litemallShopLogMapper.selectByPrimaryKeySelective(id);
    }

    public List<LitemallShopLog> all() {
        LitemallShopLogExample example = new LitemallShopLogExample();
        example.or().andDeletedEqualTo(false);
        return litemallShopLogMapper.selectByExample(example);
    }
}
