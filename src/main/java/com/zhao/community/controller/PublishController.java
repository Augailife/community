package com.zhao.community.controller;

import com.zhao.community.mapper.QuestionMapper;
import com.zhao.community.mapper.UserMapper;
import com.zhao.community.model.Question;
import com.zhao.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {
    @Autowired
    UserMapper userMapper;
    @Autowired
    QuestionMapper questionMapper;
    @GetMapping("/publish")
    public String publish(){

        return "publish";
    }
    @PostMapping("/publish")
    public String doPublish(
            @RequestParam("title") String title,
            @RequestParam("buchong") String buchong,
            @RequestParam("tag") String tag,
            HttpServletRequest servletRequest,
            Model model
    ){
        User user =null;
        Cookie[] cookies = servletRequest.getCookies();
        for(Cookie cookie:cookies){
            if(cookie.getName().equals("token")){
                String token=cookie.getValue();
                user = userMapper.findByToken(token);
                if(user !=null){
                    servletRequest.getSession().setAttribute("user", user);
                }
                break;
            }
            if(user==null){
                model.addAttribute("error","用户未登录");
                return "publish";
            }else{
               Question question=new Question();
               question.setTitle(title);
               question.setTag(tag);
               question.setBuchong(buchong);
               question.setGmtCreate(System.currentTimeMillis());
               question.setGmtModify(question.getGmtCreate());
               question.setCreator(user.getId());
               questionMapper.Insert(question);
            }


        }
        return "redirect:/";
    }
}
