package com.test720.wendujiaoyu.entity;

import java.util.List;

/**
 * Created by LuoPan on 2017/8/8 16:24.
 */

public class Major {
    private int code;

    private List<Data> data;

    private String msg;

    public void setCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public List<Data> getData() {
        return this.data;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return this.msg;
    }

    public class Data {
        private String name;

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }

    }

}

