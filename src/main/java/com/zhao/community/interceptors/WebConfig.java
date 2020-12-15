package com.zhao.community.interceptors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
//@EnableWebMvc//没有静态资源放行，会拦截静态资源
public class WebConfig implements WebMvcConfigurer {
@Autowired
SessionIntersepter sessionIntersepter;//被Spring容器接管后就不能再实例化对象了，声明后直接使用即可
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(sessionIntersepter).addPathPatterns("/**");

    }

}
