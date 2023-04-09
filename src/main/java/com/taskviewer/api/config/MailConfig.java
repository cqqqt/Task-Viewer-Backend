package com.taskviewer.api.config;

import com.taskviewer.api.property.MailProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Base64;
import java.util.Properties;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class MailConfig {

    private final MailProperty mailProperty;

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(this.mailProperty.getHost());
        mailSender.setUsername(new String(
                Base64.getDecoder()
                        .decode(this.mailProperty.getUsername())));
        mailSender.setPassword(new String(
                Base64.getDecoder()
                        .decode(this.mailProperty.getPassword())));
        mailSender.setPort(this.mailProperty.getPort());
        Properties properties = mailSender.getJavaMailProperties();
        properties.setProperty("mail.smtp.starttls.enable", "true");
        return mailSender;
    }

}
