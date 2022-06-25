package com.reddit.config.mail;//package com.reddit.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

//@Slf4j
@Service
public class MailService {

    @Autowired
    private  MailContentBuilder mailContentBuilder;

    @Autowired
    private EmailConfiguration emailConfiguration;

    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSenderImpl = new JavaMailSenderImpl();
        mailSenderImpl.setHost(emailConfiguration.getHost());
        mailSenderImpl.setPort(emailConfiguration.getPort());
        mailSenderImpl.setUsername(emailConfiguration.getUsername());
        mailSenderImpl.setPassword(emailConfiguration.getPassword());
        return mailSenderImpl;
    }

    @Async
    public void sendSimpleMessage(NotificationEmail notificationEmail) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(notificationEmail.getRecipient());
        message.setSubject(notificationEmail.getSubject());
        message.setText(mailContentBuilder.build(notificationEmail.getBody()));
        message.setFrom("oladipupo@gmail.com");
        getJavaMailSender().send(message);

    }
}
