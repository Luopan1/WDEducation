package com.test.com.study.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.test.com.R;
import com.test.com.baseUi.MBaseAdapter;
import com.test.com.study.bean.AnswerItemBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jie on 2017/8/15.
 */

public class AnswerAdapter extends MBaseAdapter<AnswerItemBean> {
    public AnswerAdapter(List<AnswerItemBean> list, Activity mActivity) {
        super(list, mActivity);
    }

    @Override
    public View getXView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mActivity).inflate(R.layout.item_answer, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if(list.get(position).getSelectedIndex() == 0)
            viewHolder.ivGou.setImageResource(R.mipmap.quan);
        else
            viewHolder.ivGou.setImageResource(R.mipmap.yuanquan);
        viewHolder.tvTitle.setText(list.get(position).getTitle());
        if(list.get(position).getCorrectAnswerCount() == 1)
        {
            viewHolder.layoutSelected.setBackground(mActivity.getResources().getDrawable(R.drawable.drawoble_base_y));
        }
        else
        {
            viewHolder.layoutSelected.setBackground(null);
        }
        return convertView;
    }


    static class ViewHolder {
        @BindView(R.id.iv_gou)
        ImageView ivGou;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.layout_selected)
        LinearLayout layoutSelected;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
