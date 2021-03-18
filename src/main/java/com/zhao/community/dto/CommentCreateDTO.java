package com.zhao.community.dto;

import lombok.Data;

@Data
public class CommentCreateDTO {
    private String content;
    private Integer ParentId;
    private Integer type;

}
