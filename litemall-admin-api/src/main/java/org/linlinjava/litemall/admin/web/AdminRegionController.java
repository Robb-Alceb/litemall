package org.linlinjava.litemall.admin.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.admin.beans.annotation.LogAnno;
import org.linlinjava.litemall.admin.beans.vo.RegionVo;
import org.linlinjava.litemall.admin.service.AdminRegionService;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.beans.Constants;
import org.linlinjava.litemall.db.domain.LitemallRegion;
import org.linlinjava.litemall.db.service.LitemallRegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/admin/region")
@Validated
public class AdminRegionController {
    private final Log logger = LogFactory.getLog(AdminRegionController.class);

    @Autowired
    private LitemallRegionService regionService;
    @Autowired
    private AdminRegionService adminRegionService;

    /**
     * 国家列表
     * @return
     */
    @GetMapping("/clist")
    @LogAnno
    public Object clist() {
        List<LitemallRegion> countryList = regionService.queryByPid(0);
        return ResponseUtil.okList(countryList);
    }

    /**
     * 子列表
     * @param id
     * @param type {2:省份，3:城市}
     * @return
     */
    @GetMapping("/slist")
    @LogAnno
    public Object plist(@RequestParam(value = "id") Integer id, @NotNull Integer type) {
        if(id == null){
            //获取所有省份
            return ResponseUtil.okList(regionService.queryByType(type.byteValue()));
        }else{
            List<LitemallRegion> regionList = regionService.queryByPid(id);
            return ResponseUtil.okList(regionList);
        }
    }

    @GetMapping("/list")
    @LogAnno
    public Object list() {
        List<RegionVo> regionVoList = new ArrayList<>();

        List<LitemallRegion> countryList = regionService.queryByPid(0);
        for (LitemallRegion country : countryList) {
            RegionVo countryVO = new RegionVo();
            countryVO.setId(country.getId());
            countryVO.setNameCn(country.getNameCn());
            countryVO.setNameEn(country.getNameEn());
            countryVO.setCode(country.getCode());
            countryVO.setType(country.getType());

            List<LitemallRegion> provinceList = regionService.queryByPid(country.getId());
            List<RegionVo> provinceVOList = new ArrayList<>();
            for (LitemallRegion province : provinceList){
                RegionVo provinceVO = new RegionVo();
                provinceVO.setId(province.getId());
                provinceVO.setNameCn(province.getNameCn());
                provinceVO.setNameEn(province.getNameEn());
                provinceVO.setCode(province.getCode());
                provinceVO.setType(province.getType());
                provinceVOList.add(provinceVO);
            }
            countryVO.setChildren(provinceVOList);
            regionVoList.add(countryVO);
        }
        return regionVoList;

        /*List<LitemallRegion> provinceList = regionService.queryByPid(0);
        for (LitemallRegion province : provinceList) {
            RegionVo provinceVO = new RegionVo();
            provinceVO.setId(province.getId());
            provinceVO.setName(province.getName());
            provinceVO.setCode(province.getCode());
            provinceVO.setType(province.getType());

            List<LitemallRegion> cityList = regionService.queryByPid(province.getId());
            List<RegionVo> cityVOList = new ArrayList<>();
            for (LitemallRegion city : cityList) {
                RegionVo cityVO = new RegionVo();
                cityVO.setId(city.getId());
                cityVO.setName(city.getName());
                cityVO.setCode(city.getCode());
                cityVO.setType(city.getType());

                List<LitemallRegion> areaList = regionService.queryByPid(city.getId());
                List<RegionVo> areaVOList = new ArrayList<>();
                for (LitemallRegion area : areaList) {
                    RegionVo areaVO = new RegionVo();
                    areaVO.setId(area.getId());
                    areaVO.setName(area.getName());
                    areaVO.setCode(area.getCode());
                    areaVO.setType(area.getType());
                    areaVOList.add(areaVO);
                }

                cityVO.setChildren(areaVOList);
                cityVOList.add(cityVO);
            }
            provinceVO.setChildren(cityVOList);
            regionVoList.add(provinceVO);
        }

        return ResponseUtil.okList(regionVoList);*/
    }

    /**
     * 获取所有父级id
     * @param id
     * @return
     */
    @GetMapping("/parents/{id}")
    @LogAnno
    public Object parents(@PathVariable Integer id) {
        LinkedList<Integer> list = new LinkedList<>();
        adminRegionService.getParentIds(id, list);
        return ResponseUtil.ok(list);
    }
}
