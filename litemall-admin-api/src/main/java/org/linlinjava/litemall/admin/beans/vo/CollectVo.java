package org.linlinjava.litemall.admin.beans.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author: stephen
 * @Date: 2020/2/10 15:21
 * @Version: 1.0
 * @Description: 用户收藏VO
 */
@Data
public class CollectVo {
    private Integer id;
    private Integer userId;
    private String userName;
    private Integer valueId;
    private String valueName;
    private Byte type;
    private LocalDateTime addTime;
    private LocalDateTime updateTime;
}
