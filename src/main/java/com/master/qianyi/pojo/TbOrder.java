package com.master.qianyi.pojo;

import java.util.Date;

public class TbOrder {
    private String orderId;

    private String thirdTradeno;

    private String userId;

    private String goodId;

    private String goodName;

    private String goodBelongTo;

    private Date orderCreateTime;

    private Date orderRefundStime;

    private Date orderRefundEtime;

    private Date orderCommentTime;

    private Date orderPayTime;

    private String modeOfPayment;

    private String orderStatus;

    private String orderRefundReason;

    private String orderType;

    private String orderAuditor;

    private Integer orderAmount;

    private String orderComment;

    private String orderRank;

    private String effectFlag;

    private String deleteFlag;

    private String remark1;

    private String remark5;

    private String remark3;

    private String remark4;

    private String remark2;

    private String buyerLogonId;

    private Double totalAmount;

    private Double receiptAmount;

    private Double buyerPayAmount;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public String getThirdTradeno() {
        return thirdTradeno;
    }

    public void setThirdTradeno(String thirdTradeno) {
        this.thirdTradeno = thirdTradeno == null ? null : thirdTradeno.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getGoodId() {
        return goodId;
    }

    public void setGoodId(String goodId) {
        this.goodId = goodId == null ? null : goodId.trim();
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName == null ? null : goodName.trim();
    }

    public String getGoodBelongTo() {
        return goodBelongTo;
    }

    public void setGoodBelongTo(String goodBelongTo) {
        this.goodBelongTo = goodBelongTo == null ? null : goodBelongTo.trim();
    }

    public Date getOrderCreateTime() {
        return orderCreateTime;
    }

    public void setOrderCreateTime(Date orderCreateTime) {
        this.orderCreateTime = orderCreateTime;
    }

    public Date getOrderRefundStime() {
        return orderRefundStime;
    }

    public void setOrderRefundStime(Date orderRefundStime) {
        this.orderRefundStime = orderRefundStime;
    }

    public Date getOrderRefundEtime() {
        return orderRefundEtime;
    }

    public void setOrderRefundEtime(Date orderRefundEtime) {
        this.orderRefundEtime = orderRefundEtime;
    }

    public Date getOrderCommentTime() {
        return orderCommentTime;
    }

    public void setOrderCommentTime(Date orderCommentTime) {
        this.orderCommentTime = orderCommentTime;
    }

    public Date getOrderPayTime() {
        return orderPayTime;
    }

    public void setOrderPayTime(Date orderPayTime) {
        this.orderPayTime = orderPayTime;
    }

    public String getModeOfPayment() {
        return modeOfPayment;
    }

    public void setModeOfPayment(String modeOfPayment) {
        this.modeOfPayment = modeOfPayment == null ? null : modeOfPayment.trim();
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus == null ? null : orderStatus.trim();
    }

    public String getOrderRefundReason() {
        return orderRefundReason;
    }

    public void setOrderRefundReason(String orderRefundReason) {
        this.orderRefundReason = orderRefundReason == null ? null : orderRefundReason.trim();
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType == null ? null : orderType.trim();
    }

    public String getOrderAuditor() {
        return orderAuditor;
    }

    public void setOrderAuditor(String orderAuditor) {
        this.orderAuditor = orderAuditor == null ? null : orderAuditor.trim();
    }

    public Integer getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(Integer orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getOrderComment() {
        return orderComment;
    }

    public void setOrderComment(String orderComment) {
        this.orderComment = orderComment == null ? null : orderComment.trim();
    }

    public String getOrderRank() {
        return orderRank;
    }

    public void setOrderRank(String orderRank) {
        this.orderRank = orderRank == null ? null : orderRank.trim();
    }

    public String getEffectFlag() {
        return effectFlag;
    }

    public void setEffectFlag(String effectFlag) {
        this.effectFlag = effectFlag == null ? null : effectFlag.trim();
    }

    public String getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag == null ? null : deleteFlag.trim();
    }

    public String getRemark1() {
        return remark1;
    }

    public void setRemark1(String remark1) {
        this.remark1 = remark1 == null ? null : remark1.trim();
    }

    public String getRemark5() {
        return remark5;
    }

    public void setRemark5(String remark5) {
        this.remark5 = remark5 == null ? null : remark5.trim();
    }

    public String getRemark3() {
        return remark3;
    }

    public void setRemark3(String remark3) {
        this.remark3 = remark3 == null ? null : remark3.trim();
    }

    public String getRemark4() {
        return remark4;
    }

    public void setRemark4(String remark4) {
        this.remark4 = remark4 == null ? null : remark4.trim();
    }

    public String getRemark2() {
        return remark2;
    }

    public void setRemark2(String remark2) {
        this.remark2 = remark2 == null ? null : remark2.trim();
    }

    public String getBuyerLogonId() {
        return buyerLogonId;
    }

    public void setBuyerLogonId(String buyerLogonId) {
        this.buyerLogonId = buyerLogonId == null ? null : buyerLogonId.trim();
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Double getReceiptAmount() {
        return receiptAmount;
    }

    public void setReceiptAmount(Double receiptAmount) {
        this.receiptAmount = receiptAmount;
    }

    public Double getBuyerPayAmount() {
        return buyerPayAmount;
    }

    public void setBuyerPayAmount(Double buyerPayAmount) {
        this.buyerPayAmount = buyerPayAmount;
    }
}