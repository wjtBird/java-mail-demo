package com.sinolife.base.factories;

import com.sinolife.base.exception.UnexpectedParametersException;
import com.sun.mail.util.MailSSLSocketFactory;
import groovy.util.logging.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.mail.Session;
import java.security.GeneralSecurityException;
import java.time.Instant;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by wjt on 2018/4/3.
 * @author wjt
 */
@Component
@Slf4j
public class MailSessionFactory {

    private Logger logger = LoggerFactory.getLogger(getClass());
    private Map<String, Session> sessionMap = new ConcurrentHashMap<>();



    /**
     *
     * @param account
     * @param password
     * @return
     * @throws Exception
     */
    public Session getSession(String account, String password){
        if (account == null || password == null) {
            throw new UnexpectedParametersException("发件人和邮箱密码不能为空");
        }
        Session cacheSession = this.sessionMap.get(account);
        if (cacheSession == null) {
            this.syncRegistrationSession(account,password);
        }

        return this.sessionMap.get(account);
    }

    @Async
    public void asyncRegistrationSession(String accout, String password) {
        this.registrationSmtpSession(accout, password);
    }

    public void syncRegistrationSession(String account, String password) {
        this.registrationSmtpSession(account, password);
    }

    private void registrationSmtpSession(String account, String password) {
        long startTime = Instant.now().toEpochMilli();
        logger.info("registrationSmtpSession,method start time:{}", startTime);
        if (account == null || password == null) {
            throw new UnexpectedParametersException("发件人和邮箱密码不能为空");
        }
        Properties props = new Properties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.host", "smtp.sino-life.com");
        props.put("mail.smtp.auth", "true");

        try {
            MailSSLSocketFactory sslSocketFactory = new MailSSLSocketFactory();
            sslSocketFactory.setTrustAllHosts(true);
            props.put("mail.smtp.socketFactory.class", sslSocketFactory);
        } catch (GeneralSecurityException e) {
            ;
        }
        props.put("mail.smtp.port", 993);
        props.put("mail.smtp.starttls.enable", true);
        props.put("mail.smtp.ssl.trust", "*");

        // 普通邮箱验证
        this.sessionMap.put(account, Session.getInstance(props));
        long endTime = Instant.now().toEpochMilli();
        logger.info("registrationSmtpSession,method end time:{},registrationSmtpSession used :{}", endTime, endTime - startTime);
    }

    private void registrationImapSession(String account, String password) {

    }



    /**
     *
     * 缓存清理
     */
    public  void clearCache() {
        this.sessionMap.clear();
    }




}
