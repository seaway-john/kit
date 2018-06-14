package com.seaway.kit.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.concurrent.ScheduledThreadPoolExecutor;

@Configuration
@EnableAsync
@EnableScheduling
public class TaskThreadPoolExecutor {

    @Value("${kit.threadpool.corepoolsize}")
    private int corePoolSize;

    @Value("${kit.threadpool.maxpoolsize}")
    private int maxPoolSize;

    @Bean
    public ScheduledThreadPoolExecutor kitScheduledPool() {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(corePoolSize);
        executor.setMaximumPoolSize(maxPoolSize);

        return executor;
    }

}
