package com.example.springmvc.controller;


import com.example.springmvc.entity.User;
import com.example.springmvc.mapper.UserMapper;
import com.example.springmvc.services.UserServices;
import com.example.springmvc.util.MailClent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller()
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserServices userServices;

    @Autowired
    private MailClent mailClent;

    @RequestMapping(value = "/signupcommit",method = RequestMethod.POST)
    @ResponseBody
    public String signUp(String account, String password, String name, String age) {
//        User user = new User();
//        user.setUser("",name,Integer.parseInt(age),password,account,true,"",);
        if (userServices.userRegister(account,password,name,age)) {
//            System.out.println(user.getUserAccount());

            mailClent.sendMailMessage(account,"激活","请点击该链接激活: www.baidu.com");
            return "成功";
        }
        return "失败";
    }


    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String getRegisterPage(Model model) {
        model.addAttribute("ef","df");
        System.out.println("------------");
        return "/demo/signup";
    }

    @RequestMapping(value = "signupacc", method = RequestMethod.POST)
    @ResponseBody
    public String getUserAccount(@RequestParam("account") String account) {
        boolean isAccountExists = userServices.checkUserByAccount(account);
        if (isAccountExists) {
            return "{\"success\": false, \"message\": \"该账号已被占用\"}";
        } else {
            return "{\"success\": true, \"message\": \"该账号可用\"}";
        }


    }

    @RequestMapping(value = "signupname", method = RequestMethod.POST)
    @ResponseBody
    public String getUserName(@RequestParam("name") String name) {
        System.out.println("name--------" + name);
        boolean isAccountExists = userServices.checkUserByName(name);
        if (isAccountExists) {
            return "{\"success\": false, \"message\": \"该名字已被占用\"}";
        } else {
            return "{\"success\": true, \"message\": \"该名字可用\"}";
        }


    }



}
