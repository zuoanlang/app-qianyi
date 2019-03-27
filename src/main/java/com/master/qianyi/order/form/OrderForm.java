package com.master.qianyi.order.form;

import com.master.qianyi.pojo.TbOrder;

public class OrderForm extends TbOrder {

    private String courseId;

    private String courseName;

    private Integer coursePrice;

    private String course_belongTo;

    private Long courseLearningFrequency;

    private String courseImg;

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }


    public String getCourse_belongTo() {
        return course_belongTo;
    }

    public void setCourse_belongTo(String course_belongTo) {
        this.course_belongTo = course_belongTo;
    }


    public Integer getCoursePrice() {
        return coursePrice;
    }

    public void setCoursePrice(Integer coursePrice) {
        this.coursePrice = coursePrice;
    }

    public String getCourseImg() {
        return courseImg;
    }

    public void setCourseImg(String courseImg) {
        this.courseImg = courseImg;
    }

    public Long getCourseLearningFrequency() {
        return courseLearningFrequency;
    }

    public void setCourseLearningFrequency(Long courseLearningFrequency) {
        this.courseLearningFrequency = courseLearningFrequency;
    }
}
