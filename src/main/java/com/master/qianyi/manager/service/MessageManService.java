package com.master.qianyi.manager.service;

import com.master.qianyi.utils.EasyUIDataGridResult;

import java.util.Map;

public interface MessageManService {

    EasyUIDataGridResult getFeedbackList(int pageNum, int pageSize);

    Map<String, String> delete(String ids);
}
