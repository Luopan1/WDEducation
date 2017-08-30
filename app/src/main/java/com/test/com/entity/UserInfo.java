package com.test.com.entity;

/**
 * Created by LuoPan on 2017/8/28 17:10.
 */

public class UserInfo {


    /**
     * msg : 查询成功
     * data : {"userInfo":{"stay_major":"四川大学","examination_major":"新闻学","nickname":"＼(￣O￣)","headimg":"Uploads/img/2017-08-29/59a51e6e6f0b0.png","school":"四川大学","uuid":"uuid","email":"111@QQ.com"}}
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
         * userInfo : {"stay_major":"四川大学","examination_major":"新闻学","nickname":"＼(￣O￣)","headimg":"Uploads/img/2017-08-29/59a51e6e6f0b0.png","school":"四川大学","uuid":"uuid","email":"111@QQ.com"}
         */

        private UserInfoBean userInfo;

        public UserInfoBean getUserInfo() {
            return userInfo;
        }

        public void setUserInfo(UserInfoBean userInfo) {
            this.userInfo = userInfo;
        }

        public static class UserInfoBean {
            /**
             * stay_major : 四川大学
             * examination_major : 新闻学
             * nickname : ＼(￣O￣)
             * headimg : Uploads/img/2017-08-29/59a51e6e6f0b0.png
             * school : 四川大学
             * uuid : uuid
             * email : 111@QQ.com
             */

            private String stay_major;
            private String examination_major;
            private String nickname;
            private String headimg;
            private String school;
            private String uuid;
            private String email;

            public String getStay_major() {
                return stay_major;
            }

            public void setStay_major(String stay_major) {
                this.stay_major = stay_major;
            }

            public String getExamination_major() {
                return examination_major;
            }

            public void setExamination_major(String examination_major) {
                this.examination_major = examination_major;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public String getHeadimg() {
                return headimg;
            }

            public void setHeadimg(String headimg) {
                this.headimg = headimg;
            }

            public String getSchool() {
                return school;
            }

            public void setSchool(String school) {
                this.school = school;
            }

            public String getUuid() {
                return uuid;
            }

            public void setUuid(String uuid) {
                this.uuid = uuid;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }
        }
    }
}
