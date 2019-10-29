package com.master.qianyi.comment.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.master.qianyi.comment.form.BriefComment;
import com.master.qianyi.comment.form.CourseComment;
import com.master.qianyi.comment.form.InfoComment;
import com.master.qianyi.comment.form.ServiceComment;
import com.master.qianyi.mapper.TbCommentMapper;
import com.master.qianyi.mapper.TbCourseMapper;
import com.master.qianyi.order.service.OrderService;
import com.master.qianyi.pojo.*;
import com.master.qianyi.user.form.MyUserInfo;
import com.master.qianyi.user.service.UserService;
import com.master.qianyi.utils.Constants;
import com.master.qianyi.utils.EasyUIDataGridResult;
import com.master.qianyi.utils.IDUtils;
import com.master.qianyi.utils.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class CommentService {

    @Autowired
    private TbCommentMapper tbCommentMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private TbCourseMapper tbCourseMapper;

    /**
     * 根据userId获取评论列表
     * (订单评论)
     *
     * @param pageNum
     * @param pageSize
     * @param userId
     * @param commentType 1.订单，2.资讯，3.课程
     * @return
     */
    public ResultBean getServiceCommentList(int pageNum, int pageSize, String userId, String commentType) {
        ResultBean bean = new ResultBean();
        TbCommentExample example = new TbCommentExample();
        //根据userId查询订单列表（已评价）
        List<TbOrder> orderList = userService.getOrderListByMasterUserId(pageNum, pageSize, userId, "2");
        //取得订单的orderIds
        List<String> orderIds = new ArrayList<>();
        List<ServiceComment> commentList = null;
        if (orderList.size() > 0) {
            for (TbOrder order : orderList) {
                orderIds.add(order.getOrderId());
            }
            example.createCriteria()
                    .andDeleteFlagEqualTo("0")
                    .andEffectFlagEqualTo("1")
                    .andCommentarySubjectIdIn(orderIds)
                    .andCommentTypeEqualTo(commentType)
                    .andRemark1EqualTo("1");
            // 设置分页
            PageHelper.startPage(pageNum, pageSize);
            example.setOrderByClause("comment_date_time desc");
            List<TbComment> tbComments = tbCommentMapper.selectByExample(example);
            commentList = getShowBackCommentList(tbComments, orderList);
        } else {
            commentList = new ArrayList<>();
        }

        bean.setResult(commentList);
        bean.setCode(Constants.code_0);
        bean.setMsg(Constants.msg_success);
        return bean;

    }

    /**
     * 构造返回界面的评论列表
     * 订单评论+资讯评论
     *
     * @param tbComments
     * @return
     */
    public List<ServiceComment> getCommentList(List<TbComment> tbComments) {
        List<ServiceComment> comments = new ArrayList<>();
        ServiceComment comment = null;
        MyUserInfo userInfo = null;
        if (tbComments != null && tbComments.size() > 0) {
            //1.根据评论的被评论主体commentedIdList(orderId)取得订单orderList
            List<String> commentedIdList = new ArrayList<>();
            for (TbComment tbComment : tbComments) {
                commentedIdList.add(tbComment.getCommentarySubjectId());
            }
            //2.根据orderList的查询订单list
            List<TbOrder> orderListByIds = orderService.getOrderListByIds(commentedIdList);

            List<TbOrder> orderList = new ArrayList<>();
            //构造评论列表
            for (TbComment tbComment : tbComments) {
                //1.根据评论人的id取得用户信息
                ResultBean userInfoBean = userService.getMyInfoByUserId(tbComment.getCommentUserId());
                userInfo = (MyUserInfo) userInfoBean.getResult();
                //2.取得服务名称（根据查询订单表,取得goodId->serviceName:1.课程购买，2.测算服务）
                String serviceName = "";
                for (TbOrder order : orderListByIds) {
                    if (order.getOrderId().equals(tbComment.getCommentarySubjectId())) {
                        serviceName = order.getGoodId();
                    }
                }
                //3.评论时间转为时间戳
                String commentDateTime = String.valueOf(tbComment.getCommentDateTime().getTime());
                comment = new ServiceComment(userInfo.getNickName(), userInfo.getHeadImg(), serviceName, commentDateTime, tbComment.getCommentContent());
                comments.add(comment);
            }
        }
        return comments;
    }

    /**
     * 构造返回界面的评论列表
     *
     * @return
     */
    public List<ServiceComment> getShowBackCommentList(List<TbComment> commentList, List<TbOrder> orderList) {
        List<ServiceComment> comments = new ArrayList<>();
        //1.取评论人的userInfo
        List<String> userIds = new ArrayList<>();
        List<TbUser> usersByUserIds = null;
        if (commentList.size() > 0) {
            for (TbComment comment : commentList) {
                userIds.add(comment.getCommentUserId());
            }
            usersByUserIds = userService.getUsersByUserIds(userIds);
        } else {
            usersByUserIds = new ArrayList<>();
        }
        //2.构建commentId-goodName map
        Map<String, String> idNameMap = new HashMap<>();
        for (TbComment comment : commentList) {
            for (TbOrder order : orderList) {
                if (comment.getCommentarySubjectId().equals(order.getOrderId())) {
                    idNameMap.put(order.getOrderId(), order.getGoodName());
                }
            }
        }
        for (TbComment comment : commentList) {
            for (TbUser user : usersByUserIds) {
                if (user.getUserId().equals(comment.getCommentUserId())) {
                    comments.add(new ServiceComment(user.getUserName(), user.getHeadImg(), idNameMap.get(comment.getCommentarySubjectId()),
                            String.valueOf(comment.getCommentDateTime().getTime()), comment.getCommentContent()));
                }
            }
        }

        return comments;

    }

    /**
     * 根据订单列表查询评论列表
     *
     * @param orderIds
     * @return
     */
    public List<TbComment> getCommnetsByOrderIds(List<String> orderIds) {
        TbCommentExample example = new TbCommentExample();
        example.createCriteria().andEffectFlagEqualTo("1").andDeleteFlagEqualTo("0")
                .andCommentarySubjectIdIn(orderIds);
        List<TbComment> tbComments = tbCommentMapper.selectByExample(example);
        if (tbComments != null) {
            return tbComments;
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * 根据userId get 评论（提问）列表
     *
     * @param pageNum
     * @param pageNum
     * @param courseId
     * @return ResultBean
     */
    public ResultBean getCourseQuizList(int pageNum, int pageSize, String courseId) {
        ResultBean bean = new ResultBean();
        TbCommentExample example = new TbCommentExample();
        TbCommentExample.Criteria criteria = example.createCriteria();
        TbCourse course = tbCourseMapper.selectByPrimaryKey(courseId);
        //1.订单评论，2.资讯评论，3.课程评论
        criteria.andCommentarySubjectIdEqualTo(courseId).andEffectFlagEqualTo("1")
                .andDeleteFlagEqualTo("0").andCommentTypeEqualTo("3");
        example.setOrderByClause("comment_date_time desc");
        //分页
        PageHelper.startPage(pageNum, pageSize);
        //2.获取评论列表
        List<TbComment> commentList = tbCommentMapper.selectByExample(example);
        if (commentList != null && commentList.size() > 0) {
            //3.根据评论列表的userIds获取usersList
            List<String> userIds = getUserIdsFromComments(commentList);
            List<TbUser> userList = userService.getUsersByUserIds(userIds);
            //4.构建Map<String,List<TbUser>>
            Map<String, TbUser> userMap = userService.getIdUserMap(userList);
            //5.筛选父评论 comment_father_id
            List<TbComment> fatherComments = new ArrayList<>();
            List<TbComment> sonComments = new ArrayList<>();
            for (TbComment tbComment : commentList) {
                if (tbComment.getCommentFatherId() == null) {
                    fatherComments.add(tbComment);
                } else {
                    sonComments.add(tbComment);
                }
            }
            //6.构建Map<String, List<BriefComment>>
            Map<String, List<BriefComment>> idBriefCommentMap = getBriefCommentListByFatherId(fatherComments, sonComments, userMap);

            //7.构建界面课程提问评论
            List<CourseComment> courseComments = new ArrayList<>();
            CourseComment courseComment;
            for (TbComment tbComment : fatherComments) {
                courseComment = new CourseComment();
                courseComment.setMasterId(course.getCourseBelongto());
                courseComment.setCommentId(tbComment.getCommentId());
                if (userMap.containsKey(tbComment.getCommentUserId())){
                    courseComment.setUserName(userMap.get(tbComment.getCommentUserId()).getUserName());
                    courseComment.setHeadImg(userMap.get(tbComment.getCommentUserId()).getHeadImg());
                } else {
                    continue;
                }
                //3.评论时间转为时间戳
                String commentDateTime = String.valueOf(tbComment.getCommentDateTime().getTime());
                courseComment.setCommentDateTime(commentDateTime);
                courseComment.setCommentContent(tbComment.getCommentContent());
                courseComment.setBriefCommentList(idBriefCommentMap.get(tbComment.getCommentId()));
                courseComments.add(courseComment);
            }
            bean.setMsg(Constants.msg_success);
            bean.setCode(Constants.code_0);
            bean.setResult(courseComments);
        } else {
            bean.setMsg(Constants.msg_success);
            bean.setCode(Constants.code_0);
            bean.setResult(new ArrayList<>());
        }
        return bean;
    }

    /**
     * 根据父评论获取子评论列表
     *
     * @return
     */
    public Map<String, List<BriefComment>> getBriefCommentListByFatherId(List<TbComment> fatherComments,
                                                                         List<TbComment> sonComments,
                                                                         Map<String, TbUser> userMap) {
        Map<String, List<BriefComment>> idsCommentsMap = new HashMap<>();
        List<BriefComment> briefComments;
        for (TbComment tbCommentF : fatherComments) {
            idsCommentsMap.put(tbCommentF.getCommentId(),null);
            briefComments = new ArrayList<>();
            for(int i= sonComments.size()-1;i>=0;i--){
                BriefComment briefComment;
                if(sonComments.get(i).getCommentFatherId().equals(tbCommentF.getCommentId())){
                    briefComment = new BriefComment();
                    briefComment.setUserName(userMap.get(sonComments.get(i).getCommentUserId()).getUserName());
                    briefComment.setCommentContent(sonComments.get(i).getCommentContent());
                    briefComments.add(briefComment);
                }
            }
            idsCommentsMap.put(tbCommentF.getCommentId(),briefComments);
        }

        return idsCommentsMap;

    }

    /**
     * 根据评论取评论人userIds
     *
     * @param tbComments
     * @return userIds
     */
    public List<String> getUserIdsFromComments(List<TbComment> tbComments) {
        List<String> userIds = new ArrayList<>();
        for (TbComment tbComment : tbComments) {
            userIds.add(tbComment.getCommentUserId());
        }
        return userIds;
    }

    /**
     * *添加评论（课程评论、资讯评论、订单评论）
     *
     * @param id                课程id 或 资讯id 或 订单id
     * @param userId            用户id
     * @param commentType       评论类型（1.订单评论，2.资讯评论，3.课程评论）
     * @param commentContent    评论内容
     * @return
     */
    public ResultBean insertComment(String id, String userId, String commentType, String commentContent,int oderScore) {
        ResultBean bean = new ResultBean();
        TbComment tbComment = new TbComment();
        tbComment.setCommentId(IDUtils.genItemId());
        tbComment.setCommentUserId(userId);
        tbComment.setCommentarySubjectId(id);
        tbComment.setCommentType(commentType);
        tbComment.setCommentContent(commentContent);
        if(commentType.equals("1")){
            if(oderScore<=0){
                bean.setCode(Constants.code_1);
                bean.setMsg("评分不能为空,且只能取1,2,3,4,5");
                return bean;
            } else {
                tbComment.setCommentScore(oderScore);
            }
        }
        tbComment.setCommentDateTime(new Date(System.currentTimeMillis()));
        tbComment.setEffectFlag("1");
        tbComment.setDeleteFlag("0");
        int insertFlag = tbCommentMapper.insertSelective(tbComment);
        if (insertFlag != 1) {
            bean.setCode(Constants.code_1);
            bean.setMsg(Constants.msg_failed);
        } else {
            bean.setCode(Constants.code_0);
            bean.setMsg(Constants.msg_success);
        }
        return bean;
    }

    /**
     * 根据infoId get commentList
     * @param   pageNum
     * @param   pageSize
     * @param   infoId
     * @return  List<InfoComment>
     */
    public List<InfoComment> getInfoComment(int pageNum, int pageSize, String infoId) {
        TbCommentExample example = new TbCommentExample();
        TbCommentExample.Criteria criteria = example.createCriteria();
        criteria.andCommentarySubjectIdEqualTo(infoId).andEffectFlagEqualTo("1")
                .andDeleteFlagEqualTo("0").andCommentTypeEqualTo("2").andRemark1EqualTo("2");
        PageHelper.startPage(pageNum,pageSize);
        example.setOrderByClause("comment_date_time desc");
        List<TbComment> commentList = tbCommentMapper.selectByExample(example);
        List<InfoComment> infoComments = new ArrayList<>();
        if(commentList!=null && commentList.size()>0){
            List<String> userIdsFromComments = getUserIdsFromComments(commentList);
            List<TbUser> userList = userService.getUsersByUserIds(userIdsFromComments);
            //4.构建Map<String,List<TbUser>>
            Map<String, TbUser> userMap = userService.getIdUserMap(userList);
            InfoComment infoComment;
            for (TbComment comment:commentList){
                infoComment = new InfoComment();
                if(userMap.containsKey(comment.getCommentUserId())){
                    infoComment.setUserName(userMap.get(comment.getCommentUserId()).getNickName());
                    if (userMap.get(comment.getCommentUserId()).getHeadImg() != null){
                        infoComment.setHeadImg(userMap.get(comment.getCommentUserId()).getHeadImg());
                    } else {
                        infoComment.setHeadImg("/qianyi/images/default_avatar.jpg");
                    }

                } else {
                    infoComment.setUserName("乾**");
                    infoComment.setHeadImg("/qianyi/images/default_avatar.jpg");
                }
                infoComment.setCommentDateTime(new SimpleDateFormat("yyyy-MM-dd").format(comment.getCommentDateTime()));
                infoComment.setCommentContent(comment.getCommentContent());
                infoComments.add(infoComment);
            }

        }

        return infoComments;
    }

    /**
     * 根据infoId 查询资讯评论列表
     * @param pageNum
     * @param pageSize
     * @param infoId
     * @return
     */
    public ResultBean getInfoCommentList(int pageNum, int pageSize, String infoId){
        ResultBean bean = new ResultBean();
        List<InfoComment> infoComment = getInfoComment(pageNum, pageSize, infoId);
        bean.setResult(infoComment);
        bean.setMsg(Constants.msg_success);
        bean.setCode(Constants.code_0);

        return bean;
    }

    public ResultBean masterReply(String id, String userId, String commentType, String commentFatherId, String commentContent) {
        ResultBean bean = new ResultBean();
        TbComment tbComment = new TbComment();
        tbComment.setCommentId(IDUtils.genItemId());
        tbComment.setCommentUserId(userId);

        tbComment.setCommentFatherId(commentFatherId);
        tbComment.setCommentarySubjectId(id);

        tbComment.setCommentType(commentType);
        tbComment.setCommentContent(commentContent);
        tbComment.setCommentDateTime(new Date(System.currentTimeMillis()));

        tbComment.setEffectFlag("1");
        tbComment.setDeleteFlag("0");
        int insertFlag = tbCommentMapper.insertSelective(tbComment);
        if (insertFlag != 1) {
            bean.setCode(Constants.code_1);
            bean.setMsg(Constants.msg_failed);
        } else {
            bean.setCode(Constants.code_0);
            bean.setMsg(Constants.msg_success);
        }
        return bean;
    }

    public EasyUIDataGridResult getCommentApplyList(int page, int rows) {
        EasyUIDataGridResult result = new EasyUIDataGridResult();
        TbCommentExample commentExample = new TbCommentExample();
        TbCommentExample.Criteria criteria = commentExample.createCriteria();
        criteria.andEffectFlagEqualTo("1")
                .andDeleteFlagEqualTo("0");
        commentExample.setOrderByClause("remark1 asc,comment_date_time desc");
        List<TbComment> commentList = tbCommentMapper.selectByExample(commentExample);
        result.setRows(commentList);
        PageInfo<TbComment> pageInfo = new PageInfo<>(commentList);
        result.setTotal(pageInfo.getTotal());
        return result;
    }

    public Map<String, String> approval(String flag,String commentId) {
        Map<String, String> infoMap = new HashMap<>();
        TbCommentExample example = new TbCommentExample();
        TbCommentExample.Criteria criteria = example.createCriteria();
        //审批多条
        String[] split = commentId.split(",");
        List<String> ids = new ArrayList<>();
        for (int i = 0; i < split.length; i++) {
            ids.add(split[i]);
        }
        criteria.andCommentIdIn(ids);
        TbComment comment = new TbComment();
        if ("0".equals(flag)){
            comment.setRemark1("3");
        } else {
            comment.setRemark1("2");
        }
        int update = tbCommentMapper.updateByExampleSelective(comment,example);
        if (update>0){
            infoMap.put("code","0");
        } else {
            infoMap.put("code","1");
            infoMap.put("msg","审批失败，请刷新重试");
        }
        return infoMap;
    }

    public Map<String, String> delete(String commentId) {
        Map<String, String> infoMap = new HashMap<>();
        int i = tbCommentMapper.deleteByPrimaryKey(commentId);
        if (i>0){
            infoMap.put("code","0");
        } else {
            infoMap.put("code","1");
            infoMap.put("msg","删除失败，请刷新重试");
        }
        return infoMap;
    }
}
