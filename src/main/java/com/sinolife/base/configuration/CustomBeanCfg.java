package com.sinolife.base.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolExecutorFactoryBean;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by wjt on 2018/4/8.
 * @author wjt
 */
@Configuration
public class CustomBeanCfg {

    @Bean
    public ThreadPoolExecutor executorService() {
        ThreadPoolExecutorFactoryBean factoryBean = new ThreadPoolExecutorFactoryBean();
        factoryBean.setMaxPoolSize(10);
        factoryBean.setQueueCapacity(300);
        factoryBean.initialize();
        return (ThreadPoolExecutor) factoryBean.getObject();

    }
}
