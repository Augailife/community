package com.zhao.community.controller;

import com.zhao.community.dto.NotificationDTO;
import com.zhao.community.exception.CustomizeErrorCode;
import com.zhao.community.exception.CustomizeException;
import com.zhao.community.mapper.NotifictionMapper;
import com.zhao.community.model.Notifiction;
import com.zhao.community.model.User;
import com.zhao.community.service.NotifictionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

@Controller
public class NotificationController {
    @Autowired
    NotifictionMapper notifictionMapper;
    @Autowired
    NotifictionService notifictionService;
    @GetMapping("/notification/{id}")
    public String readNotification(
            HttpServletRequest request,
            @PathVariable("id") Long userid,
            Model model
    ){
        User user =(User) request.getSession().getAttribute("user");
        if(user==null){

            return "redirect:/";
        }
        Notifiction notifiction = notifictionMapper.selectByPrimaryKey(userid);
        if(notifiction==null){
            throw new CustomizeException(CustomizeErrorCode.NOTIFICATION_NOTFOUND);
        }
        if(notifiction.getReciever()!=user.getId().longValue()){
            throw  new CustomizeException(CustomizeErrorCode.ERROR_NOTIFICTIONUSER);
        }
        NotificationDTO notificationDTO=notifictionService.read(notifiction);
        if("评论了".equals(notificationDTO.getType())||"回复了".equals(notificationDTO.getType())){
            return "redirect:/question/"+notificationDTO.getNotifier();
        }else {
            return "redirect:/";
        }

    }
}
