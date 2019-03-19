package com.master.qianyi.manager.service;

import com.master.qianyi.utils.ResultBean;

public interface MobileService {

    /**
     * 发送验证码
     *
     * @param phoneNumber
     * @return
     */
    ResultBean mobileSendSms(String phoneNumber);

    /**
     * 注册操作（用户名、手机号、验证码、密码）
     *
     * @param userName
     * @param phoneNumber
     * @param mobileCode
     * @param password
     * @return
     */
    ResultBean mobileSmsRegister(String userName, String phoneNumber, int mobileCode, String password);

    /**
     * 登录操作（手机号、密码）
     *
     * @param request
     * @param phoneNumber
     * @param mobileCode
     * @return
     */
    ResultBean mobilePasswordLogin(String phoneNumber, String password);

    /**
     * 重置密码
     *
     * @param phoneNumber
     * @param mobileCode
     * @param password
     * @return
     */
    ResultBean resetPassword(String phoneNumber, int mobileCode, String password);

    ResultBean threePartyLogin(String openid,String type);

}
