package org.linlinjava.litemall.db.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

public class LitemallGoodsProductLog {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table litemall_goods_product_log
     *
     * @mbg.generated
     */
    public static final Boolean IS_DELETED = Deleted.IS_DELETED.value();

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table litemall_goods_product_log
     *
     * @mbg.generated
     */
    public static final Boolean NOT_DELETED = Deleted.NOT_DELETED.value();

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column litemall_goods_product_log.id
     *
     * @mbg.generated
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column litemall_goods_product_log.shop_id
     *
     * @mbg.generated
     */
    private Integer shopId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column litemall_goods_product_log.goods_id
     *
     * @mbg.generated
     */
    private Integer goodsId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column litemall_goods_product_log.admin_order_id
     *
     * @mbg.generated
     */
    private Integer adminOrderId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column litemall_goods_product_log.goods_image
     *
     * @mbg.generated
     */
    private String goodsImage;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column litemall_goods_product_log.goods_name
     *
     * @mbg.generated
     */
    private String goodsName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column litemall_goods_product_log.pay_price
     *
     * @mbg.generated
     */
    private BigDecimal payPrice;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column litemall_goods_product_log.goods_sn
     *
     * @mbg.generated
     */
    private String goodsSn;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column litemall_goods_product_log.order_sn
     *
     * @mbg.generated
     */
    private String orderSn;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column litemall_goods_product_log.purchase_quantity
     *
     * @mbg.generated
     */
    private String purchaseQuantity;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column litemall_goods_product_log.remaining_number
     *
     * @mbg.generated
     */
    private String remainingNumber;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column litemall_goods_product_log.operation_type
     *
     * @mbg.generated
     */
    private Byte operationType;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column litemall_goods_product_log.content
     *
     * @mbg.generated
     */
    private String content;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column litemall_goods_product_log.user_name
     *
     * @mbg.generated
     */
    private String userName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column litemall_goods_product_log.log_type
     *
     * @mbg.generated
     */
    private Byte logType;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column litemall_goods_product_log.deleted
     *
     * @mbg.generated
     */
    private Boolean deleted;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column litemall_goods_product_log.add_time
     *
     * @mbg.generated
     */
    private LocalDateTime addTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column litemall_goods_product_log.add_user_id
     *
     * @mbg.generated
     */
    private Integer addUserId;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column litemall_goods_product_log.id
     *
     * @return the value of litemall_goods_product_log.id
     *
     * @mbg.generated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column litemall_goods_product_log.id
     *
     * @param id the value for litemall_goods_product_log.id
     *
     * @mbg.generated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column litemall_goods_product_log.shop_id
     *
     * @return the value of litemall_goods_product_log.shop_id
     *
     * @mbg.generated
     */
    public Integer getShopId() {
        return shopId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column litemall_goods_product_log.shop_id
     *
     * @param shopId the value for litemall_goods_product_log.shop_id
     *
     * @mbg.generated
     */
    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column litemall_goods_product_log.goods_id
     *
     * @return the value of litemall_goods_product_log.goods_id
     *
     * @mbg.generated
     */
    public Integer getGoodsId() {
        return goodsId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column litemall_goods_product_log.goods_id
     *
     * @param goodsId the value for litemall_goods_product_log.goods_id
     *
     * @mbg.generated
     */
    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column litemall_goods_product_log.admin_order_id
     *
     * @return the value of litemall_goods_product_log.admin_order_id
     *
     * @mbg.generated
     */
    public Integer getAdminOrderId() {
        return adminOrderId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column litemall_goods_product_log.admin_order_id
     *
     * @param adminOrderId the value for litemall_goods_product_log.admin_order_id
     *
     * @mbg.generated
     */
    public void setAdminOrderId(Integer adminOrderId) {
        this.adminOrderId = adminOrderId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column litemall_goods_product_log.goods_image
     *
     * @return the value of litemall_goods_product_log.goods_image
     *
     * @mbg.generated
     */
    public String getGoodsImage() {
        return goodsImage;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column litemall_goods_product_log.goods_image
     *
     * @param goodsImage the value for litemall_goods_product_log.goods_image
     *
     * @mbg.generated
     */
    public void setGoodsImage(String goodsImage) {
        this.goodsImage = goodsImage;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column litemall_goods_product_log.goods_name
     *
     * @return the value of litemall_goods_product_log.goods_name
     *
     * @mbg.generated
     */
    public String getGoodsName() {
        return goodsName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column litemall_goods_product_log.goods_name
     *
     * @param goodsName the value for litemall_goods_product_log.goods_name
     *
     * @mbg.generated
     */
    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column litemall_goods_product_log.pay_price
     *
     * @return the value of litemall_goods_product_log.pay_price
     *
     * @mbg.generated
     */
    public BigDecimal getPayPrice() {
        return payPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column litemall_goods_product_log.pay_price
     *
     * @param payPrice the value for litemall_goods_product_log.pay_price
     *
     * @mbg.generated
     */
    public void setPayPrice(BigDecimal payPrice) {
        this.payPrice = payPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column litemall_goods_product_log.goods_sn
     *
     * @return the value of litemall_goods_product_log.goods_sn
     *
     * @mbg.generated
     */
    public String getGoodsSn() {
        return goodsSn;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column litemall_goods_product_log.goods_sn
     *
     * @param goodsSn the value for litemall_goods_product_log.goods_sn
     *
     * @mbg.generated
     */
    public void setGoodsSn(String goodsSn) {
        this.goodsSn = goodsSn;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column litemall_goods_product_log.order_sn
     *
     * @return the value of litemall_goods_product_log.order_sn
     *
     * @mbg.generated
     */
    public String getOrderSn() {
        return orderSn;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column litemall_goods_product_log.order_sn
     *
     * @param orderSn the value for litemall_goods_product_log.order_sn
     *
     * @mbg.generated
     */
    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column litemall_goods_product_log.purchase_quantity
     *
     * @return the value of litemall_goods_product_log.purchase_quantity
     *
     * @mbg.generated
     */
    public String getPurchaseQuantity() {
        return purchaseQuantity;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column litemall_goods_product_log.purchase_quantity
     *
     * @param purchaseQuantity the value for litemall_goods_product_log.purchase_quantity
     *
     * @mbg.generated
     */
    public void setPurchaseQuantity(String purchaseQuantity) {
        this.purchaseQuantity = purchaseQuantity;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column litemall_goods_product_log.remaining_number
     *
     * @return the value of litemall_goods_product_log.remaining_number
     *
     * @mbg.generated
     */
    public String getRemainingNumber() {
        return remainingNumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column litemall_goods_product_log.remaining_number
     *
     * @param remainingNumber the value for litemall_goods_product_log.remaining_number
     *
     * @mbg.generated
     */
    public void setRemainingNumber(String remainingNumber) {
        this.remainingNumber = remainingNumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column litemall_goods_product_log.operation_type
     *
     * @return the value of litemall_goods_product_log.operation_type
     *
     * @mbg.generated
     */
    public Byte getOperationType() {
        return operationType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column litemall_goods_product_log.operation_type
     *
     * @param operationType the value for litemall_goods_product_log.operation_type
     *
     * @mbg.generated
     */
    public void setOperationType(Byte operationType) {
        this.operationType = operationType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column litemall_goods_product_log.content
     *
     * @return the value of litemall_goods_product_log.content
     *
     * @mbg.generated
     */
    public String getContent() {
        return content;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column litemall_goods_product_log.content
     *
     * @param content the value for litemall_goods_product_log.content
     *
     * @mbg.generated
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column litemall_goods_product_log.user_name
     *
     * @return the value of litemall_goods_product_log.user_name
     *
     * @mbg.generated
     */
    public String getUserName() {
        return userName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column litemall_goods_product_log.user_name
     *
     * @param userName the value for litemall_goods_product_log.user_name
     *
     * @mbg.generated
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column litemall_goods_product_log.log_type
     *
     * @return the value of litemall_goods_product_log.log_type
     *
     * @mbg.generated
     */
    public Byte getLogType() {
        return logType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column litemall_goods_product_log.log_type
     *
     * @param logType the value for litemall_goods_product_log.log_type
     *
     * @mbg.generated
     */
    public void setLogType(Byte logType) {
        this.logType = logType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_goods_product_log
     *
     * @mbg.generated
     */
    public void andLogicalDeleted(boolean deleted) {
        setDeleted(deleted ? Deleted.IS_DELETED.value() : Deleted.NOT_DELETED.value());
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column litemall_goods_product_log.deleted
     *
     * @return the value of litemall_goods_product_log.deleted
     *
     * @mbg.generated
     */
    public Boolean getDeleted() {
        return deleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column litemall_goods_product_log.deleted
     *
     * @param deleted the value for litemall_goods_product_log.deleted
     *
     * @mbg.generated
     */
    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column litemall_goods_product_log.add_time
     *
     * @return the value of litemall_goods_product_log.add_time
     *
     * @mbg.generated
     */
    public LocalDateTime getAddTime() {
        return addTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column litemall_goods_product_log.add_time
     *
     * @param addTime the value for litemall_goods_product_log.add_time
     *
     * @mbg.generated
     */
    public void setAddTime(LocalDateTime addTime) {
        this.addTime = addTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column litemall_goods_product_log.add_user_id
     *
     * @return the value of litemall_goods_product_log.add_user_id
     *
     * @mbg.generated
     */
    public Integer getAddUserId() {
        return addUserId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column litemall_goods_product_log.add_user_id
     *
     * @param addUserId the value for litemall_goods_product_log.add_user_id
     *
     * @mbg.generated
     */
    public void setAddUserId(Integer addUserId) {
        this.addUserId = addUserId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_goods_product_log
     *
     * @mbg.generated
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", IS_DELETED=").append(IS_DELETED);
        sb.append(", NOT_DELETED=").append(NOT_DELETED);
        sb.append(", id=").append(id);
        sb.append(", shopId=").append(shopId);
        sb.append(", goodsId=").append(goodsId);
        sb.append(", adminOrderId=").append(adminOrderId);
        sb.append(", goodsImage=").append(goodsImage);
        sb.append(", goodsName=").append(goodsName);
        sb.append(", payPrice=").append(payPrice);
        sb.append(", goodsSn=").append(goodsSn);
        sb.append(", orderSn=").append(orderSn);
        sb.append(", purchaseQuantity=").append(purchaseQuantity);
        sb.append(", remainingNumber=").append(remainingNumber);
        sb.append(", operationType=").append(operationType);
        sb.append(", content=").append(content);
        sb.append(", userName=").append(userName);
        sb.append(", logType=").append(logType);
        sb.append(", deleted=").append(deleted);
        sb.append(", addTime=").append(addTime);
        sb.append(", addUserId=").append(addUserId);
        sb.append("]");
        return sb.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_goods_product_log
     *
     * @mbg.generated
     */
    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        LitemallGoodsProductLog other = (LitemallGoodsProductLog) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getShopId() == null ? other.getShopId() == null : this.getShopId().equals(other.getShopId()))
            && (this.getGoodsId() == null ? other.getGoodsId() == null : this.getGoodsId().equals(other.getGoodsId()))
            && (this.getAdminOrderId() == null ? other.getAdminOrderId() == null : this.getAdminOrderId().equals(other.getAdminOrderId()))
            && (this.getGoodsImage() == null ? other.getGoodsImage() == null : this.getGoodsImage().equals(other.getGoodsImage()))
            && (this.getGoodsName() == null ? other.getGoodsName() == null : this.getGoodsName().equals(other.getGoodsName()))
            && (this.getPayPrice() == null ? other.getPayPrice() == null : this.getPayPrice().equals(other.getPayPrice()))
            && (this.getGoodsSn() == null ? other.getGoodsSn() == null : this.getGoodsSn().equals(other.getGoodsSn()))
            && (this.getOrderSn() == null ? other.getOrderSn() == null : this.getOrderSn().equals(other.getOrderSn()))
            && (this.getPurchaseQuantity() == null ? other.getPurchaseQuantity() == null : this.getPurchaseQuantity().equals(other.getPurchaseQuantity()))
            && (this.getRemainingNumber() == null ? other.getRemainingNumber() == null : this.getRemainingNumber().equals(other.getRemainingNumber()))
            && (this.getOperationType() == null ? other.getOperationType() == null : this.getOperationType().equals(other.getOperationType()))
            && (this.getContent() == null ? other.getContent() == null : this.getContent().equals(other.getContent()))
            && (this.getUserName() == null ? other.getUserName() == null : this.getUserName().equals(other.getUserName()))
            && (this.getLogType() == null ? other.getLogType() == null : this.getLogType().equals(other.getLogType()))
            && (this.getDeleted() == null ? other.getDeleted() == null : this.getDeleted().equals(other.getDeleted()))
            && (this.getAddTime() == null ? other.getAddTime() == null : this.getAddTime().equals(other.getAddTime()))
            && (this.getAddUserId() == null ? other.getAddUserId() == null : this.getAddUserId().equals(other.getAddUserId()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_goods_product_log
     *
     * @mbg.generated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getShopId() == null) ? 0 : getShopId().hashCode());
        result = prime * result + ((getGoodsId() == null) ? 0 : getGoodsId().hashCode());
        result = prime * result + ((getAdminOrderId() == null) ? 0 : getAdminOrderId().hashCode());
        result = prime * result + ((getGoodsImage() == null) ? 0 : getGoodsImage().hashCode());
        result = prime * result + ((getGoodsName() == null) ? 0 : getGoodsName().hashCode());
        result = prime * result + ((getPayPrice() == null) ? 0 : getPayPrice().hashCode());
        result = prime * result + ((getGoodsSn() == null) ? 0 : getGoodsSn().hashCode());
        result = prime * result + ((getOrderSn() == null) ? 0 : getOrderSn().hashCode());
        result = prime * result + ((getPurchaseQuantity() == null) ? 0 : getPurchaseQuantity().hashCode());
        result = prime * result + ((getRemainingNumber() == null) ? 0 : getRemainingNumber().hashCode());
        result = prime * result + ((getOperationType() == null) ? 0 : getOperationType().hashCode());
        result = prime * result + ((getContent() == null) ? 0 : getContent().hashCode());
        result = prime * result + ((getUserName() == null) ? 0 : getUserName().hashCode());
        result = prime * result + ((getLogType() == null) ? 0 : getLogType().hashCode());
        result = prime * result + ((getDeleted() == null) ? 0 : getDeleted().hashCode());
        result = prime * result + ((getAddTime() == null) ? 0 : getAddTime().hashCode());
        result = prime * result + ((getAddUserId() == null) ? 0 : getAddUserId().hashCode());
        return result;
    }

    /**
     * This enum was generated by MyBatis Generator.
     * This enum corresponds to the database table litemall_goods_product_log
     *
     * @mbg.generated
     */
    public enum Deleted {
        NOT_DELETED(new Boolean("0"), "未删除"),
        IS_DELETED(new Boolean("1"), "已删除");

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table litemall_goods_product_log
         *
         * @mbg.generated
         */
        private final Boolean value;

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table litemall_goods_product_log
         *
         * @mbg.generated
         */
        private final String name;

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_goods_product_log
         *
         * @mbg.generated
         */
        Deleted(Boolean value, String name) {
            this.value = value;
            this.name = name;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_goods_product_log
         *
         * @mbg.generated
         */
        public Boolean getValue() {
            return this.value;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_goods_product_log
         *
         * @mbg.generated
         */
        public Boolean value() {
            return this.value;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_goods_product_log
         *
         * @mbg.generated
         */
        public String getName() {
            return this.name;
        }
    }

    /**
     * This enum was generated by MyBatis Generator.
     * This enum corresponds to the database table litemall_goods_product_log
     *
     * @mbg.generated
     */
    public enum Column {
        id("id", "id", "INTEGER", false),
        shopId("shop_id", "shopId", "INTEGER", false),
        goodsId("goods_id", "goodsId", "INTEGER", false),
        adminOrderId("admin_order_id", "adminOrderId", "INTEGER", false),
        goodsImage("goods_image", "goodsImage", "VARCHAR", false),
        goodsName("goods_name", "goodsName", "VARCHAR", false),
        payPrice("pay_price", "payPrice", "DECIMAL", false),
        goodsSn("goods_sn", "goodsSn", "VARCHAR", false),
        orderSn("order_sn", "orderSn", "VARCHAR", false),
        purchaseQuantity("purchase_quantity", "purchaseQuantity", "VARCHAR", false),
        remainingNumber("remaining_number", "remainingNumber", "VARCHAR", false),
        operationType("operation_type", "operationType", "TINYINT", false),
        content("content", "content", "VARCHAR", false),
        userName("user_name", "userName", "VARCHAR", false),
        logType("log_type", "logType", "TINYINT", false),
        deleted("deleted", "deleted", "BIT", false),
        addTime("add_time", "addTime", "TIMESTAMP", false),
        addUserId("add_user_id", "addUserId", "INTEGER", false);

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table litemall_goods_product_log
         *
         * @mbg.generated
         */
        private static final String BEGINNING_DELIMITER = "`";

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table litemall_goods_product_log
         *
         * @mbg.generated
         */
        private static final String ENDING_DELIMITER = "`";

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table litemall_goods_product_log
         *
         * @mbg.generated
         */
        private final String column;

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table litemall_goods_product_log
         *
         * @mbg.generated
         */
        private final boolean isColumnNameDelimited;

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table litemall_goods_product_log
         *
         * @mbg.generated
         */
        private final String javaProperty;

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table litemall_goods_product_log
         *
         * @mbg.generated
         */
        private final String jdbcType;

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_goods_product_log
         *
         * @mbg.generated
         */
        public String value() {
            return this.column;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_goods_product_log
         *
         * @mbg.generated
         */
        public String getValue() {
            return this.column;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_goods_product_log
         *
         * @mbg.generated
         */
        public String getJavaProperty() {
            return this.javaProperty;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_goods_product_log
         *
         * @mbg.generated
         */
        public String getJdbcType() {
            return this.jdbcType;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_goods_product_log
         *
         * @mbg.generated
         */
        Column(String column, String javaProperty, String jdbcType, boolean isColumnNameDelimited) {
            this.column = column;
            this.javaProperty = javaProperty;
            this.jdbcType = jdbcType;
            this.isColumnNameDelimited = isColumnNameDelimited;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_goods_product_log
         *
         * @mbg.generated
         */
        public String desc() {
            return this.getEscapedColumnName() + " DESC";
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_goods_product_log
         *
         * @mbg.generated
         */
        public String asc() {
            return this.getEscapedColumnName() + " ASC";
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_goods_product_log
         *
         * @mbg.generated
         */
        public static Column[] excludes(Column ... excludes) {
            ArrayList<Column> columns = new ArrayList<>(Arrays.asList(Column.values()));
            if (excludes != null && excludes.length > 0) {
                columns.removeAll(new ArrayList<>(Arrays.asList(excludes)));
            }
            return columns.toArray(new Column[]{});
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_goods_product_log
         *
         * @mbg.generated
         */
        public String getEscapedColumnName() {
            if (this.isColumnNameDelimited) {
                return new StringBuilder().append(BEGINNING_DELIMITER).append(this.column).append(ENDING_DELIMITER).toString();
            } else {
                return this.column;
            }
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_goods_product_log
         *
         * @mbg.generated
         */
        public String getAliasedEscapedColumnName() {
            return this.getEscapedColumnName();
        }
    }
}