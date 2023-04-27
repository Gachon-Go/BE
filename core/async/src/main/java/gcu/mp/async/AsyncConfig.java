package gcu.mp.async;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfig implements AsyncConfigurer {
    @Override
    @Bean(name= "mailExecutor")
    public Executor getAsyncExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //기본 스레드 설정 2개
        executor.setCorePoolSize(2);
        //최대 스레드 설정 5개
        executor.setMaxPoolSize(5);
        //스레드 전부 사용 중일시 큐에 대기
        executor.setQueueCapacity(10);
        executor.setThreadNamePrefix("Async MailExecutor");
        executor.initialize();
        return executor;
    }
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler(){
        return AsyncConfigurer.super.getAsyncUncaughtExceptionHandler();
    }
}
