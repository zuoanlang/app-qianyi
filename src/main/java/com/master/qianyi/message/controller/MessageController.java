package com.master.qianyi.message.controller;

import com.master.qianyi.message.service.MessageService;
import com.master.qianyi.utils.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @RequestMapping("/feedback")
    public ResultBean feedback(@RequestParam(value = "userId")String userId,
                               @RequestParam(value = "token")String token,
                               @RequestParam(value = "messageContent")String messageContent){

        return messageService.sendMessage(userId, token, messageContent);
    }

    @GetMapping("/courseNotice")
    public ResultBean courseNotice(String userId){
        return messageService.courseNotice(userId);
    }

    @PostMapping("/read")
    public ResultBean read(@RequestParam(value = "messageId")String messageId){
        return messageService.read(messageId);
    }


}
