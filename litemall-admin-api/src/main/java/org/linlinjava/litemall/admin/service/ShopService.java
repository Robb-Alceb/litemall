package org.linlinjava.litemall.admin.service;

import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import org.apache.shiro.SecurityUtils;
import org.linlinjava.litemall.admin.beans.Constants;
import org.linlinjava.litemall.admin.beans.dto.ShopDto;
import org.linlinjava.litemall.admin.beans.enums.AdminOrderStatusEnum;
import org.linlinjava.litemall.admin.beans.enums.OrderStatusEnum;
import org.linlinjava.litemall.admin.beans.pojo.convert.BeanConvert;
import org.linlinjava.litemall.admin.util.DateUtil;
import org.linlinjava.litemall.admin.beans.vo.ShopVo;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.domain.*;
import org.linlinjava.litemall.db.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.linlinjava.litemall.admin.util.AdminResponseCode.SHOP_NOT_ALLOW_DELETE;

/**
 * 门店服务
 */
@Service
public class ShopService {
    @Autowired
    private LitemallShopService litemallShopService;
    @Autowired
    private LitemallAdminService litemallAdminService;
    @Autowired
    private LitemallShopLogService litemallShopLogService;
    @Autowired
    private LitemallOrderService litemallOrderService;
    @Autowired
    private LitemallGoodsService litemallGoodsService;
    @Autowired
    private LitemallAdminOrderService litemallAdminOrderService;

    public Object list(Integer shopId, String name, String address, Integer status, String addTimeFrom, String addTimeTo, Integer page, Integer limit, String sort, String order){
        Short sp = null;
        if(null != status){
            sp = status.shortValue();
        }
        List<LitemallShop> shops = litemallShopService.querySelective(shopId, name, address, sp,
                DateUtil.stringToDate(addTimeFrom), DateUtil.stringToDate(addTimeTo),
                page, limit, sort, order);

        List<ShopVo> collect = shops.stream().map(s -> {
            List<LitemallAdmin> admins = litemallAdminService.findByShopId(s.getId());
            return BeanConvert.toShopVo(s, admins);
        }).collect(Collectors.toList());
        return ResponseUtil.okList(collect);
    }

    public Object detail(Integer shopId){
        //门店信息
        LitemallShop litemallShop = litemallShopService.findById(shopId);
        //查询门店人员信息
        List<LitemallAdmin> byShopId = litemallAdminService.findByShopId(litemallShop.getId());
        return ResponseUtil.ok(BeanConvert.toShopVo(litemallShop, byShopId));
    }

    public Object delete(Integer shopId){
        if(litemallGoodsService.count(shopId) > 0){
            return ResponseUtil.fail(SHOP_NOT_ALLOW_DELETE, "门店有商品不允许删除");
        }
        litemallShopService.deleteById(shopId);
        //保存日志
        LitemallShop shop = litemallShopService.findById(shopId);
        saveShopLog(Constants.DELETE_MANAGER+shop.getName(), shop);
        return ResponseUtil.ok();
    }

