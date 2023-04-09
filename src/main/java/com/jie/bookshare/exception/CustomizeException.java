package com.jie.bookshare.exception;

public class CustomizeException extends RuntimeException{

    private Integer code;

    public CustomizeException(Integer code, String message){
        super(message);
        this.code = code;
    }


}