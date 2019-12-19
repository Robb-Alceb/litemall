package org.linlinjava.litemall.admin.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.linlinjava.litemall.admin.beans.annotation.RequiresPermissionsDesc;
import org.linlinjava.litemall.admin.service.UserService;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.core.validator.Order;
import org.linlinjava.litemall.core.validator.Sort;
import org.linlinjava.litemall.db.domain.LitemallUser;
import org.linlinjava.litemall.db.service.LitemallUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/admin/user")
@Validated
public class AdminUserController {
    private final Log logger = LogFactory.getLog(AdminUserController.class);

    @Autowired
    private LitemallUserService litemallUserService;
    @Resource
    private UserService userService;

    @RequiresPermissions("admin:user:list")
    @RequiresPermissionsDesc(menu = {"用户管理", "会员管理"}, button = "查询")
    @GetMapping("/list")
    public Object list(String username, String mobile,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @Sort @RequestParam(defaultValue = "add_time") String sort,
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
    public Object queryAddUserStatistics(@NotNull String type, @NotNull String startTime, @NotNull String endTime) {
        return ResponseUtil.ok(userService.queryAddUserStatistics(type, startTime, endTime));
    }
}
