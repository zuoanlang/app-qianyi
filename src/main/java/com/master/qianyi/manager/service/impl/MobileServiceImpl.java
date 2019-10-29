package com.master.qianyi.manager.service.impl;

import com.github.pagehelper.util.StringUtil;
import com.master.qianyi.manager.service.MobileService;
import com.master.qianyi.mapper.TbUserMapper;
import com.master.qianyi.pojo.TbUser;
import com.master.qianyi.user.service.UserService;
import com.master.qianyi.utils.Constants;
import com.master.qianyi.utils.IDUtils;
import com.master.qianyi.utils.ResultBean;
import com.master.qianyi.utils.SmsSendUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class MobileServiceImpl implements MobileService {

    @Autowired
    private UserService userService;

    @Autowired
    private TbUserMapper tbUserMapper;

    @Override
    public ResultBean mobileSendSms(String phoneNumber) {
        //1.根据用户手机号查询DB
        TbUser user = userService.getUserByPhoneNumber(phoneNumber);
        ResultBean result = new ResultBean();
        //1.生成验证码
        int mobile_code = (int) ((Math.random() * 9 + 1) * 100000);
        //2.调用sendSms发送短信
        boolean sendResult = SmsSendUtils.sendSms(phoneNumber, mobile_code);
        //3.判断结果
        if(user == null){
            //插入数据
            if(sendResult){
                //发送成功，在user表中插入该条用户记录，等待激活（effect_flag = 0:未生效)
                //设置插入条件
                TbUser tbUser = new TbUser();
                tbUser.setEffectFlag("0");
                tbUser.setSms(mobile_code);
                tbUser.setUserId(IDUtils.genItemId());
                tbUser.setHandphone(phoneNumber);
                tbUser.setNickName("li_"+phoneNumber);
                boolean insertResult = userService.insertOneUserRecord(tbUser);
                //4.设置返回条件
                if(insertResult){
                    result.setCode(Constants.code_0);
                    result.setMsg("验证码发送成功！");
                } else {
                    result.setCode(Constants.code_1);
                    result.setMsg("数据库连接失败，请稍后重试！");
                }
            } else {
                result.setCode(Constants.code_1);
                result.setMsg("验证码发送失败，请稍后重试！");
            }
        } else {
            //更新数据
            if(sendResult){
                //发送成功，在user表中更新该条用户记录的sms，
                //设置更新条件
                TbUser tbUser = new TbUser();
                tbUser.setUserId(user.getUserId());
                tbUser.setSms(mobile_code);
                boolean updateResult = userService.updateUserBySelective(tbUser);
                //4.设置跟新条件
                if(updateResult){
                    result.setCode(Constants.code_0);
                    result.setMsg("验证码发送成功！");
                } else {
                    result.setCode(Constants.code_1);
                    result.setMsg("数据库连接失败，请稍后重试！");
                }
            } else {
                result.setCode(Constants.code_1);
                result.setMsg("验证码发送失败，请稍后重试！");
            }
        }


        return result;
    }

    @Override
    public ResultBean mobileSmsRegister(String nickName, String phoneNumber, int mobileCode, String password) {
        ResultBean bean = new ResultBean();
        //1.根据用户手机号查询DB
        TbUser user = userService.getUserByPhoneNumber(phoneNumber);
        //2.数据库存在，需要激活即effect_flag 重置 1
        if(user!=null ){
            //2.1.判断用户是否已经注册激活
            if(user.getEffectFlag().equals("1")){
                //该用户已存在.已激活
                bean.setCode(Constants.code_1);
                bean.setMsg("该手机号已经注册，请用密码登录！");
            } else {
                //该用户已存在.未激活
                //2.1 验证码验证成功
                if(mobileCode==user.getSms() && phoneNumber.equals(user.getHandphone())){
                    user.setEffectFlag("1");
                    user.setNickName(nickName);
                    user.setUserPassword(password);
                    user.setRegisterTime(new Date());
                    user.setLastLoginTime(new Date());
                    //执行注册操作,更新数据库
                    boolean result = userService.updateUserBySelective(user);
                    if(result){
                        //注册成功
                        bean.setCode(Constants.code_0);
                        bean.setMsg(Constants.msg_success);
                        //返回user对象
                        Map<String,String> userInfoMap = new HashMap<>();
                        userInfoMap.put("userId",user.getUserId());
                        userInfoMap.put("nickName",user.getNickName());
                        userInfoMap.put("phoneNumber",user.getHandphone());
                        bean.setResult(userInfoMap);
                    } else {
                        //注册失败
                        bean.setCode(Constants.code_1);
                        bean.setMsg("数据库同步失败，请稍后重试！");
                    }
                } else if(mobileCode!=user.getSms()){
                    //注册失败
                    bean.setCode(Constants.code_1);
                    bean.setMsg("请输入正确的验证码！");
                } else {
                    //注册失败
                    bean.setCode(Constants.code_1);
                    bean.setMsg("请输入正确的手机号！");
                }
            }
        } else {
            //注册失败
            bean.setCode(Constants.code_1);
            bean.setMsg("请确认手机号正确或");
        }
        return bean;
    }

    @Override
    public ResultBean mobilePasswordLogin(String phoneNumber, String password) {
        ResultBean bean = new ResultBean();
        //1.根据用户手机号查询DB
        TbUser user = userService.getUserByPhoneNumber(phoneNumber);
        if(user == null){
            bean.setCode(Constants.code_1);
            bean.setMsg("当前用户不存在，请先注册！");
        } else {
            if(user.getEffectFlag().equals("1")){
                if(user.getUserPassword().equals(password)) {
                    //1.生成token值
                    String token = IDUtils.getToken();
                    user.setToken(token);
                    user.setLastLoginTime(new Date());
                    boolean result = userService.updateUserBySelective(user);
                    if(result){
                        //2.登录成功
                        bean.setCode(Constants.code_0);
                        bean.setMsg(Constants.msg_success);
                        Map<String,String> resultMap = new HashMap<>();
                        resultMap.put("token",token);
                        resultMap.put("userId",user.getUserId());
                        bean.setResult(resultMap);
                    } else {
                        bean.setCode(Constants.code_1);
                        bean.setMsg("数据库链接失败，请稍后重试！");
                    }

                } else {
                    bean.setCode(Constants.code_1);
                    bean.setMsg("密码错误，请核对后登录！");
                }
            } else {
                bean.setCode(Constants.code_1);
                bean.setMsg("当前用户不存在，请先注册！");
            }
        }
        return bean;
    }

    @Override
    public ResultBean resetPassword(String phoneNumber, int mobileCode, String password) {
        ResultBean bean = new ResultBean();
        //查询DB,拿到验证码
        //1.根据用户手机号查询DB
        TbUser user = userService.getUserByPhoneNumber(phoneNumber);
        if(user == null){
            bean.setCode(Constants.code_1);
            bean.setMsg("当前用户不存在，请先注册！");
        } else {
            //2.比较验证码
            if(user.getSms().equals(mobileCode)){
                //2.1.success 重置密码
                //执行注册操作,更新数据库
                user.setUserPassword(password);
                boolean result = userService.updateUserBySelective(user);
                if(result){
                    bean.setCode(Constants.code_0);
                    bean.setMsg("密码重置成功！");
                }else {
                    bean.setCode(Constants.code_0);
                    bean.setMsg("密码重置失败，请稍后重试！");
                }

            } else {
                bean.setCode(Constants.code_1);
                bean.setMsg("验证码错误！");
            }
        }
        return bean;
    }

    @Override
    public ResultBean threePartyLogin(String openid, String type,String headImg,String nickName) {
        ResultBean bean = new ResultBean();
        if(!type.equals("1") && !type.equals("2")){
            bean.setMsg("三方登录的类型只能是微信或者QQ");
            bean.setCode(Constants.code_1);
            return bean;
        }
        //查询用户
        TbUser user = userService.getUserByOpenId(openid, type);
        //该用户存在，返回userId and token,重置token
        if(user != null && user.getUserId() != null){
            TbUser userNewToken = new TbUser();
            userNewToken.setUserId(user.getUserId());
            userNewToken.setToken(IDUtils.getToken());
            userNewToken.setLastLoginTime(new Date());
            boolean result = userService.updateUserBySelective(userNewToken);
            if(result) {
                bean.setCode(Constants.code_0);
                bean.setMsg(Constants.msg_success);
                Map<String,Object> resultMap = new HashMap<>();
                resultMap.put("token",userNewToken.getToken());
                resultMap.put("userId",user.getUserId());
                resultMap.put("nickName",user.getNickName());
                resultMap.put("isFirstTime",user.getRemark5().equals("0")?true:false);
                bean.setResult(resultMap);
            } else {
                bean.setCode(Constants.code_1);
                bean.setMsg("数据库连接异常，请稍后重试！");
            }
        } else {
            user = new TbUser();
            //该用户不存在，向数据库插入一条记录
            user.setUserId(IDUtils.genItemId());
            user.setToken(IDUtils.getToken());
            if (type.equals("1")){
                user.setWxOpenid(openid);
            } else {
                user.setQqOppenid(openid);
            }
            user.setEffectFlag("1");
            user.setHeadImg(headImg);
            user.setNickName(nickName);
            user.setLastLoginTime(new Date());
            user.setRegisterTime(new Date());
            user.setRemark5("0");
            boolean result = userService.insertOneUserRecord(user);
            if(result){
                bean.setCode(Constants.code_0);
                bean.setMsg(Constants.msg_success);
                Map<String,Object> resultMap = new HashMap<>();
                resultMap.put("token",user.getToken());
                resultMap.put("userId",user.getUserId());
                resultMap.put("nickName",user.getNickName());
                resultMap.put("isFirstTime",true);
                bean.setResult(resultMap);
            } else {
                bean.setCode(Constants.code_1);
                bean.setMsg("数据库连接异常，请稍后重试！");
            }

        }
        return bean;
    }

    @Override
    @Transactional
    public ResultBean bindPhoneNumber(String phoneNumber, int mobileCode, String openid, String type) {
        ResultBean bean = new ResultBean();
        if(StringUtil.isEmpty(openid) || StringUtil.isEmpty(type)){
            bean.setCode(Constants.code_1);
            bean.setMsg("openid或type不可为空");
            return bean;
        }
        //用户首次授权登录场景
        //1.根据openid查询用户
        TbUser userByOpenId = userService.getUserByOpenId(openid, type);
        if(userByOpenId == null){
            bean.setCode(Constants.code_1);
            bean.setMsg("openid或type错误");
            return bean;
        }
        //2.根据手机号查询用户
        TbUser userByPhoneNumber = userService.getUserByPhoneNumber(phoneNumber);
        if(userByPhoneNumber == null){
            bean.setCode(Constants.code_1);
            bean.setMsg("手机号错误");
            return bean;
        }
        //3.判断验证码是否正确
        if(!userByPhoneNumber.getSms().equals(mobileCode)){
            bean.setCode(Constants.code_1);
            bean.setMsg("验证码错误");
            return bean;
        }

        //当前手机号已经注册过，且生效，当前场景为三方绑定到手机号的账户
        //1.将当前的三方号的相关信息赋值到手机号的实体中
        if (userByPhoneNumber.getEffectFlag().equals("1")) {
            userByPhoneNumber.setToken(userByOpenId.getToken());
            if (type.equals("1")) {
                userByPhoneNumber.setWxOpenid(openid);
            } else {
                userByPhoneNumber.setQqOppenid(openid);
            }
            if (StringUtil.isEmpty(userByPhoneNumber.getHeadImg())) {
                userByPhoneNumber.setHeadImg(userByOpenId.getHeadImg());

            }
            if (StringUtil.isEmpty(userByPhoneNumber.getNickName())) {
                userByPhoneNumber.setNickName(userByOpenId.getNickName());
            }
            userByPhoneNumber.setLastLoginTime(userByOpenId.getLastLoginTime());
            userByPhoneNumber.setRemark5("1");
            //2.更新手机号账户
            userService.updateUserBySelective(userByPhoneNumber);
            //3.删除三方号
            if (userByOpenId.getUserId() != userByPhoneNumber.getUserId()) {
                tbUserMapper.deleteByPrimaryKey(userByOpenId.getUserId());
            }
        } else if(userByPhoneNumber.getEffectFlag().equals("0")){
            //当前手机号已经注册过，且未生效，当前场景为手机号绑定到三方的账户
            //1.手机号绑定
            userByOpenId.setHandphone(phoneNumber);
            userByOpenId.setRemark5("1");
            //2.删除手机号
            tbUserMapper.deleteByPrimaryKey(userByPhoneNumber.getUserId());
            //3.设置更新条件
            userService.updateUserBySelective(userByOpenId);
        }

        bean.setCode(Constants.code_0);
        bean.setMsg("绑定成功");

        return bean;
    }
}
