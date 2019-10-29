package com.master.qianyi.inteceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * project:itcast-microservice-user
 * function:
 * author:kangkang
 * date: 2019/2/24
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private ManagerLoginInterceptor managerLoginInterceptor;

    @Autowired
    private MobileLoginInterceptor mobileLoginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry interceptorRegistry) {
        //后台登录拦截器
        // addPathPatterns("/**") 表示拦截所有的请求，
        // excludePathPatterns("/login", "/register") 表示除了登陆与注册之外，因为登陆注册不需要登陆也可以访问
        interceptorRegistry.addInterceptor(managerLoginInterceptor).addPathPatterns("/manager/**","/infoMan/**","/courseMan/**")
                .excludePathPatterns("/manager/loginPage","/manager/ossFileUpload", "/manager/login", "/css/*", "/js/**");
        //移动端登录拦截器
        interceptorRegistry.addInterceptor(mobileLoginInterceptor).addPathPatterns("/mobile/**")
                .excludePathPatterns("/mobile/sendSms","/mobile/smsRegister","/mobile/passwordLogin"
                        ,"/mobile/resetPassword","/mobile/threePartyLogin","/mobile/bindPhoneNumber");
    }

}
