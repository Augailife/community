package com.zhao.community.service;

import com.zhao.community.dto.PageDTO;
import com.zhao.community.dto.QuestionDTO;
import com.zhao.community.exception.CustomizeErrorCode;
import com.zhao.community.exception.CustomizeException;
import com.zhao.community.mapper.QuestionExtMapper;
import com.zhao.community.mapper.QuestionMapper;
import com.zhao.community.mapper.UserMapper;
import com.zhao.community.model.Question;
import com.zhao.community.model.QuestionExample;
import com.zhao.community.model.User;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Component
public class QuestionService {
    @Autowired
    QuestionExtMapper questionExtMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionMapper questionMapper;

    public PageDTO list(Integer page,Integer size){
        PageDTO pageDTO=new PageDTO();
        List<QuestionDTO> questionDTOS=new ArrayList<>();
        QuestionExample questionExample = new QuestionExample();

        Integer count =(int) questionMapper.countByExample(questionExample);
        pageDTO.fenYe(page,size,count);

        Integer startpage=(page-1)*size;

        List<Question> questions=questionMapper.selectByExampleWithRowbounds(new QuestionExample(),new RowBounds(startpage,size));

        for (Question question : questions) {
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO=new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);//使用BeanUtils工具将question注入questionDTO中；
            questionDTO.setUser(user);//写javabean对象时，尽量往大里写。
            questionDTOS.add(questionDTO);
        }
        pageDTO.setQuestionDTOS(questionDTOS);

        return pageDTO;

    }

    public PageDTO list(Integer userId, Integer page, Integer size) {
        PageDTO pageDTO=new PageDTO();
        List<QuestionDTO> questionDTOS=new ArrayList<>();
        QuestionExample questionExample = new QuestionExample();
        Integer count = (int)questionMapper.countByExample(questionExample);
        pageDTO.fenYe(page,size,count);
        Integer startpage=(page-1)*size;

        List<Question> questions=questionMapper.selectByExampleWithRowbounds(new QuestionExample(),new RowBounds(startpage,size));

        for (Question question : questions) {
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO=new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);//使用BeanUtils工具将question注入questionDTO中；
            questionDTO.setUser(user);//写javabean对象时，尽量往大里写。
            questionDTOS.add(questionDTO);
        }
        pageDTO.setQuestionDTOS(questionDTOS);

        return pageDTO;
    }

    public QuestionDTO getById(Integer id) {
        Question question=questionMapper.selectByPrimaryKey(id);
        if(question==null){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        QuestionDTO questionDTO=new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        User user=userMapper.selectByPrimaryKey(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }

    public void calView(Integer id) {
        Question question = new Question();
        question.setId(id);
        question.setViewCount(1);
        questionExtMapper.incView(question);
    }
}
