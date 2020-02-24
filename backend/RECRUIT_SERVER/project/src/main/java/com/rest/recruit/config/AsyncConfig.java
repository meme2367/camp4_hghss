package com.rest.recruit.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;


/*
* recruit-async- 의 경우 최초 8번의 요청은 CorePoolSize에 설정한 쓰레드에 할당되고
* 쓰레드가 끝나기 전 추가 요청이 들어오면 MaxPoolSize에 설정한 사이즈 만큼 추가로 쓰레드가 생성되어 할당됩니다.
*
* 10개의 쓰레드가 모두 돌고있는 도중 추가 요청이 들어오게 되면
* QueueCapacity에서 설정한 사이즈 만큼 대기열로 들어가 처리를 기다리고,
* 돌고 있는 쓰레드가 종료되면 순차적으로 처리됩니다.
* */
@Slf4j
@Configuration
@EnableAsync
public class AsyncConfig {

    @Bean(name = "threadPoolTaskExecutor", destroyMethod = "destroy")
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(50);//최초에 생성할 POOL 개수 , 기본 쓰레드 사이즈
        executor.setMaxPoolSize(100);//몇개까지 POOL을 생성할 것인지 , 최대 쓰레드 사이즈
        executor.setQueueCapacity(1000);//ASYNC 처리시 대기 QUEUE SIZE, max쓰레드가 동작하는 경우 대기하는 queue 사이즈
        executor.setWaitForTasksToCompleteOnShutdown(true);//// queue 대기열 및 task 가 완료된 이후에 shutdown 여부
        //executor.setAllowCoreThreadTimeOut(true);// core thread 를 유휴시간 (keepAliveSeconds)이 지나서 kill 할 지 여부,
        //executor.setAwaitTerminationSeconds(30);// core thread 를 유지할 timeout (초) 지정, 기본값 60(초), setAllowCoreThreadTimeOut 이 true 일 경우에만 작동한다.
        executor.setThreadNamePrefix("recruit-async-");
        executor.initialize();
        return new HandlingExecutor(executor);
    }

/*
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new SimpleAsyncUncaughtExceptionHandler();
    }
*/


    public class HandlingExecutor implements AsyncTaskExecutor {
        private AsyncTaskExecutor executor;

        public HandlingExecutor(AsyncTaskExecutor executor) {
            this.executor = executor;
        }

        @Override
        public void execute(Runnable task) {
            executor.execute(createWrappedRunnable(task));
        }

        @Override
        public void execute(Runnable task, long startTimeout) {
            executor.execute(createWrappedRunnable(task), startTimeout);
        }

        @Override
        public Future<?> submit(Runnable task) {
            return executor.submit(createWrappedRunnable(task));
        }

        @Override
        public <T> Future<T> submit(final Callable<T> task) {
            return executor.submit(createCallable(task));//반환
        }

        private <T> Callable<T> createCallable(final Callable<T> task) {
            return new Callable<T>() {
                @Override
                public T call() throws Exception {
                    try {
                        return task.call();
                    } catch (Exception ex) {
                        handle(ex);
                        throw ex;
                    }
                }
            };
        }

        private Runnable createWrappedRunnable(final Runnable task) {
            return new Runnable() {
                @Override
                public void run() {
                    try {
                        task.run();
                    } catch (Exception ex) {
                        handle(ex);
                    }
                }
            };
        }

        private void handle(Exception ex) {
            log.info("Failed to execute task. : {}", ex.getMessage());
            log.error("Failed to execute task. ",ex);
        }

        public void destroy() {
            if(executor instanceof ThreadPoolTaskExecutor){
                ((ThreadPoolTaskExecutor) executor).shutdown();
            }
        }
    }

}
