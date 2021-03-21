package com.zhao.community.controller;

import com.zhao.community.dto.CommentDTO;
import com.zhao.community.dto.QuestionDTO;
import com.zhao.community.enums.CommentTypeEnum;
import com.zhao.community.service.CommentService;
import com.zhao.community.service.QuestionService;
import com.zhao.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller

public class QuestionController {

    @Autowired
    QuestionService questionService;
    @Autowired
    CommentService commentService;
    @GetMapping("/question/{id}")
    public String question(
            @PathVariable("id") Integer id,
            Model model
    ){
        QuestionDTO questionDTO=questionService.getById(id);
        List<CommentDTO> commentDTOS=commentService.list(id, CommentTypeEnum.QUESTION);
        List<QuestionDTO> questionDTOS = questionService.selectTag(questionDTO);

        questionService.calView(id);
        model.addAttribute("questionDTO",questionDTO);
        model.addAttribute("commentDTOs",commentDTOS);
        model.addAttribute("relatedQuestions", questionDTOS);
        return "question";
    }
}
