package com.master.qianyi.user.form;

public class MyUserInfo {

    private String nickName;

    private String headImg;

    private String handphone;

    private String asign;

    private Integer userAccountBalance;

    public MyUserInfo() {
    }

    public MyUserInfo(String nickName, String headImg, String handphone, String asign, Integer userAccountBalance) {
        this.nickName = nickName;
        this.headImg = headImg;
        this.handphone = handphone;
        this.asign = asign;
        this.userAccountBalance = userAccountBalance;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getHandphone() {
        return handphone;
    }

    public void setHandphone(String handphone) {
        this.handphone = handphone;
    }

    public String getAsign() {
        return asign;
    }

    public void setAsign(String asign) {
        this.asign = asign;
    }

    public Integer getUserAccountBalance() {
        return userAccountBalance;
    }

    public void setUserAccountBalance(Integer userAccountBalance) {
        this.userAccountBalance = userAccountBalance;
    }
}
