package com.test.com.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by LuoPan on 2017/8/23 10:39.
 */

public class BaoMingDaijinQuan implements Serializable {

    /**
     * msg : 成功
     * data : {"daijin":[{"is_use":"0","end_time":"2017-08-29 23:59:59","name":"文都代金券","start_time":"1969-12-22 08:33:37","cid":"13","money":"50","coupon_id":"2","coupon_name":"支付优惠券","code":"AC12-ADRE-ER15","user_id":"20"}]}
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
        private List<DaijinBean> daijin;

        public List<DaijinBean> getDaijin() {
            return daijin;
        }

        public void setDaijin(List<DaijinBean> daijin) {
            this.daijin = daijin;
        }

        public static class DaijinBean implements Serializable {
            @Override
            public String toString() {
                return "DaijinBean{" +
                        "is_use='" + is_use + '\'' +
                        ", end_time='" + end_time + '\'' +
                        ", name='" + name + '\'' +
                        ", start_time='" + start_time + '\'' +
                        ", cid='" + cid + '\'' +
                        ", money='" + money + '\'' +
                        ", coupon_id='" + coupon_id + '\'' +
                        ", coupon_name='" + coupon_name + '\'' +
                        ", code='" + code + '\'' +
                        ", user_id='" + user_id + '\'' +
                        '}';
            }

            /**
             * is_use : 0
             * end_time : 2017-08-29 23:59:59
             * name : 文都代金券
             * start_time : 1969-12-22 08:33:37
             * cid : 13
             * money : 50
             * coupon_id : 2
             * coupon_name : 支付优惠券
             * code : AC12-ADRE-ER15
             * user_id : 20
             */

            private String is_use;
            private String end_time;
            private String name;
            private String start_time;
            private String cid;
            private String money;
            private String coupon_id;
            private String coupon_name;
            private String code;
            private String user_id;

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

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getStart_time() {
                return start_time;
            }

            public void setStart_time(String start_time) {
                this.start_time = start_time;
            }

            public String getCid() {
                return cid;
            }

            public void setCid(String cid) {
                this.cid = cid;
            }

            public String getMoney() {
                return money;
            }

            public void setMoney(String money) {
                this.money = money;
            }

            public String getCoupon_id() {
                return coupon_id;
            }

            public void setCoupon_id(String coupon_id) {
                this.coupon_id = coupon_id;
            }

            public String getCoupon_name() {
                return coupon_name;
            }

            public void setCoupon_name(String coupon_name) {
                this.coupon_name = coupon_name;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }
        }
    }
}
