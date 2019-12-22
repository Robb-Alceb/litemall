package org.linlinjava.litemall.admin.service;

import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.domain.LitemallMessage;
import org.linlinjava.litemall.db.service.LitemallMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
    @Autowired
    private LitemallMessageService messageService;

    public Object queryMessageList(String title, Byte type,
                                   Integer page, Integer limit, String sort, String order) {
        return ResponseUtil.ok(messageService.querySelective(title, type, page, limit, sort, order));
    }

    public Object insert(LitemallMessage litemallMessage) {
        messageService.create(litemallMessage);
        return ResponseUtil.ok();
    }

    public Object update(LitemallMessage litemallMessage) {
        messageService.updateById(litemallMessage);
        return ResponseUtil.ok();
    }

    public Object delete(Integer messageId) {
        messageService.deleteById(messageId);
        return ResponseUtil.ok();
    }
}
