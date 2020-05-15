package org.linlinjava.litemall.admin.service;

import org.linlinjava.litemall.admin.beans.vo.TaxVo;
import org.linlinjava.litemall.admin.util.AdminResponseEnum;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.domain.LitemallRegion;
import org.linlinjava.litemall.db.domain.LitemallTax;
import org.linlinjava.litemall.db.service.LitemallRegionService;
import org.linlinjava.litemall.db.service.LitemallTaxService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ：stephen
 * @date ：Created in 2020/5/13 17:22
 * @description：TODO
 */
@Service
public class AdminTaxService {

    @Autowired
    private LitemallTaxService litemallTaxService;
    @Autowired
    private LitemallRegionService litemallRegionService;

    public Object create(LitemallTax tax){
        if(litemallTaxService.exist(tax)){
            return ResponseUtil.fail(AdminResponseEnum.TAX_EXIST);
        }
        litemallTaxService.add(tax);
        return ResponseUtil.ok();
    }

    public Object update(LitemallTax tax){
        if(litemallTaxService.findById(tax.getId()) == null){
            return ResponseUtil.fail(AdminResponseEnum.TAX_NOT_EXIST);
        }
        litemallTaxService.update(tax);
        return ResponseUtil.ok();
    }

    public Object querySelective(Integer regionId, String code, Integer page, Integer size, String sort, String order){
        List<LitemallTax> litemallTaxes = litemallTaxService.querySelective(regionId, code, page, size, sort, order);
        List<TaxVo> collect = litemallTaxes.stream().map(tax -> {
            LitemallRegion region = litemallRegionService.findById(tax.getRegionId());
            TaxVo vo = new TaxVo();
            BeanUtils.copyProperties(tax, vo);
            if (region != null) {
                vo.setRegionNameCn(region.getNameCn());
                vo.setRegionNameEn(region.getNameEn());
            }
            return vo;
        }).collect(Collectors.toList());
        return ResponseUtil.okList(collect);
    }


    public Object delete(Integer id){
        litemallTaxService.deleteById(id);
        return ResponseUtil.ok();
    }

    public Object enable(LitemallTax tax) {
        LitemallTax update = new LitemallTax();
        update.setId(tax.getId());
        update.setEnable(tax.getEnable());
        litemallTaxService.update(update);
        return ResponseUtil.ok();
    }
}
