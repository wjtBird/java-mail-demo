package com.example.utils;

import com.example.exception.ParametersUnexpectedException;
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
        if( null != account && null != password ){
            ExchangeService exchangeService = new ExchangeService(ExchangeVersion.Exchange2010_SP2);
            ExchangeCredentials credentials = new WebCredentials(account, password);
            exchangeService.setCredentials(credentials);
            exchangeService.autodiscoverUrl(account,(a)->(a.toLowerCase().startsWith("https://")));
            return exchangeService;
        }else {
            throw new ParametersUnexpectedException("发件人和邮箱密码不能为空");
        }
    }

}
