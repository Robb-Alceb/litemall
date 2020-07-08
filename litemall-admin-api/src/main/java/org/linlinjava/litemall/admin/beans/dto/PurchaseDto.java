package org.linlinjava.litemall.admin.beans.dto;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author ：stephen
 * @date ：Created in 2020/7/2 16:16
 * @description：TODO
 */
public class PurchaseDto {
    //门店ID
    private Integer shopId;
    //申请用户ID
    private Integer userId;
    //进货金额
    @NotNull
    private BigDecimal orderPrice;
    //联系电话
    private String mobile;
    //收货人名称
    @NotNull
    private String consignee;
    //收货地址
    private String address;
    //remark
    private String remark;


    private List<PurchaseItemDto> purchaseItemDtos;


    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public BigDecimal getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(BigDecimal orderPrice) {
        this.orderPrice = orderPrice;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<PurchaseItemDto> getPurchaseItemDtos() {
        return purchaseItemDtos;
    }

    public void setPurchaseItemDtos(List<PurchaseItemDto> purchaseItemDtos) {
        this.purchaseItemDtos = purchaseItemDtos;
    }
}
