package com.master.qianyi.wxpay.utils;

public class WxPayModel {

    /** 订单内容 */
    public String body;
    /** 订单编号：商户这边的编号 */
    public String outTradeNo;
    public String deviceInfo;
    /** 币种类型：CNY */
    public String feeType;
    /** 交易金额：CNY */
    public String totalFee;
    /** ip地址：要是ipv4的不然报错 */
    public String spbillCreateIp;
    /** 回调地址：微信支付的回调必填 */
    public String notifyUrl;
    /** 交易类型：JSAPI--公众号支付、NATIVE--原生扫码支付、APP--app支付 */
    public String tradeType;
    public String productId;
    public Double refundFee;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    public String getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(String totalFee) {
        this.totalFee = totalFee;
    }

    public String getSpbillCreateIp() {
        return spbillCreateIp;
    }

    public void setSpbillCreateIp(String spbillCreateIp) {
        this.spbillCreateIp = spbillCreateIp;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    @Override
    public String toString() {
        return "WxPayModel [body=" + body + ", outTradeNo=" + outTradeNo + ", deviceInfo=" + deviceInfo + ", feeType="
                + feeType + ", totalFee=" + totalFee + ", spbillCreateIp=" + spbillCreateIp + ", notifyUrl=" + notifyUrl
                + ", tradeType=" + tradeType + ", productId=" + productId + "]";
    }
}
