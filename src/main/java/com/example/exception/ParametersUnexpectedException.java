package com.example.exception;

import java.io.Serializable;

/**
 * 不符合期望参数异常
 * Created by wjt on 13/03/2018.
 */
public class ParametersUnexpectedException extends RuntimeException implements Serializable {

    String majorCode;
    String subCode;
    String message;
    String info = null;

    public String getMajorCode() {
        return this.majorCode;
    }

    public void setMajorCode(String majorCode) {
        this.majorCode = majorCode;
    }

    public String getSubCode() {
        return this.subCode;
    }

    public void setSubCode(String subCode) {
        this.subCode = subCode;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ParametersUnexpectedException(Throwable cause) {
        super(cause);
    }

    public ParametersUnexpectedException(String msg) {
        super(msg);
        this.message = msg;
    }

    public ParametersUnexpectedException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public ParametersUnexpectedException(String majorCode, String detailCode, String message, Throwable cause) {
        super(message + " 错误码[" + majorCode + "-" + detailCode + "]", cause);
        this.majorCode = majorCode;
        this.subCode = detailCode;
        this.message = message + " 错误码[" + majorCode + "-" + detailCode + "]";
        this.info = message;
    }

    public ParametersUnexpectedException(String majorCode, String detailCode, String message) {
        super(message + " 错误码[" + majorCode + "-" + detailCode + "]");
        this.majorCode = majorCode;
        this.subCode = detailCode;
        this.info = message;
        this.message = message + " 错误码[" + majorCode + "-" + detailCode + "]";
    }

    public String getInfo() {
        return this.info;
    }

}
