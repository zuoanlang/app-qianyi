package com.master.qianyi.course.form;

import java.util.List;

public class CourseTitle {

    private String courseTitle;

    private List<CourseCatalog> catalogList;

    public CourseTitle() {
    }

    public CourseTitle(String courseTitle, List<CourseCatalog> catalogList) {
        this.courseTitle = courseTitle;
        this.catalogList = catalogList;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public List<CourseCatalog> getCatalogList() {
        return catalogList;
    }

    public void setCatalogList(List<CourseCatalog> catalogList) {
        this.catalogList = catalogList;
    }
}
