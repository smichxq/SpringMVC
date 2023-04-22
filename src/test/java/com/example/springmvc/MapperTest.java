package com.example.springmvc;

import com.example.springmvc.controller.HomeController;
import com.example.springmvc.entity.LoginTicket;
import com.example.springmvc.entity.User;
import com.example.springmvc.mapper.LoginTicketMapper;
import com.example.springmvc.mapper.UserMapper;
import com.example.springmvc.services.PageSet;
import com.example.springmvc.services.RandomGenerate;

import com.example.springmvc.util.CommonUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

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

    @Autowired
    private LoginTicketMapper loginTicketMapper;

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

    @Test
    public void loginMapperTest() {
        String ticket = CommonUtil.UUID().substring(0,6);
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setTicket(ticket);
        loginTicket.setExpired(new Date(System.currentTimeMillis() + 1000 * 60));
//        loginTicket.setId(2);
        loginTicket.setStatus(0);
        loginTicket.setUserId(2);
        loginTicketMapper.insertLoginTicket(loginTicket);
        System.out.println("-----------");
        System.out.println(loginTicketMapper.selectLoginTicketByTicket(ticket).toString());
        System.out.println("------------");
        loginTicketMapper.updateStatus(ticket,1);
        System.out.println("------------");
        System.out.println(loginTicketMapper.selectLoginTicketByTicket(ticket).toString());
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
