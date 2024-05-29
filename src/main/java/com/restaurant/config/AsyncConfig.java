package com.restaurant.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfig {
  public final String ASYNC_EXECUTOR = "asyncExecutor";
  public final int CORE_POOL_SIZE = 3;

  @Bean(name = ASYNC_EXECUTOR)
  public Executor asyncExecutor() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(CORE_POOL_SIZE);
    executor.setMaxPoolSize(CORE_POOL_SIZE);
    executor.setQueueCapacity(500);
    executor.setThreadNamePrefix("Async-");
    executor.initialize();
    return executor;
  }

}
