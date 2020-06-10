package org.linlinjava.litemall.db.service;

import com.github.pagehelper.PageHelper;
import org.linlinjava.litemall.db.dao.LitemallCollectMapper;
import org.linlinjava.litemall.db.domain.LitemallCollect;
import org.linlinjava.litemall.db.domain.LitemallCollectExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class LitemallCollectService {
    @Resource
    private LitemallCollectMapper collectMapper;

    public int count(int uid, Integer gid) {
        LitemallCollectExample example = new LitemallCollectExample();
        example.or().andUserIdEqualTo(uid).andGoodsIdEqualTo(gid).andDeletedEqualTo(false);
        return (int) collectMapper.countByExample(example);
    }

    public int updateById(LitemallCollect collect){
        return collectMapper.updateByPrimaryKeySelective(collect);
    }

    public LitemallCollect queryByGoodsId(Integer userId, Integer goodsId) {
        LitemallCollectExample example = new LitemallCollectExample();
        example.or().andUserIdEqualTo(userId).andGoodsIdEqualTo(goodsId).andDeletedEqualTo(false);
        return collectMapper.selectOneByExample(example);
    }

    public void deleteById(Integer id) {
        collectMapper.logicalDeleteByPrimaryKey(id);
    }

    public int add(LitemallCollect collect) {
        return collectMapper.insertSelective(collect);
    }

    public List<LitemallCollect> querySelective(String userId, String valueId, Integer page, Integer size, String sort, String order) {
        LitemallCollectExample example = new LitemallCollectExample();
        LitemallCollectExample.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(userId)) {
            criteria.andUserIdEqualTo(Integer.valueOf(userId));
        }
        if (!StringUtils.isEmpty(valueId)) {
            criteria.andGoodsIdEqualTo(Integer.valueOf(valueId));
        }
        criteria.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, size);
        return collectMapper.selectByExample(example);
    }

    public int deleteByGoodsId(Integer userId, Integer goodsId) {
        LitemallCollectExample example = new LitemallCollectExample();
        example.or().andUserIdEqualTo(userId).andGoodsIdEqualTo(goodsId).andDeletedEqualTo(false);
        return collectMapper.logicalDeleteByExample(example);
    }
}
