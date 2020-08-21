package org.linlinjava.litemall.db.dao;

import org.apache.ibatis.annotations.Param;
import org.linlinjava.litemall.db.domain.SubscribeUserVo;

import java.util.List;

/**
 * @author ：stephen
 * @date ：Created in 2020/8/20 14:27
 * @description：TODO
 */
public interface SubscribeUserMapper {
    List<SubscribeUserVo> querySelective(@Param("name") String name, @Param("goodsId") Integer goodsId, @Param("shopId") Integer shopId, @Param("page") Integer page, @Param("size") Integer size, @Param("sort") String sort, @Param("order") String order);
}
