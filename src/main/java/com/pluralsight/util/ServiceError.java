package com.pluralsight.util;

import javax.xml.ws.Service;

public class ServiceError {

    private int code;
    private String message;

    public ServiceError() {

    }

    public ServiceError(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
