package com.zhao.community.mapper;

import com.zhao.community.dto.QuestionQueryDTO;
import com.zhao.community.model.Comment;
import com.zhao.community.model.Question;
import com.zhao.community.model.QuestionExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface QuestionExtMapper {
    int incView(Question record);
    int incComment(Question commentCount);
    List<Question> regexpTag(Question question);
    Integer searchCount(QuestionQueryDTO queryDTO);
    List<Question>  searchQuestion(QuestionQueryDTO queryDTO);
}