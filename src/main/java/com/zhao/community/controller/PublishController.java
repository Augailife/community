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
            @RequestParam(value = "title",required = false) String title,
            @RequestParam(value = "buchong",required = false) String buchong,
            @RequestParam(value = "tag",required = false) String tag,
            HttpServletRequest servletRequest,
            Model model
    ){
        model.addAttribute("title",title);
        model.addAttribute("buchong",buchong);
        model.addAttribute("tag",tag);
        if(title==null||title==""){
            model.addAttribute("error","标题不能为空");
            return "publish";
        }
        if(buchong==null||buchong==""){
            model.addAttribute("error","补充不能为空");
            return "publish";
        }
        if(tag==null||tag==""){
            model.addAttribute("error","标签不能为空");
            return "publish";
        }
        User user =null;
        Cookie[] cookies = servletRequest.getCookies();
        if(cookies!=null && cookies.length!=0)
        for(Cookie cookie:cookies) {
            if (cookie.getName().equals("token")) {
                String token = cookie.getValue();
//                System.out.println(token + "ppPPPP");//Springboot只有完全成功了System.out.println的信息才会被打印下来。
                user = userMapper.findByToken(token);
                if (user != null) {
                    servletRequest.getSession().setAttribute("user", user);
                }
                break;
            }
        }
            if(user==null){
                model.addAttribute("error","用户未登录");
                return "publish";
            }
            else{
               Question question=new Question();
               question.setTitle(title);
               question.setTag(tag);
               question.setBuchong(buchong);
               question.setGmtCreate(System.currentTimeMillis());
               question.setGmtModified(question.getGmtCreate());
               question.setCreator(Integer.parseInt(user.getAccountId()));
               questionMapper.Insert(question);
            }



        return "redirect:/";
    }
}
