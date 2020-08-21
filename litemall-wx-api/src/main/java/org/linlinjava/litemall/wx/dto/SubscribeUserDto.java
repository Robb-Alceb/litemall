package org.linlinjava.litemall.wx.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * @author ：stephen
 * @date ：Created in 2020/8/8 16:43
 * @description：TODO
 */
@Data
public class SubscribeUserDto {
    private Integer id;
    private Integer userId;
    @NotNull
    private Integer subscribeId;
    @NotNull
    private Integer shopId;
    @NotNull
    private Integer goodsId;
    @NotNull
    private Integer goodsProductId;
    private Integer addressId;
    @NotNull
    private Byte method;
    @NotNull
    private Byte deliveryMethod;
    @NotNull
    private Integer deliveryDays;
    @NotNull
    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime deliveryStartTime;
    @NotNull
    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime deliveryEndTime;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    @NotNull
    private Boolean autoSub;
    @NotNull
    private Byte autoPayType;
    private Integer autoPayId;
    @NotNull
    private Integer number;
    private String remark;
    @NotNull
    private GoodsItemDto goodsItemDto;
}
