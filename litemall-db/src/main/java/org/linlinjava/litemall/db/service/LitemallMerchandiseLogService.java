package org.linlinjava.litemall.db.service;

import com.github.pagehelper.PageHelper;
import org.linlinjava.litemall.db.dao.LitemallMerchandiseLogMapper;
import org.linlinjava.litemall.db.domain.LitemallMerchandiseLog;
import org.linlinjava.litemall.db.domain.LitemallMerchandiseLogExample;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author ：pengjiang
 * @date ：Created in 11/23/2019 16:48 AM
 * @description：货品日志服务
 */
@Service
public class LitemallMerchandiseLogService {

    @Resource
    private LitemallMerchandiseLogMapper litemallMerchandiseLogMapper;

    public List<LitemallMerchandiseLog> querySelective(Integer merchandiseId, String merchandiseName, String orderSn, Integer shopId, Integer page,
                                                 Integer limit, String sort, String order) {
        LitemallMerchandiseLogExample example = new LitemallMerchandiseLogExample();
        LitemallMerchandiseLogExample.Criteria criteria = example.createCriteria();
        if(!ObjectUtils.isEmpty(merchandiseId)){
            criteria.andMerchandiseIdEqualTo(merchandiseId);
        }
        if(!ObjectUtils.isEmpty(shopId)){
            criteria.andShopIdEqualTo(shopId);
        }
        if (!StringUtils.isEmpty(merchandiseName)) {
            criteria.andMerchandiseNameLike("%" + merchandiseName + "%");
        }
        if (!StringUtils.isEmpty(orderSn)) {
            criteria.andOrderSnLike("%" + orderSn + "%");
        }
        criteria.andDeletedEqualTo(false);
        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }
        PageHelper.startPage(page, limit);
        return litemallMerchandiseLogMapper.selectByExampleSelective(example);
    }

    public int updateById(LitemallMerchandiseLog goodsLog) {
        return litemallMerchandiseLogMapper.updateByPrimaryKeySelective(goodsLog);
    }

    public void deleteById(Integer id) {
        litemallMerchandiseLogMapper.logicalDeleteByPrimaryKey(id);
    }

    public void add(LitemallMerchandiseLog goodsLog) {
        goodsLog.setAddTime(LocalDateTime.now());
        litemallMerchandiseLogMapper.insertSelective(goodsLog);
    }

    public LitemallMerchandiseLog findById(Integer id) {
        return litemallMerchandiseLogMapper.selectByPrimaryKeySelective(id);
    }

    public List<LitemallMerchandiseLog> all() {
        LitemallMerchandiseLogExample example = new LitemallMerchandiseLogExample();
        example.or().andDeletedEqualTo(false);
        return litemallMerchandiseLogMapper.selectByExample(example);
    }

    public void insert(LitemallMerchandiseLog merchandiseLog) {
        merchandiseLog.setAddTime(LocalDateTime.now());
        litemallMerchandiseLogMapper.insertSelective(merchandiseLog);
    }
}
