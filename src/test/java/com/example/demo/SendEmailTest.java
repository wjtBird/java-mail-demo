package com.example.demo;

import com.example.demo.service.AccessService;
import com.example.demo.service.SendEmailsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.servlet.http.HttpServletRequest;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SendEmailTest {

    @Autowired
    private SendEmailsService sendEmailsService;

    private String account = "liyong.wb@sino-life.com";
    private String bccAccount = "jintao.wang001@sino-life.com";
    private String password = "LY29436hhz";
    private String attachmentPath = "D:\\test.txt";
    private String id = "";

    //查询所收到的邮件
    @Test
    public void listFirstTenItemsTest() throws Exception {
        this.sendEmailsService.listItems(this.account,this.password);
    }

//    @Test
//    public void readMsgTest() throws Exception {
//        this.sendEmailsService.readEmail(this.account,this.password,this.id);
//    }

    //普通邮件
//    @Test
//    public void sendMsgTest() throws Exception {
//        this.sendEmailsService.sendEmail(this.account, this.bccAccount,this.password);
//    }

    //发送带附件的邮件
    @Test
    public void sendEmailFileTest() throws Exception {
        this.sendEmailsService.sendEmailFile(this.account, this.password, this.attachmentPath);
    }

    //查询带附件的邮件
    @Test
    public void readFileMail() throws Exception {
        this.sendEmailsService.readFileMail(this.account, this.password);
    }

    //查询带附件的邮件
    @Test
    public void readFileMails() throws Exception {
        this.sendEmailsService.readFileMails(this.account, this.password);
    }


}
