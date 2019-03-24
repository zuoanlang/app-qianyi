package com.master.qianyi.order.controller;

import com.master.qianyi.order.service.OrderService;
import com.master.qianyi.utils.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 根据用户id查询我的订单详情
     *
     * @param userId
     * @return
     */
    @GetMapping("/getOrderByUserId")
    public ResultBean getOrderByUserId(String userId, String orderStatus) {
        return orderService.getOrderByUserId(userId, orderStatus);
    }


    /**
     * 根据订单编号查询订单详情
     *
     * @param orderId
     * @return
     */
    @GetMapping("/getOrderDetail")
    public ResultBean getOrderDetailByOrderId(String orderId) {
        return orderService.getOrderDetailByOrderId(orderId);
    }


    /**
     * 下单（插入订单）
     *
     * @param userId   订单创建者id
     * @param courseId 购买课程id
     * @return
     */
    @GetMapping("/insertOrder")
    public ResultBean insertOrder(String userId, String courseId) {
        return orderService.insertOrder(userId, courseId);
    }

    /**
     * 修改订单（支付）
     *
     * @param orderId
     * @return
     */
    public ResultBean updateOrderState(String orderId,int orderAmount) {
        return orderService.updateOrderState(orderId);
    }

}
