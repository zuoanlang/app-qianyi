package com.master.qianyi.course.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.util.StringUtil;
import com.master.qianyi.course.form.*;
import com.master.qianyi.mapper.*;
import com.master.qianyi.pojo.*;
import com.master.qianyi.user.service.UserService;
import com.master.qianyi.utils.Constants;
import com.master.qianyi.utils.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 课程service
 */
@Service
public class CourseService {

    @Autowired
    private TbCourseMapper tbCourseMapper;

    @Autowired
    private TbUserMapper tbUserMapper;

    @Autowired
    private TbOrderMapper tbOrderMapper;

    @Autowired
    private TbCatalogMapper tbCatalogMapper;

    @Autowired
    private TbCategoryMapper tbCategoryMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private TbServiceMapper serviceMapper;

    @Autowired
    private TbLearningMapper learningMapper;

    @Autowired
    private TbEnrollMapper enrollMapper;


    /**
     * 根据条件查询课程
     *
     * @param pageNum  当前页码
     * @param pageSize 每页条数
     * @param course   查询条件
     * @return
     */
    public ResultBean getCourseSearchResult(int pageNum, int pageSize, TbCourse course) {
        ResultBean bean = new ResultBean();
        TbCourseExample example = new TbCourseExample();
        // 有效标志为1（有效），删除标志为0（未删除）
        example.createCriteria().andEffectFlagEqualTo("1").andDeleteFlagEqualTo("0");
        // 课程名称
        if (StringUtil.isNotEmpty(course.getCourseName())) {
            example.getOredCriteria().get(0).andCourseNameLike("%" + course.getCourseName() + "%");
        }
        // 课程类别
        if (StringUtil.isNotEmpty(course.getCourseType())) {
            example.getOredCriteria().get(0).andCourseTypeEqualTo(course.getCourseType());
        }
        // 课程级别
        if (StringUtil.isNotEmpty(course.getCourseLevel())) {
            example.getOredCriteria().get(0).andCourseLevelEqualTo(course.getCourseLevel());
        }
        // 课程是线上
        example.getOredCriteria().get(0).andCourseIsOnlineEqualTo("线上");
        // 按热度降序
        example.setOrderByClause("COURSE_LEARNING_FREQUENCY desc");
        // 设置分页
        PageHelper.startPage(pageNum, pageSize);
        // 查询结果集
        List<TbCourse> tbCourses = tbCourseMapper.selectByExample(example);
        //get tbUsers info by userIds
        List<String> userIds = new ArrayList<>();
        List<CourseBrief> courseBriefs = null;
        if(tbCourses != null && tbCourses.size()>0){

            for (TbCourse tbCourse : tbCourses) {
                userIds.add(tbCourse.getCourseBelongto());
            }
            List<TbUser> usersByUserIds = userService.getUsersByUserIds(userIds);

            courseBriefs = showBackCourseList(tbCourses,usersByUserIds);
        } else {
            courseBriefs = new ArrayList<>();
        }

        bean.setCode(Constants.code_0);
        bean.setMsg(Constants.msg_success);
        bean.setResult(courseBriefs);
        return bean;
    }

    /**
     * 构造界面显示的课程列表
     * @param tbCourses
     * @param usersByUserIds
     * @return List<CourseBrief>
     */
    public List<CourseBrief> showBackCourseList(List<TbCourse> tbCourses,List<TbUser> usersByUserIds){
        List<CourseBrief> courseBriefList = new ArrayList<>();
        CourseBrief brief;
        Map<String, TbUser> userMap = userService.getIdUserMap(usersByUserIds);
        for (TbCourse course : tbCourses) {
            brief = new CourseBrief();
            brief.setCourseId(course.getCourseId());
            brief.setCourseImg(course.getCourseImg());
            brief.setCourseName(course.getCourseName());
            if (userMap.containsKey(course.getCourseBelongto())){
                brief.setUserName(userMap.get(course.getCourseBelongto()).getUserName());
                brief.setMasterIntroduction(userMap.get(course.getCourseBelongto()).getMasterIntroduction());
            } else {
                brief.setUserName("未知");
                brief.setMasterIntroduction("暂无");
            }

            brief.setCourseLearningFrequency(course.getCourseLearningFrequency());
            brief.setCoursePrice(course.getCoursePrice());
            courseBriefList.add(brief);
        }
        return courseBriefList;

    }

