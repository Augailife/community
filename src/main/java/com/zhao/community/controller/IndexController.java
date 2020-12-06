package com.zhao.community.controller;//在Application同级及以下的文件都可以在Application中运行

import com.zhao.community.mapper.UserMapper;
import com.zhao.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller//允许这个类接收前端的请求
public class IndexController {
    @Autowired
    UserMapper userMapper;
    @GetMapping("/")
    public String index(HttpServletRequest servletRequest){
        Cookie[] cookies = servletRequest.getCookies();
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
        return "index";
    }
}