package org.linlinjava.litemall.admin.beans.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @Author: stephen
 * @Date: 2019/12/11 22:15
 * @Version: 1.0
 * @Description: TODO
 */

@Data
public class OrderVo {
    private Integer id;
    private String orderSn;
    private Integer shopId;
    private LocalDateTime addTime;
    private Integer userId;
    private String userName;
    private BigDecimal orderPrice;
    private Byte payType;
    private Byte orderSource;
    private Short orderStatus;
}
