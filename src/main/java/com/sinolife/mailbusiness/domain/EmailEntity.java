package com.sinolife.mailbusiness.domain;

import java.util.List;

public class EmailEntity {
    //
    private String id;
    //发件人
    private String sender;
    //收件人
    private String addressee;
    //抄送人
    private String cc;
    //密送人
    private String bcc;
    //主题
    private String subject;
    //接收时间
    private String dateTimeReceived;
    //内容
    private String content;
    //附件
    List<EnclosureEntity> enclosureList;
    //是否已读
    private boolean isRead;


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

    public String getDateTimeReceived() {
        return dateTimeReceived;
    }

    public void setDateTimeReceived(String dateTimeReceived) {
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<EnclosureEntity> getEnclosureList() {
        return enclosureList;
    }

    public void setEnclosureList(List<EnclosureEntity> enclosureList) {
        this.enclosureList = enclosureList;
    }


}
