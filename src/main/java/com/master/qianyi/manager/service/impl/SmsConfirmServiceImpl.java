package com.master.qianyi.manager.service.impl;

import com.master.qianyi.manager.service.SmsConfirmService;
import com.master.qianyi.mapper.TbUserMapper;
import com.master.qianyi.pojo.TbUser;
import com.master.qianyi.pojo.TbUserExample;
import com.master.qianyi.utils.Constants;
import com.master.qianyi.utils.Result;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class SmsConfirmServiceImpl implements SmsConfirmService {

    @Autowired
    private TbUserMapper tbuserMapper;

    @Override
    public Result sendMessage(String phoneNum) {

        Result result = new Result();
        HttpClient client = new HttpClient();
        PostMethod method = new PostMethod(Constants.MESSAGE_URL);

        client.getParams().setContentCharset("GBK");
        method.setRequestHeader("ContentType", "application/x-www-form-urlencoded;charset=GBK");
        int mobile_code = (int) ((Math.random() * 9 + 1) * 100000);
        String content = new String("您的验证码是：" + mobile_code + "。请不要把验证码泄露给其他人。");
        NameValuePair[] data = {//提交短信
                new NameValuePair("account", "C10224620"), //查看用户名 登录用户中心->验证码通知短信>产品总览->API接口信息->APIID
                new NameValuePair("password", "980c2f5e9dde36d71a7b57dacd0847c2"), //查看密码 登录用户中心->验证码通知短信>产品总览->API接口信息->APIKEY
                //new NameValuePair("password", util.StringUtil.MD5Encode("密码")),
                new NameValuePair("mobile", phoneNum),
                new NameValuePair("content", content),
        };
        method.setRequestBody(data);

        try {
            client.executeMethod(method);

            String SubmitResult = method.getResponseBodyAsString();

            System.out.println(SubmitResult);
            System.out.println(mobile_code);


            Document doc = DocumentHelper.parseText(SubmitResult);
            Element root = doc.getRootElement();

            String code = root.elementText("code");
            String msg = root.elementText("msg");

            if ("2".equals(code)) {
                result.setErrCode(Constants.errCode_0);
                result.setErrMsg(Constants.errMsg_success);
                result.setResult(msg);
                //验证码存数据库，便于验证
                updateSms(phoneNum);
            }

        } catch (HttpException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    //将验证码存入数据库
    private boolean updateSms(String phoneNum){
        //1. 根据phoneNUmber查询用户
        TbUserExample userExample = new TbUserExample();
        TbUserExample.Criteria criteria = userExample.createCriteria();
        criteria.andEffectFlagEqualTo("1").andDeleteFlagEqualTo("0").andHandphoneEqualTo(phoneNum);
        List<TbUser> userList = tbuserMapper.selectByExample(userExample);
        TbUser user = null;
        if(userList!=null && userList.size()>0){
            user = userList.get(0);
            //2. 设置更新条件
            user.setSms(phoneNum);
            //3. 跟新节点
            int i = tbuserMapper.updateByPrimaryKeySelective(user);
            if(i>0){
                return true;
            }

        }
        return false;
    }
}
