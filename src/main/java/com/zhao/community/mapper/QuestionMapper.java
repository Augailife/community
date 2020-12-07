package com.zhao.community.mapper;

import com.zhao.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper @Component
public interface QuestionMapper {
    @Insert("insert into question(id,title,buchong,gmt_create,gmt_modify,creator,tag) values(#{title},#{buchong},#{gmtCreate},#{gmtModify},#{tag})")
    void Insert(Question question);
}
