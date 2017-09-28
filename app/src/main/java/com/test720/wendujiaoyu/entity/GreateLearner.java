package com.test720.wendujiaoyu.entity;

import java.util.List;

/**
 * Created by LuoPan on 2017/8/18 16:25.
 */

public class GreateLearner {

    /**
     * msg : 查询成功
     * data : {"list":[{"major":"会计","name":"学员1","header":"Uploads/img/2017-09-13/59b8c7f1d943f.jpg","st_id":"1","sex":"0"},{"major":"市场营销","name":"学员2","header":"Uploads/img/5996808d7bb92.png","st_id":"2","sex":"0"},{"major":"汉语言文学","name":"学员3","header":"Uploads/img/5996808d7bb92.png","st_id":"3","sex":"0"},{"major":"财税","name":"学员4","header":"Uploads/img/5996808d7bb92.png","st_id":"4","sex":"0"},{"major":"新闻学","name":"学员5","header":"Uploads/img/5996808d7bb92.png","st_id":"5","sex":"0"}],"total":1}
     * code : 1
     */

    private String msg;
    private DataBean data;
    private int code;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public static class DataBean {
        /**
         * list : [{"major":"会计","name":"学员1","header":"Uploads/img/2017-09-13/59b8c7f1d943f.jpg","st_id":"1","sex":"0"},{"major":"市场营销","name":"学员2","header":"Uploads/img/5996808d7bb92.png","st_id":"2","sex":"0"},{"major":"汉语言文学","name":"学员3","header":"Uploads/img/5996808d7bb92.png","st_id":"3","sex":"0"},{"major":"财税","name":"学员4","header":"Uploads/img/5996808d7bb92.png","st_id":"4","sex":"0"},{"major":"新闻学","name":"学员5","header":"Uploads/img/5996808d7bb92.png","st_id":"5","sex":"0"}]
         * total : 1
         */

        private int total;
        private List<ListBean> list;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * major : 会计
             * name : 学员1
             * header : Uploads/img/2017-09-13/59b8c7f1d943f.jpg
             * st_id : 1
             * sex : 0
             */

            private String major;
            private String name;
            private String header;
            private String st_id;
            private String sex;

            public String getMajor() {
                return major;
            }

            public void setMajor(String major) {
                this.major = major;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getHeader() {
                return header;
            }

            public void setHeader(String header) {
                this.header = header;
            }

            public String getSt_id() {
                return st_id;
            }

            public void setSt_id(String st_id) {
                this.st_id = st_id;
            }

            public String getSex() {
                return sex;
            }

            public void setSex(String sex) {
                this.sex = sex;
            }
        }
    }
}
