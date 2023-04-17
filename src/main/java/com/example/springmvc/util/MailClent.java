package com.example.springmvc.util;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.concurrent.CompletableFuture;


@Component
public class MailClent{
    @Autowired
    private JavaMailSender jms;
    @Autowired
    private TemplateEngine templateEngine;
    @Value("${spring.mail.username}")
    private String mailSenderName;

    private static final Logger logger = LoggerFactory.getLogger(MailClent.class);


    private MimeMessageHelper mmh;



//    @Async("mailExecutor")
    public void sendMailMessage(String to, String subject, String context) throws  MessagingException{

//        try {
            MimeMessage mm = jms.createMimeMessage();
            mmh = new MimeMessageHelper(mm);

            mmh.setFrom(mailSenderName);
            mmh.setSubject(subject);
            mmh.setTo(to);
            mmh.setText(context,true);
            jms.send(mmh.getMimeMessage());
//            jms.send(mmh.getMimeMessage());
//            new Thread().start();
//            new Thread(this).start();




//        } catch (MessagingException e) {
//            logger.error("MaimClient: " + e.getMessage());
////            throw new RuntimeException(e);
//        }

    }

    @Async
    public CompletableFuture<String> sendHtmlMail(String to, String subject, String userName, String activityUrl) throws MessagingException {
        logger.info("正在执行线程"+ Thread.currentThread().getName());
        Context context = new Context();
//        logger.info("1");
        context.setVariable("name",userName);
//        logger.info("2");
        context.setVariable("activityUrl",activityUrl);
        logger.info("3");

        String html = templateEngine.process("/mail/context",context);
        logger.info("4");

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        logger.info("5");
        System.out.println(html);
        logger.info("5");
//        System.out.println("发送前");
        sendMailMessage(to,subject,html);
        logger.info("6");
//        System.out.println("发送了");
        logger.info("执行结果:"+ html);
        return CompletableFuture.completedFuture("mailExecutor Done!");


    }

//
//    public void run() {
//        System.out.println("发送中");
//        jms.send(mmh.getMimeMessage());
//        System.out.println("发送完");
//    }
}
