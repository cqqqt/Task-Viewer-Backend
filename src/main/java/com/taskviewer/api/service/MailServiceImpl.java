package com.taskviewer.api.service;

import com.taskviewer.api.model.User;
import com.taskviewer.api.property.MailProperty;
import freemarker.template.Configuration;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    private final Configuration configuration;
    private final MailProperty mailProperty;
    private final JavaMailSender javaMailSender;

    @Override
    @SneakyThrows
    public void send(final String email, final String subject, final String message) {
        MimeMessage mimeMessage = this.javaMailSender.createMimeMessage();
        StringWriter stringWriter = new StringWriter();
        Map<String, Object> model = new HashMap<>();
        model.put("message", message);
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setSubject(subject);
        helper.setFrom(this.mailProperty.getHost());
        helper.setTo(email);
        String content = stringWriter.getBuffer().toString();
        helper.setText(content, true);
        this.configuration.getTemplate("mail.ftl").process(model, stringWriter);
        this.javaMailSender.send(mimeMessage);
    }

}
