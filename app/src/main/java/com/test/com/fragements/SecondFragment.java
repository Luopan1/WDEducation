package com.test.com.fragements;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.test.com.R;
import com.test.com.WebViewActivity;
import com.test.com.adapter.CommonAdaper;
import com.test.com.adapter.ViewHolder;
import com.test.com.adapter.ZiXunRecylerAdapter;
import com.test.com.baseUi.BaseFragment;
import com.test.com.entity.ZiXunEntity;
import com.test.com.secondFragmentActivity.JoinUsActivity;
import com.test.com.secondFragmentActivity.KnownZIKaoActivity;
import com.test.com.secondFragmentActivity.StudentTouGaoActivity;
import com.test.com.secondFragmentActivity.ZiKaoInformationActivity;
import com.test.com.secondFragmentActivity.ZiKaoMajorActivity;
import com.test.com.utills.NetImageLoaderHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by LuoPan on 2017/7/31 22:34.
 */

public class SecondFragment extends BaseFragment implements ZiXunRecylerAdapter.OnItemClickListener, ZiXunRecylerAdapter.callBack, View.OnClickListener {
    private int[] dots = {R.mipmap.circle1, R.mipmap.circle2};
    private String[] images = {"http://pic41.nipic.com/20140425/18162057_145619712142_2.jpg",
            "http://pic2.ooopic.com/11/77/47/63b1OOOPIC74.jpg",
            "http://pic65.nipic.com/file/20150425/13839354_210311767000_2.jpg"};
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

    @Override
    protected void initView() {
        mListView = getView(R.id.listView);
        final View headerView = View.inflate(getActivity(), R.layout.item_second_fragment_header, null);
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

    }

    @Override
    protected void setListener() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ShowToast("点击了" + position + "个");
            }
        });
        mZikaoLinear.setOnClickListener(this);
        mLiaojiezikao.setOnClickListener(this);
        mZikaozhuanye.setOnClickListener(this);
        mMineAdvantage.setOnClickListener(this);
        mGreateLearner.setOnClickListener(this);
        mLearnerwrote.setOnClickListener(this);
        mJoinUs.setOnClickListener(this);
        mContactUs.setOnClickListener(this);


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
                bundle.putString(WebViewActivity.URL, "http://docs.easemob.com/im/200androidclientintegration/10androidsdkimport");
                jumpToActivity(WebViewActivity.class, bundle, false);
                break;
            case R.id.greateLearner:
                bundle = new Bundle();
                bundle.putString(WebViewActivity.TITLE, "优秀学员");
                bundle.putString(WebViewActivity.URL, "http://cn.bing.com/images/trending?form=Z9LH");
                jumpToActivity(WebViewActivity.class, bundle, false);
                break;
            case R.id.learnerwrote:
                jumpToActivity(StudentTouGaoActivity.class, false);
                break;
            case R.id.joinUs:
                jumpToActivity(JoinUsActivity.class, false);
                break;
            case R.id.contactUs:
                break;


        }

    }

    @Override
    protected void initData() {

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


        List<ZiXunEntity> lists = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            lists.add(new ZiXunEntity("http://pic65.nipic.com/file/20150425/13839354_210311767000_2.jpg", 1, "2017-3-20", "2017年4月(17.1次)四川省高等教育自学考试通知"));
        }

        CommonAdaper adaper = new CommonAdaper<ZiXunEntity>(getActivity(), lists, R.layout.item_zixunlistview) {
            @Override
            public void convert(ViewHolder holder, ZiXunEntity item, int position) {
                holder.setImageByUrl(R.id.newImage, item.getImageUrl());
                holder.setText(R.id.Title, item.getTitle());
                holder.setText(R.id.sendTime, item.getTime());

                holder.setImageByUrl(R.id.newImage1, item.getImageUrl());
                holder.setText(R.id.Title1, item.getTitle());
                holder.setText(R.id.sendTime1, item.getTime());

                holder.setImageByUrl(R.id.newImage2, item.getImageUrl());
                holder.setText(R.id.Title2, item.getTitle());
                holder.setText(R.id.sendTime2, item.getTime());


                holder.getView(R.id.seeAll).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ShowToast("点击了查看更多");
                    }
                });


            }
        };

        mListView.setAdapter(adaper);
    }


    @Override
    protected int setLayoutResID() {
        return R.layout.fragment_second;
    }

    @Override
    public void onItemClick(View view, int position) {
        ShowToast("点击了第" + position + "个");
    }

    @Override
    public void send(View v) {
        ShowToast("点击了查看更多");
    }

    @Override
    public void onResume() {
        super.onResume();
        mConvenientBanner.startTurning(2500);
    }

    @Override
    public void onPause() {
        super.onPause();
        mConvenientBanner.stopTurning();
    }


}
