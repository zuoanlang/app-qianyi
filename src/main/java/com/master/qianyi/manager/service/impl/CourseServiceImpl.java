package com.master.qianyi.manager.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.master.qianyi.manager.pojo.EasyUITreeNode;
import com.master.qianyi.manager.service.CourseManService;
import com.master.qianyi.mapper.TbCatalogMapper;
import com.master.qianyi.mapper.TbCategoryMapper;
import com.master.qianyi.mapper.TbCourseMapper;
import com.master.qianyi.pojo.*;
import com.master.qianyi.user.service.UserService;
import com.master.qianyi.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class CourseServiceImpl implements CourseManService {

    @Autowired
    private TbCatalogMapper catalogMapper;

    @Autowired
    private TbCourseMapper tbCourseMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private TbCategoryMapper tbCategoryMapper;

    /**
     * 获取课程目录
     *
     * @param parentId
     * @return
     */
    @Override
    public List<EasyUITreeNode> getContentCatList(long parentId, String courseId) {
        // 根据parentid查询子节点列表
        TbCatalogExample example = new TbCatalogExample();
        TbCatalogExample.Criteria criteria = example.createCriteria();
        //设置查询条件
        criteria.andCatalogParentIdEqualTo(parentId).andDeleteFlagEqualTo("0")
                .andCourseIdEqualTo(courseId).andEffectFlagEqualTo("1");

        //执行查询
        List<TbCatalog> tbCatalogs = catalogMapper.selectByExample(example);
        //转换成EasyUITreeNode的列表
        List<EasyUITreeNode> nodeList = new ArrayList<>();
        for (TbCatalog tbCatelog : tbCatalogs) {
            EasyUITreeNode node = new EasyUITreeNode();
            node.setId(tbCatelog.getCatalogId());
            node.setText(tbCatelog.getCatalogName());
            node.setState(tbCatelog.getIsParent() ? "closed" : "open");
            //添加到列表
            nodeList.add(node);
        }
        return nodeList;
    }

    /**
     * 添加目录
     *
     * @param parentId
     * @param name
     * @return
     */
    @Override
    public E3Result addContentCategory(long parentId, String name, String courseId) {
        //创建一个tb__category表对应的pojo对象
        TbCatalog catalog = new TbCatalog();
        //设置pojo的属性
        catalog.setCatalogParentId(parentId);
        catalog.setCatalogName(name);
        //排序
        int number = getNodeNumOfParent(parentId);
        catalog.setCatalogOrder(getNodeNumOfParent(parentId) + 1);
        catalog.setCourseId(courseId);
        catalog.setDeleteFlag("0");
        catalog.setEffectFlag("1");
        //新添加的节点一定是叶子节点
        catalog.setIsParent(false);
        //处理新节点插入（前三节点免费1，后面收费0）
        if (number <= 3) {
            catalog.setIsAuditioning("1");
        }
        //插入到数据库
        int insert = catalogMapper.insertSelective(catalog);
        //判断父节点的isparent属性。如果不是true改为true
        //根据parentid查询父节点
        TbCatalog parent = catalogMapper.selectByPrimaryKey(parentId);

        if (!parent.getIsParent()) {
            parent.setIsParent(true);
            //更新到数数据库
            catalogMapper.updateByPrimaryKeySelective(parent);
        }
        TbCatalogExample example = new TbCatalogExample();
        TbCatalogExample.Criteria criteria = example.createCriteria();
        criteria.andCatalogIdIsNotNull();
        example.setOrderByClause("catalog_id desc");
        PageHelper.startPage(1, 1);
        List<TbCatalog> catalogs = catalogMapper.selectByExample(example);
        catalog.setCatalogId(catalogs.get(0).getCatalogId());
        //返回结果，返回E3Result，包含pojo
        return E3Result.ok(catalog);
    }

    /**
     * 编辑目录
     *
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
        if (i > 0) {
            return true;
        }
        return false;
    }

    /**
     * 删除目录
     *
     * @param id
     * @return
     */
    @Override
    public boolean deleteCategory(long id) {
        // 根据parentid查询子节点列表
        TbCatalogExample example = new TbCatalogExample();
        //设置删除条件(逻辑删除)
        //1. 根据id查询节点
        TbCatalog catalog = catalogMapper.selectByPrimaryKey(id);
        catalog.setDeleteFlag("1");
        int delete = catalogMapper.updateByPrimaryKeySelective(catalog);
        if (delete > 0) {
            return true;
        }
        return false;
    }

    /**
     * 查询当前节点的下的子节点数目
     *
     * @return int
     */
    private int getNodeNumOfParent(Long parentId) {
        // 根据parentid查询子节点列表
        TbCatalogExample example = new TbCatalogExample();
        TbCatalogExample.Criteria criteria = example.createCriteria();
        //设置查询条件
        criteria.andCatalogParentIdEqualTo(parentId);
        //执行查询
        List<TbCatalog> catList = catalogMapper.selectByExample(example);

        return catList.size();
    }

    /**
     * 保存上传的课程信息
     *
     * @return
     */
    @Transactional
    public ResultBean saveCourseInfo(TbCourseWithBLOBs course) {
        ResultBean bean = new ResultBean();
        //查询当前用户是否存在
        String userId = getUserByIdCardNo(course.getIdCardNo(), course.getUserName());
        if (userId == null) {
            bean.setMsg(Constants.msg_failed);
            bean.setCode(Constants.code_1);
            bean.setResult("当前用户不存在，请检查身份证号码是否与姓名匹配，或者确保该用户已经入驻");
        } else {
            //公共字段填充
            course.setEffectFlag("2");
            course.setCreateTime(new Date());
            course.setUpdateTime(new Date());
            course.setIsCarousel("0");
            course.setCourseIsHot("1");

            //查询数据字典表
            //课程类型id->name
            String courseTypeName = getCategoryName(Long.parseLong(course.getCourseType()));
            course.setRemark1(course.getCourseType());
            course.setCourseType(courseTypeName);
            //课程等级id->name
            String courseLevelName = getCategoryName(Long.parseLong(course.getCourseLevel()));
            course.setRemark2(course.getCourseLevel());
            course.setCourseLevel(courseLevelName);
            //是否线上id->name
            String courseIsOnlineName = getCategoryName(Long.parseLong(course.getCourseIsOnline()));
            course.setRemark3(course.getCourseIsOnline());
            course.setCourseIsOnline(courseIsOnlineName);

            course.setCourseBelongto(userId);
            course.setCourseLearningFrequency(new Long(0));
            int update = tbCourseMapper.updateByPrimaryKeySelective(course);
            if (update > 0) {
                bean.setResult(null);
                bean.setCode(Constants.code_0);
                bean.setMsg(Constants.msg_success);
            } else {
                bean.setResult(null);
                bean.setCode(Constants.code_1);
                bean.setMsg(Constants.msg_failed);
            }
        }

        return bean;
    }

    @Override
    public EasyUIDataGridResult getCourseList(int pageNum, int pageSize, TbCourse course) {
        EasyUIDataGridResult result = new EasyUIDataGridResult();
        TbCourseExample example = new TbCourseExample();
        TbCourseExample.Criteria criteria = example.createCriteria();
        criteria.andEffectFlagNotEqualTo("0").andDeleteFlagEqualTo("0");
        if (StringUtil.isNotEmpty(course.getCourseId())) {
            criteria.andCourseIdLike("%" + course.getCourseId() + "%");
        }
        if (StringUtil.isNotEmpty(course.getCourseName())) {
            criteria.andCourseNameLike("%" + course.getCourseName() + "%");
        }
        if (StringUtil.isNotEmpty(course.getCourseType())) {
            criteria.andCourseTypeLike("%" + course.getCourseType() + "%");
        }
        if (StringUtil.isNotEmpty(course.getCourseIsOnline())) {
            criteria.andCourseIsOnlineEqualTo(course.getCourseIsOnline());
        }
        if (StringUtil.isNotEmpty(course.getUserName())) {
            criteria.andUserNameLike("%" + course.getUserName() + "%");
        }
//        criteria.andCourseIsOnlineEqualTo("线上");
        example.setOrderByClause("is_carousel desc, create_time desc");
        PageHelper.startPage(pageNum, pageSize);
        List<TbCourseWithBLOBs> courseList = tbCourseMapper.selectByExampleWithBLOBs(example);
        PageInfo<TbCourseWithBLOBs> pageInfo = new PageInfo<>(courseList);
        result.setRows(courseList);
        result.setTotal(pageInfo.getTotal());
        return result;

    }

    /**
     * 新增课程时,先获取课程id,用于课程目录的存储
     *
     * @return
     */
    @Override
    @Transactional
    public Map<String, String> getCourseId() {
        Map<String, String> courseIdMap = new HashMap<>();
        TbCourseExample courseExample = new TbCourseExample();
        TbCourseExample.Criteria criteria = courseExample.createCriteria();
        criteria.andEffectFlagEqualTo("0")
                .andDeleteFlagEqualTo("0")
                .andCourseIsOnlineEqualTo("线上");
        courseExample.setOrderByClause("create_time desc");
        List<TbCourse> courseList = tbCourseMapper.selectByExample(courseExample);
        String courseId;
        String code = "1";
        if (courseList == null || courseList.size() == 0) {
            //1.不存在留存的课程id,则新建课程id
            TbCourseWithBLOBs tbCourse = new TbCourseWithBLOBs();
            courseId = IDUtils.genItemId();
            tbCourse.setCourseId(courseId);
            int insert = tbCourseMapper.insertSelective(tbCourse);
            if (insert == 0) {
                courseIdMap.put("msg", "课程初始化失败，请稍后重试！");
                courseIdMap.put("code", code);
                return courseIdMap;
            }
            code = "0";
            //2.新建目录id-courseId,用于初始化课程目录
            TbCatalog catalog = new TbCatalog();
            catalog.setCourseId(courseId);
            catalog.setCatalogParentId(Long.parseLong(courseId));
            catalog.setCatalogName("课程名称(请修改为对应的课程名称)");
            int selective = catalogMapper.insertSelective(catalog);
            if (selective <= 0) {
                courseIdMap.put("msg", "目录初始化失败，请稍后重试！");
                courseIdMap.put("code", code);
                return courseIdMap;
            }
        } else {
            code = "0";
            courseId = courseList.get(0).getCourseId();
        }
        courseIdMap.put("courseId", courseId);
        courseIdMap.put("code", code);
        return courseIdMap;
    }

    @Override
    @Transactional
    public ResultBean editCourseInfo(TbCourseWithBLOBs course) {
        ResultBean bean = new ResultBean();
        //查询当前用户是否存在
        String userId = getUserByIdCardNo(course.getIdCardNo(), course.getUserName());
        if (userId == null) {
            bean.setMsg(Constants.msg_failed);
            bean.setCode(Constants.code_1);
            bean.setResult("当前用户不存在，请检查身份证号码是否与姓名匹配，或者确保该用户已经入驻");
        } else {

            //查询数据字典表
            //课程类型id->name
            String courseTypeName = getCategoryName(Long.parseLong(course.getCourseType()));
            course.setRemark1(course.getCourseType());
            course.setCourseType(courseTypeName);
            //课程等级id->name
            String courseLevelName = getCategoryName(Long.parseLong(course.getCourseLevel()));
            course.setRemark2(course.getCourseLevel());
            course.setCourseLevel(courseLevelName);
            //是否线上id->name
            String courseIsOnlineName = getCategoryName(Long.parseLong(course.getCourseIsOnline()));
            course.setRemark3(course.getCourseIsOnline());
            course.setCourseIsOnline(courseIsOnlineName);

            //公共字段填充
            course.setEffectFlag("1");
            course.setUpdateTime(new Date());

            course.setCourseBelongto(userId);
            int update = tbCourseMapper.updateByPrimaryKeySelective(course);
            if (update > 0) {
                bean.setResult(null);
                bean.setCode(Constants.code_0);
                bean.setMsg(Constants.msg_success);
            } else {
                bean.setResult(null);
                bean.setCode(Constants.code_1);
                bean.setMsg(Constants.msg_failed);
            }
        }

        return bean;
    }

    @Override
    public Map<String, String> getCatalogVideo(long catalogId, String type) {
        TbCatalog catalog = catalogMapper.selectByPrimaryKey(catalogId);
        Map<String, String> videoInfoMap = new HashMap<>();
        String videoName = "";
        String videoDraft = "";
        String videoTime = "";
        if (type.equals("edit")) {
            videoName = "videoNameEdit";
            videoDraft = "videoDraftEdit";
            videoTime = "videoTimeEdit";
        } else {
            videoName = "videoName";
            videoDraft = "videoDraft";
            videoTime = "videoTime";
        }
        if (catalog != null) {
            if (catalog.getVideoName() == null) {
                videoInfoMap.put(videoName, "视频未上传");
            } else {
                videoInfoMap.put(videoName, catalog.getVideoName());
            }

            if (catalog.getVideoDraft() == null) {
                videoInfoMap.put(videoDraft, "该目录章节的课件文稿未编辑");
            } else {
                videoInfoMap.put(videoDraft, catalog.getVideoDraft());
            }

            if (catalog.getVideoTime() == null) {
                videoInfoMap.put(videoTime, "00:00:00");
            } else {
                videoInfoMap.put(videoTime, catalog.getVideoTime());
            }
            videoInfoMap.put("code", "0");
            return videoInfoMap;
        }
        videoInfoMap.put("code", "1");
        return new HashMap<>();
    }

    @Override
    public Map<String, String> uploadVideoName(long id, String videoName, String videoPath, String videoTime, String isAuditioning) {
        Map<String, String> infoMap = new HashMap<>();
        TbCatalog catalog = new TbCatalog();
        catalog.setCatalogId(id);
        catalog.setVideoName(videoName);
        catalog.setVideoPath(videoPath);
        catalog.setVideoTime(videoTime);
        catalog.setIsAuditioning(isAuditioning);
        int update = catalogMapper.updateByPrimaryKeySelective(catalog);
        if (update > 0) {
            infoMap.put("code", "0");
        } else {
            infoMap.put("code", "1");
        }
        return infoMap;
    }

    @Override
    public Map<String, String> updateDraft(long id, String draft) {
        Map<String, String> infoMap = new HashMap<>();
        TbCatalog catalog = new TbCatalog();
        catalog.setCatalogId(id);
        catalog.setVideoDraft(draft);
        if (catalogMapper.updateByPrimaryKeySelective(catalog) > 0) {
            infoMap.put("code", "0");
        } else {
            infoMap.put("code", "1");
        }
        return infoMap;

    }

    @Override
    @Transactional
    public Map<String, String> deleteCourse(String courseId) {
        Map<String, String> infoMap = new HashMap<>();
        TbCourseWithBLOBs course = tbCourseMapper.selectByPrimaryKey(courseId);
        if (course != null && course.getCourseId() != null) {
            //1.删除课程（逻辑删除）
            course.setDeleteFlag("1");
            int delete = tbCourseMapper.updateByPrimaryKeySelective(course);
            //2.删除对应的课程目录
            if (delete > 0) {
                TbCatalog catalog = new TbCatalog();
                catalog.setDeleteFlag("1");
                TbCatalogExample example = new TbCatalogExample();
                TbCatalogExample.Criteria criteria = example.createCriteria();
                criteria.andCourseIdEqualTo(courseId);
                catalogMapper.updateByExampleSelective(catalog, example);
                infoMap.put("code", "0");
                infoMap.put("msg", "删除成功!");
                return infoMap;
            }

        }
        infoMap.put("code", "1");
        infoMap.put("msg", "删除失败!");
        return infoMap;
    }

    @Override
    public Map<String, String> updateEffectStatus(String courseId, String type) {
        Map<String, String> infoMap = new HashMap<>();
        TbCourseWithBLOBs course = new TbCourseWithBLOBs();
        course.setCourseId(courseId);
        if (type.equals("1") || type.equals("2")) {
            //上架
            course.setEffectFlag(type);
            int update = tbCourseMapper.updateByPrimaryKeySelective(course);
            if (update > 0) {
                infoMap.put("code", "0");
                return infoMap;
            }
        }
        infoMap.put("code", "1");
        return infoMap;
    }

    @Override
    public Map<String, String> carousel(String courseId, String type) {
        Map<String, String> infoMap = new HashMap<>();
        TbCourseWithBLOBs course = new TbCourseWithBLOBs();
        course.setCourseId(courseId);
        if (type.equals("0") || type.equals("1")) {
            //上架
            course.setIsCarousel(type);
            int update = tbCourseMapper.updateByPrimaryKeySelective(course);
            if (update > 0) {
                infoMap.put("status", "200");
                return infoMap;
            }
        }
        infoMap.put("status", "-200");
        return infoMap;
    }

    /**
     * 获取入驻名师的用户名
     *
     * @return
     */
    private String getUserByIdCardNo(String idCardNo, String userName) {
        TbUser user = userService.getUserByIdCardNo(idCardNo, userName);
        if (user.getUserId() != null) {
            return user.getUserId();
        }
        return null;
    }

    public String getCategoryName(Long id) {
        TbCategory tbCategory = tbCategoryMapper.selectByPrimaryKey(id);
        if (tbCategory != null) {
            return tbCategory.getName();
        }
        return null;
    }
}
