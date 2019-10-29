package com.master.qianyi.order.form;

public class OrderDetails {

    private String orderStatus;

    private String goodImgPath;

    private String orderId;

    private String isCourse;

    private String goodsName;

    private String masterName;

    private Integer goodsAmount;

    private Long courseLearningFrequency;

    private int courseVideoNum;

    private String modeOfPayment;

    private String orderCreateTime;

    private String orderPayTime;

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getIsCourse() {
        return isCourse;
    }

    public void setIsCourse(String isCourse) {
        this.isCourse = isCourse;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getMasterName() {
        return masterName;
    }

    public void setMasterName(String masterName) {
        this.masterName = masterName;
    }

    public Integer getGoodsAmount() {
        return goodsAmount;
    }

    public void setGoodsAmount(Integer goodsAmount) {
        this.goodsAmount = goodsAmount;
    }

    public Long getCourseLearningFrequency() {
        return courseLearningFrequency;
    }

    public void setCourseLearningFrequency(Long courseLearningFrequency) {
        this.courseLearningFrequency = courseLearningFrequency;
    }

    public int getCourseVideoNum() {
        return courseVideoNum;
    }

    public void setCourseVideoNum(int courseVideoNum) {
        this.courseVideoNum = courseVideoNum;
    }

    public String getGoodImgPath() {
        return goodImgPath;
    }

    public void setGoodImgPath(String goodImgPath) {
        this.goodImgPath = goodImgPath;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getModeOfPayment() {
        return modeOfPayment;
    }

    public void setModeOfPayment(String modeOfPayment) {
        this.modeOfPayment = modeOfPayment;
    }

    public String getOrderCreateTime() {
        return orderCreateTime;
    }

    public void setOrderCreateTime(String orderCreateTime) {
        this.orderCreateTime = orderCreateTime;
    }

    public String getOrderPayTime() {
        return orderPayTime;
    }

    public void setOrderPayTime(String orderPayTime) {
        this.orderPayTime = orderPayTime;
    }
}
