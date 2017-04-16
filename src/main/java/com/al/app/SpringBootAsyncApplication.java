package com.al.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * @author lalith
 *
 */
@EnableAutoConfiguration
@ComponentScan("com.al.app.*")
@EnableAsync
@EnableScheduling
public class SpringBootAsyncApplication extends AsyncConfigurerSupport {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootAsyncApplication.class, args);
    }

    @Override
    public TaskExecutor getAsyncExecutor() {
        System.out.println("***************** Creating the threadpool task executor");
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(5);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("SampleAsync-");
        executor.initialize();
        return executor;
    }
}
