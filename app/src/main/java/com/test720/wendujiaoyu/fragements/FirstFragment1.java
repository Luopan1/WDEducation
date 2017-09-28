package com.test720.wendujiaoyu.fragements;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;
import com.test720.wendujiaoyu.NewsCallBack;
import com.test720.wendujiaoyu.R;
import com.test720.wendujiaoyu.UrlFactory;
import com.test720.wendujiaoyu.WebViewActivity;
import com.test720.wendujiaoyu.adapter.CommonAdaper;
import com.test720.wendujiaoyu.adapter.ViewHolder;
import com.test720.wendujiaoyu.baseUi.BaseFragment;
import com.test720.wendujiaoyu.entity.ShouYeInfo;
import com.test720.wendujiaoyu.firstFragmentactivity.AskActivity;
import com.test720.wendujiaoyu.firstFragmentactivity.CheckActivity;
import com.test720.wendujiaoyu.firstFragmentactivity.NewStudentsCheckInActivity;
import com.test720.wendujiaoyu.study.activity.StudyHomeActivity;
import com.test720.wendujiaoyu.utills.NetImageLoaderHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by LuoPan on 2017/8/3 10:52.
 */

public class FirstFragment1 extends BaseFragment implements View.OnClickListener {
    private static NewsCallBack mCallBack;
    private int[] dots = {R.mipmap.circle1, R.mipmap.circle2};
    private String[] images;
    private ConvenientBanner mConvenientBanner;
    private RelativeLayout mChecKRelative;
    private RelativeLayout mAskRelative;
    private LinearLayout mNewsStudentCheck;
    private LinearLayout mKaoshi;
    private LinearLayout mBiye;
    private LinearLayout mXuwei;
    private LinearLayout mYingyu;
    private LinearLayout mBuban;
    private RelativeLayout studyRelative;
    RelativeLayout Relative;

    private TextView mFrequency;
    private TextView mTime;
    private ListView mListView;
    private List<ShouYeInfo.DataBean.NoticeBean> mZiXunList;
    private ShouYeInfo mShouYe;
    private List<ShouYeInfo.DataBean.BannerBean> mBannerLists;


    public FirstFragment1() {
    }

    public static final FirstFragment1 newInstance(NewsCallBack callBack) {
        mCallBack = callBack;
        FirstFragment1 Fragment = new FirstFragment1();
        return Fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        mConvenientBanner.startTurning(3000);


    }

    @Override
    public void onPause() {
        super.onPause();
        mConvenientBanner.stopTurning();
    }

    @Override
    protected void initView() {
        mListView = getView(R.id.lsitview);
        Relative=getView(R.id.Relative);
        View headerview = getActivity().getLayoutInflater().inflate(R.layout.fragment_first1, null, false);
        mConvenientBanner = (ConvenientBanner) headerview.findViewById(R.id.CarouselImage);
        mChecKRelative = (RelativeLayout) headerview.findViewById(R.id.ChecKRelative);
        studyRelative = (RelativeLayout) headerview.findViewById(R.id.studyRelative);
        mAskRelative = (RelativeLayout) headerview.findViewById(R.id.askRelative);
        mNewsStudentCheck = (LinearLayout) headerview.findViewById(R.id.newsStudentCheck);
        mKaoshi = (LinearLayout) headerview.findViewById(R.id.kaoshi);
        mBiye = (LinearLayout) headerview.findViewById(R.id.biye);
        mXuwei = (LinearLayout) headerview.findViewById(R.id.xuewei);
        mYingyu = (LinearLayout) headerview.findViewById(R.id.yingyu);
        mBuban = (LinearLayout) headerview.findViewById(R.id.buban);
        mFrequency = (TextView) headerview.findViewById(R.id.frequency);
        mTime = (TextView) headerview.findViewById(R.id.time);
        mListView.addHeaderView(headerview);


    }


    @Override
    protected void initData() {
        RequestParams params = new RequestParams();

        getDataFromInternet(UrlFactory.ShouYe, params, 0);
    }


    private void setData() {
        List<String> imagsList = Arrays.asList(images);//将图片放进这个集合
        Log.e("==", imagsList.toString());
        mConvenientBanner.setPointViewVisible(true);//设置小圆点可见
        mConvenientBanner.setPageIndicator(dots);//设置小圆点
        mConvenientBanner.setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);
        mConvenientBanner.setManualPageable(true);//手动滑动
        mConvenientBanner.startTurning(3000);//自动轮滑
        mConvenientBanner.setPages(new CBViewHolderCreator<NetImageLoaderHolder>() {
            @Override
            public NetImageLoaderHolder createHolder() {
                return new NetImageLoaderHolder();
            }
        }, imagsList);

