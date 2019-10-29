package com.master.qianyi.alipay.service;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.master.qianyi.alipay.utils.AlipayConfig;
import com.master.qianyi.mapper.TbOrderMapper;
import com.master.qianyi.pojo.TbOrder;
import com.master.qianyi.utils.CalculateUtils;
import com.master.qianyi.utils.Constants;
import com.master.qianyi.utils.ResultBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Service
public class AliPayService {

    private Logger logger = LoggerFactory.getLogger(AliPayService.class);

    @Autowired
    private TbOrderMapper orderMapper;
    /**
     * 1.获取支付宝签名
     * @param orderId
     * @return
     */
    public ResultBean getAliPaySign(String orderId) {
        ResultBean bean = new ResultBean();
        //根据订单号查询订单信息
        TbOrder order = orderMapper.selectByPrimaryKey(orderId);

        if(order == null || order.getOrderId()== null){
            bean.setMsg("当前订单不存在");
            bean.setCode(Constants.code_1);
            return bean;
        }
        if(!order.getOrderStatus().equals(Constants.ORDER_STATUS_1)){
            bean.setMsg("当前订单已关闭");
            bean.setCode(Constants.code_1);
            return bean;
        }

        // 实例化客户端
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.GATE,
                AlipayConfig.APPID, AlipayConfig.APP_PRIVATE_KEY, AlipayConfig.FORMAT, AlipayConfig.CHARSET,
                AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.SIGNTYPE);
        // 实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        // SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setBody("商品购买："+order.getGoodName());
        model.setSubject("商品购买："+order.getGoodName());
        model.setOutTradeNo(orderId);// 更换为自己的订单编号
        model.setTimeoutExpress("30m");
        model.setTotalAmount(CalculateUtils.fenToYuan(order.getOrderAmount()));// 订单价格
        model.setProductCode("QUICK_MSECURITY_PAY");
        model.setGoodsType("1");
        request.setBizModel(model);
        request.setNotifyUrl(AlipayConfig.notify_url);// 回调地址不可以带参数,这里的设置有效使用
        try {
            // 这里和普通的接口调用不同，使用的是sdkExecute
            AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
            Map<String,String> map = new HashMap<>();
            map.put("sign",response.getBody());
            bean.setResult(map);
            bean.setCode(Constants.code_0);
            return bean;
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        bean.setCode(Constants.code_1);
        bean.setMsg("获取失败");
        return bean;

    }

    /**
     * 支付宝支付成功后.异步请求该接口
     *
     * @param
     * @returnResult
     */
    public String notifyUrl(HttpServletRequest request, HttpServletResponse response) {
        //1.从支付宝回调的request域中取值
        //获取支付宝返回的参数集合
        Map<String, String[]> requestParams = request.getParameterMap();
        System.out.println("支付宝支付结果通知" + requestParams.toString());
        //获取支付宝POST过来反馈信息
        Map<String, String> params = new HashMap<>();

        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }
        //切记alipaypublickey是支付宝的公钥，请去open.alipay.com对应应用下查看。
        try {
            boolean flag = AlipaySignature.rsaCheckV1(params, AlipayConfig.PUBLIC_KEY, AlipayConfig.CHARSET, "RSA2");
            if (flag) {
                if ("TRADE_SUCCESS".equals(params.get("trade_status"))) {
                    //付款金额
                    String amount = params.get("buyer_pay_amount");
                    //商户订单号
                    String out_trade_no = params.get("out_trade_no");
                    //支付宝交易号
                    String trade_no = params.get("trade_no");
                    //附加数据
                    String passback_params = URLDecoder.decode(params.get("passback_params"));
                    logger.info("将要存入数据库的参数" + amount + "," + out_trade_no + "," + trade_no + "," + passback_params);
                    //更改数据库状态
                    TbOrder order = new TbOrder();
                    order.setThirdTradeno(trade_no);
                    order.setOrderId(out_trade_no);
                    order.setOrderStatus("2");
                    order.setModeOfPayment("1");
                    order.setOrderPayTime(new Date());
                    orderMapper.updateByPrimaryKeySelective(order);
                }
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return "success";
    }

    /**
     * 向支付宝发起订单查询请求
     * @param outTradeNo
     * @return
     */
    public ResultBean checkAlipay(String orderId) {
        logger.info("==================向支付宝发起查询，查询商户订单号为："+orderId);
        ResultBean bean = new ResultBean();
        try {
            //实例化客户端（参数：网关地址、商户appid、商户私钥、格式、编码、支付宝公钥、加密类型）
            AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.GATE,
                    AlipayConfig.APPID, AlipayConfig.APP_PRIVATE_KEY, AlipayConfig.FORMAT, AlipayConfig.CHARSET,
                    AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.SIGNTYPE);
            AlipayTradeQueryRequest alipayTradeQueryRequest = new AlipayTradeQueryRequest();
            alipayTradeQueryRequest.setBizContent("{" +
                    "\"out_trade_no\":\""+orderId+"\"" +
                    "}");
            AlipayTradeQueryResponse alipayTradeQueryResponse = alipayClient.execute(alipayTradeQueryRequest);
            if(alipayTradeQueryResponse.isSuccess()){
                TbOrder order = orderMapper.selectByPrimaryKey(orderId);
                //修改数据库支付宝订单表
                order.setThirdTradeno(alipayTradeQueryResponse.getTradeNo());
                order.setBuyerLogonId(alipayTradeQueryResponse.getBuyerLogonId());
                order.setTotalAmount(Double.parseDouble(alipayTradeQueryResponse.getTotalAmount()));
                order.setReceiptAmount(Double.parseDouble(alipayTradeQueryResponse.getReceiptAmount()));
                order.setBuyerPayAmount(Double.parseDouble(alipayTradeQueryResponse.getBuyerPayAmount()));
                switch (alipayTradeQueryResponse.getTradeStatus()) // 判断交易结果
                {
                    case "TRADE_FINISHED": // 交易结束并不可退款
                        order.setOrderStatus("3");
                        break;
                    case "TRADE_SUCCESS": // 交易支付成功
                        order.setOrderStatus("2");
                        break;
                    case "TRADE_CLOSED": // 未付款交易超时关闭或支付完成后全额退款
                        order.setOrderStatus("6");
                        break;
                    case "WAIT_BUYER_PAY": // 交易创建并等待买家付款
                        order.setOrderStatus("1");
                        break;
                    default:
                        break;
                }
                int update = orderMapper.updateByPrimaryKeySelective(order);//更新表记录
                if(update<0){
                    bean.setCode(Constants.code_1);
                    bean.setMsg("数据库更新失败");
                }
                bean.setCode(Constants.code_0);
                bean.setMsg(Constants.msg_success);
                Map<String,String> map = new HashMap<>();
                map.put("orderStatus",order.getOrderStatus());
                bean.setResult(map);
                return bean;
            } else {
                logger.info("==================调用支付宝查询接口失败！");
            }
        } catch (AlipayApiException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }


}
