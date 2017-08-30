package com.test.com.entity;

import java.util.List;

/**
 * Created by LuoPan on 2017/8/28 9:20.
 */

public class DaiJinQuanEntity {

    /**
     * msg : 查询成功
     * data : [{"end_time":"2017-08-29","money":"100.0","coupon_name":"支付优惠券","code":"AC12-ADRE-ER15"}]
     * code : 1
     */

    private String msg;
    private int code;
    private List<DataBean> data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * end_time : 2017-08-29
         * money : 100.0
         * coupon_name : 支付优惠券
         * code : AC12-ADRE-ER15
         */

        private String end_time;
        private String money;
        private String coupon_name;
        private String code;

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
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
    }
}
