package org.linlinjava.litemall.admin.web;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.linlinjava.litemall.admin.beans.annotation.LogAnno;
import org.linlinjava.litemall.admin.beans.annotation.RequiresPermissionsDesc;
import org.linlinjava.litemall.admin.service.FeedbackService;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.core.validator.Order;
import org.linlinjava.litemall.core.validator.Sort;
import org.linlinjava.litemall.db.domain.LitemallFeedback;
import org.linlinjava.litemall.db.service.LitemallFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/admin/feedback")
@Validated
public class AdminFeedbackController {

    @Autowired
    private LitemallFeedbackService litemallFeedbackService;
    @Autowired
    private FeedbackService feedbackService;

    @RequiresPermissions("admin:feedback:list")
    @RequiresPermissionsDesc(menu = {"用户管理", "意见反馈"}, button = "查询")
    @GetMapping("/list")
    @LogAnno
    public Object list(Integer userId, String username,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @Sort @RequestParam(defaultValue = "add_time") String sort,
                       @Order @RequestParam(defaultValue = "desc") String order) {
        List<LitemallFeedback> feedbackList = litemallFeedbackService.querySelective(userId, username, page, limit, sort,
                order);
        return ResponseUtil.okList(feedbackList);
    }

    @RequiresPermissions("admin:feedback:reply")
    @RequiresPermissionsDesc(menu = {"用户管理", "意见反馈"}, button = "回复")
    @PostMapping("/reply")
    @LogAnno
    public Object reply(@RequestBody LitemallFeedback feedback) {
        return feedbackService.replay(feedback);
    }

    @RequiresPermissions("admin:feedback:ignore")
    @RequiresPermissionsDesc(menu = {"用户管理", "意见反馈"}, button = "忽略")
    @PostMapping("/ignore")
    @LogAnno
    public Object ignore(@RequestBody LitemallFeedback feedback) {
        return feedbackService.ignore(feedback);
    }
}
