package com.kefeng.dao;


import com.kefeng.pojo.User;


 public interface UserMapper {

    void addUser(User user);

    User getUserByUserName(String user_name);
}
