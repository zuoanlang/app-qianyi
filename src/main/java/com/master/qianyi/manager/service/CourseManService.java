package com.master.qianyi.manager.service;

import com.master.qianyi.manager.pojo.EasyUITreeNode;
import com.master.qianyi.pojo.TbCourse;
import com.master.qianyi.pojo.TbCourseWithBLOBs;
import com.master.qianyi.utils.E3Result;
import com.master.qianyi.utils.EasyUIDataGridResult;
import com.master.qianyi.utils.ResultBean;

import java.util.List;
import java.util.Map;

public interface CourseManService {

    List<EasyUITreeNode> getContentCatList(long parentId,String courseId);
    E3Result addContentCategory(long parentId, String name,String courseId);

    /**
     * 修改节点名称
     * @param parentId
     * @param name
     * @return
     */
    boolean editCategory(long id, String name);

    /**
     * 删除节点
     * @param id
     * @return
     */
    boolean deleteCategory(long id);

    ResultBean saveCourseInfo(TbCourseWithBLOBs course);

    EasyUIDataGridResult getCourseList(int pageNum, int pageSize,TbCourse course);

    Map<String,String> getCourseId();

    ResultBean editCourseInfo(TbCourseWithBLOBs course);

    Map<String, String> getCatalogVideo(long catalogId,String type);

    Map<String, String> uploadVideoName(long id,String videoName,String videoPath,String videoTime);

    Map<String, String> updateDraft(long id, String draft);

    Map<String, String> deleteCourse(String courseId);

    Map<String, String> updateEffectStatus(String courseId, String type);

    Map<String, String> carousel(String courseId, String type);
}
