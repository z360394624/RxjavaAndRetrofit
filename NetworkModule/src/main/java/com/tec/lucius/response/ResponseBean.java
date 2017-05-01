package com.tec.lucius.response;

/**
 * Created by z3603 on 2017/5/1.
 */

public class ResponseBean<T> {

    private String httpStatus;

    private String httpMessage;

    private T data;

    public String getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(String httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getHttpMessage() {
        return httpMessage;
    }

    public void setHttpMessage(String httpMessage) {
        this.httpMessage = httpMessage;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
