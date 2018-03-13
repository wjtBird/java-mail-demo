package com.example.common;

import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.enumeration.misc.ExchangeVersion;
import microsoft.exchange.webservices.data.credential.ExchangeCredentials;
import microsoft.exchange.webservices.data.credential.WebCredentials;

/**
 * 邮件公共的方法类
 */
public class ExchangeUtil {

    //公共的ExchangeService
    public ExchangeService getService(String account, String password) throws Exception {

        ExchangeService exchangeService = new ExchangeService(ExchangeVersion.Exchange2010_SP2);
        ExchangeCredentials credentials = new WebCredentials(account, password);
        exchangeService.setCredentials(credentials);
        exchangeService.autodiscoverUrl(account,(a)->(a.toLowerCase().startsWith("https://")));
        return exchangeService;

    }

}
