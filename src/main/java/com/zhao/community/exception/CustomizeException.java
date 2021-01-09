package com.zhao.community.exception;

public class CustomizeException extends RuntimeException {
    private String message;
    private Integer code;
    public CustomizeException(CustomizeErrorCode customizeErrorCode) {
     this.message=customizeErrorCode.getMessage();
     this.code=customizeErrorCode.getCode();
    }

    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }
}
