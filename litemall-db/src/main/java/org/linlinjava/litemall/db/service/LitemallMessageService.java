package org.linlinjava.litemall.db.service;

import com.github.pagehelper.PageHelper;
import org.linlinjava.litemall.db.dao.LitemallMessageMapper;
import org.linlinjava.litemall.db.domain.LitemallMessage;
import org.linlinjava.litemall.db.domain.LitemallMessageExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class LitemallMessageService {
    @Resource
    private LitemallMessageMapper litemallMessageMapper;

    public List<LitemallMessage> querySelective(String title, Byte type,
                                                Integer page, Integer limit, String sort, String order) {
        LitemallMessageExample example = new LitemallMessageExample();
        LitemallMessageExample.Criteria criteria = example.createCriteria();
        if (!StringUtils.isEmpty(title)) {
            criteria.andTitleLike("%" + title + "%");
        }
        if (!StringUtils.isEmpty(type)) {
            criteria.andTypeEqualTo(type);
        }
        criteria.andDeletedEqualTo(false);
        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }
        PageHelper.startPage(page, limit);
        return litemallMessageMapper.selectByExampleSelective(example);
    }

    public void updateById(LitemallMessage litemallMessage) {
        litemallMessageMapper.updateByPrimaryKeySelective(litemallMessage);
    }

    public void deleteById(Integer messageId) {
        LitemallMessageExample messageExample = new LitemallMessageExample();
        messageExample.or().andIdEqualTo(messageId);

        LitemallMessage litemallMessage = new LitemallMessage();
        litemallMessage.setId(messageId);
        litemallMessage.setDeleted(true);
        litemallMessageMapper.updateByExample(litemallMessage, messageExample);
    }

    public int create(LitemallMessage litemallMessage){
        litemallMessage.setAddTime(LocalDateTime.now());
        return litemallMessageMapper.insertSelective(litemallMessage);
    }


}
