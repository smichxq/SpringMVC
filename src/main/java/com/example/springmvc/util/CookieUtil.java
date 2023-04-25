package com.example.springmvc.util;

import jakarta.servlet.http.Cookie;
import org.apache.commons.lang3.StringUtils;

public class CookieUtil {

    //输入cookie名称，返回对应的值
    //无值返回null
    public static String getCookieValue(Cookie[] cookies, String cookieName) {
        if (cookies != null && cookieName != null && !StringUtils.isBlank(cookieName)) {
            for (Cookie cookie :
                    cookies) {
                if (StringUtils.equals(cookie.getName(),cookieName)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
