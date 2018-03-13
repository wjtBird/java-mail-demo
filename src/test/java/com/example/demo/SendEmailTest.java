package com.example.demo;

import com.example.demo.service.SendEmailsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SendEmailTest {

    @Autowired
    private SendEmailsService sendEmailsService;

    private String account = "liyong.wb@sino-life.com";
    private String bccAccount = "jintao.wang001@sino-life.com";
    private String password = "LY29436hhz";

    private String attachmentPath = "D:\\test.txt";

//    private String account = "1669844851@qq.com";
//    private String bccAccount = "2963765982@qq.com";
//    private String password = "ly404551378wq";

    //普通邮件
    @Test
    public void sendMsgTest() throws Exception {
        this.sendEmailsService.sendEmail(this.account, this.bccAccount,this.password);
    }

    //带附件的邮件
    @Test
    public void sendEmailFileTest() throws Exception {
        this.sendEmailsService.sendEmailFile(this.account, this.password, this.attachmentPath);
    }


}
