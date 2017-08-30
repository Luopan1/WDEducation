package com.test.com.entity;

import java.util.List;

/**
 * Created by LuoPan on 2017/8/19 15:15.
 */

public class JoinUs {

    /**
     * msg : 查询成功
     * data : {"list":[{"major":"会计","name":"学员1","header":"Uploads/img/5996808d7bb92.png","st_id":"1"}],"total":5}
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
         * list : [{"major":"会计","name":"学员1","header":"Uploads/img/5996808d7bb92.png","st_id":"1"}]
         * total : 5
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
             * header : Uploads/img/5996808d7bb92.png
             * st_id : 1
             */

            private String major;
            private String name;
            private String header;
            private String st_id;

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
        }
    }
}

