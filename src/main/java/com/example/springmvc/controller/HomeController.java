package com.example.springmvc.controller;

import com.example.springmvc.entity.User;
import com.example.springmvc.mapper.UserMapper;
import com.example.springmvc.services.PageSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Controller
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private PageSet pageSet;
    @Autowired
    private UserMapper userMapper;



    @RequestMapping(value = "allusers",method = RequestMethod.GET)
//    @ResponseBody
    public String getUsers(Model model, @RequestParam(value = "p",required = false,defaultValue = "1") int page) {
//        ArrayList<User> listuser =  userMapper.getUsers();
        if (page == 1) {
            pageSet.setPageTotal(userMapper.getUserCount());
        }
        pageSet.setCurrentPage(page);

        ArrayList<User> listuser =  userMapper.getUsersForLimit(pageSet.getSIZE(),(pageSet.getCurrentPage()-1)*10);


        if (listuser != null) {
            model.addAttribute("usersList",listuser);
            model.addAttribute("page",pageSet);

        }




        return "/demo/alluser";
    }
}
