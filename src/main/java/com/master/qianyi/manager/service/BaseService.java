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

    public boolean login(HttpServletRequest request, String username, String password) {
        //查询数据库
        TbUser user = userService.getUserByUsername(username);
        if(user.getUserPassword().equals(password)){
            setUserSession(request,user);
            return true;
        } else {
            return false;
        }
    }

    //get user session
    public TbUser getUserFromSession(HttpServletRequest request){
        HttpSession session = request.getSession();
        if(session!=null && session.getAttribute("user")!=null){
            return (TbUser)session.getAttribute("user");
        } else {
            return null;
        }
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
