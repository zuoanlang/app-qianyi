package com.master.qianyi.manager.service.impl;

import com.master.qianyi.manager.pojo.EasyUITreeNode;
import com.master.qianyi.manager.service.CourseManService;
import com.master.qianyi.mapper.TbCatalogMapper;
import com.master.qianyi.pojo.TbCatalog;
import com.master.qianyi.pojo.TbCatalogExample;
import com.master.qianyi.utils.E3Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class CourseServiceImpl implements CourseManService {

    @Autowired
    private TbCatalogMapper catalogMapper;
    /**
     * 获取课程目录
     * @param parentId
     * @return
     */
    @Override
    public List<EasyUITreeNode> getContentCatList(long parentId) {
        // 根据parentid查询子节点列表
        TbCatalogExample example = new TbCatalogExample();
        TbCatalogExample.Criteria criteria = example.createCriteria();
        //设置查询条件
        criteria.andCatalogParentIdEqualTo(parentId).andDeleteFlagEqualTo("0")
                .andCourseIdEqualTo("0").andEffectFlagEqualTo("1");

        //执行查询
        List<TbCatalog> tbCatalogs = catalogMapper.selectByExample(example);
        //转换成EasyUITreeNode的列表
        List<EasyUITreeNode> nodeList = new ArrayList<>();
        for (TbCatalog tbCatelog : tbCatalogs) {
            EasyUITreeNode node = new EasyUITreeNode();
            node.setId(tbCatelog.getCatalogId());
            node.setText(tbCatelog.getCatalogName());
            node.setState(tbCatelog.getIsParent()?"closed":"open");
            //添加到列表
            nodeList.add(node);
        }
        return nodeList;
    }

    /**
     * 添加目录
     * @param parentId
     * @param name
     * @return
     */
    @Override
    public E3Result addContentCategory(long parentId, String name) {
        //创建一个tb__category表对应的pojo对象
        TbCatalog catalog = new TbCatalog();
        //设置pojo的属性
        catalog.setCatalogParentId(parentId);
        catalog.setCatalogName(name);
        //排序
        catalog.setCatalogOrder(getNodeNumOfParent(parentId)+1);
        catalog.setCourseId("0");
        catalog.setDeleteFlag("0");
        catalog.setEffectFlag("1");
        //新添加的节点一定是叶子节点
        catalog.setIsParent(false);
        //插入到数据库
        catalogMapper.insert(catalog);
        //判断父节点的isparent属性。如果不是true改为true
        //根据parentid查询父节点
        TbCatalog parent = catalogMapper.selectByPrimaryKey(parentId);
        if (!parent.getIsParent()) {
            parent.setIsParent(true);
            //更新到数数据库
            catalogMapper.updateByPrimaryKey(parent);
        }
        //返回结果，返回E3Result，包含pojo
        return E3Result.ok(catalog);
    }

    /**
     * 编辑目录
     * @param id
     * @param name
     * @return
     */
    @Override
    public boolean editCategory(long id, String name) {
        //1. 根据id查询节点
        TbCatalog tbCatalog = catalogMapper.selectByPrimaryKey(id);
        //2. 设置更新条件
        tbCatalog.setCatalogName(name);
        //3. 跟新节点
        TbCatalogExample example = new TbCatalogExample();
        int i = catalogMapper.updateByPrimaryKeySelective(tbCatalog);
        if(i>0){
            return true;
        }
        return false;
    }

    /**
     * 删除目录
     * @param id
     * @return
     */
    @Override
    public boolean deleteCategory(long id) {
        // 根据parentid查询子节点列表
        TbCatalogExample example = new TbCatalogExample();
        //设置删除条件(逻辑删除)
        //1. 根据id查询节点
        TbCatalog tbCatalog = catalogMapper.selectByPrimaryKey(id);
        tbCatalog.setDeleteFlag("1");
        int i = catalogMapper.updateByPrimaryKeySelective(tbCatalog);
        if(i>0){
            return true;
        }
        return false;
    }

    /**
     * 查询当前节点的下的子节点数目
     * @return int
     */
    private int getNodeNumOfParent(Long parentId){
        // 根据parentid查询子节点列表
        TbCatalogExample example = new TbCatalogExample();
        TbCatalogExample.Criteria criteria = example.createCriteria();
        //设置查询条件
        criteria.andCatalogIdEqualTo(parentId);
        //执行查询
        List<TbCatalog> catList = catalogMapper.selectByExample(example);

        return catList.size();
    }
}
