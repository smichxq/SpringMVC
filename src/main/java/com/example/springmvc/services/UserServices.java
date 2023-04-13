package com.example.springmvc.services;


import com.example.springmvc.entity.User;
import com.example.springmvc.mapper.UserMapper;
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

    //传入注册时用户的填写内容，
    //返回<"isNull",trur/falsse> <"user_name",trur/falsse> <"user_account",trur/falsse>
    //先检查“isNull”，如果为true，则不包含后两项
    public Map<String,Object> userMessageCheck(User user) {

        boolean userNameExist = false;
        boolean userAccExist = false;

        Map returnMap = new HashMap<String,Object>();
        if (user == null) {

            returnMap.put("isNull",true);
            return returnMap;
        }

        else {returnMap.put("isNull",false);}

        //返回<"user_name","value"> , <"user_account", "value">
        //一个键值对对应一个Map对象
        List<Map<String,String>> lists = userMapper.userCheckByAccName(user.getUserName(),user.getUserAccount());

        //已存在
        if (!lists.isEmpty()) {

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




//            for (String s:
//                    resultMap.keySet()) {
//                if (s.equals("user_account") && !((resultMap.get(user.getUserAccount())).isEmpty())) {
//                    returnMap.put("user_account",true);
//                }
//                if (s.equals("user_name") && !((resultMap.get(user.getUserName())).isEmpty())) {
//                    returnMap.put("user_name",true);
//                }
//            }
        }

        //不存在
        else {
            returnMap.put("user_name",false);
            returnMap.put("user_account",false);
        }

        return returnMap;

    }


}
