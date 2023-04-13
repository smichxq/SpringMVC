package com.example.springmvc;


import com.example.springmvc.entity.User;
import com.example.springmvc.services.UserServices;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = SpringMvcApplication.class)
public class UserServicesTest {
    @Autowired
    private UserServices userServices;


    @Test
    public void test() {
        User userExist = new User();
        userExist.setUser(
                "d",
                23,
                "sadsad",
                "d",
                false

        );
        Map<String, Object> maps = userServices.userMessageCheck(userExist);
        System.out.println("mapSize: " + maps.size());
        if (!maps.isEmpty()) {
            if ((boolean) maps.get("isNull")) {
                System.out.println("userServices.userMessageCheck(null)");
            }
            else {
                System.out.println("user_name: " + maps.get("user_name"));
                System.out.println("user_Account:" + maps.get("user_account"));
            }

        }
    }
}
