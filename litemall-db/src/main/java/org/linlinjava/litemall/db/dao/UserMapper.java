package org.linlinjava.litemall.db.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserMapper {
    List<Map<String, Object>> queryUserStatistics(@Param("map") Map<String, Object> map);
}