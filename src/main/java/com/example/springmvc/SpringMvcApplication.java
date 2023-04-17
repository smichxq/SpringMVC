package com.example.springmvc;

import com.example.springmvc.util.MailClent;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
//@EnableAsync
public class SpringMvcApplication {


    public static void main(String[] args) {
        SpringApplication.run(SpringMvcApplication.class, args);



    }

}
