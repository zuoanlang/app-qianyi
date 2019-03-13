package com.master.qianyi.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = "/qianyi/manager/*",filterName = "loginFilter")
public class LoginFilter implements Filter {

    //不需要登录就可以访问的路径(比如:注册登录等)
    String[] includeUrls = new String[]{"/manager/login","/manager/register"};

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();

        String uri = request.getRequestURI();
        //是否需要过滤
        boolean needFilter = isNeedFilter(uri);
        if(!needFilter) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            //用户在线
            if(session!=null && session.getAttribute("user") != null){
                filterChain.doFilter(servletRequest,servletResponse);
            } else {
                //重定向到登录界面
                response.getWriter().write("登录信息已失效，请重新登录！");
                response.sendRedirect("/qinayi/pages/loginPage.html");
                return;
            }
        }

    }

    @Override
    public void destroy() {

    }

    /**
     * @Author: xxxxx
     * @Description: 是否需要过滤
     * @Date: 2018-03-12 13:20:54
     * @param uri
     */
    private boolean isNeedFilter(String uri) {

        for (String includeUrl : includeUrls) {
            if(includeUrl.equals(uri)) {
                return false;
            }
        }

        return true;
    }
}
