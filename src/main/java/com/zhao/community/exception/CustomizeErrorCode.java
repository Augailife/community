package com.zhao.community.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode{
    QUESTION_NOT_FOUND(2001,"您访问的路径跑丢了呢，换个路径试试吧~~"),
    PARENTCOMMENT_NOT_FOUND(2002,"没有选中评论，无法回复"),
    LOGIN_NOT(2003,"当前操作需要先进行登录哦~~"),
    SYS_ERROR(2004,"系统故障"),
    TYPE_PARAM_WRONG(2005,"类型选择错误"),
    COMMENT_NOTFOUND(2006,"评论没有找到哦，换个路径试试吧~~"),
    COMMENT_EXSIST(2007,"输入内容不能为空"),
    NOTIFICATION_NOTFOUND(2008,"最新回复没有找到"),
    ERROR_NOTIFICTIONUSER(2009,"兄弟，你随便查看别人信息不好吧")
    ;

    private String message;
    private Integer code;

    CustomizeErrorCode(Integer code, String message) {
//        在传参时会利用上面的参数注入，所以顺序不能错。
        this.message = message;
        this.code = code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public Integer getCode() {
        return code;
    }
    //使用构造方法+属性+get方法可以在以后调用此类的对象传入枚举值来查看
}
