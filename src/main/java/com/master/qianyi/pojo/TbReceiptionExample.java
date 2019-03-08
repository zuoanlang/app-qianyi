package com.master.qianyi.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TbReceiptionExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TbReceiptionExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

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

        public Criteria andReceiptIdIsNull() {
            addCriterion("receipt_id is null");
            return (Criteria) this;
        }

        public Criteria andReceiptIdIsNotNull() {
            addCriterion("receipt_id is not null");
            return (Criteria) this;
        }

        public Criteria andReceiptIdEqualTo(String value) {
            addCriterion("receipt_id =", value, "receiptId");
            return (Criteria) this;
        }

        public Criteria andReceiptIdNotEqualTo(String value) {
            addCriterion("receipt_id <>", value, "receiptId");
            return (Criteria) this;
        }

        public Criteria andReceiptIdGreaterThan(String value) {
            addCriterion("receipt_id >", value, "receiptId");
            return (Criteria) this;
        }

        public Criteria andReceiptIdGreaterThanOrEqualTo(String value) {
            addCriterion("receipt_id >=", value, "receiptId");
            return (Criteria) this;
        }

        public Criteria andReceiptIdLessThan(String value) {
            addCriterion("receipt_id <", value, "receiptId");
            return (Criteria) this;
        }

        public Criteria andReceiptIdLessThanOrEqualTo(String value) {
            addCriterion("receipt_id <=", value, "receiptId");
            return (Criteria) this;
        }

        public Criteria andReceiptIdLike(String value) {
            addCriterion("receipt_id like", value, "receiptId");
            return (Criteria) this;
        }

        public Criteria andReceiptIdNotLike(String value) {
            addCriterion("receipt_id not like", value, "receiptId");
            return (Criteria) this;
        }

        public Criteria andReceiptIdIn(List<String> values) {
            addCriterion("receipt_id in", values, "receiptId");
            return (Criteria) this;
        }

        public Criteria andReceiptIdNotIn(List<String> values) {
            addCriterion("receipt_id not in", values, "receiptId");
            return (Criteria) this;
        }

        public Criteria andReceiptIdBetween(String value1, String value2) {
            addCriterion("receipt_id between", value1, value2, "receiptId");
            return (Criteria) this;
        }

        public Criteria andReceiptIdNotBetween(String value1, String value2) {
            addCriterion("receipt_id not between", value1, value2, "receiptId");
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

        public Criteria andUserIdEqualTo(String value) {
            addCriterion("user_id =", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotEqualTo(String value) {
            addCriterion("user_id <>", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThan(String value) {
            addCriterion("user_id >", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(String value) {
            addCriterion("user_id >=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThan(String value) {
            addCriterion("user_id <", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanOrEqualTo(String value) {
            addCriterion("user_id <=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLike(String value) {
            addCriterion("user_id like", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotLike(String value) {
            addCriterion("user_id not like", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdIn(List<String> values) {
            addCriterion("user_id in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotIn(List<String> values) {
            addCriterion("user_id not in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdBetween(String value1, String value2) {
            addCriterion("user_id between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotBetween(String value1, String value2) {
            addCriterion("user_id not between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andTradeTypeIsNull() {
            addCriterion("trade_type is null");
            return (Criteria) this;
        }

        public Criteria andTradeTypeIsNotNull() {
            addCriterion("trade_type is not null");
            return (Criteria) this;
        }

        public Criteria andTradeTypeEqualTo(String value) {
            addCriterion("trade_type =", value, "tradeType");
            return (Criteria) this;
        }

        public Criteria andTradeTypeNotEqualTo(String value) {
            addCriterion("trade_type <>", value, "tradeType");
            return (Criteria) this;
        }

        public Criteria andTradeTypeGreaterThan(String value) {
            addCriterion("trade_type >", value, "tradeType");
            return (Criteria) this;
        }

        public Criteria andTradeTypeGreaterThanOrEqualTo(String value) {
            addCriterion("trade_type >=", value, "tradeType");
            return (Criteria) this;
        }

        public Criteria andTradeTypeLessThan(String value) {
            addCriterion("trade_type <", value, "tradeType");
            return (Criteria) this;
        }

        public Criteria andTradeTypeLessThanOrEqualTo(String value) {
            addCriterion("trade_type <=", value, "tradeType");
            return (Criteria) this;
        }

        public Criteria andTradeTypeLike(String value) {
            addCriterion("trade_type like", value, "tradeType");
            return (Criteria) this;
        }

        public Criteria andTradeTypeNotLike(String value) {
            addCriterion("trade_type not like", value, "tradeType");
            return (Criteria) this;
        }

        public Criteria andTradeTypeIn(List<String> values) {
            addCriterion("trade_type in", values, "tradeType");
            return (Criteria) this;
        }

        public Criteria andTradeTypeNotIn(List<String> values) {
            addCriterion("trade_type not in", values, "tradeType");
            return (Criteria) this;
        }

        public Criteria andTradeTypeBetween(String value1, String value2) {
            addCriterion("trade_type between", value1, value2, "tradeType");
            return (Criteria) this;
        }

        public Criteria andTradeTypeNotBetween(String value1, String value2) {
            addCriterion("trade_type not between", value1, value2, "tradeType");
            return (Criteria) this;
        }

        public Criteria andTradeDateTimeIsNull() {
            addCriterion("trade_date_time is null");
            return (Criteria) this;
        }

        public Criteria andTradeDateTimeIsNotNull() {
            addCriterion("trade_date_time is not null");
            return (Criteria) this;
        }

        public Criteria andTradeDateTimeEqualTo(Date value) {
            addCriterion("trade_date_time =", value, "tradeDateTime");
            return (Criteria) this;
        }

        public Criteria andTradeDateTimeNotEqualTo(Date value) {
            addCriterion("trade_date_time <>", value, "tradeDateTime");
            return (Criteria) this;
        }

        public Criteria andTradeDateTimeGreaterThan(Date value) {
            addCriterion("trade_date_time >", value, "tradeDateTime");
            return (Criteria) this;
        }

        public Criteria andTradeDateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("trade_date_time >=", value, "tradeDateTime");
            return (Criteria) this;
        }

        public Criteria andTradeDateTimeLessThan(Date value) {
            addCriterion("trade_date_time <", value, "tradeDateTime");
            return (Criteria) this;
        }

        public Criteria andTradeDateTimeLessThanOrEqualTo(Date value) {
            addCriterion("trade_date_time <=", value, "tradeDateTime");
            return (Criteria) this;
        }

        public Criteria andTradeDateTimeIn(List<Date> values) {
            addCriterion("trade_date_time in", values, "tradeDateTime");
            return (Criteria) this;
        }

        public Criteria andTradeDateTimeNotIn(List<Date> values) {
            addCriterion("trade_date_time not in", values, "tradeDateTime");
            return (Criteria) this;
        }

        public Criteria andTradeDateTimeBetween(Date value1, Date value2) {
            addCriterion("trade_date_time between", value1, value2, "tradeDateTime");
            return (Criteria) this;
        }

        public Criteria andTradeDateTimeNotBetween(Date value1, Date value2) {
            addCriterion("trade_date_time not between", value1, value2, "tradeDateTime");
            return (Criteria) this;
        }

        public Criteria andTradeSourceIsNull() {
            addCriterion("trade_source is null");
            return (Criteria) this;
        }

        public Criteria andTradeSourceIsNotNull() {
            addCriterion("trade_source is not null");
            return (Criteria) this;
        }

        public Criteria andTradeSourceEqualTo(String value) {
            addCriterion("trade_source =", value, "tradeSource");
            return (Criteria) this;
        }

        public Criteria andTradeSourceNotEqualTo(String value) {
            addCriterion("trade_source <>", value, "tradeSource");
            return (Criteria) this;
        }

        public Criteria andTradeSourceGreaterThan(String value) {
            addCriterion("trade_source >", value, "tradeSource");
            return (Criteria) this;
        }

        public Criteria andTradeSourceGreaterThanOrEqualTo(String value) {
            addCriterion("trade_source >=", value, "tradeSource");
            return (Criteria) this;
        }

        public Criteria andTradeSourceLessThan(String value) {
            addCriterion("trade_source <", value, "tradeSource");
            return (Criteria) this;
        }

        public Criteria andTradeSourceLessThanOrEqualTo(String value) {
            addCriterion("trade_source <=", value, "tradeSource");
            return (Criteria) this;
        }

        public Criteria andTradeSourceLike(String value) {
            addCriterion("trade_source like", value, "tradeSource");
            return (Criteria) this;
        }

        public Criteria andTradeSourceNotLike(String value) {
            addCriterion("trade_source not like", value, "tradeSource");
            return (Criteria) this;
        }

        public Criteria andTradeSourceIn(List<String> values) {
            addCriterion("trade_source in", values, "tradeSource");
            return (Criteria) this;
        }

        public Criteria andTradeSourceNotIn(List<String> values) {
            addCriterion("trade_source not in", values, "tradeSource");
            return (Criteria) this;
        }

        public Criteria andTradeSourceBetween(String value1, String value2) {
            addCriterion("trade_source between", value1, value2, "tradeSource");
            return (Criteria) this;
        }

        public Criteria andTradeSourceNotBetween(String value1, String value2) {
            addCriterion("trade_source not between", value1, value2, "tradeSource");
            return (Criteria) this;
        }

        public Criteria andTradeDetailIsNull() {
            addCriterion("trade_detail is null");
            return (Criteria) this;
        }

        public Criteria andTradeDetailIsNotNull() {
            addCriterion("trade_detail is not null");
            return (Criteria) this;
        }

        public Criteria andTradeDetailEqualTo(String value) {
            addCriterion("trade_detail =", value, "tradeDetail");
            return (Criteria) this;
        }

        public Criteria andTradeDetailNotEqualTo(String value) {
            addCriterion("trade_detail <>", value, "tradeDetail");
            return (Criteria) this;
        }

        public Criteria andTradeDetailGreaterThan(String value) {
            addCriterion("trade_detail >", value, "tradeDetail");
            return (Criteria) this;
        }

        public Criteria andTradeDetailGreaterThanOrEqualTo(String value) {
            addCriterion("trade_detail >=", value, "tradeDetail");
            return (Criteria) this;
        }

        public Criteria andTradeDetailLessThan(String value) {
            addCriterion("trade_detail <", value, "tradeDetail");
            return (Criteria) this;
        }

        public Criteria andTradeDetailLessThanOrEqualTo(String value) {
            addCriterion("trade_detail <=", value, "tradeDetail");
            return (Criteria) this;
        }

        public Criteria andTradeDetailLike(String value) {
            addCriterion("trade_detail like", value, "tradeDetail");
            return (Criteria) this;
        }

        public Criteria andTradeDetailNotLike(String value) {
            addCriterion("trade_detail not like", value, "tradeDetail");
            return (Criteria) this;
        }

        public Criteria andTradeDetailIn(List<String> values) {
            addCriterion("trade_detail in", values, "tradeDetail");
            return (Criteria) this;
        }

        public Criteria andTradeDetailNotIn(List<String> values) {
            addCriterion("trade_detail not in", values, "tradeDetail");
            return (Criteria) this;
        }

        public Criteria andTradeDetailBetween(String value1, String value2) {
            addCriterion("trade_detail between", value1, value2, "tradeDetail");
            return (Criteria) this;
        }

        public Criteria andTradeDetailNotBetween(String value1, String value2) {
            addCriterion("trade_detail not between", value1, value2, "tradeDetail");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagIsNull() {
            addCriterion("delete_flag is null");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagIsNotNull() {
            addCriterion("delete_flag is not null");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagEqualTo(String value) {
            addCriterion("delete_flag =", value, "deleteFlag");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagNotEqualTo(String value) {
            addCriterion("delete_flag <>", value, "deleteFlag");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagGreaterThan(String value) {
            addCriterion("delete_flag >", value, "deleteFlag");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagGreaterThanOrEqualTo(String value) {
            addCriterion("delete_flag >=", value, "deleteFlag");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagLessThan(String value) {
            addCriterion("delete_flag <", value, "deleteFlag");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagLessThanOrEqualTo(String value) {
            addCriterion("delete_flag <=", value, "deleteFlag");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagLike(String value) {
            addCriterion("delete_flag like", value, "deleteFlag");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagNotLike(String value) {
            addCriterion("delete_flag not like", value, "deleteFlag");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagIn(List<String> values) {
            addCriterion("delete_flag in", values, "deleteFlag");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagNotIn(List<String> values) {
            addCriterion("delete_flag not in", values, "deleteFlag");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagBetween(String value1, String value2) {
            addCriterion("delete_flag between", value1, value2, "deleteFlag");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagNotBetween(String value1, String value2) {
            addCriterion("delete_flag not between", value1, value2, "deleteFlag");
            return (Criteria) this;
        }

        public Criteria andEffectFlagIsNull() {
            addCriterion("effect_flag is null");
            return (Criteria) this;
        }

        public Criteria andEffectFlagIsNotNull() {
            addCriterion("effect_flag is not null");
            return (Criteria) this;
        }

        public Criteria andEffectFlagEqualTo(String value) {
            addCriterion("effect_flag =", value, "effectFlag");
            return (Criteria) this;
        }

        public Criteria andEffectFlagNotEqualTo(String value) {
            addCriterion("effect_flag <>", value, "effectFlag");
            return (Criteria) this;
        }

        public Criteria andEffectFlagGreaterThan(String value) {
            addCriterion("effect_flag >", value, "effectFlag");
            return (Criteria) this;
        }

        public Criteria andEffectFlagGreaterThanOrEqualTo(String value) {
            addCriterion("effect_flag >=", value, "effectFlag");
            return (Criteria) this;
        }

        public Criteria andEffectFlagLessThan(String value) {
            addCriterion("effect_flag <", value, "effectFlag");
            return (Criteria) this;
        }

        public Criteria andEffectFlagLessThanOrEqualTo(String value) {
            addCriterion("effect_flag <=", value, "effectFlag");
            return (Criteria) this;
        }

        public Criteria andEffectFlagLike(String value) {
            addCriterion("effect_flag like", value, "effectFlag");
            return (Criteria) this;
        }

        public Criteria andEffectFlagNotLike(String value) {
            addCriterion("effect_flag not like", value, "effectFlag");
            return (Criteria) this;
        }

        public Criteria andEffectFlagIn(List<String> values) {
            addCriterion("effect_flag in", values, "effectFlag");
            return (Criteria) this;
        }

        public Criteria andEffectFlagNotIn(List<String> values) {
            addCriterion("effect_flag not in", values, "effectFlag");
            return (Criteria) this;
        }

        public Criteria andEffectFlagBetween(String value1, String value2) {
            addCriterion("effect_flag between", value1, value2, "effectFlag");
            return (Criteria) this;
        }

        public Criteria andEffectFlagNotBetween(String value1, String value2) {
            addCriterion("effect_flag not between", value1, value2, "effectFlag");
            return (Criteria) this;
        }

        public Criteria andRemark1IsNull() {
            addCriterion("remark1 is null");
            return (Criteria) this;
        }

        public Criteria andRemark1IsNotNull() {
            addCriterion("remark1 is not null");
            return (Criteria) this;
        }

        public Criteria andRemark1EqualTo(String value) {
            addCriterion("remark1 =", value, "remark1");
            return (Criteria) this;
        }

        public Criteria andRemark1NotEqualTo(String value) {
            addCriterion("remark1 <>", value, "remark1");
            return (Criteria) this;
        }

        public Criteria andRemark1GreaterThan(String value) {
            addCriterion("remark1 >", value, "remark1");
            return (Criteria) this;
        }

        public Criteria andRemark1GreaterThanOrEqualTo(String value) {
            addCriterion("remark1 >=", value, "remark1");
            return (Criteria) this;
        }

        public Criteria andRemark1LessThan(String value) {
            addCriterion("remark1 <", value, "remark1");
            return (Criteria) this;
        }

        public Criteria andRemark1LessThanOrEqualTo(String value) {
            addCriterion("remark1 <=", value, "remark1");
            return (Criteria) this;
        }

        public Criteria andRemark1Like(String value) {
            addCriterion("remark1 like", value, "remark1");
            return (Criteria) this;
        }

        public Criteria andRemark1NotLike(String value) {
            addCriterion("remark1 not like", value, "remark1");
            return (Criteria) this;
        }

        public Criteria andRemark1In(List<String> values) {
            addCriterion("remark1 in", values, "remark1");
            return (Criteria) this;
        }

        public Criteria andRemark1NotIn(List<String> values) {
            addCriterion("remark1 not in", values, "remark1");
            return (Criteria) this;
        }

        public Criteria andRemark1Between(String value1, String value2) {
            addCriterion("remark1 between", value1, value2, "remark1");
            return (Criteria) this;
        }

        public Criteria andRemark1NotBetween(String value1, String value2) {
            addCriterion("remark1 not between", value1, value2, "remark1");
            return (Criteria) this;
        }

        public Criteria andRemark5IsNull() {
            addCriterion("remark5 is null");
            return (Criteria) this;
        }

        public Criteria andRemark5IsNotNull() {
            addCriterion("remark5 is not null");
            return (Criteria) this;
        }

        public Criteria andRemark5EqualTo(String value) {
            addCriterion("remark5 =", value, "remark5");
            return (Criteria) this;
        }

        public Criteria andRemark5NotEqualTo(String value) {
            addCriterion("remark5 <>", value, "remark5");
            return (Criteria) this;
        }

        public Criteria andRemark5GreaterThan(String value) {
            addCriterion("remark5 >", value, "remark5");
            return (Criteria) this;
        }

        public Criteria andRemark5GreaterThanOrEqualTo(String value) {
            addCriterion("remark5 >=", value, "remark5");
            return (Criteria) this;
        }

        public Criteria andRemark5LessThan(String value) {
            addCriterion("remark5 <", value, "remark5");
            return (Criteria) this;
        }

        public Criteria andRemark5LessThanOrEqualTo(String value) {
            addCriterion("remark5 <=", value, "remark5");
            return (Criteria) this;
        }

        public Criteria andRemark5Like(String value) {
            addCriterion("remark5 like", value, "remark5");
            return (Criteria) this;
        }

        public Criteria andRemark5NotLike(String value) {
            addCriterion("remark5 not like", value, "remark5");
            return (Criteria) this;
        }

        public Criteria andRemark5In(List<String> values) {
            addCriterion("remark5 in", values, "remark5");
            return (Criteria) this;
        }

        public Criteria andRemark5NotIn(List<String> values) {
            addCriterion("remark5 not in", values, "remark5");
            return (Criteria) this;
        }

        public Criteria andRemark5Between(String value1, String value2) {
            addCriterion("remark5 between", value1, value2, "remark5");
            return (Criteria) this;
        }

        public Criteria andRemark5NotBetween(String value1, String value2) {
            addCriterion("remark5 not between", value1, value2, "remark5");
            return (Criteria) this;
        }

        public Criteria andRemark3IsNull() {
            addCriterion("remark3 is null");
            return (Criteria) this;
        }

        public Criteria andRemark3IsNotNull() {
            addCriterion("remark3 is not null");
            return (Criteria) this;
        }

        public Criteria andRemark3EqualTo(String value) {
            addCriterion("remark3 =", value, "remark3");
            return (Criteria) this;
        }

        public Criteria andRemark3NotEqualTo(String value) {
            addCriterion("remark3 <>", value, "remark3");
            return (Criteria) this;
        }

        public Criteria andRemark3GreaterThan(String value) {
            addCriterion("remark3 >", value, "remark3");
            return (Criteria) this;
        }

        public Criteria andRemark3GreaterThanOrEqualTo(String value) {
            addCriterion("remark3 >=", value, "remark3");
            return (Criteria) this;
        }

        public Criteria andRemark3LessThan(String value) {
            addCriterion("remark3 <", value, "remark3");
            return (Criteria) this;
        }

        public Criteria andRemark3LessThanOrEqualTo(String value) {
            addCriterion("remark3 <=", value, "remark3");
            return (Criteria) this;
        }

        public Criteria andRemark3Like(String value) {
            addCriterion("remark3 like", value, "remark3");
            return (Criteria) this;
        }

        public Criteria andRemark3NotLike(String value) {
            addCriterion("remark3 not like", value, "remark3");
            return (Criteria) this;
        }

        public Criteria andRemark3In(List<String> values) {
            addCriterion("remark3 in", values, "remark3");
            return (Criteria) this;
        }

        public Criteria andRemark3NotIn(List<String> values) {
            addCriterion("remark3 not in", values, "remark3");
            return (Criteria) this;
        }

        public Criteria andRemark3Between(String value1, String value2) {
            addCriterion("remark3 between", value1, value2, "remark3");
            return (Criteria) this;
        }

        public Criteria andRemark3NotBetween(String value1, String value2) {
            addCriterion("remark3 not between", value1, value2, "remark3");
            return (Criteria) this;
        }

        public Criteria andRemark2IsNull() {
            addCriterion("remark2 is null");
            return (Criteria) this;
        }

        public Criteria andRemark2IsNotNull() {
            addCriterion("remark2 is not null");
            return (Criteria) this;
        }

        public Criteria andRemark2EqualTo(String value) {
            addCriterion("remark2 =", value, "remark2");
            return (Criteria) this;
        }

        public Criteria andRemark2NotEqualTo(String value) {
            addCriterion("remark2 <>", value, "remark2");
            return (Criteria) this;
        }

        public Criteria andRemark2GreaterThan(String value) {
            addCriterion("remark2 >", value, "remark2");
            return (Criteria) this;
        }

        public Criteria andRemark2GreaterThanOrEqualTo(String value) {
            addCriterion("remark2 >=", value, "remark2");
            return (Criteria) this;
        }

        public Criteria andRemark2LessThan(String value) {
            addCriterion("remark2 <", value, "remark2");
            return (Criteria) this;
        }

        public Criteria andRemark2LessThanOrEqualTo(String value) {
            addCriterion("remark2 <=", value, "remark2");
            return (Criteria) this;
        }

        public Criteria andRemark2Like(String value) {
            addCriterion("remark2 like", value, "remark2");
            return (Criteria) this;
        }

        public Criteria andRemark2NotLike(String value) {
            addCriterion("remark2 not like", value, "remark2");
            return (Criteria) this;
        }

        public Criteria andRemark2In(List<String> values) {
            addCriterion("remark2 in", values, "remark2");
            return (Criteria) this;
        }

        public Criteria andRemark2NotIn(List<String> values) {
            addCriterion("remark2 not in", values, "remark2");
            return (Criteria) this;
        }

        public Criteria andRemark2Between(String value1, String value2) {
            addCriterion("remark2 between", value1, value2, "remark2");
            return (Criteria) this;
        }

        public Criteria andRemark2NotBetween(String value1, String value2) {
            addCriterion("remark2 not between", value1, value2, "remark2");
            return (Criteria) this;
        }

        public Criteria andRemark4IsNull() {
            addCriterion("remark4 is null");
            return (Criteria) this;
        }

        public Criteria andRemark4IsNotNull() {
            addCriterion("remark4 is not null");
            return (Criteria) this;
        }

        public Criteria andRemark4EqualTo(String value) {
            addCriterion("remark4 =", value, "remark4");
            return (Criteria) this;
        }

        public Criteria andRemark4NotEqualTo(String value) {
            addCriterion("remark4 <>", value, "remark4");
            return (Criteria) this;
        }

        public Criteria andRemark4GreaterThan(String value) {
            addCriterion("remark4 >", value, "remark4");
            return (Criteria) this;
        }

        public Criteria andRemark4GreaterThanOrEqualTo(String value) {
            addCriterion("remark4 >=", value, "remark4");
            return (Criteria) this;
        }

        public Criteria andRemark4LessThan(String value) {
            addCriterion("remark4 <", value, "remark4");
            return (Criteria) this;
        }

        public Criteria andRemark4LessThanOrEqualTo(String value) {
            addCriterion("remark4 <=", value, "remark4");
            return (Criteria) this;
        }

        public Criteria andRemark4Like(String value) {
            addCriterion("remark4 like", value, "remark4");
            return (Criteria) this;
        }

        public Criteria andRemark4NotLike(String value) {
            addCriterion("remark4 not like", value, "remark4");
            return (Criteria) this;
        }

        public Criteria andRemark4In(List<String> values) {
            addCriterion("remark4 in", values, "remark4");
            return (Criteria) this;
        }

        public Criteria andRemark4NotIn(List<String> values) {
            addCriterion("remark4 not in", values, "remark4");
            return (Criteria) this;
        }

        public Criteria andRemark4Between(String value1, String value2) {
            addCriterion("remark4 between", value1, value2, "remark4");
            return (Criteria) this;
        }

        public Criteria andRemark4NotBetween(String value1, String value2) {
            addCriterion("remark4 not between", value1, value2, "remark4");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

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
}