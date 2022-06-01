package com.course.utils;

public class NostackException extends RuntimeException{
    public NostackException(String msg) {
        super(msg,null,false,false);
    }
}

