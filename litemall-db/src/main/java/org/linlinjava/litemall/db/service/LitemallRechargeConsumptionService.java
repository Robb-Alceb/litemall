package org.linlinjava.litemall.db.service;

import com.github.pagehelper.PageHelper;
import org.linlinjava.litemall.db.dao.LitemallRechargeConsumptionMapper;
import org.linlinjava.litemall.db.domain.LitemallRechargeConsumption;
import org.linlinjava.litemall.db.domain.LitemallRechargeConsumptionExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author: stephen
 * @Date: 2020/2/13 13:37
 * @Version: 1.0
 * @Description: TODO
 */
@Service
public class LitemallRechargeConsumptionService {
    @Resource
    private LitemallRechargeConsumptionMapper litemallRechargeConsumptionMapper;

    public List<LitemallRechargeConsumption> querySelectiveList(Integer userId, String username, String mobile, Integer page, Integer size, String sort, String order) {
        LitemallRechargeConsumptionExample example = new LitemallRechargeConsumptionExample();
        LitemallRechargeConsumptionExample.Criteria criteria = example.createCriteria();

        if(userId != null){
            criteria.andUserIdEqualTo(userId);
        }
        if (!StringUtils.isEmpty(username)) {
            criteria.andUsernameLike("%" + username + "%");
        }
        if (!StringUtils.isEmpty(mobile)) {
            criteria.andMobileEqualTo(mobile);
        }
        criteria.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, size);
        return litemallRechargeConsumptionMapper.selectByExample(example);
    }
    public List<LitemallRechargeConsumption> queryByUserId(Integer userId){
        LitemallRechargeConsumptionExample example = new LitemallRechargeConsumptionExample();
        example.or().andUserIdEqualTo(userId).andDeletedEqualTo(false);
        return litemallRechargeConsumptionMapper.selectByExample(example);
    }

    public int add(LitemallRechargeConsumption record){
        record.setAddTime(LocalDateTime.now());
        return litemallRechargeConsumptionMapper.insertSelective(record);
    }
}
