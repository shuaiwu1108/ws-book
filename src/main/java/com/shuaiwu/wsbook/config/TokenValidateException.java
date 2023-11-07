package com.shuaiwu.wsbook.config;

public class TokenValidateException extends RuntimeException{


    public TokenValidateException(String message) {
        super(message);
    }

    public TokenValidateException(String message, Throwable cause) {
        super(message, cause);
    }
}
