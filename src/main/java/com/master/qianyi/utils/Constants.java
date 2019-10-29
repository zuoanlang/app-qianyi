package com.master.qianyi.utils;

import java.util.Arrays;
import java.util.List;

public final class Constants {

    //请求返回的状态码
    public static int code_0 = 0;
    public static int code_1 = 1;
    //token 已经过期
    public static int code_2 = 2;

    //请求返回的msg
    public static String msg_success = "success";
    public static String msg_failed = "failed";
    public static String msg_invalid = "当前用户已失效";

    //订单为已购买的订单状态status list
    //订单状态：0,全部订单，1已下单，2已付款，3已评价，4待审核，5已退款，6已失效
    public final static List<String> ordered_Status_LIST = Arrays.asList("2","3","4");

    //消息类型：1.用户反馈

    //订单状态
    public static String ORDER_STATUS_1 = "1";

    //入驻审批状态：0,未申请，1待审批，2已通过，3已驳回
    public final static List<String> apply_Status_LIST = Arrays.asList("1","2","3");

    //后台订单审批列表：4待审核，5已退款，6已失效，7已驳回
    public final static List<String> orderRrfund_Status_LIST = Arrays.asList("4","5","6","7");

}
