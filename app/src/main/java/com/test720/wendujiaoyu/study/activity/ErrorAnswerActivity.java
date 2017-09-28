package com.test720.wendujiaoyu.study.activity;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.test720.wendujiaoyu.R;
import com.test720.wendujiaoyu.baseUi.BaseToolbarActivity;
import com.test720.wendujiaoyu.study.adapter.AnswerAdapter;
import com.test720.wendujiaoyu.study.adapter.AnswerErrorItemAdapter;
import com.test720.wendujiaoyu.study.bean.AnswerBean;
import com.test720.wendujiaoyu.study.bean.AnswerItemBean;
import com.test720.wendujiaoyu.study.network.RxSchedulersHelper;
import com.test720.wendujiaoyu.study.network.RxSubscriber;
import com.test720.wendujiaoyu.study.view.ListViewForScrollView;
import com.test720.wendujiaoyu.study.view.MyGridView;
import com.test720.wendujiaoyu.utills.Constants;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.test720.wendujiaoyu.R.id.tv_answer_title;

public class ErrorAnswerActivity extends BaseToolbarActivity {
    @BindView(R.id.tv_name)
    TextView tvName;

    @BindView(R.id.tv_multiple_choice_answer)
    TextView tvMultipleChoiceAnswer;


    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.layout_table)
    LinearLayout layoutTable;
    @BindView(R.id.id_drawerlayout)
    DrawerLayout idDrawerlayout;
    @BindView(R.id.layout_side_bg)
    LinearLayout layoutSideBg;
    @BindView(R.id.tv_points)
    TextView tvPoints;
    @BindView(R.id.tv_again)
    TextView tvAgain;
    @BindView(R.id.layout_score)
    LinearLayout layoutScore;
    @BindView(R.id.iv_collection)
    ImageView ivCollection;
    @BindView(R.id.layout_collection)
    RelativeLayout layoutCollection;
    @BindView(R.id.layout_bg)
    RelativeLayout layoutBg;
    @BindView(R.id.layout_fanhui)
    RelativeLayout layoutFanhui;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.layout_mulv)
    RelativeLayout layoutMulv;
    private List<AnswerBean> answerBeanList = new ArrayList<>();
    private List<AnswerBean> originalDataanswerBeanList = new ArrayList<>();
    private HomeworkAdapter homeworkAdapter;
    private int answerCount = 0; //0 不查看答案  1  查看答案
    private int papersCount = 0;//0 未交卷  1  已交卷
    private List<AnswerBean> SingleAnswerBeanList = new ArrayList<>(); //单选
    private List<AnswerBean> ManyAnswerBeanList = new ArrayList<>(); //多选
    private List<AnswerBean> ShortAnswerBeanList = new ArrayList<>(); //简单
    private AnswerErrorItemAdapter SingleanswerItemAdapter;
    private AnswerErrorItemAdapter ManyanswerItemAdapter;
    private AnswerErrorItemAdapter ShortanswerItemAdapter;
    private List<String> errorList = new ArrayList<>();

    @Override
    protected int getContentView() {
        return R.layout.activity_error_answer;
    }

    @Override
    protected void initData() {
        tvTitle.setText(getIntent().getStringExtra("title"));
        idDrawerlayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        for (int i = 0; i < layoutTable.getChildCount(); i++) {
            layoutTable.getChildAt(i).setOnClickListener(new TabHostClickListener(i));
        }
        getStudyErrorErrorDeails();
    }

    @Override
    public void RightOnClick() {
        super.RightOnClick();
        getAnswerList();
        showDrawer();
    }

    @Override
    protected void setListener() {
        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int state) {
                getAccordingAnswer(state);
                WhetherCollecting();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    //判断侧滑是否出现  出现收回，否则显示
    public void showDrawer() {
        if (idDrawerlayout.isDrawerOpen(Gravity.RIGHT)) {
            idDrawerlayout.closeDrawer(Gravity.RIGHT);
        } else {
            idDrawerlayout.openDrawer(Gravity.RIGHT);
        }

    }


    @Override
    protected void initBase() {
        isshowActionbar = false;
    }



    private void getAccordingAnswer(int state) {
        if (answerCount == 1) {
            if ("单项选择题".equals(answerBeanList.get(state).getType()) || "多项选择题".equals(answerBeanList.get(state).getType())) {
                tvMultipleChoiceAnswer.setVisibility(View.VISIBLE);
                tvMultipleChoiceAnswer.setText("正确答案:");
                for (int i = 0; i < answerBeanList.get(state).getTrue_list().size(); i++) {
                    if (answerBeanList.get(state).getTrue_list().size() - 1 == i) {
                        tvMultipleChoiceAnswer.append(getAnswer(answerBeanList.get(state).getTrue_list().get(i)));
                    } else {
                        tvMultipleChoiceAnswer.append(getAnswer(answerBeanList.get(state).getTrue_list().get(i)) + ",");
                    }
                }
            } else {
                tvMultipleChoiceAnswer.setVisibility(View.GONE);
            }
        } else {
            tvMultipleChoiceAnswer.setVisibility(View.GONE);
        }
    }


    @OnClick({R.id.layout_fanhui, R.id.layout_mulv, R.id.layout_collection, R.id.tv_again})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_fanhui:
                finish();
                break;
            case R.id.layout_mulv://目录
                getAnswerList();
                showDrawer();
                break;
            case R.id.layout_collection://收藏
                getStudyQuestionsCollection(viewpager.getCurrentItem());
                break;
            case R.id.tv_again://再来一次
                layoutScore.setVisibility(View.GONE);
                answerCount = 0;
                papersCount = 0;
                tvMultipleChoiceAnswer.setVisibility(View.GONE);
                for (int i = 0; i < answerBeanList.size(); i++) {
                    answerBeanList.get(i).setSelectedIndex(null);
                }
                homeworkAdapter.notifyDataSetChanged();
                layoutTable.getChildAt(1).setVisibility(View.VISIBLE);
                layoutTable.getChildAt(2).setVisibility(View.VISIBLE);
                viewpager.setCurrentItem(0);
                getAnswerList();
                showDrawer();
                break;
        }
    }


    /**
     * tabhost选中切换
     */
    class TabHostClickListener implements View.OnClickListener {
        int index;

        TabHostClickListener(int index) {
            this.index = index;
        }

        @Override
        public void onClick(View v) {//tabhost选中切换

            if (index == 0) {
                if (viewpager.getCurrentItem() == 0)
                    ShowToast("亲，这已经是第一题了");
                else
                    viewpager.setCurrentItem(viewpager.getCurrentItem() - 1);
            } else if (index == 1) {
                getStudyQuestionsReturnFraction();
            } else if (index == 2) {
                if (answerCount == 0)
                    answerCount = 1;
                else
                    answerCount = 0;
                getAccordingAnswer(viewpager.getCurrentItem());
                homeworkAdapter.notifyDataSetChanged();
            } else if (index == 3) {
                if (viewpager.getCurrentItem() == answerBeanList.size() - 1)
                    ShowToast("亲，这已经是最后一题了");
                else
                    viewpager.setCurrentItem(viewpager.getCurrentItem() + 1);
            }
        }
    }


    //适配器
    public class HomeworkAdapter extends PagerAdapter {

        Context mContext;
        List<AnswerBean> list;

        public HomeworkAdapter(Context mContext, List<AnswerBean> list) {
            this.mContext = mContext;
            this.list = list;
        }

        // 可以删除这段代码看看，数据源更新而viewpager不更新的情况
        private int mChildCount = 0;

        @Override
        public void notifyDataSetChanged() {
            // 重写这个方法，取到子Fragment的数量，用于下面的判断，以执行多少次刷新
            mChildCount = getCount();
            super.notifyDataSetChanged();
        }

        @Override
        public int getItemPosition(Object object) {
            if (mChildCount > 0) {
                // 这里利用判断执行若干次不缓存，刷新
                mChildCount--;
                // 返回这个是强制ViewPager不缓存，每次滑动都刷新视图
                return POSITION_NONE;
            }
            // 这个则是缓存不刷新视图
            return super.getItemPosition(object);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            View view = View.inflate(container.getContext(), R.layout.item_answer_view_pager, null);
            final ViewHolder viewHolder = new ViewHolder(view);
            viewHolder.tvTitle.setText(list.get(position).getType());
            viewHolder.tvContent.setText((position + 1) + "." + list.get(position).getTitile());

            if ("1".equals(list.get(position).getType_name()) || "2".equals(list.get(position).getType_name())) {
                viewHolder.tvAnswer.setVisibility(View.GONE);
                viewHolder.lvAnswer.setVisibility(View.VISIBLE);
                List<AnswerItemBean> answerItemBeanList = new ArrayList<>();
                for (int i = 0; i < list.get(position).getAnswer_list().size(); i++) {
                    AnswerItemBean answerItemBean = new AnswerItemBean();
                    answerItemBean.setTitle(list.get(position).getAnswer_list().get(i));
                    String content = "";
                    if (list.get(position).getSelectedIndex() != null) {
                        content = list.get(position).getSelectedIndex();
                    }
                    answerItemBean.setSelectedIndex(content.contains((i + 1) + "") ? 1 : 0);
                    if (answerCount == 1) {
                        int AnswerCount = 0;
                        for (String s : list.get(position).getTrue_list()) {
                            if(s.equals((i + 1) + ""))
                                AnswerCount ++;
                        }
                        if(AnswerCount > 0)
                            answerItemBean.setCorrectAnswerCount(1);
                        else
                            answerItemBean.setCorrectAnswerCount(0);
                    } else {
                        answerItemBean.setCorrectAnswerCount(0);
                    }
                    answerItemBeanList.add(answerItemBean);
                }
                AnswerAdapter answerAdapter = new AnswerAdapter(answerItemBeanList, mActivity);
                viewHolder.lvAnswer.setAdapter(answerAdapter);
                viewHolder.lvAnswer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int count, long id) {
                        if (answerCount == 1)
                            return;
                        if ("1".equals(list.get(position).getType_name())) {
                            list.get(position).setSelectedIndex((count + 1) + ",");
                        } else if ("2".equals(list.get(position).getType_name())) {
                            if (list.get(position).getSelectedIndex() != null) {

                                if (list.get(position).getSelectedIndex().contains((count + 1) + ",")) {
                                    list.get(position).setSelectedIndex(list.get(position).getSelectedIndex().replace((count + 1) + ",", ""));
                                } else {
                                    list.get(position).setSelectedIndex(list.get(position).getSelectedIndex() + (count + 1) + ",");
                                }
                            } else {
                                list.get(position).setSelectedIndex((count + 1) + ",");
                            }

                        }
                        notifyDataSetChanged();
                    }
                });
            } else {
                viewHolder.lvAnswer.setVisibility(View.GONE);
                if (answerCount == 1) {
                    viewHolder.tvAnswerTtle.setVisibility(View.VISIBLE);
                    viewHolder.tvAnswer.setVisibility(View.VISIBLE);
                    if(list.get(position).getTrue_list().size() > 0)
                        viewHolder.tvAnswer.setText(list.get(position).getTrue_list().get(0));
                    else
                        viewHolder.tvAnswer.setText("");
                } else {
                    viewHolder.tvAnswerTtle.setVisibility(View.GONE);
                    viewHolder.tvAnswer.setVisibility(View.GONE);
                }
            }
            container.addView(view);
            return view;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        public class ViewHolder {
            @BindView(R.id.tv_title)
            TextView tvTitle;
            @BindView(R.id.tv_content)
            TextView tvContent;
            @BindView(R.id.lv_answer)
            ListViewForScrollView lvAnswer;
            @BindView(R.id.tv_answer)
            TextView tvAnswer;
            @BindView(tv_answer_title)
            TextView tvAnswerTtle;


            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }

    public String getAnswer(String count) {
        if ("1".equals(count)) return "A";
        else if ("2".equals(count)) return "B";
        else if ("3".equals(count)) return "C";
        else return "D";
    }

    private void getStudyErrorErrorDeails() {
        mSubscription = apiService.getStudyErrorErrorDeails(getIntent().getStringExtra("cid"),getIntent().getStringExtra("id"), Constants.uid).compose(RxSchedulersHelper.<JSONObject>io_main()).subscribe(new RxSubscriber<JSONObject>() {
            @Override
            public void _onNext(JSONObject jsonObject) {
                if (jsonObject.getIntValue("code") == 1) {
                    tvName.setText(jsonObject.getJSONObject("data").getString("title"));
                    originalDataanswerBeanList.addAll(JSONArray.parseArray(jsonObject.getJSONObject("data").getJSONArray("list").toJSONString(), AnswerBean.class));
                    answerBeanList.addAll(JSONArray.parseArray(jsonObject.getJSONObject("data").getJSONArray("list").toJSONString(), AnswerBean.class));
                    homeworkAdapter = new HomeworkAdapter(mContext, answerBeanList);
                    viewpager.setAdapter(homeworkAdapter);
                    JSONArray jsonArray = jsonObject.getJSONObject("data").getJSONArray("error_list");
                    for (int i = 0; i < jsonArray.size(); i++) {
                        errorList.add(jsonArray.getString(i));
                    }
                    getAnswerList();
                    if(answerBeanList.size()>0)
                    {
                        WhetherCollecting();
                    }
                    else
                    {
                        layoutCollection.setVisibility(View.GONE);
                    }
                    
                    
                    
                    layoutBg.setVisibility(View.VISIBLE);
                } else
                    ShowToast(jsonObject.getString("msg"));
            }
        });
    }

    /** type 1 单选 2多选 3判断 4简答 5计算分析题 6综合分析题 7论述题
     8名词解释 9案例分析 10其他
     **/
    List<List<AnswerBean>> listList = new ArrayList<>();
    private void getAnswerList() {
        listList.clear();
        int index = 0;
        for (int i = 0; i < layoutSideBg.getChildCount(); i++) {
            if(i > 0)
            {
                layoutSideBg.removeView(layoutSideBg.getChildAt(i));
                //                layoutSideBg.recomputeViewAttributes(layoutSideBg.getChildAt(i));
                i--;
            }
        }
        for (int i = 0; i < 10; i++) {
            final List<AnswerBean> answerBeens = new ArrayList<>();
            for (int j = 0; j < answerBeanList.size(); j++) {
                if(((i+1)+"").equals(answerBeanList.get(j).getType_name()))
                {
                    AnswerBean answerBean = answerBeanList.get(j);
                    answerBean.setPosition(j);
                    answerBeens.add(answerBean);
                }
            }
            if(answerBeens.size()> 0)
            {
                listList.add(answerBeens);
                index++;
                View view = LayoutInflater.from(mContext).inflate(R.layout.item_side,null);
                TextView Titlte = (TextView) view.findViewById(R.id.tv_title);
                Titlte.setText(index+"."+answerBeens.get(0).getType());
                MyGridView myGridView = (MyGridView) view.findViewById(R.id.gr_ti);
                sizeUtils.setLayoutSizeWidht(myGridView, 400);
                myGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        viewpager.setCurrentItem(answerBeens.get(position).getPosition());
                        showDrawer();
                    }
                });
                AnswerErrorItemAdapter answerItemAdapter = new AnswerErrorItemAdapter(answerBeens, mActivity, papersCount,errorList);
                myGridView.setAdapter(answerItemAdapter);
                layoutSideBg.addView(view);
            }
        }
    }




    private void WhetherCollecting()
    {
        if(answerBeanList.get(viewpager.getCurrentItem()).getIs_shoucang() == 0)
            ivCollection.setImageResource(R.mipmap.ic_guanzwu_y);
        else
            ivCollection.setImageResource(R.mipmap.ic_guanzyou_y);
    }


    private void getStudyQuestionsReturnFraction() {
        int answerscount = 0;
        List<AnswerBean> answerPointsBeanList = new ArrayList<>();
        for (int i = 0; i < listList.size(); i++) {
            if (listList.get(i).get(0).getType_name().equals("1") || listList.get(i).get(0).getType_name().equals("2"))
                answerPointsBeanList.addAll(listList.get(i));
        }
        List<String> stringList = new ArrayList<>();
        List<String> correctList = new ArrayList<>();
        for (int i = 0; i < answerPointsBeanList.size(); i++) {
            if (answerPointsBeanList.get(i).getSelectedIndex() != null) {
                String answers[] = answerPointsBeanList.get(i).getSelectedIndex().split(",");
                int count = 0;
                for (int i1 = 0; i1 < answerPointsBeanList.get(i).getTrue_list().size(); i1++) {
                    for (int j = 0; j < answers.length; j++) {
                        if (answerPointsBeanList.get(i).getTrue_list().get(i1).equals(answers[j])) {
                            count++;
                            break;
                        }
                    }
                }
                if (answers.length != count) {
                    stringList.add(answerPointsBeanList.get(i).getId());
                } else {
                    correctList.add(answerPointsBeanList.get(i).getId());
                    answerscount++;
                }
            }
        }
        double points = 100 / (double)answerPointsBeanList.size();
        DecimalFormat df = new DecimalFormat("0");
        final String pointsContent = df.format(points * answerscount);
        String json = JSONArray.toJSONString(stringList);
        String correct = JSONArray.toJSONString(correctList);
        mSubscription = apiService.getStudyQuestionsReturnFraction(getIntent().getStringExtra("id"), Constants.uid, pointsContent, getIntent().getStringExtra("cid"), json,correct).compose(RxSchedulersHelper.<JSONObject>io_main()).subscribe(new RxSubscriber<JSONObject>() {
            @Override
            public void _onNext(JSONObject jsonObject) {
                if (jsonObject.getIntValue("code") == 1) {
                    papersCount = 1;
                    getAnswerList();
                    if (!idDrawerlayout.isDrawerOpen(Gravity.RIGHT)) {
                        idDrawerlayout.openDrawer(Gravity.RIGHT);
                    }
                    answerCount = 1;
                    tvPoints.setText(pointsContent + "分");
                    layoutTable.getChildAt(1).setVisibility(View.GONE);
                    layoutTable.getChildAt(2).setVisibility(View.GONE);
                    getAccordingAnswer(viewpager.getCurrentItem());
                    homeworkAdapter.notifyDataSetChanged();
                    layoutScore.setVisibility(View.VISIBLE);
                } else
                    ShowToast(jsonObject.getString("msg"));
            }
        });
    }



    private void getStudyQuestionsCollection(final int position)
    {
        mSubscription = apiService.getStudyQuestionsCollection(getIntent().getStringExtra("id"),answerBeanList.get(position).getId(),Constants.uid,answerBeanList.get(position).getIs_shoucang() == 0?"1":"0").compose(RxSchedulersHelper.<JSONObject>io_main()).subscribe(new RxSubscriber<JSONObject>() {
            @Override
            public void _onNext(JSONObject jsonObject) {
                if(jsonObject.getIntValue("code") == 1)
                {
                    if(answerBeanList.get(position).getIs_shoucang() == 1)
                    {
                        answerBeanList.get(position).setIs_shoucang(0);
                    }
                    else
                    {
                        answerBeanList.get(position).setIs_shoucang(1);
                    }
                    WhetherCollecting();
                }
                else
                    ShowToast(jsonObject.getString("msg"));
            }
        });
    }



}
