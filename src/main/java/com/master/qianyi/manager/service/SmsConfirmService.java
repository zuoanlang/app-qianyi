package com.master.qianyi.manager.service;

import com.master.qianyi.utils.Result;

public interface SmsConfirmService {

    public Result sendMessage(String phoneNum);
}
