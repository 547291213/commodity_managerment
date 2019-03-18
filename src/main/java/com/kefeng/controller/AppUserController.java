/**
 * Copyright(C) H3C
 * Author : kefeng
 * Filename : AppUserController
 * Description : user controller for android
 **/
package com.kefeng.controller;

import com.alibaba.fastjson.JSONObject;
import com.kefeng.pojo.Code;
import com.kefeng.pojo.User;
import com.kefeng.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "/android")

public class AppUserController {


    @Autowired
    UserService userService;

    //接口返回的code值
    public final static String CODE = "\"code\"";

    /**
     * android端用户登陆
     * @param userName
     * @param password
     * @param httpSession
     * @return 0成功  1用户名或者密码为空  2用户名未找到  3密码错误  4其他错误
     */
    @RequestMapping(value = "/login", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Code login(@RequestParam("userName") String userName,
                      @RequestParam("password") String password,
                      HttpSession httpSession) {

        Code code = new Code();
        if (userName == null || password == null) {
            //用户名或者密码为空
            code.setCode("1");
            return code;
        }

        User user = userService.getUserByUserName(userName);
        if (user == null) {
            //用户名错误
            code.setCode("2");
            return code;
        }

        if (!user.getPassword().equals(password)) {
            code.setCode("3");
            return code;
        } else {
            httpSession.setAttribute("username", userName);
            System.out.println("App 用户 ：" + userName + " 登陆");
            code.setCode("0");
            return code;
        }

    }


    /**
     * app端注册用户
     * @param userName
     * @param password
     * @return 1 用户已经存在  0成功  2未知错误
     */
    @RequestMapping(value = "/register", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Code Register(@RequestParam("userName") String userName,
                      @RequestParam("password") String password) {

        Code code = new Code() ;
        /**
         * 用户已经存在
         */
        if (userService.getUserByUserName(userName) != null){
            code.setCode("1");
            return code ;
        }
        try {
            User user = new User() ;
            user.setUserName(userName);
            user.setPassword(password);
            userService.addUser(user);
            code.setCode("0");
            return code ;
        }catch (Exception e){

            code.setCode("2");
            return code ;
        }



    }
}
