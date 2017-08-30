package com.test.com.study.activity;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.test.com.R;
import com.test.com.baseUi.BaseToolbarActivity;
import com.test.com.study.bean.CacheLisstOne;
import com.test.com.study.bean.DownloadBean;
import com.test.com.study.utils.BaseRecyclerAdapter;
import com.test.com.study.utils.BaseRecyclerHolder;
import com.test.com.study.utils.ItemAnimatorFactory;
import com.test.com.study.utils.SpacesItemDecoration;
import com.test.com.utills.Constants;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class CacheCurriculumListActivity extends BaseToolbarActivity {
    @BindView(R.id.my_recycler_view)
    RecyclerView myRecyclerView;
    private BaseRecyclerAdapter<CacheLisstOne> adapter;
    private List<CacheLisstOne> cacheLisstOnes = new ArrayList<>();

    @Override
    protected int getContentView() {
        return R.layout.activity_cache_curriculum_list;
    }

    @Override
    protected void initData() {
        initToobar("我的自考");
        adapter = new BaseRecyclerAdapter<CacheLisstOne>(context, cacheLisstOnes, R.layout.item_major_choice) {
            @Override
            public void convert(BaseRecyclerHolder holder, CacheLisstOne item, int position, boolean isScrolling) {
//                int tolt = sizeUtils.screenWidth()/2-30;
//                sizeUtils.setLayoutSizeWidht(holder.getView(R.id.layout_bg),tolt);
//                sizeUtils.setLayoutSize(holder.getView(R.id.layout_within),tolt,tolt);
//                sizeUtils.setLayoutSizeHeight(holder.getView(R.id.iv_icon),((int)(tolt/3.5))*2);
                sizeUtils.setTextSize((TextView) holder.getView(R.id.tv_title),20);
                holder.setText(R.id.tv_title,cacheLisstOnes.get(position).getName());
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
                startActivity(new Intent(mContext,CacheCurriculumTypeListActivity.class).putExtra("title",getIntent().getStringExtra("title")).putExtra("id",cacheLisstOnes.get(position).getId()).putExtra("name",cacheLisstOnes.get(position).getName()));
            }
        });
    }

    @Override
    protected void initBase() {

    }

    @Override
    public void onClick(View v) {

    }

    private void getDataList()
    {
        cacheLisstOnes.clear();
        String json = spUtils.get("src", Constants.uniqueness + "json", "").toString();
        if(!"".equals(json))
        {
            List<DownloadBean> downloadBeens = JSONArray.parseArray(json,DownloadBean.class);
            String list = spUtils.get("cache", Constants.uniqueness + "list", "").toString();
            List<CacheLisstOne> cacheLisstOnes1 = JSONArray.parseArray(list,CacheLisstOne.class);
            for (int i = 0; i < downloadBeens.size(); i++) {
                for (int i1 = 0; i1 < cacheLisstOnes1.size(); i1++) {
                   if(downloadBeens.get(i).getType().equals(cacheLisstOnes1.get(i1).getId()))
                   {
                       if(getIntent().getStringExtra("title").equals(downloadBeens.get(i).getFormat()))
                       {
                           cacheLisstOnes1.get(i1).setNum(cacheLisstOnes1.get(i1).getNum()+1);
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


    @Override
    protected void onResume() {
        super.onResume();
        getDataList();
    }
}
