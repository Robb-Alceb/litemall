package org.linlinjava.litemall.core.notify;

import org.linlinjava.litemall.core.notify.netty.PushService;
import org.linlinjava.litemall.db.domain.LitemallMsg;
import org.linlinjava.litemall.db.service.LitemallMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author ：stephen
 * @date ：Created in 2020/4/9 14:21
 * @description：TODO
 */
@Component
public class NoticeHelper {
    @Autowired
    private LitemallMsgService litemallMsgService;
    @Autowired
    private PushService pushService;

    /**
     * 推送消息并保存到数据库
     * @param userId
     * @param content
     * @param type
     * @return
     */
    public void noticeUser(Byte type, String content, Integer userId) {
        //推送消息
        pushService.pushMsgToOne(String.valueOf(userId), content);
        //保存消息
        LitemallMsg msg = new LitemallMsg();
        msg.setContent(content);
        msg.setUserId(userId);
        msg.setType(type);
        litemallMsgService.create(msg);
    }
}
