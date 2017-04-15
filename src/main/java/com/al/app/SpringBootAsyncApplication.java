package com.al.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author lalith
 *
 */
@EnableAutoConfiguration
@ComponentScan("com.al.app.*")
public class SpringBootAsyncApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringBootAsyncApplication.class, args);
	}
}
