package com.al.app;

import java.util.concurrent.Executor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * @author lalith
 *
 */
@EnableAutoConfiguration
@ComponentScan("com.al.app.*")
@EnableAsync
public class SpringBootAsyncApplication extends AsyncConfigurerSupport {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootAsyncApplication.class, args);
    }

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(2);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("SampleAsync-");
        executor.initialize();
        return executor;
    }
}
