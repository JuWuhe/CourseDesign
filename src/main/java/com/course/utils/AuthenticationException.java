package com.course.utils;

public class AuthenticationException extends RuntimeException{
    public AuthenticationException() {
        this("验证有误");
    }
    public AuthenticationException(String msg) {
        super(msg,null,true,true);
    }
}

