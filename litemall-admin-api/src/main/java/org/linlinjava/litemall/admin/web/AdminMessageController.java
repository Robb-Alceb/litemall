package org.linlinjava.litemall.admin.web;

import com.alibaba.fastjson.JSON;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.linlinjava.litemall.admin.beans.annotation.LogAnno;
import org.linlinjava.litemall.admin.beans.annotation.RequiresPermissionsDesc;
import org.linlinjava.litemall.admin.beans.dto.MessageDto;
import org.linlinjava.litemall.admin.service.MessageService;
import org.linlinjava.litemall.core.validator.Order;
import org.linlinjava.litemall.core.validator.Sort;
import org.linlinjava.litemall.db.domain.LitemallMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/admin/message")
@Validated
public class AdminMessageController {
    private final Log logger = LogFactory.getLog(AdminMessageController.class);

    @Autowired
    private MessageService messageService;

    /**
     * 消息列表
     * @param title
     * @param type
     * @param page
     * @param limit
     * @param sort
     * @param order
     * @return
     */
    @RequiresPermissions("admin:message:queryMessageList")
    @RequiresPermissionsDesc(menu = {"消息通知", "消息管理"}, button = "消息列表")
    @GetMapping("/queryMessageList")
    @LogAnno
    public Object queryMessageList(String title, Byte type,
                                   @RequestParam(defaultValue = "1") Integer page,
                                   @RequestParam(defaultValue = "10") Integer limit,
                                   @Sort @RequestParam(defaultValue = "add_time") String sort,
                                   @Order @RequestParam(defaultValue = "desc") String order) {
        return messageService.queryMessageList(title, type, page, limit, sort, order);
    }

    /**
     * 新增
     * @param messageDto
     * @return
     */
    @RequiresPermissions("admin:message:create")
    @RequiresPermissionsDesc(menu = {"消息通知", "消息管理"}, button = "新增")
    @PostMapping("/create")
    @LogAnno
    public Object insert(@RequestBody MessageDto messageDto) {
        return messageService.insert(messageDto);
    }


    /**
     * 删除
     * @param id
     * @return
     */
    @RequiresPermissions("admin:message:delete")
    @RequiresPermissionsDesc(menu = {"消息通知", "消息管理"}, button = "删除")
    @DeleteMapping("/delete")
    @LogAnno
    public Object delete(@NotNull @RequestParam(value = "id") Integer id) {
        return messageService.delete(id);
    }

}
