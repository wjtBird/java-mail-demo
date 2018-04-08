package com.sinolife.mailbusiness.service;

/**
 * Created by wjt on 2018/4/3.
 */
public interface ReceiveMailService {

    void receive(String account, String password) throws Exception;

}
