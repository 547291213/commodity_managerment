/**
 * Copyright(C) H3C
 * Author : kefeng
 * Filename : UserGrid
 * Description : 用户列表分页处理
 **/
package com.kefeng.pojo;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UserGrid {

    //当前页面号
    private int current ;
    //每页行数
    private int rowCount ;
    //总行数
    private int total ;
    private List<User> rows ;

    public void setCurrent(int current) {
        this.current = current;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public void setRows(List<User> rows) {
        this.rows = rows;
    }

    public int getCurrent() {
        return current;
    }

    public int getRowCount() {
        return rowCount;
    }

    public int getTotal() {
        return total;
    }

    public List<User> getRows() {
        return rows;
    }
}
