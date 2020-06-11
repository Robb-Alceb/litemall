package org.linlinjava.litemall.db.service;

import com.github.pagehelper.PageHelper;
import org.linlinjava.litemall.db.dao.LitemallNoticeMapper;
import org.linlinjava.litemall.db.domain.LitemallNotice;
import org.linlinjava.litemall.db.domain.LitemallNoticeExample;
import org.linlinjava.litemall.db.domain.LitemallNoticeWithBLOBs;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class LitemallNoticeService {
    @Resource
    private LitemallNoticeMapper litemallNoticeMapper;

    public List<LitemallNoticeWithBLOBs> querySelective(Integer userId, Integer page, Integer limit, String sort, String order) {
        LitemallNoticeExample example = new LitemallNoticeExample();
        LitemallNoticeExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(false);
        criteria.andUserIdEqualTo(userId);
        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }
        PageHelper.startPage(page, limit);
        return litemallNoticeMapper.selectByExampleSelective(example);
    }

    public void updateById(LitemallNoticeWithBLOBs litemallMsg) {
        litemallNoticeMapper.updateByPrimaryKeySelective(litemallMsg);
    }

    public void deleteById(Integer id) {
        LitemallNoticeExample messageExample = new LitemallNoticeExample();
        messageExample.or().andIdEqualTo(id);

        LitemallNoticeWithBLOBs litemallMsg = new LitemallNoticeWithBLOBs();
        litemallMsg.setId(id);
        litemallMsg.setDeleted(true);
        litemallNoticeMapper.updateByExampleSelective(litemallMsg, messageExample);
    }

    public int create(LitemallNoticeWithBLOBs litemallMsg){
        litemallMsg.setAddTime(LocalDateTime.now());
        return litemallNoticeMapper.insertSelective(litemallMsg);
    }


    public long countByMessageId(Integer mid, Integer uid) {
        LitemallNoticeExample example = new LitemallNoticeExample();
        example.or().andMessageIdEqualTo(mid).andUserIdEqualTo(uid);
        return litemallNoticeMapper.countByExample(example);
    }

    public int updateMarkByUserId(LitemallNotice msg) {
        LitemallNoticeExample example = new LitemallNoticeExample();
        example.or().andUserIdEqualTo(msg.getUserId());
        LitemallNoticeWithBLOBs record = new LitemallNoticeWithBLOBs();
        record.setMarkRead(true);
        return litemallNoticeMapper.updateByExampleSelective(record, example);
    }

    public int deleteByUserId(Integer userId) {
        LitemallNoticeExample messageExample = new LitemallNoticeExample();
        messageExample.or().andUserIdEqualTo(userId);

        LitemallNoticeWithBLOBs litemallMsg = new LitemallNoticeWithBLOBs();
        litemallMsg.setDeleted(true);
        return litemallNoticeMapper.updateByExampleSelective(litemallMsg, messageExample);
    }
}
