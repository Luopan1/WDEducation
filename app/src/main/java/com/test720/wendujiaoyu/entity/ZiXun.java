
package com.test720.wendujiaoyu.entity;

import java.util.List;

public class ZiXun {
    private int code;

    private Data data;

    private String msg;

    @Override
    public String toString() {
        return "ZiXun{" +
                "code=" + code +
                ", data=" + data +
                ", msg='" + msg + '\'' +
                '}';
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Data getData() {
        return this.data;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return this.msg;
    }

    public class Data {
        @Override
        public String toString() {
            return "Data{" +
                    "banner=" + banner +
                    ", understand=" + understand +
                    ", college=" + college +
                    ", join=" + join +
                    '}';
        }

        private List<Banner> banner;

        private List<Understand> understand;

        private List<College> college;

        private List<Join> join;

        public void setBanner(List<Banner> banner) {
            this.banner = banner;
        }

        public List<Banner> getBanner() {
            return this.banner;
        }

        public void setUnderstand(List<Understand> understand) {
            this.understand = understand;
        }

        public List<Understand> getUnderstand() {
            return this.understand;
        }

        public void setCollege(List<College> college) {
            this.college = college;
        }

        public List<College> getCollege() {
            return this.college;
        }

        public void setJoin(List<Join> join) {
            this.join = join;
        }

        public List<Join> getJoin() {
            return this.join;
        }

    }

    public class Join {

        @Override
        public String toString() {
            return "Join{" +
                    "id='" + id + '\'' +
                    ", zikao_title='" + zikao_title + '\'' +
                    ", img='" + img + '\'' +
                    ", time='" + time + '\'' +
                    '}';
        }

        private String id;

        private String zikao_title;

        private String img;

        private String time;

        public void setId(String id) {
            this.id = id;
        }

        public String getId() {
            return this.id;
        }

        public void setZikao_title(String zikao_title) {
            this.zikao_title = zikao_title;
        }

        public String getZikao_title() {
            return this.zikao_title;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getImg() {
            return this.img;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getTime() {
            return this.time;
        }

    }

    public class College {
        @Override
        public String toString() {
            return "College{" +
                    "id='" + id + '\'' +
                    ", zikao_title='" + zikao_title + '\'' +
                    ", img='" + img + '\'' +
                    ", time='" + time + '\'' +
                    '}';
        }

        private String id;

        private String zikao_title;

        private String img;

        private String time;

        public void setId(String id) {
            this.id = id;
        }

        public String getId() {
            return this.id;
        }

        public void setZikao_title(String zikao_title) {
            this.zikao_title = zikao_title;
        }

        public String getZikao_title() {
            return this.zikao_title;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getImg() {
            return this.img;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getTime() {
            return this.time;
        }

    }

    public class Understand {

        @Override
        public String toString() {
            return "Understand{" +
                    "id='" + id + '\'' +
                    ", zikao_title='" + zikao_title + '\'' +
                    ", img='" + img + '\'' +
                    ", time='" + time + '\'' +
                    '}';
        }

        private String id;

        private String zikao_title;

        private String img;

        private String time;

        public void setId(String id) {
            this.id = id;
        }

        public String getId() {
            return this.id;
        }

        public void setZikao_title(String zikao_title) {
            this.zikao_title = zikao_title;
        }

        public String getZikao_title() {
            return this.zikao_title;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getImg() {
            return this.img;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getTime() {
            return this.time;
        }

    }

    public class Banner {
        private String ba_id;

        private String ba_img;

        private String target_type;

        public void setBa_id(String ba_id) {
            this.ba_id = ba_id;
        }

        public String getBa_id() {
            return this.ba_id;
        }

        public void setBa_img(String ba_img) {
            this.ba_img = ba_img;
        }

        public String getBa_img() {
            return this.ba_img;
        }

        public void setTarget_type(String target_type) {
            this.target_type = target_type;
        }

        public String getTarget_type() {
            return this.target_type;
        }

    }

}