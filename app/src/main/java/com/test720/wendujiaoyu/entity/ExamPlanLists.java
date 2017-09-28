package com.test720.wendujiaoyu.entity;

import java.util.List;

/**
 * Created by LuoPan on 2017/8/12 14:54.
 */

public class ExamPlanLists {
    private int code;

    private Data data;

    private String msg;

    public void setCode(int code){
        this.code = code;
    }
    public int getCode(){
        return this.code;
    }
    public void setData(Data data){
        this.data = data;
    }
    public Data getData(){
        return this.data;
    }
    public void setMsg(String msg){
        this.msg = msg;
    }
    public String getMsg(){
        return this.msg;
    }
    public class Data {
        private List<PlanList> planList ;

        private MajorInfo majorInfo;

        public void setPlanList(List<PlanList> planList){
            this.planList = planList;
        }
        public List<PlanList> getPlanList(){
            return this.planList;
        }
        public void setMajorInfo(MajorInfo majorInfo){
            this.majorInfo = majorInfo;
        }
        public MajorInfo getMajorInfo(){
            return this.majorInfo;
        }

    }

    public class PlanList {

        @Override
        public String toString() {
            return "PlanList{" +
                    "start_time='" + start_time + '\'' +
                    ", end_time='" + end_time + '\'' +
                    ", course_list=" + course_list +
                    '}';
        }

        private String start_time;

        private String end_time;

        private List<Course_list> course_list ;

        public void setStart_time(String start_time){
            this.start_time = start_time;
        }
        public String getStart_time(){
            return this.start_time;
        }
        public void setEnd_time(String end_time){
            this.end_time = end_time;
        }
        public String getEnd_time(){
            return this.end_time;
        }
        public void setCourse_list(List<Course_list> course_list){
            this.course_list = course_list;
        }
        public List<Course_list> getCourse_list(){
            return this.course_list;
        }

    }

    public class Course_list {
        private String course_name;

        private String course_code;

        public void setCourse_name(String course_name){
            this.course_name = course_name;
        }
        public String getCourse_name(){
            return this.course_name;
        }
        public void setCourse_code(String course_code){
            this.course_code = course_code;
        }
        public String getCourse_code(){
            return this.course_code;
        }

        @Override
        public String toString() {
            return "Course_list{" +
                    "course_name='" + course_name + '\'' +
                    ", course_code='" + course_code + '\'' +
                    '}';
        }
    }

    public class MajorInfo {
        private String name;

        private String code;

        public void setName(String name){
            this.name = name;
        }
        public String getName(){
            return this.name;
        }
        public void setCode(String code){
            this.code = code;
        }
        public String getCode(){
            return this.code;
        }

    }


}
