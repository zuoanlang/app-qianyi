package com.master.qianyi.manager.controller;

import com.master.qianyi.manager.service.InfoManService;
import com.master.qianyi.utils.EasyUIDataGridResult;
import com.master.qianyi.utils.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 后台管理-资讯编辑模块
 */
@RestController
@RequestMapping("/infoMan")
public class InfoManController {

    @Autowired
    private InfoManService infoServiceImpl;

    @RequestMapping("/addNews")
    public ResultBean addNews(@RequestParam(value="infoType")Long infoType,
                              @RequestParam(value="infoTitle")String infoTitle,
                              @RequestParam(value="infoImgPath")String infoImgPath,
                              @RequestParam(value="infoWriter")String infoWriter,
                              @RequestParam(value="infoContent")String infoContent){
        return infoServiceImpl.addNews(infoType,infoTitle,infoImgPath,infoWriter,infoContent);
    }

    @RequestMapping("/editNews")
    public ResultBean editNews(@RequestParam(value="infoId")String infoId,
                               @RequestParam(value="infoType")Long infoType,
                               @RequestParam(value="infoTitle")String infoTitle,
                               @RequestParam(value="infoImgPath")String infoImgPath,
                               @RequestParam(value="infoWriter")String infoWriter,
                               @RequestParam(value="effectFlag")String effectFlag,
                               @RequestParam(value="infoContent")String infoContent){
        return infoServiceImpl.editNews(infoId,infoType,infoTitle,infoImgPath,infoWriter,effectFlag,infoContent);
    }

    @RequestMapping("/deleteNews")
    public ResultBean deleteNews(@RequestParam(value="infoIds")String infoIds){
        return infoServiceImpl.deleteNews(infoIds);
    }

    @RequestMapping("/publish")
    public ResultBean publish(@RequestParam(value="infoIds")String infoIds,
                                 @RequestParam(value="type")int type){
        return infoServiceImpl.publish(infoIds,type);
    }

    //获取infoList
    @GetMapping("/getInfoList")
    public EasyUIDataGridResult getInfoList(@RequestParam(value="page")int pageNum ,
                                            @RequestParam(value="rows")int pageSize,
                                            String infoId,String infoTitle,String infoWriter) {
        EasyUIDataGridResult infoList = infoServiceImpl.getNewsInfoList(pageNum, pageSize,infoId,infoTitle,infoWriter);
        return infoList;
    }
}
