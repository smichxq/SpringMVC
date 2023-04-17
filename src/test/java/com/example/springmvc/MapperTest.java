package com.example.springmvc;

import com.example.springmvc.controller.HomeController;
import com.example.springmvc.entity.User;
import com.example.springmvc.mapper.UserMapper;
import com.example.springmvc.services.PageSet;
import com.example.springmvc.services.RandomGenerate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = SpringMvcApplication.class)
public class MapperTest implements ApplicationContextAware {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RandomGenerate randomGenerate;
    private ApplicationContext applicationContext;
    User user;
    @Autowired
    HomeController homeController;

    @Autowired
    private PageSet pageSet;

    @Test
    public void test(){
//        homeController.getUsers(new ConcurrentModel());
//        for (int i = 0; i<101; i++){
            user = new User();

            user.setUser(
                RandomGenerate.generateString(8),
                RandomGenerate.generateInt(0,99),
                RandomGenerate.generateString(10),
                RandomGenerate.generateString(5),
                false,
                    "",
                    false

        );
//
//
        userMapper.insertUser(user);
//
//        }

        System.out.println(user.toString());

//        System.out.println(userMapper.deleteUser(2));

//        userMapper.updateAge(4,10);
//        userMapper.updateName(4,"sedfffrgg");

//        System.out.println(userMapper.getUser(7).toString());
//        List<User> users = userMapper.getUsers();
//        for (User user :
//                users) {
//            System.out.println(user.toString());
//        }

//        List<User> users = userMapper.getUsersForLimit(pageSet.getSIZE(),pageSet.getCurrentPage());
//        for (User user:
//                users) {
//            System.out.println(user.toString());
//        }

//        System.out.println(userMapper.getUserCountDevide(10));
//        System.out.println(userMapper.userCheckByAccount("zrpnq"));


    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
