package org.linlinjava.litemall.admin.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.linlinjava.litemall.admin.beans.annotation.LogAnno;
import org.linlinjava.litemall.admin.beans.annotation.RequiresPermissionsDesc;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.core.validator.Order;
import org.linlinjava.litemall.core.validator.Sort;
import org.linlinjava.litemall.db.domain.LitemallAddress;
import org.linlinjava.litemall.db.service.LitemallAddressService;
import org.linlinjava.litemall.db.service.LitemallRegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin/address")
@Validated
public class AdminAddressController {
    private final Log logger = LogFactory.getLog(AdminAddressController.class);

    @Autowired
    private LitemallAddressService addressService;
    @Autowired
    private LitemallRegionService regionService;

    @RequiresPermissions("admin:address:list")
    @RequiresPermissionsDesc(menu = {"用户管理", "收货地址"}, button = "查询")
    @GetMapping("/list")
    @LogAnno
    public Object list(Integer userId, String name,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @Sort @RequestParam(defaultValue = "add_time") String sort,
                       @Order @RequestParam(defaultValue = "desc") String order) {

        List<LitemallAddress> addressList = addressService.querySelective(userId, name, page, limit, sort, order);
        addressList.stream().map(litemallAddress -> {
            Integer countryId = Integer.valueOf(litemallAddress.getCountry());
            Integer provinceId = Integer.valueOf(litemallAddress.getProvince());
            Integer cityId = Integer.valueOf(litemallAddress.getCity());
            if(!StringUtils.isEmpty(litemallAddress.getCounty())){
                Integer countyId = Integer.valueOf(litemallAddress.getCounty());
                litemallAddress.setCounty(regionService.findById(countyId).getNameCn());
            }
            litemallAddress.setCountry(regionService.findById(countryId).getNameCn());
            litemallAddress.setProvince(regionService.findById(provinceId).getNameCn());
            litemallAddress.setCity(regionService.findById(cityId).getNameCn());
            return litemallAddress;
        }).collect(Collectors.toList());
        return ResponseUtil.okList(addressList);
    }
}
