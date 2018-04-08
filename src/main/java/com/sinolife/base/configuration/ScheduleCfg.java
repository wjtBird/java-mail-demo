package com.sinolife.base.configuration;

import com.sinolife.base.factories.ExchangeServiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * Created by wjt on 2018/4/2.
 * @author wjt
 */
@Configuration
@EnableScheduling
public class ScheduleCfg {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ExchangeServiceFactory serviceFactory;

    /**
     *
     */
    @Scheduled(cron = "0 0/10 * * * ?") // 每10分钟执行一次
    public void getToken() {
        serviceFactory.clearCache();
    }
}
