package com.example.springmvc.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class ThreadConfig {
    @Bean
    public Executor asyncMailExecutor() {
//        ThreadPoolTaskExecutor executor = new SimpleAsyncTaskExecutor();
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(10);
        executor.setKeepAliveSeconds(120);
        executor.setThreadNamePrefix("MailAsync-");
        executor.initialize();
        return executor;

    }
}
