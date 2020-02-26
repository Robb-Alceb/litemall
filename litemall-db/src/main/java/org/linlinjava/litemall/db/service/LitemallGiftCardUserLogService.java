package org.linlinjava.litemall.db.service;

import com.github.pagehelper.PageHelper;
import org.linlinjava.litemall.db.dao.LitemallGiftCardUserLogMapper;
import org.linlinjava.litemall.db.dao.LitemallGiftCardUserMapper;
import org.linlinjava.litemall.db.domain.LitemallGiftCardUser;
import org.linlinjava.litemall.db.domain.LitemallGiftCardUserExample;
import org.linlinjava.litemall.db.domain.LitemallGiftCardUserLog;
import org.linlinjava.litemall.db.domain.LitemallGiftCardUserLogExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author ：stephen
 * @date ：Created in 2020/2/24 15:23
 * @description：TODO
 */
@Service
public class LitemallGiftCardUserLogService {
    @Resource
    private LitemallGiftCardUserLogMapper litemallGiftCardUserLogMapper;

    public List<LitemallGiftCardUserLog> querySelective(String userName, Byte type, Integer page, Integer size, String sort, String order){
        LitemallGiftCardUserLogExample example = new LitemallGiftCardUserLogExample();
        LitemallGiftCardUserLogExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(userName)){
            criteria.andAddUserNameLike("%" + userName + "%");
        }
        if(type != null){
            criteria.andTypeEqualTo(type);
        }
        criteria.andDeletedEqualTo(false);
        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }
        PageHelper.startPage(page, size);
        return litemallGiftCardUserLogMapper.selectByExample(example);
    }

    public List<LitemallGiftCardUserLog> queryByUserId(Byte type){
        LitemallGiftCardUserLogExample example = new LitemallGiftCardUserLogExample();
        example.or().andTypeEqualTo(type).andDeletedEqualTo(false);
        return litemallGiftCardUserLogMapper.selectByExample(example);
    }

    public LitemallGiftCardUserLog findById(Integer id) {
        return litemallGiftCardUserLogMapper.selectByPrimaryKey(id);
    }

    public void deleteById(Integer id) {
        litemallGiftCardUserLogMapper.logicalDeleteByPrimaryKey(id);
    }

    public void add(LitemallGiftCardUserLog giftCard) {
        giftCard.setAddTime(LocalDateTime.now());
        giftCard.setUpdateTime(LocalDateTime.now());
        litemallGiftCardUserLogMapper.insertSelective(giftCard);
    }
}
