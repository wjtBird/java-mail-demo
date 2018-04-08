package com.sinolife.base.domain;

import java.io.Serializable;


/**
 * 页面统一处理返回结果对象
 * @param <T>
 */
public class ResultBean<T> implements Serializable {


    private static final int SUCCESS = 0;
    private static final int FAIL = 1;
    private static final int NO_PERMISSION = 2;

    private String msg = "success";

    private int code = SUCCESS;

    private T data;

    public ResultBean() {
        super();
    }

    public ResultBean(String msg) {
        super();
        this.msg = msg;
    }
    public ResultBean(T data) {
        super();
        this.data = data;
    }

    public ResultBean(Throwable throwable) {
        super();
        this.msg = throwable.getMessage();
        this.code = FAIL;
    }

    public static int getSUCCESS() {
        return SUCCESS;
    }

    public static int getFAIL() {
        return FAIL;
    }

    public static int getNoPermission() {
        return NO_PERMISSION;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
