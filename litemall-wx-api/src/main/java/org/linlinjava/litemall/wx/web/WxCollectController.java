package org.linlinjava.litemall.wx.web;

import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.wx.annotation.LogAnno;
import org.linlinjava.litemall.wx.annotation.LoginUser;
import org.linlinjava.litemall.wx.dto.WxCollectDto;
import org.linlinjava.litemall.wx.service.WxCollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

/**
 * 用户收藏服务
 */
@RestController
@RequestMapping("/wx/collect")
@Validated
public class WxCollectController {

    @Autowired
    private WxCollectService wxCollectService;

    /**
     * 用户收藏添加或删除
     * <p>
     * 如果商品没有收藏，则添加收藏；如果商品已经收藏，则删除收藏状态。
     *
     * @param userId 用户ID
     * @param wxCollectDto
     * @return 操作结果
     */
    @PostMapping("addorupdate")
    @LogAnno
    public Object addorupdate(@LoginUser Integer userId, @RequestBody WxCollectDto wxCollectDto) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }
        return wxCollectService.addorupdate(userId, wxCollectDto);
    }

    @DeleteMapping("delete")
    @LogAnno
    public Object delete(@LoginUser Integer userId,@NotNull Integer goodsId) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }
        return wxCollectService.delete(userId, goodsId);
    }
}