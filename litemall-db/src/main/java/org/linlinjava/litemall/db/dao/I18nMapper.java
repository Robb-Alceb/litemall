package org.linlinjava.litemall.db.dao;

import org.apache.ibatis.annotations.Param;
import org.linlinjava.litemall.db.domain.I18nVo;

import java.util.List;

/**
 * @author ：stephen
 * @date ：Created in 2020/5/21 10:47
 * @description：TODO
 */
public interface I18nMapper {
    List<I18nVo> list(@Param("key") String key, @Param("type") String type, @Param("page") Integer page, @Param("size") Integer size, @Param("sort") String sort, @Param("order") String order);
}
