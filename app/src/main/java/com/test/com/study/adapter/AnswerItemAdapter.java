package com.test.com.study.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.test.com.R;
import com.test.com.baseUi.MBaseAdapter;
import com.test.com.study.bean.AnswerBean;
import com.test.com.study.utils.SizeUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jie on 2017/8/15.
 */

public class AnswerItemAdapter extends MBaseAdapter<AnswerBean> {
    private List<Integer> integers = new ArrayList<>();
    private SizeUtils sizeUtils;

    public AnswerItemAdapter(List<AnswerBean> list, Activity mActivity, int stats) {
        super(list, mActivity, stats);
        sizeUtils = new SizeUtils(mActivity);
        int index = 0;
        for (int i = 0; i < list.size(); i++) {
            if(index == i)
            {
                integers.add(i);
                index = index+5;
            }
        }
    }

    @Override
    public View getXView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mActivity).inflate(R.layout.item_answer_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        sizeUtils.setLayoutSize(viewHolder.layoutBg,80,80);
        sizeUtils.setLayoutSize(viewHolder.layoutTopicBg,75,75);
        sizeUtils.setTextSize(viewHolder.tvTitle,16);
        viewHolder.tvTitle.setText((position+1)+"");
        if(position > 4)
            viewHolder.layoutBg.setBackground(mActivity.getResources().getDrawable(R.drawable.shape_top_rmain_list_bg));
        else
            viewHolder.layoutBg.setBackground(mActivity.getResources().getDrawable(R.drawable.shape_main_left_list_bg));

        for (int i = 0; i <integers.size() ; i++) {
            if(position < integers.get(i))
            {
                break;
            }
            if(position == integers.get(i))
            {
                if(i == 0)
                    viewHolder.layoutBg.setBackground(mActivity.getResources().getDrawable(R.drawable.shape_main_list_bg));
                else
                    viewHolder.layoutBg.setBackground(mActivity.getResources().getDrawable(R.drawable.shape_top_main_list_bg));

                break;
            }
        }
        viewHolder.tvTitle.setTextColor(mActivity.getResources().getColor(R.color.text_color));
        viewHolder.layoutTopicBg.setBackgroundColor(0);
        viewHolder.ivDui.setVisibility(View.GONE);
        if(stats == 1)
        {
            if("1".equals(list.get(position).getType_name()) || "2".equals(list.get(position).getType_name()))
            {
                if(list.get(position).getSelectedIndex() == null)
                {
                    viewHolder.layoutTopicBg.setBackgroundColor(mActivity.getResources().getColor(R.color.white));
                    viewHolder.tvTitle.setTextColor(mActivity.getResources().getColor(R.color.text_color));
                }
                else
                {
                    String answers[] = list.get(position).getSelectedIndex().split(",");
                    int count = 0;
                    for (int i = 0; i < list.get(position).getTrue_list().size(); i++) {
                        for (int j = 0; j <answers.length; j++) {
                            if(list.get(position).getTrue_list().get(i).equals(answers[j]))
                            {
                                count++;
                                break;
                            }
                        }
                    }
                    if(answers.length != count)
                    {
                        viewHolder.layoutTopicBg.setBackgroundColor(mActivity.getResources().getColor(R.color.base_color));
                        viewHolder.tvTitle.setTextColor(mActivity.getResources().getColor(R.color.white));
                    }
                    else
                    {
                        viewHolder.tvTitle.setTextColor(mActivity.getResources().getColor(R.color.base_color));
                        viewHolder.ivDui.setVisibility(View.VISIBLE);
                    }
                }
            }
        }
        else
        {
            if(list.get(position).getSelectedIndex() != null)
            {
                viewHolder.tvTitle.setTextColor(mActivity.getResources().getColor(R.color.base_color));
            }
        }
        return convertView;
    }

    class ViewHolder {
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.layout_topic_bg)
        RelativeLayout layoutTopicBg;
        @BindView(R.id.layout_bg)
        RelativeLayout layoutBg;
        @BindView(R.id.iv_dui)
        ImageView ivDui;


        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
