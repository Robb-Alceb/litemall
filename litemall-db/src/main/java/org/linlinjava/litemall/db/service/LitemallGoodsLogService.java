package org.linlinjava.litemall.db.service;

import com.github.pagehelper.PageHelper;
import org.linlinjava.litemall.db.dao.LitemallGoodsLogMapper;
import org.linlinjava.litemall.db.domain.LitemallGoodsLog;
import org.linlinjava.litemall.db.domain.LitemallGoodsLogExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author ：liushichang
 * @date ：Created in 11/15/2019 10:22 AM
 * @description：商品日志服务
 */
@Service
public class LitemallGoodsLogService {

    @Resource
    private LitemallGoodsLogMapper litemallGoodsLogMapper;

    public List<LitemallGoodsLog> querySelective(Integer goodsId, String goodsSn, String userName, String content, Integer page,
                                                 Integer limit, String sort, String order) {
        LitemallGoodsLogExample example = new LitemallGoodsLogExample();
        LitemallGoodsLogExample.Criteria criteria = example.createCriteria();
        if(null != goodsId){
            criteria.andGoodsIdEqualTo(goodsId);
        }
        if (!StringUtils.isEmpty(goodsSn)) {
            criteria.andGoodsSnLike("%" + goodsSn + "%");
        }
        if (!StringUtils.isEmpty(content)) {
            criteria.andContentLike("%" + content + "%");
        }
        if (!StringUtils.isEmpty(userName)) {
            criteria.andUserNameLike("%" + userName + "%");
        }
        criteria.andDeletedEqualTo(false);
        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }
        PageHelper.startPage(page, limit);
        return litemallGoodsLogMapper.selectByExampleSelective(example);
    }

    public int updateById(LitemallGoodsLog goodsLog) {
        return litemallGoodsLogMapper.updateByPrimaryKeySelective(goodsLog);
    }

    public void deleteById(Integer id) {
        litemallGoodsLogMapper.logicalDeleteByPrimaryKey(id);
    }

    public void add(LitemallGoodsLog goodsLog) {
        goodsLog.setAddTime(LocalDateTime.now());
        litemallGoodsLogMapper.insertSelective(goodsLog);
    }

    public LitemallGoodsLog findById(Integer id) {
        return litemallGoodsLogMapper.selectByPrimaryKeySelective(id);
    }

    public List<LitemallGoodsLog> all() {
        LitemallGoodsLogExample example = new LitemallGoodsLogExample();
        example.or().andDeletedEqualTo(false);
        return litemallGoodsLogMapper.selectByExample(example);
    }
}
