package com.master.qianyi.utils;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;

public class SmsSendUtils {

    public static boolean sendSms(String PhoneNumbers,int mobile_code){
        DefaultProfile profile = DefaultProfile.getProfile("default", "LTAIh7QUIIjheNnI", "o6YUhS76FNkr8k6BXYjkeqwu0TXKqA");
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        //request.setProtocol(ProtocolType.HTTPS);
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("PhoneNumbers", PhoneNumbers);
        request.putQueryParameter("SignName", "乾易");
        request.putQueryParameter("TemplateCode", "SMS_160630065");
        request.putQueryParameter("TemplateParam", "{\"code\":\""+mobile_code+"\"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            JSONObject jsonResult = JSONObject.parseObject(response.getData());
            String code = (String) jsonResult.get("Code");
            if(code.equals("OK")){
                return true;
            } else {
                return false;
            }
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }

        return false;


    }

    public static void main(String[] args) {
        sendSms("13685510180",1314520);
    }


}
