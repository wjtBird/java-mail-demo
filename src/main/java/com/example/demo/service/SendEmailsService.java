package com.example.demo.service;

import com.example.common.ExchangeUtil;
import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.service.item.EmailMessage;
import microsoft.exchange.webservices.data.property.complex.EmailAddress;
import microsoft.exchange.webservices.data.property.complex.MessageBody;
import org.springframework.stereotype.Service;

/**
 * 发送邮件Service
 */
@Service
public class SendEmailsService {

    /**
     * 发送普通形式邮件
     * @param account 发件人
     * @param bccAccount 密送人
     * @param password 密码
     * @throws Exception
     */
    public void sendEmail(String account,String bccAccount,String password) throws Exception {

        ExchangeUtil util = new ExchangeUtil();
        ExchangeService service = util.getService(account, password);
        EmailMessage message = new EmailMessage(service);
        message.setFrom(EmailAddress.getEmailAddressFromString(account));
        //收件人
        message.getToRecipients().add(EmailAddress.getEmailAddressFromString(bccAccount));
        //密送人
        message.getBccRecipients().add(EmailAddress.getEmailAddressFromString(account));
        //抄送人
//        message.getCcRecipients().add(EmailAddress.getEmailAddressFromString(bccAccount));
        //主题
        message.setSubject("EWS API TEST");
        //内容
        message.setBody(MessageBody.getMessageBodyFromText("hi,this is a test"));

        message.send();

    }

    /**
     * 发送带附件的邮件
     * @param account 发件人
     * @param password 密码
     * @param attachmentPath 附件地址
     * @throws Exception
     */
    public void sendEmailFile(String account,String password,String attachmentPath) throws Exception {

        ExchangeUtil util = new ExchangeUtil();
        ExchangeService service = util.getService(account, password);
        EmailMessage message = new EmailMessage(service);
        message.setFrom(EmailAddress.getEmailAddressFromString(account));
        //收件人
        message.getToRecipients().add(EmailAddress.getEmailAddressFromString(account));
        //主题
        message.setSubject("EWS API TEST");
        //内容
        message.setBody(MessageBody.getMessageBodyFromText("hi,this is a test"));
        //附件地址
        if (attachmentPath != null) {
            message.getAttachments().addFileAttachment(attachmentPath);
        }
        message.send();

    }




}
