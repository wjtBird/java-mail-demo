package com.sinolife.base.exception;

import java.io.Serializable;

/**
 * 不符合期望参数异常
 * Created by wjt on 13/03/2018.
 * @author wjt
 */
public class UnexpectedParametersException extends RuntimeException implements Serializable {

    private String majorCode;
    private String subCode;
    private String message;
    private String info = null;

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

    @Override
    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UnexpectedParametersException(Throwable cause) {
        super(cause);
    }

    public UnexpectedParametersException(String msg) {
        super(msg);
        this.message = msg;
    }

    public UnexpectedParametersException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public UnexpectedParametersException(String majorCode, String detailCode, String message, Throwable cause) {
        super(message + " 错误码[" + majorCode + "-" + detailCode + "]", cause);
        this.majorCode = majorCode;
        this.subCode = detailCode;
        this.message = message + " 错误码[" + majorCode + "-" + detailCode + "]";
        this.info = message;
    }

    public UnexpectedParametersException(String majorCode, String detailCode, String message) {
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
