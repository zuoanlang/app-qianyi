package com.master.qianyi.information.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.master.qianyi.mapper.TbInformationMapper;
import com.master.qianyi.pojo.TbInformation;
import com.master.qianyi.pojo.TbInformationExample;
import com.master.qianyi.utils.Constants;
import com.master.qianyi.utils.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 资讯service
 */
@Service
public class InformationService {

    @Autowired
    private TbInformationMapper mapper;

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
}
