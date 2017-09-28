package com.test720.wendujiaoyu.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by LuoPan on 2017/8/23 11:45.
 */

public class BaoMIngCuXiaoMa implements Serializable {

    /**
     * msg : 成功
     * data : {"cuxiao":[{"is_use":"0","end_time":"1974-11-25 02:14:48","use_id":"20","id":"3","start_time":"1974-11-25 01:00:44","name":"促销码","money":"50"}]}
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
        private List<CuxiaoBean> cuxiao;

        public List<CuxiaoBean> getCuxiao() {
            return cuxiao;
        }

        public void setCuxiao(List<CuxiaoBean> cuxiao) {
            this.cuxiao = cuxiao;
        }

        public static class CuxiaoBean implements Serializable {

            @Override
            public String toString() {
                return "CuxiaoBean{" +
                        "is_use='" + is_use + '\'' +
                        ", end_time='" + end_time + '\'' +
                        ", use_id='" + use_id + '\'' +
                        ", id='" + id + '\'' +
                        ", start_time='" + start_time + '\'' +
                        ", name='" + name + '\'' +
                        ", money='" + money + '\'' +
                        '}';
            }

            /**
             * is_use : 0
             * end_time : 1974-11-25 02:14:48
             * use_id : 20
             * id : 3
             * start_time : 1974-11-25 01:00:44
             * name : 促销码
             * money : 50
             */

            private String is_use;
            private String end_time;
            private String use_id;
            private String id;
            private String start_time;
            private String name;
            private String money;

            public String getIs_use() {
                return is_use;
            }

            public void setIs_use(String is_use) {
                this.is_use = is_use;
            }

            public String getEnd_time() {
                return end_time;
            }

            public void setEnd_time(String end_time) {
                this.end_time = end_time;
            }

            public String getUse_id() {
                return use_id;
            }

            public void setUse_id(String use_id) {
                this.use_id = use_id;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getStart_time() {
                return start_time;
            }

            public void setStart_time(String start_time) {
                this.start_time = start_time;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getMoney() {
                return money;
            }

            public void setMoney(String money) {
                this.money = money;
            }
        }
    }
}
