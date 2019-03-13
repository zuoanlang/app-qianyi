package com.master.qianyi.manager.controller;

import com.master.qianyi.manager.service.SmsConfirmService;
import com.master.qianyi.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sms")
public class SmsConfirmController {

    @Autowired
    private SmsConfirmService smsConfirmService;

    @GetMapping("/sendMsg")
    public Result sendMessage(String phoneNumber){
        return smsConfirmService.sendMessage(phoneNumber);
    }


}
