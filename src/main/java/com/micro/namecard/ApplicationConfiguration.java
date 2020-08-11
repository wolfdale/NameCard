package com.micro.namecard;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class ApplicationConfiguration {

    @Bean(name= "taskExecutor")
    public ThreadPoolTaskExecutor taskExecutor() {
        return new ThreadPoolTaskExecutor();
    }
}
