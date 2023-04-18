package com.example.springmvc.controller;

import com.example.springmvc.util.CommonUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/cookie")
public class CookieDemo {

    @RequestMapping(value = "/set",method = RequestMethod.GET)
    @ResponseBody
    public String setCookie(HttpServletResponse response) {
//        Cookie cookie = new Cookie("currentcok", CommonUtil.UUID().substring(0,8));
        Cookie cookie = new Cookie(" "," ");
        cookie.setPath("/community/cookie");
        //设置失效时间，使用持久化存放
//        cookie.setMaxAge(60 * 10);
        response.addCookie(cookie);
        return "set cookie";
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ResponseBody
    public String getCookie(@CookieValue("currentcok") String cok) {

        return cok;
    }

    @RequestMapping(value = "/session/set",method = RequestMethod.GET)
    @ResponseBody
    public String setSession(HttpSession session) {
        session.setAttribute("name","session");
        session.setAttribute("value: ",CommonUtil.UUID());
        return "set session";
    }

    @RequestMapping(value = "/session/get",method = RequestMethod.GET)
    @ResponseBody
    public String getSession(HttpSession session) {

        return "sessionId:" + session.getId() + " " + "name:" + session.getAttribute("name") + "" + "value:" + session.getAttribute("value");
    }
}
