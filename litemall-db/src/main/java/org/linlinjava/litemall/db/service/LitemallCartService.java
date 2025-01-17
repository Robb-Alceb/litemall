package org.linlinjava.litemall.db.service;

import com.github.pagehelper.PageHelper;
import org.linlinjava.litemall.db.dao.LitemallCartMapper;
import org.linlinjava.litemall.db.domain.LitemallCart;
import org.linlinjava.litemall.db.domain.LitemallCartExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class LitemallCartService {
    @Resource
    private LitemallCartMapper cartMapper;

    public LitemallCart queryExist(Integer goodsId, Integer productId, Integer userId, Integer[] specIds) {
        LitemallCartExample example = new LitemallCartExample();
        example.or().andGoodsIdEqualTo(goodsId).andProductIdEqualTo(productId).andUserIdEqualTo(userId).andSpecificationIdsEqualTo(specIds).andDeletedEqualTo(false);
        return cartMapper.selectOneByExample(example);
    }

    public void add(LitemallCart cart) {
        cart.setAddTime(LocalDateTime.now());
        cart.setUpdateTime(LocalDateTime.now());
        cartMapper.insertSelective(cart);
    }

    public int updateById(LitemallCart cart) {
        cart.setUpdateTime(LocalDateTime.now());
        return cartMapper.updateByPrimaryKeySelective(cart);
    }

    public List<LitemallCart> queryByUid(int userId) {
        LitemallCartExample example = new LitemallCartExample();
        example.or().andUserIdEqualTo(userId).andDeletedEqualTo(false);
        return cartMapper.selectByExample(example);
    }


    public List<LitemallCart> queryByUidAndChecked(Integer userId) {
        LitemallCartExample example = new LitemallCartExample();
        example.or().andUserIdEqualTo(userId).andCheckedEqualTo(true).andDeletedEqualTo(false);
        return cartMapper.selectByExample(example);
    }

    public int delete(List<Integer> cartIds, int userId) {
        LitemallCartExample example = new LitemallCartExample();
        example.or().andUserIdEqualTo(userId).andIdIn(cartIds);
        return cartMapper.logicalDeleteByExample(example);
    }


    public int delete(int userId) {
        LitemallCartExample example = new LitemallCartExample();
        example.or().andUserIdEqualTo(userId);
        return cartMapper.logicalDeleteByExample(example);
    }

    public LitemallCart findById(Integer id) {
        return cartMapper.selectByPrimaryKey(id);
    }


    public List<LitemallCart> findByIds(List<Integer> ids) {
        LitemallCartExample example = new LitemallCartExample();
        example.or().andIdIn(ids).andDeletedEqualTo(false);
        return cartMapper.selectByExample(example);
    }

    public int updateCheck(Integer userId, List<Integer> idsList, Boolean checked) {
        LitemallCartExample example = new LitemallCartExample();
        example.or().andUserIdEqualTo(userId).andIdIn(idsList).andDeletedEqualTo(false);
        LitemallCart cart = new LitemallCart();
        cart.setChecked(checked);
        cart.setUpdateTime(LocalDateTime.now());
        return cartMapper.updateByExampleSelective(cart, example);
    }

    public void clearGoods(Integer userId) {
        clearGoods(userId, 0);
    }


    public void clearGoods(Integer userId, Integer id) {
        LitemallCartExample example = new LitemallCartExample();
        LitemallCartExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(userId);
        if(null != id && id > 0){
            criteria.andIdEqualTo(id);
        }
        LitemallCart cart = new LitemallCart();
        cart.setDeleted(true);
        cartMapper.updateByExampleSelective(cart, example);
    }

    public void clearGoods(Integer userId, List<Integer> ids) {
        LitemallCartExample example = new LitemallCartExample();
        LitemallCartExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(userId);
        if(null != ids && ids.size() > 0){
            criteria.andIdIn(ids);
        }
        LitemallCart cart = new LitemallCart();
        cart.setDeleted(true);
        cartMapper.updateByExampleSelective(cart, example);
    }

    public List<LitemallCart> querySelective(Integer userId, Integer goodsId, Integer page, Integer limit, String sort, String order) {
        LitemallCartExample example = new LitemallCartExample();
        LitemallCartExample.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(userId)) {
            criteria.andUserIdEqualTo(userId);
        }
        if (!StringUtils.isEmpty(goodsId)) {
            criteria.andGoodsIdEqualTo(goodsId);
        }
        criteria.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, limit);
        return cartMapper.selectByExample(example);
    }

    public void deleteById(Integer id) {
        cartMapper.logicalDeleteByPrimaryKey(id);
    }

    public boolean checkExist(Integer goodsId) {
        LitemallCartExample example = new LitemallCartExample();
        example.or().andGoodsIdEqualTo(goodsId).andCheckedEqualTo(true).andDeletedEqualTo(false);
        return cartMapper.countByExample(example) != 0;
    }

    public List<LitemallCart> queryByIds(List<Integer> cartIds) {
        LitemallCartExample example = new LitemallCartExample();
        example.or().andIdIn(cartIds);
        return cartMapper.selectByExample(example);
    }
}
