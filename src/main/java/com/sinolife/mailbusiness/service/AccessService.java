package com.sinolife.mailbusiness.service;

import com.sinolife.base.factories.ExchangeServiceFactory;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccessService {



//    public ExchangeService getService(String account,String password) throws Exception {
//        ExchangeService exchangeService = new ExchangeService(ExchangeVersion.Exchange2010_SP2);
//        ExchangeCredentials credentials = new WebCredentials(account, password);
//        exchangeService.setCredentials(credentials);
//        exchangeService.autodiscoverUrl(account,(a)->(a.toLowerCase().startsWith("https://")));
//        return exchangeService;
//    }

    @Autowired
    private ExchangeServiceFactory serviceFactory;


    public void listFirstTenItems(String account,String password) throws Exception {
        ExchangeService service = this.serviceFactory.getService(account, password);
        Folder inbox = Folder.bind(service, WellKnownFolderName.Inbox);
        ItemView view = new ItemView(10);
        FindItemsResults<Item> findResults = service.findItems(inbox.getId(), view);

        //MOOOOOOST IMPORTANT: load messages' properties before
        service.loadPropertiesForItems(findResults, PropertySet.FirstClassProperties);

        for (Item item : findResults.getItems()) {


            // Do something with the item as shown
            System.out.println("id==========" + item.getId());
            System.out.println("sub==========" + item.getSubject());
        }
    }

    public void readMeg(String account,String password) throws Exception {
        ExchangeService service = this.serviceFactory.getService(account, password);
        Folder inbox = Folder.bind(service, WellKnownFolderName.Inbox);
        ItemView view = new ItemView(10);
        FindItemsResults<Item> findResults = service.findItems(inbox.getId(), view);

        for (Item item : findResults) {
            EmailMessage message = (EmailMessage) item;
            String subject = message.getSubject();
            System.out.println("isread:"+message.getIsRead());
            if ("send user by ews api".equals(subject)) {
                System.out.println("send user by ews api is read");
                message.setIsRead(true);
                message.send();
            }
            System.out.println("subject："+message.getSubject());
            System.out.println("from:"+message.getFrom());
//            System.out.println("msgBody:"+message.getBody().getBodyType());
//            System.out.println(item.getBody());
        }


    }


    public void sendMsg(String account,String password) throws Exception {
        ExchangeService service = this.serviceFactory.getService(account, password);

        EmailMessage message = new EmailMessage(service);

        message.setFrom(EmailAddress.getEmailAddressFromString(account));
        message.getToRecipients().add(EmailAddress.getEmailAddressFromString(account));
        message.setSubject("send user by ews api");
        message.setBody(MessageBody.getMessageBodyFromText("hi,this is a bug"));

        message.send();

    }



}
