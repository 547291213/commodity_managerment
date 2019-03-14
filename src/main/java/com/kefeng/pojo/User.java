/**
 * Copyright(C) H3C
 * Author : kefeng
 * Filename : User
 * Description : user
 **/
package com.kefeng.pojo;

import java.io.Serializable;

public class User implements Serializable {
    int id ;
    String user_name ;
    String password ;
    int permissions  ; //0 默认权限 1 管理员权限

    public void setId(int id) {
        this.id = id;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPermissions(int permissions) {
        this.permissions = permissions;
    }

    public int getId() {
        return id;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getPassword() {
        return password;
    }

    public int getPermissions() {
        return permissions;
    }
}
