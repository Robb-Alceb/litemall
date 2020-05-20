package org.linlinjava.litemall.admin.service;

import org.linlinjava.litemall.admin.beans.dto.UserDto;
import org.linlinjava.litemall.admin.util.RandomUtils;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.core.util.bcrypt.BCryptPasswordEncoder;
import org.linlinjava.litemall.db.domain.LitemallAddress;
import org.linlinjava.litemall.db.domain.LitemallOrder;
import org.linlinjava.litemall.db.domain.LitemallUser;
import org.linlinjava.litemall.db.service.LitemallAddressService;
import org.linlinjava.litemall.db.service.LitemallOrderService;
import org.linlinjava.litemall.db.service.LitemallUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: stephen
 * @Date: 2020/1/4 9:49
 * @Version: 1.0
 * @Description: TODO
 */

@Service
public class AdminUserInfoService {
    @Autowired
    private LitemallUserService litemallUserService;
    @Autowired
    private LitemallAddressService litemallAddressService;
    @Autowired
    private LitemallOrderService litemallOrderService;

    public Object userInfo(Integer userId){
        Map<String, Object> map = new HashMap<>();
        LitemallUser user = litemallUserService.findById(userId);
        List<LitemallAddress> addressList = litemallAddressService.findByUserId(userId);
        List<LitemallOrder> orders = litemallOrderService.findByUserId(userId);
        map.put("user", user);
        map.put("addressList", addressList);
        map.put("orders", orders);
        return ResponseUtil.ok(map);
    }

    public Object resetPwd(Integer userId, String newPwd) {
        if(StringUtils.isEmpty(newPwd)){
            newPwd = RandomUtils.generateRandomStr(6);
        }
        LitemallUser user = litemallUserService.findById(userId);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(newPwd);
        LitemallUser update = new LitemallUser();
        update.setId(user.getId());
        update.setPassword(encodedPassword);
        litemallUserService.updateById(update);
        return ResponseUtil.ok(newPwd);
    }

    public Object integral(Integer userId, Integer integral) {
        LitemallUser user = litemallUserService.findById(userId);
        LitemallUser update = new LitemallUser();
        update.setId(user.getId());
        update.setPoints(user.getPoints().add(new BigDecimal(integral)));
        litemallUserService.updateById(update);
        return ResponseUtil.ok();
    }

    public Object update(UserDto dto) {
        LitemallUser update = new LitemallUser();
        BeanUtils.copyProperties(dto, update);
        litemallUserService.updateById(update);
        return ResponseUtil.ok();
    }
}
