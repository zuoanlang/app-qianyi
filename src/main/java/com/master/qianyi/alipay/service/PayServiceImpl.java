//package com.master.qianyi.alipay.service;
//
//import com.alibaba.fastjson.JSON;
//import com.alipay.api.AlipayApiException;
//import com.alipay.api.AlipayClient;
//import com.alipay.api.DefaultAlipayClient;
//import com.alipay.api.domain.AlipayTradeAppPayModel;
//import com.alipay.api.domain.OrderItem;
//import com.alipay.api.internal.util.AlipaySignature;
//import com.alipay.api.request.AlipayTradeAppPayRequest;
//import com.alipay.api.response.AlipayTradeAppPayResponse;
//import com.github.pagehelper.util.StringUtil;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.annotation.Order;
//import org.springframework.stereotype.Service;
//
//import javax.servlet.http.HttpServletRequest;
//import java.math.BigDecimal;
//import java.util.*;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
//
///**
// * 支付管理Service
// */
//@Service("payService")
//public class PayServiceImpl implements PayService {
//
//    private Logger logger = LoggerFactory.getLogger(PayServiceImpl.class);
//
//    @Autowired
//    private OrderMapper orderMapper;                 //订单Mapper
//    @Autowired
//    private OrderItemMapper orderItemMapper;         //订单详情表
//    @Autowired
//    private StDictionaryMapper stDictionaryMapper;  //科普参数表
//    @Autowired
//    private ProductMapper productMapper;             //商品表
//    @Autowired
//    private SkuStockMapper skuStockMapper;           //商品SKU表
//    @Autowired
//    private PtCodeMapper ptCodeMapper;                            //拼团总表
//    @Autowired
//    private OrderOperateHistoryMapper historyMapper;              //订单操作历史表
//
//    private ExecutorService executorService = Executors.newFixedThreadPool(20);
//
//
//    /**
//     * 生成支付宝签名
//     * @param orderNo 订单号
//     * @return
//     */
//    @Override
//    public ResponseResp alipaySign(String orderNo) {
//        //根据订单号查询订单信息
//        Order order = orderMapper.selectByOrderSn(orderNo);
//
//        if(!order.getStatus().equals(ServiceConst.ORDER_STATUS.DAI_FUKUAN)){
//            return ResponseResp.error("当前订单已关闭");
//        }
//
//        if(null == order || null == order.getItemList() || order.getItemList().size() <= 0){
//            return null;
//        }
//        //查询支付宝秘钥是多少
//        String app_private_key = stDictionaryMapper.selectByKey("APP_PRIVATE_KEY");
//        if(StringUtil.isEmpty(app_private_key)){
//            return ResponseResp.error("暂未获取到相关支付宝配置信息");
//        }
//        // 实例化客户端
//        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",
//                AliApppayConfig.APPID, app_private_key, AliApppayConfig.FORMAT, AliApppayConfig.CHARSET,
//                AliApppayConfig.ALIPAY_PUBLIC_KEY, AliApppayConfig.SIGNTYPE);
//        // 实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
//        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
//        // SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
//        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
//        model.setBody("商品购买："+order.getItemList().get(0).getProductName());
//        model.setSubject("商品购买："+order.getItemList().get(0).getProductName());
//        model.setOutTradeNo(orderNo);// 更换为自己的订单编号
//        model.setTimeoutExpress("30m");
//        model.setTotalAmount(order.getPayAmount()+"");// 订单价格
//        model.setProductCode("QUICK_MSECURITY_PAY");
//        model.setGoodsType("1");
//        request.setBizModel(model);
//        request.setNotifyUrl(AliApppayConfig.notify_url);// 回调地址不可以带参数,这里的设置有效使用
//        try {
//            // 这里和普通的接口调用不同，使用的是sdkExecute
//            AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
//            return ResponseResp.ok(new StringBuffer(response.getBody()));
//        } catch (AlipayApiException e) {
//            e.printStackTrace();
//        }
//        return ResponseResp.error("获取失败！");
//    }
//
//
//    /**
//     * 支付宝回调接口
//     * @return
//     */
//    @Override
//    public String alipayValidate(HttpServletRequest request) {
//        Map<String, String> params = convertRequestParamsToMap(request); // 将异步通知中收到的待验证所有参数都存放到map中
//        String paramsJson = JSON.toJSONString(params);
//        logger.info("支付宝回调，{}", paramsJson);
//        try {
//            // 调用SDK验证签名
//            boolean signVerified = AlipaySignature.rsaCheckV1(params, AliApppayConfig.ALIPAY_PUBLIC_KEY,
//                    AliApppayConfig.CHARSET, AliApppayConfig.SIGNTYPE);
//            if (signVerified) {
//                // 按照支付结果异步通知中的描述，对支付结果中的业务内容进行1\2\3\4二次校验，校验成功后在response中返回success，校验失败返回failure
//                this.check(params);
//                // 另起线程处理业务
//                executorService.execute(new Runnable() {
//                    @Override
//                    public void run() {
//                        AlipayNotifyParam param = buildAlipayNotifyParam(params);
//                        String trade_status = param.getTradeStatus();
//                        if(null != param.getGmtRefund()){
//                            // 退款回调
//                            logger.info("支付宝退款回调", paramsJson);
//                        }else{
//                            // 支付成功
//                            if (trade_status.equals(ServiceConst.TRADE_STATUS.TRADE_SUCCESS)
//                                    || trade_status.equals(ServiceConst.TRADE_STATUS.TRADE_FINISHED)) {
//                                // 处理支付成功逻辑
//                                try {
//
//                                    updateOrder(param.getOutTradeNo(),param.getGmtPayment(),paramsJson);
//
//                                } catch (Exception e) {
//                                    logger.error("支付宝回调业务处理报错,params:" + paramsJson, e);
//                                }
//                            } else {
//                                logger.error("没有处理支付宝回调业务，支付宝交易状态：{},params:{}",trade_status,paramsJson);
//                            }
//                        }
//                    }
//                });
//                // 如果签名验证正确，立即返回success，后续业务另起线程单独处理
//                // 业务处理失败，可查看日志进行补偿，跟支付宝已经没多大关系。
//                return "success";
//            } else {
//                logger.info("支付宝回调签名认证失败，signVerified=false, paramsJson:{}", paramsJson);
//                return "failure";
//            }
//        } catch (AlipayApiException e) {
//            logger.error("支付宝回调签名认证失败,paramsJson:{},errorMsg:{}", paramsJson, e.getMessage());
//            return "failure";
//        }
//    }
//
//
//
//    // 将request中的参数转换成Map
//    private static Map<String, String> convertRequestParamsToMap(HttpServletRequest request) {
//        Map<String, String> retMap = new HashMap<String, String>();
//
//        Set<Map.Entry<String, String[]>> entrySet = request.getParameterMap().entrySet();
//
//        for (Map.Entry<String, String[]> entry : entrySet) {
//            String name = entry.getKey();
//            String[] values = entry.getValue();
//            int valLen = values.length;
//
//            if (valLen == 1) {
//                retMap.put(name, values[0]);
//            } else if (valLen > 1) {
//                StringBuilder sb = new StringBuilder();
//                for (String val : values) {
//                    sb.append(",").append(val);
//                }
//                retMap.put(name, sb.toString().substring(1));
//            } else {
//                retMap.put(name, "");
//            }
//        }
//        return retMap;
//    }
//
//
//
//
//
//    private AlipayNotifyParam buildAlipayNotifyParam(Map<String, String> params) {
//        String json = JSON.toJSONString(params);
//        return JSON.parseObject(json, AlipayNotifyParam.class);
//    }
//
//
//    /**
//     * 执行付款成功后的业务逻辑
//     */
//    private void updateOrder(String orderSn,Date gmtPayment,String paramsJson){
//        Order oldOrder = orderMapper.selectByOrderSn(orderSn);
//        if(oldOrder.getPayType().equals(0)){//是未付款订单再去执行
//            Order order = new Order();
//            OrderOperateHistory history = new OrderOperateHistory();
//
//            if(oldOrder.getOrderType().equals(ServiceConst.ORDER_TYPE.ZHENG_CHANG)){
//                //正常订单   将当前订单状态修改为已付款
//                order.setStatus(ServiceConst.ORDER_STATUS.DAI_FAHUO);
//                history.setOrderStatus(ServiceConst.ORDER_STATUS.DAI_FAHUO);
//                history.setNote("付款完毕，修改订单为代发货状态");
//            }else{
//                //拼团订单   将当前订单状态修改为拼团中
//                order.setStatus(ServiceConst.ORDER_STATUS.PINTUAN);
//                history.setOrderStatus(ServiceConst.ORDER_STATUS.PINTUAN);
//                history.setNote("付款完毕，修改订单为拼团中");
//            }
//            order.setPayType(1);
//            order.setOrderSn(orderSn);
//            order.setPaymentTime(gmtPayment);
//            int a = orderMapper.updateStatusByOrderSn(order);
//            if(a < 0){
//                logger.error("支付宝回调业务处理报错,params:" +paramsJson+"Order参数:"+ order.toString());
//            }else{
//                history.setOrderId(oldOrder.getId());
//                history.setOperateMan("买家");
//                history.setCreateTime(new Date());
//
//                historyMapper.insertSelective(history);
//                List<OrderItem> idList = orderItemMapper.getItemByOrderSn(orderSn);
//                for(OrderItem item : idList){
//                    skuStockMapper.updateSkuLock(item.getId(),item.getProductQuantity());
//                }
//            }
//
//            //拼团订单处理
//            if(oldOrder.getOrderType().equals(ServiceConst.ORDER_TYPE.PIN_TUAN)){
//                //根据拼团号查询当前团是否拼成功，成功的话修改所有订单类型为拼团中的改为代发货，没有成功的话不修改
//                PtCode ptCode = ptCodeMapper.selectByPrimaryKey(oldOrder.getPtId());
//                ptCode.setId(oldOrder.getPtId());
//                if(ptCode.getPtStatus().equals(3)){
//                    ptCode.setPtStatus(0);
//                    ptCode.setEndTime(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000));//设置结束时间为24小时后
//                }
//                ptCodeMapper.updatePtNumber(ptCode);
//                if(ptCode.getPtNumber()+1 >= ptCode.getShopPtNumber()){
//                    //拼团成功，人数达标，修改为代发货
//                    orderMapper.updatePtStatus(oldOrder.getPtId());
//                }
//            }
//
//        }
//    }
//
//
//
//
//    /**
//     * 1、商户需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
//     * 2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
//     * 3、校验通知中的seller_id（或者seller_email)是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email），
//     * 4、验证app_id是否为该商户本身。上述1、2、3、4有任何一个验证不通过，则表明本次通知是异常通知，务必忽略。
//     * 在上述验证通过后商户必须根据支付宝不同类型的业务通知，正确的进行不同的业务处理，并且过滤重复的通知结果数据。
//     * 在支付宝的业务通知中，只有交易通知状态为TRADE_SUCCESS或TRADE_FINISHED时，支付宝才会认定为买家付款成功。
//     *
//     * @param params
//     * @throws AlipayApiException
//     */
//    private void check(Map<String, String> params) throws AlipayApiException {
//        String outTradeNo = params.get("out_trade_no");
//
//        // 1、商户需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
//        Order order = orderMapper.selectByOrderSn(outTradeNo);
//        if (order == null) {
//            throw new AlipayApiException("out_trade_no错误");
//        }
//
//        // 2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
//        int result = new BigDecimal(params.get("total_amount")).compareTo(order.getPayAmount());
//        if(result != 0){
//            throw new AlipayApiException("error 付款金额与实际金额不匹配");
//        }
//
//
//        // 3、校验通知中的seller_id（或者seller_email)是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email），
//        // 第三步可根据实际情况省略
//
//        // 4、验证app_id是否为该商户本身。
//        if (!params.get("app_id").equals(AliApppayConfig.APPID)) {
//            throw new AlipayApiException("app_id不一致");
//        }
//    }
//
//
//
//
//
//
//
//
//}
