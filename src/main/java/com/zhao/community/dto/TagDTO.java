package com.zhao.community.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
public class TagDTO {
    String kuNames;
    List<String> tags;
}
