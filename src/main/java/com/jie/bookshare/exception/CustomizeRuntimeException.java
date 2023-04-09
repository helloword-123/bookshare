package com.jie.bookshare.exception;

public class CustomizeRuntimeException extends RuntimeException{

    public CustomizeRuntimeException(String message){
        super(message);
    }

    // 关键
    @Override
    public Throwable fillInStackTrace() {
        return this;
    }
}
