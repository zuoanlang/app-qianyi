package com.master.qianyi.course.form;

public class CourseCatalog {

    private Long catalogId;

    private String catalogName;

    private String videoTime;

    private Long learningTimes;

    private String videoPath;

    private String isAuditioning;

    public CourseCatalog() {
    }

    public CourseCatalog(Long catalogId, String catalogName, String videoTime, Long learningTimes) {
        this.catalogId = catalogId;
        this.catalogName = catalogName;
        this.videoTime = videoTime;
        this.learningTimes = learningTimes;
    }

    public Long getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(Long catalogId) {
        this.catalogId = catalogId;
    }

    public String getCatalogName() {
        return catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    public String getVideoTime() {
        return videoTime;
    }

    public void setVideoTime(String videoTime) {
        this.videoTime = videoTime;
    }

    public Long getLearningTimes() {
        return learningTimes;
    }

    public void setLearningTimes(Long learningTimes) {
        this.learningTimes = learningTimes;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    public String getIsAuditioning() {
        return isAuditioning;
    }

    public void setIsAuditioning(String isAuditioning) {
        this.isAuditioning = isAuditioning;
    }
}
