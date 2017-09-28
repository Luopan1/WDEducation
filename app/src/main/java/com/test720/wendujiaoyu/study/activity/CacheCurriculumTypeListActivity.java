package com.test720.wendujiaoyu.study.activity;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.test720.wendujiaoyu.R;
import com.test720.wendujiaoyu.baseUi.BaseToolbarActivity;
import com.test720.wendujiaoyu.study.bean.CacheLisstOne;
import com.test720.wendujiaoyu.study.bean.DownloadBean;
import com.test720.wendujiaoyu.study.utils.BaseRecyclerAdapter;
import com.test720.wendujiaoyu.study.utils.BaseRecyclerHolder;
import com.test720.wendujiaoyu.study.utils.ItemAnimatorFactory;
import com.test720.wendujiaoyu.study.utils.SpacesItemDecoration;
import com.test720.wendujiaoyu.utills.Constants;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class CacheCurriculumTypeListActivity extends BaseToolbarActivity {
    @BindView(R.id.my_recycler_view)
    RecyclerView myRecyclerView;
    private BaseRecyclerAdapter<CacheLisstOne> adapter;
    private List<CacheLisstOne> cacheLisstOnes = new ArrayList<>();
    @Override
    protected int getContentView() {
        return R.layout.activity_cache_curriculum_type_list;
    }

    @Override
    protected void initData() {
        initToobar(getIntent().getStringExtra("name"));
        adapter = new BaseRecyclerAdapter<CacheLisstOne>(context, cacheLisstOnes, R.layout.item_major_choice) {
            @Override
            public void convert(BaseRecyclerHolder holder, CacheLisstOne item, int position, boolean isScrolling) {
//                int tolt = sizeUtils.screenWidth()/2-30;
//                sizeUtils.setLayoutSizeWidht(holder.getView(R.id.layout_bg),tolt);
//                sizeUtils.setLayoutSize(holder.getView(R.id.layout_within),tolt,tolt);
//                sizeUtils.setLayoutSizeHeight(holder.getView(R.id.iv_icon),((int)(tolt/3.5))*2);
                sizeUtils.setTextSize((TextView) holder.getView(R.id.tv_title),20);
                sizeUtils.setLayoutSize(holder.getView(R.id.layout_num),45,45);
                sizeUtils.setTextSize((TextView) holder.getView(R.id.tv_num),15);
                holder.getView(R.id.layout_num).setVisibility(View.VISIBLE);
                holder.setText(R.id.tv_title,cacheLisstOnes.get(position).getName());
                holder.setText(R.id.tv_num,cacheLisstOnes.get(position).getNum()+"");
                if(cacheLisstOnes.get(position).getImg().contains(Constants.ImagHost))
                    holder.setImageByUrl(R.id.iv_icon, cacheLisstOnes.get(position).getImg());
                else
                    holder.setImageByUrl(R.id.iv_icon, new File(cacheLisstOnes.get(position).getImg()));
            }
        };
        myRecyclerView.setLayoutManager(new GridLayoutManager(mActivity, 2, GridLayoutManager.VERTICAL, false));
        myRecyclerView.setItemAnimator(ItemAnimatorFactory.slidein());
        myRecyclerView.addItemDecoration(new SpacesItemDecoration(2, 0, 0, 7, 3));
        myRecyclerView.setAdapter(adapter);
        getDataList();
    }

    @Override
    protected void setListener() {
        adapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position) {
                startActivity(new Intent(mContext,CacheListActivity.class).putExtra("id",getIntent().getStringExtra("id")).putExtra("name",cacheLisstOnes.get(position).getName()).putExtra("title",getIntent().getStringExtra("title")).putExtra("typeId",cacheLisstOnes.get(position).getId()));
            }
        });
    }

    @Override
    protected void initBase() {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        getDataList();
    }

    private void getDataList()
    {
        cacheLisstOnes.clear();
        String json = spUtils.get("src", Constants.uniqueness + "json", "").toString();
        if(!"".equals(json))
        {
            List<DownloadBean> downloadBeens = JSONArray.parseArray(json,DownloadBean.class);
            String list = spUtils.get("cache", Constants.uniqueness + "list"+getIntent().getStringExtra("id"), "").toString();
            List<CacheLisstOne> cacheLisstOnes1 = JSONArray.parseArray(list,CacheLisstOne.class);

                for (int i = 0; i < downloadBeens.size(); i++) {
                    for (int i1 = 0; i1 < cacheLisstOnes1.size(); i1++) {
                        if(downloadBeens.get(i).getType().equals(getIntent().getStringExtra("id")))
                        {
                            Log.e("====",cacheLisstOnes1.get(i1).getId()+"===="+downloadBeens.get(i).getCourseType());
                            if(downloadBeens.get(i).getCourseType().equals(cacheLisstOnes1.get(i1).getId()))
                            {
                                Log.e("====",getIntent().getStringExtra("title")+"===="+downloadBeens.get(i).getFormat());
                                if(getIntent().getStringExtra("title").equals(downloadBeens.get(i).getFormat()))
                                {
                                    cacheLisstOnes1.get(i1).setNum(cacheLisstOnes1.get(i1).getNum()+1);
                                    break;
                                }
                            }
                        }

                    }
                }
                for (int i = 0; i < cacheLisstOnes1.size(); i++) {
                    if (cacheLisstOnes1.get(i).getNum() == 0) {
                        cacheLisstOnes1.remove(i);
                        i--;
                    }
                }
            cacheLisstOnes.addAll(cacheLisstOnes1);
            adapter.notifyDataSetChanged();
        }
    }


}
