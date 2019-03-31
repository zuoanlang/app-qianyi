package com.master.qianyi.user.controller;

import com.github.pagehelper.PageInfo;
import com.master.qianyi.pojo.TbUser;
import com.master.qianyi.user.service.UserService;
import com.master.qianyi.utils.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Administrator on 2017/12/6.
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 对外提供接口服务，根据账号查询用户信息
     *
     * @param username
     * @return
     */
    @GetMapping(value = "user/{username}")
    public TbUser getUserByUsername(@PathVariable("username") String username) {
        return this.userService.getUserByUsername(username);
    }

    /**
     * @return
     */
    @GetMapping(value = "user/getOrdinaryUserList")
    public List<TbUser> getOrdinaryUserList(int pageNum, int pageSize) {
        List<TbUser> userList = this.userService.getOrdinaryUserList(pageNum, pageSize);
        PageInfo pageInfo = new PageInfo<>(userList);
//        System.out.println("total:" + pageInfo.getTotal());
//        System.out.println("pages:" + pageInfo.getPages());
//        System.out.println("pageSize:" + pageInfo.getPageSize());
        return userList;
    }

    /**
     * 根据条件查询名师列表
     *
     * @param pageNum
     * @param pageSize
     * @param user
     * @return
     */
    @GetMapping("user/getFamousTeachers")
    public ResultBean getFamousTeachers(int pageNum, int pageSize, TbUser user) {
        return userService.getFamousTeachers(pageNum, pageSize, user);
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
    @GetMapping("user/rechargeAndWithdraw")
    public ResultBean rechargeAndWithdraw(String userId, String tradeType, int tradeAmount, String tradeSource) {
        return userService.rechargeAndWithdraw(userId, tradeType, tradeAmount, tradeSource);
    }

    /**
     * 根据用户id查询收支明细
     *
     * @param userId 用户id
     * @return
     */
    @GetMapping("user/getReceiptionDetail")
    @ResponseBody
    public ResultBean getReceiptionDetail(String userId) {
        return userService.getReceiptionDetail(userId);
    }
}
