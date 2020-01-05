package org.linlinjava.litemall.db.service;

import org.linlinjava.litemall.db.dao.LitemallBrowseRecordMapper;
import org.linlinjava.litemall.db.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class LitemallBrowseRecordService {
    @Resource
    private LitemallBrowseRecordMapper browseRecordMapper;


    public List<LitemallBrowseRecord> querySelective(Integer goodsId, Integer categoryId, LocalDateTime startTime, LocalDateTime endTime, Integer offset, Integer limit, String sort, String order) {
        LitemallBrowseRecordExample example = new LitemallBrowseRecordExample();
        LitemallBrowseRecordExample.Criteria criteria1 = example.or();

        if (!StringUtils.isEmpty(goodsId)) {
            criteria1.andGoodsIdEqualTo(goodsId);
        }
        if (!StringUtils.isEmpty(categoryId)) {
            criteria1.andCategoryIdEqualTo(categoryId);
        }
        if (!StringUtils.isEmpty(startTime) && !StringUtils.isEmpty(endTime)) {
            criteria1.andAddTimeBetween(startTime, endTime);
        }
        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }
        criteria1.andDeletedEqualTo(false);
//        PageHelper.startPage(offset, limit);
        return browseRecordMapper.selectByExample(example);
    }

    public List<LitemallBrowseRecord> queryBrowseUserCount(LocalDateTime startTimes, LocalDateTime endTimes) {
        LitemallBrowseRecordExample example = new LitemallBrowseRecordExample();
        LitemallBrowseRecordExample.Criteria criteria1 = example.or();

        criteria1.andAddTimeBetween(startTimes, endTimes);
        criteria1.andDeletedEqualTo(false);
        return browseRecordMapper.selectByExample(example);
    }
    public LitemallBrowseRecord queryByGoodsId(Integer goodsId) {
        LitemallBrowseRecordExample example = new LitemallBrowseRecordExample();
        example.or().andGoodsIdEqualTo(goodsId).andDeletedEqualTo(false);
        return browseRecordMapper.selectOneByExample(example);
    }
    public int add(LitemallBrowseRecord record) {
        record.setAddTime(LocalDateTime.now());
        return browseRecordMapper.insertSelective(record);
    }
    public int updateById(LitemallBrowseRecord record) {
        record.setAddTime(LocalDateTime.now());
        return browseRecordMapper.updateByPrimaryKeySelective(record);
    }
}