    /**
     * 根据用户id查询该用户已购买的课程
     *
     * @param userId
     * @param pageNum
     * @param pageSize
     * @return
     */
    public ResultBean getCourseByUserId(String userId, String token,int pageNum, int pageSize) {
        ResultBean bean = new ResultBean();
        //1.身份验证
        TbUser user = tbUserMapper.selectByPrimaryKey(userId);
        if (!user.getToken().equals(token)) {
            bean.setCode(Constants.code_2);
            bean.setMsg(Constants.msg_invalid);
            return bean;
        }
        // 1.根据用户id查询订单表
        TbOrderExample tbOrderExample = new TbOrderExample();
        TbOrderExample.Criteria criteria = tbOrderExample.createCriteria();
        // 2.该用户已付款的订单（订单状态：0,全部订单，1已下单，2已付款，3已评价，4待审核，5已退款，6已失效）
        criteria.andUserIdEqualTo(userId).andOrderStatusIn(Constants.ordered_Status_LIST)
                .andEffectFlagEqualTo("1")
                .andDeleteFlagEqualTo("0");
        // 3.订单支付时间降序
        tbOrderExample.setOrderByClause("ORDER_PAY_TIME desc");
        PageHelper.startPage(pageNum,pageSize);
        // 4.关联查询课程表信息
        List<TbOrder> tbOrders = tbOrderMapper.selectByExample(tbOrderExample);
        List<String> courseIdList = new ArrayList<>();
        if (tbOrders.size() == 0) {
            bean.setCode(Constants.code_0);
            bean.setMsg("暂无课程，快去购买吧");
            bean.setResult(new ArrayList<>());
            return bean;
        } else {
            for (TbOrder order : tbOrders) {
                courseIdList.add(order.getGoodId());
            }
        }
        TbCourseExample tbCourseExample = new TbCourseExample();
        tbCourseExample.createCriteria()
                .andEffectFlagEqualTo("1")
                .andDeleteFlagEqualTo("0")
                .andCourseIdIn(courseIdList);
        List<TbCourse> tbCourses = tbCourseMapper.selectByExample(tbCourseExample);
        // 5.根据名师id查询名师列表
        List<String> masterIdList = new ArrayList<>();
        for (TbCourse course:tbCourses){
            masterIdList.add(course.getCourseBelongto());
        }
        List<TbUser> usersByUserIds = userService.getUsersByUserIds(masterIdList);
        List<MyCourse> myCourseList = showBackMyCourseList(tbCourses, usersByUserIds,userId);
        bean.setCode(Constants.code_0);
        bean.setMsg(Constants.msg_success);
        bean.setResult(myCourseList);
        return bean;
    }

