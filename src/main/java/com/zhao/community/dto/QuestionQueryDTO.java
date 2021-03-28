package com.zhao.community.dto;

import lombok.Data;

@Data
public class QuestionQueryDTO {
    String search;
    Integer size;
    Integer startPage;
}
