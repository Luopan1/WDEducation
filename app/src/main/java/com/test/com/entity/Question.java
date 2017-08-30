package com.test.com.entity;

/**
 * Created by LuoPan on 2017/8/8 20:51.
 */

public class Question {

    /**
     * {
     * "title": "请问毕业后可以申请学士学位吗",
     * "id": "1",
     * "answer_teacher": "宋老师",
     * "touxiang": null,
     * "lovemun": "6"
     * },
     */

    public String title;
    public String id;
    public String answer_teacher;
    public String touxiang;
    public String lovemun;
    public String answer;

    @Override
    public String toString() {
        return "Question{" +
                "title='" + title + '\'' +
                ", id='" + id + '\'' +
                ", answer_teacher='" + answer_teacher + '\'' +
                ", touxiang='" + touxiang + '\'' +
                ", lovemun='" + lovemun + '\'' +
                ", answer='" + answer + '\'' +
                '}';
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Question() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAnswer_teacher() {
        return answer_teacher;
    }

    public void setAnswer_teacher(String answer_teacher) {
        this.answer_teacher = answer_teacher;
    }

    public String getTouxiang() {
        return touxiang;
    }

    public void setTouxiang(String touxiang) {
        this.touxiang = touxiang;
    }

    public String getLovemun() {
        return lovemun;
    }

    public void setLovemun(String lovemun) {
        this.lovemun = lovemun;
    }


}
