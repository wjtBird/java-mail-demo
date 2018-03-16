package com.example.demo.entity;

/**
 * 附件实体类
 */
public class EnclosureEntity {

    private String fileName; //附件名称
    private String appendixAddress; //附件地址

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getAppendixAddress() {
        return appendixAddress;
    }

    public void setAppendixAddress(String appendixAddress) {
        this.appendixAddress = appendixAddress;
    }
}