    @Transactional(rollbackFor = Exception.class)
    public Object create(ShopDto shop){
        litemallShopService.add(shop.getLitemallShop());
        //保存日志
        saveShopLog(Constants.CREATE_SHOP+shop.getLitemallShop().getName(), shop.getLitemallShop());

        /**
         * 添加当前用户为店长
         */
        if(null != shop.getShopkeeperId()){
            LitemallAdmin admin = litemallAdminService.findById(shop.getShopkeeperId());
            if(null != admin){
                setShopRole(admin, shop.getShopkeeperId(), shop.getLitemallShop().getId());
            }
        }
        /**
         * 添加当前用户为门店经理
         */
        if(null != shop.getShopManagerId()){
            LitemallAdmin admin = litemallAdminService.findById(shop.getShopManagerId());
            if(null != admin){
                setShopRole(admin, shop.getShopManagerId(), shop.getLitemallShop().getId());
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
    public Object update(ShopDto shop) {
        litemallShopService.updateById(shop.getLitemallShop());
        //保存日志
        saveShopLog(Constants.UPDATE_SHOP+shop.getLitemallShop().getName(), shop.getLitemallShop());
        if (null != shop.getShopkeeperId() || null != shop.getShopManagerId()) {
            List<LitemallAdmin> admins = litemallAdminService.findByShopId(shop.getLitemallShop().getId());
            //判断店长是否修改
            Boolean updateShopkeeper = admins.stream().anyMatch(admin ->
                 shop.getShopkeeperId() == admin.getId() &&
                    Arrays.asList(admin.getRoleIds()).contains(Constants.SHOPKEEPER_ROLE_ID)
            );
            //判断门店经理是否修改
            Boolean updateShopManager = admins.stream().anyMatch(admin ->
                    shop.getShopManagerId() == admin.getId()&&
                            Arrays.asList(admin.getRoleIds()).contains(Constants.SHOP_MANAGER_ROLE_ID)
            );
            if(!updateShopkeeper && null != shop.getShopkeeperId()){
                admins.forEach(admin -> {
                    /**
                     * 修改旧的门店店长为普通门店用户
                     */
                    updateShopRole(admin, Constants.SHOPKEEPER_ROLE_ID, shop.getLitemallShop());
                    /**
                     * 添加当前用户为店长
                     */
                    if(shop.getShopkeeperId() == admin.getId()){
                        setShopRole(admin, shop.getShopkeeperId(), shop.getLitemallShop().getId());
                        //保存日志
                        saveShopLog(Constants.ADD_SHOPKEEPER+admin.getUsername(), shop.getLitemallShop());
                    }
                });
            }
            if(!updateShopManager && null != shop.getShopManagerId()){
                admins.forEach(admin -> {
                    /**
                     * 修改旧的门店经理为普通门店用户
                     */
                    updateShopRole(admin, Constants.SHOP_MANAGER_ROLE_ID, shop.getLitemallShop());
                    /**
                     * 添加当前用户为门店经理
                     */
                    if(shop.getShopManagerId() == admin.getId()){
                        setShopRole(admin, shop.getShopManagerId(), shop.getLitemallShop().getId());
                        //保存日志
                        saveShopLog(Constants.ADD_MANAGER+admin.getUsername(), shop.getLitemallShop());
                    }
                });
            }

        }

        return ResponseUtil.ok();
    }


    /**
     * 查询门店商品信息 销售情况 订单情况  商品情况
     * @param shopId
     * @return
     */
    public Object querShopGoodsInfo(Integer shopId){

        Map<String, Object> map = Maps.newHashMap();
        //今日销售
        LocalDateTime todayStart = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        LocalDateTime todayEnd = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);//当天零点
        map.put("todayAmount", getSalesAmount(shopId, todayStart, todayEnd));
        //最近7天内销售
        LocalDateTime sevenStart = LocalDateTime.of(LocalDate.now().minusDays(7), LocalTime.MIN);
        LocalDateTime sevenEnd = LocalDateTime.of(LocalDate.now().minusDays(1), LocalTime.MAX);
        System.out.println(sevenStart+" "+sevenEnd);
        map.put("sevenAmount", getSalesAmount(shopId, sevenStart, sevenEnd));
        //历史总销售额
        map.put("allAmount", getSalesAmount(shopId, null, null));
        //今日订单数量
        List<LitemallOrder> todayOrders = litemallOrderService.querShopGoodsSalesInfo(shopId, todayStart, todayEnd);
        map.put("todayCount", !CollectionUtils.isEmpty(todayOrders)?todayOrders.size():0);
        //正在进行的订单
        List<LitemallOrder> sevenOrder = litemallOrderService.querNotCompletedOrder(shopId, todayStart, todayEnd);
        map.put("sevenCount", !CollectionUtils.isEmpty(sevenOrder)?sevenOrder.size():0);
        //历史总订单
        List<LitemallOrder> allOrder = litemallOrderService.querShopGoodsSalesInfo(shopId, null, null);
        map.put("allCount", !CollectionUtils.isEmpty(allOrder)?allOrder.size():0);
        //总上架商品
        List<LitemallGoods> litemallGoods = litemallGoodsService.queryPutOnSale(shopId);
        map.put("putOnSaleGoods", !CollectionUtils.isEmpty(litemallGoods)?litemallGoods.size():0);
        //待处理进货请求
        List<Byte> statusList = new ArrayList<>(Arrays.asList(new Byte[]{AdminOrderStatusEnum.P_1.getCode().byteValue(), AdminOrderStatusEnum.P_2.getCode().byteValue(), AdminOrderStatusEnum.P_3.getCode().byteValue(), AdminOrderStatusEnum.P_4.getCode().byteValue()}));
        Long processingCount = litemallAdminOrderService.countProcessingByShopId(shopId, statusList);
        map.put("processingCount", processingCount);
        //该门店的总进货金额
        List<LitemallAdminOrder> litemallAdminOrders = litemallAdminOrderService.merchandiseTotalAmount(shopId, AdminOrderStatusEnum.P_5.getCode().byteValue());
        BigDecimal totalAmount = new BigDecimal(0.0);
        for(LitemallAdminOrder order : litemallAdminOrders){
            totalAmount = totalAmount.add(order.getActualPrice());
        }
        map.put("merchandiseTotalAmount", totalAmount);

        return map;
    }

    private Double getSalesAmount(Integer shopId, LocalDateTime todayStart, LocalDateTime todayEnd) {
        List<LitemallOrder> litemallOrders = litemallOrderService.querShopGoodsSalesInfo(shopId, todayStart, todayEnd);
        if(!CollectionUtils.isEmpty(litemallOrders)){
            return litemallOrders.stream().mapToDouble(orders->Double.valueOf(orders.getActualPrice().toString())).sum();
        }
        return 0d;
    }

    /**
     * 修改用户为普通门店用户
     * @param admin
     * @param role
     * @return
     */
    public void updateShopRole(LitemallAdmin admin, Integer role, LitemallShop shop) {
        if (Arrays.asList(admin.getRoleIds()).contains(role)) {
            LitemallAdmin update = admin;
            update.setRoleIds(Iterables.toArray(Arrays.stream(admin.getRoleIds()).filter(roleId ->
                    role != roleId).collect(Collectors.toList()), Integer.class));

            List<Integer> roleIds = new ArrayList(Arrays.asList(update.getRoleIds()));
            roleIds.add(Constants.SHOP_ASSISTANT_ROLE_ID);
            update.setRoleIds(Iterables.toArray(roleIds, Integer.class));
            litemallAdminService.updateById(update);

            //保存日志
            saveShopLog(Constants.UPDATE_PERMISSION+admin.getUsername(), shop);
        }
    }

    private void saveShopLog(String context, LitemallShop shop) {
        LitemallAdmin litemallAdmin = (LitemallAdmin) SecurityUtils.getSubject().getPrincipal();
        LitemallShopLog litemallShopLog = new LitemallShopLog();
        litemallShopLog.setCreateUserId(litemallAdmin.getId());
        litemallShopLog.setCreateUserName(litemallAdmin.getUsername());
        litemallShopLog.setIpAddr(litemallAdmin.getLastLoginIp());
        litemallShopLog.setContent(context);
        litemallShopLog.setShopId(shop.getId());
        litemallShopLog.setShopName(shop.getName());
        litemallShopLogService.add(litemallShopLog);
    }

    public void setShopRole(LitemallAdmin admin, Integer role, Integer shopId) {
        List<Integer> roleIds = new ArrayList<>(Arrays.asList(admin.getRoleIds()));
        roleIds.add(role);
        roleIds = roleIds.stream().distinct().collect(Collectors.toList());
        admin.setRoleIds(Iterables.toArray(roleIds, Integer.class));
        admin.setShopId(shopId);
        litemallAdminService.updateById(admin);
    }

    public List<LitemallShop> all(Integer id) {
        if(null != id){
            ArrayList<LitemallShop> rtn = new ArrayList<>();
            rtn.add(litemallShopService.findById(id));
            return rtn;
        }
        return litemallShopService.all();
    }
}
