package org.linlinjava.litemall.admin.service;

import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.domain.LitemallShopLog;
import org.linlinjava.litemall.db.service.LitemallShopLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * 门店服务
 */
@Service
public class ShopLogService {
    @Autowired
    private LitemallShopLogService litemallShopLogService;

    public Object list(Integer shopId,String content, Integer page, Integer limit, String sort, String order){

        List<LitemallShopLog> shops = litemallShopLogService.querySelective(shopId, content, page, limit, sort, order);
        return ResponseUtil.okList(shops);
    }

}
