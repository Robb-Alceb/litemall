package org.linlinjava.litemall.db.service;

import com.github.pagehelper.PageHelper;
import org.linlinjava.litemall.db.dao.LitemallCommentMapper;
import org.linlinjava.litemall.db.dao.LitemallOrderRecordMapper;
import org.linlinjava.litemall.db.domain.LitemallComment;
import org.linlinjava.litemall.db.domain.LitemallCommentExample;
import org.linlinjava.litemall.db.domain.LitemallOrderRecord;
import org.linlinjava.litemall.db.domain.LitemallOrderRecordExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class LitemallOrderRecordService {
    @Resource
    private LitemallOrderRecordMapper orderRecordMapper;

    public List<LitemallOrderRecord> querySelective(Integer orderId, Integer page, Integer limit, String sort, String order) {
        LitemallOrderRecordExample example = new LitemallOrderRecordExample();
        LitemallOrderRecordExample.Criteria criteria = example.createCriteria();

        if (orderId != null) {
            criteria.andOrderIdEqualTo(orderId);
        }
        criteria.andDeletedEqualTo(false);
        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, limit);
        return orderRecordMapper.selectByExample(example);
    }
    
    public LitemallOrderRecord queryById(Integer id) {
        return orderRecordMapper.selectByPrimaryKey(id);
    }

    public int add(LitemallOrderRecord record) {
        record.setAddTime(LocalDateTime.now());
        return orderRecordMapper.insertSelective(record);
    }

}
