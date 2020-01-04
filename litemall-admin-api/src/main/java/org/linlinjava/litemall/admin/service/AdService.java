package org.linlinjava.litemall.admin.service;

import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.domain.LitemallAd;
import org.linlinjava.litemall.db.service.LitemallAdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * @author ：stephen
 * @date ：Created in 2019/12/17 12:11
 * @description：TODO
 */
@Service
public class AdService {

    @Autowired
    private LitemallAdService litemallAdService;

    public Object update(LitemallAd ad, Integer shopId){
        if(null == ad || null == ad.getId()){
            return ResponseUtil.badArgument();
        }
        if(null != shopId){
            LitemallAd litemallAd = litemallAdService.findById(ad.getId());
            if(null == litemallAd || !Arrays.asList(litemallAd.getShopIds()).contains(shopId)){
                return ResponseUtil.unauthz();
            }
        }
        if (litemallAdService.updateById(ad) == 0) {
            return ResponseUtil.updatedDataFailed();
        }
        return ResponseUtil.ok(ad);
    }
}
