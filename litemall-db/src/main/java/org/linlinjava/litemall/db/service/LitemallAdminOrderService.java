package org.linlinjava.litemall.db.service;

import com.github.pagehelper.PageHelper;
import org.linlinjava.litemall.db.dao.LitemallAdminOrderMapper;
import org.linlinjava.litemall.db.domain.LitemallAdminOrder;
import org.linlinjava.litemall.db.domain.LitemallAdminOrderExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class LitemallAdminOrderService {
    @Resource
    private LitemallAdminOrderMapper adminOrderMapper;




    public List<LitemallAdminOrder> querySelective(LocalDateTime startTimes, LocalDateTime endTimes, Integer orderStatus, String orderSn, String userName, String address, Integer shopId, Integer page, Integer limit, String sort, String order) {
        LitemallAdminOrderExample example = new LitemallAdminOrderExample();
        LitemallAdminOrderExample.Criteria criteria = example.createCriteria();

        if(null != startTimes && null != endTimes){
            criteria.andUpdateTimeBetween(startTimes, endTimes);
        }
        if(null != orderStatus){
            criteria.andOrderStatusEqualTo(orderStatus.byteValue());
        }
        if (!StringUtils.isEmpty(orderSn)) {
            criteria.andUsernameLike("%" + orderSn + "%");
        }
        if (!StringUtils.isEmpty(userName)) {
            criteria.andUsernameLike("%" + userName + "%");
        }
        if (!StringUtils.isEmpty(address)) {
            criteria.andUsernameLike("%" + address + "%");
        }
        if(null != shopId){
            criteria.andShopIdEqualTo(shopId);
        }
        criteria.andDeletedEqualTo(false);
        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, limit);
        return adminOrderMapper.selectByExampleSelective(example);
    }

    public LitemallAdminOrder queryById(Integer id) {
        return adminOrderMapper.selectByPrimaryKey(id);
    }


    public LitemallAdminOrder queryByIdAndShopId(Integer id, Integer shopId) {
        LitemallAdminOrderExample example = new LitemallAdminOrderExample();
        example.or().andLogicalDeleted(false).andIdEqualTo(id);
        if(null != shopId){
            example.or().andShopIdEqualTo(shopId);
        }
        return adminOrderMapper.selectByPrimaryKey(id);
    }

    public void insert(LitemallAdminOrder adminOrder) {
        adminOrder.setAddTime(LocalDateTime.now());
        adminOrder.setUpdateTime(LocalDateTime.now());
        adminOrderMapper.insertSelective(adminOrder);
    }

    public void update(LitemallAdminOrder adminOrder) {
        adminOrder.setUpdateTime(LocalDateTime.now());
        adminOrderMapper.updateByPrimaryKeySelective(adminOrder);
    }


    /**
     * 统计正在处理的进货请求
     * @param shopId
     * @return
     */
    public long countProcessingByShopId(Integer shopId, List<Byte> status) {
        LitemallAdminOrderExample example = new LitemallAdminOrderExample();
        example.or().andShopIdEqualTo(shopId).andOrderStatusIn(status).andDeletedEqualTo(false);
        return adminOrderMapper.countByExample(example);
    }
    public List<LitemallAdminOrder> merchandiseTotalAmount(Integer shopId, Byte status){
        LitemallAdminOrderExample example = new LitemallAdminOrderExample();
        example.or().andShopIdEqualTo(shopId).andOrderStatusEqualTo(status).andDeletedEqualTo(false);
        return adminOrderMapper.selectByExample(example);
    }
}
