package com.master.qianyi.manager.service;

import com.master.qianyi.manager.pojo.EasyUITreeNode;
import com.master.qianyi.utils.E3Result;

import java.util.List;

public interface CourseManService {

    List<EasyUITreeNode> getContentCatList(long parentId);
    E3Result addContentCategory(long parentId, String name);

    /**
     * 修改节点名称
     * @param parentId
     * @param name
     * @return
     */
    public boolean editCategory(long id, String name);

    /**
     * 删除节点
     * @param parentId
     * @param name
     * @return
     */
    public boolean deleteCategory(long id);
}