    private List<MyCourse> showBackMyCourseList(List<TbCourse> tbCourses, List<TbUser> usersByUserIds,String userId) {
        //查询学习表，获取最后一次观看的视频信息
        List<String> courseIdList = new ArrayList<>();
        for (TbCourse course:tbCourses) {
            courseIdList.add(course.getCourseId());
        }
        Map<String, TbLearning> lastVideoInfoMap = getLastVideoInfoMap(userId, courseIdList);
        List<MyCourse> myCourseList = new ArrayList<>();
        MyCourse myCourse;
        Map<String, TbUser> userMap = userService.getIdUserMap(usersByUserIds);
        for (TbCourse course : tbCourses) {
            myCourse = new MyCourse();
            myCourse.setCourseId(course.getCourseId());
            myCourse.setCourseImg(course.getCourseImg());
            myCourse.setCourseName(course.getCourseName());
            myCourse.setUserName(userMap.get(course.getCourseBelongto()).getUserName());
            myCourse.setCourseLearningFrequency(course.getCourseLearningFrequency());
            if(lastVideoInfoMap.containsKey(course.getCourseId())){
                myCourse.setIsLearning(1);
                myCourse.setCatalogId(lastVideoInfoMap.get(course.getCourseId()).getCatalogId());
                myCourse.setLastLearningTime(String.valueOf(lastVideoInfoMap.get(course.getCourseId())
                        .getUpdateTime().getTime()));
                myCourse.setLastLearningPercent(lastVideoInfoMap.get(course.getCourseId()).getLearningPercent());
            } else {
                //没有进度
                myCourse.setCatalogId(null);
                myCourse.setLastLearningTime(null);
                myCourse.setLastLearningPercent(0);
                myCourse.setIsLearning(0);
            }

            myCourseList.add(myCourse);
        }
        return myCourseList;
    }

    private Map<String,TbLearning> getLastVideoInfoMap(String userId,List<String> courseIds) {
        TbLearningExample learningExample = new TbLearningExample();
        TbLearningExample.Criteria criteria = learningExample.createCriteria();
        criteria.andUserIdEqualTo(userId).andCourseIdIn(courseIds);
        learningExample.setOrderByClause("update_time desc");
        PageHelper.startPage(1,1);
        List<TbLearning> tbLearnings = learningMapper.selectByExample(learningExample);
        Map<String,TbLearning> idLearningMap = new HashMap<>();
        if(tbLearnings.size() > 0){
            for (String id :courseIds){
                for (int i = 0; i <tbLearnings.size() ; i++) {
                    if(tbLearnings.get(i).getCourseId().equals(id)){
                        if(!idLearningMap.containsKey(id)){
                            idLearningMap.put(id,tbLearnings.get(i));
                        }
                    }
                }
            }
        }
        return idLearningMap;
    }

    /**
     * 根据课程id查询课程信息
     * (课程+目录)
     *
     * @param courseId
     * @return
     */
    public ResultBean getDetailsByCourseId(String courseId,String userId) {
        TbCourseExample tbCourseExample = new TbCourseExample();
        tbCourseExample.createCriteria()
                .andCourseIdEqualTo(courseId)
                .andEffectFlagEqualTo("1").andDeleteFlagEqualTo("0");
        List<TbCourse> courseList = tbCourseMapper.selectByExample(tbCourseExample);
        ResultBean bean = new ResultBean();
        if (courseList != null && courseList.size() > 0) {

            //1.get courseDetails obj
            //1.1 get tbCourse obj
            TbCourse tbCourse = courseList.get(0);
            //1.2 get tbUser obj
            TbUser tbUser = tbUserMapper.selectByPrimaryKey(tbCourse.getCourseBelongto());
            //1.3 check course buy or not
            String isPurchased = "0";
            if(StringUtil.isNotEmpty(userId)){
                List<String> statusList = new ArrayList<>();
                statusList.add("2");
                statusList.add("3");
                statusList.add("4");
                TbOrderExample orderExample = new TbOrderExample();
                TbOrderExample.Criteria criteria = orderExample.createCriteria();
                criteria.andDeleteFlagEqualTo("0").andEffectFlagEqualTo("1")
                        .andGoodIdEqualTo(courseId).andUserIdEqualTo(userId).andOrderStatusIn(statusList);
                List<TbOrder> orderList = tbOrderMapper.selectByExample(orderExample);
                if(orderList.size()==1){
                    isPurchased = "1";
                }
            }

            CourseDetails courseDetails = showBackCourseDetails(tbCourse, tbUser);
            courseDetails.setIsPurchased(isPurchased);
//            //2.get catalogList
//            TbCatalogExample tbCatalogExample = new TbCatalogExample();
//            tbCatalogExample.createCriteria().andEffectFlagEqualTo("1")
//                    .andDeleteFlagEqualTo("0").andCourseIdEqualTo(courseId);
//            List<TbCatalog> tbCatalogs = tbCatalogMapper.selectByExample(tbCatalogExample);
//            if (tbCatalogs != null && tbCatalogs.size() > 0) {
//                //tbCourse.setCatalogList(tbCatalogs);
//            }

            bean.setCode(Constants.code_0);
            bean.setMsg(Constants.msg_success);
            bean.setResult(courseDetails);
        }

        return bean;
    }

