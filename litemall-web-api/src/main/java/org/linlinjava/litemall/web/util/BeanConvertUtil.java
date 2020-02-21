package org.linlinjava.litemall.web.util;

import org.linlinjava.litemall.db.domain.LitemallSettlementLog;
import org.linlinjava.litemall.db.domain.LitemallUser;
import org.linlinjava.litemall.web.vo.CalculationOrderVo;

import java.time.LocalDateTime;

/**
 * @author ：stephen
 * @date ：Created in 2020/2/21 10:47
 * @description：TODO
 */
public class BeanConvertUtil {
    public static LitemallSettlementLog toSettlementLot(LitemallUser user, CalculationOrderVo vo){
        LitemallSettlementLog log = new LitemallSettlementLog();
        log.setAmount(vo.getAmount());
        log.setNumber(vo.getNumber().intValue());
        log.setUserId(user.getId());
        log.setUserName(user.getUsername());
        return log;
    }
}
