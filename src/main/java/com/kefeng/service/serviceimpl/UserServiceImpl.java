/**
 * Copyright(C) H3C
 * Author : kefeng
 * Filename : UserServiceImpl
 * Description :
 **/
package com.kefeng.service.serviceimpl;


import com.github.pagehelper.PageHelper;
import com.kefeng.dao.UserMapper;
import com.kefeng.poi.WriteExcel;
import com.kefeng.pojo.User;
import com.kefeng.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


@Service("userService")
public class UserServiceImpl implements UserService {


    @Autowired
    private UserMapper userMapper;

    @Override
    public void addUser(User user) {
        userMapper.addUser(user);
    }

    @Override
    public User getUserByUserName(String user_name) {
        User user = userMapper.getUserByUserName(user_name);
        return user;
    }

    @Override
    public User checkLoginIn(String user_name, String password) {
        User user = userMapper.getUserByUserName(user_name);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    @Override
    public void delUser(int userId) throws Exception {
        userMapper.delUser(userId);
    }

    @Override
    public void updateUser(User user) {
        userMapper.updateUser(user);
    }

    @Override
    public List<User> getPageUser(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);//分页核心代码

        return userMapper.getUserList();
    }

    @Override
    public int getUserNum() {
        List<User> users = userMapper.getUserList();
        return users.size();
    }

    @Override
    public InputStream getInputStream() throws Exception {
        String[] title = new String[]{"userId", "userName", "permissions", "nickName", "password"};
        List<User> plist = userMapper.getUserList();
        System.out.println("list size" + plist.size());
        for(User user : plist) {
           System.out.println("userName is " + user.getUserName());
        }
        List<Object[]> dataList = new ArrayList<Object[]>();
        for (User stu : plist) {
            Object[] obj = new Object[5];
            obj[0] = stu.getUserId();
            obj[1] = stu.getUserName();
            obj[2] = stu.getPermissions();
            obj[3] = stu.getNickName();
            obj[4] = stu.getPassword();
            dataList.add(obj);
        }
        WriteExcel ex = new WriteExcel(title, dataList);
        InputStream in;
        try {
            in = ex.export();
            return in;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User getUserById(int userId) {
        return userMapper.getUserById(userId);
    }

    public static HttpSession getSession() {
        HttpSession httpSession = null;
        try {
            httpSession = getRequest().getSession();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return httpSession;
    }

    private static HttpServletRequest getRequest() {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attrs.getRequest();
    }
}
