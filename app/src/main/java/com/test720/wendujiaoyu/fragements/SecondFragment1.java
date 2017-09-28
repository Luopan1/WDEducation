package com.test720.wendujiaoyu.fragements;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;
import com.test720.wendujiaoyu.R;
import com.test720.wendujiaoyu.UrlFactory;
import com.test720.wendujiaoyu.WebViewActivity;
import com.test720.wendujiaoyu.adapter.ZIXunListAdapter;
import com.test720.wendujiaoyu.baseUi.BaseFragment;
import com.test720.wendujiaoyu.entity.Banner;
import com.test720.wendujiaoyu.entity.ZiXun;
import com.test720.wendujiaoyu.secondFragmentActivity.GreatLearnerAvtivity;
import com.test720.wendujiaoyu.secondFragmentActivity.JoinUsActivity;
import com.test720.wendujiaoyu.secondFragmentActivity.KnownZIKaoActivity;
import com.test720.wendujiaoyu.secondFragmentActivity.StudentTouGaoActivity;
import com.test720.wendujiaoyu.secondFragmentActivity.ZiKaoInformationActivity;
import com.test720.wendujiaoyu.secondFragmentActivity.ZiKaoMajorActivity;
import com.test720.wendujiaoyu.utills.NetImageLoaderHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by LuoPan on 2017/7/31 22:34.
 */

public class SecondFragment1 extends BaseFragment implements View.OnClickListener, ZIXunListAdapter.callBack {
    private int[] dots = {R.mipmap.circle1, R.mipmap.circle2};
    private String[] images;
    private ConvenientBanner mConvenientBanner;
    private ListView mListView;
    private LinearLayout mZikaoLinear;
    private LinearLayout mLiaojiezikao;
    private LinearLayout mZikaozhuanye;
    private LinearLayout mMineAdvantage;
    private LinearLayout mGreateLearner;
    private LinearLayout mLearnerwrote;
    private LinearLayout mJoinUs;
    private LinearLayout mContactUs;
    private ZIXunListAdapter zAdapter;
    private List<ZiXun> lists;
    private List<Banner> mBanners;
    RelativeLayout Relative;

    @Override
    protected void initView() {
        mListView = getView(R.id.listView);
        final View headerView = View.inflate(getActivity(), R.layout.item_second_fragment_header1, null);
        mConvenientBanner = (ConvenientBanner) headerView.findViewById(R.id.convenientBaner);
        mListView.addHeaderView(headerView);
        mZikaoLinear = (LinearLayout) headerView.findViewById(R.id.zikaoLinear);
        mLiaojiezikao = (LinearLayout) headerView.findViewById(R.id.liaojiezikao);
        mZikaozhuanye = (LinearLayout) headerView.findViewById(R.id.zikaozhuanye);
        mMineAdvantage = (LinearLayout) headerView.findViewById(R.id.mineAdvantage);
        mGreateLearner = (LinearLayout) headerView.findViewById(R.id.greateLearner);
        mLearnerwrote = (LinearLayout) headerView.findViewById(R.id.learnerwrote);
        mJoinUs = (LinearLayout) headerView.findViewById(R.id.joinUs);
        mContactUs = (LinearLayout) headerView.findViewById(R.id.contactUs);
        Relative=getView(R.id.Relative);
    }

    @Override
    protected void setListener() {
        mZikaoLinear.setOnClickListener(this);
        mLiaojiezikao.setOnClickListener(this);
        mZikaozhuanye.setOnClickListener(this);
        mMineAdvantage.setOnClickListener(this);
        mGreateLearner.setOnClickListener(this);
        mLearnerwrote.setOnClickListener(this);
        mJoinUs.setOnClickListener(this);
        mContactUs.setOnClickListener(this);
        Relative.setOnClickListener(this);

      /*  mConvenientBanner.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (mBanners.get(position).getTarget_type().equals("1")) {


                } else if (mBanners.get(position).getTarget_type().equals("2")) {
                    Bundle bundle = new Bundle();
                    bundle.putString(WebViewActivity.TITLE, "详情");
                    bundle.putString(WebViewActivity.URL, UrlFactory.bannerDetail + "/id/" + mBanners.get(position).getBa_id());
                    jumpToActivity(WebViewActivity.class, bundle, false);
                } else if (mBanners.get(position).getTarget_type().equals("3")) {
                    Bundle bundle = new Bundle();
                    bundle.putString(WebViewActivity.TITLE, "详情");
                    //                    bundle.putString(WebViewActivity.URL, mBanners.get(position).getTarget_url());
                    jumpToActivity(WebViewActivity.class, bundle, false);
                }
            }
        });
*/

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.zikaoLinear:
                jumpToActivity(ZiKaoInformationActivity.class, false);
                break;
            case R.id.liaojiezikao:
                jumpToActivity(KnownZIKaoActivity.class, false);
                break;
            case R.id.zikaozhuanye:
                jumpToActivity(ZiKaoMajorActivity.class, false);
                break;
            case R.id.mineAdvantage:
                Bundle bundle = new Bundle();
                bundle.putString(WebViewActivity.TITLE, "我们的优势");
                bundle.putString(WebViewActivity.URL, UrlFactory.advantage);
                jumpToActivity(WebViewActivity.class, bundle, false);
                break;
            case R.id.greateLearner:

                jumpToActivity(GreatLearnerAvtivity.class, false);
                break;
            case R.id.learnerwrote:
                jumpToActivity(StudentTouGaoActivity.class, false);
                break;
            case R.id.joinUs:
                jumpToActivity(JoinUsActivity.class, false);
                break;
            case R.id.contactUs:
                bundle = new Bundle();
                bundle.putString(WebViewActivity.TITLE, "奖学金计划");
                bundle.putString(WebViewActivity.URL, UrlFactory.scholarshipPlan);
                jumpToActivity(WebViewActivity.class, bundle, false);
                break;
            case R.id.Relative:
                break;


        }

    }

    @Override
    protected void initData() {
        RequestParams params = new RequestParams();
        getDataFromInternet(UrlFactory.information, params, 0);
        showLoadingDialog("初始化中");
    }

    @Override
    public void getSuccess(JSONObject object, int what) {
        super.getSuccess(object, what);
        ZiXun mZixun = null;
        if (object.getInteger("code") == 1) {
            JSONObject data = object.getJSONObject("data");
            JSONArray array = data.getJSONArray("banner");
            Log.e("TGA+++", data.toString());
            mBanners = array.toJavaList(Banner.class);
            images = new String[mBanners.size()];
            for (int i = 0; i < mBanners.size(); i++) {
                images[i] = UrlFactory.baseUrl + mBanners.get(i).getBa_img();
            }

            Gson gson = new Gson();
            mZixun = gson.fromJson(object.toJSONString(), ZiXun.class);

        }

        lists = new ArrayList<>();
        lists.add(mZixun);
        setData();
        Log.e(TAG, lists.toString());

    }

    private void setData() {
        /**设置轮播图*/
        List<String> imagsList = Arrays.asList(images);//将图片放进这个集合
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

        /**设置下方listview的数据*/
        zAdapter = new ZIXunListAdapter(getActivity(), lists, this);
        mListView.setAdapter(zAdapter);
    }


    @Override
    protected int setLayoutResID() {
        return R.layout.fragment_second1;
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
    public void send(View v, int position) {
        if (position == 0) {
            jumpToActivity(KnownZIKaoActivity.class, false);
        } else if (position == 1) {
            jumpToActivity(ZiKaoMajorActivity.class, false);
        } else if (position == 2) {
            jumpToActivity(JoinUsActivity.class, false);
        }
    }
}
