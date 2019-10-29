package com.master.qianyi.alipay.utils;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class AlipayUtil {

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(AlipayUtil.class);

    /**
     * 创建商户订单  请求支付宝
     * @param amount
     * @param random
     * @return
     */
    public static String getSign(String amount,String random){
        //实例化客户端
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.GATE,
                AlipayConfig.APPID,
                AlipayConfig.APP_PRIVATE_KEY,
                AlipayConfig.FORMAT,
                AlipayConfig.CHARSET,
                AlipayConfig.ALIPAY_PUBLIC_KEY,
                AlipayConfig.SIGNTYPE);
        if (Double.valueOf(amount) <= 0){ // 一些必要的验证，防止抓包恶意修改支付金额
            return null;
        }
        //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        //SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setBody("我是测试数据");
        model.setSubject("乾易");
        model.setOutTradeNo(random);  //订单号
        model.setTimeoutExpress("30m");  // 该参数数值不接受小数点， 如 1.5h，可转换为 90m。
        model.setTotalAmount(amount); // 订单总金额，单位为元，精确到小数点后两位，取值范围[0.01,100000000]这里调试每次支付1分钱，在项目上线前应将此处改为订单的总金额
        model.setProductCode("QUICK_MSECURITY_PAY");// 销售产品码，商家和支付宝签约的产品码，为固定值QUICK_MSECURITY_PAY
        request.setBizModel(model);
        // 设置后台异步通知的地址，在手机端支付成功后支付宝会通知后台，手机端的真实支付结果依赖于此地址
        request.setNotifyUrl("http://catering.saimark.xusage.com/catering/a/RechargeUpdateFromAlipayNotify.xml");
        AlipayTradeAppPayResponse response = new AlipayTradeAppPayResponse();
        try {
            //这里和普通的接口调用不同，使用的是sdkExecute
            response = alipayClient.sdkExecute(request);
            System.out.println(response.getBody());//就是orderString 可以直接给客户端请求，无需再做处理。
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response.getBody();
    }



    /**
     * 从request中获得参数Map，并返回可读的Map
     *
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Map getParameterMap(HttpServletRequest request) {
        // 参数Map
        Map properties = request.getParameterMap();
        LOGGER.debug("包装参数："+properties);
        // 返回值Map
        Map<String, String> returnMap = new HashMap<String, String>();
        Iterator entries = properties.entrySet().iterator();
        Map.Entry entry;
        String name = "";
        String value = "";
        while (entries.hasNext()) {
            entry = (Map.Entry) entries.next();
            name = (String) entry.getKey();
            Object valueObj = entry.getValue();
            if(null == valueObj){
                value = "";
            }else if(valueObj instanceof String[]){
                String[] values = (String[])valueObj;
                for(int i=0;i<values.length;i++){
                    value = values[i] + ",";
                }
                value = value.substring(0, value.length()-1);
            }else{
                value = valueObj.toString();
            }
            returnMap.put(name, value);
        }
        return returnMap;
    }
}
