package org.linlinjava.litemall.wx.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.core.validator.Order;
import org.linlinjava.litemall.core.validator.Sort;
import org.linlinjava.litemall.db.domain.LitemallMsg;
import org.linlinjava.litemall.db.service.LitemallMsgService;
import org.linlinjava.litemall.wx.annotation.LogAnno;
import org.linlinjava.litemall.wx.annotation.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

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
    private LitemallMsgService litemallMsgService;

    /**
     * 消息列表
     */
    @GetMapping("/list")
    @LogAnno
    public Object list(@LoginUser Integer userId,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer size,
                       @Sort @RequestParam(defaultValue = "mark_read,add_time") String sort,
                       @Order @RequestParam(defaultValue = "asc") String order){
        return ResponseUtil.okList(litemallMsgService.querySelective(userId, page, size, sort, order));
    }

    /**
     * 标记已读
     */
    @PostMapping("/mark/{id}")
    @LogAnno
    public Object markRead(@LoginUser Integer userId, @PathVariable Integer id){
        LitemallMsg msg = new LitemallMsg();
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
        LitemallMsg msg = new LitemallMsg();
        msg.setUserId(userId);
        msg.setMarkRead(true);
        return ResponseUtil.ok(litemallMsgService.updateMarkByUserId(msg));
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    @LogAnno
    public Object delete(@LoginUser Integer uerId, @PathVariable Integer id){
        litemallMsgService.deleteById(id);
        return ResponseUtil.ok();
    }

    /**
     * 删除所有
     */
    @DeleteMapping("/delete/all")
    @LogAnno
    public Object deleteAll(@LoginUser Integer userId){
        LitemallMsg msg = new LitemallMsg();
        msg.setUserId(userId);
        msg.setMarkRead(true);
        return ResponseUtil.ok(litemallMsgService.deleteByUserId(userId));
    }
}