    /**
     * 构建课程详细信息实体类
     * @param tbCourse
     * @param tbUser
     * @return
     */
    public CourseDetails showBackCourseDetails(TbCourse tbCourse, TbUser tbUser){
        CourseDetails details = new CourseDetails();

        details.setCourseImg(tbCourse.getCourseImg());
        details.setCourseName(tbCourse.getCourseName());
        details.setCourseDescription(tbCourse.getCourseDescription());

        //需要去查询tbCatalog获取外层的点击数
        TbCatalogExample catalogExample = new TbCatalogExample();
        TbCatalogExample.Criteria criteria = catalogExample.createCriteria();
        criteria.andCourseIdEqualTo(tbCourse.getCourseId()).andDeleteFlagEqualTo("0").andEffectFlagEqualTo("1");
        Long times = 0l;
        List<TbCatalog> catalogs = tbCatalogMapper.selectByExample(catalogExample);
        for (TbCatalog catalog:catalogs) {
            times+=catalog.getLearningTimes();
        }
        details.setCourseLearningFrequency(times);
        details.setHeadImg(tbUser.getHeadImg());
        details.setUserName(tbUser.getUserName());
        details.setMasterIntroduction(tbUser.getMasterIntroduction());
        details.setCourseWays(tbCourse.getCourseWays());
        details.setCoursePrice(tbCourse.getCoursePrice());
        details.setCourseWelfare(tbCourse.getCourseWelfare());

        return details;

    }
    /**
     * addLearningTimes
     *
     * @param catalogId
     * @return
     */
    @Transactional
    public ResultBean addLearningTimes(Long catalogId) {
        ResultBean bean = new ResultBean();
        //1.更新目录的点击次数
        TbCatalog catalog = tbCatalogMapper.selectByPrimaryKey(catalogId);
        TbCatalog tbCatalog = new TbCatalog();
        tbCatalog.setCatalogId(catalogId);
        tbCatalog.setLearningTimes(catalog.getLearningTimes() + 1);
        int count = tbCatalogMapper.updateByPrimaryKeySelective(tbCatalog);
        if (count > 0) {
            //2.查询对应的课程，更新课程里总的点击次数
            TbCourseWithBLOBs tbCourse = tbCourseMapper.selectByPrimaryKey(catalog.getCourseId());
            tbCourse.setCourseLearningFrequency(tbCourse.getCourseLearningFrequency()+1);
            int update = tbCourseMapper.updateByPrimaryKeySelective(tbCourse);
            if(update>0){
                bean.setCode(Constants.code_0);
                bean.setMsg(Constants.msg_success);
            }
        }
        return bean;
    }

