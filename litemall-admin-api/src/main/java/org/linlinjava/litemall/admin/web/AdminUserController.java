package org.linlinjava.litemall.admin.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.linlinjava.litemall.admin.beans.Constants;
import org.linlinjava.litemall.admin.beans.annotation.LogAnno;
import org.linlinjava.litemall.admin.beans.annotation.RequiresPermissionsDesc;
import org.linlinjava.litemall.admin.service.AdminUserInfoService;
import org.linlinjava.litemall.admin.service.UserService;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.core.validator.Order;
import org.linlinjava.litemall.core.validator.Sort;
import org.linlinjava.litemall.db.domain.LitemallRechargeConsumption;
import org.linlinjava.litemall.db.domain.LitemallUser;
import org.linlinjava.litemall.db.service.LitemallCouponService;
import org.linlinjava.litemall.db.service.LitemallRechargeConsumptionService;
import org.linlinjava.litemall.db.service.LitemallUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/user")
@Validated
public class AdminUserController {
    private final Log logger = LogFactory.getLog(AdminUserController.class);

    @Autowired
    private LitemallUserService litemallUserService;
    @Resource
    private UserService userService;
    @Autowired
    private LitemallCouponService litemallCouponService;
    @Autowired
    private AdminUserInfoService adminUserInfoService;
    @Autowired
    private LitemallRechargeConsumptionService litemallRechargeService;

    @RequiresPermissions("admin:user:list")
    @RequiresPermissionsDesc(menu = {"用户管理", "会员管理"}, button = "查询")
    @GetMapping("/list")
    @LogAnno
    public Object list(String username, String mobile,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @Sort(accepts = {"id", "add_time", "username", "user_level", "status"}) @RequestParam(defaultValue = "add_time") String sort,
                       @Order @RequestParam(defaultValue = "desc") String order) {
        List<LitemallUser> userList = litemallUserService.querySelective(username, mobile, page, limit, sort, order);
        return ResponseUtil.okList(userList);
    }

    /**
     * 新增数量统计
     * @param startTime
     * @param endTime
     * @return
     */
    @RequiresPermissions("admin:user:queryUserStatistics")
    @RequiresPermissionsDesc(menu = {"用户管理", "会员统计"}, button = "会员统计")
    @GetMapping("/queryUserStatistics")
    @LogAnno
    public Object queryUserStatistics(String startTime, String endTime) {
        return ResponseUtil.ok(userService.queryUserStatistics(startTime, endTime));
    }

    /**
     * 会员增长统计
     * @param type(1:按天统计 2：按月统计)
     * @param startTime
     * @param endTime
     * @return
     */
    @RequiresPermissions("admin:user:queryAddUserStatistics")
    @RequiresPermissionsDesc(menu = {"用户管理", "会员增长统计"}, button = "会员增长统计")
    @GetMapping("/queryAddUserStatistics")
    @LogAnno
    public Object queryAddUserStatistics(@NotNull @RequestParam(value = "type") String type, @NotNull @RequestParam(value = "startTime") String startTime, @NotNull @RequestParam(value = "endTime") String endTime) {
        return ResponseUtil.ok(userService.queryAddUserStatistics(type, startTime, endTime));
    }

    @GetMapping("/option")
    @LogAnno
    public Object queryAll() {
        return ResponseUtil.ok(userService.queryAll());
    }

    @RequiresPermissions("admin:user:rechargeConsumptionList")
    @RequiresPermissionsDesc(menu = {"用户管理", "账户明细"}, button = "查询")
    @GetMapping("/rechargeConsumptionList")
    @LogAnno
    public Object rechargeConsumptionList(String mobile, String username,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @Sort @RequestParam(defaultValue = "add_time") String sort,
                       @Order @RequestParam(defaultValue = "desc") String order) {
        return ResponseUtil.okList(litemallRechargeService.querySelectiveList(null, username, mobile, page, limit, sort, order));
    }

    /**
     * 用户优惠券数量
     * @param userId
     * @return
     */
    @RequiresPermissions("admin:user:queryUserCoupon")
    @RequiresPermissionsDesc(menu = {"用户管理", "用户优惠券数量"}, button = "查询")
    @GetMapping("/queryUserCoupon")
    @LogAnno
    public Object queryUserCouponCount(@NotNull Integer userId) {
        return ResponseUtil.ok(litemallCouponService.queryUserCouponCount(userId));
    }

    /**
     * 用户信息
     * @param userId
     * @return
     */
    @RequiresPermissions("admin:user:info")
    @RequiresPermissionsDesc(menu = {"用户管理", "用户信息"}, button = "查询")
    @GetMapping("/info")
    @LogAnno
    public Object userInfo(@NotNull Integer userId) {
        return adminUserInfoService.userInfo(userId);
    }

    /**
     * 账号明细统计
     * @param userId
     * @return
     */
    @RequiresPermissions("admin:user:statistics")
    @RequiresPermissionsDesc(menu = {"用户管理", "账号明细统计"}, button = "查询")
    @GetMapping("/statistics")
    @LogAnno
    public Object billStatistics(@NotNull Integer userId){
        Map<String, Object> map = new HashMap<>();
        List<LitemallRechargeConsumption> litemallRechargeConsumptions = litemallRechargeService.queryByUserId(userId);
        BigDecimal totalSaving = new BigDecimal(0.0);
        BigDecimal totalConsume = new BigDecimal(0.0);
        BigDecimal totalBalance = new BigDecimal(0.0);
        for(LitemallRechargeConsumption item : litemallRechargeConsumptions){
            if(item.getType().equals(Constants.USER_SAVING)){
                totalSaving = totalSaving.add(item.getAmount());
            }else if(item.getType().equals(Constants.USER_CONSUME)){
                totalConsume = totalConsume.add(item.getAmount());
            }
        }
        LitemallUser user = litemallUserService.findById(userId);
        totalBalance = totalBalance.add(user.getAvailableAmount());
        map.put("totalSaving",totalSaving);
        map.put("totalConsume",totalConsume);
        map.put("totalBalance",totalBalance);
        return ResponseUtil.ok(map);
    }
}
