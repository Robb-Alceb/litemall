package org.linlinjava.litemall.db.service;

import com.github.pagehelper.PageHelper;
import org.linlinjava.litemall.db.dao.LitemallMessageMapper;
import org.linlinjava.litemall.db.dao.LitemallMessageReceiverMapper;
import org.linlinjava.litemall.db.domain.LitemallMessage;
import org.linlinjava.litemall.db.domain.LitemallMessageExample;
import org.linlinjava.litemall.db.domain.LitemallMessageReceiver;
import org.linlinjava.litemall.db.domain.LitemallMessageReceiverExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class LitemallMessageReceiverService {
    @Resource
    private LitemallMessageReceiverMapper litemallMessageReceiverMapper;

    public void create(LitemallMessageReceiver litemallMessage){
        litemallMessage.setAddTime(LocalDateTime.now());
        litemallMessageReceiverMapper.insertSelective(litemallMessage);
    }

}
