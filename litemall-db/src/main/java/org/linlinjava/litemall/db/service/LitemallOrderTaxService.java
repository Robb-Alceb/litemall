package org.linlinjava.litemall.db.service;

import org.linlinjava.litemall.db.dao.LitemallOrderTaxMapper;
import org.linlinjava.litemall.db.domain.LitemallOrderTax;
import org.linlinjava.litemall.db.domain.LitemallOrderTaxExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author ：stephen
 * @date ：Created in 2020/5/13 16:31
 * @description：TODO
 */
@Service
public class LitemallOrderTaxService {
    @Resource
    private LitemallOrderTaxMapper litemallOrderTaxMapper;


    public LitemallOrderTax findById(Integer id) {
        return litemallOrderTaxMapper.selectByPrimaryKey(id);
    }


    public int add(LitemallOrderTax record){
        record.setAddTime(LocalDateTime.now());
        return litemallOrderTaxMapper.insertSelective(record);
    }

    public int update(LitemallOrderTax record){
        return litemallOrderTaxMapper.updateByPrimaryKeySelective(record);
    }
    public List<LitemallOrderTax> queryByOrderId(Integer orderId){
        LitemallOrderTaxExample example = new LitemallOrderTaxExample();
        example.or().andOrderIdEqualTo(orderId).andDeletedEqualTo(false);
        return litemallOrderTaxMapper.selectByExample(example);
    }

    public int deleteById(Integer id) {
        return litemallOrderTaxMapper.logicalDeleteByPrimaryKey(id);
    }
}
