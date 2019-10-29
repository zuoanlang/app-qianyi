package com.master.qianyi.course.form;


public class CourseDetails {

    private String courseImg;

    private String courseName;

    private String courseDescription;

    private Long courseLearningFrequency;

    //讲师介绍
    private String headImg;

    private String userName;

    private String masterIntroduction;

    //课程介绍
    private String courseUpdateDetails;

    private String courseWays;

    private int coursePrice;

    private String courseWelfare;

    private String isPurchased;

    public CourseDetails() {
    }

    public CourseDetails(String courseImg, String courseName, String courseDescription, Long courseLearningFrequency,
                         String headImg, String userName, String masterIntroduction, String courseUpdateDetails,
                         String courseWays, int coursePrice, String courseWelfare, String isPurchased) {
        this.courseImg = courseImg;
        this.courseName = courseName;
        this.courseDescription = courseDescription;
        this.courseLearningFrequency = courseLearningFrequency;
        this.headImg = headImg;
        this.userName = userName;
        this.masterIntroduction = masterIntroduction;
        this.courseUpdateDetails = courseUpdateDetails;
        this.courseWays = courseWays;
        this.coursePrice = coursePrice;
        this.courseWelfare = courseWelfare;
        this.isPurchased = isPurchased;
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

    public String getCourseDescription() {
        return courseDescription;
    }

    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }

    public Long getCourseLearningFrequency() {
        return courseLearningFrequency;
    }

    public void setCourseLearningFrequency(Long courseLearningFrequency) {
        this.courseLearningFrequency = courseLearningFrequency;
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

    public String getMasterIntroduction() {
        return masterIntroduction;
    }

    public void setMasterIntroduction(String masterIntroduction) {
        this.masterIntroduction = masterIntroduction;
    }

    public String getCourseUpdateDetails() {
        return courseUpdateDetails;
    }

    public void setCourseUpdateDetails(String courseUpdateDetails) {
        this.courseUpdateDetails = courseUpdateDetails;
    }

    public String getCourseWays() {
        return courseWays;
    }

    public void setCourseWays(String courseWays) {
        this.courseWays = courseWays;
    }

    public int getCoursePrice() {
        return coursePrice;
    }

    public void setCoursePrice(int coursePrice) {
        this.coursePrice = coursePrice;
    }

    public String getCourseWelfare() {
        return courseWelfare;
    }

    public void setCourseWelfare(String courseWelfare) {
        this.courseWelfare = courseWelfare;
    }

    public String getIsPurchased() {
        return isPurchased;
    }

    public void setIsPurchased(String isPurchased) {
        this.isPurchased = isPurchased;
    }
}
