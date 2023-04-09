package com.jie.bookshare.exception;

public class CustomizeRuntimeException extends RuntimeException{

    private Integer code;

    public CustomizeRuntimeException(Integer code, String message){
        super(message);
        this.code = code;
    }


}
