package com.sinolife.mailbusiness.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by wjt on 2018/4/3.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SendEmailsServiceTest {
    @Autowired
    private SendEmailsService sendEmailsService;

    private String account = "liyong.wb@sino-life.com";
    private String bccAccount = "jintao.wang001@sino-life.com";
    private String password = "LY29436hhz";
    private String attachmentPath = "/Users/wjt/Downloads/1186020379987810.txt";
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

    //office转换成pdf
//    @Test
//    public void converter(){
//        String filePath = "E:\\workspace\\java-mail-demo\\files\\ActivityUtil类新增公共方法.docx";
//        DocConverter docConverter = new DocConverter(filePath);
//        docConverter.setFile(filePath);
//        docConverter.conver();
//    }

}