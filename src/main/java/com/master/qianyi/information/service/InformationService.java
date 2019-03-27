package com.master.qianyi.information.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.master.qianyi.information.form.InfomationForm;
import com.master.qianyi.mapper.TbCommentMapper;
import com.master.qianyi.mapper.TbInformationMapper;
import com.master.qianyi.pojo.TbComment;
import com.master.qianyi.pojo.TbCommentExample;
import com.master.qianyi.pojo.TbInformation;
import com.master.qianyi.pojo.TbInformationExample;
import com.master.qianyi.utils.Constants;
import com.master.qianyi.utils.ResultBean;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 资讯service
 */
@Service
public class InformationService {

    @Autowired
    private TbInformationMapper mapper;

    @Autowired
    private TbCommentMapper tbCommentMapper;

    /**
     * 查询资讯
     *
     * @param pageNum  页码
     * @param pageSize 当前页条数
     * @param infoType 咨询类型
     * @return
     */
    public ResultBean getInformation(int pageNum, int pageSize, String infoType) {
        TbInformationExample example = new TbInformationExample();
        example.createCriteria()
                // // 有效标志为1（有效）
                .andEffectFlagEqualTo("1")
                //删除标志为0（未删除）
                .andDeleteFlagEqualTo("0");
        // 资讯类型（八字，风水，奇门）
        if (StringUtil.isNotEmpty(infoType)) {
            example.getOredCriteria().get(0).andInfoTypeEqualTo(infoType);
        }
        // 按发布时间降序
        example.setOrderByClause("PUBLISHED_TIME desc");
        PageHelper.startPage(pageNum, pageSize);
        List<TbInformation> tbInformations = mapper.selectByExample(example);
        PageInfo pageInfo = new PageInfo<>(tbInformations);
        ResultBean bean = new ResultBean();
        bean.setCode(Constants.code_0);
        bean.setMsg(Constants.msg_success);
        bean.setResult(tbInformations);
        return bean;
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
    public ResultBean insertComment(String id, String userId, String commentType, String commentContent) {
        ResultBean bean = new ResultBean();
        TbComment tbComment = new TbComment();
        tbComment.setCommentUserId(userId);
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


    /**
     * 根据资讯id查找资讯(加上评论)
     *
     * @param infoId
     * @return
     */
    public ResultBean getInformationById(String infoId) {
        ResultBean bean = new ResultBean();
        TbInformationExample tbInformationExample = new TbInformationExample();
        tbInformationExample.createCriteria()
                .andInfoIdEqualTo(infoId)
                .andEffectFlagEqualTo("1")
                .andDeleteFlagEqualTo("0");
        List<TbInformation> infoList = mapper.selectByExample(tbInformationExample);
        TbInformation info = infoList.get(0);
        InfomationForm infoForm = null;
        if (infoList != null && infoList.size() > 0) {
            // 阅读次数+1
            info.setInfoViewTimes(info.getInfoViewTimes() + 1);
            infoForm = new InfomationForm();
            BeanUtils.copyProperties(info, infoForm);
            TbCommentExample tbCommentExample = new TbCommentExample();
            tbCommentExample.createCriteria()
                    .andCommentarySubjectIdEqualTo(info.getInfoId())
                    .andEffectFlagEqualTo("1")
                    .andDeleteFlagEqualTo("0");
            tbCommentExample.setOrderByClause("commentDateTime desc");
            List<TbComment> tbCommentList = tbCommentMapper.selectByExample(tbCommentExample);
            if (tbCommentList != null && tbCommentList.size() > 0) {
                infoForm.setTbCommentList(tbCommentList);
            }
            bean.setCode(Constants.code_0);
            bean.setMsg(Constants.msg_success);
            bean.setResult(infoForm);
        } else {
            bean.setCode(Constants.code_1);
            bean.setMsg(Constants.msg_failed);
        }
        return bean;
    }
}
