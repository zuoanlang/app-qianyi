package com.master.qianyi.manager.controller;

import com.master.qianyi.manager.service.BaseService;
import com.master.qianyi.pojo.TbUser;
import com.master.qianyi.user.service.UserService;
import com.master.qianyi.utils.EasyUIDataGridResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

/**
 * project:后台登录及相关操作
 * function:
 * author:kangkang
 * date: 2019/2/22
 */
@Controller
@RequestMapping("/manager")
public class ManagerController {

    @Autowired
    private BaseService baseService;

    @Autowired
    private UserService userService;

    /**
     * 访问登录界面
     *
     * @return
     */
    @RequestMapping("/loginPage")
    public String loginPage(Model model, String index) {
        if (index != null && index.equals("1")) {
            model.addAttribute("result", "您的登陆信息已失效，请重新登陆！");
        } else if (index != null && index.equals("2")) {
            model.addAttribute("result", "用户名或密码错误，请重新登陆！");
        } else {
            model.addAttribute("result", "");
        }

        return "loginPage";
    }

    @RequestMapping("/sessionTimeOut")
    public void sessionTimeOut(HttpServletResponse response, HttpServletRequest request) throws IOException {
        response.sendRedirect(request.getContextPath() + "/loginPage.html");
    }

    /**
     * 访问主页
     *
     * @return
     */
    @RequestMapping("/index")
    public String index(HttpServletRequest request,Model model) {
        HttpSession session = request.getSession();
        //这里的User是登陆时放入session的
        TbUser user = (TbUser) session.getAttribute("user");
        model.addAttribute("username", user.getUserName());
        return "index";
    }

    //登录操作
    @PostMapping("/login")
    public void userLogin(HttpServletRequest request, HttpServletResponse response, String username, String password) throws IOException {
        boolean result = baseService.login(request, username, password);
        if (result) {
            response.sendRedirect("index");
        } else {
            //重定向到登录界面
            response.getWriter().write("登录信息已失效，请重新登录！");
            response.sendRedirect("/qianyi/loginPage.html");
        }
    }

    //请求核对名师信息
    @GetMapping("/checkOutMaster")
    @ResponseBody
    public Map<String, String> checkOutMaster(String idNum, String userName) {
        return userService.checkOutMaster(idNum, userName);
    }

    //获取普通用户列表
    @GetMapping("/getOrdinaryUserList")
    @ResponseBody
    public EasyUIDataGridResult getOrdinaryUserList(int page, int rows) {
        EasyUIDataGridResult dataGridResult = userService.getOrdinaryUserList(page, rows, "ordinary");
        return dataGridResult;
    }

    //获取管理员列表
    @GetMapping("/getAdminUserList")
    @ResponseBody
    public EasyUIDataGridResult getAdminUserList(int page, int rows) {
        EasyUIDataGridResult dataGridResult = userService.getOrdinaryUserList(page, rows, "admin");
        return dataGridResult;
    }

    //获取管理员列表
    @GetMapping("/getTeacherUserList")
    @ResponseBody
    public EasyUIDataGridResult getTeacherUserList(int page, int rows) {
        EasyUIDataGridResult dataGridResult = userService.getOrdinaryUserList(page, rows, "master");
        return dataGridResult;
    }

    //获取管理员列表
    @PostMapping("/deleteUserById")
    @ResponseBody
    public Map<String, String> deleteUserById(String userId) {
        return userService.deleteUserById(userId);
    }

    //获取管理员列表
    @GetMapping("/getMasterServerList")
    @ResponseBody
    public EasyUIDataGridResult getMasterServerList(String userId) {
        return userService.getMasterServerList(userId);
    }

    //保存名师service类目
    @PostMapping("/saveServiceByMasterId")
    @ResponseBody
    public Map<String, String> saveServiceByMasterId(String userId, String serviceId, String serviceName, int servicePrice) {
        return userService.saveServiceByMasterId(userId, serviceId, serviceName, servicePrice);
    }

    //删除名师service类目
    @PostMapping("/deleteServiceByServiceId")
    @ResponseBody
    public Map<String, String> deleteServiceByServiceId(String serviceId) {
        return userService.deleteServiceByServiceId(serviceId);
    }

    //保存名师身份证
    @PostMapping("/saveMasterIdNo")
    @ResponseBody
    public Map<String, String> saveMasterIdNo(String userId, String idCardNo,String masterRank,String isOfficial) {
        return userService.saveMasterIdNo(userId, idCardNo,masterRank,isOfficial);
    }

    //入驻审批
    @PostMapping("/approval")
    @ResponseBody
    public Map<String, String> approval(String userId, String flag, String approvalOpinion) {
        return userService.approval(userId, flag, approvalOpinion);
    }

    //退款审批
    @PostMapping("/orderApprove")
    @ResponseBody
    public Map<String, String> orderApprove(String orderStatus, String orderId) {
        return userService.orderApprove(orderStatus, orderId);
    }

    //退款审批
    @PostMapping("/getApprovalCount")
    @ResponseBody
    public Map<String, Integer> getApprovalCount() {
        return userService.getApprovalCount();
    }

    //反馈消息已读
    @PostMapping("/read")
    @ResponseBody
    public Map<String, Integer> read(String ids) {
        return userService.read(ids);
    }

    //反馈消息已读
    @PostMapping("/deleteFeedBack")
    @ResponseBody
    public Map<String, Integer> deleteFeedBack(String ids) {
        return userService.deleteFeedBack(ids);
    }

    //添加管理员
    @PostMapping("/addAdmin")
    @ResponseBody
    public Map<String, String> addAdmin(HttpServletRequest request,String userName,String password) {
        return userService.addAdmin(request,userName,password);
    }

    //修改管理员
    @PostMapping("/editAdmin")
    @ResponseBody
    public Map<String, String> editAdmin(HttpServletRequest request,String userName,String password_n,String password) {
        return userService.editAdmin(request,userName,password,password_n);
    }

    //删除管理员
    @PostMapping("/deleteAdmin")
    @ResponseBody
    public Map<String, String> deleteAdmin(HttpServletRequest request,String userId) {
        return userService.deleteAdmin(request,userId);
    }

    //删除管理员
    @PostMapping("/deleteMaster")
    @ResponseBody
    public Map<String, String> deleteMaster(String userId) {
        return userService.deleteMaster(userId);
    }



}
