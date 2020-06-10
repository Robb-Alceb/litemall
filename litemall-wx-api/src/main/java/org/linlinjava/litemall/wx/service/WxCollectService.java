package org.linlinjava.litemall.wx.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.domain.LitemallCollect;
import org.linlinjava.litemall.db.domain.LitemallCollectAccessory;
import org.linlinjava.litemall.db.service.LitemallCollectAccessoryService;
import org.linlinjava.litemall.db.service.LitemallCollectService;
import org.linlinjava.litemall.wx.annotation.LoginUser;
import org.linlinjava.litemall.wx.dto.WxCollectDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author ：stephen
 * @date ：Created in 2020/6/9 18:43
 * @description：TODO
 */
@Service
public class WxCollectService {
    private static final Log log = LogFactory.getLog(WxCollectService.class);

    @Autowired
    private LitemallCollectService collectService;
    @Autowired
    private LitemallCollectAccessoryService collectAccessoryService;

    /**
     * 添加或更新收藏
     * @param userId
     * @param wxCollectDto
     * @return
     */
    public Object addorupdate(Integer userId, WxCollectDto wxCollectDto) {
        LitemallCollect collect = collectService.queryByGoodsId(userId, wxCollectDto.getGoodsId());

        if (collect != null) {
            BeanUtils.copyProperties(wxCollectDto, collect);
            collectAccessoryService.deleteByCollectId(collect.getId());
            if(wxCollectDto.getAccessories() != null){
                for(LitemallCollectAccessory accessory : wxCollectDto.getAccessories()){
                    accessory.setCollectId(collect.getId());
                    collectAccessoryService.add(accessory);
                }
            }
            collectService.updateById(collect);
        } else {
            collect = new LitemallCollect();
            BeanUtils.copyProperties(wxCollectDto, collect);
            collect.setUserId(userId);
            collectService.add(collect);
            if(wxCollectDto.getAccessories() != null){
                for(LitemallCollectAccessory accessory : wxCollectDto.getAccessories()){
                    accessory.setCollectId(collect.getId());
                    collectAccessoryService.add(accessory);
                }
            }
        }
        return ResponseUtil.ok();
    }

    /**
     * 删除收藏
     * @param userId
     * @param goodsId
     * @return
     */
    public Object delete(Integer userId, Integer goodsId) {
        LitemallCollect litemallCollect = collectService.queryByGoodsId(userId, goodsId);
        collectService.deleteByGoodsId(userId, goodsId);
        collectAccessoryService.deleteByCollectId(litemallCollect.getId());
        return ResponseUtil.ok();
    }
}
