package com.master.qianyi.manager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/oss")
public class OssFileUploadController {


    @GetMapping("/getUploadToken")
    @ResponseBody
    public Map<String,String> getUploadToken(){
        Map<String,String> tokenMap = new HashMap<>();
        tokenMap.put("region","oss-cn-shenzhen");
        tokenMap.put("accessKeyId","LTAIh7QUIIjheNnI");
        tokenMap.put("accessKeySecret","oss-cn-shenzhen");
        tokenMap.put("bucket","qianyi-app");
        tokenMap.put("stsToken",null);
        return tokenMap;
    }
}
