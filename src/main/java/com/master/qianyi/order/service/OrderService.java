package com.master.qianyi.order.service;

import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.util.StringUtil;
import com.master.qianyi.mapper.TbCourseMapper;
import com.master.qianyi.mapper.TbOrderMapper;
import com.master.qianyi.pojo.TbCourse;
import com.master.qianyi.pojo.TbCourseExample;
import com.master.qianyi.pojo.TbOrder;
import com.master.qianyi.pojo.TbOrderExample;
import com.master.qianyi.order.form.OrderForm;
import com.master.qianyi.utils.Constants;
import com.master.qianyi.utils.ResultBean;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private TbOrderMapper tbOrderMapper;

    @Autowired
    private TbCourseMapper tbCourseMapper;

    /**
     * @param userId
     * @param orderStatus
     * @return
     */
    public ResultBean getOrderByUserId(String userId, String orderStatus) {
        // 1. 查询订单表
        TbOrderExample tbOrderExample = new TbOrderExample();
        tbOrderExample.createCriteria()
                .andEffectFlagEqualTo("1")
                .andDeleteFlagEqualTo("0")
                .andUserIdEqualTo(userId);
        // 订单状态（进行中、已完成等）
        if (StringUtil.isNotEmpty(orderStatus)) {
            tbOrderExample.getOredCriteria().get(0).andOrderStatusEqualTo(orderStatus);
        }
        List<TbOrder> tbOrderList = tbOrderMapper.selectByExample(tbOrderExample);

        // 2. 查询课程表
        List<String> courseIdList = new ArrayList<>();
        if (tbOrderList != null && tbOrderList.size() > 0) {
            for (TbOrder order : tbOrderList) {
                courseIdList.add(order.getGoodId());
            }
        }
        TbCourseExample tbCourseExample = new TbCourseExample();
        tbCourseExample.createCriteria().andCourseIdIn(courseIdList);
        List<TbCourse> courses = tbCourseMapper.selectByExample(tbCourseExample);
        OrderForm form = null;
        List<OrderForm> formList = new ArrayList<>();
        for (TbOrder order : tbOrderList) {
            form = new OrderForm();
            BeanUtils.copyProperties(order, form);
            for (TbCourse course : courses) {
                if (StringUtils.equals(order.getGoodId(), course.getCourseId())) {
                    form.setCourseId(course.getCourseId());
                    form.setCourseName(course.getCourseName());
                    form.setCourseImg(course.getCourseImg());
                    form.setCourse_belongTo(course.getCourseBelongto());
                    form.setCoursePrice(course.getCoursePrice());
                    form.setCourseLearningFrequency(course.getCourseLearningFrequency());
                }
            }
            formList.add(form);
        }
        ResultBean bean = new ResultBean();
        bean.setCode(Constants.code_0);
        bean.setMsg(Constants.msg_success);
        bean.setResult(formList);
        return bean;
    }

    /**
     * 根据订单id查询订单信息
     *
     * @param orderId
     * @return
     */
    public ResultBean getOrderDetailByOrderId(String orderId) {
        // 1. 查询订单表
        TbOrder tbOrder = tbOrderMapper.selectByPrimaryKey(orderId);
        // 2. 查询课程表
        TbCourse tbCourse = tbCourseMapper.selectByPrimaryKey(tbOrder.getGoodId());
        OrderForm form = new OrderForm();
        BeanUtils.copyProperties(tbOrder, form);
        form.setCourseName(tbCourse.getCourseName());
        form.setCourseImg(tbCourse.getCourseImg());
        form.setCourse_belongTo(tbCourse.getCourseBelongto());
        form.setCoursePrice(tbCourse.getCoursePrice());
        form.setCourseLearningFrequency(tbCourse.getCourseLearningFrequency());
        ResultBean bean = new ResultBean();
        bean.setCode(Constants.code_0);
        bean.setMsg(Constants.msg_success);
        bean.setResult(form);
        return bean;
    }

    /**
     * 下单（插入订单）
     *
     * @param userId   订单创建者id
     * @param courseId 购买课程id
     * @return
     */
    public ResultBean insertOrder(String userId, String courseId) {
        ResultBean bean = new ResultBean();
        if (StringUtil.isNotEmpty(userId) && StringUtil.isNotEmpty((courseId))) {
            TbOrder tbOrder = new TbOrder();
            tbOrder.setUserId(userId);
            tbOrder.setGoodId(courseId);
            tbOrder.setOrderCreateTime(new Date(System.currentTimeMillis()));
            tbOrder.setEffectFlag("1");
            tbOrder.setDeleteFlag("0");
            int insertFlag = tbOrderMapper.insertSelective(tbOrder);
            if (insertFlag != 1) {
                bean.setCode(Constants.code_1);
                bean.setMsg(Constants.msg_failed);
            }
            bean.setCode(Constants.code_0);
            bean.setMsg(Constants.msg_success);
        } else {
            bean.setCode(Constants.code_1);
            bean.setMsg(Constants.msg_failed);
            bean.setResult("userId or courseId is null,please check it");
        }
        return bean;
    }

    public ResultBean updateOrderState(String orderId) {
        ResultBean bean = new ResultBean();
        if (StringUtil.isEmpty(orderId)) {
            return null;
        }
        TbOrder tbOrder = tbOrderMapper.selectByPrimaryKey(orderId);
        if (tbOrder != null) {

        }
        return null;
    }
}
