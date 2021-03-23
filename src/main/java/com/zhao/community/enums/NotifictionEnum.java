package com.zhao.community.enums;

public enum NotifictionEnum {
    NOTICE_PINGLUN(1,"评论了"),
    NOTICE_HUIFU(1,"回复了")
    ;
    int type;
    String notice;
    NotifictionEnum(int type, String notice) {
        this.type = type;
        this.notice = notice;
    }

    public String getNotice() {
        return notice;
    }

    public int getType() {
        return type;
    }
    public static String typeToGetName(int type){
        for (NotifictionEnum notifictionEnum : NotifictionEnum.values()) {
            if(notifictionEnum.getType()==type){
                return notifictionEnum.getNotice();
            }
        }
        return "";
    }
}
