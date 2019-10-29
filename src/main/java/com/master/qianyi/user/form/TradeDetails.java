package com.master.qianyi.user.form;

public class TradeDetails {

    private String tradeName;

    private String tradeDataTime;

    private int tradeAmount;

    public TradeDetails() {
    }

    public TradeDetails(String tradeName, String tradeDataTime, int tradeAmount) {
        this.tradeName = tradeName;
        this.tradeDataTime = tradeDataTime;
        this.tradeAmount = tradeAmount;
    }

    public String getTradeName() {
        return tradeName;
    }

    public void setTradeName(String tradeName) {
        this.tradeName = tradeName;
    }

    public String getTradeDataTime() {
        return tradeDataTime;
    }

    public void setTradeDataTime(String tradeDataTime) {
        this.tradeDataTime = tradeDataTime;
    }

    public int getTradeAmount() {
        return tradeAmount;
    }

    public void setTradeAmount(int tradeAmount) {
        this.tradeAmount = tradeAmount;
    }
}
