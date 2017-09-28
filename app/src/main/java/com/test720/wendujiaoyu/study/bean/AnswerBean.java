package com.test720.wendujiaoyu.study.bean;

import java.util.List;

/**
 * Created by jie on 2017/8/15.
 */

public class AnswerBean {

    /**
     * id : 2
     * titile : 有那些车?
     * type : 2
     * answer_list : ["1","2","3","4"]
     * true_list : 1,2
     * judge_answer : 0
     * simple_answer :
     * is_shoucang : 1
     *type_name
     */

    private String id;
    private String type_name;

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public String getTitile() {
        return titile;
    }

    public void setTitile(String titile) {
        this.titile = titile;
    }

    private String titile;
    private String type;
    private List<String> true_list;

    public List<String> getTrue_list() {
        return true_list;
    }

    public void setTrue_list(List<String> true_list) {
        this.true_list = true_list;
    }

    private int is_shoucang;
    private List<String> answer_list;
    private String selectedIndex;
    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getSelectedIndex() {
        return selectedIndex;
    }

    public void setSelectedIndex(String selectedIndex) {
        this.selectedIndex = selectedIndex;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getIs_shoucang() {
        return is_shoucang;
    }

    public void setIs_shoucang(int is_shoucang) {
        this.is_shoucang = is_shoucang;
    }

    public List<String> getAnswer_list() {
        return answer_list;
    }

    public void setAnswer_list(List<String> answer_list) {
        this.answer_list = answer_list;
    }
}
