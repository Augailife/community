package com.zhao.community.service;

import com.zhao.community.enums.CommentTypeEnum;
import com.zhao.community.exception.CustomizeErrorCode;
import com.zhao.community.exception.CustomizeException;
import com.zhao.community.mapper.CommentMapper;
import com.zhao.community.mapper.QuestionExtMapper;
import com.zhao.community.mapper.QuestionMapper;
import com.zhao.community.model.Comment;
import com.zhao.community.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    @Autowired
    CommentMapper commentMapper;
    @Autowired
    QuestionMapper questionMapper;
    @Autowired
    QuestionExtMapper questionExtMapper;
    public void insert(Comment comment){
        if(comment.getParentId()==null||comment.getParentId()==0){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        if(comment.getType()==null|| !CommentTypeEnum.isExist(comment.getType())){
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }
        if(comment.getType()==CommentTypeEnum.COMMENT.getType()){
            //回复评论
            if(commentMapper.selectByPrimaryKey(comment.getId())==null){
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOTFOUND);
            }else{
                commentMapper.insert(comment);
            }
        }else{
            //回复问题
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            if(question==null){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }else{
                commentMapper.insert(comment);
                question.setCommentCount(1);
                questionExtMapper.incComment(question);
            }
        }
    }
}
