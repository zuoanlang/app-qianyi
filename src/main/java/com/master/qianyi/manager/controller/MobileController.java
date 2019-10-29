package com.master.qianyi.manager.controller;

import com.master.qianyi.manager.service.MobileService;
import com.master.qianyi.utils.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mobile")
public class MobileController {

    @Autowired
    private MobileService mobileService;

    /**
     * 发送验证码（用于找回密码、注册）
     *
     * @param phoneNumber
     * @return
     */
    @PostMapping("/sendSms")
    public ResultBean sendMessage(@RequestParam(value="phoneNumber")String phoneNumber) {
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
    public ResultBean resetPassword( @RequestParam(value="phoneNumber")String phoneNumber,
                                     @RequestParam(value="mobileCode")int mobileCode,
                                     @RequestParam(value="password")String password){
        return mobileService.resetPassword(phoneNumber,mobileCode,password);
    }

    @PostMapping("/threePartyLogin")
    public ResultBean threePartyLogin( @RequestParam(value="openid")String openid,
                                       @RequestParam(value="type")String type,
                                       @RequestParam(value="headImg")String headImg,
                                       @RequestParam(value="nickName")String nickName){
        return mobileService.threePartyLogin(openid,type,headImg,nickName);
    }

    @PostMapping("/bindPhoneNumber")
    public ResultBean bindPhoneNumber(  @RequestParam(value="phoneNumber")String phoneNumber,
                                        @RequestParam(value="mobileCode")int mobileCode,
                                        @RequestParam(value="openid")String openid,
                                        @RequestParam(value="type")String type){
        return mobileService.bindPhoneNumber(phoneNumber,mobileCode,openid,type);
    }


}
