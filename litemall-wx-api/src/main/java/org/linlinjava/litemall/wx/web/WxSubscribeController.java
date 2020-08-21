package org.linlinjava.litemall.wx.web;

import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.wx.annotation.LogAnno;
import org.linlinjava.litemall.wx.annotation.LoginUser;
import org.linlinjava.litemall.wx.dto.SubscribeUserDto;
import org.linlinjava.litemall.wx.service.WxSubscribeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author ：stephen
 * @date ：Created in 2020/8/17 16:45
 * @description：用户订阅controller
 */
@RestController
@RequestMapping("/wx/subscribe")
@Validated
public class WxSubscribeController {
    @Autowired
    private WxSubscribeService wxSubscribeService;

    /**
     * 可订阅商品列表
     * @param shopId
     * @return
     */
    @GetMapping("/list")
    @LogAnno
    public Object list(@NotNull Integer shopId){
        return wxSubscribeService.list(shopId);
    }

    /**
     * 可订阅商品详情
     * @param id
     * @param userId
     * @param shopId
     * @param subscribeId
     * @param subscribeGoodsId
     * @return
     */
    @GetMapping("/detail")
    @LogAnno
    public Object detail(@NotNull Integer id, @LoginUser Integer userId, @NotNull Integer shopId, @NotNull Integer subscribeId, @NotNull Integer subscribeGoodsId){
        if(userId == null){
            return ResponseUtil.unlogin();
        }
        return wxSubscribeService.detail(id, userId, shopId, subscribeId, subscribeGoodsId);
    }

    /**
     * 可订阅商品辅料
     * @param shopId
     * @param goodsId
     * @return
     */
    @GetMapping("/accessory")
    @LogAnno
    public Object accessory(@NotNull Integer shopId, @NotNull Integer goodsId){
        return wxSubscribeService.accessory(goodsId, shopId);
    }

    /**
     * 我的订阅
     * @param userId
     * @return
     */
    @GetMapping("/myList")
    @LogAnno
    public Object myList(@LoginUser Integer userId){
        if(userId == null){
            return ResponseUtil.unlogin();
        }
        return wxSubscribeService.myList(userId);
    }

    /**
     * 我的订阅详情
     * @param userId
     * @param id
     * @return
     */
    @GetMapping("/read")
    @LogAnno
    public Object read(@LoginUser Integer userId, @NotNull Integer id){
        if(userId == null){
            return ResponseUtil.unlogin();
        }
        return wxSubscribeService.read(id);
    }

    /**
     * 用户订阅
     * @param userId
     * @param dto
     * @return
     */
    @PostMapping("/create")
    @LogAnno
    public Object create(@LoginUser Integer userId, @RequestBody @Valid SubscribeUserDto dto){
        if(userId == null){
            return ResponseUtil.unlogin();
        }
        return wxSubscribeService.create(dto, userId);
    }
}
