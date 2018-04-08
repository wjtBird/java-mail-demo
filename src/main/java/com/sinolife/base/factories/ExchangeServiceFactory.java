package com.sinolife.base.factories;

import com.sinolife.base.exception.UnexpectedParametersException;
import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.enumeration.misc.ExchangeVersion;
import microsoft.exchange.webservices.data.credential.ExchangeCredentials;
import microsoft.exchange.webservices.data.credential.WebCredentials;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 邮件公共的方法类
 * @author wjt
 */
@Component
public class ExchangeServiceFactory {

    private static Logger logger = LoggerFactory.getLogger(ExchangeServiceFactory.class);
    private  Map<String, ExchangeService> exchangeServiceMap = new ConcurrentHashMap<>();
    /**
     *
     * @param account
     * @param password
     * @return
     * @throws Exception
     */
    public  ExchangeService getService(String account, String password){
        if (account == null || password == null) {
            throw new UnexpectedParametersException("发件人和邮箱密码不能为空");
        }
        ExchangeService cacheService = exchangeServiceMap.get(account);

        if (cacheService == null) {
            this.syncRegistrationService(account,password);
        }
        return exchangeServiceMap.get(account);
    }

    @Async
    public void asyncRegistrationService(String accout, String password) {
        this.registrationService(accout, password);
    }

    public void syncRegistrationService(String account, String password) {
        this.registrationService(account, password);
    }

    private void registrationService(String account, String password) {
        long startTime = Instant.now().toEpochMilli();
        logger.info("registrationService,method start time:{}", startTime);
        if (account == null || password == null) {
            throw new UnexpectedParametersException("发件人和邮箱密码不能为空");
        }
        ExchangeService exchangeService = new ExchangeService(ExchangeVersion.Exchange2010_SP2);
        ExchangeCredentials credentials = new WebCredentials(account, password);
        exchangeService.setCredentials(credentials);
        try {
            exchangeService.autodiscoverUrl(account, (a) -> (a.toLowerCase().startsWith("https://")));
        } catch (Exception e) {
            logger.error("自动发现服务异常",e);
            throw new UnexpectedParametersException("自动发现服务异常");
        }
        //TODO 缓存清理机制，或者放入Session
        String mapKey = account;
        this.exchangeServiceMap.put(mapKey, exchangeService);
        long endTime = Instant.now().toEpochMilli();
        logger.info("registrationService,method end time:{},registrationService used :{}", endTime, endTime - startTime);
    }

        /**
         *
         * 缓存清理
         */
    public  void clearCache() {
        this.exchangeServiceMap.clear();
    }

}