    /**
     * 获取首页轮播图
     *
     * @return
     */
    public ResultBean getCarouselList() {
        ResultBean bean = new ResultBean();
        TbCourseExample tbCourseExample = new TbCourseExample();
        //"1"是轮播,"0"不是
        tbCourseExample.createCriteria().andIsCarouselEqualTo("1").andEffectFlagEqualTo("1")
                .andDeleteFlagEqualTo("0");
        List<TbCourse> tbCourses = tbCourseMapper.selectByExample(tbCourseExample);
        List<CourseCarousel> carouselList = new ArrayList<>();
        if (tbCourses != null) {
            CourseCarousel courseCarousel = null;
            for (int i = 0; i < tbCourses.size(); i++) {
                courseCarousel = new CourseCarousel();
                courseCarousel.setCourseId(tbCourses.get(i).getCourseId());
                courseCarousel.setCourseImg(tbCourses.get(i).getCourseImg());
                courseCarousel.setCourseName(tbCourses.get(i).getCourseName());
                carouselList.add(courseCarousel);
            }
            bean.setMsg(Constants.msg_success);
            bean.setCode(Constants.code_0);
            bean.setResult(carouselList);
        } else {
            bean.setMsg(Constants.msg_success);
            bean.setCode(Constants.code_0);
            bean.setResult(null);
        }
        return bean;
    }

    /**
     * 取课程分类（名师入驻时的专业）
     * 课程分类的id:2
     * @return
     */
    public ResultBean getCourseType(){
        TbCategoryExample example = new TbCategoryExample();
        ResultBean bean = new ResultBean();
        //课程分类的id:24
        example.createCriteria().andParentIdEqualTo(Long.parseLong("2")).andStatusEqualTo(1);
        List<TbCategory> tbCategories = tbCategoryMapper.selectByExample(example);
        if(tbCategories != null){
            List<String> courseTypeSet = new ArrayList<>();
            for (int i = 0; i < tbCategories.size() ; i++) {
                courseTypeSet.add(tbCategories.get(i).getName());
            }
            Map<String,List> courseTypeMap = new HashMap<>();
            courseTypeMap.put("courseType",courseTypeSet);
            bean.setResult(courseTypeMap);
            bean.setMsg(Constants.msg_success);
            bean.setCode(Constants.code_0);
        } else {
            bean.setResult(null);
            bean.setMsg(Constants.msg_success);
            bean.setCode(Constants.code_0);
        }
        return bean;
    }

    /**
     * 根据课程courseId取课程目录
     * @param courseId
     * @return
     */
    public ResultBean getCourseCatalogList(String courseId,String userId,String token) {
        //验证用户信息
        // 1.验证当前用户登陆状态
        // 判断用户是否登陆以及用户登陆信息是否过期
        boolean userValite = false;
        if(StringUtil.isNotEmpty(userId) && StringUtil.isNotEmpty(token)){
            ResultBean userOperableBean = userService.getUserOperable(userId, token,null);
            if (userOperableBean.getCode() == 0 && checkUserByCourseOrNot(userId,courseId)) {
                userValite = true;
            }
        }
        //查看该用户是否和购买该门课程

        ResultBean bean = new ResultBean();
        TbCatalogExample catalogExample = new TbCatalogExample();
        TbCatalogExample.Criteria criteria = catalogExample.createCriteria();
        criteria.andDeleteFlagEqualTo("0").andEffectFlagEqualTo("1").andCourseIdEqualTo(courseId);
        // 按catalog_id降序
        catalogExample.setOrderByClause("catalog_order asc");
        List<TbCatalog> tbCatalogs = tbCatalogMapper.selectByExample(catalogExample);
        if(tbCatalogs != null){

            List<CourseTitle> titleList = new ArrayList<>();

            List<TbCatalog> titleCatalogs = new ArrayList<>();
            List<TbCatalog> detailCatalogs = new ArrayList<>();
            //1.取出一级目录
            for (TbCatalog tbCatalog : tbCatalogs) {
                if(tbCatalog.getIsParent()){
                    titleCatalogs.add(tbCatalog);
                } else {
                    if(tbCatalog.getIsAuditioning().equals("0")){
                        if(!userValite){
                            tbCatalog.setVideoPath(null);
                        }
                    }
                    detailCatalogs.add(tbCatalog);
                }
            }
            //判断是否有二级标题
            if(titleCatalogs.size()>0){
                CourseTitle courseTitle =null;
                for (TbCatalog tbCatalog : titleCatalogs) {
                    List<TbCatalog> catalogs = new ArrayList<>();
                    //筛选章节
                    for(TbCatalog detail : detailCatalogs){
                        if(detail.getCatalogParentId() == tbCatalog.getCatalogId()){
                            catalogs.add(detail);
                        }
                    }
                    courseTitle = new CourseTitle();
                    courseTitle.setCourseTitle(tbCatalog.getCatalogName());
                    courseTitle.setCatalogList(getCatalogList(catalogs));
                    titleList.add(courseTitle);
                }
                bean.setResult(titleList);
            } else {
                bean.setResult(getCatalogList(detailCatalogs));
            }
        }
        bean.setCode(Constants.code_0);
        bean.setMsg(Constants.msg_success);
        return bean;
    }

