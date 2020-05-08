package org.linlinjava.litemall.db.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

public class LitemallCoupon {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table litemall_coupon
     *
     * @mbg.generated
     */
    public static final Boolean IS_DELETED = Deleted.IS_DELETED.value();

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table litemall_coupon
     *
     * @mbg.generated
     */
    public static final Boolean NOT_DELETED = Deleted.NOT_DELETED.value();

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column litemall_coupon.id
     *
     * @mbg.generated
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column litemall_coupon.name
     *
     * @mbg.generated
     */
    private String name;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column litemall_coupon.desc
     *
     * @mbg.generated
     */
    private String desc;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column litemall_coupon.tag
     *
     * @mbg.generated
     */
    private String tag;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column litemall_coupon.total
     *
     * @mbg.generated
     */
    private Integer total;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column litemall_coupon.discount
     *
     * @mbg.generated
     */
    private BigDecimal discount;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column litemall_coupon.discount_type
     *
     * @mbg.generated
     */
    private Byte discountType;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column litemall_coupon.discount_rate
     *
     * @mbg.generated
     */
    private Byte discountRate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column litemall_coupon.min
     *
     * @mbg.generated
     */
    private BigDecimal min;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column litemall_coupon.limit
     *
     * @mbg.generated
     */
    private Short limit;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column litemall_coupon.type
     *
     * @mbg.generated
     */
    private Short type;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column litemall_coupon.user_level
     *
     * @mbg.generated
     */
    private Integer[] userLevel;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column litemall_coupon.status
     *
     * @mbg.generated
     */
    private Short status;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column litemall_coupon.goods_type
     *
     * @mbg.generated
     */
    private Short goodsType;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column litemall_coupon.goods_value
     *
     * @mbg.generated
     */
    private Integer[] goodsValue;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column litemall_coupon.code
     *
     * @mbg.generated
     */
    private String code;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column litemall_coupon.time_type
     *
     * @mbg.generated
     */
    private Short timeType;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column litemall_coupon.days
     *
     * @mbg.generated
     */
    private Short days;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column litemall_coupon.start_time
     *
     * @mbg.generated
     */
    private LocalDateTime startTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column litemall_coupon.end_time
     *
     * @mbg.generated
     */
    private LocalDateTime endTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column litemall_coupon.promotion_only
     *
     * @mbg.generated
     */
    private Boolean promotionOnly;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column litemall_coupon.add_time
     *
     * @mbg.generated
     */
    private LocalDateTime addTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column litemall_coupon.update_time
     *
     * @mbg.generated
     */
    private LocalDateTime updateTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column litemall_coupon.deleted
     *
     * @mbg.generated
     */
    private Boolean deleted;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column litemall_coupon.id
     *
     * @return the value of litemall_coupon.id
     *
     * @mbg.generated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column litemall_coupon.id
     *
     * @param id the value for litemall_coupon.id
     *
     * @mbg.generated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column litemall_coupon.name
     *
     * @return the value of litemall_coupon.name
     *
     * @mbg.generated
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column litemall_coupon.name
     *
     * @param name the value for litemall_coupon.name
     *
     * @mbg.generated
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column litemall_coupon.desc
     *
     * @return the value of litemall_coupon.desc
     *
     * @mbg.generated
     */
    public String getDesc() {
        return desc;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column litemall_coupon.desc
     *
     * @param desc the value for litemall_coupon.desc
     *
     * @mbg.generated
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column litemall_coupon.tag
     *
     * @return the value of litemall_coupon.tag
     *
     * @mbg.generated
     */
    public String getTag() {
        return tag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column litemall_coupon.tag
     *
     * @param tag the value for litemall_coupon.tag
     *
     * @mbg.generated
     */
    public void setTag(String tag) {
        this.tag = tag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column litemall_coupon.total
     *
     * @return the value of litemall_coupon.total
     *
     * @mbg.generated
     */
    public Integer getTotal() {
        return total;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column litemall_coupon.total
     *
     * @param total the value for litemall_coupon.total
     *
     * @mbg.generated
     */
    public void setTotal(Integer total) {
        this.total = total;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column litemall_coupon.discount
     *
     * @return the value of litemall_coupon.discount
     *
     * @mbg.generated
     */
    public BigDecimal getDiscount() {
        return discount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column litemall_coupon.discount
     *
     * @param discount the value for litemall_coupon.discount
     *
     * @mbg.generated
     */
    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column litemall_coupon.discount_type
     *
     * @return the value of litemall_coupon.discount_type
     *
     * @mbg.generated
     */
    public Byte getDiscountType() {
        return discountType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column litemall_coupon.discount_type
     *
     * @param discountType the value for litemall_coupon.discount_type
     *
     * @mbg.generated
     */
    public void setDiscountType(Byte discountType) {
        this.discountType = discountType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column litemall_coupon.discount_rate
     *
     * @return the value of litemall_coupon.discount_rate
     *
     * @mbg.generated
     */
    public Byte getDiscountRate() {
        return discountRate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column litemall_coupon.discount_rate
     *
     * @param discountRate the value for litemall_coupon.discount_rate
     *
     * @mbg.generated
     */
    public void setDiscountRate(Byte discountRate) {
        this.discountRate = discountRate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column litemall_coupon.min
     *
     * @return the value of litemall_coupon.min
     *
     * @mbg.generated
     */
    public BigDecimal getMin() {
        return min;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column litemall_coupon.min
     *
     * @param min the value for litemall_coupon.min
     *
     * @mbg.generated
     */
    public void setMin(BigDecimal min) {
        this.min = min;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column litemall_coupon.limit
     *
     * @return the value of litemall_coupon.limit
     *
     * @mbg.generated
     */
    public Short getLimit() {
        return limit;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column litemall_coupon.limit
     *
     * @param limit the value for litemall_coupon.limit
     *
     * @mbg.generated
     */
    public void setLimit(Short limit) {
        this.limit = limit;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column litemall_coupon.type
     *
     * @return the value of litemall_coupon.type
     *
     * @mbg.generated
     */
    public Short getType() {
        return type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column litemall_coupon.type
     *
     * @param type the value for litemall_coupon.type
     *
     * @mbg.generated
     */
    public void setType(Short type) {
        this.type = type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column litemall_coupon.user_level
     *
     * @return the value of litemall_coupon.user_level
     *
     * @mbg.generated
     */
    public Integer[] getUserLevel() {
        return userLevel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column litemall_coupon.user_level
     *
     * @param userLevel the value for litemall_coupon.user_level
     *
     * @mbg.generated
     */
    public void setUserLevel(Integer[] userLevel) {
        this.userLevel = userLevel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column litemall_coupon.status
     *
     * @return the value of litemall_coupon.status
     *
     * @mbg.generated
     */
    public Short getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column litemall_coupon.status
     *
     * @param status the value for litemall_coupon.status
     *
     * @mbg.generated
     */
    public void setStatus(Short status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column litemall_coupon.goods_type
     *
     * @return the value of litemall_coupon.goods_type
     *
     * @mbg.generated
     */
    public Short getGoodsType() {
        return goodsType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column litemall_coupon.goods_type
     *
     * @param goodsType the value for litemall_coupon.goods_type
     *
     * @mbg.generated
     */
    public void setGoodsType(Short goodsType) {
        this.goodsType = goodsType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column litemall_coupon.goods_value
     *
     * @return the value of litemall_coupon.goods_value
     *
     * @mbg.generated
     */
    public Integer[] getGoodsValue() {
        return goodsValue;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column litemall_coupon.goods_value
     *
     * @param goodsValue the value for litemall_coupon.goods_value
     *
     * @mbg.generated
     */
    public void setGoodsValue(Integer[] goodsValue) {
        this.goodsValue = goodsValue;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column litemall_coupon.code
     *
     * @return the value of litemall_coupon.code
     *
     * @mbg.generated
     */
    public String getCode() {
        return code;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column litemall_coupon.code
     *
     * @param code the value for litemall_coupon.code
     *
     * @mbg.generated
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column litemall_coupon.time_type
     *
     * @return the value of litemall_coupon.time_type
     *
     * @mbg.generated
     */
    public Short getTimeType() {
        return timeType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column litemall_coupon.time_type
     *
     * @param timeType the value for litemall_coupon.time_type
     *
     * @mbg.generated
     */
    public void setTimeType(Short timeType) {
        this.timeType = timeType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column litemall_coupon.days
     *
     * @return the value of litemall_coupon.days
     *
     * @mbg.generated
     */
    public Short getDays() {
        return days;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column litemall_coupon.days
     *
     * @param days the value for litemall_coupon.days
     *
     * @mbg.generated
     */
    public void setDays(Short days) {
        this.days = days;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column litemall_coupon.start_time
     *
     * @return the value of litemall_coupon.start_time
     *
     * @mbg.generated
     */
    public LocalDateTime getStartTime() {
        return startTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column litemall_coupon.start_time
     *
     * @param startTime the value for litemall_coupon.start_time
     *
     * @mbg.generated
     */
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column litemall_coupon.end_time
     *
     * @return the value of litemall_coupon.end_time
     *
     * @mbg.generated
     */
    public LocalDateTime getEndTime() {
        return endTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column litemall_coupon.end_time
     *
     * @param endTime the value for litemall_coupon.end_time
     *
     * @mbg.generated
     */
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column litemall_coupon.promotion_only
     *
     * @return the value of litemall_coupon.promotion_only
     *
     * @mbg.generated
     */
    public Boolean getPromotionOnly() {
        return promotionOnly;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column litemall_coupon.promotion_only
     *
     * @param promotionOnly the value for litemall_coupon.promotion_only
     *
     * @mbg.generated
     */
    public void setPromotionOnly(Boolean promotionOnly) {
        this.promotionOnly = promotionOnly;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column litemall_coupon.add_time
     *
     * @return the value of litemall_coupon.add_time
     *
     * @mbg.generated
     */
    public LocalDateTime getAddTime() {
        return addTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column litemall_coupon.add_time
     *
     * @param addTime the value for litemall_coupon.add_time
     *
     * @mbg.generated
     */
    public void setAddTime(LocalDateTime addTime) {
        this.addTime = addTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column litemall_coupon.update_time
     *
     * @return the value of litemall_coupon.update_time
     *
     * @mbg.generated
     */
    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column litemall_coupon.update_time
     *
     * @param updateTime the value for litemall_coupon.update_time
     *
     * @mbg.generated
     */
    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_coupon
     *
     * @mbg.generated
     */
    public void andLogicalDeleted(boolean deleted) {
        setDeleted(deleted ? Deleted.IS_DELETED.value() : Deleted.NOT_DELETED.value());
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column litemall_coupon.deleted
     *
     * @return the value of litemall_coupon.deleted
     *
     * @mbg.generated
     */
    public Boolean getDeleted() {
        return deleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column litemall_coupon.deleted
     *
     * @param deleted the value for litemall_coupon.deleted
     *
     * @mbg.generated
     */
    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_coupon
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
        sb.append(", name=").append(name);
        sb.append(", desc=").append(desc);
        sb.append(", tag=").append(tag);
        sb.append(", total=").append(total);
        sb.append(", discount=").append(discount);
        sb.append(", discountType=").append(discountType);
        sb.append(", discountRate=").append(discountRate);
        sb.append(", min=").append(min);
        sb.append(", limit=").append(limit);
        sb.append(", type=").append(type);
        sb.append(", userLevel=").append(userLevel);
        sb.append(", status=").append(status);
        sb.append(", goodsType=").append(goodsType);
        sb.append(", goodsValue=").append(goodsValue);
        sb.append(", code=").append(code);
        sb.append(", timeType=").append(timeType);
        sb.append(", days=").append(days);
        sb.append(", startTime=").append(startTime);
        sb.append(", endTime=").append(endTime);
        sb.append(", promotionOnly=").append(promotionOnly);
        sb.append(", addTime=").append(addTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", deleted=").append(deleted);
        sb.append("]");
        return sb.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_coupon
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
        LitemallCoupon other = (LitemallCoupon) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getDesc() == null ? other.getDesc() == null : this.getDesc().equals(other.getDesc()))
            && (this.getTag() == null ? other.getTag() == null : this.getTag().equals(other.getTag()))
            && (this.getTotal() == null ? other.getTotal() == null : this.getTotal().equals(other.getTotal()))
            && (this.getDiscount() == null ? other.getDiscount() == null : this.getDiscount().equals(other.getDiscount()))
            && (this.getDiscountType() == null ? other.getDiscountType() == null : this.getDiscountType().equals(other.getDiscountType()))
            && (this.getDiscountRate() == null ? other.getDiscountRate() == null : this.getDiscountRate().equals(other.getDiscountRate()))
            && (this.getMin() == null ? other.getMin() == null : this.getMin().equals(other.getMin()))
            && (this.getLimit() == null ? other.getLimit() == null : this.getLimit().equals(other.getLimit()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (Arrays.equals(this.getUserLevel(), other.getUserLevel()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getGoodsType() == null ? other.getGoodsType() == null : this.getGoodsType().equals(other.getGoodsType()))
            && (Arrays.equals(this.getGoodsValue(), other.getGoodsValue()))
            && (this.getCode() == null ? other.getCode() == null : this.getCode().equals(other.getCode()))
            && (this.getTimeType() == null ? other.getTimeType() == null : this.getTimeType().equals(other.getTimeType()))
            && (this.getDays() == null ? other.getDays() == null : this.getDays().equals(other.getDays()))
            && (this.getStartTime() == null ? other.getStartTime() == null : this.getStartTime().equals(other.getStartTime()))
            && (this.getEndTime() == null ? other.getEndTime() == null : this.getEndTime().equals(other.getEndTime()))
            && (this.getPromotionOnly() == null ? other.getPromotionOnly() == null : this.getPromotionOnly().equals(other.getPromotionOnly()))
            && (this.getAddTime() == null ? other.getAddTime() == null : this.getAddTime().equals(other.getAddTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getDeleted() == null ? other.getDeleted() == null : this.getDeleted().equals(other.getDeleted()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_coupon
     *
     * @mbg.generated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getDesc() == null) ? 0 : getDesc().hashCode());
        result = prime * result + ((getTag() == null) ? 0 : getTag().hashCode());
        result = prime * result + ((getTotal() == null) ? 0 : getTotal().hashCode());
        result = prime * result + ((getDiscount() == null) ? 0 : getDiscount().hashCode());
        result = prime * result + ((getDiscountType() == null) ? 0 : getDiscountType().hashCode());
        result = prime * result + ((getDiscountRate() == null) ? 0 : getDiscountRate().hashCode());
        result = prime * result + ((getMin() == null) ? 0 : getMin().hashCode());
        result = prime * result + ((getLimit() == null) ? 0 : getLimit().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + (Arrays.hashCode(getUserLevel()));
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getGoodsType() == null) ? 0 : getGoodsType().hashCode());
        result = prime * result + (Arrays.hashCode(getGoodsValue()));
        result = prime * result + ((getCode() == null) ? 0 : getCode().hashCode());
        result = prime * result + ((getTimeType() == null) ? 0 : getTimeType().hashCode());
        result = prime * result + ((getDays() == null) ? 0 : getDays().hashCode());
        result = prime * result + ((getStartTime() == null) ? 0 : getStartTime().hashCode());
        result = prime * result + ((getEndTime() == null) ? 0 : getEndTime().hashCode());
        result = prime * result + ((getPromotionOnly() == null) ? 0 : getPromotionOnly().hashCode());
        result = prime * result + ((getAddTime() == null) ? 0 : getAddTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getDeleted() == null) ? 0 : getDeleted().hashCode());
        return result;
    }

    /**
     * This enum was generated by MyBatis Generator.
     * This enum corresponds to the database table litemall_coupon
     *
     * @mbg.generated
     */
    public enum Deleted {
        NOT_DELETED(new Boolean("0"), "未删除"),
        IS_DELETED(new Boolean("1"), "已删除");

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table litemall_coupon
         *
         * @mbg.generated
         */
        private final Boolean value;

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table litemall_coupon
         *
         * @mbg.generated
         */
        private final String name;

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_coupon
         *
         * @mbg.generated
         */
        Deleted(Boolean value, String name) {
            this.value = value;
            this.name = name;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_coupon
         *
         * @mbg.generated
         */
        public Boolean getValue() {
            return this.value;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_coupon
         *
         * @mbg.generated
         */
        public Boolean value() {
            return this.value;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_coupon
         *
         * @mbg.generated
         */
        public String getName() {
            return this.name;
        }
    }

    /**
     * This enum was generated by MyBatis Generator.
     * This enum corresponds to the database table litemall_coupon
     *
     * @mbg.generated
     */
    public enum Column {
        id("id", "id", "INTEGER", false),
        name("name", "name", "VARCHAR", true),
        desc("desc", "desc", "VARCHAR", true),
        tag("tag", "tag", "VARCHAR", false),
        total("total", "total", "INTEGER", false),
        discount("discount", "discount", "DECIMAL", false),
        discountType("discount_type", "discountType", "TINYINT", false),
        discountRate("discount_rate", "discountRate", "TINYINT", false),
        min("min", "min", "DECIMAL", true),
        limit("limit", "limit", "SMALLINT", true),
        type("type", "type", "SMALLINT", true),
        userLevel("user_level", "userLevel", "VARCHAR", false),
        status("status", "status", "SMALLINT", true),
        goodsType("goods_type", "goodsType", "SMALLINT", false),
        goodsValue("goods_value", "goodsValue", "VARCHAR", false),
        code("code", "code", "VARCHAR", false),
        timeType("time_type", "timeType", "SMALLINT", false),
        days("days", "days", "SMALLINT", true),
        startTime("start_time", "startTime", "TIMESTAMP", false),
        endTime("end_time", "endTime", "TIMESTAMP", false),
        promotionOnly("promotion_only", "promotionOnly", "BIT", false),
        addTime("add_time", "addTime", "TIMESTAMP", false),
        updateTime("update_time", "updateTime", "TIMESTAMP", false),
        deleted("deleted", "deleted", "BIT", false);

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table litemall_coupon
         *
         * @mbg.generated
         */
        private static final String BEGINNING_DELIMITER = "`";

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table litemall_coupon
         *
         * @mbg.generated
         */
        private static final String ENDING_DELIMITER = "`";

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table litemall_coupon
         *
         * @mbg.generated
         */
        private final String column;

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table litemall_coupon
         *
         * @mbg.generated
         */
        private final boolean isColumnNameDelimited;

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table litemall_coupon
         *
         * @mbg.generated
         */
        private final String javaProperty;

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table litemall_coupon
         *
         * @mbg.generated
         */
        private final String jdbcType;

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_coupon
         *
         * @mbg.generated
         */
        public String value() {
            return this.column;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_coupon
         *
         * @mbg.generated
         */
        public String getValue() {
            return this.column;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_coupon
         *
         * @mbg.generated
         */
        public String getJavaProperty() {
            return this.javaProperty;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_coupon
         *
         * @mbg.generated
         */
        public String getJdbcType() {
            return this.jdbcType;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_coupon
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
         * This method corresponds to the database table litemall_coupon
         *
         * @mbg.generated
         */
        public String desc() {
            return this.getEscapedColumnName() + " DESC";
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_coupon
         *
         * @mbg.generated
         */
        public String asc() {
            return this.getEscapedColumnName() + " ASC";
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_coupon
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
         * This method corresponds to the database table litemall_coupon
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
         * This method corresponds to the database table litemall_coupon
         *
         * @mbg.generated
         */
        public String getAliasedEscapedColumnName() {
            return this.getEscapedColumnName();
        }
    }
}