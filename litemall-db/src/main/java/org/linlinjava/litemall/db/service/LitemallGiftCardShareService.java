package org.linlinjava.litemall.db.service;

import org.linlinjava.litemall.db.dao.LitemallGiftCardShareMapper;
import org.linlinjava.litemall.db.domain.LitemallGiftCardShare;
import org.linlinjava.litemall.db.domain.LitemallGiftCardOrderExample;
import org.linlinjava.litemall.db.domain.LitemallGiftCardShareExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ：stephen
 * @date ：Created in 2020/2/24 14:51
 * @description：礼物分享
 */
@Service
public class LitemallGiftCardShareService {
    @Resource
    private LitemallGiftCardShareMapper litemallGiftCardShareMapper;

    public void deleteById(Integer id) {
        litemallGiftCardShareMapper.logicalDeleteByPrimaryKey(id);
    }

    public void add(LitemallGiftCardShare giftCard) {
        litemallGiftCardShareMapper.insertSelective(giftCard);
    }

    public void update(LitemallGiftCardShare giftCard) {
        litemallGiftCardShareMapper.updateByPrimaryKeySelective(giftCard);
    }

    public LitemallGiftCardShare findById(Integer id){
        return litemallGiftCardShareMapper.selectByPrimaryKey(id);
    }

    public LitemallGiftCardShare findByLink(String link) {
        LitemallGiftCardShareExample example = new LitemallGiftCardShareExample();
        example.or().andLinkEqualTo(link).andDeletedEqualTo(false);
        return litemallGiftCardShareMapper.selectOneByExample(example);
    }

    public LitemallGiftCardShare findByCodeAndLink(String link, String code) {
        LitemallGiftCardShareExample example = new LitemallGiftCardShareExample();
        example.or().andLinkEqualTo(link).andCodeEqualTo(code).andDeletedEqualTo(false);
        return litemallGiftCardShareMapper.selectOneByExample(example);
    }

    public List<LitemallGiftCardShare> findByUserId(Integer userId) {
        LitemallGiftCardShareExample example = new LitemallGiftCardShareExample();
        example.or().andUserIdEqualTo(userId);
        return litemallGiftCardShareMapper.selectByExample(example);
    }
}
