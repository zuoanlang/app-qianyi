package com.master.qianyi.comment.form;

public class BriefComment {

    private String userName;

    private String commentContent;

    public BriefComment() {
    }

    public BriefComment(String userName, String commentContent) {
        this.userName = userName;
        this.commentContent = commentContent;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }
}
