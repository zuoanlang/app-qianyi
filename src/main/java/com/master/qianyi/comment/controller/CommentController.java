package com.master.qianyi.comment.controller;

import com.master.qianyi.comment.service.CommentService;
import com.master.qianyi.utils.EasyUIDataGridResult;
import com.master.qianyi.utils.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    /**
     * 获取名师评论列表
     *
     * @param userId      名师id
     * @param commentType 评论的类型
     * @return ResultBean
     */
    @RequestMapping("/getCommentList")
    public ResultBean getCommentList(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                     @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                     @RequestParam(value = "userId") String userId,
                                     @RequestParam(value = "commentType") String commentType) {
        ResultBean commentList = commentService.getServiceCommentList(pageNum, pageSize, userId, commentType);
        return commentList;
    }

    /**
     * 根据课程courseId取课程提问
     *
     * @param courseId 课程id
     * @return ResultBean 结果
     */
    @RequestMapping("/getCourseQuizList")
    public ResultBean getCourseQuizList(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                        @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                        @RequestParam(value = "courseId") String courseId) {
        return commentService.getCourseQuizList(pageNum, pageSize, courseId);
    }

    /**
     * 名师回复课程提问接口
     *
     * @param courseId 课程id
     * @return ResultBean 结果
     */
    @RequestMapping("/masterReply")
    public ResultBean masterReply(@RequestParam(value = "id") String id,
                                  @RequestParam(value = "userId") String userId,
                                  @RequestParam(value = "commentType") String commentType,
                                  @RequestParam(value = "commentFatherId") String commentFatherId,
                                  @RequestParam(value = "commentContent") String commentContent) {
        return commentService.masterReply(id, userId, commentType, commentFatherId, commentContent);
    }

    /**
     * *添加评论（课程评论、资讯评论、订单评论）
     *
     * @param id          课程id 或 资讯id 或 订单id
     * @param userId      用户id
     * @param commentType 评论类型（1：课程(提问) or 2：资讯 or 3：订单（1.测算服务,2.课程购买））
     * @param commentType 评论内容
     * @return
     */
    @RequestMapping("/insertComment")
    public ResultBean insertComment(@RequestParam(value = "id") String id,
                                    @RequestParam(value = "userId") String userId,
                                    @RequestParam(value = "commentType") String commentType,
                                    @RequestParam(value = "commentContent") String commentContent,
                                    @RequestParam(value = "oderScore", required = false, defaultValue = "0") int oderScore) {
        return commentService.insertComment(id, userId, commentType, commentContent, oderScore);
    }

    @RequestMapping("/getInfoCommentList")
    public ResultBean insertComment(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                    @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                    @RequestParam(value = "infoId") String infoId) {
        return commentService.getInfoCommentList(pageNum, pageSize, infoId);
    }

    @GetMapping("/getCommentApplyList")
    public EasyUIDataGridResult getCommentApplyList(int page , int rows){
        return commentService.getCommentApplyList(page , rows);
    }

    @PostMapping("/approval")
    public Map<String,String> approval(String flag,String commentId){
        return commentService.approval(flag,commentId);
    }

    @PostMapping("/delete")
    public Map<String,String> delete(String commentId){
        return commentService.delete(commentId);
    }

}
