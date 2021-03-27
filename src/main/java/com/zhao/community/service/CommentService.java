package com.zhao.community.service;

import com.zhao.community.dto.CommentDTO;
import com.zhao.community.enums.CommentTypeEnum;
import com.zhao.community.enums.NotifictionEnum;
import com.zhao.community.enums.NotifictionStatusEnum;
import com.zhao.community.exception.CustomizeErrorCode;
import com.zhao.community.exception.CustomizeException;
import com.zhao.community.mapper.*;
import com.zhao.community.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired
    CommentMapper commentMapper;
    @Autowired
    QuestionMapper questionMapper;
    @Autowired
    QuestionExtMapper questionExtMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    CommentExtMapper commentExtMapper;
    @Autowired
    NotifictionMapper notifictionMapper;
    @Transactional
    public void insert(Comment comment, User commentator){
        if(comment.getParentId()==null||comment.getParentId()==0){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        if(comment.getType()==null|| !CommentTypeEnum.isExist(comment.getType())){
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }
        Comment comment1 = commentMapper.selectByPrimaryKey(comment.getParentId().longValue());
        if(comment.getType()==CommentTypeEnum.COMMENT.getType()){
            //回复评论
            CommentExample commentExample=new CommentExample();
            commentExample.createCriteria()
                    .andParentIdEqualTo(comment.getParentId());
            List<Comment> comments = commentMapper.selectByExample(commentExample);
            if(comments==null){
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOTFOUND);
            }else{
//            回复评论
                commentMapper.insert(comment);
                comment1.setCommentCount(1);
                commentExtMapper.incErComment(comment1);
                CreateNotifiction(comment,comment1.getParentId().longValue(), comment1.getCommentator(), commentator.getName(),comment1.getContent(),NotifictionEnum.NOTICE_PINGLUN);

            }
        }else{
            //回复问题
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            if(question==null){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }else{
                commentMapper.insert(comment);
                question.setCommentCount(1);
                questionExtMapper.incComment(question);
                CreateNotifiction(comment, question.getId().longValue(),question.getCreator(),commentator.getName(),question.getTitle(),NotifictionEnum.NOTICE_HUIFU);

            }

        }
    }
    public void CreateNotifiction(Comment comment,Long notifier,Integer reciever,String notifiername,
                                      String outertitle,NotifictionEnum notifictionEnum){
        if(comment.getCommentator()==reciever){
            return;
        }
        Notifiction notifiction=new Notifiction();
        notifiction.setGmtCreate(System.currentTimeMillis());
        notifiction.setType(notifictionEnum.getType());
        notifiction.setNotifier(notifier);
        notifiction.setOuterid(comment.getCommentator().longValue());
        notifiction.setStatus(NotifictionStatusEnum.NOREAD.getStatus());
        notifiction.setNotifiername(notifiername);
        notifiction.setOutertitle(outertitle);
        notifiction.setReciever(reciever.longValue());
        notifictionMapper.insert(notifiction);
    }

    public List<CommentDTO> list(Integer id,CommentTypeEnum commentTypeEnum) {
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria()
                .andParentIdEqualTo(id)
                .andTypeEqualTo(commentTypeEnum.getType());
        List<Comment> comments = commentMapper.selectByExample(commentExample);
        commentExample.setOrderByClause("gmt_create desc");
        if(comments.size()==0){
            return new ArrayList();
        }
        Set<Integer> commentators = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());

        List<Integer> userIds= new ArrayList<>();
        userIds.addAll(commentators);
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andIdIn(userIds);
        List<User> users = userMapper.selectByExample(userExample);

        //将List转化为map，方便下面将其注入到CommentDTO中去。map可以一次获取到，降低时间复杂度。
        Map<Integer, User> usercollect = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));

        List<CommentDTO> commentDTOS = comments.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment, commentDTO);
            commentDTO.setUser(usercollect.get(comment.getCommentator()));
            return commentDTO;
        }).collect(Collectors.toList());

        return commentDTOS;
    }
}
