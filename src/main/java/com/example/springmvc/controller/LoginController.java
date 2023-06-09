package com.example.springmvc.controller;


import com.example.springmvc.entity.LoginTicket;
import com.example.springmvc.entity.User;
import com.example.springmvc.mapper.UserMapper;
import com.example.springmvc.services.UserServices;
import com.google.code.kaptcha.Producer;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import jakarta.servlet.http.Cookie;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

import java.util.Map;

@Controller
//@CrossOrigin(origins = "http://localhost:3000/")
@RequestMapping("/login")
public class LoginController {

//    private static int count = 0;
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserServices userServices;

    @Autowired
    private Producer producer;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);





    @RequestMapping(value = "/activation",method = RequestMethod.GET)
    public String userActivity(@RequestParam("i") String userId,@RequestParam("c") String activityCode,Model model) {

        int userId_Int = Integer.parseInt(userId);

        User user = userMapper.getUser(userId_Int);
        if (user.getUserId() == userId_Int && user.getUserActivityCode().equals(activityCode)) {
             model.addAttribute("activityStatue",true);
             model.addAttribute("userId",userId);
             userMapper.updateStatusById(userId_Int,true);
        }
        else {
            model.addAttribute("activityStatue",false);
        }
        return "/demo/activity";
    }

    @RequestMapping(value = "/signupcommit",method = RequestMethod.POST)
    @ResponseBody
    public String signUp(String account, String password, String name, String age) {
//        User user = new User();
//        user.setUser("",name,Integer.parseInt(age),password,account,true,"",);
        Map<String,Object> map = userServices.userRegister(account,password,name,Integer.parseInt(age));
        if ((boolean)(map.get("canUse"))) {
//            System.out.println(user.getUserAccount());

//            mailClent.sendMailMessage(account,"激活","请点击该链接激活: www.baidu.com");
//            return "redirect:" + "/activation?" + "i=" +  map.get("userId") + "&" + "c=" + map.get("userActivityCode");
            return  "成功，请打开邮箱激活";
        }
        return "失败";
//        return  new RedirectView("/activation");
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

    @RequestMapping(value = "/kaptcha*", method = RequestMethod.GET)
//    @RequestMapping(value = "/kaptcha", method = RequestMethod.POST)//使用React重构
//    @CrossOrigin(allowedHeaders = "*")
    public void getKaptcha(HttpServletResponse httpServletResponse, HttpSession session) {
        //验证码
        String text = producer.createText();
        BufferedImage image = producer.createImage(text);

        //验证码存入session以便记录档次当此状态

        //使用session后，服务器会将该session信息放在Cookie中一并返回给浏览器
        //该Cookie默认当前位置有效，浏览器标签关闭失效
        session.setAttribute("kaptcha",text);

        //图片输出给浏览器
        httpServletResponse.setContentType("image/png");

        try {
            OutputStream out = httpServletResponse.getOutputStream();

            ImageIO.write(image, "png", out);
        } catch (IOException e) {
            logger.error("验证码响应失败: " + e.getMessage());
        }
    }

    //用来转到用户登录页面
    //提前截获免登录用户并将其重定向
    //还可使用@CookieValue("ticket") String ticket来直接获取
    //不建议将requres/response对象传入Userservices，因为容器会随时清空这个对象的栈信息
    @RequestMapping(value = "/sigin", method = RequestMethod.GET)
    public String preUserSignin(HttpServletRequest request, Model model) {
        String cookie = null;
        Cookie[] cookies = request.getCookies();
        if (cookies==null){
            model.addAttribute("errMsg","cookie 为空");
            return "/err/loginerr";
        }
        for (Cookie c:cookies){
            if(c.getName().equals("ticket")){
                cookie = c.getValue();
            }
        }
        //没有携带cookie的直接去登录
        if (cookie == null) {
            return "/demo/login";
        }

        Map<String,String> map = userServices.userLogin(cookie);

        if (map.containsKey("userId")) {
            User user = userMapper.getUser(Integer.parseInt(map.get("userId")));
            if (user != null) {
                model.addAttribute("user",user.getUserAccount());
//                return  "redirect"
                return "/demo/success";
            }
            else {
                model.addAttribute("errMsg","你的ticket确实存在，但用户数据已被注销");
                return "/err/loginerr";
            }
        }

        return "/demo/login";
    }


//    @RequestMapping(value = "/error", method = RequestMethod.GET)
//    @ResponseBody
//    public Map<String,Object> getErrMsg() {
//        return null;
//    }


    //处理用户登录
    @RequestMapping(value = "/sigin", method = RequestMethod.POST)
    public String userSignin(String account, String password, String code, boolean remberme, Model model, HttpSession session, HttpServletResponse response, HttpServletRequest request) {
        System.out.println(account + password + code + remberme);
        System.out.println(request.getCookies());
        System.out.println(response.getHeaderNames());

//        System.out.println(response.getTrailerFields());
//        System.out.println(session.getAttributeNames());
//        System.out.println(session.getAttributeNames());

        String kaptcha =(String) session.getAttribute("kaptcha");
//        rememberMe = true;
        logger.info("kaptcha: " + kaptcha);

        //接收UserServices.userLogin方法返回的值
        Map<String,Object> map = null;


        //浏览器传入的验证码为空、session保存的验证码未空、传入的验证码错误(不区分大小写)
        if (StringUtils.isBlank(code) || StringUtils.isBlank(kaptcha) || !kaptcha.equalsIgnoreCase(code)) {
            model.addAttribute("errMsg", "验证码错误");
            return "/err/loginerr";
        }

        //remberMe用来选择是否免登录，即服务端记录用户登录凭证时间
//        if (rememberMe) {
//            //有效期30天
//            map = userServices.userLogin(account,password,1000 * 60 * 60 * 24 * 30);
//        }
//        else {
//            //有效期1天
//            map = userServices.userLogin(account,password,1000 * 60 * 60 * 24 * 1);
//        }

        //上面等效替代
        //单位为秒，浏览器cookie的有效时间
        //2,678,400
        int expired = remberme?1 * 60 * 60 * 24 * 31:1 * 60 * 60 * 24 * 1;

        //expired * 800：19小时~31天
        map = userServices.userLogin(account,password, expired * 800);

        if (map.containsKey("errMsg")) {
            model.addAttribute("errMsg", map.get("errMsg"));
            return "/err/loginerr";
        }


        //登陆成功，Services已经注册凭证
        if (map.containsKey("loginTicket")) {
            //让浏览器接收凭证
            Cookie cookie = new Cookie("ticket",((LoginTicket) map.get("loginTicket")).getTicket());

            //Cookie生效范围，登陆后一般是整个项目都可
            cookie.setPath(contextPath);
            cookie.setMaxAge(expired);
            response.addCookie(cookie);
            //重定向登陆成功页面
            model.addAttribute("user",account);
            return "/demo/success";

        }
        else {
            model.addAttribute("errMsg","登陆凭证无效，请联系管理员");
            return "/err/loginerr";
        }




//        return "";

    }

    //处理用户登录(前后端分离)
//    @RequestMapping(value = "/sigin", method = RequestMethod.POST)
//    @ResponseBody
//    public Map<String,String> userSigninJson(String account, String password, String code, boolean remberme, HttpSession session, HttpServletResponse response) {
//        String kaptcha =(String) session.getAttribute("kaptcha");
////        rememberMe = true;
//        logger.info("kaptcha: " + kaptcha);
//
//        //接收UserServices.userLogin方法返回的值
//        Map<String,Object> map = null;
//        Map<String,String> mapResponse = null;
//
//
//        //浏览器传入的验证码为空、session保存的验证码未空、传入的验证码错误(不区分大小写)
//        if (StringUtils.isBlank(code) || StringUtils.isBlank(kaptcha) || !kaptcha.equalsIgnoreCase(code)) {
//
//            model.addAttribute("errMsg", "验证码错误");
//            return "/err/loginerr";
//        }
//
//        //remberMe用来选择是否免登录，即服务端记录用户登录凭证时间
////        if (rememberMe) {
////            //有效期30天
////            map = userServices.userLogin(account,password,1000 * 60 * 60 * 24 * 30);
////        }
////        else {
////            //有效期1天
////            map = userServices.userLogin(account,password,1000 * 60 * 60 * 24 * 1);
////        }
//
//        //上面等效替代
//        int expired = remberme?1 * 60 * 60 * 24 * 31:1000 * 60 * 60 * 24 * 1;
//
//        map = userServices.userLogin(account,password,expired);
//
//        if (map.containsKey("errMsg")) {
//            model.addAttribute("errMsg", map.get("errMsg"));
//            return "/err/loginerr";
//        }
//
//
//        //登陆成功，Services已经注册凭证
//        if (map.containsKey("loginTicket")) {
//            //让浏览器接收凭证
//            Cookie cookie = new Cookie("ticket",((LoginTicket) map.get("loginTicket")).getTicket());
//
//            //Cookie生效范围，登陆后一般是整个项目都可
//            cookie.setPath(contextPath);
//            cookie.setMaxAge(expired);
//            response.addCookie(cookie);
//            //重定向登陆成功页面
//            model.addAttribute("user",account);
//            return "/demo/success";
//
//        }
//        else {
//            model.addAttribute("errMsg","登陆凭证无效，请联系管理员");
//            return "/err/loginerr";
//        }
//
//
//
//
////        return "";
//
//    }



}
