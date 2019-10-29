package com.master.qianyi.comment.form;

public class ServiceComment {

    private String userName;

    private String headImg;

    private String serviceName;

    private String commentDateTime;

    private String commentContent;

    public ServiceComment() {
    }

    public ServiceComment(String userName, String headImg, String serviceName, String commentDateTime, String commentContent) {
        this.userName = userName;
        this.headImg = headImg;
        this.serviceName = serviceName;
        this.commentDateTime = commentDateTime;
        this.commentContent = commentContent;
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

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
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
}
