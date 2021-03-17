package com.zhao.community.service;

import com.zhao.community.mapper.UserMapper;
import com.zhao.community.model.User;
import com.zhao.community.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Component
public class UserService {
    @Autowired
    UserMapper userMapper;
@Transactional
    public void findByAccountId(User user){
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andAccountIdEqualTo(user.getAccountId());
        List<User> users = userMapper.selectByExample(userExample);

        if(users.size()==0){
//            添加
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
        }else{
//            更新
            User dbuser = users.get(0);
            User updateUser = new User();
            updateUser.setName(user.getName());
            updateUser.setTouxiang(user.getTouxiang());
            updateUser.setToken(user.getToken());
            updateUser.setGmtModified(System.currentTimeMillis());
            UserExample userExample1 = new UserExample();
            userExample1.createCriteria()
                    .andIdEqualTo(dbuser.getId());
            userMapper.updateByExampleSelective(updateUser,userExample1);
        }
    }

}
