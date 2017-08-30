package com.test.com.study.bean;

/**
 * Created by jie on 2017/8/12.
 */

public class CurriculumClassificationListBean {

    /**
     * id : 188
     * name : 视频2
     * video_src : Uploads/video/哲学类1课1/视频2/视频2.mp4
     * voice_src : Public/Video/wendu.mp3
     * handout_src : Public/Video/wendu.doc
     * stats  0 未下载过  1 开始下载  2暂停 3成功
     */



    private String id;
    private String name;
    private String video_src;
    private String voice_src;
    private String handout_src;
    private int stats;
    private String progress;

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public int getStats() {
        return stats;
    }

    public void setStats(int stats) {
        this.stats = stats;
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

    public String getVideo_src() {
        return video_src;
    }

    public void setVideo_src(String video_src) {
        this.video_src = video_src;
    }

    public String getVoice_src() {
        return voice_src;
    }

    public void setVoice_src(String voice_src) {
        this.voice_src = voice_src;
    }

    public String getHandout_src() {
        return handout_src;
    }

    public void setHandout_src(String handout_src) {
        this.handout_src = handout_src;
    }
}
