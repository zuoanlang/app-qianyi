package com.master.qianyi.manager.controller;

import com.master.qianyi.manager.pojo.EasyUITreeNode;
import com.master.qianyi.manager.service.CourseManService;
import com.master.qianyi.mapper.TbCourseMapper;
import com.master.qianyi.pojo.TbCourse;
import com.master.qianyi.pojo.TbCourseExample;
import com.master.qianyi.pojo.TbCourseWithBLOBs;
import com.master.qianyi.utils.E3Result;
import com.master.qianyi.utils.EasyUIDataGridResult;
import com.master.qianyi.utils.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 课程管理模块controller
 */
@Controller
public class CourseManController {

    @Autowired
    private CourseManService courseServiceImpl;

    @Autowired
    private TbCourseMapper tbCourseMapper;

    /**
     * 查询数据字典(全部)
     *
     * @param parentId
     * @return
     */
    @RequestMapping("/catalog/list")
    @ResponseBody
    public List<EasyUITreeNode> getCatList(
            @RequestParam(value = "id", defaultValue = "0") Long parentId,String courseId,String type) {
        //外部没有指定id
        long courseIdLong = parentId;
        if (parentId == 0) {
            if(type.equals("0")){
                TbCourseExample example = new TbCourseExample();
                TbCourseExample.Criteria criteria = example.createCriteria();
                criteria.andEffectFlagEqualTo("0").andDeleteFlagEqualTo("0");
                List<TbCourse> courseList = tbCourseMapper.selectByExample(example);
                if (courseList.size() == 1) {
                    courseIdLong = Long.parseLong(courseList.get(0).getCourseId());
                }
            } else {
                courseIdLong = Long.parseLong(courseId);
            }

        }
        List<EasyUITreeNode> list = courseServiceImpl.getContentCatList(courseIdLong,courseId);
        return list;
    }

    /**
     * 添加分类节点
     */
    @RequestMapping(value = "/catalog/create", method = RequestMethod.POST)
    @ResponseBody
    public E3Result createCategory(Long parentId, String name,String courseId) {
        //调用服务添加节点
        E3Result e3Result = courseServiceImpl.addContentCategory(parentId, name,courseId);
        return e3Result;
    }

    /**
     * 修改分类节点
     */
    @RequestMapping(value = "/catalog/update", method = RequestMethod.POST)
    @ResponseBody
    public boolean editCategory(Long id, String name) {
        //调用服务添加节点
        return courseServiceImpl.editCategory(id, name);
    }

    /**
     * 删除分类节点（逻辑删除）
     */
    @RequestMapping(value = "/catalog/delete", method = RequestMethod.POST)
    @ResponseBody
    public boolean deleteCategory(Long id) {
        return courseServiceImpl.deleteCategory(id);
    }

    @GetMapping("/catalog/getCatalogVideo")
    @ResponseBody
    public Map<String, String> getCatalogVideo(Long id,String type) {
        return courseServiceImpl.getCatalogVideo(id,type);
    }

    @PostMapping("/course/saveCourseInfo")
    @ResponseBody
    public ResultBean saveCourseInfo(TbCourseWithBLOBs course) {
        return courseServiceImpl.saveCourseInfo(course);
    }

    @PostMapping("/course/editCourseInfo")
    @ResponseBody
    public ResultBean editCourseInfo(TbCourseWithBLOBs course) {
        return courseServiceImpl.editCourseInfo(course);
    }

    @GetMapping("/courseMan/getCourseList")
    @ResponseBody
    public EasyUIDataGridResult getCourseList(@RequestParam(value = "page") int pageNum,
                                              @RequestParam(value = "rows") int pageSize,
                                              TbCourse tbCourse) {
        return courseServiceImpl.getCourseList(pageNum, pageSize, tbCourse);
    }

    @GetMapping("/courseMan/getCourseId")
    @ResponseBody
    public Map<String, String> getCourseId() {
        return courseServiceImpl.getCourseId();
    }

    @PostMapping("/catalog/uploadVideoName")
    @ResponseBody
    public Map<String, String> uploadVideoName(long id,String videoName,String videoPath,String videoTime,String isAuditioning) {
        return courseServiceImpl.uploadVideoName(id,videoName,videoPath,videoTime,isAuditioning);
    }


    @PostMapping("/catalog/updateDraft")
    @ResponseBody
    public Map<String, String> updateDraft(long id,String draft) {
        return courseServiceImpl.updateDraft(id,draft);
    }

    @PostMapping("/course/deleteCourse")
    @ResponseBody
    public Map<String, String> deleteCourse(@RequestParam(value = "courseId") String courseId) {
        return courseServiceImpl.deleteCourse(courseId);
    }

    @PostMapping("/course/updateEffectStatus")
    @ResponseBody
    public Map<String, String> updateEffectStatus(@RequestParam(value = "courseId") String courseId,
                                                  @RequestParam(value = "type") String type) {
        return courseServiceImpl.updateEffectStatus(courseId,type);
    }

    @PostMapping("/course/carousel")
    @ResponseBody
    public Map<String, String> carousel(@RequestParam(value = "courseId") String courseId,
                                                  @RequestParam(value = "type") String type) {
        return courseServiceImpl.carousel(courseId,type);
    }





}
