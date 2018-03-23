package com.example.demo.service;

import com.example.demo.entity.EmailEntity;
import com.example.demo.entity.EnclosureEntity;
import com.example.exception.ParametersUnexpectedException;
import com.example.utils.ExchangeUtil;
import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.PropertySet;
import microsoft.exchange.webservices.data.core.enumeration.property.BasePropertySet;
import microsoft.exchange.webservices.data.core.enumeration.property.WellKnownFolderName;
import microsoft.exchange.webservices.data.core.service.folder.Folder;
import microsoft.exchange.webservices.data.core.service.item.EmailMessage;
import microsoft.exchange.webservices.data.core.service.item.Item;
import microsoft.exchange.webservices.data.core.service.schema.EmailMessageSchema;
import microsoft.exchange.webservices.data.core.service.schema.ItemSchema;
import microsoft.exchange.webservices.data.property.complex.*;
import microsoft.exchange.webservices.data.search.FindItemsResults;
import microsoft.exchange.webservices.data.search.ItemView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 发送邮件Service
 */
@Service
public class SendEmailsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SendEmailsService.class);

    private static final SimpleDateFormat dateFormater = new SimpleDateFormat("MM-dd");

    /**
     * 发送带附件的邮件
     * @param account 发件人
     * @param password 密码
     * @throws Exception
     */
    public void sendEmail(String account,String password,EmailEntity emailEntity,List<String> attachmentPaths) throws Exception {
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
                //附件地址
                if (attachmentPaths != null && attachmentPaths.size()>0) {
                    for (String attachmentPath:attachmentPaths){
                        message.getAttachments().addFileAttachment(attachmentPath);
                    }
                }
                message.send();
            }else {
                throw new ParametersUnexpectedException("发件人、收件人、密码不能为空");
            }
        }
    }

    /**
     * 发送带附件的邮件 测试例子
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
            Folder inbox = Folder.bind(service, WellKnownFolderName.Inbox);// 收件箱
//            Folder outbox = Folder.bind(service, WellKnownFolderName.Outbox);// 发件箱
//            Folder deletedItems = Folder.bind(service, WellKnownFolderName.DeletedItems);// 已删除邮件
//            Folder junkEmail = Folder.bind(service, WellKnownFolderName.JunkEmail);// 垃圾邮件
//            Folder sentItems = Folder.bind(service,WellKnownFolderName.SentItems);// 已发送邮件
//            Folder drafts = Folder.bind(service,WellKnownFolderName.Drafts);// 草稿
            ItemView view = new ItemView(Integer.MAX_VALUE);
            FindItemsResults<Item> findResults = service.findItems(inbox.getId(), view);
            if(null != findResults){
                service.loadPropertiesForItems(findResults, PropertySet.FirstClassProperties);
                for (Item item : findResults) {
                    //收件人
                    String toRecipients = "";
                    //密送人
                    String bccRecipients = "";
                    //抄送人
                    String ccRecipients = "";
                    EmailMessage message = EmailMessage.bind(service, item.getId());
                    EmailEntity emailEntity = new EmailEntity();
                    emailEntity.setSender(message.getSender().getName()); //发件人
                    emailEntity.setIsRead(message.getIsRead()); //状态
                    emailEntity.setSubject(message.getSubject()); //主题
                    emailEntity.setDateTimeReceived(dateFormater.format(message.getDateTimeReceived())); //时间
                    emailEntity.setId(item.getId().toString()); //
                    for (EmailAddress to:message.getToRecipients().getItems()){
                        if ("".equals(toRecipients)){
                            toRecipients = to.getName();
                        }else {
                            toRecipients += ";"+to.getName();
                        }
                    }
                    for (EmailAddress bc:message.getBccRecipients().getItems()){
                        if ("".equals(bccRecipients)){
                            bccRecipients = bc.getName();
                        }else {
                            bccRecipients += ";"+bc.getName();
                        }
                    }
                    System.out.println(message.getCcRecipients().getItems().toString());
                    for (EmailAddress cc:message.getCcRecipients().getItems()){
                        if ("".equals(ccRecipients)){
                            ccRecipients = cc.getName();
                        }else {
                            ccRecipients += ";"+cc.getName();
                        }
                    }
                    for (Attachment attachment:message.getAttachments()) {
                        if (attachment instanceof FileAttachment){
                            FileAttachment fileAttachment = (FileAttachment)attachment;
                            // 创建文件
                            File saveDir = new File("./files/" + fileAttachment.getName());
                            //保存附件
                            if (!saveDir.getParentFile().exists()) saveDir.getParentFile().mkdirs();
                            fileAttachment.load("./files/" + fileAttachment.getName());
                        }else {
                            ItemAttachment itemAttachment = (ItemAttachment)attachment;
                            itemAttachment.load();
                        }
                    }
                    emailEntity.setAddressee(toRecipients);
                    emailEntity.setBcc(bccRecipients);
                    emailEntity.setCc(ccRecipients);
                    LOGGER.info(message.getSender().getName());
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
    public EmailEntity readEmail(String account, String password, String id, String realPath) throws Exception {
        EmailEntity emailEntity = new EmailEntity();
        if (null != account && null != password && null != id){
            ExchangeUtil util = new ExchangeUtil();
            ExchangeService service = util.getService(account, password);
            Folder inbox = Folder.bind(service, WellKnownFolderName.Inbox);
            ItemView view = new ItemView(Integer.MAX_VALUE);
            FindItemsResults<Item> findResults = service.findItems(inbox.getId(), view);
            service.loadPropertiesForItems(findResults,
                    new PropertySet(BasePropertySet.FirstClassProperties, EmailMessageSchema.Attachments));
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
                        List<EnclosureEntity> entityList = new ArrayList<>();
                        for (Attachment attachment:message.getAttachments()) {
                            EnclosureEntity enclosureEntity = new EnclosureEntity();
                            if (attachment instanceof FileAttachment){
                                FileAttachment fileAttachment = (FileAttachment)attachment;
                                enclosureEntity.setFileName(fileAttachment.getName());
                                realPath = realPath+"files/"+fileAttachment.getName();
                                enclosureEntity.setAppendixAddress(realPath);
                            }else {
                                ItemAttachment itemAttachment = (ItemAttachment)attachment;
                                itemAttachment.load();
                            }
                            entityList.add(enclosureEntity);
                        }
                        emailEntity.setEnclosureList(entityList);
                        LOGGER.info(message.getSender().toString());
                    }
                }
            }
        }else {
            throw new ParametersUnexpectedException("邮箱，密码，id不能为空");
        }
        return emailEntity;
    }

    /**
     * 查询带附件的邮件
     * @param account
     * @param password
     */
    public void readFileMail(String account, String password) throws Exception{
        ExchangeUtil util = new ExchangeUtil();
        ExchangeService service = util.getService(account, password);
        ItemView view = new ItemView(1);
        Folder folder = Folder.bind(service, WellKnownFolderName.Inbox);
        FindItemsResults<Item> results = service.findItems(folder.getId(),view);
        service.loadPropertiesForItems(results,
                new PropertySet(BasePropertySet.FirstClassProperties, EmailMessageSchema.Attachments));
        for (Item item : results) {
            Item itm = Item.bind(service, item.getId(), new PropertySet(BasePropertySet.FirstClassProperties, EmailMessageSchema.Attachments));
//            EmailMessage emailMessage = EmailMessage.bind(service, itm.getId(), new PropertySet(BasePropertySet.FirstClassProperties, EmailMessageSchema.Attachments));
            // Bind to an existing message item and retrieve the attachments collection.
            // This method results in an GetItem call to EWS.
            EmailMessage message = EmailMessage.bind(service, itm.getId(), new PropertySet(ItemSchema.Attachments));

            // Iterate through the attachments collection and load each attachment.
            for (Attachment attachment:message.getAttachments()) {
                if (attachment instanceof FileAttachment){
                    FileAttachment fileAttachment = (FileAttachment)attachment;
                    // Load the attachment into a file.
                    // This call results in a GetAttachment call to EWS.
                    fileAttachment.load("./src/main/resources/public/" + fileAttachment.getName());
                    LOGGER.info("文件名称===="+ fileAttachment.getName());
                }else{// Attachment is an item attachment.
                    ItemAttachment itemAttachment = (ItemAttachment)attachment;
                    // Load attachment into memory and write out the subject.
                    // This does not save the file like it does with a file attachment.
                    // This call results in a GetAttachment call to EWS.
                    itemAttachment.load();
                    LOGGER.info("文件名称===="+ itemAttachment.getName());
                }
            }
        }

    }

    /**
     * 查询带附件的邮件并且返回流
     * @param account
     * @param password
     */
    public void readFileMails(String account, String password) throws Exception{
        ExchangeUtil util = new ExchangeUtil();
        ExchangeService service = util.getService(account, password);
        ItemView view = new ItemView(1);
        FindItemsResults<Item> findResults = service.findItems(WellKnownFolderName.Inbox, view);
        for(Item item:findResults.getItems()){
            System.out.println(item.getSubject());
            item.load();
            if(item.getHasAttachments()) {
                for (Attachment attachment:item.getAttachments()) {
                    FileAttachment fileAttachment = (FileAttachment)attachment;
                    File fileStream = new File(fileAttachment.getName());
                    OutputStream out = new FileOutputStream(fileStream);
                    fileAttachment.load();
                    out.flush();
                }
            }
        }

    }




}
