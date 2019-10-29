package com.master.qianyi.course.controller;

import com.master.qianyi.course.service.CourseService;
import com.master.qianyi.pojo.TbCourse;
import com.master.qianyi.utils.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
     * @return ResultBean
     */
    @GetMapping("/searchCourse")
    @ResponseBody
    public ResultBean getCourseSearchResult(@RequestParam(value="pageNum", defaultValue="1") int pageNum,
                                            @RequestParam(value="pageSize", defaultValue="15")int pageSize, TbCourse course) {
        return service.getCourseSearchResult(pageNum, pageSize, course);
    }

    /**
     * 根据课程id查询课程信息
     *
     * @param courseId 课程id
     * @return ResultBean
     */
    @GetMapping("/getDetailsByCourseId")
    @ResponseBody
    public ResultBean getDetailsByCourseId(String courseId,String userId) {
        return service.getDetailsByCourseId(courseId,userId);
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
    public ResultBean getCourseByUserId(@RequestParam(value="userId")String userId,
                                        @RequestParam(value="token")String token,
                                        @RequestParam(value="pageNum",defaultValue = "1")int pageNum,
                                        @RequestParam(value="pageSize",defaultValue = "10")int pageSize) {
        return service.getCourseByUserId(userId, token,pageNum, pageSize);
    }

    /**
     * 课程每点击一次，学习次数加一
     *
     * @param catalogId 目录的id
     * @return
     */
    @PostMapping("/addLearningTimes")
    public ResultBean addLearningTimes(@RequestParam(value="catalogId")Long catalogId) {
        return service.addLearningTimes(catalogId);
    }

    /**
     * 取首页课程轮播图
     * @return
     */
    @GetMapping("/getCarouselList")
    @ResponseBody
    public ResultBean getCarouselList() {
        return service.getCarouselList();
    }

    /**
     * 取课程分类（名师擅长专业相同接口）
     */
    @GetMapping("/getCourseTypeList")
    public ResultBean getCourseType(){
        return service.getCourseType();
    }

    /**
     * 根据课程courseId取课程目录
     * @param courseId 课程id
     * @return ResultBean 结果
     */
    @GetMapping("/getCourseCatalogList")
    public ResultBean getCourseCatalogList(@RequestParam(value="courseId") String courseId,String userId,String token){
        return service.getCourseCatalogList(courseId,userId,token);
    }

    /**
     * 根据课程catalogId取课程目录视屏的播放连接及记录的播放秒数
     * @param catalogId     课程目录id
     * @return ResultBean   结果
     */
    @GetMapping("/getCatalogVideoInfo")
    public ResultBean getCatalogVideoInfo(@RequestParam(value="courseId") String courseId,
                                          @RequestParam(value="catalogId") String catalogId,
                                          @RequestParam(value="userId")String userId,
                                          @RequestParam(value="token")String token){
        return service.getCatalogVideoInfo(courseId,catalogId,userId,token);
    }

    /**
     * 根据课程catalogId取课程目录文稿代码块
     * @param catalogId     课程目录id
     * @return ResultBean   结果
     */
    @GetMapping("/getCatalogDraft")
    public ResultBean getCatalogDraft(@RequestParam(value="catalogId") String catalogId){
        return service.getCatalogDraft(catalogId);
    }

    /**
     * 取线下课程列表
     * @param catalogId     课程目录id
     * @return ResultBean   结果
     */
    @GetMapping("/getUnderlineCourseList")
    public ResultBean getUnderlineCourseList(@RequestParam(value="pageNum",defaultValue = "1")int pageNum,
                                      @RequestParam(value="pageSize",defaultValue = "10")int pageSize){
        return service.getUnderlineCourseList(pageNum,pageSize);
    }
}
