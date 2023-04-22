package com.example.springmvc;


import com.example.springmvc.util.MailClent;
import jakarta.mail.MessagingException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = SpringMvcApplication.class)
//@EnableAsync
public class MailTest {

    @Autowired
    private MailClent mailClent;
    @Autowired
    private TemplateEngine templateEngine;

    @Test
    public void sendHrmlMail(){
//        mailClent.sendMailMessage("1070678891@qq.com","Test","context test");
//        mailClent.sendMailMessage("1070678891@qq.com","激活","请点击该链接激活: www.baidu.com");
        System.out.println("调用发送");
        try {

            mailClent.sendHtmlMail("1070678891@qq.com","注册","6666666","www.baidu.com").get(300, TimeUnit.SECONDS);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }
        System.out.println("调用发送完毕");

    }

    @Test
    public void sendMail() {
        try {
            mailClent.sendMailMessage("1070678891@qq.com","激活","请点击该链接激活: www.baidu.com");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }



}
