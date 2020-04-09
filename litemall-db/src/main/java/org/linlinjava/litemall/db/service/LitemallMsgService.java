package org.linlinjava.litemall.db.service;

import com.github.pagehelper.PageHelper;
import org.linlinjava.litemall.db.dao.LitemallMsgMapper;
import org.linlinjava.litemall.db.domain.LitemallMsg;
import org.linlinjava.litemall.db.domain.LitemallMsgExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class LitemallMsgService {
    @Resource
    private LitemallMsgMapper litemallMsgMapper;

    public List<LitemallMsg> querySelective(Integer userId, Integer page, Integer limit, String sort, String order) {
        LitemallMsgExample example = new LitemallMsgExample();
        LitemallMsgExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(false);
        criteria.andUserIdEqualTo(userId);
        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }
        PageHelper.startPage(page, limit);
        return litemallMsgMapper.selectByExampleSelective(example);
    }

    public void updateById(LitemallMsg litemallMsg) {
        litemallMsgMapper.updateByPrimaryKeySelective(litemallMsg);
    }

    public void deleteById(Integer id) {
        LitemallMsgExample messageExample = new LitemallMsgExample();
        messageExample.or().andIdEqualTo(id);

        LitemallMsg litemallMsg = new LitemallMsg();
        litemallMsg.setId(id);
        litemallMsg.setDeleted(true);
        litemallMsgMapper.updateByExampleSelective(litemallMsg, messageExample);
    }

    public int create(LitemallMsg litemallMsg){
        litemallMsg.setAddTime(LocalDateTime.now());
        return litemallMsgMapper.insertSelective(litemallMsg);
    }


    public long countByMessageId(Integer mid, Integer uid) {
        LitemallMsgExample example = new LitemallMsgExample();
        example.or().andMessageIdEqualTo(mid).andUserIdEqualTo(uid);
        return litemallMsgMapper.countByExample(example);
    }

    public int updateMarkByUserId(LitemallMsg msg) {
        LitemallMsgExample example = new LitemallMsgExample();
        example.or().andUserIdEqualTo(msg.getUserId());
        LitemallMsg record = new LitemallMsg();
        record.setMarkRead(true);
        return litemallMsgMapper.updateByExampleSelective(record, example);
    }

    public int deleteByUserId(Integer userId) {
        LitemallMsgExample messageExample = new LitemallMsgExample();
        messageExample.or().andUserIdEqualTo(userId);

        LitemallMsg litemallMsg = new LitemallMsg();
        litemallMsg.setDeleted(true);
        return litemallMsgMapper.updateByExampleSelective(litemallMsg, messageExample);
    }
}
