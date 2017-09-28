package com.test720.wendujiaoyu.entity;

import java.util.List;

/**
 * Created by LuoPan on 2017/8/8 17:37.
 */

public class ShouYeInfo {


    /**
     * msg : 查询成功
     * data : {"icon":[{"icon_id":"1","icon_img":"Uploads/icon/f.png","icon_name":"新生注册"},{"icon_id":"2","icon_img":"Uploads/icon/e.png","icon_name":"考试报考"},{"icon_id":"3","icon_img":"Uploads/icon/a.png","icon_name":"毕业申请"},{"icon_id":"4","icon_img":"Uploads/icon/b.png","icon_name":"学位申请"},{"icon_id":"5","icon_img":"Uploads/icon/d.png","icon_name":"学位英语"},{"icon_id":"6","icon_img":"Uploads/icon/g.png","icon_name":"准考证补办"}],"examinationTime":{"days":56,"frequency":"172"},"msgCount":"0","notice":[{"describe":"描述描述描述描述描述描述","title":"标题标题标题标题标题标题标题标题","cover_img":"Public/Img/2017-08-17/599539197714f.jpg","activity_id":"4"},{"describe":"描述描述描述描述描述描述","title":"标题标题标题标题标题标题标题标题","cover_img":"Public/Img/2017-08-17/599539103f808.jpg","activity_id":"5"}],"banner":[{"ba_img":"Public/Img/4.jpg","target_url":"","target_type":"2","ba_id":"1"},{"ba_img":"Public/Img/index_lb_03.png","target_url":"","target_type":"2","ba_id":"2"},{"ba_img":"Public/Img/2017-08-17/59953acd045cd.jpg","target_url":"","target_type":"2","ba_id":"7"}]}
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
        /**
         * icon : [{"icon_id":"1","icon_img":"Uploads/icon/f.png","icon_name":"新生注册"},{"icon_id":"2","icon_img":"Uploads/icon/e.png","icon_name":"考试报考"},{"icon_id":"3","icon_img":"Uploads/icon/a.png","icon_name":"毕业申请"},{"icon_id":"4","icon_img":"Uploads/icon/b.png","icon_name":"学位申请"},{"icon_id":"5","icon_img":"Uploads/icon/d.png","icon_name":"学位英语"},{"icon_id":"6","icon_img":"Uploads/icon/g.png","icon_name":"准考证补办"}]
         * examinationTime : {"days":56,"frequency":"172"}
         * msgCount : 0
         * notice : [{"describe":"描述描述描述描述描述描述","title":"标题标题标题标题标题标题标题标题","cover_img":"Public/Img/2017-08-17/599539197714f.jpg","activity_id":"4"},{"describe":"描述描述描述描述描述描述","title":"标题标题标题标题标题标题标题标题","cover_img":"Public/Img/2017-08-17/599539103f808.jpg","activity_id":"5"}]
         * banner : [{"ba_img":"Public/Img/4.jpg","target_url":"","target_type":"2","ba_id":"1"},{"ba_img":"Public/Img/index_lb_03.png","target_url":"","target_type":"2","ba_id":"2"},{"ba_img":"Public/Img/2017-08-17/59953acd045cd.jpg","target_url":"","target_type":"2","ba_id":"7"}]
         */

        private ExaminationTimeBean examinationTime;
        private String msgCount;
        private List<IconBean> icon;
        private List<NoticeBean> notice;
        private List<BannerBean> banner;

        public ExaminationTimeBean getExaminationTime() {
            return examinationTime;
        }

        public void setExaminationTime(ExaminationTimeBean examinationTime) {
            this.examinationTime = examinationTime;
        }

        public String getMsgCount() {
            return msgCount;
        }

        public void setMsgCount(String msgCount) {
            this.msgCount = msgCount;
        }

        public List<IconBean> getIcon() {
            return icon;
        }

        public void setIcon(List<IconBean> icon) {
            this.icon = icon;
        }

        public List<NoticeBean> getNotice() {
            return notice;
        }

        public void setNotice(List<NoticeBean> notice) {
            this.notice = notice;
        }

        public List<BannerBean> getBanner() {
            return banner;
        }

        public void setBanner(List<BannerBean> banner) {
            this.banner = banner;
        }

        public static class ExaminationTimeBean {
            /**
             * days : 56
             * frequency : 172
             */

            private int days;
            private String frequency;

            public int getDays() {
                return days;
            }

            public void setDays(int days) {
                this.days = days;
            }

            public String getFrequency() {
                return frequency;
            }

            public void setFrequency(String frequency) {
                this.frequency = frequency;
            }
        }

        public static class IconBean {
            /**
             * icon_id : 1
             * icon_img : Uploads/icon/f.png
             * icon_name : 新生注册
             */

            private String icon_id;
            private String icon_img;
            private String icon_name;

            public String getIcon_id() {
                return icon_id;
            }

            public void setIcon_id(String icon_id) {
                this.icon_id = icon_id;
            }

            public String getIcon_img() {
                return icon_img;
            }

            public void setIcon_img(String icon_img) {
                this.icon_img = icon_img;
            }

            public String getIcon_name() {
                return icon_name;
            }

            public void setIcon_name(String icon_name) {
                this.icon_name = icon_name;
            }
        }

        public static class NoticeBean {
            /**
             * describe : 描述描述描述描述描述描述
             * title : 标题标题标题标题标题标题标题标题
             * cover_img : Public/Img/2017-08-17/599539197714f.jpg
             * activity_id : 4
             */

            private String describe;
            private String title;
            private String cover_img;
            private String activity_id;

            public String getDescribe() {
                return describe;
            }

            public void setDescribe(String describe) {
                this.describe = describe;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getCover_img() {
                return cover_img;
            }

            public void setCover_img(String cover_img) {
                this.cover_img = cover_img;
            }

            public String getActivity_id() {
                return activity_id;
            }

            public void setActivity_id(String activity_id) {
                this.activity_id = activity_id;
            }
        }

        public static class BannerBean {
            /**
             * ba_img : Public/Img/4.jpg
             * target_url :
             * target_type : 2
             * ba_id : 1
             */

            private String ba_img;
            private String target_url;
            private String target_type;
            private String ba_id;

            public String getBa_img() {
                return ba_img;
            }

            public void setBa_img(String ba_img) {
                this.ba_img = ba_img;
            }

            public String getTarget_url() {
                return target_url;
            }

            public void setTarget_url(String target_url) {
                this.target_url = target_url;
            }

            public String getTarget_type() {
                return target_type;
            }

            public void setTarget_type(String target_type) {
                this.target_type = target_type;
            }

            public String getBa_id() {
                return ba_id;
            }

            public void setBa_id(String ba_id) {
                this.ba_id = ba_id;
            }
        }
    }
}
