package com.master.qianyi.manager.controller;

import com.master.qianyi.manager.pojo.EasyUITreeNode;
import com.master.qianyi.manager.service.CategoryService;
import com.master.qianyi.utils.E3Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 数据字典Controller
 * <p>Title: CatController</p>
 * <p>Description: </p>
 * <p>Company: www.itcast.cn</p>
 *
 * @version 1.0
 */
@Controller
public class CategoryController {

    @Autowired
    private CategoryService categoryServiceImpl;

    /**
     * 查询数据字典(全部)
     * @param parentId
     * @return
     */
    @RequestMapping("/category/list")
    @ResponseBody
    public List<EasyUITreeNode> getCatList(
            @RequestParam(name = "id", defaultValue = "0") Long parentId) {
        List<EasyUITreeNode> list = categoryServiceImpl.getContentCatList(parentId);
        return list;
    }

    /**
     * 查询数据字典(课程分类)
     * @param parentId
     * @return
     */
    @RequestMapping("/category/getCourseList")
    @ResponseBody
    public List<EasyUITreeNode> getCourseList(
            @RequestParam(name = "id", defaultValue = "2") Long parentId) {
        List<EasyUITreeNode> list = categoryServiceImpl.getContentCatList(parentId);
        return list;
    }
    /**
     * 查询数据字典(测算服务)
     * @param parentId
     * @return
     */
    @RequestMapping("/category/getServiceList")
    @ResponseBody
    public List<EasyUITreeNode> getServiceList(
            @RequestParam(name = "id", defaultValue = "24") Long parentId) {
        List<EasyUITreeNode> list = categoryServiceImpl.getContentCatList(parentId);
        return list;
    }

    /**
     * 查询数据字典(课程级别)
     * @param parentId
     * @return
     */
    @RequestMapping("/category/getCourseLevelList")
    @ResponseBody
    public List<EasyUITreeNode> getCourseLevelList(
            @RequestParam(name = "id", defaultValue = "3") Long parentId) {
        List<EasyUITreeNode> list = categoryServiceImpl.getContentCatList(parentId);
        return list;
    }

    /**
     * 查询数据字典(授课方式)
     * @param parentId
     * @return
     */
    @RequestMapping("/category/getTeachMethods")
    @ResponseBody
    public List<EasyUITreeNode> getTeachMethods(
            @RequestParam(name = "id", defaultValue = "4") Long parentId) {
        List<EasyUITreeNode> list = categoryServiceImpl.getContentCatList(parentId);
        return list;
    }


    /**
     * 添加分类节点
     */
    @RequestMapping(value = "/category/create", method = RequestMethod.POST)
    @ResponseBody
    public E3Result createCategory(Long parentId, String name) {
        //调用服务添加节点
        E3Result e3Result = categoryServiceImpl.addContentCategory(parentId, name);
        return e3Result;
    }

    /**
     * 修改分类节点
     */
    @RequestMapping(value = "/category/update", method = RequestMethod.POST)
    @ResponseBody
    public boolean editCategory(Long id, String name) {
        //调用服务添加节点
        return categoryServiceImpl.editCategory(id, name);
    }

    /**
     * 删除分类节点（逻辑删除）
     */
    @RequestMapping(value = "/category/delete", method = RequestMethod.POST)
    @ResponseBody
    public boolean deleteCategory(Long id) {
        return categoryServiceImpl.deleteCategory(id);
    }

}
