package com.test.com.study.network;


import com.alibaba.fastjson.JSONObject;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by jie on 2016/12/8.
 */

public interface  ApiService {

    /**
     * 学习首页
     * @param uid 用户ID
     */
    @POST("Study/index")
    @FormUrlEncoded
    Observable<JSONObject> getStudyIndex(@Field("uid") String uid);

    /**
     * 类型入口接口
     */
    @POST("Study/Entrance")
    @FormUrlEncoded
    Observable<JSONObject> getStudyEntrance(@Field("type") String type);



    /**
     * 视频课程接口
     * @param tid 类型ID
     */
    @POST("StudyVideo/index")
    @FormUrlEncoded
    Observable<JSONObject> getStudyVideoIndex(@Field("tid") String tid);

    /**
     * 音频课程接口
     * @param tid 类型ID
     */
    @POST("StudyVoice/index")
    @FormUrlEncoded
    Observable<JSONObject> getStudyVoiceIndex(@Field("tid") String tid);

    /**
     * 讲义课程接口
     * @param tid 类型ID
     */
    @POST("StudyHandout/index")
    @FormUrlEncoded
    Observable<JSONObject> getStudyHandoutIndex(@Field("tid") String tid);

    /**
     * 题库课程接口
     * @param tid 类型ID
     */
    @POST("StudyQuestions/index")
    @FormUrlEncoded
    Observable<JSONObject> getStudyQuestionsIndex(@Field("tid") String tid);

    /**
     * 收藏课程接口
     * @param tid 类型ID
     * @param uid 用户ID
     */
    @POST("StudyCollection/index")
    @FormUrlEncoded
    Observable<JSONObject> getStudyCollectionIndex(@Field("tid") String tid, @Field("uid") String uid);

    /**
     * 错题集课程接口
     * @param tid 类型ID
     * @param uid 用户ID
     */
    @POST("StudyError/index")
    @FormUrlEncoded
    Observable<JSONObject> getStudyErrorIndex(@Field("tid") String tid, @Field("uid") String uid);


    /**
     * 视频列表接口
     * @param cid 用户ID
     * @param page 页数
     */
    @POST("StudyVideo/videoList")
    @FormUrlEncoded
    Observable<JSONObject> getStudyVideoVideoList(@Field("cid") String cid, @Field("page") int page);


    /**
     * 音频列表接口
     * @param cid 课程的ID
     * @param page 页数
     */
    @POST("StudyVoice/voiceList")
    @FormUrlEncoded
    Observable<JSONObject> getStudyVoiceVoiceList(@Field("cid") String cid, @Field("page") int page);

    /**
     * 讲义列表接口
     * @param cid 课程的ID
     * @param page 页数
     */
    @POST("StudyHandout/HandoutList")
    @FormUrlEncoded
    Observable<JSONObject> getStudyHandoutHandoutList(@Field("cid") String cid, @Field("page") int page);

    /**
     * 题库列表接口
     * @param cid 课程的ID
     * @param page 页数
     */
    @POST("StudyQuestions/QusestionList")
    @FormUrlEncoded
    Observable<JSONObject> getStudyQuestionsQusestionList(@Field("cid") String cid, @Field("page") int page);

    /**
     * 收藏列表接口
     * @param cid 课程的ID
     * @param uid 用户ID
     * @param page 页数
     */
    @POST("StudyCollection/collectionList")
    @FormUrlEncoded
    Observable<JSONObject> getStudyCollectionCollectionList(@Field("cid") String cid, @Field("uid") String uid, @Field("page") int page);


    /**
     * 收藏列表接口
     * @param cid 课程的ID
     * @param uid 用户ID
     * @param page 页数
     */
    @POST("StudyError/errorCourseList")
    @FormUrlEncoded
    Observable<JSONObject> getStudyErrorErrorCourseList(@Field("cid") String cid, @Field("uid") String uid, @Field("page") int page);



    /**
     * 题详情接口
     * @param qid 试卷ID
     * @param uid 用户ID
     */
    @POST("StudyQuestions/QusestionDeails")
    @FormUrlEncoded
    Observable<JSONObject> getStudyQuestionsQusestionDeails(@Field("qid") String qid, @Field("uid") String uid);


    /**
     * 错题详情接口
     * @param cid * @param qid 试卷ID
     * @param qid 试卷ID
     * @param uid 用户ID
     */
    @POST("StudyError/errorDeails")
    @FormUrlEncoded
    Observable<JSONObject> getStudyErrorErrorDeails(@Field("cid") String cid, @Field("qid") String qid, @Field("uid") String uid);




    /**
     * 交卷接口
     * @param qid 试卷ID
     * @param uid 用户ID
     * @param fraction 成绩
     * @param cid 课程ID
     * @param tid 错题的ID
     */
    @POST("StudyQuestions/returnFraction")
    @FormUrlEncoded
    Observable<JSONObject> getStudyQuestionsReturnFraction(@Field("qid") String qid, @Field("uid") String uid, @Field("fraction") String fraction, @Field("cid") String cid, @Field("tid") String tid,@Field("did") String did);



    /**
     * 题收藏接口
     * @param tid 单题的ID
     * @param uid 用户ID
     * @param type 0取消收藏  1 收藏
     */
    @POST("StudyQuestions/Collection")
    @FormUrlEncoded
    Observable<JSONObject> getStudyQuestionsCollection(@Field("qid") String qid,@Field("tid") String tid, @Field("uid") String uid, @Field("type") String type);



    /**
     * 题详情接口
     * @param qid 试卷ID
     * @param cid 课程的ID
     * @param uid 用户ID
     */
    @POST("StudyCollection/CollectionDeails")
    @FormUrlEncoded
    Observable<JSONObject> getStudyCollectionCollectionDeails(@Field("qid") String qid, @Field("cid") String cid, @Field("uid") String uid);


    /**
     * 试看视频接口
     */
    @POST("StudyVideo/shikan")
    Observable<JSONObject> getStudyVideoLook();


    /**
     * 课程老师接口
     * @param cid 课程ID
     */
    @POST("StudyVideo/teacher")
    @FormUrlEncoded
    Observable<JSONObject> getStudyVideoTeacher(@Field("cid") String cid);


}
