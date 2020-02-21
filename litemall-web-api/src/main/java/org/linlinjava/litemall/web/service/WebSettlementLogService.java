package org.linlinjava.litemall.web.service;

import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.domain.LitemallSettlementLog;
import org.linlinjava.litemall.db.domain.LitemallUser;
import org.linlinjava.litemall.db.service.LitemallSettlementLogService;
import org.linlinjava.litemall.db.service.LitemallUserService;
import org.linlinjava.litemall.web.util.BeanConvertUtil;
import org.linlinjava.litemall.web.vo.CalculationOrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ：stephen
 * @date ：Created in 2020/2/21 10:42
 * @description：TODO
 */
@Service
public class WebSettlementLogService {

    @Autowired
    private LitemallUserService userService;
    @Autowired
    private LitemallSettlementLogService settlementLogService;

    /**
     * 添加ipad用户结算记录
     * @param userId
     * @param calculationOrderVos
     * @return
     */
    public Object add(Integer userId, List<CalculationOrderVo> calculationOrderVos){
        LitemallUser user = userService.findById(userId);
        List<LitemallSettlementLog> collect = calculationOrderVos.stream().map(vo -> {
            return BeanConvertUtil.toSettlementLot(user, vo);
        }).collect(Collectors.toList());
        collect.forEach(litemallSettlementLog -> {
            settlementLogService.add(litemallSettlementLog);
        });
        return ResponseUtil.ok();
    }
}
