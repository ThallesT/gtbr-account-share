package com.gtbr.gtbraccountshare;

import com.gtbr.gtbraccountshare.config.SpringConfiguration;
import com.gtbr.gtbraccountshare.listener.MessageListener;
import com.gtbr.gtbraccountshare.utils.SpringUtils;
import net.dv8tion.jda.api.JDABuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.security.auth.login.LoginException;

@SpringBootApplication
public class GtbrAccountShareApplication {

    public static void main(String[] args) throws LoginException {
        SpringApplication.run(GtbrAccountShareApplication.class, args);
        SpringConfiguration configuration = SpringUtils.getBean(SpringConfiguration.class);
        JDABuilder.createDefault(configuration.getToken())
                .addEventListeners(new MessageListener())
                .build();
    }

}