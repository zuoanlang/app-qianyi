package com.master.qianyi.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TbCourseExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TbCourseExample() {
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

        public Criteria andCourseIdIsNull() {
            addCriterion("course_id is null");
            return (Criteria) this;
        }

        public Criteria andCourseIdIsNotNull() {
            addCriterion("course_id is not null");
            return (Criteria) this;
        }

        public Criteria andCourseIdEqualTo(String value) {
            addCriterion("course_id =", value, "courseId");
            return (Criteria) this;
        }

        public Criteria andCourseIdNotEqualTo(String value) {
            addCriterion("course_id <>", value, "courseId");
            return (Criteria) this;
        }

        public Criteria andCourseIdGreaterThan(String value) {
            addCriterion("course_id >", value, "courseId");
            return (Criteria) this;
        }

        public Criteria andCourseIdGreaterThanOrEqualTo(String value) {
            addCriterion("course_id >=", value, "courseId");
            return (Criteria) this;
        }

        public Criteria andCourseIdLessThan(String value) {
            addCriterion("course_id <", value, "courseId");
            return (Criteria) this;
        }

        public Criteria andCourseIdLessThanOrEqualTo(String value) {
            addCriterion("course_id <=", value, "courseId");
            return (Criteria) this;
        }

        public Criteria andCourseIdLike(String value) {
            addCriterion("course_id like", value, "courseId");
            return (Criteria) this;
        }

        public Criteria andCourseIdNotLike(String value) {
            addCriterion("course_id not like", value, "courseId");
            return (Criteria) this;
        }

        public Criteria andCourseIdIn(List<String> values) {
            addCriterion("course_id in", values, "courseId");
            return (Criteria) this;
        }

        public Criteria andCourseIdNotIn(List<String> values) {
            addCriterion("course_id not in", values, "courseId");
            return (Criteria) this;
        }

        public Criteria andCourseIdBetween(String value1, String value2) {
            addCriterion("course_id between", value1, value2, "courseId");
            return (Criteria) this;
        }

        public Criteria andCourseIdNotBetween(String value1, String value2) {
            addCriterion("course_id not between", value1, value2, "courseId");
            return (Criteria) this;
        }

        public Criteria andCourseNameIsNull() {
            addCriterion("course_name is null");
            return (Criteria) this;
        }

        public Criteria andCourseNameIsNotNull() {
            addCriterion("course_name is not null");
            return (Criteria) this;
        }

        public Criteria andCourseNameEqualTo(String value) {
            addCriterion("course_name =", value, "courseName");
            return (Criteria) this;
        }

        public Criteria andCourseNameNotEqualTo(String value) {
            addCriterion("course_name <>", value, "courseName");
            return (Criteria) this;
        }

        public Criteria andCourseNameGreaterThan(String value) {
            addCriterion("course_name >", value, "courseName");
            return (Criteria) this;
        }

        public Criteria andCourseNameGreaterThanOrEqualTo(String value) {
            addCriterion("course_name >=", value, "courseName");
            return (Criteria) this;
        }

        public Criteria andCourseNameLessThan(String value) {
            addCriterion("course_name <", value, "courseName");
            return (Criteria) this;
        }

        public Criteria andCourseNameLessThanOrEqualTo(String value) {
            addCriterion("course_name <=", value, "courseName");
            return (Criteria) this;
        }

        public Criteria andCourseNameLike(String value) {
            addCriterion("course_name like", value, "courseName");
            return (Criteria) this;
        }

        public Criteria andCourseNameNotLike(String value) {
            addCriterion("course_name not like", value, "courseName");
            return (Criteria) this;
        }

        public Criteria andCourseNameIn(List<String> values) {
            addCriterion("course_name in", values, "courseName");
            return (Criteria) this;
        }

        public Criteria andCourseNameNotIn(List<String> values) {
            addCriterion("course_name not in", values, "courseName");
            return (Criteria) this;
        }

        public Criteria andCourseNameBetween(String value1, String value2) {
            addCriterion("course_name between", value1, value2, "courseName");
            return (Criteria) this;
        }

        public Criteria andCourseNameNotBetween(String value1, String value2) {
            addCriterion("course_name not between", value1, value2, "courseName");
            return (Criteria) this;
        }

        public Criteria andCourseTypeIsNull() {
            addCriterion("course_type is null");
            return (Criteria) this;
        }

        public Criteria andCourseTypeIsNotNull() {
            addCriterion("course_type is not null");
            return (Criteria) this;
        }

        public Criteria andCourseTypeEqualTo(String value) {
            addCriterion("course_type =", value, "courseType");
            return (Criteria) this;
        }

        public Criteria andCourseTypeNotEqualTo(String value) {
            addCriterion("course_type <>", value, "courseType");
            return (Criteria) this;
        }

        public Criteria andCourseTypeGreaterThan(String value) {
            addCriterion("course_type >", value, "courseType");
            return (Criteria) this;
        }

        public Criteria andCourseTypeGreaterThanOrEqualTo(String value) {
            addCriterion("course_type >=", value, "courseType");
            return (Criteria) this;
        }

        public Criteria andCourseTypeLessThan(String value) {
            addCriterion("course_type <", value, "courseType");
            return (Criteria) this;
        }

        public Criteria andCourseTypeLessThanOrEqualTo(String value) {
            addCriterion("course_type <=", value, "courseType");
            return (Criteria) this;
        }

        public Criteria andCourseTypeLike(String value) {
            addCriterion("course_type like", value, "courseType");
            return (Criteria) this;
        }

        public Criteria andCourseTypeNotLike(String value) {
            addCriterion("course_type not like", value, "courseType");
            return (Criteria) this;
        }

        public Criteria andCourseTypeIn(List<String> values) {
            addCriterion("course_type in", values, "courseType");
            return (Criteria) this;
        }

        public Criteria andCourseTypeNotIn(List<String> values) {
            addCriterion("course_type not in", values, "courseType");
            return (Criteria) this;
        }

        public Criteria andCourseTypeBetween(String value1, String value2) {
            addCriterion("course_type between", value1, value2, "courseType");
            return (Criteria) this;
        }

        public Criteria andCourseTypeNotBetween(String value1, String value2) {
            addCriterion("course_type not between", value1, value2, "courseType");
            return (Criteria) this;
        }

        public Criteria andCourseLevelIsNull() {
            addCriterion("course_level is null");
            return (Criteria) this;
        }

        public Criteria andCourseLevelIsNotNull() {
            addCriterion("course_level is not null");
            return (Criteria) this;
        }

        public Criteria andCourseLevelEqualTo(String value) {
            addCriterion("course_level =", value, "courseLevel");
            return (Criteria) this;
        }

        public Criteria andCourseLevelNotEqualTo(String value) {
            addCriterion("course_level <>", value, "courseLevel");
            return (Criteria) this;
        }

        public Criteria andCourseLevelGreaterThan(String value) {
            addCriterion("course_level >", value, "courseLevel");
            return (Criteria) this;
        }

        public Criteria andCourseLevelGreaterThanOrEqualTo(String value) {
            addCriterion("course_level >=", value, "courseLevel");
            return (Criteria) this;
        }

        public Criteria andCourseLevelLessThan(String value) {
            addCriterion("course_level <", value, "courseLevel");
            return (Criteria) this;
        }

        public Criteria andCourseLevelLessThanOrEqualTo(String value) {
            addCriterion("course_level <=", value, "courseLevel");
            return (Criteria) this;
        }

        public Criteria andCourseLevelLike(String value) {
            addCriterion("course_level like", value, "courseLevel");
            return (Criteria) this;
        }

        public Criteria andCourseLevelNotLike(String value) {
            addCriterion("course_level not like", value, "courseLevel");
            return (Criteria) this;
        }

        public Criteria andCourseLevelIn(List<String> values) {
            addCriterion("course_level in", values, "courseLevel");
            return (Criteria) this;
        }

        public Criteria andCourseLevelNotIn(List<String> values) {
            addCriterion("course_level not in", values, "courseLevel");
            return (Criteria) this;
        }

        public Criteria andCourseLevelBetween(String value1, String value2) {
            addCriterion("course_level between", value1, value2, "courseLevel");
            return (Criteria) this;
        }

        public Criteria andCourseLevelNotBetween(String value1, String value2) {
            addCriterion("course_level not between", value1, value2, "courseLevel");
            return (Criteria) this;
        }

        public Criteria andCourseIsOnlineIsNull() {
            addCriterion("course_is_online is null");
            return (Criteria) this;
        }

        public Criteria andCourseIsOnlineIsNotNull() {
            addCriterion("course_is_online is not null");
            return (Criteria) this;
        }

        public Criteria andCourseIsOnlineEqualTo(String value) {
            addCriterion("course_is_online =", value, "courseIsOnline");
            return (Criteria) this;
        }

        public Criteria andCourseIsOnlineNotEqualTo(String value) {
            addCriterion("course_is_online <>", value, "courseIsOnline");
            return (Criteria) this;
        }

        public Criteria andCourseIsOnlineGreaterThan(String value) {
            addCriterion("course_is_online >", value, "courseIsOnline");
            return (Criteria) this;
        }

        public Criteria andCourseIsOnlineGreaterThanOrEqualTo(String value) {
            addCriterion("course_is_online >=", value, "courseIsOnline");
            return (Criteria) this;
        }

        public Criteria andCourseIsOnlineLessThan(String value) {
            addCriterion("course_is_online <", value, "courseIsOnline");
            return (Criteria) this;
        }

        public Criteria andCourseIsOnlineLessThanOrEqualTo(String value) {
            addCriterion("course_is_online <=", value, "courseIsOnline");
            return (Criteria) this;
        }

        public Criteria andCourseIsOnlineLike(String value) {
            addCriterion("course_is_online like", value, "courseIsOnline");
            return (Criteria) this;
        }

        public Criteria andCourseIsOnlineNotLike(String value) {
            addCriterion("course_is_online not like", value, "courseIsOnline");
            return (Criteria) this;
        }

        public Criteria andCourseIsOnlineIn(List<String> values) {
            addCriterion("course_is_online in", values, "courseIsOnline");
            return (Criteria) this;
        }

        public Criteria andCourseIsOnlineNotIn(List<String> values) {
            addCriterion("course_is_online not in", values, "courseIsOnline");
            return (Criteria) this;
        }

        public Criteria andCourseIsOnlineBetween(String value1, String value2) {
            addCriterion("course_is_online between", value1, value2, "courseIsOnline");
            return (Criteria) this;
        }

        public Criteria andCourseIsOnlineNotBetween(String value1, String value2) {
            addCriterion("course_is_online not between", value1, value2, "courseIsOnline");
            return (Criteria) this;
        }

        public Criteria andCourseAddressIsNull() {
            addCriterion("course_address is null");
            return (Criteria) this;
        }

        public Criteria andCourseAddressIsNotNull() {
            addCriterion("course_address is not null");
            return (Criteria) this;
        }

        public Criteria andCourseAddressEqualTo(String value) {
            addCriterion("course_address =", value, "courseAddress");
            return (Criteria) this;
        }

        public Criteria andCourseAddressNotEqualTo(String value) {
            addCriterion("course_address <>", value, "courseAddress");
            return (Criteria) this;
        }

        public Criteria andCourseAddressGreaterThan(String value) {
            addCriterion("course_address >", value, "courseAddress");
            return (Criteria) this;
        }

        public Criteria andCourseAddressGreaterThanOrEqualTo(String value) {
            addCriterion("course_address >=", value, "courseAddress");
            return (Criteria) this;
        }

        public Criteria andCourseAddressLessThan(String value) {
            addCriterion("course_address <", value, "courseAddress");
            return (Criteria) this;
        }

        public Criteria andCourseAddressLessThanOrEqualTo(String value) {
            addCriterion("course_address <=", value, "courseAddress");
            return (Criteria) this;
        }

        public Criteria andCourseAddressLike(String value) {
            addCriterion("course_address like", value, "courseAddress");
            return (Criteria) this;
        }

        public Criteria andCourseAddressNotLike(String value) {
            addCriterion("course_address not like", value, "courseAddress");
            return (Criteria) this;
        }

        public Criteria andCourseAddressIn(List<String> values) {
            addCriterion("course_address in", values, "courseAddress");
            return (Criteria) this;
        }

        public Criteria andCourseAddressNotIn(List<String> values) {
            addCriterion("course_address not in", values, "courseAddress");
            return (Criteria) this;
        }

        public Criteria andCourseAddressBetween(String value1, String value2) {
            addCriterion("course_address between", value1, value2, "courseAddress");
            return (Criteria) this;
        }

        public Criteria andCourseAddressNotBetween(String value1, String value2) {
            addCriterion("course_address not between", value1, value2, "courseAddress");
            return (Criteria) this;
        }

        public Criteria andStartdateIsNull() {
            addCriterion("startDate is null");
            return (Criteria) this;
        }

        public Criteria andStartdateIsNotNull() {
            addCriterion("startDate is not null");
            return (Criteria) this;
        }

        public Criteria andStartdateEqualTo(String value) {
            addCriterion("startDate =", value, "startdate");
            return (Criteria) this;
        }

        public Criteria andStartdateNotEqualTo(String value) {
            addCriterion("startDate <>", value, "startdate");
            return (Criteria) this;
        }

        public Criteria andStartdateGreaterThan(String value) {
            addCriterion("startDate >", value, "startdate");
            return (Criteria) this;
        }

        public Criteria andStartdateGreaterThanOrEqualTo(String value) {
            addCriterion("startDate >=", value, "startdate");
            return (Criteria) this;
        }

        public Criteria andStartdateLessThan(String value) {
            addCriterion("startDate <", value, "startdate");
            return (Criteria) this;
        }

        public Criteria andStartdateLessThanOrEqualTo(String value) {
            addCriterion("startDate <=", value, "startdate");
            return (Criteria) this;
        }

        public Criteria andStartdateLike(String value) {
            addCriterion("startDate like", value, "startdate");
            return (Criteria) this;
        }

        public Criteria andStartdateNotLike(String value) {
            addCriterion("startDate not like", value, "startdate");
            return (Criteria) this;
        }

        public Criteria andStartdateIn(List<String> values) {
            addCriterion("startDate in", values, "startdate");
            return (Criteria) this;
        }

        public Criteria andStartdateNotIn(List<String> values) {
            addCriterion("startDate not in", values, "startdate");
            return (Criteria) this;
        }

        public Criteria andStartdateBetween(String value1, String value2) {
            addCriterion("startDate between", value1, value2, "startdate");
            return (Criteria) this;
        }

        public Criteria andStartdateNotBetween(String value1, String value2) {
            addCriterion("startDate not between", value1, value2, "startdate");
            return (Criteria) this;
        }

        public Criteria andEnddateIsNull() {
            addCriterion("endDate is null");
            return (Criteria) this;
        }

        public Criteria andEnddateIsNotNull() {
            addCriterion("endDate is not null");
            return (Criteria) this;
        }

        public Criteria andEnddateEqualTo(String value) {
            addCriterion("endDate =", value, "enddate");
            return (Criteria) this;
        }

        public Criteria andEnddateNotEqualTo(String value) {
            addCriterion("endDate <>", value, "enddate");
            return (Criteria) this;
        }

        public Criteria andEnddateGreaterThan(String value) {
            addCriterion("endDate >", value, "enddate");
            return (Criteria) this;
        }

        public Criteria andEnddateGreaterThanOrEqualTo(String value) {
            addCriterion("endDate >=", value, "enddate");
            return (Criteria) this;
        }

        public Criteria andEnddateLessThan(String value) {
            addCriterion("endDate <", value, "enddate");
            return (Criteria) this;
        }

        public Criteria andEnddateLessThanOrEqualTo(String value) {
            addCriterion("endDate <=", value, "enddate");
            return (Criteria) this;
        }

        public Criteria andEnddateLike(String value) {
            addCriterion("endDate like", value, "enddate");
            return (Criteria) this;
        }

        public Criteria andEnddateNotLike(String value) {
            addCriterion("endDate not like", value, "enddate");
            return (Criteria) this;
        }

        public Criteria andEnddateIn(List<String> values) {
            addCriterion("endDate in", values, "enddate");
            return (Criteria) this;
        }

        public Criteria andEnddateNotIn(List<String> values) {
            addCriterion("endDate not in", values, "enddate");
            return (Criteria) this;
        }

        public Criteria andEnddateBetween(String value1, String value2) {
            addCriterion("endDate between", value1, value2, "enddate");
            return (Criteria) this;
        }

        public Criteria andEnddateNotBetween(String value1, String value2) {
            addCriterion("endDate not between", value1, value2, "enddate");
            return (Criteria) this;
        }

        public Criteria andCoursePriceIsNull() {
            addCriterion("course_price is null");
            return (Criteria) this;
        }

        public Criteria andCoursePriceIsNotNull() {
            addCriterion("course_price is not null");
            return (Criteria) this;
        }

        public Criteria andCoursePriceEqualTo(Integer value) {
            addCriterion("course_price =", value, "coursePrice");
            return (Criteria) this;
        }

        public Criteria andCoursePriceNotEqualTo(Integer value) {
            addCriterion("course_price <>", value, "coursePrice");
            return (Criteria) this;
        }

        public Criteria andCoursePriceGreaterThan(Integer value) {
            addCriterion("course_price >", value, "coursePrice");
            return (Criteria) this;
        }

        public Criteria andCoursePriceGreaterThanOrEqualTo(Integer value) {
            addCriterion("course_price >=", value, "coursePrice");
            return (Criteria) this;
        }

        public Criteria andCoursePriceLessThan(Integer value) {
            addCriterion("course_price <", value, "coursePrice");
            return (Criteria) this;
        }

        public Criteria andCoursePriceLessThanOrEqualTo(Integer value) {
            addCriterion("course_price <=", value, "coursePrice");
            return (Criteria) this;
        }

        public Criteria andCoursePriceIn(List<Integer> values) {
            addCriterion("course_price in", values, "coursePrice");
            return (Criteria) this;
        }

        public Criteria andCoursePriceNotIn(List<Integer> values) {
            addCriterion("course_price not in", values, "coursePrice");
            return (Criteria) this;
        }

        public Criteria andCoursePriceBetween(Integer value1, Integer value2) {
            addCriterion("course_price between", value1, value2, "coursePrice");
            return (Criteria) this;
        }

        public Criteria andCoursePriceNotBetween(Integer value1, Integer value2) {
            addCriterion("course_price not between", value1, value2, "coursePrice");
            return (Criteria) this;
        }

        public Criteria andCourseWelfareIsNull() {
            addCriterion("course_welfare is null");
            return (Criteria) this;
        }

        public Criteria andCourseWelfareIsNotNull() {
            addCriterion("course_welfare is not null");
            return (Criteria) this;
        }

        public Criteria andCourseWelfareEqualTo(String value) {
            addCriterion("course_welfare =", value, "courseWelfare");
            return (Criteria) this;
        }

        public Criteria andCourseWelfareNotEqualTo(String value) {
            addCriterion("course_welfare <>", value, "courseWelfare");
            return (Criteria) this;
        }

        public Criteria andCourseWelfareGreaterThan(String value) {
            addCriterion("course_welfare >", value, "courseWelfare");
            return (Criteria) this;
        }

        public Criteria andCourseWelfareGreaterThanOrEqualTo(String value) {
            addCriterion("course_welfare >=", value, "courseWelfare");
            return (Criteria) this;
        }

        public Criteria andCourseWelfareLessThan(String value) {
            addCriterion("course_welfare <", value, "courseWelfare");
            return (Criteria) this;
        }

        public Criteria andCourseWelfareLessThanOrEqualTo(String value) {
            addCriterion("course_welfare <=", value, "courseWelfare");
            return (Criteria) this;
        }

        public Criteria andCourseWelfareLike(String value) {
            addCriterion("course_welfare like", value, "courseWelfare");
            return (Criteria) this;
        }

        public Criteria andCourseWelfareNotLike(String value) {
            addCriterion("course_welfare not like", value, "courseWelfare");
            return (Criteria) this;
        }

        public Criteria andCourseWelfareIn(List<String> values) {
            addCriterion("course_welfare in", values, "courseWelfare");
            return (Criteria) this;
        }

        public Criteria andCourseWelfareNotIn(List<String> values) {
            addCriterion("course_welfare not in", values, "courseWelfare");
            return (Criteria) this;
        }

        public Criteria andCourseWelfareBetween(String value1, String value2) {
            addCriterion("course_welfare between", value1, value2, "courseWelfare");
            return (Criteria) this;
        }

        public Criteria andCourseWelfareNotBetween(String value1, String value2) {
            addCriterion("course_welfare not between", value1, value2, "courseWelfare");
            return (Criteria) this;
        }

        public Criteria andCourseImgIsNull() {
            addCriterion("course_img is null");
            return (Criteria) this;
        }

        public Criteria andCourseImgIsNotNull() {
            addCriterion("course_img is not null");
            return (Criteria) this;
        }

        public Criteria andCourseImgEqualTo(String value) {
            addCriterion("course_img =", value, "courseImg");
            return (Criteria) this;
        }

        public Criteria andCourseImgNotEqualTo(String value) {
            addCriterion("course_img <>", value, "courseImg");
            return (Criteria) this;
        }

        public Criteria andCourseImgGreaterThan(String value) {
            addCriterion("course_img >", value, "courseImg");
            return (Criteria) this;
        }

        public Criteria andCourseImgGreaterThanOrEqualTo(String value) {
            addCriterion("course_img >=", value, "courseImg");
            return (Criteria) this;
        }

        public Criteria andCourseImgLessThan(String value) {
            addCriterion("course_img <", value, "courseImg");
            return (Criteria) this;
        }

        public Criteria andCourseImgLessThanOrEqualTo(String value) {
            addCriterion("course_img <=", value, "courseImg");
            return (Criteria) this;
        }

        public Criteria andCourseImgLike(String value) {
            addCriterion("course_img like", value, "courseImg");
            return (Criteria) this;
        }

        public Criteria andCourseImgNotLike(String value) {
            addCriterion("course_img not like", value, "courseImg");
            return (Criteria) this;
        }

        public Criteria andCourseImgIn(List<String> values) {
            addCriterion("course_img in", values, "courseImg");
            return (Criteria) this;
        }

        public Criteria andCourseImgNotIn(List<String> values) {
            addCriterion("course_img not in", values, "courseImg");
            return (Criteria) this;
        }

        public Criteria andCourseImgBetween(String value1, String value2) {
            addCriterion("course_img between", value1, value2, "courseImg");
            return (Criteria) this;
        }

        public Criteria andCourseImgNotBetween(String value1, String value2) {
            addCriterion("course_img not between", value1, value2, "courseImg");
            return (Criteria) this;
        }

        public Criteria andCourseDescriptionIsNull() {
            addCriterion("course_description is null");
            return (Criteria) this;
        }

        public Criteria andCourseDescriptionIsNotNull() {
            addCriterion("course_description is not null");
            return (Criteria) this;
        }

        public Criteria andCourseDescriptionEqualTo(String value) {
            addCriterion("course_description =", value, "courseDescription");
            return (Criteria) this;
        }

        public Criteria andCourseDescriptionNotEqualTo(String value) {
            addCriterion("course_description <>", value, "courseDescription");
            return (Criteria) this;
        }

        public Criteria andCourseDescriptionGreaterThan(String value) {
            addCriterion("course_description >", value, "courseDescription");
            return (Criteria) this;
        }

        public Criteria andCourseDescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("course_description >=", value, "courseDescription");
            return (Criteria) this;
        }

        public Criteria andCourseDescriptionLessThan(String value) {
            addCriterion("course_description <", value, "courseDescription");
            return (Criteria) this;
        }

        public Criteria andCourseDescriptionLessThanOrEqualTo(String value) {
            addCriterion("course_description <=", value, "courseDescription");
            return (Criteria) this;
        }

        public Criteria andCourseDescriptionLike(String value) {
            addCriterion("course_description like", value, "courseDescription");
            return (Criteria) this;
        }

        public Criteria andCourseDescriptionNotLike(String value) {
            addCriterion("course_description not like", value, "courseDescription");
            return (Criteria) this;
        }

        public Criteria andCourseDescriptionIn(List<String> values) {
            addCriterion("course_description in", values, "courseDescription");
            return (Criteria) this;
        }

        public Criteria andCourseDescriptionNotIn(List<String> values) {
            addCriterion("course_description not in", values, "courseDescription");
            return (Criteria) this;
        }

        public Criteria andCourseDescriptionBetween(String value1, String value2) {
            addCriterion("course_description between", value1, value2, "courseDescription");
            return (Criteria) this;
        }

        public Criteria andCourseDescriptionNotBetween(String value1, String value2) {
            addCriterion("course_description not between", value1, value2, "courseDescription");
            return (Criteria) this;
        }

        public Criteria andCourseLearningFrequencyIsNull() {
            addCriterion("course_learning_frequency is null");
            return (Criteria) this;
        }

        public Criteria andCourseLearningFrequencyIsNotNull() {
            addCriterion("course_learning_frequency is not null");
            return (Criteria) this;
        }

        public Criteria andCourseLearningFrequencyEqualTo(Long value) {
            addCriterion("course_learning_frequency =", value, "courseLearningFrequency");
            return (Criteria) this;
        }

        public Criteria andCourseLearningFrequencyNotEqualTo(Long value) {
            addCriterion("course_learning_frequency <>", value, "courseLearningFrequency");
            return (Criteria) this;
        }

        public Criteria andCourseLearningFrequencyGreaterThan(Long value) {
            addCriterion("course_learning_frequency >", value, "courseLearningFrequency");
            return (Criteria) this;
        }

        public Criteria andCourseLearningFrequencyGreaterThanOrEqualTo(Long value) {
            addCriterion("course_learning_frequency >=", value, "courseLearningFrequency");
            return (Criteria) this;
        }

        public Criteria andCourseLearningFrequencyLessThan(Long value) {
            addCriterion("course_learning_frequency <", value, "courseLearningFrequency");
            return (Criteria) this;
        }

        public Criteria andCourseLearningFrequencyLessThanOrEqualTo(Long value) {
            addCriterion("course_learning_frequency <=", value, "courseLearningFrequency");
            return (Criteria) this;
        }

        public Criteria andCourseLearningFrequencyIn(List<Long> values) {
            addCriterion("course_learning_frequency in", values, "courseLearningFrequency");
            return (Criteria) this;
        }

        public Criteria andCourseLearningFrequencyNotIn(List<Long> values) {
            addCriterion("course_learning_frequency not in", values, "courseLearningFrequency");
            return (Criteria) this;
        }

        public Criteria andCourseLearningFrequencyBetween(Long value1, Long value2) {
            addCriterion("course_learning_frequency between", value1, value2, "courseLearningFrequency");
            return (Criteria) this;
        }

        public Criteria andCourseLearningFrequencyNotBetween(Long value1, Long value2) {
            addCriterion("course_learning_frequency not between", value1, value2, "courseLearningFrequency");
            return (Criteria) this;
        }

        public Criteria andUserNameIsNull() {
            addCriterion("user_name is null");
            return (Criteria) this;
        }

        public Criteria andUserNameIsNotNull() {
            addCriterion("user_name is not null");
            return (Criteria) this;
        }

        public Criteria andUserNameEqualTo(String value) {
            addCriterion("user_name =", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotEqualTo(String value) {
            addCriterion("user_name <>", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameGreaterThan(String value) {
            addCriterion("user_name >", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameGreaterThanOrEqualTo(String value) {
            addCriterion("user_name >=", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameLessThan(String value) {
            addCriterion("user_name <", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameLessThanOrEqualTo(String value) {
            addCriterion("user_name <=", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameLike(String value) {
            addCriterion("user_name like", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotLike(String value) {
            addCriterion("user_name not like", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameIn(List<String> values) {
            addCriterion("user_name in", values, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotIn(List<String> values) {
            addCriterion("user_name not in", values, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameBetween(String value1, String value2) {
            addCriterion("user_name between", value1, value2, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotBetween(String value1, String value2) {
            addCriterion("user_name not between", value1, value2, "userName");
            return (Criteria) this;
        }

        public Criteria andIdCardNoIsNull() {
            addCriterion("id_card_no is null");
            return (Criteria) this;
        }

        public Criteria andIdCardNoIsNotNull() {
            addCriterion("id_card_no is not null");
            return (Criteria) this;
        }

        public Criteria andIdCardNoEqualTo(String value) {
            addCriterion("id_card_no =", value, "idCardNo");
            return (Criteria) this;
        }

        public Criteria andIdCardNoNotEqualTo(String value) {
            addCriterion("id_card_no <>", value, "idCardNo");
            return (Criteria) this;
        }

        public Criteria andIdCardNoGreaterThan(String value) {
            addCriterion("id_card_no >", value, "idCardNo");
            return (Criteria) this;
        }

        public Criteria andIdCardNoGreaterThanOrEqualTo(String value) {
            addCriterion("id_card_no >=", value, "idCardNo");
            return (Criteria) this;
        }

        public Criteria andIdCardNoLessThan(String value) {
            addCriterion("id_card_no <", value, "idCardNo");
            return (Criteria) this;
        }

        public Criteria andIdCardNoLessThanOrEqualTo(String value) {
            addCriterion("id_card_no <=", value, "idCardNo");
            return (Criteria) this;
        }

        public Criteria andIdCardNoLike(String value) {
            addCriterion("id_card_no like", value, "idCardNo");
            return (Criteria) this;
        }

        public Criteria andIdCardNoNotLike(String value) {
            addCriterion("id_card_no not like", value, "idCardNo");
            return (Criteria) this;
        }

        public Criteria andIdCardNoIn(List<String> values) {
            addCriterion("id_card_no in", values, "idCardNo");
            return (Criteria) this;
        }

        public Criteria andIdCardNoNotIn(List<String> values) {
            addCriterion("id_card_no not in", values, "idCardNo");
            return (Criteria) this;
        }

        public Criteria andIdCardNoBetween(String value1, String value2) {
            addCriterion("id_card_no between", value1, value2, "idCardNo");
            return (Criteria) this;
        }

        public Criteria andIdCardNoNotBetween(String value1, String value2) {
            addCriterion("id_card_no not between", value1, value2, "idCardNo");
            return (Criteria) this;
        }

        public Criteria andCourseBelongtoIsNull() {
            addCriterion("course_belongTo is null");
            return (Criteria) this;
        }

        public Criteria andCourseBelongtoIsNotNull() {
            addCriterion("course_belongTo is not null");
            return (Criteria) this;
        }

        public Criteria andCourseBelongtoEqualTo(String value) {
            addCriterion("course_belongTo =", value, "courseBelongto");
            return (Criteria) this;
        }

        public Criteria andCourseBelongtoNotEqualTo(String value) {
            addCriterion("course_belongTo <>", value, "courseBelongto");
            return (Criteria) this;
        }

        public Criteria andCourseBelongtoGreaterThan(String value) {
            addCriterion("course_belongTo >", value, "courseBelongto");
            return (Criteria) this;
        }

        public Criteria andCourseBelongtoGreaterThanOrEqualTo(String value) {
            addCriterion("course_belongTo >=", value, "courseBelongto");
            return (Criteria) this;
        }

        public Criteria andCourseBelongtoLessThan(String value) {
            addCriterion("course_belongTo <", value, "courseBelongto");
            return (Criteria) this;
        }

        public Criteria andCourseBelongtoLessThanOrEqualTo(String value) {
            addCriterion("course_belongTo <=", value, "courseBelongto");
            return (Criteria) this;
        }

        public Criteria andCourseBelongtoLike(String value) {
            addCriterion("course_belongTo like", value, "courseBelongto");
            return (Criteria) this;
        }

        public Criteria andCourseBelongtoNotLike(String value) {
            addCriterion("course_belongTo not like", value, "courseBelongto");
            return (Criteria) this;
        }

        public Criteria andCourseBelongtoIn(List<String> values) {
            addCriterion("course_belongTo in", values, "courseBelongto");
            return (Criteria) this;
        }

        public Criteria andCourseBelongtoNotIn(List<String> values) {
            addCriterion("course_belongTo not in", values, "courseBelongto");
            return (Criteria) this;
        }

        public Criteria andCourseBelongtoBetween(String value1, String value2) {
            addCriterion("course_belongTo between", value1, value2, "courseBelongto");
            return (Criteria) this;
        }

        public Criteria andCourseBelongtoNotBetween(String value1, String value2) {
            addCriterion("course_belongTo not between", value1, value2, "courseBelongto");
            return (Criteria) this;
        }

        public Criteria andCourseUpdateDetailsIsNull() {
            addCriterion("course_update_details is null");
            return (Criteria) this;
        }

        public Criteria andCourseUpdateDetailsIsNotNull() {
            addCriterion("course_update_details is not null");
            return (Criteria) this;
        }

        public Criteria andCourseUpdateDetailsEqualTo(String value) {
            addCriterion("course_update_details =", value, "courseUpdateDetails");
            return (Criteria) this;
        }

        public Criteria andCourseUpdateDetailsNotEqualTo(String value) {
            addCriterion("course_update_details <>", value, "courseUpdateDetails");
            return (Criteria) this;
        }

        public Criteria andCourseUpdateDetailsGreaterThan(String value) {
            addCriterion("course_update_details >", value, "courseUpdateDetails");
            return (Criteria) this;
        }

        public Criteria andCourseUpdateDetailsGreaterThanOrEqualTo(String value) {
            addCriterion("course_update_details >=", value, "courseUpdateDetails");
            return (Criteria) this;
        }

        public Criteria andCourseUpdateDetailsLessThan(String value) {
            addCriterion("course_update_details <", value, "courseUpdateDetails");
            return (Criteria) this;
        }

        public Criteria andCourseUpdateDetailsLessThanOrEqualTo(String value) {
            addCriterion("course_update_details <=", value, "courseUpdateDetails");
            return (Criteria) this;
        }

        public Criteria andCourseUpdateDetailsLike(String value) {
            addCriterion("course_update_details like", value, "courseUpdateDetails");
            return (Criteria) this;
        }

        public Criteria andCourseUpdateDetailsNotLike(String value) {
            addCriterion("course_update_details not like", value, "courseUpdateDetails");
            return (Criteria) this;
        }

        public Criteria andCourseUpdateDetailsIn(List<String> values) {
            addCriterion("course_update_details in", values, "courseUpdateDetails");
            return (Criteria) this;
        }

        public Criteria andCourseUpdateDetailsNotIn(List<String> values) {
            addCriterion("course_update_details not in", values, "courseUpdateDetails");
            return (Criteria) this;
        }

        public Criteria andCourseUpdateDetailsBetween(String value1, String value2) {
            addCriterion("course_update_details between", value1, value2, "courseUpdateDetails");
            return (Criteria) this;
        }

        public Criteria andCourseUpdateDetailsNotBetween(String value1, String value2) {
            addCriterion("course_update_details not between", value1, value2, "courseUpdateDetails");
            return (Criteria) this;
        }

        public Criteria andCourseWaysIsNull() {
            addCriterion("course_ways is null");
            return (Criteria) this;
        }

        public Criteria andCourseWaysIsNotNull() {
            addCriterion("course_ways is not null");
            return (Criteria) this;
        }

        public Criteria andCourseWaysEqualTo(String value) {
            addCriterion("course_ways =", value, "courseWays");
            return (Criteria) this;
        }

        public Criteria andCourseWaysNotEqualTo(String value) {
            addCriterion("course_ways <>", value, "courseWays");
            return (Criteria) this;
        }

        public Criteria andCourseWaysGreaterThan(String value) {
            addCriterion("course_ways >", value, "courseWays");
            return (Criteria) this;
        }

        public Criteria andCourseWaysGreaterThanOrEqualTo(String value) {
            addCriterion("course_ways >=", value, "courseWays");
            return (Criteria) this;
        }

        public Criteria andCourseWaysLessThan(String value) {
            addCriterion("course_ways <", value, "courseWays");
            return (Criteria) this;
        }

        public Criteria andCourseWaysLessThanOrEqualTo(String value) {
            addCriterion("course_ways <=", value, "courseWays");
            return (Criteria) this;
        }

        public Criteria andCourseWaysLike(String value) {
            addCriterion("course_ways like", value, "courseWays");
            return (Criteria) this;
        }

        public Criteria andCourseWaysNotLike(String value) {
            addCriterion("course_ways not like", value, "courseWays");
            return (Criteria) this;
        }

        public Criteria andCourseWaysIn(List<String> values) {
            addCriterion("course_ways in", values, "courseWays");
            return (Criteria) this;
        }

        public Criteria andCourseWaysNotIn(List<String> values) {
            addCriterion("course_ways not in", values, "courseWays");
            return (Criteria) this;
        }

        public Criteria andCourseWaysBetween(String value1, String value2) {
            addCriterion("course_ways between", value1, value2, "courseWays");
            return (Criteria) this;
        }

        public Criteria andCourseWaysNotBetween(String value1, String value2) {
            addCriterion("course_ways not between", value1, value2, "courseWays");
            return (Criteria) this;
        }

        public Criteria andIsCarouselIsNull() {
            addCriterion("is_carousel is null");
            return (Criteria) this;
        }

        public Criteria andIsCarouselIsNotNull() {
            addCriterion("is_carousel is not null");
            return (Criteria) this;
        }

        public Criteria andIsCarouselEqualTo(String value) {
            addCriterion("is_carousel =", value, "isCarousel");
            return (Criteria) this;
        }

        public Criteria andIsCarouselNotEqualTo(String value) {
            addCriterion("is_carousel <>", value, "isCarousel");
            return (Criteria) this;
        }

        public Criteria andIsCarouselGreaterThan(String value) {
            addCriterion("is_carousel >", value, "isCarousel");
            return (Criteria) this;
        }

        public Criteria andIsCarouselGreaterThanOrEqualTo(String value) {
            addCriterion("is_carousel >=", value, "isCarousel");
            return (Criteria) this;
        }

        public Criteria andIsCarouselLessThan(String value) {
            addCriterion("is_carousel <", value, "isCarousel");
            return (Criteria) this;
        }

        public Criteria andIsCarouselLessThanOrEqualTo(String value) {
            addCriterion("is_carousel <=", value, "isCarousel");
            return (Criteria) this;
        }

        public Criteria andIsCarouselLike(String value) {
            addCriterion("is_carousel like", value, "isCarousel");
            return (Criteria) this;
        }

        public Criteria andIsCarouselNotLike(String value) {
            addCriterion("is_carousel not like", value, "isCarousel");
            return (Criteria) this;
        }

        public Criteria andIsCarouselIn(List<String> values) {
            addCriterion("is_carousel in", values, "isCarousel");
            return (Criteria) this;
        }

        public Criteria andIsCarouselNotIn(List<String> values) {
            addCriterion("is_carousel not in", values, "isCarousel");
            return (Criteria) this;
        }

        public Criteria andIsCarouselBetween(String value1, String value2) {
            addCriterion("is_carousel between", value1, value2, "isCarousel");
            return (Criteria) this;
        }

        public Criteria andIsCarouselNotBetween(String value1, String value2) {
            addCriterion("is_carousel not between", value1, value2, "isCarousel");
            return (Criteria) this;
        }

        public Criteria andCourseIsHotIsNull() {
            addCriterion("course_is_hot is null");
            return (Criteria) this;
        }

        public Criteria andCourseIsHotIsNotNull() {
            addCriterion("course_is_hot is not null");
            return (Criteria) this;
        }

        public Criteria andCourseIsHotEqualTo(String value) {
            addCriterion("course_is_hot =", value, "courseIsHot");
            return (Criteria) this;
        }

        public Criteria andCourseIsHotNotEqualTo(String value) {
            addCriterion("course_is_hot <>", value, "courseIsHot");
            return (Criteria) this;
        }

        public Criteria andCourseIsHotGreaterThan(String value) {
            addCriterion("course_is_hot >", value, "courseIsHot");
            return (Criteria) this;
        }

        public Criteria andCourseIsHotGreaterThanOrEqualTo(String value) {
            addCriterion("course_is_hot >=", value, "courseIsHot");
            return (Criteria) this;
        }

        public Criteria andCourseIsHotLessThan(String value) {
            addCriterion("course_is_hot <", value, "courseIsHot");
            return (Criteria) this;
        }

        public Criteria andCourseIsHotLessThanOrEqualTo(String value) {
            addCriterion("course_is_hot <=", value, "courseIsHot");
            return (Criteria) this;
        }

        public Criteria andCourseIsHotLike(String value) {
            addCriterion("course_is_hot like", value, "courseIsHot");
            return (Criteria) this;
        }

        public Criteria andCourseIsHotNotLike(String value) {
            addCriterion("course_is_hot not like", value, "courseIsHot");
            return (Criteria) this;
        }

        public Criteria andCourseIsHotIn(List<String> values) {
            addCriterion("course_is_hot in", values, "courseIsHot");
            return (Criteria) this;
        }

        public Criteria andCourseIsHotNotIn(List<String> values) {
            addCriterion("course_is_hot not in", values, "courseIsHot");
            return (Criteria) this;
        }

        public Criteria andCourseIsHotBetween(String value1, String value2) {
            addCriterion("course_is_hot between", value1, value2, "courseIsHot");
            return (Criteria) this;
        }

        public Criteria andCourseIsHotNotBetween(String value1, String value2) {
            addCriterion("course_is_hot not between", value1, value2, "courseIsHot");
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

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
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