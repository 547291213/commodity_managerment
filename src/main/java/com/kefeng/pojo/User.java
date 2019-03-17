/**
 * Copyright(C) H3C
 * Author : kefeng
 * Filename : User
 * Description : user
 **/
package com.kefeng.pojo;

import java.io.Serializable;

public class User implements Serializable {
    int userId ;
    String userName ;
    String password ;
    int permissions  ; //0 默认权限 1 管理员权限
    String nickName;

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPermissions(int permissions) {
        this.permissions = permissions;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public int getPermissions() {
        return permissions;
    }

    public String getNickName() {
        return nickName;
    }
}
