package com.master.qianyi.order.controller;

import com.master.qianyi.order.service.OrderService;
import com.master.qianyi.utils.EasyUIDataGridResult;
import com.master.qianyi.utils.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 根据用户id,订单状态查询我的订单
     *
     * @param userId
     * @return
     */
    @GetMapping("/getOrderByUserId")
    public ResultBean getOrderByUserId(@RequestParam(value="userId")String userId,
                                       @RequestParam(value="token")String token,
                                       @RequestParam(value="orderStatus")String orderStatus) {
        return orderService.getOrderByUserId(userId,token, orderStatus);
    }

    /**
     * 根据订单编号查询订单详情
     *
     * @param orderId
     * @return
     */
    @GetMapping("/getOrderDetail")
    public ResultBean getOrderDetailByOrderId(@RequestParam(value="orderId")String orderId) {
        return orderService.getOrderDetailByOrderId(orderId);
    }


    /**
     * 下单（插入订单）
     *
     * @param   userId    订单创建者id
     * @param   token     订单创建者id
     * @param   goodsId   购买课程id,购买服务的服务id
     * @param   goodsType 商品类型1.课程购买2.测算服务
     * @return  ResultBean
     */
    @PostMapping("/insertOrder")
    public ResultBean insertOrder(@RequestParam(value="userId")String userId,
                                  @RequestParam(value="token")String token,
                                  @RequestParam(value="goodsId")String goodsId,
                                  @RequestParam(value="goodsType")String goodsType,
                                  @RequestParam(value="underLine",defaultValue = "0")String underLine,
                                  String name,String phoneNumber) {
        return orderService.insertOrder(userId,token, goodsId,goodsType,underLine,name,phoneNumber);
    }

    /**
     * 修改订单（支付）
     *
     * @param orderId           订单id
     * @param modeOfPayment     支付方式
     * @param orderAmount       订单支付金额
     * @return
     */
    @GetMapping("/payOrder")
    public ResultBean updateOrderState(@RequestParam(value="userId")String userId,
                                       @RequestParam(value="token")String token,
                                       @RequestParam(value="orderId")String orderId,
                                       @RequestParam(value="modeOfPayment")String modeOfPayment) {
        return orderService.updateOrderState(userId,token,orderId, modeOfPayment);
    }

    /**
     * ios金币支付
     *
     * @param orderId           订单id
     * @param modeOfPayment     支付方式
     * @param orderAmount       订单支付金额
     * @return
     */
    @PostMapping("/payOrderByCoins")
    public ResultBean payOrderByCoins(@RequestParam(value="userId")String userId,
                                      @RequestParam(value="token")String token,
                                      @RequestParam(value="orderId")String orderId) {
        return orderService.payOrderByCoins(userId,token,orderId);
    }


    /**
     * 退款
     *
     * @param userId            用户id
     * @param orderId           订单id
     * @param orderRefundReason 退款原因
     * @return
     */
    @GetMapping("/refund")
    public ResultBean refund(@RequestParam(value="userId")String userId,
                            @RequestParam(value="token")String token,
                            @RequestParam(value="orderId")String orderId,
                            @RequestParam(value="orderRefundReason") String orderRefundReason) {
        return orderService.refund(userId, token, orderId, orderRefundReason);
    }

    @GetMapping("/getRefundList")
    public EasyUIDataGridResult getRefundList(int page , int rows) {
        return orderService.getRefundList(page,rows);
    }

}
