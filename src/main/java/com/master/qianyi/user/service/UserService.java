package com.master.qianyi.user.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.master.qianyi.mapper.TbCourseMapper;
import com.master.qianyi.mapper.TbOrderMapper;
import com.master.qianyi.mapper.TbReceiptionMapper;
import com.master.qianyi.mapper.TbUserMapper;
import com.master.qianyi.pojo.TbReceiption;
import com.master.qianyi.pojo.TbReceiptionExample;
import com.master.qianyi.pojo.TbUser;
import com.master.qianyi.pojo.TbUserExample;
import com.master.qianyi.utils.Constants;
import com.master.qianyi.utils.IDUtils;
import com.master.qianyi.utils.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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

    @Autowired
    private TbReceiptionMapper tbReceiptionMapper;

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


    /**
     * 根据openid查询用户
     *
     * @param openid
     * @param type
     * @return
     */
    public TbUser getUserByOpenId(String openid, String type) {
        TbUserExample userExample = new TbUserExample();
        TbUserExample.Criteria criteria = userExample.createCriteria();
        if (type.equals("1")) {
            criteria.andWxOpenidEqualTo(openid);
        } else {
            criteria.andQqOppenidEqualTo(openid);
        }
        List<TbUser> tbUsers = tbuserMapper.selectByExample(userExample);
        if (tbUsers != null && tbUsers.size() == 1) {
            return tbUsers.get(0);
        }
        return new TbUser();
    }

    /**
     * 用户充值和提现
     *
     * @param userId      用户id
     * @param tradeType   交易类型：1充值，2提现
     * @param tradeAmount 交易金额
     * @param tradeSource 交易来源（去向）：1.支付宝，2微信
     * @return
     */
    public ResultBean rechargeAndWithdraw(String userId, String tradeType, int tradeAmount, String tradeSource) {
        ResultBean bean = new ResultBean();
        // 用户id为空，直接返回错误信息
        if (StringUtil.isEmpty(userId)) {
            bean.setCode(Constants.code_1);
            bean.setMsg(Constants.msg_failed);
            bean.setResult("UserId is null!");
            return bean;
        }
        // 若交易金额小于等于0，直接返回错误信息
        if (tradeAmount <= 0) {
            bean.setCode(Constants.code_1);
            bean.setMsg(Constants.msg_failed);
            bean.setResult("Trade Amount Less than or equal to 0!");
            return bean;
        }
        TbReceiption tbReceiption = new TbReceiption();
        tbReceiption.setReceiptId(IDUtils.genItemId());
        tbReceiption.setUserId(userId);
        tbReceiption.setTradeType(tradeType);
        tbReceiption.setTradeAmount(tradeAmount);
        tbReceiption.setTradeDateTime(new Date(System.currentTimeMillis()));
        tbReceiption.setTradeSource(tradeSource);
        tbReceiption.setEffectFlag("1");
        tbReceiption.setDeleteFlag("0");
        int insertFlag = tbReceiptionMapper.insertSelective(tbReceiption);
        if (insertFlag > 0) {
            // 更新用户表
            int updateUser = updateUserAccountBalance(userId, tradeType, tradeAmount);
            if (updateUser < 0) {
                bean.setCode(Constants.code_1);
                bean.setMsg(Constants.msg_failed);
            }
        } else {
            bean.setCode(Constants.code_1);
            bean.setMsg(Constants.msg_failed);
        }
        bean.setCode(Constants.code_0);
        bean.setMsg(Constants.msg_success);
        return bean;
    }

    /**
     * 更新用户表账户余额
     *
     * @param userId      用户id
     * @param tradeType   交易类型：1充值，2提现
     * @param tradeAmount 交易金额
     * @return
     */
    private int updateUserAccountBalance(String userId, String tradeType, int tradeAmount) {
        int updateFlag = -1;
        TbUserExample example = new TbUserExample();
        example.createCriteria()
                .andDeleteFlagEqualTo("0")
                .andEffectFlagEqualTo("1")
                .andUserIdEqualTo(userId);
        List<TbUser> users = tbuserMapper.selectByExample(example);
        if (users != null && users.size() > 0) {
            TbUser user = users.get(0);
            // 1.充值
            if ("1".equals(tradeType)) {
                user.setUserAccountBalance(user.getUserAccountBalance() + tradeAmount);
            }
            // 2.提现
            else if ("2".equals(tradeType)) {
                if (tradeAmount > user.getUserAccountBalance()) {
                    return updateFlag;
                }
                user.setUserAccountBalance(user.getUserAccountBalance() - tradeAmount);
            }
            updateFlag = tbuserMapper.updateByExampleSelective(user, example);
        } else {
            return updateFlag;
        }
        return updateFlag;
    }


    /**
     * 根据用户id查询收支明细
     *
     * @param userId 用户id
     * @return
     */
    public ResultBean getReceiptionDetail(String userId) {
        ResultBean bean = new ResultBean();
        if (StringUtil.isEmpty(userId)) {
            bean.setCode(Constants.code_1);
            bean.setMsg(Constants.msg_failed);
            return bean;
        }
        TbReceiptionExample example = new TbReceiptionExample();
        example.createCriteria()
                .andDeleteFlagEqualTo("0")
                .andEffectFlagEqualTo("1")
                .andUserIdEqualTo(userId);
        List<TbReceiption> receiptions = tbReceiptionMapper.selectByExample(example);
        bean.setCode(Constants.code_0);
        bean.setMsg(Constants.msg_success);
        bean.setResult(receiptions);
        return bean;
    }

    /**
     * 更新用户信息（个人资料编辑和入驻）
     *
     * @param user
     * @return
     */
    public ResultBean updateUserInfo(TbUser user) {
        ResultBean bean = new ResultBean();
        if (StringUtil.isEmpty(user.getUserId())) {
            bean.setCode(Constants.code_1);
            bean.setMsg(Constants.msg_failed);
            return bean;
        }
        TbUserExample tbUserExample = new TbUserExample();
        tbUserExample.createCriteria()
                .andUserIdEqualTo(user.getUserId())
                .andEffectFlagEqualTo("1")
                .andDeleteFlagEqualTo("0");
        List<TbUser> users = tbuserMapper.selectByExample(tbUserExample);
        if (users != null) {
            TbUser tbUser = users.get(0);
            // 真实姓名
            if (StringUtil.isNotEmpty(user.getUserName())) {
                tbUser.setUserName(user.getUserName());
            }
            // 联系电话
            if (StringUtil.isNotEmpty(user.getTelephone())) {
                tbUser.setTelephone(user.getTelephone());
            }
            // 职业
            if (StringUtil.isNotEmpty(user.getProfession())) {
                tbUser.setProfession(user.getProfession());
            }
            // 擅长分类
            if (StringUtil.isNotEmpty(user.getMajor())) {
                tbUser.setMajor(user.getMajor());
            }
            // 证件照片（正反面）
            if (StringUtil.isNotEmpty(user.getIdCardImg1()) && StringUtil.isNotEmpty(user.getIdCardImg2())) {
                tbUser.setIdCardImg1(user.getIdCardImg1());
                tbUser.setIdCardImg2(user.getIdCardImg2());
            }
            // 名师简介（个人介绍）
            if (StringUtil.isNotEmpty(user.getMasterIntroduction())) {
                tbUser.setMasterIntroduction(user.getMasterIntroduction());
            }
            // 头像
            if (StringUtil.isNotEmpty(user.getHeadImg())) {
                tbUser.setHeadImg(user.getHeadImg());
            }
            // 昵称
            if (StringUtil.isNotEmpty(user.getNickName())) {
                tbUser.setNickName(user.getNickName());
            }
            // 个性签名
            if (StringUtil.isNotEmpty(user.getAsign())) {
                tbUser.setAsign(user.getAsign());
            }
            int update = tbuserMapper.updateByExampleSelective(tbUser, tbUserExample);
            if (update > 0) {
                bean.setCode(Constants.code_0);
                bean.setMsg(Constants.msg_success);
            } else {
                bean.setCode(Constants.code_1);
                bean.setMsg(Constants.msg_failed);
            }
        } else {
            bean.setCode(Constants.code_1);
            bean.setMsg(Constants.msg_failed);
        }
        return bean;
    }
}
