package org.linlinjava.litemall.db.domain;

import java.util.ArrayList;
import java.util.List;

public class LitemallCollectAccessoryExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table litemall_collect_accessory
     *
     * @mbg.generated
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table litemall_collect_accessory
     *
     * @mbg.generated
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table litemall_collect_accessory
     *
     * @mbg.generated
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_collect_accessory
     *
     * @mbg.generated
     */
    public LitemallCollectAccessoryExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_collect_accessory
     *
     * @mbg.generated
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_collect_accessory
     *
     * @mbg.generated
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_collect_accessory
     *
     * @mbg.generated
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_collect_accessory
     *
     * @mbg.generated
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_collect_accessory
     *
     * @mbg.generated
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_collect_accessory
     *
     * @mbg.generated
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_collect_accessory
     *
     * @mbg.generated
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_collect_accessory
     *
     * @mbg.generated
     */
    public LitemallCollectAccessoryExample orderBy(String orderByClause) {
        this.setOrderByClause(orderByClause);
        return this;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_collect_accessory
     *
     * @mbg.generated
     */
    public LitemallCollectAccessoryExample orderBy(String ... orderByClauses) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < orderByClauses.length; i++) {
            sb.append(orderByClauses[i]);
            if (i < orderByClauses.length - 1) {
                sb.append(" , ");
            }
        }
        this.setOrderByClause(sb.toString());
        return this;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_collect_accessory
     *
     * @mbg.generated
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_collect_accessory
     *
     * @mbg.generated
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria(this);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_collect_accessory
     *
     * @mbg.generated
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_collect_accessory
     *
     * @mbg.generated
     */
    public static Criteria newAndCreateCriteria() {
        LitemallCollectAccessoryExample example = new LitemallCollectAccessoryExample();
        return example.createCriteria();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_collect_accessory
     *
     * @mbg.generated
     */
    public LitemallCollectAccessoryExample when(boolean condition, IExampleWhen then) {
        if (condition) {
            then.example(this);
        }
        return this;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_collect_accessory
     *
     * @mbg.generated
     */
    public LitemallCollectAccessoryExample when(boolean condition, IExampleWhen then, IExampleWhen otherwise) {
        if (condition) {
            then.example(this);
        } else {
            otherwise.example(this);
        }
        return this;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table litemall_collect_accessory
     *
     * @mbg.generated
     */
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_collect_accessory
         *
         * @mbg.generated
         */
        public Criteria andIdEqualToColumn(LitemallCollectAccessory.Column column) {
            addCriterion(new StringBuilder("id = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_collect_accessory
         *
         * @mbg.generated
         */
        public Criteria andIdNotEqualToColumn(LitemallCollectAccessory.Column column) {
            addCriterion(new StringBuilder("id <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_collect_accessory
         *
         * @mbg.generated
         */
        public Criteria andIdGreaterThanColumn(LitemallCollectAccessory.Column column) {
            addCriterion(new StringBuilder("id > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_collect_accessory
         *
         * @mbg.generated
         */
        public Criteria andIdGreaterThanOrEqualToColumn(LitemallCollectAccessory.Column column) {
            addCriterion(new StringBuilder("id >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_collect_accessory
         *
         * @mbg.generated
         */
        public Criteria andIdLessThanColumn(LitemallCollectAccessory.Column column) {
            addCriterion(new StringBuilder("id < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_collect_accessory
         *
         * @mbg.generated
         */
        public Criteria andIdLessThanOrEqualToColumn(LitemallCollectAccessory.Column column) {
            addCriterion(new StringBuilder("id <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andCollectIdIsNull() {
            addCriterion("collect_id is null");
            return (Criteria) this;
        }

        public Criteria andCollectIdIsNotNull() {
            addCriterion("collect_id is not null");
            return (Criteria) this;
        }

        public Criteria andCollectIdEqualTo(Integer value) {
            addCriterion("collect_id =", value, "collectId");
            return (Criteria) this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_collect_accessory
         *
         * @mbg.generated
         */
        public Criteria andCollectIdEqualToColumn(LitemallCollectAccessory.Column column) {
            addCriterion(new StringBuilder("collect_id = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCollectIdNotEqualTo(Integer value) {
            addCriterion("collect_id <>", value, "collectId");
            return (Criteria) this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_collect_accessory
         *
         * @mbg.generated
         */
        public Criteria andCollectIdNotEqualToColumn(LitemallCollectAccessory.Column column) {
            addCriterion(new StringBuilder("collect_id <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCollectIdGreaterThan(Integer value) {
            addCriterion("collect_id >", value, "collectId");
            return (Criteria) this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_collect_accessory
         *
         * @mbg.generated
         */
        public Criteria andCollectIdGreaterThanColumn(LitemallCollectAccessory.Column column) {
            addCriterion(new StringBuilder("collect_id > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCollectIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("collect_id >=", value, "collectId");
            return (Criteria) this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_collect_accessory
         *
         * @mbg.generated
         */
        public Criteria andCollectIdGreaterThanOrEqualToColumn(LitemallCollectAccessory.Column column) {
            addCriterion(new StringBuilder("collect_id >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCollectIdLessThan(Integer value) {
            addCriterion("collect_id <", value, "collectId");
            return (Criteria) this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_collect_accessory
         *
         * @mbg.generated
         */
        public Criteria andCollectIdLessThanColumn(LitemallCollectAccessory.Column column) {
            addCriterion(new StringBuilder("collect_id < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCollectIdLessThanOrEqualTo(Integer value) {
            addCriterion("collect_id <=", value, "collectId");
            return (Criteria) this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_collect_accessory
         *
         * @mbg.generated
         */
        public Criteria andCollectIdLessThanOrEqualToColumn(LitemallCollectAccessory.Column column) {
            addCriterion(new StringBuilder("collect_id <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCollectIdIn(List<Integer> values) {
            addCriterion("collect_id in", values, "collectId");
            return (Criteria) this;
        }

        public Criteria andCollectIdNotIn(List<Integer> values) {
            addCriterion("collect_id not in", values, "collectId");
            return (Criteria) this;
        }

        public Criteria andCollectIdBetween(Integer value1, Integer value2) {
            addCriterion("collect_id between", value1, value2, "collectId");
            return (Criteria) this;
        }

        public Criteria andCollectIdNotBetween(Integer value1, Integer value2) {
            addCriterion("collect_id not between", value1, value2, "collectId");
            return (Criteria) this;
        }

        public Criteria andAccessoryIdIsNull() {
            addCriterion("accessory_id is null");
            return (Criteria) this;
        }

        public Criteria andAccessoryIdIsNotNull() {
            addCriterion("accessory_id is not null");
            return (Criteria) this;
        }

        public Criteria andAccessoryIdEqualTo(Integer value) {
            addCriterion("accessory_id =", value, "accessoryId");
            return (Criteria) this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_collect_accessory
         *
         * @mbg.generated
         */
        public Criteria andAccessoryIdEqualToColumn(LitemallCollectAccessory.Column column) {
            addCriterion(new StringBuilder("accessory_id = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andAccessoryIdNotEqualTo(Integer value) {
            addCriterion("accessory_id <>", value, "accessoryId");
            return (Criteria) this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_collect_accessory
         *
         * @mbg.generated
         */
        public Criteria andAccessoryIdNotEqualToColumn(LitemallCollectAccessory.Column column) {
            addCriterion(new StringBuilder("accessory_id <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andAccessoryIdGreaterThan(Integer value) {
            addCriterion("accessory_id >", value, "accessoryId");
            return (Criteria) this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_collect_accessory
         *
         * @mbg.generated
         */
        public Criteria andAccessoryIdGreaterThanColumn(LitemallCollectAccessory.Column column) {
            addCriterion(new StringBuilder("accessory_id > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andAccessoryIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("accessory_id >=", value, "accessoryId");
            return (Criteria) this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_collect_accessory
         *
         * @mbg.generated
         */
        public Criteria andAccessoryIdGreaterThanOrEqualToColumn(LitemallCollectAccessory.Column column) {
            addCriterion(new StringBuilder("accessory_id >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andAccessoryIdLessThan(Integer value) {
            addCriterion("accessory_id <", value, "accessoryId");
            return (Criteria) this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_collect_accessory
         *
         * @mbg.generated
         */
        public Criteria andAccessoryIdLessThanColumn(LitemallCollectAccessory.Column column) {
            addCriterion(new StringBuilder("accessory_id < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andAccessoryIdLessThanOrEqualTo(Integer value) {
            addCriterion("accessory_id <=", value, "accessoryId");
            return (Criteria) this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_collect_accessory
         *
         * @mbg.generated
         */
        public Criteria andAccessoryIdLessThanOrEqualToColumn(LitemallCollectAccessory.Column column) {
            addCriterion(new StringBuilder("accessory_id <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andAccessoryIdIn(List<Integer> values) {
            addCriterion("accessory_id in", values, "accessoryId");
            return (Criteria) this;
        }

        public Criteria andAccessoryIdNotIn(List<Integer> values) {
            addCriterion("accessory_id not in", values, "accessoryId");
            return (Criteria) this;
        }

        public Criteria andAccessoryIdBetween(Integer value1, Integer value2) {
            addCriterion("accessory_id between", value1, value2, "accessoryId");
            return (Criteria) this;
        }

        public Criteria andAccessoryIdNotBetween(Integer value1, Integer value2) {
            addCriterion("accessory_id not between", value1, value2, "accessoryId");
            return (Criteria) this;
        }

        public Criteria andNumberIsNull() {
            addCriterion("`number` is null");
            return (Criteria) this;
        }

        public Criteria andNumberIsNotNull() {
            addCriterion("`number` is not null");
            return (Criteria) this;
        }

        public Criteria andNumberEqualTo(Integer value) {
            addCriterion("`number` =", value, "number");
            return (Criteria) this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_collect_accessory
         *
         * @mbg.generated
         */
        public Criteria andNumberEqualToColumn(LitemallCollectAccessory.Column column) {
            addCriterion(new StringBuilder("`number` = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andNumberNotEqualTo(Integer value) {
            addCriterion("`number` <>", value, "number");
            return (Criteria) this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_collect_accessory
         *
         * @mbg.generated
         */
        public Criteria andNumberNotEqualToColumn(LitemallCollectAccessory.Column column) {
            addCriterion(new StringBuilder("`number` <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andNumberGreaterThan(Integer value) {
            addCriterion("`number` >", value, "number");
            return (Criteria) this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_collect_accessory
         *
         * @mbg.generated
         */
        public Criteria andNumberGreaterThanColumn(LitemallCollectAccessory.Column column) {
            addCriterion(new StringBuilder("`number` > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andNumberGreaterThanOrEqualTo(Integer value) {
            addCriterion("`number` >=", value, "number");
            return (Criteria) this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_collect_accessory
         *
         * @mbg.generated
         */
        public Criteria andNumberGreaterThanOrEqualToColumn(LitemallCollectAccessory.Column column) {
            addCriterion(new StringBuilder("`number` >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andNumberLessThan(Integer value) {
            addCriterion("`number` <", value, "number");
            return (Criteria) this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_collect_accessory
         *
         * @mbg.generated
         */
        public Criteria andNumberLessThanColumn(LitemallCollectAccessory.Column column) {
            addCriterion(new StringBuilder("`number` < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andNumberLessThanOrEqualTo(Integer value) {
            addCriterion("`number` <=", value, "number");
            return (Criteria) this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_collect_accessory
         *
         * @mbg.generated
         */
        public Criteria andNumberLessThanOrEqualToColumn(LitemallCollectAccessory.Column column) {
            addCriterion(new StringBuilder("`number` <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andNumberIn(List<Integer> values) {
            addCriterion("`number` in", values, "number");
            return (Criteria) this;
        }

        public Criteria andNumberNotIn(List<Integer> values) {
            addCriterion("`number` not in", values, "number");
            return (Criteria) this;
        }

        public Criteria andNumberBetween(Integer value1, Integer value2) {
            addCriterion("`number` between", value1, value2, "number");
            return (Criteria) this;
        }

        public Criteria andNumberNotBetween(Integer value1, Integer value2) {
            addCriterion("`number` not between", value1, value2, "number");
            return (Criteria) this;
        }

        public Criteria andDeletedIsNull() {
            addCriterion("deleted is null");
            return (Criteria) this;
        }

        public Criteria andDeletedIsNotNull() {
            addCriterion("deleted is not null");
            return (Criteria) this;
        }

        public Criteria andDeletedEqualTo(Boolean value) {
            addCriterion("deleted =", value, "deleted");
            return (Criteria) this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_collect_accessory
         *
         * @mbg.generated
         */
        public Criteria andDeletedEqualToColumn(LitemallCollectAccessory.Column column) {
            addCriterion(new StringBuilder("deleted = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDeletedNotEqualTo(Boolean value) {
            addCriterion("deleted <>", value, "deleted");
            return (Criteria) this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_collect_accessory
         *
         * @mbg.generated
         */
        public Criteria andDeletedNotEqualToColumn(LitemallCollectAccessory.Column column) {
            addCriterion(new StringBuilder("deleted <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDeletedGreaterThan(Boolean value) {
            addCriterion("deleted >", value, "deleted");
            return (Criteria) this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_collect_accessory
         *
         * @mbg.generated
         */
        public Criteria andDeletedGreaterThanColumn(LitemallCollectAccessory.Column column) {
            addCriterion(new StringBuilder("deleted > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDeletedGreaterThanOrEqualTo(Boolean value) {
            addCriterion("deleted >=", value, "deleted");
            return (Criteria) this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_collect_accessory
         *
         * @mbg.generated
         */
        public Criteria andDeletedGreaterThanOrEqualToColumn(LitemallCollectAccessory.Column column) {
            addCriterion(new StringBuilder("deleted >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDeletedLessThan(Boolean value) {
            addCriterion("deleted <", value, "deleted");
            return (Criteria) this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_collect_accessory
         *
         * @mbg.generated
         */
        public Criteria andDeletedLessThanColumn(LitemallCollectAccessory.Column column) {
            addCriterion(new StringBuilder("deleted < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDeletedLessThanOrEqualTo(Boolean value) {
            addCriterion("deleted <=", value, "deleted");
            return (Criteria) this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_collect_accessory
         *
         * @mbg.generated
         */
        public Criteria andDeletedLessThanOrEqualToColumn(LitemallCollectAccessory.Column column) {
            addCriterion(new StringBuilder("deleted <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDeletedIn(List<Boolean> values) {
            addCriterion("deleted in", values, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedNotIn(List<Boolean> values) {
            addCriterion("deleted not in", values, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedBetween(Boolean value1, Boolean value2) {
            addCriterion("deleted between", value1, value2, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedNotBetween(Boolean value1, Boolean value2) {
            addCriterion("deleted not between", value1, value2, "deleted");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table litemall_collect_accessory
     *
     * @mbg.generated do_not_delete_during_merge
     */
    public static class Criteria extends GeneratedCriteria {
        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table litemall_collect_accessory
         *
         * @mbg.generated
         */
        private LitemallCollectAccessoryExample example;

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_collect_accessory
         *
         * @mbg.generated
         */
        protected Criteria(LitemallCollectAccessoryExample example) {
            super();
            this.example = example;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_collect_accessory
         *
         * @mbg.generated
         */
        public LitemallCollectAccessoryExample example() {
            return this.example;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_collect_accessory
         *
         * @mbg.generated
         */
        @Deprecated
        public Criteria andIf(boolean ifAdd, ICriteriaAdd add) {
            if (ifAdd) {
                add.add(this);
            }
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_collect_accessory
         *
         * @mbg.generated
         */
        public Criteria when(boolean condition, ICriteriaWhen then) {
            if (condition) {
                then.criteria(this);
            }
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_collect_accessory
         *
         * @mbg.generated
         */
        public Criteria when(boolean condition, ICriteriaWhen then, ICriteriaWhen otherwise) {
            if (condition) {
                then.criteria(this);
            } else {
                otherwise.criteria(this);
            }
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_collect_accessory
         *
         * @mbg.generated
         */
        public Criteria andLogicalDeleted(boolean deleted) {
            return deleted ? andDeletedEqualTo(LitemallCollectAccessory.Deleted.IS_DELETED.value()) : andDeletedNotEqualTo(LitemallCollectAccessory.Deleted.IS_DELETED.value());
        }

        @Deprecated
        public interface ICriteriaAdd {
            /**
             * This method was generated by MyBatis Generator.
             * This method corresponds to the database table litemall_collect_accessory
             *
             * @mbg.generated
             */
            Criteria add(Criteria add);
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table litemall_collect_accessory
     *
     * @mbg.generated
     */
    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }

    public interface ICriteriaWhen {
        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_collect_accessory
         *
         * @mbg.generated
         */
        void criteria(Criteria criteria);
    }

    public interface IExampleWhen {
        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_collect_accessory
         *
         * @mbg.generated
         */
        void example(org.linlinjava.litemall.db.domain.LitemallCollectAccessoryExample example);
    }
}