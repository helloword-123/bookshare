package com.jie.bookshare.exception;

public class CustomizeException extends Exception{

    public CustomizeException(String message){
        super(message);
    }

    // 关键
    @Override
    public Throwable fillInStackTrace() {
        return this;
    }

}