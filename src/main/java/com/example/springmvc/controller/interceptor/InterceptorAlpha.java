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

//    //Controller前
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        Cookie[] cookies = request.getCookies();
//        if (cookies != null) {
//
//
//            String ticket = CookieUtil.getCookieValue(cookies, "ticket");
//            //请求存在名称为ticket的Cookie
//            if (ticket != null) {
////            LoginTicket loginTicket = loginTicketMapper.selectLoginTicketByTicket(ticket);
//
//                //userLogin底层是直接向数据库查询用户的ticket合法性
//                Map<String, String> map = userServices.userLogin(ticket);
//                if (map.containsKey("userId")) {
//                    //线程安全：并发时，User的存放需要线程隔离技术
//
//                    //直接向数据库查询用户，以防不合法的用户登录
//
//                    User user = userMapper.getUser(Integer.parseInt(map.get("userId")));
//
//                    if (user != null) {
//                        //本次请求用存放用户
//                        hostHolder.setUser(user);
//                        return true;
//                    }
//                    return true;
//
//                }
//                return true;
//            }
//            //不存在名称为ticket的Cookie
//            else {
//
//            }
//            return true;
//        }
//
//        else {
//
//        }
//
//        return true;
//    }


    //Controller前
    //拦截未非法用户(没有cookie、用户的cookie不存在)-重定向登陆页面
    //拦截合法用户(cookie合法的用户)-重定向已登录页面
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies = request.getCookies();
        User user = null;
        if (cookies ==  null) {
            //拦截
            response.sendRedirect("/community/html/login.html");


            return false;
        }
        String ticket = CookieUtil.getCookieValue(cookies, "ticket");

        if (ticket == null){
            //拦截
            response.sendRedirect("/community/html/login.html");
            return false;
        }

        //用户ticket合法性检查
        Map<String,String> map = userServices.userLogin(ticket);
        //ticket在有效期内且用户状态没有被禁止
        if (map.containsKey("userId")) {

            user = userMapper.getUser(Integer.parseInt(map.get("userId")));

            //用户不存在
            if(user == null){
                //拦截
                response.sendRedirect("/community/html/login.html");
                return false;
            }

            //ticket在有效期内且用户状态没有被禁止
            //用户存在

            //可用于并发情况存放user实体
            //hostHolder由拦截器维持
            //user用于在controller之后后渲染视图
            //登陆凭证未过期的用户实体可以直接用在渲染期间，所以使用hostHolder存放并在postHandle接收
            //controller层不需要再次管理有登录凭证的User实体，一切交由postHandle处理
            hostHolder.setUser(user);
            return true;

        }

        //ticket不在在有效期内
        // 或用户状态被禁止
        response.sendRedirect("/community/html/login.html");
        return false;



    }
    //Controller后，渲染前
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        User user = hostHolder.getUser();


        if (user != null) {
            modelAndView.addObject("user",user.getUserAccount());

        }

        else {
            modelAndView.addObject("errMsg","用户状态被禁止");
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
