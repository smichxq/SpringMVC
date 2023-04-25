package com.example.springmvc.controller.interceptor;

import com.example.springmvc.entity.LoginTicket;
import com.example.springmvc.entity.User;
import com.example.springmvc.mapper.LoginTicketMapper;
import com.example.springmvc.mapper.UserMapper;
import com.example.springmvc.services.UserServices;
import com.example.springmvc.util.CookieUtil;
import com.example.springmvc.util.HostHolder;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.Map;

@Component
public class InterceptorAlpha implements HandlerInterceptor {

//    @Autowired
//    private LoginTicketMapper loginTicketMapper;
    @Autowired
    private UserServices userServices;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private HostHolder hostHolder;

    //Controller前
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {


            String ticket = CookieUtil.getCookieValue(cookies, "ticket");

            if (ticket != null) {
//            LoginTicket loginTicket = loginTicketMapper.selectLoginTicketByTicket(ticket);
                Map<String, String> map = userServices.userLogin(ticket);
                if (map.containsKey("userId")) {
                    //线程安全：多并发时，User的存放需要线程隔离技术
                    User user = userMapper.getUser(Integer.parseInt(map.get("userId")));

                    if (user != null) {
                        //本次请求用存放用户
                        hostHolder.setUser(user);
                        return true;
                    }
                    return true;

                }
                return true;
            }
            return true;
        }

        return true;
    }

    //Controller后，渲染前
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        User user = hostHolder.getUser();


        if (user != null) {
            modelAndView.addObject("user",user.getUserAccount());
        }

        else {
            modelAndView.addObject("user","没有登陆");
        }

//        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    //渲染后,连接结束
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        hostHolder.clear();
//        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
