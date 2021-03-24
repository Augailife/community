package com.zhao.community.controller;

import com.zhao.community.dto.NotificationDTO;
import com.zhao.community.dto.PageDTO;
import com.zhao.community.mapper.UserMapper;
import com.zhao.community.model.Notifiction;
import com.zhao.community.model.User;
import com.zhao.community.service.NotifictionService;
import com.zhao.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class profileController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private NotifictionService notifictionService;

    @GetMapping("/profile/{action}")
    public String profile(
            HttpServletRequest servletRequest,
            @PathVariable(name = "action") String action,
            Model model,
            @RequestParam(name = "page",defaultValue = "1") Integer page,
            @RequestParam(name = "size",defaultValue = "5") Integer size
    ){
        User user=(User)servletRequest.getSession().getAttribute("user");
        if(user==null){
            return "redirect:/";
        }
        if("questions".equals(action)){
            model.addAttribute("section","questions");
            model.addAttribute("sectionName","我的提问");
            PageDTO list = questionService.list(user.getId(),page,size);
            model.addAttribute("PageYe",list);

        }else if("replies".equals(action)){
            model.addAttribute("section","replies");
            model.addAttribute("sectionName","我的回复");
            Long unreadCount=notifictionService.getUnreadCount(user.getId());
            model.addAttribute("unreadCount",unreadCount);
            PageDTO pageDTO= notifictionService.List(page, size, user.getId());
            model.addAttribute("PageYe",pageDTO);
        }
        return "profile";
    }
}
