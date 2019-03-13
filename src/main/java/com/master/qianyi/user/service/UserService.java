package com.master.qianyi.user.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.master.qianyi.mapper.TbCourseMapper;
import com.master.qianyi.mapper.TbOrderMapper;
import com.master.qianyi.mapper.TbUserMapper;
import com.master.qianyi.pojo.*;
import com.master.qianyi.user.form.OrderForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public List<TbUser> getFamousTeachers(int pageNum, int pageSize, TbUser user) {
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
        return tbUsers;
    }

    /**
     * @param orderForm
     * @return
     */
    public List<OrderForm> getOrderByUserId(OrderForm orderForm) {
        // 1. 查询订单表
        TbOrderExample tbOrderExample = new TbOrderExample();
        tbOrderExample.createCriteria()
                .andEffectFlagEqualTo("1")
                .andDeleteFlagEqualTo("1")
                .andUserIdEqualTo(orderForm.getUserId());
        // 订单状态（进行中、已完成等）
        if (StringUtil.isNotEmpty(orderForm.getOrderStatus())) {
            tbOrderExample.getOredCriteria().get(0).andOrderStatusEqualTo(orderForm.getOrderStatus());
        }
        List<TbOrder> tbOrderList = tbOrderMapper.selectByExample(tbOrderExample);

        // 2. 查询课程表
        List<String> courseIdList = new ArrayList<>();
        if (tbOrderList != null && tbOrderList.size() > 0) {
            for (TbOrder order : tbOrderList) {
                courseIdList.add(order.getGoodId());
            }
        }
        return null;
    }

}
