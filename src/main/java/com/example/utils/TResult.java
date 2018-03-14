package com.example.utils;

import java.io.Serializable;

/**
 *
 * Created by ly on 2018/3/14.
 */
public class TResult<T extends Object> implements Serializable {
    // 错误码
    private int code;
    // 提示信息
    private String msg;
    // 返回值
    private T data = null;

    public TResult(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        if (msg == null) {
            return "";
        }
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TResult<?> tResult = (TResult<?>) o;

        if (code != tResult.code) return false;
        if (msg != null ? !msg.equals(tResult.msg) : tResult.msg != null) return false;
        return data != null ? data.equals(tResult.data) : tResult.data == null;
    }

    @Override
    public int hashCode() {
        int result = code;
        result = 31 * result + (msg != null ? msg.hashCode() : 0);
        result = 31 * result + (data != null ? data.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TResult{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
