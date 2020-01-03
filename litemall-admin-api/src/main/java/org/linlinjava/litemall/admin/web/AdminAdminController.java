package org.linlinjava.litemall.admin.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.linlinjava.litemall.admin.beans.Constants;
import org.linlinjava.litemall.admin.beans.annotation.LogAnno;
import org.linlinjava.litemall.admin.beans.annotation.LoginAdminShopId;
import org.linlinjava.litemall.admin.beans.annotation.RequiresPermissionsDesc;
import org.linlinjava.litemall.admin.service.AdminService;
import org.linlinjava.litemall.admin.service.LogHelper;
import org.linlinjava.litemall.core.util.RegexUtil;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.core.util.bcrypt.BCryptPasswordEncoder;
import org.linlinjava.litemall.core.validator.Order;
import org.linlinjava.litemall.core.validator.Sort;
import org.linlinjava.litemall.db.domain.LitemallAdmin;
import org.linlinjava.litemall.db.domain.LitemallShop;
import org.linlinjava.litemall.db.service.LitemallAdminService;
import org.linlinjava.litemall.db.service.LitemallShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.linlinjava.litemall.admin.util.AdminResponseCode.*;

@RestController
@RequestMapping("/admin/admin")
@Validated
public class AdminAdminController {
    private final Log logger = LogFactory.getLog(AdminAdminController.class);

    @Autowired
    private LitemallAdminService litemallAdminService;
    @Autowired
    private LogHelper logHelper;
    @Autowired
    private AdminService adminService;
    @Autowired
    private LitemallShopService litemallShopService;

    @RequiresPermissions("admin:admin:list")
    @RequiresPermissionsDesc(menu = {"系统管理", "管理员管理"}, button = "查询")
    @GetMapping("/list")
    @LogAnno
    public Object list(String nickname,
                       @LoginAdminShopId Integer shopId,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @Sort @RequestParam(defaultValue = "add_time") String sort,
                       @Order @RequestParam(defaultValue = "desc") String order) {
        List<LitemallAdmin> adminList = litemallAdminService.querySelective(nickname, shopId, page, limit, sort, order);
        return ResponseUtil.okList(adminList);
    }

    private Object validate(LitemallAdmin admin) {
        String name = admin.getUsername();
        if (StringUtils.isEmpty(name)) {
            return ResponseUtil.badArgument();
        }
        if (!RegexUtil.isUsername(name)) {
            return ResponseUtil.fail(ADMIN_INVALID_NAME, "管理员名称不符合规定");
        }
        String password = admin.getPassword();
        if (StringUtils.isEmpty(password) || password.length() < 6) {
            return ResponseUtil.fail(ADMIN_INVALID_PASSWORD, "管理员密码长度不能小于6");
        }
        return null;
    }

    private Object validatePassword(LitemallAdmin admin) {
        String password = admin.getPassword();
        if (!StringUtils.isEmpty(password) && password.length() < 6) {
            return ResponseUtil.fail(ADMIN_INVALID_PASSWORD, "管理员密码长度不能小于6");
        }
        return null;
    }


    @RequiresPermissions("admin:admin:create")
    @RequiresPermissionsDesc(menu = {"系统管理", "管理员管理"}, button = "添加")
    @PostMapping("/create")
    @LogAnno
    public Object create(@RequestBody LitemallAdmin admin) {
        Object error = validate(admin);
        if (error != null) {
            return error;
        }

        String username = admin.getUsername();
        List<LitemallAdmin> adminList = litemallAdminService.findAdmin(username);
        if (adminList.size() > 0) {
            return ResponseUtil.fail(ADMIN_NAME_EXIST, "管理员已经存在");
        }

        String rawPassword = admin.getPassword();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(rawPassword);
        admin.setPassword(encodedPassword);
        litemallAdminService.add(admin);
        logHelper.logAuthSucceed("添加管理员", username);
        return ResponseUtil.ok(admin);
    }

