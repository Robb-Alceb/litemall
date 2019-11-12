package org.linlinjava.litemall.db.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

public class LitemallVipGoodsPrice {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table litemall_vip_goods_price
     *
     * @mbg.generated
     */
    public static final Boolean IS_DELETED = Deleted.IS_DELETED.value();

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table litemall_vip_goods_price
     *
     * @mbg.generated
     */
    public static final Boolean NOT_DELETED = Deleted.NOT_DELETED.value();

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column litemall_vip_goods_price.id
     *
     * @mbg.generated
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column litemall_vip_goods_price.goods_id
     *
     * @mbg.generated
     */
    private Integer goodsId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column litemall_vip_goods_price.goods_name
     *
     * @mbg.generated
     */
    private String goodsName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column litemall_vip_goods_price.silver_vip_price
     *
     * @mbg.generated
     */
    private BigDecimal silverVipPrice;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column litemall_vip_goods_price.gold_vip_price
     *
     * @mbg.generated
     */
    private BigDecimal goldVipPrice;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column litemall_vip_goods_price.platinum_vip_price
     *
     * @mbg.generated
     */
    private BigDecimal platinumVipPrice;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column litemall_vip_goods_price.diamond_vip_price
     *
     * @mbg.generated
     */
    private BigDecimal diamondVipPrice;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column litemall_vip_goods_price.deleted
     *
     * @mbg.generated
     */
    private Boolean deleted;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column litemall_vip_goods_price.add_time
     *
     * @mbg.generated
     */
    private LocalDateTime addTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column litemall_vip_goods_price.add_user_id
     *
     * @mbg.generated
     */
    private Integer addUserId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column litemall_vip_goods_price.update_time
     *
     * @mbg.generated
     */
    private LocalDateTime updateTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column litemall_vip_goods_price.update_user_id
     *
     * @mbg.generated
     */
    private Integer updateUserId;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column litemall_vip_goods_price.id
     *
     * @return the value of litemall_vip_goods_price.id
     *
     * @mbg.generated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column litemall_vip_goods_price.id
     *
     * @param id the value for litemall_vip_goods_price.id
     *
     * @mbg.generated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column litemall_vip_goods_price.goods_id
     *
     * @return the value of litemall_vip_goods_price.goods_id
     *
     * @mbg.generated
     */
    public Integer getGoodsId() {
        return goodsId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column litemall_vip_goods_price.goods_id
     *
     * @param goodsId the value for litemall_vip_goods_price.goods_id
     *
     * @mbg.generated
     */
    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column litemall_vip_goods_price.goods_name
     *
     * @return the value of litemall_vip_goods_price.goods_name
     *
     * @mbg.generated
     */
    public String getGoodsName() {
        return goodsName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column litemall_vip_goods_price.goods_name
     *
     * @param goodsName the value for litemall_vip_goods_price.goods_name
     *
     * @mbg.generated
     */
    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column litemall_vip_goods_price.silver_vip_price
     *
     * @return the value of litemall_vip_goods_price.silver_vip_price
     *
     * @mbg.generated
     */
    public BigDecimal getSilverVipPrice() {
        return silverVipPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column litemall_vip_goods_price.silver_vip_price
     *
     * @param silverVipPrice the value for litemall_vip_goods_price.silver_vip_price
     *
     * @mbg.generated
     */
    public void setSilverVipPrice(BigDecimal silverVipPrice) {
        this.silverVipPrice = silverVipPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column litemall_vip_goods_price.gold_vip_price
     *
     * @return the value of litemall_vip_goods_price.gold_vip_price
     *
     * @mbg.generated
     */
    public BigDecimal getGoldVipPrice() {
        return goldVipPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column litemall_vip_goods_price.gold_vip_price
     *
     * @param goldVipPrice the value for litemall_vip_goods_price.gold_vip_price
     *
     * @mbg.generated
     */
    public void setGoldVipPrice(BigDecimal goldVipPrice) {
        this.goldVipPrice = goldVipPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column litemall_vip_goods_price.platinum_vip_price
     *
     * @return the value of litemall_vip_goods_price.platinum_vip_price
     *
     * @mbg.generated
     */
    public BigDecimal getPlatinumVipPrice() {
        return platinumVipPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column litemall_vip_goods_price.platinum_vip_price
     *
     * @param platinumVipPrice the value for litemall_vip_goods_price.platinum_vip_price
     *
     * @mbg.generated
     */
    public void setPlatinumVipPrice(BigDecimal platinumVipPrice) {
        this.platinumVipPrice = platinumVipPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column litemall_vip_goods_price.diamond_vip_price
     *
     * @return the value of litemall_vip_goods_price.diamond_vip_price
     *
     * @mbg.generated
     */
    public BigDecimal getDiamondVipPrice() {
        return diamondVipPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column litemall_vip_goods_price.diamond_vip_price
     *
     * @param diamondVipPrice the value for litemall_vip_goods_price.diamond_vip_price
     *
     * @mbg.generated
     */
    public void setDiamondVipPrice(BigDecimal diamondVipPrice) {
        this.diamondVipPrice = diamondVipPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_vip_goods_price
     *
     * @mbg.generated
     */
    public void andLogicalDeleted(boolean deleted) {
        setDeleted(deleted ? Deleted.IS_DELETED.value() : Deleted.NOT_DELETED.value());
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column litemall_vip_goods_price.deleted
     *
     * @return the value of litemall_vip_goods_price.deleted
     *
     * @mbg.generated
     */
    public Boolean getDeleted() {
        return deleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column litemall_vip_goods_price.deleted
     *
     * @param deleted the value for litemall_vip_goods_price.deleted
     *
     * @mbg.generated
     */
    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column litemall_vip_goods_price.add_time
     *
     * @return the value of litemall_vip_goods_price.add_time
     *
     * @mbg.generated
     */
    public LocalDateTime getAddTime() {
        return addTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column litemall_vip_goods_price.add_time
     *
     * @param addTime the value for litemall_vip_goods_price.add_time
     *
     * @mbg.generated
     */
    public void setAddTime(LocalDateTime addTime) {
        this.addTime = addTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column litemall_vip_goods_price.add_user_id
     *
     * @return the value of litemall_vip_goods_price.add_user_id
     *
     * @mbg.generated
     */
    public Integer getAddUserId() {
        return addUserId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column litemall_vip_goods_price.add_user_id
     *
     * @param addUserId the value for litemall_vip_goods_price.add_user_id
     *
     * @mbg.generated
     */
    public void setAddUserId(Integer addUserId) {
        this.addUserId = addUserId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column litemall_vip_goods_price.update_time
     *
     * @return the value of litemall_vip_goods_price.update_time
     *
     * @mbg.generated
     */
    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column litemall_vip_goods_price.update_time
     *
     * @param updateTime the value for litemall_vip_goods_price.update_time
     *
     * @mbg.generated
     */
    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column litemall_vip_goods_price.update_user_id
     *
     * @return the value of litemall_vip_goods_price.update_user_id
     *
     * @mbg.generated
     */
    public Integer getUpdateUserId() {
        return updateUserId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column litemall_vip_goods_price.update_user_id
     *
     * @param updateUserId the value for litemall_vip_goods_price.update_user_id
     *
     * @mbg.generated
     */
    public void setUpdateUserId(Integer updateUserId) {
        this.updateUserId = updateUserId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_vip_goods_price
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
        sb.append(", goodsId=").append(goodsId);
        sb.append(", goodsName=").append(goodsName);
        sb.append(", silverVipPrice=").append(silverVipPrice);
        sb.append(", goldVipPrice=").append(goldVipPrice);
        sb.append(", platinumVipPrice=").append(platinumVipPrice);
        sb.append(", diamondVipPrice=").append(diamondVipPrice);
        sb.append(", deleted=").append(deleted);
        sb.append(", addTime=").append(addTime);
        sb.append(", addUserId=").append(addUserId);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", updateUserId=").append(updateUserId);
        sb.append("]");
        return sb.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_vip_goods_price
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
        LitemallVipGoodsPrice other = (LitemallVipGoodsPrice) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getGoodsId() == null ? other.getGoodsId() == null : this.getGoodsId().equals(other.getGoodsId()))
            && (this.getGoodsName() == null ? other.getGoodsName() == null : this.getGoodsName().equals(other.getGoodsName()))
            && (this.getSilverVipPrice() == null ? other.getSilverVipPrice() == null : this.getSilverVipPrice().equals(other.getSilverVipPrice()))
            && (this.getGoldVipPrice() == null ? other.getGoldVipPrice() == null : this.getGoldVipPrice().equals(other.getGoldVipPrice()))
            && (this.getPlatinumVipPrice() == null ? other.getPlatinumVipPrice() == null : this.getPlatinumVipPrice().equals(other.getPlatinumVipPrice()))
            && (this.getDiamondVipPrice() == null ? other.getDiamondVipPrice() == null : this.getDiamondVipPrice().equals(other.getDiamondVipPrice()))
            && (this.getDeleted() == null ? other.getDeleted() == null : this.getDeleted().equals(other.getDeleted()))
            && (this.getAddTime() == null ? other.getAddTime() == null : this.getAddTime().equals(other.getAddTime()))
            && (this.getAddUserId() == null ? other.getAddUserId() == null : this.getAddUserId().equals(other.getAddUserId()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getUpdateUserId() == null ? other.getUpdateUserId() == null : this.getUpdateUserId().equals(other.getUpdateUserId()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_vip_goods_price
     *
     * @mbg.generated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getGoodsId() == null) ? 0 : getGoodsId().hashCode());
        result = prime * result + ((getGoodsName() == null) ? 0 : getGoodsName().hashCode());
        result = prime * result + ((getSilverVipPrice() == null) ? 0 : getSilverVipPrice().hashCode());
        result = prime * result + ((getGoldVipPrice() == null) ? 0 : getGoldVipPrice().hashCode());
        result = prime * result + ((getPlatinumVipPrice() == null) ? 0 : getPlatinumVipPrice().hashCode());
        result = prime * result + ((getDiamondVipPrice() == null) ? 0 : getDiamondVipPrice().hashCode());
        result = prime * result + ((getDeleted() == null) ? 0 : getDeleted().hashCode());
        result = prime * result + ((getAddTime() == null) ? 0 : getAddTime().hashCode());
        result = prime * result + ((getAddUserId() == null) ? 0 : getAddUserId().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getUpdateUserId() == null) ? 0 : getUpdateUserId().hashCode());
        return result;
    }

    /**
     * This enum was generated by MyBatis Generator.
     * This enum corresponds to the database table litemall_vip_goods_price
     *
     * @mbg.generated
     */
    public enum Deleted {
        NOT_DELETED(new Boolean("0"), "未删除"),
        IS_DELETED(new Boolean("1"), "已删除");

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table litemall_vip_goods_price
         *
         * @mbg.generated
         */
        private final Boolean value;

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table litemall_vip_goods_price
         *
         * @mbg.generated
         */
        private final String name;

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_vip_goods_price
         *
         * @mbg.generated
         */
        Deleted(Boolean value, String name) {
            this.value = value;
            this.name = name;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_vip_goods_price
         *
         * @mbg.generated
         */
        public Boolean getValue() {
            return this.value;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_vip_goods_price
         *
         * @mbg.generated
         */
        public Boolean value() {
            return this.value;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_vip_goods_price
         *
         * @mbg.generated
         */
        public String getName() {
            return this.name;
        }
    }

    /**
     * This enum was generated by MyBatis Generator.
     * This enum corresponds to the database table litemall_vip_goods_price
     *
     * @mbg.generated
     */
    public enum Column {
        id("id", "id", "INTEGER", false),
        goodsId("goods_id", "goodsId", "INTEGER", false),
        goodsName("goods_name", "goodsName", "VARCHAR", false),
        silverVipPrice("silver_vip_price", "silverVipPrice", "DECIMAL", false),
        goldVipPrice("gold_vip_price", "goldVipPrice", "DECIMAL", false),
        platinumVipPrice("platinum_vip_price", "platinumVipPrice", "DECIMAL", false),
        diamondVipPrice("diamond_vip_price", "diamondVipPrice", "DECIMAL", false),
        deleted("deleted", "deleted", "BIT", false),
        addTime("add_time", "addTime", "TIMESTAMP", false),
        addUserId("add_user_id", "addUserId", "INTEGER", false),
        updateTime("update_time", "updateTime", "TIMESTAMP", false),
        updateUserId("update_user_id", "updateUserId", "INTEGER", false);

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table litemall_vip_goods_price
         *
         * @mbg.generated
         */
        private static final String BEGINNING_DELIMITER = "`";

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table litemall_vip_goods_price
         *
         * @mbg.generated
         */
        private static final String ENDING_DELIMITER = "`";

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table litemall_vip_goods_price
         *
         * @mbg.generated
         */
        private final String column;

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table litemall_vip_goods_price
         *
         * @mbg.generated
         */
        private final boolean isColumnNameDelimited;

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table litemall_vip_goods_price
         *
         * @mbg.generated
         */
        private final String javaProperty;

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table litemall_vip_goods_price
         *
         * @mbg.generated
         */
        private final String jdbcType;

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_vip_goods_price
         *
         * @mbg.generated
         */
        public String value() {
            return this.column;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_vip_goods_price
         *
         * @mbg.generated
         */
        public String getValue() {
            return this.column;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_vip_goods_price
         *
         * @mbg.generated
         */
        public String getJavaProperty() {
            return this.javaProperty;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_vip_goods_price
         *
         * @mbg.generated
         */
        public String getJdbcType() {
            return this.jdbcType;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_vip_goods_price
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
         * This method corresponds to the database table litemall_vip_goods_price
         *
         * @mbg.generated
         */
        public String desc() {
            return this.getEscapedColumnName() + " DESC";
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_vip_goods_price
         *
         * @mbg.generated
         */
        public String asc() {
            return this.getEscapedColumnName() + " ASC";
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_vip_goods_price
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
         * This method corresponds to the database table litemall_vip_goods_price
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
         * This method corresponds to the database table litemall_vip_goods_price
         *
         * @mbg.generated
         */
        public String getAliasedEscapedColumnName() {
            return this.getEscapedColumnName();
        }
    }
}