package com.zhao.community.controller;

import com.zhao.community.dto.CommentDTO;
import com.zhao.community.mapper.CommentMapper;
import com.zhao.community.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CommentController {
    @Autowired
    CommentMapper commentMapper;

    @ResponseBody
    @RequestMapping(value ="/comment",method = RequestMethod.POST)
public Object post(@RequestBody CommentDTO commentDTO){
        Comment comment=new Comment();
        comment.setCommentator(1);
        comment.setParentId(commentDTO.getParentId());
        comment.setType(commentDTO.getType());
        comment.setContent(commentDTO.getContent());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(System.currentTimeMillis());
        commentMapper.insert(comment);

    return null;
}
}
