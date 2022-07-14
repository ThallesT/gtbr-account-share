package com.gtbr.gtbraccountshare.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Configuration
@Getter
public class SpringConfiguration {
    @Value("${discord.token}")
    private String token;
}
