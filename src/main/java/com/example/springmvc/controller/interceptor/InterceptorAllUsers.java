//`package com.example.springmvc.controller.interceptor;
//
//import com.example.springmvc.entity.User;
//import com.example.springmvc.services.UserServices;
//import com.example.springmvc.util.CookieUtil;
//import com.example.springmvc.util.HostHolder;
//import jakarta.servlet.http.Cookie;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.HandlerInterceptor;
//import org.springframework.web.servlet.ModelAndView;
//
//import java.util.Map;
//
//@Component
//public class InterceptorAllUsers implements HandlerInterceptor {
//
//    @Autowired
//    private UserServices userServices;
//
//    @Autowired
//    private HostHolder hostHolder;
//
//
//
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        Cookie[] cookies = request.getCookies();
//        String ticket = CookieUtil.getCookieValue(cookies, "ticket");
//
//        if (ticket != null && !StringUtils.isBlank(ticket)) {
//            Map<String, String> map = userServices.userLogin(ticket);
//            if (map.containsKey("userId")) {
//                User user = userServices.getUserById(Integer.parseInt(map.get("userId")));
//                if (user != null) {
//                    hostHolder.setUser(user);
//                    return true;
//                }
//            }
//        }
//        return true;
//
//
////        return HandlerInterceptor.super.preHandle(request, response, handler);
//    }
//
//    @Override
//    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//
//        User user = hostHolder.getUser();
//        if (user != null) {
//            modelAndView.addObject("canUse", true);
//            modelAndView.addObject("usersList",)
//        }
//
//        else {
//            modelAndView.addObject("user")
//        }
//
////        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
//    }
//
//    @Override
//    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
//    }
//}
//`