package com.master.qianyi.wxpay.controller;

import com.alibaba.fastjson.JSON;
import com.master.qianyi.utils.ResultBean;
import com.master.qianyi.wxpay.service.WxPayService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping("wx")
public class WxPayController {

    private Log log = LogFactory.getLog(WxPayController.class);

    @Autowired
    WxPayService wxPayService;

    @PostMapping("/GetWxPay")
    public Map<Object, Object> getWxPay(@RequestParam(value = "orderId") String orderId) {
        log.info("订单id:"+JSON.toJSONString(orderId));
        Map<Object, Object> map = wxPayService.getWxPay(orderId, "");
        return map;
    }

    @PostMapping("/WeChatNotify")
    public ResultBean weChatNotify(HttpServletRequest request, HttpServletResponse response) {
//        ResultBean bean = new ResultBean();
//        try {
//            wxPayService.weChatNotify(request, response);
//            bean.setMsg(Constants.msg_success);
//            return bean;
//        } catch (Exception e) {
//            log.error(e.getMessage(), e);
//            bean.setMsg(e.getMessage());
//            return bean;
//        }
        return null;
    }
}
