package com.example.springmvc;


import com.example.springmvc.controller.LoginController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = SpringMvcApplication.class)
public class LoginControllerTest {
    @Autowired
    private LoginController loginController;

    @Test
    public void test(){
        System.out.println(loginController.signUp("234f", "sadasd", "sadasda", "22"));
    }
}
