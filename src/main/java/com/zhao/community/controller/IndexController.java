package com.zhao.community.controller;//在Application同级及以下的文件都可以在Application中运行

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller//允许这个类接收前端的请求
public class IndexController {
    @GetMapping("/")
    public String index(){
        return "index";

    }
}