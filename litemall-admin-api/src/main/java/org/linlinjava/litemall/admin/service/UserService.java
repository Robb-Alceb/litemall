package org.linlinjava.litemall.admin.service;

import com.google.common.collect.Maps;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.domain.LitemallUser;
import org.linlinjava.litemall.db.service.LitemallUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Map;

@Service
public class UserService {
    @Autowired
    private LitemallUserService litemallUserService;

    public Object queryUserStatistics(String startTime, String endTime) {
        DateTimeFormatter timeDtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startTimes = LocalDateTime.parse(startTime, timeDtf);
        LocalDateTime endTimes = LocalDateTime.parse(endTime, timeDtf);

        //查询用户信息
        List<LitemallUser> litemallUsers = litemallUserService.queryUserByTime(startTimes, endTimes);
        Map<String, Object> map = Maps.newHashMap();
        //今日新增
        LocalDateTime todayStart = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        LocalDateTime todayEnd = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);//当天零点
        map.put("todayCount",litemallUserService.queryUserByTime(todayStart, todayEnd).size());
        //昨日新增
        LocalDateTime yesterdayStart = LocalDateTime.of(LocalDate.now().minusDays(1), LocalTime.MIN);
        LocalDateTime yesterdayEnd = LocalDateTime.of(LocalDate.now().minusDays(1), LocalTime.MAX);//当天零点
        map.put("yesterdayCount", litemallUserService.queryUserByTime(yesterdayStart, yesterdayEnd).size());
        //本月新增
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDateTime firstday = LocalDateTime.of(localDateTime.with(TemporalAdjusters.firstDayOfMonth()).toLocalDate(), LocalTime.MIN);
        LocalDateTime lastDay = LocalDateTime.of(localDateTime.with(TemporalAdjusters.lastDayOfMonth()).toLocalDate(), LocalTime.MAX);
        map.put("monthCount", litemallUserService.queryUserByTime(firstday, lastDay).size());
        //会员总数
        map.put("total", litemallUsers.size());

        return ResponseUtil.ok(map);
    }

    public Object queryAddUserStatistics(String type, String startTime, String endTime) {
        DateTimeFormatter timeDtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Map<String, Object> map = Maps.newHashMap();
        map.put("type", type);
        map.put("startTime", LocalDateTime.parse(startTime, timeDtf));
        map.put("endTime", LocalDateTime.parse(endTime, timeDtf));

        return ResponseUtil.ok(litemallUserService.queryAddUserStatistics(map));
    }
}
