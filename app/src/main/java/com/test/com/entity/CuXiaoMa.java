package com.test.com.entity;

import java.util.List;

/**
 * Created by LuoPan on 2017/8/24 13:46.
 */

public class CuXiaoMa {

    /**
     * msg : 成功
     * data : [{"question":"如何获取代金券","id":"1","answer":"就这丫的哈是的哈是的哈USD和"},{"question":"什么是代金券","id":"2","answer":"阿萨德机爱时间都"},{"question":"如何使用代金券","id":"3","answer":"阿斯都下时间地"}]
     * code : 1
     */

    private String msg;
    private int code;
    private List<DataBean> data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * question : 如何获取代金券
         * id : 1
         * answer : 就这丫的哈是的哈是的哈USD和
         */

        private String question;
        private String id;
        private String answer;

        public String getQuestion() {
            return question;
        }

        public void setQuestion(String question) {
            this.question = question;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAnswer() {
            return answer;
        }

        public void setAnswer(String answer) {
            this.answer = answer;
        }
    }
}

