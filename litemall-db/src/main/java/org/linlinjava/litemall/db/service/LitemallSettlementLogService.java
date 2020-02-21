package org.linlinjava.litemall.db.service;

import com.github.pagehelper.PageHelper;
import org.linlinjava.litemall.db.dao.LitemallLogMapper;
import org.linlinjava.litemall.db.dao.LitemallSettlementLogMapper;
import org.linlinjava.litemall.db.domain.LitemallLog;
import org.linlinjava.litemall.db.domain.LitemallLogExample;
import org.linlinjava.litemall.db.domain.LitemallSettlementLog;
import org.linlinjava.litemall.db.domain.LitemallSettlementLogExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class LitemallSettlementLogService {
    @Resource
    private LitemallSettlementLogMapper settlementLogMapper;

    public void deleteById(Integer id) {
        settlementLogMapper.logicalDeleteByPrimaryKey(id);
    }

    public void add(LitemallSettlementLog log) {
        log.setAddTime(LocalDateTime.now());
        log.setUpdateTime(LocalDateTime.now());
        settlementLogMapper.insertSelective(log);
    }

    public List<LitemallSettlementLog> querySelective(String name, Integer page, Integer size, String sort, String order) {
        LitemallSettlementLogExample example = new LitemallSettlementLogExample();
        LitemallSettlementLogExample.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(name)) {
            criteria.andUserNameLike("%" + name + "%");
        }
        criteria.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, size);
        return settlementLogMapper.selectByExample(example);
    }
}
