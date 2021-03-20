package com.zhao.community.mapper;

import com.zhao.community.model.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface CommentExtMapper {
    int incErComment(Comment comment);
}
