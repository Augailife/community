package com.zhao.community.exception;

public class CustomizeException extends RuntimeException {
    private String message;
    public CustomizeException(CustomizeErrorCode customizeErrorCode) {
     this.message=customizeErrorCode.getMessage();
    }

    @Override
    public String getMessage() {
        return message;
    }
}
