package com.master.qianyi.information.controller;

import com.master.qianyi.information.service.InformationService;
import com.master.qianyi.pojo.TbComment;
import com.master.qianyi.pojo.TbInformation;
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
    @GetMapping("/searchInformations")
    @ResponseBody
    public ResultBean getInformation(int pageNum, int pageSize, String infoType) {
        return service.getInformation(pageNum, pageSize, infoType);
    }

    /**
     * 根据资讯id查找资讯(加上评论)
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
     * *添加评论（课程评论、资讯评论、订单评论）
     *
     * @param id          课程id 或 资讯id 或 订单id
     * @param userId      用户id
     * @param commentType 评论类型（1：课程 or 2：资讯 or 3：订单）
     * @param commentType 评论内容
     * @return
     */
    @RequestMapping("/insertComment")
    public ResultBean insertComment(String id, String userId, String commentType, String commentContent) {
        return service.insertComment(id, userId, commentType, commentContent);
    }
}
