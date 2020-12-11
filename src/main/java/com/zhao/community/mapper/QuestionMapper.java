package com.zhao.community.mapper;

import com.zhao.community.dto.QuestionDTO;
import com.zhao.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper @Component
public interface QuestionMapper {
    @Insert("insert into question(title,buchong,gmt_create,gmt_modified,creator,tag) values(#{title},#{buchong},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    void Insert(Question question);
    @Select("select * from question limit #{startpage},#{size}")
    List<Question> list(@Param("startpage") Integer startpage, Integer size);
    @Select("select count(*) from question")
    Integer count();
}
