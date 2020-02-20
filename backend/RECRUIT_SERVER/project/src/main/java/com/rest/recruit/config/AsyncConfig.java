package com.rest.recruit.config;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import java.util.concurrent.Executor;


/*
* recruit-async- 의 경우 최초 8번의 요청은 CorePoolSize에 설정한 쓰레드에 할당되고
* 쓰레드가 끝나기 전 추가 요청이 들어오면 MaxPoolSize에 설정한 사이즈 만큼 추가로 쓰레드가 생성되어 할당됩니다.
*
* 10개의 쓰레드가 모두 돌고있는 도중 추가 요청이 들어오게 되면
* QueueCapacity에서 설정한 사이즈 만큼 대기열로 들어가 처리를 기다리고,
* 돌고 있는 쓰레드가 종료되면 순차적으로 처리됩니다.
* */
@Configuration
@EnableAsync
public class AsyncConfig extends AsyncConfigurerSupport {
    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(8);//최초에 생성할 POOL 개수 , 기본 쓰레드 사이즈
        executor.setMaxPoolSize(10);//몇개까지 POOL을 생성할 것인지 , 최대 쓰레드 사이즈
        executor.setQueueCapacity(500);//ASYNC 처리시 대기 QUEUE SIZE, max쓰레드가 동작하는 경우 대기하는 queue 사이즈
        executor.setThreadNamePrefix("recruit-async-");
        executor.initialize();
        return executor;
    }


    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new SimpleAsyncUncaughtExceptionHandler();
    }


}