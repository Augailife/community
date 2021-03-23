package com.zhao.community.enums;

public enum NotifictionStatusEnum {
    READ(1),
    NOREAD(2)
    ;
    int status;

    NotifictionStatusEnum(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
