package com.zhao.community.dto;

import com.zhao.community.model.User;
import lombok.Data;

@Data
public class NotificationDTO {
    private Long id;
    private Long gmtCreate;
    private Integer status;
    private String notifiername;
    private String outertitle;
    private String type;

}
