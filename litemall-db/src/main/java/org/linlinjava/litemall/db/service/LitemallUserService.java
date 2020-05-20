package org.linlinjava.litemall.db.service;

import com.github.pagehelper.PageHelper;
import org.linlinjava.litemall.db.dao.LitemallRechargeConsumptionMapper;
import org.linlinjava.litemall.db.dao.LitemallUserMapper;
import org.linlinjava.litemall.db.dao.UserMapper;
import org.linlinjava.litemall.db.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class LitemallUserService {
    @Resource
    private LitemallUserMapper litemallUserMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private LitemallRechargeConsumptionMapper litemallRechargeConsumptionMapper;

    public LitemallUser findById(Integer userId) {
        return litemallUserMapper.selectByPrimaryKey(userId);
    }

    public UserVo findUserVoById(Integer userId) {
        LitemallUser user = findById(userId);
        UserVo userVo = new UserVo();
        userVo.setNickname(user.getNickname());
        userVo.setAvatar(user.getAvatar());
        return userVo;
    }

    public LitemallUser queryByOid(String openId) {
        LitemallUserExample example = new LitemallUserExample();
        example.or().andWeixinOpenidEqualTo(openId).andDeletedEqualTo(false);
        return litemallUserMapper.selectOneByExample(example);
    }

    public void add(LitemallUser user) {
        user.setAddTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        litemallUserMapper.insertSelective(user);
    }

    public int updateById(LitemallUser user) {
        user.setUpdateTime(LocalDateTime.now());
        return litemallUserMapper.updateByPrimaryKeySelective(user);
    }

    public List<LitemallUser> querySelective(String username, String mobile, Integer page, Integer size, String sort, String order) {
        LitemallUserExample example = new LitemallUserExample();
        LitemallUserExample.Criteria criteria = example.createCriteria();

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
        return litemallUserMapper.selectByExample(example);
    }

    public int count() {
        LitemallUserExample example = new LitemallUserExample();
        example.or().andDeletedEqualTo(false);

        return (int) litemallUserMapper.countByExample(example);
    }

    public List<LitemallUser> queryByUsername(String username) {
        LitemallUserExample example = new LitemallUserExample();
        example.or().andUsernameEqualTo(username).andDeletedEqualTo(false);
        return litemallUserMapper.selectByExample(example);
    }

    public boolean checkByUsername(String username) {
        LitemallUserExample example = new LitemallUserExample();
        example.or().andUsernameEqualTo(username).andDeletedEqualTo(false);
        return litemallUserMapper.countByExample(example) != 0;
    }

    public List<LitemallUser> queryByMobile(String mobile) {
        LitemallUserExample example = new LitemallUserExample();
        example.or().andMobileEqualTo(mobile).andDeletedEqualTo(false);
        return litemallUserMapper.selectByExample(example);
    }


    public List<LitemallUser> queryByEmail(String email) {
        LitemallUserExample example = new LitemallUserExample();
        example.or().andEmailEqualTo(email).andDeletedEqualTo(false);
        return litemallUserMapper.selectByExample(example);
    }

    public List<LitemallUser> queryByOpenid(String openid) {
        LitemallUserExample example = new LitemallUserExample();
        example.or().andWeixinOpenidEqualTo(openid).andDeletedEqualTo(false);
        return litemallUserMapper.selectByExample(example);
    }

    public void deleteById(Integer id) {
        litemallUserMapper.logicalDeleteByPrimaryKey(id);
    }

    public List<LitemallUser> queryUserByTime(LocalDateTime startTime, LocalDateTime endTime) {
        LitemallUserExample example = new LitemallUserExample();
        LitemallUserExample.Criteria criteria = example.createCriteria();
        criteria.andAddTimeBetween(startTime, endTime);
        criteria.andDeletedEqualTo(false);
        return litemallUserMapper.selectByExample(example);
    }

    public long total() {
        LitemallUserExample example = new LitemallUserExample();
        example.or().andDeletedEqualTo(false);
        return litemallUserMapper.countByExample(example);
    }

    public List<Map<String, Object>>  queryAddUserStatistics(Map<String, Object> map){
        return userMapper.queryUserStatistics(map);
    }

    public List<LitemallUser> queryAll() {
        LitemallUserExample example = new LitemallUserExample();
        example.or().andDeletedEqualTo(false);
        return litemallUserMapper.selectByExample(example);
    }

    public List<LitemallRechargeConsumption> querySelectiveList(String username, String mobile, Integer page, Integer size, String sort, String order) {
        LitemallRechargeConsumptionExample example = new LitemallRechargeConsumptionExample();
        LitemallRechargeConsumptionExample.Criteria criteria = example.createCriteria();

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

    public List<LitemallUser> queryByUserLevels(List<Byte> integers) {
        LitemallUserExample example = new LitemallUserExample();
        example.or().andUserLevelIn(integers).andDeletedEqualTo(false);
        return litemallUserMapper.selectByExample(example);
    }

    public int updateWithOptimisticLocker(LitemallUser update, LocalDateTime updateTime) {
        LitemallUserExample example = new LitemallUserExample();
        example.or().andIdEqualTo(update.getId()).andUpdateTimeEqualTo(updateTime).andDeletedEqualTo(false);
        return litemallUserMapper.updateByExampleSelective(update,example);
    }

    public List<LitemallUser> findByName(String userName) {
        LitemallUserExample example = new LitemallUserExample();
        example.or().andUsernameLike("%"+userName+"%").andDeletedEqualTo(false);
        return litemallUserMapper.selectByExample(example);
    }

    public void deleteByUserName(String username) {
        LitemallUserExample example = new LitemallUserExample();
        example.or().andUsernameEqualTo(username).andInnerAccountEqualTo(true).andDeletedEqualTo(false);
        litemallUserMapper.logicalDeleteByExample(example);
    }

    public LitemallUser findByAdmin(String username, boolean innerAccount) {
        LitemallUserExample example = new LitemallUserExample();
        example.or().andUsernameEqualTo(username).andInnerAccountEqualTo(innerAccount).andDeletedEqualTo(false);
        return litemallUserMapper.selectOneByExample(example);
    }
}
