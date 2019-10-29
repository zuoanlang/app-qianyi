package com.master.qianyi.comment.form;

import java.util.List;

public class CourseComment {

    private String masterId;

    private String commentId;

    private String userName;

    private String headImg;

    private String commentDateTime;

    private String commentContent;

    private List<BriefComment> briefCommentList;

    public CourseComment() {
    }

    public CourseComment(String userName, String headImg, String commentDateTime, String commentContent) {
        this.userName = userName;
        this.headImg = headImg;
        this.commentDateTime = commentDateTime;
        this.commentContent = commentContent;
    }

    public String getMasterId() {
        return masterId;
    }

    public void setMasterId(String masterId) {
        this.masterId = masterId;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
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

    public String getCommentDateTime() {
        return commentDateTime;
    }

    public void setCommentDateTime(String commentDateTime) {
        this.commentDateTime = commentDateTime;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public List<BriefComment> getBriefCommentList() {
        return briefCommentList;
    }

    public void setBriefCommentList(List<BriefComment> briefCommentList) {
        this.briefCommentList = briefCommentList;
    }
}
