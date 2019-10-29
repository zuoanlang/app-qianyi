package com.master.qianyi.wxpay.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public interface WxPayService {

    Map<Object, Object> getWxPay(String orderId, String ip);

    void weChatNotify(HttpServletRequest request, HttpServletResponse response);
}
