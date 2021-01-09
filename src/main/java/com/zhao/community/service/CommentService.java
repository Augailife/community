package com.zhao.community.service;

import com.zhao.community.exception.CustomizeErrorCode;
import com.zhao.community.exception.CustomizeException;
import com.zhao.community.model.Comment;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    public void insert(Comment comment){
        if(comment.getParentId()==null||comment.getParentId()==0){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
    }
}
