package com.master.qianyi.manager.controller;

import com.master.qianyi.manager.service.MessageManService;
import com.master.qianyi.utils.EasyUIDataGridResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/message")
public class MessageManController {

    @Autowired
    private MessageManService messageManServiceImpl;

    @GetMapping("/getFeedbackList")
    @ResponseBody
    public EasyUIDataGridResult getFeedbackList(@RequestParam(value = "page") int pageNum,
                                             @RequestParam(value = "rows") int pageSize) {
        return messageManServiceImpl.getFeedbackList(pageNum, pageSize);
    }

    @GetMapping("/delete")
    @ResponseBody
    public Map<String, String> delete(String ids) {
        return messageManServiceImpl.delete(ids);
    }

}
