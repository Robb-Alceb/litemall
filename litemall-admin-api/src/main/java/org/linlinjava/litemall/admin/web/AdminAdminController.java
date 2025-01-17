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
import org.linlinjava.litemall.admin.beans.dto.AdminDto;
import org.linlinjava.litemall.admin.beans.pojo.convert.BeanConvert;
import org.linlinjava.litemall.admin.service.AdminService;
import org.linlinjava.litemall.admin.service.LogHelper;
import org.linlinjava.litemall.admin.util.AdminResponseCode;
import org.linlinjava.litemall.core.util.DateTimeUtil;
import org.linlinjava.litemall.core.util.RegexUtil;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.core.util.bcrypt.BCryptPasswordEncoder;
import org.linlinjava.litemall.core.validator.Order;
import org.linlinjava.litemall.core.validator.Sort;
import org.linlinjava.litemall.db.domain.LitemallAdmin;
import org.linlinjava.litemall.db.domain.LitemallShop;
import org.linlinjava.litemall.db.domain.LitemallUser;
import org.linlinjava.litemall.db.service.LitemallAdminService;
import org.linlinjava.litemall.db.service.LitemallShopService;
import org.linlinjava.litemall.db.service.LitemallUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.*;

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
    @Autowired
    private LitemallUserService litemallUserService;

    @RequiresPermissions("admin:admin:list")
    @RequiresPermissionsDesc(menu = {"系统管理", "管理员管理"}, button = "查询")
    @GetMapping("/list")
    @LogAnno
    public Object list(String nickname,
                       @LoginAdminShopId Integer shopId,
                       Integer roleId,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @Sort @RequestParam(defaultValue = "add_time") String sort,
                       @Order @RequestParam(defaultValue = "desc") String order) {
        List<LitemallAdmin> adminList = litemallAdminService.querySelective(nickname, shopId, roleId, page, limit, sort, order);
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
    public Object create(@RequestBody AdminDto dto) {
        LitemallAdmin admin = new LitemallAdmin();
        BeanUtils.copyProperties(dto, admin);
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

        //给管理员生成一个user，用于pos下单
        LitemallUser user = BeanConvert.toUser(admin);
        user.setInnerAccount(true);
        litemallUserService.add(user);
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
    public Object update(@RequestBody AdminDto dto) {
        LitemallAdmin admin = new LitemallAdmin();
        BeanUtils.copyProperties(dto, admin);
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

        if(admin.getShopId() != null){
            List<LitemallAdmin> byShopId = litemallAdminService.findByShopId(admin.getShopId());
            if(Arrays.asList(admin.getRoleIds()).contains(Constants.SHOPKEEPER_ROLE_ID)){
                boolean hasShopkepper = byShopId.stream().anyMatch(ad -> {
                    return ad.getId() !=  admin.getId() && Arrays.asList(ad.getRoleIds()).contains(Constants.SHOPKEEPER_ROLE_ID);
                });
                if(hasShopkepper){
                    return ResponseUtil.fail(SHOP_HAS_SHOPKEEPER, "该门店已存在店长");
                }
            }
            if(Arrays.asList(admin.getRoleIds()).contains(Constants.SHOP_MANAGER_ROLE_ID)){
                boolean hasManager = byShopId.stream().anyMatch(ad -> {
                    return ad.getId() !=  admin.getId() && Arrays.asList(ad.getRoleIds()).contains(Constants.SHOP_MANAGER_ROLE_ID);
                });
                if(hasManager){
                    return ResponseUtil.fail(SHOP_HAS_MANAGER, "该门店已存在经理");
                }
            }
        }


        if (litemallAdminService.updateById(admin) == 0) {
            return ResponseUtil.updatedDataFailed();
        }

        logHelper.logAuthSucceed("编辑管理员", admin.getUsername());

        //修改admin信息时，同时修改user信息
        LitemallAdmin info = litemallAdminService.findAdmin(admin.getId());
        LitemallUser litemallUser = litemallUserService.findByAdmin(info.getUsername(), true);
        if(litemallUser != null){
            LitemallUser user = BeanConvert.toUser(admin);
            user.setId(litemallUser.getId());
            litemallUserService.updateById(user);
        }
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

        Integer[] roleIds = currentAdmin.getRoleIds();
        if(!ObjectUtils.isEmpty(roleIds)){
            for (Integer roleId:roleIds) {
                if(roleId.equals(Constants.SHOPKEEPER_ROLE_ID)){
                    return ResponseUtil.fail(ADMIN_DELETE_NOT_ALLOWED, "已被选做店长的管理员不能被删除");
                }
            }
        }


        litemallAdminService.deleteById(anotherAdminId);
        //逻辑删除pos收银账号
        litemallUserService.deleteByUserName(currentAdmin.getUsername());
        logHelper.logAuthSucceed("删除管理员", admin.getUsername());
        return ResponseUtil.ok();
    }

    @GetMapping("/shop/shopkeeper")
    @LogAnno
    public Object getShopkeeper(@NotNull @LoginAdminShopId Integer shopId) {
        return adminService.findShopMemberByRole(shopId, Constants.SHOPKEEPER_ROLE_ID);
    }
    @GetMapping("/shop/manager")
    @LogAnno
    public Object getShopManager(@NotNull @LoginAdminShopId Integer shopId) {
        return adminService.findShopMemberByRole(shopId, Constants.SHOP_MANAGER_ROLE_ID);
    }

    @RequiresPermissions("admin:admin:shopMembers")
    @RequiresPermissionsDesc(menu = {"门店管理", "门店成员"}, button = "列表")
    @GetMapping("/shop/members")
    @LogAnno
    public Object getShopMembers(@LoginAdminShopId Integer shopId) {
        if(shopId != null){
            return adminService.findShopMembers(shopId);
        }else{
            return adminService.all();
        }
    }

//    @RequiresPermissions("admin:admin:all")
//    @RequiresPermissionsDesc(menu = {"系统管理员", "系统管理员"}, button = "所有")
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
