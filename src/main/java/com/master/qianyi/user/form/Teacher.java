package com.master.qianyi.user.form;

import com.master.qianyi.comment.form.ServiceComment;

import java.util.List;

public class Teacher {

    private String userId;

    private String userName;

    private String headImg;

    private String masterRank;

    private String masterScore;

    private String isOfficial;

    private String isOnline;

    private String masterIntroduction;

    private int commentNum;

    private Integer floorPrice;

    private List<String> majors;

    private List<ServiceComment> commentList;

    public Teacher() {
    }

    public Teacher(String userId, String userName, String headImg, String masterRank, String masterScore,
                   String isOfficial, String isOnline, String masterIntroduction, int commentNum,
                   Integer floorPrice, List<String> majors) {
        this.userId = userId;
        this.userName = userName;
        this.headImg = headImg;
        this.masterRank = masterRank;
        this.masterScore = masterScore;
        this.isOfficial = isOfficial;
        this.isOnline = isOnline;
        this.masterIntroduction = masterIntroduction;
        this.commentNum = commentNum;
        this.floorPrice = floorPrice;
        this.majors = majors;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getMasterRank() {
        return masterRank;
    }

    public void setMasterRank(String masterRank) {
        this.masterRank = masterRank;
    }

    public String getMasterScore() {
        return masterScore;
    }

    public void setMasterScore(String masterScore) {
        this.masterScore = masterScore;
    }

    public String getIsOfficial() {
        return isOfficial;
    }

    public void setIsOfficial(String isOfficial) {
        this.isOfficial = isOfficial;
    }

    public String getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(String isOnline) {
        this.isOnline = isOnline;
    }

    public String getMasterIntroduction() {
        return masterIntroduction;
    }

    public void setMasterIntroduction(String masterIntroduction) {
        this.masterIntroduction = masterIntroduction;
    }

    public int getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(int commentNum) {
        this.commentNum = commentNum;
    }

    public Integer getFloorPrice() {
        return floorPrice;
    }

    public void setFloorPrice(Integer floorPrice) {
        this.floorPrice = floorPrice;
    }

    public List<String> getMajors() {
        return majors;
    }

    public void setMajors(List<String> majors) {
        this.majors = majors;
    }
}
