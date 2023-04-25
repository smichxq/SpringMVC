package com.example.springmvc.services;


import com.example.springmvc.entity.LoginTicket;
import com.example.springmvc.entity.User;
import com.example.springmvc.mapper.LoginTicketMapper;
import com.example.springmvc.mapper.UserMapper;
import com.example.springmvc.util.CommonUtil;
import com.example.springmvc.util.MailClent;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServices extends Thread{

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private LoginTicketMapper loginTicketMapper;

    //域名:localhost:8080
    @Value("${community.path.domain}")
    private String domain;

    //项目路径:localhost:8080/community
    @Value("${server.servlet.context-path}")
    private String path;

    @Autowired
    private MailClent mailClent;


    public User getUserById(int id) {
        return userMapper.getUser(id);
    }




//    @Async("mailExecutor")
    public Map<String,Object> userRegister(String account, String password, String name, int age) {
        Map<String,Object> returnMap = new HashMap();
        User user = new User();
        user.setUserAccount(account);
        user.setUserName(name);
        user.setUserAge(age);
        user.setUserPassword(password);

        //提交后二次验证
        Map<String,Object> map = userMessageCheck(user);

        //user为空
        if (!(boolean) map.get("canUse")) {
            returnMap.put("canUse",false);
            return returnMap;
//            return false;
        }

        //姓名已存在
        if ((boolean) map.get("user_name")) {
            returnMap.put("canUse",false);
            return returnMap;
//            return false;
        }

        //账号已存在
        if ((boolean) map.get("user_account")) {
            returnMap.put("canUse",false);
            return returnMap;
//            return false;
        }

        //没有设置密码
        if (StringUtils.isBlank(user.getUserPassword())) {
            returnMap.put("canUse",false);
            return returnMap;
//            return false;
        }



        user.setUserSalt(CommonUtil.UUID().substring(0,5));

        user.setUserPassword(CommonUtil.getMd5(password + user.getUserSalt()));

        user.setUserActivityCode(CommonUtil.UUID());

        if (userMapper.insertUser(user) == 1) {
            try {
                mailActivity(user.getUserAccount(),user.getUserName(),user.getUserId(),user.getUserActivityCode());
            } catch (MessagingException e) {

                throw new RuntimeException(e);
            }
//            return CompletableFuture.completedFuture("mailExecutor Done!");
            returnMap.put("canUse",true);
            returnMap.put("userActivityCode",user.getUserActivityCode());
            returnMap.put("userId",user.getUserId());
            return returnMap;
//            return true;
        }

//        return CompletableFuture.completedFuture("mailExecutor Done!");
        returnMap.put("canUse",false);
        return returnMap;
//        return false;
    }



    public void mailActivity(String userAccount, String userName, int userId, String activityCode) throws MessagingException {
        //localhost:8080/community/
        String activityUrl = domain + path + "/login/activation?" + "i=" + userId + "&" + "c=" + activityCode;
        mailClent.sendHtmlMail(userAccount,"激活",userName,activityUrl);

    }



    //传入注册时用户的填写内容，
    //返回<"isNull",trur/falsse> <"user_name",trur/falsse> <"user_account",trur/falsse>
    //先检查“isNull”，如果为true，则不包含后两项
    public Map<String,Object> userMessageCheck(User user) {

        boolean userNameExist = false;
        boolean userAccExist = false;

        Map returnMap = new HashMap<String,Object>();
        if (user == null) {

            returnMap.put("canUse",false);
//            throw new IllegalArgumentException("UserServices.userMessageCheck(User): User为空");
            return returnMap;
        }

         returnMap.put("canUse",true);







        //返回<"user_name","value"> , <"user_account", "value">
        //一个键值对对应一个Map对象
        List<Map<String,String>> lists = userMapper.userCheckByAccName(user.getUserName(),user.getUserAccount());

        //已存在
        if (!lists.isEmpty()) {

            //{<>},{<>},{<>}
            for (Map<String,String> map:
                 lists) {
                if (map.get("user_account").equals(user.getUserAccount())) {
                    userAccExist = true;


                }
                if (map.get("user_name").equals(user.getUserName())) {
                    userNameExist = true;

                }
            }

            if (userAccExist && userNameExist) {

                returnMap.put("user_account",true);
                returnMap.put("user_name",true);
            }

            else {
                if (userAccExist) {

                    returnMap.put("user_account",true);
                    returnMap.put("user_name",false);
                }
                else {

                    returnMap.put("user_account",false);
                    returnMap.put("user_name",true);
                }
            }


        }

        //不存在
        else {
            returnMap.put("user_name",false);
            returnMap.put("user_account",false);
        }

        return returnMap;

    }

    public boolean checkUserByAccount(String userAcc) {
        User user = new User();
        user.setUser(
                "",
                0,
                "",
                userAcc,
                false,
                "",
                false
        );

        Map<String,Object> map = userMessageCheck(user);
        if ((boolean) map.get("user_account")  && (boolean) map.get("canUse")){
            return true;
        }
        return false;

    }

    public boolean checkUserByName(String userName) {
        User user = new User();
        user.setUser(
                userName,
                0,
                "",
                "",
                false,
                "",
                false
        );

        Map<String,Object> map = userMessageCheck(user);
        if ((boolean) map.get("user_name") && (boolean) map.get("canUse")){
            return true;
        }
        return false;

    }

    //用来处理从Controller层返回的用户登录信息
    //用户合法性判断
    //合法后注册ticket，返回登陆凭证
    public Map<String, Object> userLogin(String account, String password, long expired) {
        Map<String, Object> map = new HashMap<>();

        //数据合法性校验
        if (StringUtils.isBlank(account)) {
            map.put("errMsg", "账号不能未空");
            return map;

        }

        if (StringUtils.isBlank(password)) {
            map.put("errMsg", "密码不能为空");
            return map;
        }

        //用户查询
        User user = userMapper.getUserByAccount(account);
        if (user == null) {
            map.put("errMsg", "无该用户");
            return map;
        }

        if (!CommonUtil.getMd5(password + user.getUserSalt()).equals(user.getUserPassword())) {
            map.put("errMsg", "密码错误");
            return map;
        }

        if (!user.getUserStatus()) {
            map.put("errMsg", "用户未激活");
            return map;
        }





        //登录凭证注册
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setStatus(0);
        loginTicket.setUserId(user.getUserId());
        //ticket是浏览器保存的凭证
        loginTicket.setTicket(CommonUtil.UUID());
        System.out.println("UserServices: " + expired);
        //有效时间：expired
        loginTicket.setExpired(new Date(System.currentTimeMillis() + expired));
        System.out.println(loginTicket.toString());
        loginTicketMapper.insertLoginTicket(loginTicket);

        map.put("loginTicket",loginTicket);

        return map;



    }


    //用户登录免登录检查
    public Map<String,String> userLogin(String ticketForCookie) {
        Map<String,String> map = new HashMap<>();

        LoginTicket loginTicket = loginTicketMapper.selectLoginTicketByTicket(ticketForCookie);

        //ticket在有效期内且用户状态没有被禁止
        //优化思路：可以增加定时维护loginTicket表的服务,将过期的ticket删除、更新被封禁用户的status字段
        if (loginTicket != null && loginTicket.getExpired().after(new Date()) && loginTicket.getStatus() == 1) {
            map.put("userId", String.valueOf(loginTicket.getUserId()));
            return map;
        }



        return map;

    }



}
