package com.micro.namecard;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
public class ApplicationConfiguration {
    @Value(value = "${bulk.data.page.size}")
    private long pageSize;

    @Value(value = "${data.generator.limit}")
    private int syncLimit;

    @Bean
    public Executor asyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(5);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("name-card-job-");
        executor.initialize();
        return executor;
    }

    public long getPageSize(){
        return this.pageSize;
    }

    public int getSyncLimit() {
        return this.syncLimit;
    }
}
