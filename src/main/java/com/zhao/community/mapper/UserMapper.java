package com.zhao.community.mapper;

import com.zhao.community.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface UserMapper {
    @Insert("insert into user(name,account_id,token,gmt_create,gmt_modified,touxiang) values(#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified},#{touXiang})")
    void insert(User user);
    @Select("select * from user where token=#{token}")//如果注解下面是一个自定义对象可以直接获取，如果是一个值则需要使用@Param注解解释一下
    User findByToken(@Param("token") String token);
    @Select("select * from user where id=#{id}")
    User findById(@Param("id") Integer id);
    @Update("update user set name=#{name},token=#{token},gmt_modified=#{gmtModified},touxiang=#{touXiang} where account_id=#{accountId}")
    void update(User user);
    @Select("select * from user where account_id=#{accountId}")
    User findByAccountId(@Param("accountId") String accountId);
}
