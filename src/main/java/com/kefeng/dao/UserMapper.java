package com.kefeng.dao;


import com.kefeng.pojo.User;

import java.util.List;


public interface UserMapper {

    void addUser(User user);

    User getUserByUserName(String userName);

    void delUser(int userId) ;

    void updateUser(User user) ;

    List<User> getUserList();

    User getUserById(int userId);



}