    @RequiresPermissions("admin:admin:read")
    @RequiresPermissionsDesc(menu = {"系统管理", "管理员管理"}, button = "详情")
    @GetMapping("/read")
    @LogAnno
    public Object read(@NotNull @RequestParam(value = "id") Integer id) {
        LitemallAdmin admin = litemallAdminService.findById(id);
        return ResponseUtil.ok(admin);
    }

    @RequiresPermissions("admin:admin:update")
    @RequiresPermissionsDesc(menu = {"系统管理", "管理员管理"}, button = "编辑")
    @PostMapping("/update")
    @LogAnno
    public Object update(@RequestBody LitemallAdmin admin) {
        Object error = validatePassword(admin);
        if (error != null) {
            return error;
        }

        Integer anotherAdminId = admin.getId();
        if (anotherAdminId == null) {
            return ResponseUtil.badArgument();
        }

        // 不允许管理员通过编辑接口用户名
        admin.setUsername(null);

        if(!StringUtils.isEmpty(admin.getPassword())){
            String rawPassword = admin.getPassword();
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String encodedPassword = encoder.encode(rawPassword);
            admin.setPassword(encodedPassword);
        }


        if (litemallAdminService.updateById(admin) == 0) {
            return ResponseUtil.updatedDataFailed();
        }

        logHelper.logAuthSucceed("编辑管理员", admin.getUsername());
        return ResponseUtil.ok(admin);
    }

    @RequiresPermissions("admin:admin:delete")
    @RequiresPermissionsDesc(menu = {"系统管理", "管理员管理"}, button = "删除")
    @PostMapping("/delete")
    @LogAnno
    public Object delete(@RequestBody LitemallAdmin admin) {
        Integer anotherAdminId = admin.getId();
        if (anotherAdminId == null) {
            return ResponseUtil.badArgument();
        }

        // 管理员不能删除自身账号
        Subject currentUser = SecurityUtils.getSubject();
        LitemallAdmin currentAdmin = (LitemallAdmin) currentUser.getPrincipal();
        if (currentAdmin.getId().equals(anotherAdminId)) {
            return ResponseUtil.fail(ADMIN_DELETE_NOT_ALLOWED, "管理员不能删除自己账号");
        }

        litemallAdminService.deleteById(anotherAdminId);
        logHelper.logAuthSucceed("删除管理员", admin.getUsername());
        return ResponseUtil.ok();
    }

    @GetMapping("/shop/shopkeeper")
    @LogAnno
    public Object getShopkeeper(@NotNull @LoginAdminShopId @RequestParam(value = "shopId") Integer shopId) {
        return adminService.findShopMemberByRole(shopId, Constants.SHOPKEEPER_ROLE_ID);
    }
    @GetMapping("/shop/manager")
    @LogAnno
    public Object getShopManager(@NotNull @LoginAdminShopId @RequestParam(value = "shopId") Integer shopId) {
        return adminService.findShopMemberByRole(shopId, Constants.SHOP_MANAGER_ROLE_ID);
    }

    @RequiresPermissions("admin:admin:shopMembers")
    @RequiresPermissionsDesc(menu = {"门店管理", "门店成员"}, button = "列表")
    @GetMapping("/shop/members")
    @LogAnno
    public Object getShopMembers(@NotNull @LoginAdminShopId @RequestParam(value = "shopId") Integer shopId) {
        return adminService.findShopMembers(shopId);
    }

    @RequiresPermissions("admin:admin:all")
    @RequiresPermissionsDesc(menu = {"系统管理员", "系统管理员"}, button = "所有")
    @GetMapping("/all")
    @LogAnno
    public Object all() {
        return adminService.all();
    }

    @GetMapping("/info")
    @LogAnno
    public Object info() {
        LitemallAdmin admin = (LitemallAdmin)SecurityUtils.getSubject().getPrincipal();
        LitemallShop shop = litemallShopService.findById(admin.getShopId());
        Map<String, Object> map = new HashMap<>();
        map.put("admin", admin);
        map.put("shop", shop);
        return ResponseUtil.ok(map);
    }
}
