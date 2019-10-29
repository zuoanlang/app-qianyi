package com.master.qianyi.manager.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.master.qianyi.manager.service.InfoManService;
import com.master.qianyi.mapper.TbCategoryMapper;
import com.master.qianyi.mapper.TbInformationMapper;
import com.master.qianyi.pojo.TbCategory;
import com.master.qianyi.pojo.TbInformationExample;
import com.master.qianyi.pojo.TbInformationWithBLOBs;
import com.master.qianyi.utils.Constants;
import com.master.qianyi.utils.EasyUIDataGridResult;
import com.master.qianyi.utils.IDUtils;
import com.master.qianyi.utils.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class InfoManServiceImpl implements InfoManService {

    @Autowired
    private TbInformationMapper tbInformationMapper;

    @Autowired
    private TbCategoryMapper tbCategoryMapper;

    @Override
    public ResultBean addNews(Long infoType, String infoTitle, String infoImgPath, String infoWriter, String infoContent) {
        ResultBean bean = new ResultBean();
        String infoTypeName = getInfoTypeName(infoType);
        if (infoTypeName == null) {
            bean.setCode(Constants.code_1);
            bean.setMsg("当前资讯分类不可用，请刷新重新选择");
            return bean;
        }
        TbInformationWithBLOBs informationWithBLOBs = new TbInformationWithBLOBs();
        informationWithBLOBs.setInfoId(IDUtils.genItemId());
        informationWithBLOBs.setInfoType(infoTypeName);
        informationWithBLOBs.setRemark2("草稿");
        informationWithBLOBs.setInfoTitle(infoTitle);
        informationWithBLOBs.setInfoImgPath(infoImgPath);
        informationWithBLOBs.setRemark1(infoType.toString());
        informationWithBLOBs.setInfoWriter(infoWriter);
        informationWithBLOBs.setPublishedTime(new Date());
        informationWithBLOBs.setInfoContent(infoContent);

        int insertSelective = tbInformationMapper.insertSelective(informationWithBLOBs);
        if (insertSelective > 0) {
            bean.setCode(Constants.code_0);
            return bean;
        }
        bean.setCode(Constants.code_1);
        bean.setMsg("数据库连接失败，请稍后重试");
        return bean;
    }

    @Override
    public ResultBean editNews(String infoId, Long infoType, String infoTitle, String infoImgPath, String infoWriter,
                               String effectFlag, String infoContent) {
        ResultBean bean = new ResultBean();
        String infoTypeName = getInfoTypeName(infoType);
        if (infoTypeName == null) {
            bean.setCode(Constants.code_1);
            bean.setMsg("当前资讯分类不可用，请刷新重新选择");
            return bean;
        }
        TbInformationWithBLOBs informationWithBLOBs = new TbInformationWithBLOBs();
        informationWithBLOBs.setInfoId(infoId);
        informationWithBLOBs.setInfoType(infoTypeName);
        informationWithBLOBs.setInfoTitle(infoTitle);
        informationWithBLOBs.setInfoImgPath(infoImgPath);
        informationWithBLOBs.setInfoWriter(infoWriter);
        informationWithBLOBs.setPublishedTime(new Date());
        informationWithBLOBs.setEffectFlag(effectFlag);
        if (effectFlag.equals("1")) {
            informationWithBLOBs.setRemark2("发布");
        } else {
            informationWithBLOBs.setRemark2("草稿");
        }
        informationWithBLOBs.setInfoContent(infoContent);
        informationWithBLOBs.setRemark1(infoType.toString());
        int selective = tbInformationMapper.updateByPrimaryKeySelective(informationWithBLOBs);

        if (selective > 0) {
            bean.setCode(Constants.code_0);
            return bean;
        }
        bean.setCode(Constants.code_1);
        bean.setMsg("数据库连接失败，请稍后重试");
        return bean;
    }

    @Override
    public ResultBean deleteNews(String infoIds) {
        ResultBean bean = new ResultBean();
        if (StringUtil.isEmpty(infoIds)) {
            bean.setCode(Constants.code_1);
            bean.setMsg("资讯Id不可为空");
            return bean;
        }
        String[] split = infoIds.split(",");
        List<String> infoIdsList = new ArrayList<>();
        for (int i = 0; i < split.length; i++) {
            infoIdsList.add(split[i]);
        }
        TbInformationExample example = new TbInformationExample();
        TbInformationExample.Criteria criteria = example.createCriteria();
        criteria.andInfoIdIn(infoIdsList);
        int deleteCount = tbInformationMapper.deleteByExample(example);
        if (deleteCount <= 0) {
            bean.setCode(Constants.code_1);
            bean.setMsg("删除失败");
            return bean;
        }
        bean.setCode(Constants.code_0);
        bean.setMsg("删除成功");
        return bean;
    }

    @Override
    public EasyUIDataGridResult getNewsInfoList(int pageNum, int pageSize,String infoId,String infoTitle,String infoWriter) {
        EasyUIDataGridResult result = new EasyUIDataGridResult();
        TbInformationExample example = new TbInformationExample();
        TbInformationExample.Criteria criteria = example.createCriteria();
        if(StringUtil.isNotEmpty(infoId)){
            criteria.andInfoIdLike("%"+infoId+"%");
        }
        if(StringUtil.isNotEmpty(infoTitle)){
            criteria.andInfoTitleLike("%"+infoTitle+"%");
        }
        if(StringUtil.isNotEmpty(infoWriter)){
            criteria.andInfoWriterLike("%"+infoWriter+"%");
        }
        criteria.andDeleteFlagEqualTo("0");
        example.setOrderByClause("published_time desc");
        PageHelper.startPage(pageNum, pageSize);
        List<TbInformationWithBLOBs> infoList = tbInformationMapper.selectByExampleWithBLOBs(example);
        result.setRows(infoList);
        PageInfo<TbInformationWithBLOBs> pageInfo = new PageInfo<>(infoList);
        result.setTotal(pageInfo.getTotal());
        return result;
    }

    @Override
    public ResultBean publish(String infoIds, int type) {
        ResultBean bean = new ResultBean();
        if (type != 1 && type != 0) {
            bean.setCode(Constants.code_1);
            bean.setMsg("资讯发布type错误");
        }
        String[] split = infoIds.split(",");
        List<String> infoIdsList = new ArrayList<>();
        for (int i = 0; i < split.length; i++) {
            infoIdsList.add(split[i]);
        }

        TbInformationExample example = new TbInformationExample();
        TbInformationWithBLOBs tbInformation = new TbInformationWithBLOBs();
        TbInformationExample.Criteria criteria = example.createCriteria();
        criteria.andInfoIdIn(infoIdsList);
        //发布
        if(type == 1){
            tbInformation.setEffectFlag("1");
            tbInformation.setRemark2("发布");
        } else {
            tbInformation.setEffectFlag("0");
            tbInformation.setRemark2("草稿");
        }
        int updateCount = tbInformationMapper.updateByExampleSelective(tbInformation, example);
        if(updateCount<=0){
            bean.setCode(Constants.code_1);
            return bean;
        }
        bean.setCode(Constants.code_0);
        return bean;
    }

    public String getInfoTypeName(Long id) {
        TbCategory tbCategory = tbCategoryMapper.selectByPrimaryKey(id);
        if (tbCategory != null) {
            return tbCategory.getName();
        }
        return null;
    }
}
