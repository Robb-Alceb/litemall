package org.linlinjava.litemall.admin.service;

import org.linlinjava.litemall.db.beans.Constants;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.domain.LitemallFeedback;
import org.linlinjava.litemall.db.service.LitemallFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ：stephen
 * @date ：Created in 2019/12/17 10:39
 * @description：TODO
 */
@Service
public class FeedbackService {
    @Autowired
    private LitemallFeedbackService litemallFeedbackService;

    /**
     * 回复用户反馈
     * @param feedback
     * @return
     */
    public Object replay(LitemallFeedback feedback){
        if(null == feedback.getId()){
            return ResponseUtil.badArgument();
        }
        LitemallFeedback updateData = new LitemallFeedback();
        updateData.setId(feedback.getId());
        updateData.setStatus(Constants.FEEDBACK_STATUS_REPLY);
        updateData.setReply(feedback.getReply());
        litemallFeedbackService.updateById(updateData);
        return ResponseUtil.ok();
    }

    /**
     * 忽略用户反馈
     * @param feedback
     * @return
     */
    public Object ignore(LitemallFeedback feedback){
        if(null == feedback.getId()){
            return ResponseUtil.badArgument();
        }
        LitemallFeedback updateData = new LitemallFeedback();
        updateData.setId(feedback.getId());
        updateData.setStatus(Constants.FEEDBACK_STATUS_IGNORE);
        litemallFeedbackService.updateById(updateData);
        return ResponseUtil.ok();
    }
}
