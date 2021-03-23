package com.zhao.community.controller;

import com.zhao.community.dto.CommentCreateDTO;
import com.zhao.community.dto.CommentDTO;
import com.zhao.community.dto.ResultDTO;
import com.zhao.community.enums.CommentTypeEnum;
import com.zhao.community.exception.CustomizeErrorCode;
import com.zhao.community.model.Comment;
import com.zhao.community.model.User;
import com.zhao.community.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
        if(StringUtils.isBlank(commentCreateDTO.getContent())){

            return ResultDTO.errorOf(CustomizeErrorCode.COMMENT_EXSIST);
        }
        Comment comment=new Comment();
        comment.setCommentator(1);
        comment.setParentId(commentCreateDTO.getParentId());
        comment.setType(commentCreateDTO.getType());
        comment.setContent(commentCreateDTO.getContent());
        comment.setCommentator(user.getId());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(System.currentTimeMillis());
        commentServicer.insert(comment,user);
//        HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
//        objectObjectHashMap.put("message", "成功");
        return ResultDTO.okOf();
}

@ResponseBody
@RequestMapping(value = "/comment/{id}",method = RequestMethod.GET)
    public ResultDTO<List<CommentDTO>> getErComment(
            @PathVariable("id") Integer id
){
    List<CommentDTO> comments = commentServicer.list(id, CommentTypeEnum.COMMENT);
    ResultDTO<List<CommentDTO>> resultDTO = ResultDTO.okOf(comments);
    return resultDTO;
}
}
