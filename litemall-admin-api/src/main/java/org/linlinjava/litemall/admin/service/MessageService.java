package org.linlinjava.litemall.admin.service;

import org.apache.shiro.SecurityUtils;
import org.linlinjava.litemall.admin.beans.dto.MessageDto;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.domain.LitemallAdmin;
import org.linlinjava.litemall.db.domain.LitemallMessage;
import org.linlinjava.litemall.db.domain.LitemallMessageReceiver;
import org.linlinjava.litemall.db.service.LitemallMessageReceiverService;
import org.linlinjava.litemall.db.service.LitemallMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class MessageService {
    @Autowired
    private LitemallMessageService messageService;
    @Autowired
    private LitemallMessageReceiverService receiverService;

    public Object queryMessageList(String title, Byte type,
                                   Integer page, Integer limit, String sort, String order) {
        return ResponseUtil.okList(messageService.querySelective(title, type, page, limit, sort, order));
    }

    public Object insert(MessageDto messageDto) {
        if(null == messageDto.getMessage() || StringUtils.isEmpty(messageDto.getMessage().getTitle()) || StringUtils.isEmpty(messageDto.getMessage().getContent())){
            return ResponseUtil.badArgument();
        }
        LitemallMessage message = messageDto.getMessage();
        LitemallAdmin admin = (LitemallAdmin)SecurityUtils.getSubject().getPrincipal();
        message.setAddUserId(admin.getId());
        message.setAddUserName(admin.getUsername());
        int messageId = messageService.create(message);

        if(messageDto.getUsers() != null && messageDto.getUsers().size() > 0){
            for (Integer userId:
            messageDto.getUsers()) {
                LitemallMessageReceiver receiver = new LitemallMessageReceiver();
                receiver.setMessageId(messageId);
                receiver.setUserId(userId);
                receiverService.create(receiver);
            }
        }
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
