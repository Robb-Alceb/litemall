package org.linlinjava.litemall.db.service;

import com.github.pagehelper.PageHelper;
import org.linlinjava.litemall.db.beans.Constants;
import org.linlinjava.litemall.db.dao.LitemallUserRechargeOrderMapper;
import org.linlinjava.litemall.db.domain.LitemallUserRechargeOrder;
import org.linlinjava.litemall.db.domain.LitemallUserRechargeOrderExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class LitemallUserRechargeOrderService {
    @Resource
    private LitemallUserRechargeOrderMapper litemallUserRechargeOrderMapper;


    public List<LitemallUserRechargeOrder> querySelective(Integer userId, Integer page, Integer limit, String sort, String order) {
        LitemallUserRechargeOrderExample example = new LitemallUserRechargeOrderExample();
        LitemallUserRechargeOrderExample.Criteria criteria = example.createCriteria();

        criteria.andDeletedEqualTo(false);

        criteria.andUserIdEqualTo(userId);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, limit);
        return litemallUserRechargeOrderMapper.selectByExample(example);
    }

    public int updateById(LitemallUserRechargeOrder order) {
        order.setUpdateTime(LocalDateTime.now());
        return litemallUserRechargeOrderMapper.updateByPrimaryKeySelective(order);
    }

    public void deleteById(Integer id) {
        litemallUserRechargeOrderMapper.logicalDeleteByPrimaryKey(id);
    }

    public void add(LitemallUserRechargeOrder order) {
        order.setAddTime(LocalDateTime.now());
        order.setUpdateTime(LocalDateTime.now());
        litemallUserRechargeOrderMapper.insertSelective(order);
    }

    public LitemallUserRechargeOrder findById(Integer id) {
        return litemallUserRechargeOrderMapper.selectByPrimaryKey(id);
    }

    public int paddingPay(LitemallUserRechargeOrder update, LitemallUserRechargeOrder order) {
        LitemallUserRechargeOrderExample example = new LitemallUserRechargeOrderExample();
        example.or().andIdEqualTo(order.getId()).andUpdateTimeEqualTo(order.getUpdateTime());
        update.setUpdateTime(LocalDateTime.now());
        return litemallUserRechargeOrderMapper.updateByExampleSelective(update,example);
    }

    public LitemallUserRechargeOrder findByOutTradeNo(String outTradeNo) {
        LitemallUserRechargeOrderExample example = new LitemallUserRechargeOrderExample();
        example.or().andOutTradeNoEqualTo(outTradeNo).andDeletedEqualTo(false);
        return litemallUserRechargeOrderMapper.selectOneByExample(example);
    }

    public int payDone(LitemallUserRechargeOrder order,LitemallUserRechargeOrder update) {
        LitemallUserRechargeOrderExample example = new LitemallUserRechargeOrderExample();
        example.or().andIdEqualTo(order.getId()).andUpdateTimeEqualTo(order.getUpdateTime());
        update.setUpdateTime(LocalDateTime.now());
        return litemallUserRechargeOrderMapper.updateByExampleSelective(update,example);
    }

    public List<LitemallUserRechargeOrder> getUnpaidOrder(Integer minutes) {
        LitemallUserRechargeOrderExample example = new LitemallUserRechargeOrderExample();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expired = now.minusMinutes(minutes);
        example.or().andPayStatusEqualTo(Constants.PAY_STATUS_AUTO_CANCEL).andUpdateTimeLessThan(expired).andDeletedEqualTo(false);
        return litemallUserRechargeOrderMapper.selectByExample(example);
    }

}
