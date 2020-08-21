package org.linlinjava.litemall.db.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * @author ：stephen
 * @date ：Created in 2020/8/20 14:28
 * @description：TODO
 */
public class SubscribeUserVo {
    private Integer id;
    private Integer subscribeId;
    private Integer userId;
    private String userName;
    private String nickname;
    private Integer shopId;
    private String shopName;
    private Integer goodsId;
    private String goodsName;
    private Integer goodsProductId;
    private Integer number;
    private String goodsItem;
    private BigDecimal price;
    private BigDecimal taxPrice;
    private Integer addressId;
    private String address;
    private Byte method;
    private Byte deliveryMethod;
    private Integer deliveryDays;
    private LocalTime deliveryStartTime;
    private LocalTime deliveryEndTime;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Boolean autoSub;
    private Byte autoPayType;
    private Integer autoPayId;
    private Integer linkId;
    private String remark;
    private LocalDateTime addTime;
    private LocalDateTime updateTime;
    private Boolean deleted;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSubscribeId() {
        return subscribeId;
    }

    public void setSubscribeId(Integer subscribeId) {
        this.subscribeId = subscribeId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Integer getGoodsProductId() {
        return goodsProductId;
    }

    public void setGoodsProductId(Integer goodsProductId) {
        this.goodsProductId = goodsProductId;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getGoodsItem() {
        return goodsItem;
    }

    public void setGoodsItem(String goodsItem) {
        this.goodsItem = goodsItem;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getTaxPrice() {
        return taxPrice;
    }

    public void setTaxPrice(BigDecimal taxPrice) {
        this.taxPrice = taxPrice;
    }

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Byte getMethod() {
        return method;
    }

    public void setMethod(Byte method) {
        this.method = method;
    }

    public Byte getDeliveryMethod() {
        return deliveryMethod;
    }

    public void setDeliveryMethod(Byte deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    public Integer getDeliveryDays() {
        return deliveryDays;
    }

    public void setDeliveryDays(Integer deliveryDays) {
        this.deliveryDays = deliveryDays;
    }

    public LocalTime getDeliveryStartTime() {
        return deliveryStartTime;
    }

    public void setDeliveryStartTime(LocalTime deliveryStartTime) {
        this.deliveryStartTime = deliveryStartTime;
    }

    public LocalTime getDeliveryEndTime() {
        return deliveryEndTime;
    }

    public void setDeliveryEndTime(LocalTime deliveryEndTime) {
        this.deliveryEndTime = deliveryEndTime;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Boolean getAutoSub() {
        return autoSub;
    }

    public void setAutoSub(Boolean autoSub) {
        this.autoSub = autoSub;
    }

    public Byte getAutoPayType() {
        return autoPayType;
    }

    public void setAutoPayType(Byte autoPayType) {
        this.autoPayType = autoPayType;
    }

    public Integer getAutoPayId() {
        return autoPayId;
    }

    public void setAutoPayId(Integer autoPayId) {
        this.autoPayId = autoPayId;
    }

    public Integer getLinkId() {
        return linkId;
    }

    public void setLinkId(Integer linkId) {
        this.linkId = linkId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public LocalDateTime getAddTime() {
        return addTime;
    }

    public void setAddTime(LocalDateTime addTime) {
        this.addTime = addTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return "SubscribeUserVo{" +
                "id=" + id +
                ", subscribeId=" + subscribeId +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                ", nickname='" + nickname + '\'' +
                ", shopId=" + shopId +
                ", shopName=" + shopName +
                ", goodsId=" + goodsId +
                ", goodsName='" + goodsName + '\'' +
                ", goodsProductId=" + goodsProductId +
                ", number=" + number +
                ", goodsItem='" + goodsItem + '\'' +
                ", price=" + price +
                ", taxPrice=" + taxPrice +
                ", addressId=" + addressId +
                ", address='" + address + '\'' +
                ", method=" + method +
                ", deliveryMethod=" + deliveryMethod +
                ", deliveryDays=" + deliveryDays +
                ", deliveryStartTime=" + deliveryStartTime +
                ", deliveryEndTime=" + deliveryEndTime +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", autoSub=" + autoSub +
                ", autoPayType=" + autoPayType +
                ", autoPayId=" + autoPayId +
                ", linkId=" + linkId +
                ", remark='" + remark + '\'' +
                ", addTime=" + addTime +
                ", updateTime=" + updateTime +
                ", deleted=" + deleted +
                '}';
    }
}
