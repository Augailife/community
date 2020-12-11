package com.zhao.community.controller;//在Application同级及以下的文件都可以在Application中运行

import com.zhao.community.dto.PageDTO;
import com.zhao.community.mapper.UserMapper;
import com.zhao.community.model.User;
import com.zhao.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller//允许这个类接收前端的请求
public class IndexController {
    @Autowired
    UserMapper userMapper;
    @Autowired
    QuestionService questionService;
    @GetMapping("/")
    public String index(HttpServletRequest servletRequest,
                        Model model,
                        @RequestParam(name = "page",defaultValue = "1") Integer page,
                        @RequestParam(name = "size",defaultValue = "5") Integer size
        ){
        Cookie[] cookies = servletRequest.getCookies();
        if(cookies!=null && cookies.length!=0)
        for(Cookie cookie:cookies){
        if(cookie.getName().equals("token")){
        String token=cookie.getValue();
        User user = userMapper.findByToken(token);
        if(user !=null){
        servletRequest.getSession().setAttribute("user", user);
        }
        break;
        }
        }
        PageDTO list = questionService.list(page,size);
        model.addAttribute("pageYe",list);
        return "index";
        }
        }