package org.linlinjava.litemall.db.dao;

import org.apache.ibatis.annotations.Param;
import org.linlinjava.litemall.db.domain.LitemallOrder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface OrderMapper {
    int updateWithOptimisticLocker(@Param("lastUpdateTime") LocalDateTime lastUpdateTime, @Param("order") LitemallOrder order);
    List<Map<String, Object>> queryGoodsSales(@Param("map") Map<String, Object> map);
    List<Map<String, Object>> queryGoodsCategorySales(@Param("map") Map<String, Object> map);
    Long queryOrderGoodsNum(@Param("type") String type);
}