package com.test.com.entity;

/**
 * Created by LuoPan on 2017/8/9 12:30.
 */

public class News {
        private String title;

        private String activity_id;

        private String cover_img;

        private String describe;

        private String add_time;

        public void setTitle(String title){
            this.title = title;
        }
        public String getTitle(){
            return this.title;
        }

        public void setActivity_id(String activity_id){
            this.activity_id = activity_id;
        }
        public String getActivity_id(){
            return this.activity_id;
        }
        public void setCover_img(String cover_img){
            this.cover_img = cover_img;
        }
        public String getCover_img(){
            return this.cover_img;
        }
        public void setDescribe(String describe){
            this.describe = describe;
        }
        public String getDescribe(){
            return this.describe;
        }
        public void setAdd_time(String add_time){
            this.add_time = add_time;
        }
        public String getAdd_time(){
            return this.add_time;
        }


}
