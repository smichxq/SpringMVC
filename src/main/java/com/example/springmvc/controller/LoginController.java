package com.example.springmvc.controller;


import com.example.springmvc.entity.User;
import com.example.springmvc.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller("/login")
public class LoginController {
    @Autowired
    private UserMapper userMapper;

//    @RequestMapping(value = "/signup",method = RequestMethod.POST)
//    @ResponseBody
//    public Map<User,Integer> signUp(String account, String password, String name, String age) {
//        User user = new User();
//        Map<User,Integer> map = new HashMap();
//        user.setUser(name,Integer.parseInt(age),password,account,false);
//        int suc = userMapper.insertUser(user);
//        map.put(user,Integer.valueOf(suc));
//        return map;
//    }


    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public void getRegisterPage() {

    }

}
