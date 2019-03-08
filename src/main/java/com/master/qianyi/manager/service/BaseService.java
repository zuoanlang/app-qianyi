package com.master.qianyi.manager.service;

import com.master.qianyi.pojo.TbUser;
import com.master.qianyi.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * project:itcast-microservice-user
 * function:
 * author:kangkang
 * date: 2019/2/24
 */
@Service
public class BaseService {

    @Autowired
    private UserService userService;

    public String login(HttpServletRequest request, String username, String password) {

        TbUser user = null;
        //
        user = this.getUserFromSession(request);
        //判断是否当前登录用户再登录
        if (user!=null) {
            if(user.getUserName().equals(username) && user.getUserPassword().equals(password)) {
                return "index";
            } else {
                //新用户登录
                user = userService.getUserByUsername(username);
                if(user!=null && user.getUserPassword().equals(password)){
                    return "index";
                }
            }

        } else {
            //新用户登录
            user = userService.getUserByUsername(username);
            if(user!=null && user.getUserPassword().equals(password)){
                return "index";
            } else {
                return "loginPage";
            }

        }
        return null;
    }

    //get user session
    public TbUser getUserFromSession(HttpServletRequest request){
        HttpSession session = request.getSession();
        return  (TbUser) session.getAttribute("user");
    }

    //set user session
    public void setUserSession(HttpServletRequest request, TbUser user){
        HttpSession session = request.getSession();
        session.setAttribute("user",user);
    }

    //log off user
    public String logOffUser(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.setAttribute("user",null);
        return "loginPage";
    }



}
