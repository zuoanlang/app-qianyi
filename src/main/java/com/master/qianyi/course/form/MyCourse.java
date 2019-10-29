package com.master.qianyi.course.form;

public class MyCourse {

    private String courseId;

    private String catalogId;

    private String courseImg;

    private String courseName;

    private String userName;

    private Long courseLearningFrequency;

    private String lastLearningTime;

    private float lastLearningPercent;

    private float isLearning;

    public MyCourse() {
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(String catalogId) {
        this.catalogId = catalogId;
    }

    public String getCourseImg() {
        return courseImg;
    }

    public void setCourseImg(String courseImg) {
        this.courseImg = courseImg;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getCourseLearningFrequency() {
        return courseLearningFrequency;
    }

    public void setCourseLearningFrequency(Long courseLearningFrequency) {
        this.courseLearningFrequency = courseLearningFrequency;
    }

    public String getLastLearningTime() {
        return lastLearningTime;
    }

    public void setLastLearningTime(String lastLearningTime) {
        this.lastLearningTime = lastLearningTime;
    }

    public float getLastLearningPercent() {
        return lastLearningPercent;
    }

    public void setLastLearningPercent(float lastLearningPercent) {
        this.lastLearningPercent = lastLearningPercent;
    }

    public float getIsLearning() {
        return isLearning;
    }

    public void setIsLearning(float isLearning) {
        this.isLearning = isLearning;
    }
}
