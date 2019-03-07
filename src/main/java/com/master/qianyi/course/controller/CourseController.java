package com.master.qianyi.course.controller;

import com.master.qianyi.course.service.CourseService;
import com.master.qianyi.pojo.TbCourse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 课程controller
 */
@RestController
public class CourseController {

    @Autowired
    private CourseService service;

    /**
     * 根据条件查询课程
     *
     * @param course   查询条件
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/searchCourse")
    @ResponseBody
    public List<TbCourse> getCourseSearchResult(@RequestParam(defaultValue = "1") int pageNum,
                                                @RequestParam(defaultValue = "6") int pageSize,
                                                TbCourse course) {
        return service.getCourseSearchResult(pageNum, pageSize, course);
    }
}
