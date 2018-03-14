package com.example.demo.entity;

import java.util.Date;

public class EmailEntity {

    private String sender; //发件人
    private String addressee; //收件人
    private String cc; //抄送人
    private String bcc; //密送人
    private String subject; //主题
    private Date dateTimeReceived; //接收时间
    private String content; //内容
    private boolean isRead; //是否已读

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Date getDateTimeReceived() {
        return dateTimeReceived;
    }

    public void setDateTimeReceived(Date dateTimeReceived) {
        this.dateTimeReceived = dateTimeReceived;
    }

    public boolean getIsRead() {
        return isRead;
    }

    public void setIsRead(boolean isRead) {
        this.isRead = isRead;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAddressee() {
        return addressee;
    }

    public void setAddressee(String addressee) {
        this.addressee = addressee;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getBcc() {
        return bcc;
    }

    public void setBcc(String bcc) {
        this.bcc = bcc;
    }
}
