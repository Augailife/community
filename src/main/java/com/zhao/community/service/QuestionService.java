package com.zhao.community.service;

import com.zhao.community.dto.QuestionDTO;
import com.zhao.community.mapper.QuestionMapper;
import com.zhao.community.mapper.UserMapper;
import com.zhao.community.model.Question;
import com.zhao.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionMapper questionMapper;

    public List<QuestionDTO> list(){
        List<Question> questions=questionMapper.list();
        List<QuestionDTO> questionDTOS=new ArrayList<>();
        for (Question question : questions) {
            User user = userMapper.findByAccountId(question.getCreator());
            QuestionDTO questionDTO=new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);//使用BeanUtils工具将question注入questionDTO中；
            questionDTO.setUser(user);//写javabean对象时，尽量往大里写。
            questionDTOS.add(questionDTO);
        }
        return questionDTOS;

    }

}
