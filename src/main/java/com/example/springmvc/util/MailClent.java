package com.example.springmvc.util;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class MailClent {
    @Autowired
    private JavaMailSender jms;
    @Value("${spring.mail.username}")
    private String mailSenderName;

    private static final Logger logger = LoggerFactory.getLogger(MailClent.class);

    public void sendMailMessage(String to, String subject, String context) {

        try {
            MimeMessage mm = jms.createMimeMessage();
            MimeMessageHelper mmh = new MimeMessageHelper(mm);

            mmh.setFrom(mailSenderName);
            mmh.setSubject(subject);
            mmh.setTo(to);
            mmh.setText(context,true);

            jms.send(mmh.getMimeMessage());


        } catch (MessagingException e) {
            logger.error("MaimClient: " + e.getMessage());
//            throw new RuntimeException(e);
        }

    }
}
