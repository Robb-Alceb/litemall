package org.linlinjava.litemall.core.notify;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.core.notify.netty.PushService;
import org.linlinjava.litemall.db.domain.LitemallMsg;
import org.linlinjava.litemall.db.service.LitemallMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author ：stephen
 * @date ：Created in 2020/4/9 14:21
 * @description：TODO
 */
@Component
public class NoticeHelper {
    private Log log = LogFactory.getLog(NoticeHelper.class);
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
    @Async
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void noticeUser(Byte type, String content, Integer userId) {
        noticeUser(type, null, content, null, userId);
    }

    @Async
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void noticeUser(Byte type, String title, String content, Integer userId) {
        noticeUser(type, title, content, null, userId);
    }

    @Async
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void noticeUser(Byte type, String title, String content, String link, Integer userId) {


        //保存消息
        LitemallMsg msg = new LitemallMsg();
        msg.setContent(content);
        msg.setUserId(userId);
        msg.setType(type);
        msg.setLink(link);
        msg.setTitle(title);

        log.info("noticeUser param is :" + msg.toString());
        //推送消息
        pushService.pushMsgToOne(String.valueOf(userId), msg);
        litemallMsgService.create(msg);
    }
}
