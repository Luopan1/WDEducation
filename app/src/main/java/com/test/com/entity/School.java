package com.test.com.entity;

import java.util.List;

/**
 * Created by LuoPan on 2017/8/8 15:07.
 */

public class School {
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

    public static class Data {
        @Override
        public String toString() {
            return "Data{" +
                    "sc_name='" + sc_name + '\'' +
                    '}';
        }

        private String sc_name;

        public void setSc_name(String sc_name) {
            this.sc_name = sc_name;
        }

        public String getSc_name() {
            return this.sc_name;
        }

    }

    @Override
    public String toString() {
        return "School{" +
                "code=" + code +
                ", data=" + data +
                ", msg='" + msg + '\'' +
                '}';
    }
}
