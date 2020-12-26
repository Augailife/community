package com.zhao.community.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode{
    QUESTION_NOT_FOUND("您访问的路径跑丢了呢，换个路径试试吧~~");
    private String message;

    CustomizeErrorCode(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
    //使用构造方法+属性+get方法可以在以后调用此类的对象传入枚举值来查看
}
