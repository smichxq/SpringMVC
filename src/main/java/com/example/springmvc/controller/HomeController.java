package com.example.springmvc.controller;

import com.example.springmvc.entity.User;
import com.example.springmvc.mapper.UserMapper;
import com.example.springmvc.services.PageSet;
import com.example.springmvc.util.CommonUtil;
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

    public String getUsers(Model model, @RequestParam(value = "p",required = false,defaultValue = "1") String page) {
//        ArrayList<User> listuser =  userMapper.getUsers();
        ArrayList<User> listuser = null;
        int pageInt;

        if (CommonUtil.isNum(page)) {
            pageInt = Integer.parseInt(page);
        }

        else {
            model.addAttribute("canUse",false);
            return "/demo/alluser";
        }

        //第一次访问时默认是第一页
        if (pageInt == 1) {
            pageSet.setPageTotal(userMapper.getUserCountDevide(10));
            System.out.println();
        }

        if (pageInt > pageSet.getPageTotal() || pageInt <= 0) {
            model.addAttribute("canUse",false);
            return "/demo/alluser";
        }

        else {

            model.addAttribute("canUse",true);
        }

        pageSet.setCurrentPage(pageInt);

        if (pageInt > 0 && pageInt <= pageSet.getPageTotal()) {

            listuser = userMapper.getUsersForLimit(pageSet.getSIZE(), (pageSet.getCurrentPage() - 1) * 10);

        }


        if (listuser != null) {
            model.addAttribute("usersList",listuser);
            model.addAttribute("page",pageSet);

        }


        return "/demo/alluser";
    }
}
