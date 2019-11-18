package org.linlinjava.litemall.admin.service;

import org.apache.shiro.SecurityUtils;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.domain.LitemallAdmin;
import org.linlinjava.litemall.db.service.LitemallAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: robb
 * @Date: 2019/11/3 21:57
 * @Version: 1.0
 * @Description: 管理员service
 */

@Service
public class AdminService {
    @Autowired
    private LitemallAdminService litemallAdminService;

    public Object findShopMemberByRole(Integer shopId, Integer role) {
        LitemallAdmin admin = (LitemallAdmin) SecurityUtils.getSubject().getPrincipal();
        if(null != admin.getShopId()){
            shopId = admin.getShopId();
        }
        return ResponseUtil.ok(litemallAdminService.findShopMember(shopId, role));
    }

    public Object findShopMembers(Integer shopId) {
        LitemallAdmin admin = (LitemallAdmin) SecurityUtils.getSubject().getPrincipal();
        if(null != admin.getShopId()){
            shopId = admin.getShopId();
        }
        return ResponseUtil.ok(litemallAdminService.findByShopId(shopId));
    }

    public Object all() {
        return ResponseUtil.ok(litemallAdminService.all());
    }
}
