package org.linlinjava.litemall.db.domain;

import java.util.ArrayList;
import java.util.List;

public class LitemallCollectExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table litemall_collect
     *
     * @mbg.generated
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table litemall_collect
     *
     * @mbg.generated
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table litemall_collect
     *
     * @mbg.generated
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_collect
     *
     * @mbg.generated
     */
    public LitemallCollectExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_collect
     *
     * @mbg.generated
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_collect
     *
     * @mbg.generated
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_collect
     *
     * @mbg.generated
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_collect
     *
     * @mbg.generated
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_collect
     *
     * @mbg.generated
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_collect
     *
     * @mbg.generated
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_collect
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
     * This method corresponds to the database table litemall_collect
     *
     * @mbg.generated
     */
    public LitemallCollectExample orderBy(String orderByClause) {
        this.setOrderByClause(orderByClause);
        return this;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_collect
     *
     * @mbg.generated
     */
    public LitemallCollectExample orderBy(String ... orderByClauses) {
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
     * This method corresponds to the database table litemall_collect
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
     * This method corresponds to the database table litemall_collect
     *
     * @mbg.generated
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria(this);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_collect
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
     * This method corresponds to the database table litemall_collect
     *
     * @mbg.generated
     */
    public static Criteria newAndCreateCriteria() {
        LitemallCollectExample example = new LitemallCollectExample();
        return example.createCriteria();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_collect
     *
     * @mbg.generated
     */
    public LitemallCollectExample when(boolean condition, IExampleWhen then) {
        if (condition) {
            then.example(this);
        }
        return this;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_collect
     *
     * @mbg.generated
     */
    public LitemallCollectExample when(boolean condition, IExampleWhen then, IExampleWhen otherwise) {
        if (condition) {
            then.example(this);
        } else {
            otherwise.example(this);
        }
        return this;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table litemall_collect
     *
     * @mbg.generated
     */
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> specificationIdsCriteria;

        protected List<Criterion> allCriteria;

        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
            specificationIdsCriteria = new ArrayList<Criterion>();
        }

        public List<Criterion> getSpecificationIdsCriteria() {
            return specificationIdsCriteria;
        }

        protected void addSpecificationIdsCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            specificationIdsCriteria.add(new Criterion(condition, value, "org.linlinjava.litemall.db.mybatis.JsonIntegerArrayTypeHandler"));
            allCriteria = null;
        }

        protected void addSpecificationIdsCriterion(String condition, Integer[] value1, Integer[] value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            specificationIdsCriteria.add(new Criterion(condition, value1, value2, "org.linlinjava.litemall.db.mybatis.JsonIntegerArrayTypeHandler"));
            allCriteria = null;
        }

        public boolean isValid() {
            return criteria.size() > 0
                || specificationIdsCriteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            if (allCriteria == null) {
                allCriteria = new ArrayList<Criterion>();
                allCriteria.addAll(criteria);
                allCriteria.addAll(specificationIdsCriteria);
            }
            return allCriteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
            allCriteria = null;
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
            allCriteria = null;
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
            allCriteria = null;
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
         * This method corresponds to the database table litemall_collect
         *
         * @mbg.generated
         */
        public Criteria andIdEqualToColumn(LitemallCollect.Column column) {
            addCriterion(new StringBuilder("id = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_collect
         *
         * @mbg.generated
         */
        public Criteria andIdNotEqualToColumn(LitemallCollect.Column column) {
            addCriterion(new StringBuilder("id <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_collect
         *
         * @mbg.generated
         */
        public Criteria andIdGreaterThanColumn(LitemallCollect.Column column) {
            addCriterion(new StringBuilder("id > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_collect
         *
         * @mbg.generated
         */
        public Criteria andIdGreaterThanOrEqualToColumn(LitemallCollect.Column column) {
            addCriterion(new StringBuilder("id >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_collect
         *
         * @mbg.generated
         */
        public Criteria andIdLessThanColumn(LitemallCollect.Column column) {
            addCriterion(new StringBuilder("id < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_collect
         *
         * @mbg.generated
         */
        public Criteria andIdLessThanOrEqualToColumn(LitemallCollect.Column column) {
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

        public Criteria andUserIdIsNull() {
            addCriterion("user_id is null");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNotNull() {
            addCriterion("user_id is not null");
            return (Criteria) this;
        }

        public Criteria andUserIdEqualTo(Integer value) {
            addCriterion("user_id =", value, "userId");
            return (Criteria) this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_collect
         *
         * @mbg.generated
         */
        public Criteria andUserIdEqualToColumn(LitemallCollect.Column column) {
            addCriterion(new StringBuilder("user_id = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andUserIdNotEqualTo(Integer value) {
            addCriterion("user_id <>", value, "userId");
            return (Criteria) this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_collect
         *
         * @mbg.generated
         */
        public Criteria andUserIdNotEqualToColumn(LitemallCollect.Column column) {
            addCriterion(new StringBuilder("user_id <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThan(Integer value) {
            addCriterion("user_id >", value, "userId");
            return (Criteria) this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_collect
         *
         * @mbg.generated
         */
        public Criteria andUserIdGreaterThanColumn(LitemallCollect.Column column) {
            addCriterion(new StringBuilder("user_id > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("user_id >=", value, "userId");
            return (Criteria) this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_collect
         *
         * @mbg.generated
         */
        public Criteria andUserIdGreaterThanOrEqualToColumn(LitemallCollect.Column column) {
            addCriterion(new StringBuilder("user_id >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andUserIdLessThan(Integer value) {
            addCriterion("user_id <", value, "userId");
            return (Criteria) this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_collect
         *
         * @mbg.generated
         */
        public Criteria andUserIdLessThanColumn(LitemallCollect.Column column) {
            addCriterion(new StringBuilder("user_id < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("user_id <=", value, "userId");
            return (Criteria) this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_collect
         *
         * @mbg.generated
         */
        public Criteria andUserIdLessThanOrEqualToColumn(LitemallCollect.Column column) {
            addCriterion(new StringBuilder("user_id <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andUserIdIn(List<Integer> values) {
            addCriterion("user_id in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotIn(List<Integer> values) {
            addCriterion("user_id not in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdBetween(Integer value1, Integer value2) {
            addCriterion("user_id between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("user_id not between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andGoodsIdIsNull() {
            addCriterion("goods_id is null");
            return (Criteria) this;
        }

        public Criteria andGoodsIdIsNotNull() {
            addCriterion("goods_id is not null");
            return (Criteria) this;
        }

        public Criteria andGoodsIdEqualTo(Integer value) {
            addCriterion("goods_id =", value, "goodsId");
            return (Criteria) this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_collect
         *
         * @mbg.generated
         */
        public Criteria andGoodsIdEqualToColumn(LitemallCollect.Column column) {
            addCriterion(new StringBuilder("goods_id = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andGoodsIdNotEqualTo(Integer value) {
            addCriterion("goods_id <>", value, "goodsId");
            return (Criteria) this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_collect
         *
         * @mbg.generated
         */
        public Criteria andGoodsIdNotEqualToColumn(LitemallCollect.Column column) {
            addCriterion(new StringBuilder("goods_id <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andGoodsIdGreaterThan(Integer value) {
            addCriterion("goods_id >", value, "goodsId");
            return (Criteria) this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_collect
         *
         * @mbg.generated
         */
        public Criteria andGoodsIdGreaterThanColumn(LitemallCollect.Column column) {
            addCriterion(new StringBuilder("goods_id > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andGoodsIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("goods_id >=", value, "goodsId");
            return (Criteria) this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_collect
         *
         * @mbg.generated
         */
        public Criteria andGoodsIdGreaterThanOrEqualToColumn(LitemallCollect.Column column) {
            addCriterion(new StringBuilder("goods_id >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andGoodsIdLessThan(Integer value) {
            addCriterion("goods_id <", value, "goodsId");
            return (Criteria) this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_collect
         *
         * @mbg.generated
         */
        public Criteria andGoodsIdLessThanColumn(LitemallCollect.Column column) {
            addCriterion(new StringBuilder("goods_id < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andGoodsIdLessThanOrEqualTo(Integer value) {
            addCriterion("goods_id <=", value, "goodsId");
            return (Criteria) this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_collect
         *
         * @mbg.generated
         */
        public Criteria andGoodsIdLessThanOrEqualToColumn(LitemallCollect.Column column) {
            addCriterion(new StringBuilder("goods_id <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andGoodsIdIn(List<Integer> values) {
            addCriterion("goods_id in", values, "goodsId");
            return (Criteria) this;
        }

        public Criteria andGoodsIdNotIn(List<Integer> values) {
            addCriterion("goods_id not in", values, "goodsId");
            return (Criteria) this;
        }

        public Criteria andGoodsIdBetween(Integer value1, Integer value2) {
            addCriterion("goods_id between", value1, value2, "goodsId");
            return (Criteria) this;
        }

        public Criteria andGoodsIdNotBetween(Integer value1, Integer value2) {
            addCriterion("goods_id not between", value1, value2, "goodsId");
            return (Criteria) this;
        }

        public Criteria andSpecificationIdsIsNull() {
            addCriterion("specification_ids is null");
            return (Criteria) this;
        }

        public Criteria andSpecificationIdsIsNotNull() {
            addCriterion("specification_ids is not null");
            return (Criteria) this;
        }

        public Criteria andSpecificationIdsEqualTo(Integer[] value) {
            addSpecificationIdsCriterion("specification_ids =", value, "specificationIds");
            return (Criteria) this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_collect
         *
         * @mbg.generated
         */
        public Criteria andSpecificationIdsEqualToColumn(LitemallCollect.Column column) {
            addCriterion(new StringBuilder("specification_ids = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andSpecificationIdsNotEqualTo(Integer[] value) {
            addSpecificationIdsCriterion("specification_ids <>", value, "specificationIds");
            return (Criteria) this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_collect
         *
         * @mbg.generated
         */
        public Criteria andSpecificationIdsNotEqualToColumn(LitemallCollect.Column column) {
            addCriterion(new StringBuilder("specification_ids <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andSpecificationIdsGreaterThan(Integer[] value) {
            addSpecificationIdsCriterion("specification_ids >", value, "specificationIds");
            return (Criteria) this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_collect
         *
         * @mbg.generated
         */
        public Criteria andSpecificationIdsGreaterThanColumn(LitemallCollect.Column column) {
            addCriterion(new StringBuilder("specification_ids > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andSpecificationIdsGreaterThanOrEqualTo(Integer[] value) {
            addSpecificationIdsCriterion("specification_ids >=", value, "specificationIds");
            return (Criteria) this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_collect
         *
         * @mbg.generated
         */
        public Criteria andSpecificationIdsGreaterThanOrEqualToColumn(LitemallCollect.Column column) {
            addCriterion(new StringBuilder("specification_ids >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andSpecificationIdsLessThan(Integer[] value) {
            addSpecificationIdsCriterion("specification_ids <", value, "specificationIds");
            return (Criteria) this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_collect
         *
         * @mbg.generated
         */
        public Criteria andSpecificationIdsLessThanColumn(LitemallCollect.Column column) {
            addCriterion(new StringBuilder("specification_ids < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andSpecificationIdsLessThanOrEqualTo(Integer[] value) {
            addSpecificationIdsCriterion("specification_ids <=", value, "specificationIds");
            return (Criteria) this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_collect
         *
         * @mbg.generated
         */
        public Criteria andSpecificationIdsLessThanOrEqualToColumn(LitemallCollect.Column column) {
            addCriterion(new StringBuilder("specification_ids <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andSpecificationIdsLike(Integer[] value) {
            addSpecificationIdsCriterion("specification_ids like", value, "specificationIds");
            return (Criteria) this;
        }

        public Criteria andSpecificationIdsNotLike(Integer[] value) {
            addSpecificationIdsCriterion("specification_ids not like", value, "specificationIds");
            return (Criteria) this;
        }

        public Criteria andSpecificationIdsIn(List<Integer[]> values) {
            addSpecificationIdsCriterion("specification_ids in", values, "specificationIds");
            return (Criteria) this;
        }

        public Criteria andSpecificationIdsNotIn(List<Integer[]> values) {
            addSpecificationIdsCriterion("specification_ids not in", values, "specificationIds");
            return (Criteria) this;
        }

        public Criteria andSpecificationIdsBetween(Integer[] value1, Integer[] value2) {
            addSpecificationIdsCriterion("specification_ids between", value1, value2, "specificationIds");
            return (Criteria) this;
        }

        public Criteria andSpecificationIdsNotBetween(Integer[] value1, Integer[] value2) {
            addSpecificationIdsCriterion("specification_ids not between", value1, value2, "specificationIds");
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
         * This method corresponds to the database table litemall_collect
         *
         * @mbg.generated
         */
        public Criteria andDeletedEqualToColumn(LitemallCollect.Column column) {
            addCriterion(new StringBuilder("deleted = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDeletedNotEqualTo(Boolean value) {
            addCriterion("deleted <>", value, "deleted");
            return (Criteria) this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_collect
         *
         * @mbg.generated
         */
        public Criteria andDeletedNotEqualToColumn(LitemallCollect.Column column) {
            addCriterion(new StringBuilder("deleted <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDeletedGreaterThan(Boolean value) {
            addCriterion("deleted >", value, "deleted");
            return (Criteria) this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_collect
         *
         * @mbg.generated
         */
        public Criteria andDeletedGreaterThanColumn(LitemallCollect.Column column) {
            addCriterion(new StringBuilder("deleted > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDeletedGreaterThanOrEqualTo(Boolean value) {
            addCriterion("deleted >=", value, "deleted");
            return (Criteria) this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_collect
         *
         * @mbg.generated
         */
        public Criteria andDeletedGreaterThanOrEqualToColumn(LitemallCollect.Column column) {
            addCriterion(new StringBuilder("deleted >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDeletedLessThan(Boolean value) {
            addCriterion("deleted <", value, "deleted");
            return (Criteria) this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_collect
         *
         * @mbg.generated
         */
        public Criteria andDeletedLessThanColumn(LitemallCollect.Column column) {
            addCriterion(new StringBuilder("deleted < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDeletedLessThanOrEqualTo(Boolean value) {
            addCriterion("deleted <=", value, "deleted");
            return (Criteria) this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_collect
         *
         * @mbg.generated
         */
        public Criteria andDeletedLessThanOrEqualToColumn(LitemallCollect.Column column) {
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
     * This class corresponds to the database table litemall_collect
     *
     * @mbg.generated do_not_delete_during_merge
     */
    public static class Criteria extends GeneratedCriteria {
        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table litemall_collect
         *
         * @mbg.generated
         */
        private LitemallCollectExample example;

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_collect
         *
         * @mbg.generated
         */
        protected Criteria(LitemallCollectExample example) {
            super();
            this.example = example;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_collect
         *
         * @mbg.generated
         */
        public LitemallCollectExample example() {
            return this.example;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_collect
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
         * This method corresponds to the database table litemall_collect
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
         * This method corresponds to the database table litemall_collect
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
         * This method corresponds to the database table litemall_collect
         *
         * @mbg.generated
         */
        public Criteria andLogicalDeleted(boolean deleted) {
            return deleted ? andDeletedEqualTo(LitemallCollect.Deleted.IS_DELETED.value()) : andDeletedNotEqualTo(LitemallCollect.Deleted.IS_DELETED.value());
        }

        @Deprecated
        public interface ICriteriaAdd {
            /**
             * This method was generated by MyBatis Generator.
             * This method corresponds to the database table litemall_collect
             *
             * @mbg.generated
             */
            Criteria add(Criteria add);
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table litemall_collect
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
         * This method corresponds to the database table litemall_collect
         *
         * @mbg.generated
         */
        void criteria(Criteria criteria);
    }

    public interface IExampleWhen {
        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_collect
         *
         * @mbg.generated
         */
        void example(org.linlinjava.litemall.db.domain.LitemallCollectExample example);
    }
}