package com.test.com.entity;

/**
 * Created by LuoPan on 2017/8/8 19:25.
 */

public class MasjorLists {
    /*"id": "21",
            "name": "哲学类1",
            "code": "12311",
            "school": "西南大学"*/
    public String id;
    public String name;
    public String code;

    public String school;

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String education;
    public String learning_form;
    public String time;
    public String degree;

    @Override
    public String toString() {
        return "MasjorLists{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", education='" + education + '\'' +
                ", learning_form='" + learning_form + '\'' +
                ", time='" + time + '\'' +
                ", degree='" + degree + '\'' +
                '}';
    }

    public MasjorLists() {
    }

    public MasjorLists(String id, String name, String code, String education, String learning_form, String time, String degree) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.education = education;
        this.learning_form = learning_form;
        this.time = time;
        this.degree = degree;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getLearning_form() {
        return learning_form;
    }

    public void setLearning_form(String learning_form) {
        this.learning_form = learning_form;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }
}
