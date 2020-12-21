package com.zhao.community.service;

import com.zhao.community.mapper.UserMapper;
import com.zhao.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserService {
    @Autowired
    UserMapper userMapper;

    public void findByAccountId(User user){
        User dbuser=new User();
        dbuser=userMapper.findByAccountId(user.getAccountId());
        if(dbuser==null){
//            添加
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
        }else{
//            更新
            dbuser.setName(user.getName());
            dbuser.setTouXiang(user.getTouXiang());
            dbuser.setToken(user.getToken());
            dbuser.setGmtModified(System.currentTimeMillis());
            userMapper.update(dbuser);
        }
    }

}
