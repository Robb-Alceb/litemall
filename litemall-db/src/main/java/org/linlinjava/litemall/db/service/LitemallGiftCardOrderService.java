package org.linlinjava.litemall.db.service;

import com.github.pagehelper.PageHelper;
import org.linlinjava.litemall.db.beans.Constants;
import org.linlinjava.litemall.db.dao.LitemallGiftCardMapper;
import org.linlinjava.litemall.db.dao.LitemallGiftCardOrderMapper;
import org.linlinjava.litemall.db.domain.LitemallGiftCard;
import org.linlinjava.litemall.db.domain.LitemallGiftCardExample;
import org.linlinjava.litemall.db.domain.LitemallGiftCardOrder;
import org.linlinjava.litemall.db.domain.LitemallGiftCardOrderExample;
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
public class LitemallGiftCardOrderService {
    @Resource
    private LitemallGiftCardOrderMapper litemallGiftCardOrderMapper;

    public void deleteById(Integer id) {
        litemallGiftCardOrderMapper.logicalDeleteByPrimaryKey(id);
    }

    public void add(LitemallGiftCardOrder giftCard) {
        giftCard.setUpdateTime(LocalDateTime.now());
        litemallGiftCardOrderMapper.insertSelective(giftCard);
    }

    public void update(LitemallGiftCardOrder giftCard) {
        giftCard.setUpdateTime(LocalDateTime.now());
        litemallGiftCardOrderMapper.updateByPrimaryKeySelective(giftCard);
    }

    public LitemallGiftCardOrder findById(Integer id){
        return litemallGiftCardOrderMapper.selectByPrimaryKey(id);
    }

    public int paddingPay(LitemallGiftCardOrder update, LitemallGiftCardOrder order) {
        LitemallGiftCardOrderExample example = new LitemallGiftCardOrderExample();
        example.or().andIdEqualTo(order.getId()).andUpdateTimeEqualTo(order.getUpdateTime());
        update.setUpdateTime(LocalDateTime.now());
        return litemallGiftCardOrderMapper.updateByExampleSelective(update,example);
    }

    public LitemallGiftCardOrder findByOutTradeNo(String outTradeNo) {
        LitemallGiftCardOrderExample example = new LitemallGiftCardOrderExample();
        example.or().andOutTradeNoEqualTo(outTradeNo).andDeletedEqualTo(false);
        return litemallGiftCardOrderMapper.selectOneByExample(example);
    }

    public int payDone(LitemallGiftCardOrder order,LitemallGiftCardOrder update) {
        LitemallGiftCardOrderExample example = new LitemallGiftCardOrderExample();
        example.or().andIdEqualTo(order.getId()).andUpdateTimeEqualTo(order.getUpdateTime());
        update.setUpdateTime(LocalDateTime.now());
        return litemallGiftCardOrderMapper.updateByExampleSelective(update,example);
    }

    public List<LitemallGiftCardOrder> getUnpaidOrder(Integer minutes) {
        LitemallGiftCardOrderExample example = new LitemallGiftCardOrderExample();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expired = now.minusMinutes(minutes);
        example.or().andPayStatusEqualTo(Constants.PAY_STATUS_AUTO_CANCEL).andUpdateTimeLessThan(expired).andDeletedEqualTo(false);
        return litemallGiftCardOrderMapper.selectByExample(example);
    }
}
