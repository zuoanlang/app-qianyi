package com.master.qianyi.course.controller;

import com.master.qianyi.course.service.CourseService;
import com.master.qianyi.pojo.TbCourse;
import com.master.qianyi.user.form.OrderForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 课程controller
 */
@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseService service;

    /**
     * 根据条件查询课程
     *
     * @param pageNum  当前页码
     * @param pageSize 每页条数
     * @param course   查询条件
     * @return
     */
    @GetMapping("/searchCourse")
    @ResponseBody
    public List<TbCourse> getCourseSearchResult(int pageNum, int pageSize, TbCourse course) {
        return service.getCourseSearchResult(pageNum, pageSize, course);
    }

    /**
     * 根据用户id查询该用户已购买的课程
     *
     * @param userId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/getCourseByUserId")
    public List<TbCourse> getCourseByUserId(String userId, int pageNum, int pageSize) {
        return service.getCourseByUserId(userId, pageNum, pageSize);
    }
}
