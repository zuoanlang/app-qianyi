package com.master.qianyi.manager.controller;

import com.master.qianyi.manager.service.MobileService;
import com.master.qianyi.utils.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mobile")
public class MobileController {

    @Autowired
    private MobileService mobileService;

    /**
     * 发送验证码（用于找回密码、注册）
     *
     * @param request
     * @param phoneNumber
     * @return
     */
    @GetMapping("/sendSms")
    public ResultBean sendMessage(String phoneNumber) {
        return mobileService.mobileSendSms(phoneNumber);
    }

    @PostMapping("/smsRegister")
    public ResultBean mobileSmsRegister(String userName, String phoneNumber, int mobileCode, String password){
        return mobileService.mobileSmsRegister(userName,phoneNumber,mobileCode,password);
    }

    @PostMapping("/passwordLogin")
    public ResultBean mobilePasswordLogin( String phoneNumber, String password){
        return mobileService.mobilePasswordLogin(phoneNumber,password);
    }

    @PostMapping("/resetPassword")
    public ResultBean resetPassword( String phoneNumber, int mobileCode, String password){
        return mobileService.resetPassword(phoneNumber,mobileCode,password);
    }

}