    private boolean checkUserByCourseOrNot(String userId,String courseId){
        TbOrderExample tbOrderExample = new TbOrderExample();
        TbOrderExample.Criteria criteria = tbOrderExample.createCriteria();
        criteria.andUserIdEqualTo(userId).andGoodIdEqualTo(courseId)
                .andOrderStatusIn(Constants.ordered_Status_LIST).andEffectFlagEqualTo("1")
                .andDeleteFlagEqualTo("0");
        List<TbOrder> orderList = tbOrderMapper.selectByExample(tbOrderExample);
        if(orderList.size() == 1){
            return true;
        }
        return false;
    }
    /**
     * 构建目录
     * @param tbCatalogs
     * @return
     */
    public List<CourseCatalog> getCatalogList(List<TbCatalog> tbCatalogs){
        List<CourseCatalog> courseCatalogs = new ArrayList<>();
        CourseCatalog courseCatalog;
        if(tbCatalogs!=null){
            for(TbCatalog detail : tbCatalogs){
                courseCatalog = new CourseCatalog();
                courseCatalog.setCatalogId(detail.getCatalogId());
                courseCatalog.setCatalogName(detail.getCatalogName());
                courseCatalog.setVideoTime(detail.getVideoTime());
                courseCatalog.setLearningTimes(detail.getLearningTimes());
                courseCatalog.setVideoPath(detail.getVideoPath());
                courseCatalog.setIsAuditioning(detail.getIsAuditioning());
                courseCatalogs.add(courseCatalog);
            }
        }
        return courseCatalogs;
    }

    /**
     * 根据courseIds get course
     * @param   courseIds
     * @return  List<TbCourse>
     */
    public List<TbCourse> getCourseListByIds(List<String> courseIds){
        TbCourseExample example = new TbCourseExample();
        TbCourseExample.Criteria criteria = example.createCriteria();
        criteria.andDeleteFlagEqualTo("0").andEffectFlagEqualTo("1").andCourseIdIn(courseIds);
        List<TbCourse> tbCourses = tbCourseMapper.selectByExample(example);
        if(tbCourses == null){
            return new ArrayList<>();
        }
        return tbCourses;
    }

    /**
     * 根据serviceIds get service
     * @param   serviceIds
     * @return  List<TbService>
     */
    public List<TbService> getServiceListByIds(List<String> serviceIds){
        TbServiceExample example = new TbServiceExample();
        TbServiceExample.Criteria criteria = example.createCriteria();
        criteria.andDeleteFlagEqualTo("0").andEffectFlagEqualTo("1").andServiceIdIn(serviceIds);
        List<TbService> tbServices = serviceMapper.selectByExample(example);
        if(tbServices == null){
            return new ArrayList<>();
        }
        return tbServices;
    }

