package org.linlinjava.litemall.db.service;

import com.github.pagehelper.PageHelper;
import org.linlinjava.litemall.db.dao.LitemallAdminOrderMapper;
import org.linlinjava.litemall.db.domain.LitemallAdminOrder;
import org.linlinjava.litemall.db.domain.LitemallAdminOrderExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import javax.annotation.Resource;
import java.util.List;

@Service
public class LitemallAdminOrderService {
    @Resource
    private LitemallAdminOrderMapper adminOrderMapper;


    public List<LitemallAdminOrder> querySelective(String orderSn, String userName, String address, Integer shopId, Integer page, Integer limit, String sort, String order) {
        LitemallAdminOrderExample example = new LitemallAdminOrderExample();
        LitemallAdminOrderExample.Criteria criteria = example.createCriteria();

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
}
