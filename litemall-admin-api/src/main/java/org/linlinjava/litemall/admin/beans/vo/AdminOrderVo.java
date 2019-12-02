package org.linlinjava.litemall.admin.beans.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 订单对象
 */
@Data
public class AdminOrderVo {
    //申请调货
    //货品ID
    private Integer merchandiseId;
    //门店ID
    private Integer shopId;
    //申请用户ID
    private Integer userId;
    //进货金额
    private BigDecimal orderPrice;
    //申请数量
    private int number;
    //联系电话
    private String mobile;
    //收货人名称
    private String consignee;
    //收货地址
    private String address;
    //remark
    private String remark;

    //审批
    //订单ID
    private Integer adminOrderId;
    private String orderStatus;
    private String shipSn;
    private String orderSn;
    //处理备注
    private String handleRemark;
    //运费
    private BigDecimal shipPrice;
}
