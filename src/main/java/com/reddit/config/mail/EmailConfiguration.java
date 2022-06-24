package com.reddit.config.mail;//package com.reddit.app.config.mail;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class EmailConfiguration {
    @Value("${spring.mail.host}")
    private String host;
    @Value("${spring.mail.port}")
    private Integer port;
    @Value("${spring.mail.username}")
    private String username;
    @Value("${spring.mail.password}")
    private String password;


    @Override
    public String toString() {
        return "EmailConfiguration [host=" + host + ", port=" + port + ", username=" + username + ", password="
                + password + "]";
    }
}
