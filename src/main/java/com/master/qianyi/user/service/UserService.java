package com.master.qianyi.user.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.master.qianyi.mapper.TbCourseMapper;
import com.master.qianyi.mapper.TbOrderMapper;
import com.master.qianyi.mapper.TbUserMapper;
import com.master.qianyi.pojo.*;
import com.master.qianyi.utils.Constants;
import com.master.qianyi.utils.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/12/6.
 */
@Service
public class UserService {

    @Autowired
    private TbUserMapper tbuserMapper;

    @Autowired
    private TbOrderMapper tbOrderMapper;

    @Autowired
    private TbCourseMapper tbCourseMapper;

    /**
     * 根据用户名查询用户
     *
     * @param username
     * @return
     */
    public TbUser getUserByUsername(String username) {
        TbUserExample userExample = new TbUserExample();
        TbUserExample.Criteria criteria = userExample.createCriteria();
        criteria.andUserNameEqualTo(username);
        List<TbUser> tbUsers = tbuserMapper.selectByExample(userExample);
        return tbUsers.get(0);
    }

    /**
     * 查询所有普通用户
     *
     * @return
     */
    public List<TbUser> getOrdinaryUserList(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        TbUserExample userExample = new TbUserExample();
        TbUserExample.Criteria criteria = userExample.createCriteria();
        criteria.andDeleteFlagEqualTo("0").andEffectFlagEqualTo("1");
        List<TbUser> userList = tbuserMapper.selectByExample(userExample);
        PageInfo pageInfo = new PageInfo<>(userList);
        return userList;
    }

    /**
     * 根据条件查询名师列表(遗留：是否在线如何判断？)
     *
     * @param pageNum
     * @param pageSize
     * @param user
     * @return
     */
    public ResultBean getFamousTeachers(int pageNum, int pageSize, TbUser user) {
        TbUserExample example = new TbUserExample();
        // 有效标志为1（有效），删除标志为0（未删除）,是名师
        example.createCriteria().andEffectFlagEqualTo("1").andDeleteFlagEqualTo("0").andIsMasterEqualTo("1");
        // 名师等级
        if (StringUtil.isNotEmpty(user.getMasterRank())) {
            example.getOredCriteria().get(0).andMasterRankEqualTo(user.getMasterRank());
        }
        // 按名师专业(多个专业，搜索时只按一个专业搜索，只要包含在该名师专业中即可)
        if (StringUtil.isNotEmpty(user.getMajor())) {
            example.getOredCriteria().get(0).andMajorLike(user.getMajor());
        }
        PageHelper.startPage(pageNum, pageSize);
        List<TbUser> tbUsers = tbuserMapper.selectByExample(example);
        PageInfo pageInfo = new PageInfo<>(tbUsers);
        ResultBean bean = new ResultBean();
        bean.setCode(Constants.code_0);
        bean.setMsg(Constants.msg_success);
        bean.setResult(tbUsers);
        return bean;
    }


    /**
     * 根据手机号查询用户
     *
     * @return
     */
    public TbUser getUserByPhoneNumber(String phoneNumber) {
        TbUserExample userExample = new TbUserExample();
        TbUserExample.Criteria criteria = userExample.createCriteria();
        criteria.andHandphoneEqualTo(phoneNumber);
        List<TbUser> usersList = tbuserMapper.selectByExample(userExample);
        if (usersList != null && usersList.size() == 1) {
            return usersList.get(0);
        }
        return null;
    }

    /**
     * 用户表插入一条数据：根据已有值的字段
     *
     * @param user
     * @return
     */
    public boolean insertOneUserRecord(TbUser user) {
        int count = tbuserMapper.insertSelective(user);
        if (count > 0) {
            return true;
        }
        return false;
    }

    /**
     * 根据主键userId更新用户信息，（不为空的字段）
     *
     * @return
     */
    public boolean updateUserBySelective(TbUser user) {
        int count = tbuserMapper.updateByPrimaryKeySelective(user);
        if (count > 0) {
            return true;
        }
        return false;
    }


}
