package com.example.demo.service;

import com.example.demo.entity.EmailEntity;
import com.example.utils.ExchangeUtil;
import com.example.exception.ParametersUnexpectedException;
import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.PropertySet;
import microsoft.exchange.webservices.data.core.enumeration.property.WellKnownFolderName;
import microsoft.exchange.webservices.data.core.service.folder.Folder;
import microsoft.exchange.webservices.data.core.service.item.EmailMessage;
import microsoft.exchange.webservices.data.core.service.item.Item;
import microsoft.exchange.webservices.data.property.complex.EmailAddress;
import microsoft.exchange.webservices.data.property.complex.MessageBody;
import microsoft.exchange.webservices.data.search.FindItemsResults;
import microsoft.exchange.webservices.data.search.ItemView;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
        if ( null != account && null != bccAccount && null != password){
            ExchangeUtil util = new ExchangeUtil();
            ExchangeService service = util.getService(account, password);
            EmailMessage message = new EmailMessage(service);
            message.setFrom(EmailAddress.getEmailAddressFromString(account));
            //收件人
            message.getToRecipients().add(EmailAddress.getEmailAddressFromString(account));
            //密送人
            message.getBccRecipients().add(EmailAddress.getEmailAddressFromString(account));
            //抄送人
            message.getCcRecipients().add(EmailAddress.getEmailAddressFromString(account));
            //主题
            message.setSubject("EWS API TEST");
            //内容
            message.setBody(MessageBody.getMessageBodyFromText("hi,this is a test"));
            message.send();
        }else {
            throw new ParametersUnexpectedException("发件人、收件人、密码不能为空");
        }


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

    /**
     * 查询出所有收到的邮箱
     * @param account
     * @param password
     * @return
     * @throws Exception
     */
    public List<EmailEntity> listItems(String account,String password) throws Exception {
        List<EmailEntity> list = new ArrayList<>();
        if (null != account && null != password){
            ExchangeUtil util = new ExchangeUtil();
            ExchangeService service = util.getService(account, password);
            Folder inbox = Folder.bind(service, WellKnownFolderName.Inbox);
            ItemView view = new ItemView(2);
            FindItemsResults<Item> findResults = service.findItems(inbox.getId(), view);
//        service.loadPropertiesForItems(findResults, PropertySet.FirstClassProperties);
            for (Item item : findResults) {
                EmailMessage message = EmailMessage.bind(service, item.getId());
                EmailEntity emailEntity = new EmailEntity();
                emailEntity.setSender(message.getSender().toString()); //发件人
                emailEntity.setIsRead(message.getIsRead()); //状态
                emailEntity.setSubject(message.getSubject()); //主题
                emailEntity.setDateTimeReceived(message.getDateTimeReceived()); //时间
                list.add(emailEntity);
                System.out.println(message.getSender());
            }
        }else {
            throw new ParametersUnexpectedException("邮箱，密码不能为空");
        }

        return list;
    }


    public EmailEntity readEmail(String account, String password, String id) throws Exception {
        if (null != account && null != password && null != id){
            ExchangeUtil util = new ExchangeUtil();
            ExchangeService service = util.getService(account, password);
            Folder inbox = Folder.bind(service, WellKnownFolderName.Inbox);
            ItemView view = new ItemView(100);
            FindItemsResults<Item> findResults = service.findItems(inbox.getId(), view);
            for (Item item : findResults) {
                if(item.getId().toString().equals(id)){
                    EmailMessage message = EmailMessage.bind(service, item.getId());
                    EmailEntity emailEntity = new EmailEntity();
                    emailEntity.setSender(message.getSender().toString()); //发件人
                    emailEntity.setIsRead(message.getIsRead()); //状态
                    emailEntity.setSubject(message.getSubject()); //主题
                    emailEntity.setDateTimeReceived(message.getDateTimeReceived()); //时间
                    emailEntity.setAddressee(message.getToRecipients().toString());
                    emailEntity.setBcc(message.getBccRecipients().toString());
                    emailEntity.setCc(message.getCcRecipients().toString());
                    emailEntity.setContent(message.getBody().toString());
                    return emailEntity;
                }
            }
        }else {
            throw new ParametersUnexpectedException("邮箱，密码，id不能为空");
        }
        return null;
    }



}
