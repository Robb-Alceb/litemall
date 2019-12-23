package org.linlinjava.litemall.admin.beans.dto;

import lombok.Data;
import org.linlinjava.litemall.db.domain.LitemallMessage;

import java.util.List;

/**
 * @author ：stephen
 * @date ：Created in 2019/12/23 10:35
 * @description：消息
 */
@Data
public class MessageDto {
    private LitemallMessage message;
    private List<Integer> users;
}
