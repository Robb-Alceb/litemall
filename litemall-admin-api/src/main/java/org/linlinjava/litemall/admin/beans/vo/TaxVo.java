package org.linlinjava.litemall.admin.beans.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author ：stephen
 * @date ：Created in 2020/5/14 10:16
 * @description：TODO
 */
@Data
public class TaxVo {
    private Integer id;
    private Integer regionId;
    private Byte type;
    private String name;
    private String code;
    private BigDecimal value;
    private Boolean enable;
    private LocalDateTime addTime;
    private Integer updateUserId;
    private LocalDateTime updateTime;
    private Boolean deleted;
    private String regionNameCn;
    private String regionNameEn;
}
