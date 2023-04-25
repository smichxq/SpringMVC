package com.example.springmvc.util;

import com.example.springmvc.entity.User;
import org.springframework.stereotype.Component;

/**
 * 持有用户信息，在整个请求中有效
 * 使用ThreadLocal实现线程隔离
 * 原理是用Map去存放各个线程
 */
@Component
public class HostHolder {
    private ThreadLocal<User> users = new ThreadLocal<User>();

    public void setUser(User user) {
        users.set(user);
    }

    public User getUser() {
        return users.get();
    }


    public void clear() {
        users.remove();
    }
}
