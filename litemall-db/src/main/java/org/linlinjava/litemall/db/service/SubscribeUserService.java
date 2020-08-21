package org.linlinjava.litemall.db.service;

import com.github.pagehelper.PageHelper;
import org.linlinjava.litemall.db.dao.SubscribeUserMapper;
import org.linlinjava.litemall.db.domain.SubscribeUserVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ：stephen
 * @date ：Created in 2020/8/20 14:23
 * @description：TODO
 */
@Service
public class SubscribeUserService {
    @Resource
    private SubscribeUserMapper subscribeUserMapper;

    public List<SubscribeUserVo> querySelective(String name, Integer goodsId, Integer shopId, Integer page, Integer size, String sort, String order){
        PageHelper.startPage(page, size);
        return subscribeUserMapper.querySelective(name, goodsId, shopId, page, size, sort, order);
    }
}
