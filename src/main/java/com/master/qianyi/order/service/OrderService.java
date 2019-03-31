package com.master.qianyi.order.service;

import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.util.StringUtil;
import com.master.qianyi.mapper.TbCourseMapper;
import com.master.qianyi.mapper.TbMessageMapper;
import com.master.qianyi.mapper.TbOrderMapper;
import com.master.qianyi.pojo.*;
import com.master.qianyi.order.form.OrderForm;
import com.master.qianyi.utils.Constants;
import com.master.qianyi.utils.IDUtils;
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

    @Autowired
    private TbMessageMapper tbMessageMapper;

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

    /**
     * 修改订单（支付）
     *
     * @param orderId
     * @param modeOfPaymentint
     * @param orderAmount
     * @return
     */
    public ResultBean updateOrderState(String orderId, String modeOfPaymentint, int orderAmount) {
        if (StringUtil.isEmpty(orderId)) {
            return null;
        }
        ResultBean bean = new ResultBean();
        TbOrderExample tbOrderExample = new TbOrderExample();
        tbOrderExample.createCriteria().andOrderIdEqualTo(orderId)
                .andEffectFlagEqualTo("1")
                .andDeleteFlagEqualTo("0");
        List<TbOrder> tbOrderList = tbOrderMapper.selectByExample(tbOrderExample);
        TbOrder tbOrder = tbOrderList.get(0);
        if (tbOrder != null) {
            if (orderAmount > 0) {
                if (StringUtil.isNotEmpty(modeOfPaymentint)) {
                    tbOrder.setModeOfPayment(modeOfPaymentint);
                }
                // 订单状态由未付款改为已付款
                tbOrder.setOrderStatus("2");
                tbOrder.setOrderAmount(orderAmount);
                int updateFlag = tbOrderMapper.updateByExample(tbOrder, tbOrderExample);
                if (updateFlag != 1) {
                    bean.setCode(Constants.code_1);
                    bean.setMsg(Constants.msg_failed);
                }
            } else {
                bean.setCode(Constants.code_1);
                bean.setMsg(Constants.msg_failed);
            }
        } else {
            bean.setCode(Constants.code_1);
            bean.setMsg(Constants.msg_failed);
        }
        bean.setCode(Constants.code_0);
        bean.setMsg(Constants.msg_success);
        return bean;
    }

    /**
     * 退款
     *
     * @param userId            用户id
     * @param orderId           订单id
     * @param orderRefundReason 退款原因
     * @return
     */
    public ResultBean refund(String userId, String orderId, String orderRefundReason) {
        ResultBean bean = new ResultBean();
        if (StringUtil.isEmpty(orderId)) {
            bean.setCode(Constants.code_1);
            bean.setMsg(Constants.msg_failed);
            return bean;
        }
        TbOrderExample tbOrderExample = new TbOrderExample();
        tbOrderExample.createCriteria()
                .andEffectFlagEqualTo("1")
                .andDeleteFlagEqualTo("0")
                .andOrderIdEqualTo(orderId);
        List<TbOrder> orders = tbOrderMapper.selectByExample(tbOrderExample);
        if (orders != null) {
            TbOrder order = orders.get(0);
            // 状态设置为4：退款中
            order.setOrderStatus("4");
            // 退款理由
            order.setOrderRefundReason(orderRefundReason);
            // 订单审核人为admin
            order.setOrderAuditor("admin");
            int updateFlag = tbOrderMapper.updateByExampleSelective(order, tbOrderExample);
            if (updateFlag > 0) {
                // 消息内容
                String messageContent = "用户" + userId + "发起退款。退款原因：" + orderRefundReason;
                // 发消息给订单审核人admin
                int insert = insertMessage(userId, order.getGoodId(), messageContent);
                if (insert > 0) {
                    bean.setCode(Constants.code_0);
                    bean.setMsg(Constants.msg_success);
                } else {
                    bean.setCode(Constants.code_1);
                    bean.setMsg(Constants.msg_failed);
                }
            } else {
                bean.setCode(Constants.code_1);
                bean.setMsg(Constants.msg_failed);
            }
        } else {
            bean.setCode(Constants.code_1);
            bean.setMsg(Constants.msg_failed);
        }
        return bean;
    }

    /**
     * 发送给admin的系统消息
     *
     * @param userId
     * @param courseId
     * @param messageContent
     * @return
     */
    private int insertMessage(String userId, String courseId, String messageContent) {
        // 发消息给订单审核人admin
        TbMessage message = new TbMessage();
        message.setMessageId(IDUtils.genItemId());
        message.setCourseId(courseId);
        // 1:系统消息
        message.setMessageType("1");
        message.setMessageSender(userId);
        // 消息接收者为admin
        message.setMessageReceiver("admin");
        message.setMessageContent(messageContent);
        message.setMessageDateTime(new Date(System.currentTimeMillis()));
        message.setIsRead("0");
        message.setEffectFlag("1");
        message.setDeleteFlag("0");
        int insert = tbMessageMapper.insert(message);
        if (insert > 0) {
            return 1;
        } else {
            return -1;
        }
    }
}
