package com.zhao.community.model;

import lombok.Data;

@Data
public class Question {
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


}
