package com.zhao.community.controller;

import com.zhao.community.dto.CommentCreateDTO;
import com.zhao.community.dto.ResultDTO;
import com.zhao.community.exception.CustomizeErrorCode;
import com.zhao.community.model.Comment;
import com.zhao.community.model.User;
import com.zhao.community.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CommentController {
    @Autowired
    CommentService commentServicer;

    @ResponseBody
    @RequestMapping(value ="/comment",method = RequestMethod.POST)
public Object post(@RequestBody CommentCreateDTO commentCreateDTO,
                   HttpServletRequest httpServletRequest){
        User user=(User) httpServletRequest.getSession().getAttribute("user");
        if(user==null){
            return ResultDTO.errorOf(CustomizeErrorCode.LOGIN_NOT);
        }
        Comment comment=new Comment();
        comment.setCommentator(1);
        comment.setParentId(commentCreateDTO.getParentId());
        comment.setType(commentCreateDTO.getType());
        comment.setContent(commentCreateDTO.getContent());
        comment.setCommentator(user.getId());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(System.currentTimeMillis());
        commentServicer.insert(comment);
//        HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
//        objectObjectHashMap.put("message", "成功");
        return ResultDTO.okOf();
}
}
