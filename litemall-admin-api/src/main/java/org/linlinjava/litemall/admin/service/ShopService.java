package org.linlinjava.litemall.admin.service;

import com.google.common.collect.Iterables;
import org.linlinjava.litemall.admin.beans.Constants;
import org.linlinjava.litemall.admin.beans.dto.Shop;
import org.linlinjava.litemall.admin.beans.pojo.convert.BeanConvert;
import org.linlinjava.litemall.admin.util.DateUtil;
import org.linlinjava.litemall.admin.beans.vo.ShopVo;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.domain.LitemallAdmin;
import org.linlinjava.litemall.db.domain.LitemallShop;
import org.linlinjava.litemall.db.service.LitemallAdminService;
import org.linlinjava.litemall.db.service.LitemallShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 门店服务
 */
@Service
public class ShopService {
    @Autowired
    private LitemallShopService litemallShopService;
    @Autowired
    private LitemallAdminService litemallAdminService;

    public Object list(String name, String address, Integer status, String addTimeFrom, String addTimeTo, Integer page, Integer limit, String sort, String order){
        Short sp = null;
        if(null != status){
            sp = status.shortValue();
        }
        List<LitemallShop> shops = litemallShopService.querySelective(name, address, sp,
                DateUtil.stringToDate(addTimeFrom), DateUtil.stringToDate(addTimeTo),
                page, limit, sort, order);

        List<ShopVo> collect = shops.stream().map(s -> {
            List<LitemallAdmin> admins = litemallAdminService.findByShopId(s.getId());
            return BeanConvert.toShopVo(s, admins);
        }).collect(Collectors.toList());
        return ResponseUtil.okList(collect);
    }

    public Object detail(Integer shopId){
        return ResponseUtil.ok(litemallShopService.findById(shopId));
    }

    public Object delete(Integer shopId){
        litemallShopService.deleteById(shopId);
        return ResponseUtil.ok();
    }

    @Transactional(rollbackFor = Exception.class)
    public Object create(Shop shop){
        litemallShopService.add(shop.getLitemallShop());

        /**
         * 添加当前用户为店长
         */
        if(null != shop.getShopkeeperId()){
            LitemallAdmin admin = litemallAdminService.findById(shop.getShopkeeperId());
            if(null != admin){
                setShopRole(admin, shop.getShopkeeperId());
            }
        }
        /**
         * 添加当前用户为门店经理
         */
        if(null != shop.getShopManagerId()){
            LitemallAdmin admin = litemallAdminService.findById(shop.getShopManagerId());
            if(null != admin){
                setShopRole(admin, shop.getShopManagerId());
            }
        }
        return ResponseUtil.ok();
    }

    /**
     * 修改门店信息
     * @param shop
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public Object update(Shop shop) {
        litemallShopService.updateById(shop.getLitemallShop());
        if (null != shop.getShopkeeperId() || null != shop.getShopManagerId()) {
            List<LitemallAdmin> admins = litemallAdminService.findByShopId(shop.getLitemallShop().getId());
            admins.forEach(admin -> {
                /**
                 * 修改旧的门店店长为普通门店用户
                 */
                updateShopRole(admin, Constants.SHOPKEEPER_ROLE_ID);
                /**
                 * 添加当前用户为店长
                 */
                if(shop.getShopkeeperId() == admin.getId()){
                    setShopRole(admin, shop.getShopkeeperId());
                }

                /**
                 * 修改旧的门店经理为普通门店用户
                 */
                updateShopRole(admin, Constants.SHOP_MANAGER_ROLE_ID);
                /**
                 * 添加当前用户为门店经理
                 */
                if(shop.getShopManagerId() == admin.getId()){
                    setShopRole(admin, shop.getShopManagerId());
                }
            });

        }

        return ResponseUtil.ok();
    }

    /**
     * 修改用户为普通门店用户
     * @param admin
     * @param role
     * @return
     */
    public void updateShopRole(LitemallAdmin admin, Integer role) {
        if (Arrays.asList(admin.getRoleIds()).contains(role)) {
            LitemallAdmin update = admin;
            update.setRoleIds(Iterables.toArray(Arrays.stream(admin.getRoleIds()).filter(roleId ->
                    role != roleId).collect(Collectors.toList()), Integer.class));

            List<Integer> roleIds = new ArrayList(Arrays.asList(update.getRoleIds()));
            roleIds.add(Constants.SHOP_ASSISTANT_ROLE_ID);
            litemallAdminService.updateById(update);
        }
    }
    public void setShopRole(LitemallAdmin admin, Integer role) {
        List<Integer> roleIds = Arrays.asList(admin.getRoleIds());
        roleIds.add(role);
        litemallAdminService.updateById(admin);
    }
}
