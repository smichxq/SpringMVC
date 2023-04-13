package com.example.springmvc;


import com.example.springmvc.util.MailClent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = SpringMvcApplication.class)
public class MailTest {

    @Autowired
    private MailClent mailClent;
    @Autowired
    private TemplateEngine templateEngine;

    @Test
    public void sendMail(){
        mailClent.sendMailMessage("1070678891@qq.com","Test","context test");
    }

    @Test
    public void sendHtmlMail(){
        Context context = new Context();
        context.setVariable("name","smith");

        String html = templateEngine.process("/mail/context",context);

//        System.out.println(html);
        mailClent.sendMailMessage("1070678891@qq.com","Welcome",html);
    }
}
