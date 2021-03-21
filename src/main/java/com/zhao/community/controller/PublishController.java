package com.zhao.community.controller;

import com.zhao.community.cache.TagCache;
import com.zhao.community.exception.CustomizeErrorCode;
import com.zhao.community.exception.CustomizeException;
import com.zhao.community.mapper.QuestionMapper;
import com.zhao.community.model.Question;
import com.zhao.community.model.QuestionExample;
import com.zhao.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {

    @Autowired
    QuestionMapper questionMapper;
    @GetMapping("/publish/{id}")
    public String edit(
            @PathVariable(value = "id") Integer id,
            Model model
    ){
        Question question = questionMapper.selectByPrimaryKey(id);
        model.addAttribute("title",question.getTitle());
        model.addAttribute("buchong",question.getBuchong());
        model.addAttribute("tag",question.getTag());
        model.addAttribute("id",question.getId());
        model.addAttribute("tags", TagCache.getTags());
        return "publish";
    }
    @GetMapping("/publish")
    public String publish(Model model){
        model.addAttribute("tags", TagCache.getTags());

        return "publish";
    }
    @PostMapping("/publish")
    public String doPublish(
            @RequestParam(value = "title",required = false) String title,
            @RequestParam(value = "buchong",required = false) String buchong,
            @RequestParam(value = "tag",required = false) String tag,
            @RequestParam(value = "id",required = false) Integer id,
            HttpServletRequest servletRequest,
            Model model
    ){
        model.addAttribute("title",title);
        model.addAttribute("buchong",buchong);
        model.addAttribute("tag",tag);
        model.addAttribute("tags", TagCache.getTags());
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
        User user=(User)servletRequest.getSession().getAttribute("user");
        if(user==null){
            return "redirect:/";
        }

            if(user==null){
                model.addAttribute("error","用户未登录");
                return "publish";
            }
            else{
                if(id==null) {
                    Question question = new Question();
                    question.setTitle(title);
                    question.setTag(tag);
                    question.setBuchong(buchong);
                    question.setViewCount(0);
                    question.setCommentCount(0);
                    question.setLikeCount(0);
                    question.setGmtCreate(System.currentTimeMillis());
                    question.setGmtModified(question.getGmtCreate());
                    question.setCreator(user.getId());
                    questionMapper.insert(question);
                    return "redirect:/";
                }else{
                    Question question = new Question();
                    question.setId(id);
                    question.setTitle(title);
                    question.setTag(tag);
                    question.setBuchong(buchong);
                    question.setGmtModified(System.currentTimeMillis());
                    QuestionExample questionExample = new QuestionExample();
                    questionExample.createCriteria()
                            .andIdEqualTo(question.getId());
                    int update=questionMapper.updateByExampleSelective(question,questionExample);
                    if(update != 1){
                        throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
                    }
                    return "redirect:/profile/questions";
                }}

    }
}
