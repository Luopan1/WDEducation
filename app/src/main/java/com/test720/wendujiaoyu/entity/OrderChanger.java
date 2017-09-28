package com.test720.wendujiaoyu.entity;

import java.util.List;

/**
 * Created by LuoPan on 2017/8/23 19:01.
 */

public class OrderChanger {

    /**
     * msg : 成功
     * data : {"price":"190","userList":[{"baokao_major":"金融","id":"252","phone":"1","name":"1","stay_school":"西华大学","user_idcard":"1"}],"old_price":"190","d_id":"199"}
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
         * price : 190
         * userList : [{"baokao_major":"金融","id":"252","phone":"1","name":"1","stay_school":"西华大学","user_idcard":"1"}]
         * old_price : 190
         * d_id : 199
         */

        private String price;
        private String old_price;
        private String d_id;
        private List<UserListBean> userList;

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getOld_price() {
            return old_price;
        }

        public void setOld_price(String old_price) {
            this.old_price = old_price;
        }

        public String getD_id() {
            return d_id;
        }

        public void setD_id(String d_id) {
            this.d_id = d_id;
        }

        public List<UserListBean> getUserList() {
            return userList;
        }

        public void setUserList(List<UserListBean> userList) {
            this.userList = userList;
        }

        public static class UserListBean {
            /**
             * baokao_major : 金融
             * id : 252
             * phone : 1
             * name : 1
             * stay_school : 西华大学
             * user_idcard : 1
             */

            private String baokao_major;
            private String id;
            private String phone;
            private String name;
            private String stay_school;
            private String user_idcard;

            public String getBaokao_major() {
                return baokao_major;
            }

            public void setBaokao_major(String baokao_major) {
                this.baokao_major = baokao_major;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getStay_school() {
                return stay_school;
            }

            public void setStay_school(String stay_school) {
                this.stay_school = stay_school;
            }

            public String getUser_idcard() {
                return user_idcard;
            }

            public void setUser_idcard(String user_idcard) {
                this.user_idcard = user_idcard;
            }
        }
    }
}
