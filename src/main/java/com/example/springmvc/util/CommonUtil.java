package com.example.springmvc.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;

import java.util.UUID;

public class CommonUtil {

    //随机字符串
    public static String UUID() {
        //UUID提供随机字符串，附加条件是将“-”替换为”空字符串“
        return UUID.randomUUID().toString().replace("-","");
    }


    //md5单向加密，用于数据库中密码加密处理
    //配合salt处理，防止暴力破解
    public static String getMd5(String str) {
        if (StringUtils.isBlank(str)){return null;}
        return DigestUtils.md5DigestAsHex(str.getBytes());
    }

    public static boolean isNum(String str) {
        if (StringUtils.isNumeric(str)) {
            return true;
        }
        return false;
    }
}
