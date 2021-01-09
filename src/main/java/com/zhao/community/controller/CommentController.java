package com.zhao.community.controller;

import com.zhao.community.dto.CommentDTO;
import com.zhao.community.dto.ResultDTO;
import com.zhao.community.exception.CustomizeErrorCode;
import com.zhao.community.mapper.CommentMapper;
import com.zhao.community.model.Comment;
import com.zhao.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@Controller
public class CommentController {
    @Autowired
    CommentMapper commentMapper;

    @ResponseBody
    @RequestMapping(value ="/comment",method = RequestMethod.POST)
public Object post(@RequestBody CommentDTO commentDTO,
                   HttpServletRequest httpServletRequest){
        User user=(User) httpServletRequest.getSession().getAttribute("user");
        if(user==null){
            return ResultDTO.errorOf(CustomizeErrorCode.LOGIN_NOT);
        }
        Comment comment=new Comment();
        comment.setCommentator(1);
        comment.setParentId(commentDTO.getParentId());
        comment.setType(commentDTO.getType());
        comment.setContent(commentDTO.getContent());
        comment.setCommentator(user.getId());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(System.currentTimeMillis());
        commentMapper.insert(comment);

        HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("message", "成功");
        return objectObjectHashMap;
}
}
