package com.master.qianyi.course.form;

public class CourseBrief {

    private String courseId;

    private String courseImg;

    private String courseName;

    private String userName;

    private String masterIntroduction;

    private Long courseLearningFrequency;

    private int coursePrice;

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
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

    public String getMasterIntroduction() {
        return masterIntroduction;
    }

    public void setMasterIntroduction(String masterIntroduction) {
        this.masterIntroduction = masterIntroduction;
    }

    public Long getCourseLearningFrequency() {
        return courseLearningFrequency;
    }

    public void setCourseLearningFrequency(Long courseLearningFrequency) {
        this.courseLearningFrequency = courseLearningFrequency;
    }

    public int getCoursePrice() {
        return coursePrice;
    }

    public void setCoursePrice(int coursePrice) {
        this.coursePrice = coursePrice;
    }
}
