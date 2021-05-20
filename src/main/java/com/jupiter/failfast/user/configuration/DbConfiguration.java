package com.jupiter.failfast.user.configuration;

import com.jupiter.failfast.user.entity.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class DbConfiguration {

    @Bean
    public Map<String, User> getUserDb() {
        return new ConcurrentHashMap<>();
    }
}
