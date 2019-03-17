package com.kefeng.service;

import com.kefeng.pojo.User;

import java.io.InputStream;
import java.util.List;


public interface UserService {

    void addUser(User user);

    User getUserByUserName(String userName);

    User checkLoginIn(String suerName, String password);

    void delUser(int userId) throws Exception;

    void updateUser(User user);

    List<User> getPageUser(int pageNum, int pageSize);

    int getUserNum();

    InputStream getInputStream() throws Exception;

    User getUserById(int userId);



}
