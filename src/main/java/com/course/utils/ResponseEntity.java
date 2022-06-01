package com.course.utils;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseEntity<T> {
    public static final int  SUCCESS_CODE = 200;
    public static final int NORMAL_ERROR_CODE = 500;
    public static final int  AUTHENTICATION_ERROR_CODE = 501;
    public static final int  VALIDATION_ERROR_CODE = 502;
    public static final int  FILE_ERROR_CODE = 503;

    private Boolean success;
    private String errMsg;
    private Integer errCode;
    private T data;

}
