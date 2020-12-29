package com.zhao.community.controller;

import com.zhao.community.dto.QuestionDTO;
import com.zhao.community.service.QuestionService;
import com.zhao.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller

public class QuestionController {

    @Autowired
    QuestionService questionService;
    @GetMapping("/question/{id}")
    public String question(
            @PathVariable("id") Integer id,
            Model model
    ){
        questionService.calView(id);
        QuestionDTO questionDTO=questionService.getById(id);
        model.addAttribute("questionDTO",questionDTO);
        return "question";
    }
}
