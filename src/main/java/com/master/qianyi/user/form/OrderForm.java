package com.master.qianyi.user.form;

import com.master.qianyi.pojo.TbOrder;

public class OrderForm extends TbOrder {

    private String courseId;

    private String courseName;

    private String coursePrice;

    private String course_belongTo;

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

    public String getCoursePrice() {
        return coursePrice;
    }

    public void setCoursePrice(String coursePrice) {
        this.coursePrice = coursePrice;
    }

    public String getCourse_belongTo() {
        return course_belongTo;
    }

    public void setCourse_belongTo(String course_belongTo) {
        this.course_belongTo = course_belongTo;
    }
}
