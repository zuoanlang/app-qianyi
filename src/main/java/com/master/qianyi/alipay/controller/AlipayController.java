package com.master.qianyi.alipay.controller;

import com.master.qianyi.alipay.service.AliPayService;
import com.master.qianyi.utils.Constants;
import com.master.qianyi.utils.ResultBean;
import com.master.qianyi.wxpay.service.WxPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping("/pay")
public class AlipayController {

    @Autowired
    private AliPayService alipayService;

    @Autowired
    private WxPayService wxPayServiceImpl;

    /**
     * @param orderId
     * @param type    0:wxPay,1:aliPay
     * @return
     */
    @RequestMapping(value = "/getPaySign", method = RequestMethod.POST)
    public ResultBean getAliPaySign(@RequestParam(value = "orderId") String orderId,
                                    @RequestParam(value = "type") String type) {
        if ("0".equals(type)) {
            Map<Object, Object> wxPay = wxPayServiceImpl.getWxPay(orderId, "");
            ResultBean bean = new ResultBean();
            bean.setResult(wxPay);
            bean.setMsg(Constants.msg_success);
            bean.setCode(Constants.code_0);
            return bean;
        } else if ("1".equals(type)) {
            return alipayService.getAliPaySign(orderId);
        }
        return null;
    }

    @RequestMapping(value = "/notify_url", method = RequestMethod.POST)
    public String notify(HttpServletRequest request, HttpServletResponse response) {
        return alipayService.notifyUrl(request, response);
    }

    @PostMapping("/WeChatNotify")
    public ResultBean weChatNotify(HttpServletRequest request, HttpServletResponse response) {
        ResultBean bean = new ResultBean();
        try {
            wxPayServiceImpl.weChatNotify(request, response);
            bean.setMsg(Constants.msg_success);
            return bean;
        } catch (Exception e) {
            bean.setMsg(e.getMessage());
            return bean;
        }
    }

    @RequestMapping(value = "/checkAlipay", method = RequestMethod.POST)
    public ResultBean notify(@RequestParam(value = "orderId") String orderId) {
        return alipayService.checkAlipay(orderId);
    }

}
