/**
 * Copyright(C) H3C
 * Author : kefeng
 * Filename : UserLoginedController
 * Description : 管理员用户登陆后的操作
 **/
package com.kefeng.controller;

import com.kefeng.pojo.User;
import com.kefeng.pojo.UserGrid;
import com.kefeng.service.UserService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.List;

@Controller
@RequestMapping(value = "/logined")
public class UserLoginedController {
    @Autowired
    UserService userService;

    /**
     * 登陆后默认跳转的界面
     * @return 返回用户列表的jsp
     */
    @RequestMapping(value = "/userList1", method = RequestMethod.GET)
    public String stuList() {
        return "user/userList";
    }


    /**
     * 把用户列表按json格式传出，使用pagehelper实现分页
     * @param current  当前页面
     * @param rowCount 行数
     * @return 用户列表
     */
    @RequestMapping(value = "/userList", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public UserGrid userList(@RequestParam("current") int current, @RequestParam("rowCount") int rowCount) {
        return getUserGrid(current, rowCount);
    }

    /**
     * 添加新的用户
     * @param userName
     * @param permissions
     * @param password
     * @param nickName
     * @return 用户列表界面的jsp
     */
    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public String add(@RequestParam("userName") String userName,
                      @RequestParam("permissions") int permissions,
                      @RequestParam("password") String password,
                      @RequestParam("nickName") String nickName) {
        System.out.println("add user stuName:" + userName);
        User stu = new User();
        stu.setUserName(userName);
        if (permissions <= 0 || permissions > 2) {
            stu.setPermissions(0);
        } else {
            stu.setPermissions(permissions);
        }
        stu.setNickName(nickName);
        stu.setPassword(password);
        userService.addUser(stu);
        return "redirect:userList1";
    }


    /**
     * 更具userId删除用户
     * @param userId
     * @return 用户列表界面的jsp
     */
    @RequestMapping(value = "/delUser", method = RequestMethod.POST)
    public String delete(@RequestParam("userId") int userId) {
        try {
            userService.delUser(userId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:userList1";
    }


    /**
     * 获取用户列表界面
     * @param current
     * @param rowCount
     * @return 用户列表
     */
    private UserGrid getUserGrid(@RequestParam("current") int current, @RequestParam("rowCount") int rowCount) {
        int total = userService.getUserNum();
        System.out.println("user number is " + total);
        List<User> list = userService.getPageUser(current, rowCount);
        UserGrid userGrid = new UserGrid();
        userGrid.setCurrent(current);
        userGrid.setRowCount(rowCount);
        userGrid.setRows(list);
        userGrid.setTotal(total);
        return userGrid;
    }

    /**
     * 根据用户id获取用户信息，返回json数据
     * @param userId
     * @return 用户信息
     */
    @RequestMapping(value = "/getUserInfo", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public User getStuById(@RequestParam("userId") int userId) {
        User user = userService.getUserById(userId);
        System.out.println("getStuById , stu name is " + user.getUserName());
        return user;
    }

    /**
     * 更新用户信息
     * @param userId
     * @param userName
     * @param permissions
     * @param password
     * @param nickName
     * @return 用户列表的jsp
     */
    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    public String update(@RequestParam("userId") int userId, @RequestParam("userName") String userName,
                         @RequestParam("permissions") int permissions,
                         @RequestParam("password") String password,
                         @RequestParam("nickName") String nickName) {
        User user = new User();
        user.setUserId(userId);
        user.setPassword(password);
        user.setPermissions(permissions);
        user.setUserName(userName);
        user.setNickName(nickName);
        userService.updateUser(user);
        return "redirect:userList1";
    }

    /**
     * 将用户列表导出为excel文件
     * 目前有bug，错误未知
     * @param response
     * @throws Exception
     */
    @RequestMapping("/exportUser")
    public void export(HttpServletResponse response) throws Exception {
        InputStream is = userService.getInputStream();
        if (is == null) {

            System.out.println("导出excel出错。输入流为空");
            return;
        }

        response.setContentType("application/vnd.ms-excel");
        response.setHeader("contentDisposition", "attachment;filename=AllUsers.xls");
        ServletOutputStream output = response.getOutputStream();
        IOUtils.copy(is, output);
    }
}