    /**
     * 根据courseId get 章节list
     * @param courseIds
     * @return
     */
    public List<TbCatalog> getCatalogListByIds(List<String> courseIds){
        TbCatalogExample example = new TbCatalogExample();
        TbCatalogExample.Criteria criteria = example.createCriteria();
        criteria.andDeleteFlagEqualTo("0").andEffectFlagEqualTo("1")
                .andIsParentEqualTo(false).andCourseIdIn(courseIds);
        List<TbCatalog> catalogs = tbCatalogMapper.selectByExample(example);
        if(catalogs == null){
            catalogs = new ArrayList<>();
        }
        return catalogs;
    }

    /**
     * 构建 Map<String,List<TbCatalog>>
     *     courseId---List<TbCatalog>
     * @param catalogs
     * @return
     */
    public Map<String,List<TbCatalog>> courseIdCatalogListMap(List<TbCatalog> catalogs){
        Map<String,List<TbCatalog>> idCatalogMap = new HashMap<>();
        for(TbCatalog tbCatalog:catalogs){
            if(!idCatalogMap.containsKey(tbCatalog.getCourseId())){
                List<TbCatalog> catalogList = new ArrayList<>();
                catalogList.add(tbCatalog);
                idCatalogMap.put(tbCatalog.getCourseId(),catalogList);
            } else {
                idCatalogMap.get(tbCatalog.getCourseId()).add(tbCatalog);
            }
        }
        return idCatalogMap;
    }

    /**
     * 构建 Map<String,Long>
     *     courseId---courseIdLearningTimes
     * @param catalogs
     * @return
     */
    public Map<String,Long> courseIdLearningTimesMap(List<TbCatalog> catalogs){
        Map<String,Long> idTimesMap = new HashMap<>();
        for(TbCatalog tbCatalog:catalogs){
            if(!idTimesMap.containsKey(tbCatalog.getCourseId())){
                idTimesMap.put(tbCatalog.getCourseId(),tbCatalog.getLearningTimes());
            } else {
                long temp = idTimesMap.get(tbCatalog.getCourseId())+tbCatalog.getLearningTimes();
                idTimesMap.put(tbCatalog.getCourseId(),temp);
            }
        }
        return idTimesMap;
    }

    /**
     * 根据课程catalogId取课程目录视屏的播放连接及记录的播放秒数
     * @param catalogId     课程目录id
     * @return ResultBean   结果
     */
    public ResultBean getCatalogVideoInfo(String courseId,String catalogId, String userId, String token) {
        ResultBean bean = new ResultBean();
        //验证用户信息
        // 1.验证当前用户登陆状态
        // 判断用户是否登陆以及用户登陆信息是否过期
        boolean userValite = false;
        if(StringUtil.isNotEmpty(userId) && StringUtil.isNotEmpty(token)){
            ResultBean userOperableBean = userService.getUserOperable(userId, token,null);
            if (userOperableBean.getCode() == 0 && checkUserByCourseOrNot(userId,courseId)) {
                userValite = true;
            }
        }
        // 2.取课程目录的info
        // 2.1
        TbCatalog catalog = tbCatalogMapper.selectByPrimaryKey(Long.parseLong(catalogId));
        Map<String,Object> infoMap = new HashMap<>();

        TbLearningExample example = new TbLearningExample();
        TbLearningExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(userId).andCatalogIdEqualTo(catalogId);
        List<TbLearning> tbLearnings = learningMapper.selectByExample(example);

        infoMap.put("videoPath",catalog.getVideoPath());
        infoMap.put("currentSecond",tbLearnings.get(0).getCurrentSecond());

        bean.setMsg(Constants.msg_success);
        bean.setCode(Constants.code_0);
        bean.setResult(infoMap);
        return bean;
    }

