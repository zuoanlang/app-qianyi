package com.master.qianyi.information.controller;

import com.master.qianyi.information.service.InformationService;
import com.master.qianyi.manager.pojo.BaseResult;
import com.master.qianyi.pojo.TbInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @GetMapping("/searchInformations")
    @ResponseBody
    public List<TbInformation> getInformation(int pageNum, int pageSize, String infoType) {
        return service.getInformation(pageNum, pageSize, infoType);
    }

    @RequestMapping("/insertInfo")
    public BaseResult informationAdd(@RequestBody TbInformation information) {
        return null;
    }
}
