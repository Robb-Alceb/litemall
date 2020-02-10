package org.linlinjava.litemall.admin.service;

import com.google.common.collect.Maps;
import org.linlinjava.litemall.admin.beans.vo.UserOptionVo;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.domain.LitemallUser;
import org.linlinjava.litemall.db.service.LitemallUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private LitemallUserService litemallUserService;

    public Object queryUserStatistics(String startTime, String endTime) {
//        DateTimeFormatter timeDtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        LocalDateTime startTimes = LocalDateTime.parse(startTime, timeDtf);
//        LocalDateTime endTimes = LocalDateTime.parse(endTime, timeDtf);

        //查询用户信息
        long  userTotal = litemallUserService.count();
        Map<String, Object> map = Maps.newHashMap();
        //今日新增
        LocalDateTime todayStart = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        LocalDateTime todayEnd = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);//当天零点
        map.put("userTodayCount",litemallUserService.queryUserByTime(todayStart, todayEnd).size());
        //昨日新增
        LocalDateTime yesterdayStart = LocalDateTime.of(LocalDate.now().minusDays(1), LocalTime.MIN);
        LocalDateTime yesterdayEnd = LocalDateTime.of(LocalDate.now().minusDays(1), LocalTime.MAX);//当天零点
        map.put("userYesterdayCount", litemallUserService.queryUserByTime(yesterdayStart, yesterdayEnd).size());
        //本月新增
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDateTime firstday = LocalDateTime.of(localDateTime.with(TemporalAdjusters.firstDayOfMonth()).toLocalDate(), LocalTime.MIN);
        LocalDateTime lastDay = LocalDateTime.of(localDateTime.with(TemporalAdjusters.lastDayOfMonth()).toLocalDate(), LocalTime.MAX);
        map.put("userMonthCount", litemallUserService.queryUserByTime(firstday, lastDay).size());
        //会员总数
        map.put("userTotal", userTotal);

        return ResponseUtil.ok(map);
    }

    public Object queryAddUserStatistics(String type, String startTime, String endTime) {
        DateTimeFormatter timeDtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        Map<String, Object> map = Maps.newHashMap();
        map.put("type", type);
        map.put("startTime", LocalDateTime.parse(startTime, timeDtf));
        map.put("endTime", LocalDateTime.parse(endTime, timeDtf));

        return ResponseUtil.ok(litemallUserService.queryAddUserStatistics(map));
    }

    public Object queryAll() {
        List<LitemallUser> litemallUsers = litemallUserService.queryAll();
        List<UserOptionVo> collect = litemallUsers.stream().map(user -> {
            UserOptionVo vo = new UserOptionVo();
            vo.setKey(user.getId());
            vo.setLabel(user.getUsername());
            return vo;
        }).collect(Collectors.toList());
        return collect;
    }
}
