package org.linlinjava.litemall.wx.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.core.validator.Order;
import org.linlinjava.litemall.core.validator.Sort;
import org.linlinjava.litemall.db.domain.LitemallNotice;
import org.linlinjava.litemall.db.domain.LitemallNoticeWithBLOBs;
import org.linlinjava.litemall.db.service.LitemallNoticeService;
import org.linlinjava.litemall.wx.annotation.LogAnno;
import org.linlinjava.litemall.wx.annotation.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author ：stephen
 * @date ：Created in 2020/4/8 16:44
 * @description：TODO
 */
@RestController
@RequestMapping("/wx/msg")
public class WxMsgController {

    private final Log logger = LogFactory.getLog(WxMsgController.class);

    @Autowired
    private LitemallNoticeService litemallMsgService;

    /**
     * 消息列表
     */
    @GetMapping("/list")
    @LogAnno
    public Object list(@LoginUser Integer userId,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @Sort @RequestParam(defaultValue = "add_time") String sort,
                       @Order @RequestParam(defaultValue = "desc") String order){
        if(userId == null){
            return ResponseUtil.unlogin();
        }
        return ResponseUtil.okList(litemallMsgService.querySelective(userId, page, limit, sort, order));
    }

    /**
     * 标记已读
     */
    @PostMapping("/mark/{id}")
    @LogAnno
    public Object markRead(@LoginUser Integer userId, @PathVariable Integer id){
        if(userId == null){
            return ResponseUtil.unlogin();
        }
        LitemallNoticeWithBLOBs msg = new LitemallNoticeWithBLOBs();
        msg.setId(id);
        msg.setMarkRead(true);
        litemallMsgService.updateById(msg);
        return ResponseUtil.ok();
    }

    /**
     * 标记所有已读
     */
    @PostMapping("/mark/all")
    @LogAnno
    public Object markAll(@LoginUser Integer userId){
        if(userId == null){
            return ResponseUtil.unlogin();
        }
        LitemallNotice msg = new LitemallNotice();
        msg.setUserId(userId);
        msg.setMarkRead(true);
        return ResponseUtil.ok(litemallMsgService.updateMarkByUserId(msg));
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    @LogAnno
    public Object delete(@LoginUser Integer userId, @PathVariable Integer id){
        if(userId == null){
            return ResponseUtil.unlogin();
        }
        litemallMsgService.deleteById(id);
        return ResponseUtil.ok();
    }

    /**
     * 删除所有
     */
    @DeleteMapping("/delete/all")
    @LogAnno
    public Object deleteAll(@LoginUser Integer userId){
        if(userId == null){
            return ResponseUtil.unlogin();
        }
        LitemallNotice msg = new LitemallNotice();
        msg.setUserId(userId);
        msg.setMarkRead(true);
        return ResponseUtil.ok(litemallMsgService.deleteByUserId(userId));
    }
}
