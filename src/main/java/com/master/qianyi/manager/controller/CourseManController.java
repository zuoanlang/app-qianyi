package com.master.qianyi.manager.controller;

import com.master.qianyi.manager.pojo.EasyUITreeNode;
import com.master.qianyi.manager.service.CourseManService;
import com.master.qianyi.utils.E3Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 课程管理controller
 */
@Controller
public class CourseManController {

    @Autowired
    private CourseManService courseServiceImpl;

    /**
     * 查询数据字典(全部)
     * @param parentId
     * @return
     */
    @RequestMapping("/catalog/list")
    @ResponseBody
    public List<EasyUITreeNode> getCatList(
            @RequestParam(name = "id", defaultValue = "0") Long parentId) {
        List<EasyUITreeNode> list = courseServiceImpl.getContentCatList(parentId);
        return list;
    }

    /**
     * 添加分类节点
     */
    @RequestMapping(value = "/catalog/create", method = RequestMethod.POST)
    @ResponseBody
    public E3Result createCategory(Long parentId, String name) {
        //调用服务添加节点
        E3Result e3Result = courseServiceImpl.addContentCategory(parentId, name);
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
}
