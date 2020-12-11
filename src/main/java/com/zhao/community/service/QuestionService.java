package com.zhao.community.service;

import com.zhao.community.dto.PageDTO;
import com.zhao.community.dto.QuestionDTO;
import com.zhao.community.mapper.QuestionMapper;
import com.zhao.community.mapper.UserMapper;
import com.zhao.community.model.Question;
import com.zhao.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionMapper questionMapper;

    public PageDTO list(Integer page,Integer size){
        PageDTO pageDTO=new PageDTO();
        List<QuestionDTO> questionDTOS=new ArrayList<>();
        Integer startpage=(page-1)*size;
        Integer count = questionMapper.count();
        pageDTO.fenYe(page,size,count);
        if(page<1){
            page=1;
        }

        if(page>pageDTO.getTotalPage()){
            page=pageDTO.getTotalPage();
        }

        List<Question> questions=questionMapper.list(startpage,size);

        for (Question question : questions) {
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO=new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);//使用BeanUtils工具将question注入questionDTO中；
            questionDTO.setUser(user);//写javabean对象时，尽量往大里写。
            questionDTOS.add(questionDTO);
        }
        pageDTO.setQuestionDTOS(questionDTOS);

        return pageDTO;

    }

}
