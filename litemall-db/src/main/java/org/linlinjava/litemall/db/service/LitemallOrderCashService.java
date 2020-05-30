package org.linlinjava.litemall.db.service;

import com.github.pagehelper.PageHelper;
import org.linlinjava.litemall.db.dao.LitemallOrderCashMapper;
import org.linlinjava.litemall.db.domain.LitemallOrderCash;
import org.linlinjava.litemall.db.domain.LitemallOrderCashExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class LitemallOrderCashService {

    @Resource
    private LitemallOrderCashMapper litemallOrderCashMapper;



    public LitemallOrderCash findById(Integer id) {
        return litemallOrderCashMapper.selectByPrimaryKey(id);
    }

    public List<LitemallOrderCash> querySelective(String username, Integer orderId, Integer page, Integer size, String sort, String order) {
        LitemallOrderCashExample example = new LitemallOrderCashExample();
        LitemallOrderCashExample.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(username)) {
            criteria.andAddUserNameEqualTo(username);
        }
        if (orderId != null) {
            criteria.andOrderIdEqualTo(orderId);
        }

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, size);
        return litemallOrderCashMapper.selectByExample(example);
    }

    public int add(LitemallOrderCash record){
        record.setAddTime(LocalDateTime.now());
        return litemallOrderCashMapper.insertSelective(record);
    }

    public int update(LitemallOrderCash record){
        return litemallOrderCashMapper.updateByPrimaryKeySelective(record);
    }

    public int delete(Integer id){
        return litemallOrderCashMapper.logicalDeleteByPrimaryKey(id);
    }


    public List<LitemallOrderCash> queryByOrderId(Integer orderId) {
        LitemallOrderCashExample example = new LitemallOrderCashExample();
        example.or().andOrderIdEqualTo(orderId).andDeletedEqualTo(false);
        return litemallOrderCashMapper.selectByExample(example);
    }
}
