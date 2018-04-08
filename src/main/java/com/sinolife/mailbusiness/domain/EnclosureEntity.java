package com.sinolife.mailbusiness.domain;

/**
 * 附件实体类
 */
public class EnclosureEntity {
    //附件名称
    private String fileName;
    //附件地址
    private String appendixAddress;


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
