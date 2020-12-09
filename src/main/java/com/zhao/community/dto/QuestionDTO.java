package com.zhao.community.dto;

import lombok.Data;

@Data
public class QuestionDTO {
    private Integer id;
    private String title;
    private String buchong;
    private String tag;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer creator;
    private Integer viewCount;
    private Integer likeCount;
    private Integer commentCount;
    private String touXiang;
}
