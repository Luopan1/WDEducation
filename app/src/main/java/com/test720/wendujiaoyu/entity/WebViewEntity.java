package com.test720.wendujiaoyu.entity;

import java.io.Serializable;

/**
 * Created by LuoPan on 2017/8/28 10:27.
 */

public class WebViewEntity implements Serializable{

    /**
     * msg : 查询成功
     * data : {"examinationTime":{"days":53,"phone":"400-1055-899","frequency":"172"},"newregisterMsg":{"handle":"http://www.dzkonline.com/index.php/myapi/index/businessManagement/type/1","notice":"http://www.dzkonline.com/index.php/myapi/index/businessInstructions/type/1"}}
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

    public static class DataBean implements Serializable{
        /**
         * examinationTime : {"days":53,"phone":"400-1055-899","frequency":"172"}
         * newregisterMsg : {"handle":"http://www.dzkonline.com/index.php/myapi/index/businessManagement/type/1","notice":"http://www.dzkonline.com/index.php/myapi/index/businessInstructions/type/1"}
         */

        private ExaminationTimeBean examinationTime;
        private NewregisterMsgBean newregisterMsg;

        public ExaminationTimeBean getExaminationTime() {
            return examinationTime;
        }

        public void setExaminationTime(ExaminationTimeBean examinationTime) {
            this.examinationTime = examinationTime;
        }

        public NewregisterMsgBean getNewregisterMsg() {
            return newregisterMsg;
        }

        public void setNewregisterMsg(NewregisterMsgBean newregisterMsg) {
            this.newregisterMsg = newregisterMsg;
        }

        public static class ExaminationTimeBean implements Serializable{
            /**
             * days : 53
             * phone : 400-1055-899
             * frequency : 172
             */

            private int days;
            private String phone;
            private String frequency;

            public int getDays() {
                return days;
            }

            public void setDays(int days) {
                this.days = days;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getFrequency() {
                return frequency;
            }

            public void setFrequency(String frequency) {
                this.frequency = frequency;
            }
        }

        public static class NewregisterMsgBean implements Serializable{
            /**
             * handle : http://www.dzkonline.com/index.php/myapi/index/businessManagement/type/1
             * notice : http://www.dzkonline.com/index.php/myapi/index/businessInstructions/type/1
             */

            private String handle;
            private String notice;

            public String getHandle() {
                return handle;
            }

            public void setHandle(String handle) {
                this.handle = handle;
            }

            public String getNotice() {
                return notice;
            }

            public void setNotice(String notice) {
                this.notice = notice;
            }
        }
    }
}
