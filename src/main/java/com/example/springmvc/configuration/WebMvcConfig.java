package com.example.springmvc.configuration;

import com.example.springmvc.controller.interceptor.InterceptorAlpha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private InterceptorAlpha interceptorAlpha;

    //注册拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptorAlpha)//拦截器注册
                .excludePathPatterns("/**/*.css","/**/*.js","/**/*.png","/**/*.jpg","/**/*.jpeg");//放行的资源
//                .addPathPatterns("/home","/login/activation","login/sigin", "home/allusers");//拦截的路径
    }
}
