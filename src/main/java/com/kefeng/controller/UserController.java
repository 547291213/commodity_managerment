/**
 * Copyright(C) H3C
 * Author : kefeng
 * Filename : UserController
 * Description :
 **/
package com.kefeng.controller;

import com.kefeng.pojo.User;
import com.kefeng.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "/")
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String userLogin() {
        return "user/login";
    }

    @RequestMapping(value = "/loginValidate", method = RequestMethod.POST)
    public String userLoginValidate(@RequestParam("username") String username,
                                    @RequestParam("password") String password,
                                    HttpSession httpSession) {
        System.out.println("用户进入登陆请求");
        if (username == null || password == null) {
            return "user/login";
        }

        User user = userService.getUserByUserName(username);
        if (user == null) {
            return "user/login";
        }

        //需要对用户的密码和权限进行确认
        if (user.getPassword().equals(password) && user.getPermissions() >= 2) {
            httpSession.setAttribute("username", username);
            System.out.println("用户 ：" + username + " 登陆");
            //转到用户管理界面
            return "redirect:/logined/userList1";
        }

        return "user/login";


    }

    @RequestMapping(value = "logout")
    public String logout(HttpSession httpSession) {
        httpSession.removeAttribute("username");
        return "user/login";
    }



}
