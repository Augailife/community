package com.zhao.community.dto;

import com.zhao.community.model.User;
import lombok.Data;

@Data
public class CommentDTO {
    private Long id;

    private Integer parentId;
    private Integer type;

    private Integer commentator;

    private Long gmtCreate;

    private Long gmtModified;

    private String content;
    private User user;

}