    /**
     * 根据课程catalogId取课程目录文稿代码块
     * @param catalogId     课程目录id
     * @return ResultBean   结果
     */
    public ResultBean getCatalogDraft(String catalogId) {
        ResultBean bean = new ResultBean();
        TbCatalog catalog = tbCatalogMapper.selectByPrimaryKey(Long.parseLong(catalogId));
        if(catalog == null){
            bean.setCode(Constants.code_1);
            bean.setMsg("查询失败");
            return bean;
        }

        Map<String,String> draftMap = new HashMap<>();
        draftMap.put("draft",catalog.getVideoDraft());
        bean.setCode(Constants.code_0);
        bean.setMsg(Constants.msg_success);
        bean.setResult(draftMap);
        return bean;
    }

    /**
     * 获取线下课程列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    public ResultBean getUnderlineCourseList(int pageNum, int pageSize) {
        ResultBean bean = new ResultBean();
        TbCourseExample courseExample = new TbCourseExample();
        TbCourseExample.Criteria criteria = courseExample.createCriteria();
        criteria.andDeleteFlagEqualTo("0").andEffectFlagEqualTo("1")
                .andCourseIsOnlineEqualTo("线下");
        courseExample.setOrderByClause("create_time desc");
        PageHelper.startPage(pageNum,pageSize);
        List<TbCourse> courseList = tbCourseMapper.selectByExample(courseExample);
        List<CourseUnderline> underlines = new ArrayList<>();
        if (courseList.size()>0){
            //处理线下课程列表
            //1.get master list
            List<String> masterIds = new ArrayList<>();
            List<String> courseIds = new ArrayList<>();
            for (TbCourse course:courseList){
                masterIds.add(course.getCourseBelongto());
                courseIds.add(course.getCourseId());
            }
            List<TbUser> usersByUserIds = userService.getUsersByUserIds(masterIds);
            Map<String, TbUser> idUserMap = userService.getIdUserMap(usersByUserIds);
            //2.get enroll list
            List<TbEnroll> enrollListByCourseIds = getEnrollListByCourseIds(courseIds);
            Map<String, List<TbEnroll>> courseIdEnrollMap = getCourseIdEnrollMap(enrollListByCourseIds);

            CourseUnderline underline = null;
            for (TbCourse course:courseList){
                underline = new CourseUnderline();
                underline.setCourseId(course.getCourseId());
                underline.setCourseImg(course.getCourseImg());
                underline.setCourseName(course.getCourseName());
                underline.setCoursePrice(course.getCoursePrice());
                underline.setMasterName(idUserMap.get(course.getCourseBelongto()).getUserName());
                underline.setStartDate(course.getStartdate());
                if(courseIdEnrollMap.size()>0 && courseIdEnrollMap.containsKey(course.getCourseId())){
                    underline.setSignNum(courseIdEnrollMap.get(course.getCourseId()).size());
                } else {
                    underline.setSignNum(0);
                }
                underlines.add(underline);

            }
            bean.setResult(underlines);
            bean.setMsg(Constants.msg_success);
        } else {
            bean.setResult(underlines);
            bean.setMsg(Constants.msg_success);
        }

        return bean;
    }

    public List<TbEnroll> getEnrollListByCourseIds(List<String> courIds){
        TbEnrollExample enrollExample = new TbEnrollExample();
        TbEnrollExample.Criteria criteria = enrollExample.createCriteria();
        criteria.andCourseIdIn(courIds);
        List<TbEnroll> tbEnrolls = enrollMapper.selectByExample(enrollExample);
        return tbEnrolls;
    }

    public Map<String,List<TbEnroll>> getCourseIdEnrollMap(List<TbEnroll> enrollListl){
        Map<String,List<TbEnroll>> stringListMap = new HashMap<>();
        if (enrollListl.size()>0){
            for (TbEnroll enroll:enrollListl){
                if(!stringListMap.containsKey(enroll.getCourseId())){
                    List<TbEnroll> enrolls = new ArrayList<>();
                    enrolls.add(enroll);
                    stringListMap.put(enroll.getCourseId(),enrolls);
                } else {
                    stringListMap.get(enroll.getCourseId()).add(enroll);
                }
            }
        }
        return stringListMap;

    }
}
