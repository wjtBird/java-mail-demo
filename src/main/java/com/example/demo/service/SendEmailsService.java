package com.example.demo.service;

import com.example.demo.entity.EmailEntity;
import com.example.exception.ParametersUnexpectedException;
import com.example.utils.ExchangeUtil;
import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.PropertySet;
import microsoft.exchange.webservices.data.core.enumeration.property.WellKnownFolderName;
import microsoft.exchange.webservices.data.core.service.folder.Folder;
import microsoft.exchange.webservices.data.core.service.item.EmailMessage;
import microsoft.exchange.webservices.data.core.service.item.Item;
import microsoft.exchange.webservices.data.property.complex.AttachmentCollection;
import microsoft.exchange.webservices.data.property.complex.EmailAddress;
import microsoft.exchange.webservices.data.property.complex.MessageBody;
import microsoft.exchange.webservices.data.search.FindItemsResults;
import microsoft.exchange.webservices.data.search.ItemView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 发送邮件Service
 */
@Service
public class SendEmailsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SendEmailsService.class);

    private static final SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 发送普通形式邮件
     * @param account 发件人
     * @param password 密码
     * @throws Exception
     */
    public void sendEmail(String account,String password,EmailEntity emailEntity) throws Exception {
        if (null != emailEntity){
            if ( null != account && null != emailEntity.getAddressee()
                    && null != password){
                ExchangeUtil util = new ExchangeUtil();
                ExchangeService service = util.getService(account, password);
                EmailMessage message = new EmailMessage(service);
                message.setFrom(EmailAddress.getEmailAddressFromString(account));
                //收件人
                message.getToRecipients().add(EmailAddress.getEmailAddressFromString(emailEntity.getAddressee()));
                //密送人
//            message.getBccRecipients().add(EmailAddress.getEmailAddressFromString(account));
                //抄送人
                message.getCcRecipients().add(EmailAddress.getEmailAddressFromString(emailEntity.getCc()));
                //主题
                message.setSubject(emailEntity.getSubject());
                //内容
                message.setBody(MessageBody.getMessageBodyFromText(emailEntity.getContent()));
                message.send();
            }else {
                throw new ParametersUnexpectedException("发件人、收件人、密码不能为空");
            }
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
            if(null != findResults){
              service.loadPropertiesForItems(findResults, PropertySet.FirstClassProperties);
                for (Item item : findResults) {
                    EmailMessage message = EmailMessage.bind(service, item.getId());
                    EmailEntity emailEntity = new EmailEntity();
                    emailEntity.setSender(message.getSender().getName()); //发件人
                    emailEntity.setIsRead(message.getIsRead()); //状态
                    emailEntity.setSubject(message.getSubject()); //主题
                    emailEntity.setDateTimeReceived(dateFormater.format(message.getDateTimeReceived())); //时间
                    emailEntity.setId(item.getId().toString());
                    emailEntity.setAddressee(message.getToRecipients().getItems().toString());
                    emailEntity.setBcc(message.getBccRecipients().getItems().toString());
                    emailEntity.setCc(message.getCcRecipients().getItems().toString());
                    System.out.println(message.getBody().toString());
//                    AttachmentCollection attachments = message.getAttachments();//附件
//                    attachments.save();

//                    System.out.println(attachments.getItems().get(0).getName());
//                    System.out.println(attachments.getPropertyAtIndex(0));
//                    System.out.println(attachments.getItems().get(0).getContentLocation());
//                    System.out.println(attachments.getPropertyAtIndex(0).getContentLocation());
//                    System.out.println(attachments.getPropertyAtIndex(0).getContentType());
//                    System.out.println(attachments.getPropertyAtIndex(0).getContentId());
                    list.add(emailEntity);
                }
            }
        }else {
            throw new ParametersUnexpectedException("邮箱，密码不能为空");
        }
        return list;
    }

    /**
     * 邮箱的详情
     * @param account
     * @param password
     * @param id
     * @return
     * @throws Exception
     */
    public EmailEntity readEmail(String account, String password, String id) throws Exception {
        EmailEntity emailEntity = new EmailEntity();
        if (null != account && null != password && null != id){
            ExchangeUtil util = new ExchangeUtil();
            ExchangeService service = util.getService(account, password);
            Folder inbox = Folder.bind(service, WellKnownFolderName.Inbox);
            ItemView view = new ItemView(100);
            FindItemsResults<Item> findResults = service.findItems(inbox.getId(), view);
            if(null != findResults){
                for (Item item : findResults) {
                    if(id.equals(item.getId().toString())){
                        EmailMessage message = EmailMessage.bind(service, item.getId());
                        if (!message.getIsRead()) {
                            message.setIsRead(true);
                            message.send();
                        }
                        emailEntity.setSender(message.getSender().toString()); //发件人
                        emailEntity.setIsRead(message.getIsRead()); //状态
                        emailEntity.setSubject(message.getSubject()); //主题
                        emailEntity.setDateTimeReceived(dateFormater.format(message.getDateTimeReceived())); //时间
                        emailEntity.setAddressee(message.getToRecipients().getItems().toString());
                        emailEntity.setBcc(message.getBccRecipients().getItems().toString());
                        emailEntity.setCc(message.getCcRecipients().getItems().toString());
                        emailEntity.setContent(message.getBody().toString());//内容
                        LOGGER.info(message.getSender().toString());
                    }
                }
            }
        }else {
            throw new ParametersUnexpectedException("邮箱，密码，id不能为空");
        }
        return emailEntity;
    }

}
