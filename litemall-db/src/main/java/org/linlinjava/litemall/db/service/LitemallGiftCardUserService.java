package org.linlinjava.litemall.db.service;

import com.github.pagehelper.PageHelper;
import org.linlinjava.litemall.db.dao.LitemallGiftCardUserMapper;
import org.linlinjava.litemall.db.domain.LitemallGiftCardUser;
import org.linlinjava.litemall.db.domain.LitemallGiftCardUserExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author ：stephen
 * @date ：Created in 2020/2/24 15:01
 * @description：TODO
 */
@Service
public class LitemallGiftCardUserService {
    @Resource
    private LitemallGiftCardUserMapper litemallGiftCardUserMapper;

    public List<LitemallGiftCardUser> queryByUserId(Integer userId){
        LitemallGiftCardUserExample example = new LitemallGiftCardUserExample();
        example.or().andUserIdEqualTo(userId).andDeletedEqualTo(false);
        return litemallGiftCardUserMapper.selectByExample(example);
    }

    public LitemallGiftCardUser findById(Integer id) {
        return litemallGiftCardUserMapper.selectByPrimaryKey(id);
    }

    public void deleteById(Integer id) {
        litemallGiftCardUserMapper.logicalDeleteByPrimaryKey(id);
    }

    public void add(LitemallGiftCardUser giftCard) {
        giftCard.setAddTime(LocalDateTime.now());
        giftCard.setUpdateTime(LocalDateTime.now());
        litemallGiftCardUserMapper.insertSelective(giftCard);
    }
    public List<LitemallGiftCardUser> querySelective(Integer giftCardId, String userName, Integer page, Integer size, String sort, String order) {
        LitemallGiftCardUserExample example = new LitemallGiftCardUserExample();
        LitemallGiftCardUserExample.Criteria criteria = example.createCriteria();
        if(!StringUtils.isEmpty(userName)){
            criteria.andUserNameLike("%" + userName + "%");
        }
        if(giftCardId != null){
            criteria.andGiftCardIdEqualTo(giftCardId);
        }
        criteria.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, size);
        return litemallGiftCardUserMapper.selectByExample(example);
    }
}
