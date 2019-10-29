package com.master.qianyi.pojo;

public class TbCatalog {
    private Long catalogId;

    private Long catalogParentId;

    private String courseId;

    private Boolean isParent;

    private String catalogName;

    private Integer catalogOrder;

    private String videoPath;

    private String videoTime;

    private String videoName;

    private Long learningTimes;

    private String isAuditioning;

    private String deleteFlag;

    private String effectFlag;

    private String remark1;

    private String remark2;

    private String videoDraft;

    public Long getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(Long catalogId) {
        this.catalogId = catalogId;
    }

    public Long getCatalogParentId() {
        return catalogParentId;
    }

    public void setCatalogParentId(Long catalogParentId) {
        this.catalogParentId = catalogParentId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId == null ? null : courseId.trim();
    }

    public Boolean getIsParent() {
        return isParent;
    }

    public void setIsParent(Boolean isParent) {
        this.isParent = isParent;
    }

    public String getCatalogName() {
        return catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName == null ? null : catalogName.trim();
    }

    public Integer getCatalogOrder() {
        return catalogOrder;
    }

    public void setCatalogOrder(Integer catalogOrder) {
        this.catalogOrder = catalogOrder;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath == null ? null : videoPath.trim();
    }

    public String getVideoTime() {
        return videoTime;
    }

    public void setVideoTime(String videoTime) {
        this.videoTime = videoTime == null ? null : videoTime.trim();
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName == null ? null : videoName.trim();
    }

    public Long getLearningTimes() {
        return learningTimes;
    }

    public void setLearningTimes(Long learningTimes) {
        this.learningTimes = learningTimes;
    }

    public String getIsAuditioning() {
        return isAuditioning;
    }

    public void setIsAuditioning(String isAuditioning) {
        this.isAuditioning = isAuditioning == null ? null : isAuditioning.trim();
    }

    public String getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag == null ? null : deleteFlag.trim();
    }

    public String getEffectFlag() {
        return effectFlag;
    }

    public void setEffectFlag(String effectFlag) {
        this.effectFlag = effectFlag == null ? null : effectFlag.trim();
    }

    public String getRemark1() {
        return remark1;
    }

    public void setRemark1(String remark1) {
        this.remark1 = remark1 == null ? null : remark1.trim();
    }

    public String getRemark2() {
        return remark2;
    }

    public void setRemark2(String remark2) {
        this.remark2 = remark2 == null ? null : remark2.trim();
    }

    public String getVideoDraft() {
        return videoDraft;
    }

    public void setVideoDraft(String videoDraft) {
        this.videoDraft = videoDraft == null ? null : videoDraft.trim();
    }
}