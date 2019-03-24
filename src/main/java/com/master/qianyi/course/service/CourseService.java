package com.master.qianyi.course.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.master.qianyi.mapper.TbCourseMapper;
import com.master.qianyi.mapper.TbOrderMapper;
import com.master.qianyi.mapper.TbUserMapper;
import com.master.qianyi.pojo.TbCourse;
import com.master.qianyi.pojo.TbCourseExample;
import com.master.qianyi.pojo.TbOrder;
import com.master.qianyi.pojo.TbOrderExample;
import com.master.qianyi.utils.Constants;
import com.master.qianyi.utils.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    /**
     * 根据条件查询课程
     *
     * @param pageNum  当前页码
     * @param pageSize 每页条数
     * @param course   查询条件
     * @return
     */
    public ResultBean getCourseSearchResult(int pageNum, int pageSize, TbCourse course) {
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
        // 课程是否线上
        if (StringUtil.isNotEmpty(course.getCourseIsOnline())) {
            example.getOredCriteria().get(0).andCourseIsOnlineEqualTo(course.getCourseIsOnline());
        }
        // 按热度降序
        example.setOrderByClause("COURSE_LEARNING_FREQUENCY desc");
        // 设置分页
        PageHelper.startPage(pageNum, pageSize);
        // 查询结果集
        List<TbCourse> tbCourses = tbCourseMapper.selectByExample(example);
        PageInfo pageInfo = new PageInfo<>(tbCourses);
        ResultBean bean = new ResultBean();
        bean.setCode(Constants.code_0);
        bean.setMsg(Constants.msg_success);
        bean.setResult(tbCourses);
        return bean;
    }

    /**
     * 根据用户id查询该用户已购买的课程
     *
     * @param userId
     * @param pageNum
     * @param pageSize
     * @return
     */
    public ResultBean getCourseByUserId(String userId, int pageNum, int pageSize) {
        // 1.根据用户id查询订单表
        TbOrderExample tbOrderExample = new TbOrderExample();
        // 该用户已完成的订单
        tbOrderExample.createCriteria().andUserIdEqualTo(userId).andOrderStatusEqualTo("2");
        // 订单支付时间降序
        tbOrderExample.setOrderByClause("ORDER_PAY_TIME desc");

        // 2.关联查询课程表信息
        List<String> courseIdList = new ArrayList<>();
        List<TbOrder> tbOrders = tbOrderMapper.selectByExample(tbOrderExample);
        if (tbOrders != null && tbOrders.size() > 0) {
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
        ResultBean bean = new ResultBean();
        bean.setCode(Constants.code_0);
        bean.setMsg(Constants.msg_success);
        bean.setResult(tbCourses);
        return bean;
    }

    /**
     * 根据课程id查询课程信息
     *
     * @param couserId
     * @return
     */
    public ResultBean getCourseByCourseId(String couserId) {
        TbCourse course = tbCourseMapper.selectByPrimaryKey(couserId);
        ResultBean bean = new ResultBean();
        bean.setCode(Constants.code_0);
        bean.setMsg(Constants.msg_success);
        bean.setResult(course);
        return bean;
    }
}
