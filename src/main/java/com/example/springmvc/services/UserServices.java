package com.example.springmvc.services;


import com.example.springmvc.entity.User;
import com.example.springmvc.mapper.UserMapper;
import com.example.springmvc.util.CommonUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServices {

    @Autowired
    private UserMapper userMapper;

    //域名:localhost:8080
    @Value("${community.path.domain}")
    private String domain;

    //项目路径:localhost:8080/community
    @Value("${server.servlet.context-path}")
    private String path;


    public User getUserById(int id) {
        return userMapper.getUser(id);
    }




    public boolean userRegister(User user) {

        //提交后二次验证
        Map<String,Object> map = userMessageCheck(user);

        //user为空
        if (!(boolean) map.get("canUse")) {
            return false;
        }

        //姓名已存在
        if ((boolean) map.get("user_name")) {
            return false;
        }

        //账号已存在
        if ((boolean) map.get("user_account")) {
            return false;
        }

        //没有设置密码
        if (StringUtils.isBlank(user.getUserPassword())) {
            return false;
        }

        user.setUserSalt(CommonUtil.UUID().substring(0,5));

        user.setUserPassword(CommonUtil.getMd5(user.getUserPassword() + user.getUserSalt()));

        userMapper.insertUser(user);

        return true;
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
                false
        );

        Map<String,Object> map = userMessageCheck(user);
        if ((boolean) map.get("user_name") && (boolean) map.get("canUse")){
            return true;
        }
        return false;

    }


}
