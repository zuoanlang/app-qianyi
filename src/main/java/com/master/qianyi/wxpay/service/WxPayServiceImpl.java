package com.master.qianyi.wxpay.service;

import com.alibaba.fastjson.JSON;
import com.master.qianyi.mapper.TbOrderMapper;
import com.master.qianyi.pojo.TbOrder;
import com.master.qianyi.wxpay.utils.PayCommonUtil;
import com.master.qianyi.wxpay.utils.WxPayProperties;
import com.master.qianyi.wxpay.utils.XMLUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom2.JDOMException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

@Service
public class WxPayServiceImpl implements WxPayService {

    private Log log = LogFactory.getLog(WxPayServiceImpl.class);

    @Autowired
    private TbOrderMapper orderMapper;

    @Override
    public Map<Object, Object> getWxPay(String orderId, String ip) {
        TbOrder order = orderMapper.selectByPrimaryKey(orderId);
        try {
            SortedMap<Object, Object> parameters = PayCommonUtil.getWXPrePayID(); // 获取预付单，此处已做封装，需要工具类
            //parameters.put("body", wxPayModel.getBody());
            parameters.put("body", order.getGoodName());

            parameters.put("notify_url", WxPayProperties.notify_url);
            parameters.put("spbill_create_ip", ip);
            parameters.put("out_trade_no", order.getOrderId()); // 订单id这里我的订单id生成规则是订单id+时间
            parameters.put("total_fee", String.valueOf(order.getOrderAmount())); // 测试时，每次支付一分钱，微信支付所传的金额是以分为单位的，因此实际开发中需要x100

            // 设置签名
            String sign = PayCommonUtil.createSign("UTF-8", parameters, WxPayProperties.api_key);
            parameters.put("sign", sign);

            // 封装请求参数结束
            String requestXML = PayCommonUtil.getRequestXml(parameters); // 获取xml结果
            // 调用统一下单接口
            String result = PayCommonUtil.httpsRequest(WxPayProperties.UNIFIED_ORDER_URL, "POST",
                    requestXML);
            System.out.println("result=" + result);
            SortedMap<Object, Object> parMap = PayCommonUtil.startWXPay(result);
            parMap.put("outTradeNo", order.getOrderId());
            parMap.put("productId", order.getGoodId());
            return parMap;
        } catch (Exception e) {
            e.printStackTrace();
            Map<Object, Object> errMap = new HashMap<>();
            errMap.put("errMsg", e.getMessage());
            return errMap;
        }
    }

    @Override
    public void weChatNotify(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("----------------pda微信回调-------" + request);
        // 读取参数
        InputStream inputStream;
        StringBuffer sb = new StringBuffer();
        try {
            inputStream = request.getInputStream();

            String s;
            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            while ((s = in.readLine()) != null) {
                sb.append(s);
            }

            in.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 解析xml成map
        Map<String, String> m = new HashMap<String, String>();
        try {
            m = XMLUtil.doXMLParse(sb.toString());
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 过滤空 设置 TreeMap
        SortedMap<Object, Object> packageParams = new TreeMap<Object, Object>();
        Iterator it = m.keySet().iterator();
        while (it.hasNext()) {
            String parameter = (String) it.next();
            String parameterValue = m.get(parameter);

            String v = "";
            if (null != parameterValue) {
                v = parameterValue.trim();
            }
            packageParams.put(parameter, v);
        }

        String key = WxPayProperties.api_key; // key

        // logger.info(packageParams);
        // 判断签名是否正确
        if (PayCommonUtil.isTenpaySign("UTF-8", packageParams, key)) {
            if ("SUCCESS".equals((String) packageParams.get("result_code"))) {
                // 这里是支付成功
                ////////// 执行自己的业务逻辑////////////////
                String mch_id = (String) packageParams.get("mch_id");
				String openid = (String) packageParams.get("openid");
				String is_subscribe = (String) packageParams.get("is_subscribe");
				String out_trade_no = (String) packageParams.get("out_trade_no");
                String prepay_id = (String) packageParams.get("prepay_id");
                String transaction_id = (String) packageParams.get("transaction_id");
//				String total_fee = (String) packageParams.get("total_fee");
                //更改数据库状态
                TbOrder order = new TbOrder();
                order.setThirdTradeno(transaction_id);
                order.setOrderId(out_trade_no);
                order.setModeOfPayment("2");
                order.setOrderStatus("2");
                order.setOrderPayTime(new Date());
                orderMapper.updateByPrimaryKeySelective(order);
                log.info(JSON.toJSONString(packageParams));
                // }
                // to存信息
                // 通知微信 预下单成功
                SortedMap<Object, Object> prepayParams = new TreeMap<Object, Object>();
                prepayParams.put("prepay_id", prepay_id);
                prepayParams.put("return_code", "SUCCESS");
                prepayParams.put("result_code", "SUCCESS");
                String prepaySign = PayCommonUtil.createSign("UTF-8", prepayParams, key);
                prepayParams.put("sign", prepaySign);
                String prepayXml = PayCommonUtil.getRequestXml(prepayParams);
                try {
                    BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
                    out.write(prepayXml.getBytes());
                    out.flush();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
