package org.linlinjava.litemall.db.service;

import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.PageHelper;
import org.linlinjava.litemall.db.dao.LitemallGoodsProductLogMapper;
import org.linlinjava.litemall.db.domain.LitemallGoodsProductLog;
import org.linlinjava.litemall.db.domain.LitemallGoodsProductLogExample;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import javax.annotation.Resource;
import java.util.List;

@Service
public class LitemallGoodsProductLogService {
    @Resource
    private LitemallGoodsProductLogMapper litemallGoodsProductLogMapper;
    
    public List<LitemallGoodsProductLog> querySelective(String orderSn, String goodsName,Integer shopId,
                                                        Integer page, Integer limit, String sort, String order) {
        LitemallGoodsProductLogExample example = new LitemallGoodsProductLogExample();
        LitemallGoodsProductLogExample.Criteria criteria = example.createCriteria();
        if (!ObjectUtils.isEmpty(shopId)) {
            criteria.andShopIdEqualTo(shopId);
        }
        if (!StringUtils.isEmpty(orderSn)) {
            criteria.andOrderSnLike("%" + orderSn + "%");
        }
        if (!StringUtils.isEmpty(goodsName)) {
            criteria.andGoodsNameLike("%" + goodsName + "%");
        }
        criteria.andDeletedEqualTo(false);
        PageHelper.startPage(page, limit);
        return litemallGoodsProductLogMapper.selectByExampleSelective(example);
    }
}