        mFrequency.setText("您距离第" + mShouYe.getData().getExaminationTime().getFrequency() + "次自考还有");
        mTime.setText(mShouYe.getData().getExaminationTime().getDays() + "");


    }

    private void setAdapter() {
        CommonAdaper commonAdaper = new CommonAdaper<ShouYeInfo.DataBean.NoticeBean>(getActivity(), mZiXunList, R.layout.item_shhouyenewslistview) {

            @Override
            public void convert(ViewHolder holder, ShouYeInfo.DataBean.NoticeBean item, final int position) {
                Log.e("TAG+mZiXunList", mZiXunList.toString());
                holder.setText(R.id.title, item.getTitle());
                holder.setText(R.id.descriptio, item.getDescribe());
                holder.setImageByUrl(R.id.imageView, UrlFactory.imagePath + item.getCover_img());
                holder.getView(R.id.liner_all).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putString(WebViewActivity.URL, UrlFactory.activityInfo + "/activityId/" + mZiXunList.get(position).getActivity_id());
                        Log.e("TAG", mZiXunList.get(position).getActivity_id());
                        bundle.putString(WebViewActivity.TITLE, "消息详情");
                        jumpToActivity(WebViewActivity.class, bundle, false);
                    }
                });

            }
        };
        mListView.setAdapter(commonAdaper);
    }

    @Override
    protected void setListener() {
        mChecKRelative.setOnClickListener(this);
        mAskRelative.setOnClickListener(this);
        mNewsStudentCheck.setOnClickListener(this);
        mKaoshi.setOnClickListener(this);
        mBiye.setOnClickListener(this);
        mXuwei.setOnClickListener(this);
        mYingyu.setOnClickListener(this);
        mBuban.setOnClickListener(this);
        studyRelative.setOnClickListener(this);
        Relative.setOnClickListener(this);

        /*mConvenientBanner.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (mBannerLists.get(position).getTarget_type().equals("1")) {


                } else if (mBannerLists.get(position).getTarget_type().equals("2")) {
                    Bundle bundle = new Bundle();
                    bundle.putString(WebViewActivity.TITLE, "详情");
                    bundle.putString(WebViewActivity.URL, UrlFactory.bannerDetail + "/id/" + mBannerLists.get(position).getBa_id());
                    jumpToActivity(WebViewActivity.class, bundle, false);
                } else if (mBannerLists.get(position).getTarget_type().equals("3")) {
                    Bundle bundle = new Bundle();
                    bundle.putString(WebViewActivity.TITLE, "详情");
                    bundle.putString(WebViewActivity.URL, mBannerLists.get(position).getTarget_url());
                    jumpToActivity(WebViewActivity.class, bundle, false);
                }
            }
        });*/

    }

    @Override
    protected int setLayoutResID() {
        return R.layout.fragment_first2;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.studyRelative:
                if (isLogined()) {
                    jumpToActivity(StudyHomeActivity.class, false);
                }
                break;
            case R.id.ChecKRelative:
                jumpToActivity(CheckActivity.class, false);
                break;
            case R.id.askRelative:
                jumpToActivity(AskActivity.class, false);
                break;
            case R.id.newsStudentCheck:
                Bundle bundle = new Bundle();
                bundle.putInt("type", 1);
                jumpToActivity(NewStudentsCheckInActivity.class, bundle, false);


                break;
            case R.id.kaoshi:
                bundle = new Bundle();
                bundle.putInt("type", 2);
                jumpToActivity(NewStudentsCheckInActivity.class, bundle, false);
                break;
            case R.id.biye:
                bundle = new Bundle();
                bundle.putInt("type", 3);
                jumpToActivity(NewStudentsCheckInActivity.class, bundle, false);
                break;
            case R.id.xuewei:
                bundle = new Bundle();
                bundle.putInt("type", 4);
                jumpToActivity(NewStudentsCheckInActivity.class, bundle, false);
                break;
            case R.id.yingyu:
                bundle = new Bundle();
                bundle.putInt("type", 5);
                jumpToActivity(NewStudentsCheckInActivity.class, bundle, false);
                break;
            case R.id.buban:
                bundle = new Bundle();
                bundle.putInt("type", 6);
                jumpToActivity(NewStudentsCheckInActivity.class, bundle, false);
                break;
            case R.id.Relative:

                break;

        }
    }

    @Override
    public void getSuccess(JSONObject object, int what) {
        super.getSuccess(object, what);
        switch (what) {
            case 0:
                Gson gson = new Gson();
                Log.e("TAG+shouye", object.toString());
                mShouYe = gson.fromJson(object.toString(), ShouYeInfo.class);
                mBannerLists = mShouYe.getData().getBanner();
                images = new String[mBannerLists.size()];
                for (int i = 0; i < mBannerLists.size(); i++) {
                    images[i] = UrlFactory.imagePath + mBannerLists.get(i).getBa_img();
                }
                mZiXunList = new ArrayList<ShouYeInfo.DataBean.NoticeBean>();
                mZiXunList.addAll(mShouYe.getData().getNotice());
                if (mShouYe.getData().getMsgCount().equals("0")) {
                    mCallBack.hasNes();
                }
                setData();
                setAdapter();
                break;

        }


    }

}
