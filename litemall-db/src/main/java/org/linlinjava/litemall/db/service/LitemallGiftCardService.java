package org.linlinjava.litemall.db.service;

import com.github.pagehelper.PageHelper;
import org.linlinjava.litemall.db.dao.LitemallGiftCardMapper;
import org.linlinjava.litemall.db.domain.LitemallGiftCard;
import org.linlinjava.litemall.db.domain.LitemallGiftCardExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author ：stephen
 * @date ：Created in 2020/2/24 14:51
 * @description：礼物卡
 */
@Service
public class LitemallGiftCardService {
    @Resource
    private LitemallGiftCardMapper litemallGiftCardMapper;

    public List<LitemallGiftCard> querySelective(String name, Byte type, Integer page, Integer size, String sort, String order) {
        LitemallGiftCardExample example = new LitemallGiftCardExample();
        LitemallGiftCardExample.Criteria criteria = example.createCriteria();
        if(!StringUtils.isEmpty(name)){
            criteria.andNameLike("%" + name + "%");
        }
        if(type != null){
            criteria.andTypeEqualTo(type);
        }
        criteria.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, size);
        return litemallGiftCardMapper.selectByExample(example);
    }

    public LitemallGiftCard findById(Integer id) {
        return litemallGiftCardMapper.selectByPrimaryKey(id);
    }

    public void deleteById(Integer id) {
        litemallGiftCardMapper.logicalDeleteByPrimaryKey(id);
    }

    public void add(LitemallGiftCard giftCard) {
        giftCard.setAddTime(LocalDateTime.now());
        giftCard.setUpdateTime(LocalDateTime.now());
        litemallGiftCardMapper.insertSelective(giftCard);
    }

    public void update(LitemallGiftCard giftCard) {
        giftCard.setUpdateTime(LocalDateTime.now());
        litemallGiftCardMapper.updateByPrimaryKeySelective(giftCard);
    }
}
