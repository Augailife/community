package com.zhao.community.mapper;

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
}