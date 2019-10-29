package com.master.qianyi.information.controller;

import com.master.qianyi.information.service.InformationService;
import com.master.qianyi.utils.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 资讯controller
 */
@RestController
@RequestMapping("/info")
public class InformationController {

    @Autowired
    private InformationService service;


    /**
     * 查询资讯
     *
     * @param pageNum  页码
     * @param pageSize 当前页条数
     * @param infoType 咨询类型（为空查询全部类型）
     * @return
     */
    @GetMapping("/getInformationList")
    @ResponseBody
    public ResultBean getInformation(@RequestParam(value="pageNum", defaultValue="1") int pageNum,
                                     @RequestParam(value="pageSize", defaultValue="1")  int pageSize, String infoType) {
        return service.getInformation(pageNum, pageSize, infoType);
    }

    /**
     * 后台根据资讯id查找资讯details
     *
     * @param infoId
     * @return
     */
    @RequestMapping("/getInformationById")
    @ResponseBody
    public ResultBean getInformationById(String infoId) {
        return service.getInformationById(infoId);
    }

    /**
     * app根据资讯id查找资讯details
     *
     * @param infoId
     * @return
     */
    @RequestMapping("/getInfoDetails")
    @ResponseBody
    public ResultBean getInfoDetails(@RequestParam(value="infoId") String infoId) {
        return service.getInfoDetails(infoId);
    }

    /**
     * 调用时，改条资讯的阅读数+1
     * @return
     */
    @RequestMapping("/addInfoViewTimes")
    public ResultBean addInfoViewTimes(@RequestParam(value="infoId") String infoId){
        return service.addInfoViewTimes(infoId);
    }

}
