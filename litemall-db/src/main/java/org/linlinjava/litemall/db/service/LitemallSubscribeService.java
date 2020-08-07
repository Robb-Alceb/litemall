package org.linlinjava.litemall.db.service;

import com.github.pagehelper.PageHelper;
import org.linlinjava.litemall.db.dao.LitemallSubscribeMapper;
import org.linlinjava.litemall.db.domain.LitemallSubscribe;
import org.linlinjava.litemall.db.domain.LitemallSubscribeExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class LitemallSubscribeService {
    @Resource
    private LitemallSubscribeMapper subscribeMapper;

    public void deleteById(Integer id) {
        subscribeMapper.logicalDeleteByPrimaryKey(id);
    }

    public void add(LitemallSubscribe issue) {
        issue.setAddTime(LocalDateTime.now());
        issue.setUpdateTime(LocalDateTime.now());
        subscribeMapper.insertSelective(issue);
    }

    public List<LitemallSubscribe> querySelective(String name, Integer page, Integer limit, String sort, String order) {
        LitemallSubscribeExample example = new LitemallSubscribeExample();
        LitemallSubscribeExample.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(name)) {
            criteria.andNameLike("%" + name + "%");
        }
        criteria.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, limit);
        return subscribeMapper.selectByExample(example);
    }

    public int updateById(LitemallSubscribe issue) {
        issue.setUpdateTime(LocalDateTime.now());
        return subscribeMapper.updateByPrimaryKeySelective(issue);
    }

    public LitemallSubscribe findById(Integer id) {
        return subscribeMapper.selectByPrimaryKey(id);
    }
}
