package com.taskviewer.api.property;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@AllArgsConstructor
@ConfigurationProperties(prefix = "mail")
public class MailProperty {

    private final String host;
    private final Integer port;
    private final String username;
    private final String password;

}
