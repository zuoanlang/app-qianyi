package com.master.qianyi.user.form;

import com.master.qianyi.comment.form.ServiceComment;

import java.util.List;

public class TeacherDetails {

    private String userName;

    private String headImg;

    private int serviceNum;

    private int commentNum;

    private String masterScore;

    private String masterIntroduction;

    private List<String> majors;

    private List<ServiceComment> commentList;


    public TeacherDetails() {
    }

    public TeacherDetails(String userName, String headImg, int serviceNum, int commentNum, String masterScore,
                          String masterIntroduction, List<String> majors, List<ServiceComment> commentList) {
        this.userName = userName;
        this.headImg = headImg;
        this.serviceNum = serviceNum;
        this.commentNum = commentNum;
        this.masterScore = masterScore;
        this.masterIntroduction = masterIntroduction;
        this.majors = majors;
        this.commentList = commentList;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getServiceNum() {
        return serviceNum;
    }

    public void setServiceNum(int serviceNum) {
        this.serviceNum = serviceNum;
    }

    public int getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(int commentNum) {
        this.commentNum = commentNum;
    }

    public String getMasterScore() {
        return masterScore;
    }

    public void setMasterScore(String masterScore) {
        this.masterScore = masterScore;
    }

    public String getMasterIntroduction() {
        return masterIntroduction;
    }

    public void setMasterIntroduction(String masterIntroduction) {
        this.masterIntroduction = masterIntroduction;
    }

    public List<String> getMajors() {
        return majors;
    }

    public void setMajors(List<String> majors) {
        this.majors = majors;
    }

    public List<ServiceComment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<ServiceComment> commentList) {
        this.commentList = commentList;
    }
}
