package com.svulinovic.cityapi.exception;

public class ExceptionInfo {

    public String message;

    public ExceptionInfo(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
