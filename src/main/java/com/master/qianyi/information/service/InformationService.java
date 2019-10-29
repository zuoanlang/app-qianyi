package com.master.qianyi.information.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.util.StringUtil;
import com.master.qianyi.comment.form.InfoComment;
import com.master.qianyi.comment.service.CommentService;
import com.master.qianyi.information.form.BriefInformation;
import com.master.qianyi.information.form.DetailsInformation;
import com.master.qianyi.mapper.TbCommentMapper;
import com.master.qianyi.mapper.TbInformationMapper;
import com.master.qianyi.pojo.TbInformation;
import com.master.qianyi.pojo.TbInformationExample;
import com.master.qianyi.pojo.TbInformationWithBLOBs;
import com.master.qianyi.utils.Constants;
import com.master.qianyi.utils.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 资讯service
 */
@Service
public class InformationService {

    @Autowired
    private TbInformationMapper mapper;

    @Autowired
    private TbCommentMapper tbCommentMapper;

    @Autowired
    private CommentService commentService;

    /**
     * 查询资讯
     *
     * @param pageNum  页码
     * @param pageSize 当前页条数
     * @param infoType 咨询类型
     * @return
     */
    public ResultBean getInformation(int pageNum, int pageSize, String infoType) {
        ResultBean bean = new ResultBean();
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
        List<TbInformation> informations = mapper.selectByExample(example);
        List<BriefInformation> informationList = new ArrayList<>();
        if(informations!=null && informations.size()>0){
            BriefInformation information;
            for (TbInformation info: informations){
                information = new BriefInformation();
                information.setInfoId(info.getInfoId());
                information.setInfoImgPath(info.getInfoImgPath());
                information.setInfoTitle(info.getInfoTitle());
                information.setInfoWriter(info.getInfoWriter());
                information.setPublishedTime(String.valueOf(info.getPublishedTime().getTime()));
                information.setInfoViewTimes(info.getInfoViewTimes());
                informationList.add(information);
            }
        }
        bean.setCode(Constants.code_0);
        bean.setMsg(Constants.msg_success);
        bean.setResult(informationList);
        return bean;
    }

    /**
     * 后台渲染根据资讯id查找资讯详情
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
        List<TbInformationWithBLOBs> infoList = mapper.selectByExampleWithBLOBs(tbInformationExample);
        if (infoList != null && infoList.size() > 0) {
            TbInformationWithBLOBs info = infoList.get(0);
            DetailsInformation details = new DetailsInformation();

            details.setInfoImgPath(info.getInfoImgPath());
            details.setInfoTitle(info.getInfoTitle());
            details.setInfoWriter(info.getInfoWriter());
            details.setInfoContent(info.getInfoContent());
            details.setPublishedTime(new SimpleDateFormat("yyyy-MM-dd").format(info.getPublishedTime()));

            Map<String,Object> objectMap = new HashMap<>();
            ResultBean infoDetailsBean = getInfoDetails(infoId);

            objectMap.put("infoComments",infoDetailsBean.getResult());
            objectMap.put("infoDetails",details);
            bean.setCode(Constants.code_0);
            bean.setMsg(Constants.msg_success);
            bean.setResult(objectMap);
        } else {
            bean.setCode(Constants.code_1);
            bean.setMsg(Constants.msg_failed);
        }
        return bean;
    }

    /**
     * app获取资讯详情
     * @param infoId
     * @return
     */
    public ResultBean getInfoDetails(String infoId) {
//        Map<String,Object> detailsMap = new HashMap<>();
        //1.url
        TbInformation tbInformation = mapper.selectByPrimaryKey(infoId);
        //2.comment
        List<InfoComment> infoComments = commentService.getInfoComment(1, 10, infoId);
//        detailsMap.put("infoId",infoId);
//        detailsMap.put("detailsUrl",tbInformation.getInfoUrl());
//        detailsMap.put("commentList",infoComments);

        ResultBean bean = new ResultBean();
        bean.setResult(infoComments);
        bean.setCode(Constants.code_0);
        bean.setMsg(Constants.msg_success);

        return bean;

    }

    public ResultBean addInfoViewTimes(String infoId) {
        ResultBean bean = new ResultBean();
        TbInformation tbInformation = mapper.selectByPrimaryKey(infoId);
        if(tbInformation != null){
            tbInformation.setInfoViewTimes(tbInformation.getInfoViewTimes()+1);
            int update = mapper.updateByPrimaryKey(tbInformation);
            if(update>0){
                Map<String,Long> timesMap = new HashMap<>();
                timesMap.put("viewTimes",tbInformation.getInfoViewTimes()+1);
                bean.setCode(Constants.code_0);
                bean.setMsg(Constants.msg_success);
                bean.setResult(timesMap);
            }
        }
        return bean;
    }
}
