package com.master.qianyi.user.controller;

import com.master.qianyi.pojo.TbUser;
import com.master.qianyi.user.service.UserService;
import com.master.qianyi.utils.EasyUIDataGridResult;
import com.master.qianyi.utils.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public EasyUIDataGridResult getOrdinaryUserList(int pageNum, int pageSize) {
        EasyUIDataGridResult ordinaryUserList = this.userService.getOrdinaryUserList(pageNum, pageSize,"index");
        return ordinaryUserList;
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
    public ResultBean getFamousTeachers(@RequestParam(value="pageNum", defaultValue="1") int pageNum,
                                        @RequestParam(value="pageSize", defaultValue="15")int pageSize, TbUser user) {
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
    @PostMapping("userInfo/rechargeAndWithdraw")
    public ResultBean rechargeAndWithdraw(@RequestParam(value="userId")String userId,
                                          @RequestParam(value="tradeType")String tradeType,
                                          @RequestParam(value="tradeAmount")int tradeAmount) {
        return userService.rechargeAndWithdraw(userId,tradeType, tradeAmount);
    }

    /**
     * 根据用户id查询收支明细
     *
     * @param userId 用户id
     * @return
     */
    @GetMapping("userInfo/getTradeDetail")
    @ResponseBody
    public ResultBean getTradeDetail(@RequestParam(value="userId")String userId,
                                     @RequestParam(value="pageNum", defaultValue="1") int pageNum,
                                     @RequestParam(value="pageSize", defaultValue="15")int pageSize) {
        return userService.getTradeDetail(userId,pageNum,pageSize);
    }

    /**
     * 更新用户信息（个人资料编辑和入驻）
     *
     * @param user
     * @return
     */
    @PostMapping("userInfo/teacherSettledIn")
    @ResponseBody
    public ResultBean teacherSettledIn(@RequestParam(value = "userId")String userId,
                                       @RequestParam(value = "token")String token,
                                       @RequestParam(value = "userName")String userName,
                                       @RequestParam(value = "major")String major,
                                       @RequestParam(value = "idCardNo")String idCardNo,
                                       @RequestParam(value = "profession")String profession,
                                       @RequestParam(value = "idCardImg1")String idCardImg1,
                                       @RequestParam(value = "idCardImg2")String idCardImg2,
                                       @RequestParam(value = "masterIntroduction")String masterIntroduction) {
        return userService.updateUserInfo(userId,token,userName,major,idCardNo,profession,idCardImg1,idCardImg2,masterIntroduction);
    }

    /**
     * 更新用户信息（个人资料编辑）
     *  7个字段
     *  昵称、头像、签名、改绑手机号
     * @param userId
     * @param token
     * @param nickName
     * @param headImg
     * @param asign
     * @param handphone
     * @param mobileCode
     * @return
     */
    @PostMapping("userInfo/updateMyInfo")
    @ResponseBody
    public ResultBean updateMyInfo(@RequestParam(value = "userId")String userId,
                                   @RequestParam(value = "token")String token,
                                   String nickName,
                                   String headImg,
                                   String asign,
                                   String handphone,
                                   String mobileCode) {
        return userService.updateMyInfo(userId,token,nickName,headImg,asign,handphone,mobileCode);
    }

    /**
     * 根据userId 查询用户信息(我的-个人)
     *  个人的, 头像、昵称、签名、手机号、账户余额
     * @param user
     * @return
     */
    @GetMapping("userInfo/getMyInfoByUserId")
    @ResponseBody
    public ResultBean getMyInfoByUserId(String userId) {
        return userService.getMyInfoByUserId(userId);
    }

    /**
     * 根据userId 查询用户信息(我的-个人)
     *  根据userid，获取头像、昵称
     * @param user
     * @return
     */
    @GetMapping("userInfo/getHeadAndNameByUserId")
    @ResponseBody
    public ResultBean getHeadAndNameByUserId(String userId) {
        return userService.getHeadAndNameByUserId(userId);
    }

    /**
     * 根据userId查询名师详情
     *
     * @param userId
     * @return
     */
    @GetMapping("user/getTeacherByUserId")
    public ResultBean getTeacherByUserId(String userId) {
        return userService.getTeacherByUserId(userId);
    }

    /**
     * 取名师测算类型
     */
    @GetMapping("/master/getServiceTypeList")
    public ResultBean getServiceType(){
        return userService.getServiceType();
    }

    /**
     * 根据名师的userId取名师的服务列表
     * @return
     */
    @GetMapping("/master/getServiceItemByUserId")
    public ResultBean getServiceItemByUserId(@RequestParam(value = "userId",required = true) String userId){
        return userService.getServiceItemByUserId(userId);
    }

    /**
     * 更新具体目录的个人学习进度
     * @param userId
     * @param catalogId
     * @param learningPercent
     * @return
     */
    @PostMapping("/userInfo/recordVideoPercent")
    public ResultBean recordVideoPercent(@RequestParam(value = "userId")String userId,
                                         @RequestParam(value = "catalogId")String catalogId,
                                         @RequestParam(value = "token")String token,
                                         @RequestParam(value = "learningPercent")float learningPercent,
                                         @RequestParam(value = "currentSecond")float currentSecond){
        return userService.recordVideoPercent(userId,token,catalogId,learningPercent,currentSecond);
    }

    @GetMapping("/approve/getMasterSettledList")
    public EasyUIDataGridResult getMasterSettledList(int page , int rows){
        return userService.getMasterSettledList(page , rows);
    }

    @GetMapping("/userInfo/getMasterApplyInfo")
    public ResultBean getMasterApplyInfo(String userId){
        return userService.getMasterApplyInfo(userId);
    }
}
